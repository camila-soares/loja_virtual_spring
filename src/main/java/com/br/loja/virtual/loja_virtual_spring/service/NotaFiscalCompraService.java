package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioCompraNotaFiscalRequestDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioCompraNotaFiscalResponseDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioProdutoAlertaEstoqueRequestDTO;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalVenda;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaFiscalCompraRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaFiscalVendaaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaFiscalCompraService {

    private final NotaFiscalCompraRepository notaFiscalCompraRepository;

    private final NotaFiscalVendaaRepository notaFiscalVendaaRepository;

    private final JdbcTemplate jdbcTemplate;

    public NotaFiscalCompraService(NotaFiscalCompraRepository notaFiscalCompraRepository, NotaFiscalVendaaRepository notaFiscalVendaaRepository, JdbcTemplate jdbcTemplate) {
        this.notaFiscalCompraRepository = notaFiscalCompraRepository;
        this.notaFiscalVendaaRepository = notaFiscalVendaaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public NotaFiscalCompra cadastrarNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        return notaFiscalCompraRepository.save(notaFiscalCompra);
    }

    public List<NotaFiscalCompra> buscarNotaFiscalCompraPorDescricao(String descricao) {
        return notaFiscalCompraRepository.findNotaByDescricao(descricao);
    }

    public void deletarNotaFiscalCompra(Long id) {

        notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);
        notaFiscalCompraRepository.deleteById(id);
    }

    public NotaFiscalCompra buscarNotaFiscalCompraPorId(Long id) throws ExceptinLojaVirtual {
        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);

        if (notaFiscalCompra == null) {
            throw new ExceptinLojaVirtual("Não encontrou Nota Fisdcl com código: " + id);
        }
        return notaFiscalCompra;
    }

    public List<NotaFiscalVenda> consultarNotaFiscalVendaPorVenda(Long idVenda) throws ExceptinLojaVirtual {

        List<NotaFiscalVenda> notaFiscalVenda = notaFiscalVendaaRepository.findNotaFiscalVendaByVendaCompraLojaVirtual(idVenda);
        if (notaFiscalVenda.isEmpty()) {
            throw new ExceptinLojaVirtual("Nao enconrou nota fiscal com o codigo de venda" + idVenda);
        }
        return notaFiscalVenda;
    }


    public NotaFiscalVenda consultarNotaFiscalVendaPorVendaUnica(Long idVenda) throws ExceptinLojaVirtual {

        NotaFiscalVenda notaFiscalVenda = notaFiscalVendaaRepository.findNotaFiscalVendaByVenda(idVenda);
        if (notaFiscalVenda == null) {
            throw new ExceptinLojaVirtual("Nao enconrou nota fiscal com o codigo de venda" + idVenda);
        }
        return notaFiscalVenda;
    }

    public List<RelatorioCompraNotaFiscalResponseDTO>   relatorioProCompradoNotaFiscalCompra
            (RelatorioCompraNotaFiscalRequestDTO fiscalRequestDTO) {


        List<RelatorioCompraNotaFiscalResponseDTO> retorno  = new ArrayList<RelatorioCompraNotaFiscalResponseDTO>();
        String sql = "select p.id as codigoProduto, p.nome as nomeProduto, " +
                " p.valor_venda as valorVendaProduto, ntp.quantidade as quantidadeComprada, " +
                "pj.id as codigoFornecedor, pj.nome as nomeFornecedor, cfc.data_compra as dataCompra " +
                "from nota_fiscal_compra as cfc " +
                "inner join nota_item_produto as ntp on cfc.id = ntp.nota_fiscal_compra_id " +
                "inner join produto as p on p.id = ntp.produto_id " +
                "inner join pessoa_juridica as pj on pj.id = cfc.empresa_id where "+
                " cfc.data_compra >='"+fiscalRequestDTO.getDataInicial()+"' and "+
                "cfc.data_compra <='"+fiscalRequestDTO.getDataFinal()+"' ";
                if (!fiscalRequestDTO.getCodigoNota().isEmpty()) {
                    sql += " and cfc.id = " + fiscalRequestDTO.getCodigoNota() + " ";
                }
                if (!fiscalRequestDTO.getCodigoProduto().isEmpty()) {
                    sql += " and p.id = " + fiscalRequestDTO.getCodigoProduto() + " ";
                }
                if (!fiscalRequestDTO.getNomeProduto().isEmpty()) {
                    sql = "upper(p.nomeProduto) like upper('%"+fiscalRequestDTO.getNomeProduto()+"') + " ;
                }
                if (!fiscalRequestDTO.getNomeFornecedor().isEmpty()) {
                    sql = "upper(pj.nome) like upper('%"+fiscalRequestDTO.getNomeFornecedor()+"') + " ;
                }
        retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RelatorioCompraNotaFiscalResponseDTO.class));
        return retorno;
    }

    public List<RelatorioProdutoAlertaEstoqueRequestDTO>
    relatorioProEstoqueNotaFiscalCompra(RelatorioProdutoAlertaEstoqueRequestDTO relatorioProdutoAlertaEstoqueRequestDTO) {
        List<RelatorioProdutoAlertaEstoqueRequestDTO> retorno  = new ArrayList<RelatorioProdutoAlertaEstoqueRequestDTO>();

        String sql = "select p.id as codigoProduto, p.nome as nomeProduto, " +
                " p.valor_venda as valorVendaProduto, p.qdt_estoque as quantidadeEstoque, p.qtde_alerta_estoque as alertaEstoque, ntp.quantidade as quantidadeComprada, " +
                "pj.id as codigoFornecedor, pj.nome as nomeFornecedor, cfc.data_compra as dataCompra " +
                "from nota_fiscal_compra as cfc " +
                "inner join nota_item_produto as ntp on cfc.id = ntp.nota_fiscal_compra_id " +
                "inner join produto as p on p.id = ntp.produto_id " +
                "inner join pessoa_juridica as pj on pj.id = cfc.empresa_id where "+
                " cfc.data_compra >='"+relatorioProdutoAlertaEstoqueRequestDTO.getDataInicial()+"' and "+
                "cfc.data_compra <='"+relatorioProdutoAlertaEstoqueRequestDTO.getDataFinal()+"' "+
         " and p.alerta_qtde_estoque = true and p.qdt_estoque <= p.qtde_alerta_estoque ";
        if (!relatorioProdutoAlertaEstoqueRequestDTO.getCodigoNota().isEmpty()) {
            sql += " and cfc.id = " + relatorioProdutoAlertaEstoqueRequestDTO.getCodigoNota() + " ";
        }
        if (!relatorioProdutoAlertaEstoqueRequestDTO.getCodigoProduto().isEmpty()) {
            sql += " and p.id = " + relatorioProdutoAlertaEstoqueRequestDTO.getCodigoProduto() + " ";
        }
        if (!relatorioProdutoAlertaEstoqueRequestDTO.getNomeProduto().isEmpty()) {
            sql = "upper(p.nomeProduto) like upper('%"+relatorioProdutoAlertaEstoqueRequestDTO.getNomeProduto()+"') + " ;
        }
        if (!relatorioProdutoAlertaEstoqueRequestDTO.getNomeFornecedor().isEmpty()) {
            sql = "upper(pj.nome) like upper('%"+relatorioProdutoAlertaEstoqueRequestDTO.getNomeFornecedor()+"') + " ;
        }
        retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RelatorioProdutoAlertaEstoqueRequestDTO.class));
        return retorno;
    }
}
