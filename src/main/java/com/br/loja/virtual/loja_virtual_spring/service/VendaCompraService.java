package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.dto.ItemVendaDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioProdutoAlertaEstoqueRequestDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.VendaCompraLojaVirtualDTO;
import com.br.loja.virtual.loja_virtual_spring.enums.ContasReceberStatus;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ControlException;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.*;
import com.br.loja.virtual.loja_virtual_spring.repository.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import static java.lang.String.format;

@Service
public class VendaCompraService {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private NotaFiscalVendaaRepository notaFiscalVendaaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;

    @Autowired
    private ContaReceberRepository contaReceberRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<RelatorioProdutoAlertaEstoqueRequestDTO>
    relatorioComprasCancelada(RelatorioProdutoAlertaEstoqueRequestDTO relatorioProdutoAlertaEstoqueRequestDTO) {
        List<RelatorioProdutoAlertaEstoqueRequestDTO> retorno  = new ArrayList<RelatorioProdutoAlertaEstoqueRequestDTO>();

        String sql = """
                select  vclv.id as codigoVenda,
                        vclv.status_venda as statusVenda,
                        p.id as codigoProduto,
                        p.nome as nomeProduto,
                        p.valor_venda as valorVendaProduto,
                        p.qdt_estoque as quantidadeEstoque,
                        pf.id as codigoCliente,
                        pf.nome as nomeCliente,
                        pf.cpf as cpfCliente,
                        pf.email as emailCliente,
                        pf.telefone as telefoneCliente
                                from venda_compra_loja_virtual as vclv
                                inner join item_venda_loja as ivl on ivl.venda_compra_loja_virtual_id = vclv.id
                                inner join produto as p on p.id = ivl.produto_id
                                inner join pessoa_fisica as pf on pf.id = vclv.pessoa_id where
                                upper(p.nome) like upper() and vclv.status_venda = 'CANCELADA';
                """;

        retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RelatorioProdutoAlertaEstoqueRequestDTO.class));
        return retorno;
    }

    public List<VendaCompraLojaVirtual> consulgaPorNomeCliente(String nomepessoa) {
        List<VendaCompraLojaVirtual> lojaVirtualList = vendaCompraLojaVirtualRepository.vendaPorNomeCliente(nomepessoa);
        if (lojaVirtualList.isEmpty()) {
            return new ArrayList<>();
        }
        return lojaVirtualList;
    }

    public List<VendaCompraLojaVirtual> consultaPorEnderecoCobranca(String enderecoCobranca) {
        List<VendaCompraLojaVirtual> vendaCompraLojaVirtualList = vendaCompraLojaVirtualRepository.vendaPorEndereCobranca(enderecoCobranca);

        if (vendaCompraLojaVirtualList.isEmpty()) {
            return new ArrayList<>();
        }
        return vendaCompraLojaVirtualList;
    }

    public List<VendaCompraLojaVirtual> consultaPorEnderecoEntrega(String enderecoEntrega) {
        List<VendaCompraLojaVirtual> vendaCompraLojaVirtualList =
                vendaCompraLojaVirtualRepository.vendaPorEnderecoEntrega(enderecoEntrega);

        if (vendaCompraLojaVirtualList.isEmpty()) {
            return new ArrayList<>();
        }
        return vendaCompraLojaVirtualList;
    }

    public List<VendaCompraLojaVirtual> consultaPorNomeProduto(String valor) {

        List<VendaCompraLojaVirtual> lojaVirtualList = vendaCompraLojaVirtualRepository.vendaPorNomeProduto(valor);
        if (lojaVirtualList.isEmpty()) {
            return new ArrayList<>();
        }
        return lojaVirtualList;
    }
public List<VendaCompraLojaVirtual> consultaPorProduto(Long idProduto) {
    List<VendaCompraLojaVirtual> lojaVirtualList = vendaCompraLojaVirtualRepository.vendaPorProduto(idProduto);

    if (lojaVirtualList == null || lojaVirtualList.isEmpty()) {
        lojaVirtualList = new ArrayList<>();
    }

    return lojaVirtualList;
}
    @Transactional
    @Modifying(flushAutomatically = true)
    public void exclusaoTotalVendaBanco2(Long id) {

       String value =
                "begin;"
        + "update nota_fiscal_venda set venda_compra_loja_virtual_id = null where venda_compra_loja_virtual_id = " +id+";"+
                        "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = null;"+
                        "delete from item_venda_loja where venda_compra_loja_virtual_id = " + id+";"+
                        "delete from status_rastreio where venda_compra_loja_virtual_id = "+id +";"+
                        "delete from venda_compra_loja_virtual where id = "+id+";"+
                        "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = null;"+
                        "commit;";

        jdbcTemplate.execute(value);
    }
    public VendaCompraLojaVirtual save(VendaCompraLojaVirtual vendaCompraLojaVirtual) throws MessagingException, UnsupportedEncodingException {

        vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

           PessoaFisica pessoaFisica = pessoaFisicaRepository.save(vendaCompraLojaVirtual.getPessoa());
           vendaCompraLojaVirtual.setPessoa(pessoaFisica);


           vendaCompraLojaVirtual.getEnderecoCobranca().setPessoa(pessoaFisica);
           vendaCompraLojaVirtual.getEnderecoCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
           Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoCobranca());
           vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

           vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
           vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
           Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());


           vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);
       //}
        vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        for (int i = 0; i < vendaCompraLojaVirtual.getItemVendaLojas().size(); i++) {
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        }
        
        vendaCompraLojaVirtual.setDataVenda(Calendar.getInstance().getTime());
        vendaCompraLojaVirtual.setDataEntrega(Calendar.getInstance().getTime());
        VendaCompraLojaVirtual vendaSalva = vendaCompraLojaVirtualRepository.save(vendaCompraLojaVirtual);

        StatusRatreio statusRatreio = new StatusRatreio();
        statusRatreio.setCentroDistribuicao("Loja local");
        statusRatreio.setCidade("Recife");
        statusRatreio.setEstado("PE");
        statusRatreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        statusRatreio.setStatus("Inicio Compra");
        statusRatreio.setVendaCompraLojaVirtual(vendaSalva);

        statusRastreioRepository.save(statusRatreio);
        NotaFiscalVenda notaFiscalVenda = vendaSalva.getNotaFiscalVenda();
        // notaFiscalVenda.getVendaCompraLojaVirtual().setId(vendaSalva.getId());
        vendaSalva = vendaCompraLojaVirtualRepository.findById(vendaSalva.getId()).get();
        notaFiscalVenda.setVendaCompraLojaVirtual(vendaSalva);
        notaFiscalVendaaRepository.save(notaFiscalVenda);

        ContaReceber contaReceber = new ContaReceber();
        contaReceber.setDescricao("Venda da loja virtual n: " + vendaSalva.getId());
        contaReceber.setDtPagamento(Calendar.getInstance().getTime());
        contaReceber.setDtVencimento(Calendar.getInstance().getTime());
        contaReceber.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        contaReceber.setPessoa(vendaSalva.getPessoa());
        contaReceber.setStatus(ContasReceberStatus.QUITADA);
        contaReceber.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
        contaReceber.setValorTotal(vendaCompraLojaVirtual.getValorTotal());


        contaReceberRepository.saveAndFlush(contaReceber);

        StringBuilder msgEmail = new StringBuilder();
        msgEmail.append("Olá").append(pessoaFisica.getNome()).append("<br/>");
        msgEmail.append("Você realizou a coma de n : ").append(vendaCompraLojaVirtual.getId()).append("<br/>");
        msgEmail.append("Na loja ").append(vendaCompraLojaVirtual.getEmpresa().getNomeFantasia()).append("<br/>");

        sendEmailService.enviarEmailHtmlLoginESenha("Compra realizaada ", msgEmail.toString(), pessoaFisica.getEmail());

        //emial para o vendedor
        msgEmail = new StringBuilder();
        msgEmail.append("VOcê realizou uma venda, n : " ).append(vendaCompraLojaVirtual.getId());
        sendEmailService.enviarEmailHtmlLoginESenha("Vensda realizada", msgEmail.toString(), vendaCompraLojaVirtual.getPessoa().getEmail());

        return vendaSalva;
    }


    public VendaCompraLojaVirtual consultaVendaId(Long id) throws ExceptinLojaVirtual {

        VendaCompraLojaVirtual virtual = vendaCompraLojaVirtualRepository.findByIdExclusao(id);
        if (virtual == null) {
            throw new ExceptinLojaVirtual("Venda nao encontrada");
        }

        return virtual;
    }

    public List<VendaCompraLojaVirtual> consultaVendaFaixaData(String data1, String data2) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         Date data11 = formatter.parse(data1);
         Date data12 = formatter.parse(data2);

        List<VendaCompraLojaVirtual> lojaVirtualList =
                vendaCompraLojaVirtualRepository.consultaVendaFaixaData(data11, data12);
        if (lojaVirtualList.isEmpty()) {
            return new ArrayList<>();
        }
        return lojaVirtualList;
    }
}
