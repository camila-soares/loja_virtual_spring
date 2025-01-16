package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.VendaCompraLojaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface VendaCompraLojaVirtualRepository extends CrudRepository<VendaCompraLojaVirtual, Long> {




    @Modifying
    @Query(value = "update VendaCompraLojaVirtual v set  v.excluido = true where v.id = ?1")
    void updateById(Long id);

    @Query(value="select a from VendaCompraLojaVirtual a where a.id = ?1 and a.excluido = false")
    VendaCompraLojaVirtual findByIdExclusao(Long id);

    @Query(value="select i.vendaCompraLojaVirtual from ItemVendaLoja i where "
            + " i.vendaCompraLojaVirtual.excluido = false and i.produto.id = ?1")
    List<VendaCompraLojaVirtual> vendaPorProduto(Long idProduto);

    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.produto.nome)) like %?1%")
    List<VendaCompraLojaVirtual> vendaPorNomeProduto(String valor);


    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false and i.vendaCompraLojaVirtual.pessoa.id = ?1")
    List<VendaCompraLojaVirtual> vendaPorCliente(Long idCliente);

    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.pessoa.nome)) like %?1%")
    List<VendaCompraLojaVirtual> vendaPorNomeCliente(String nomepessoa);


    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false "
            + " and upper(trim(i.vendaCompraLojaVirtual.pessoa.nome)) like %?1% "
            + " and i.vendaCompraLojaVirtual.pessoa.cpf = ?2")
    List<VendaCompraLojaVirtual> vendaPorNomeCliente(String nomepessoa, String cpf);


    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.pessoa.cpf)) like %?1%")
    List<VendaCompraLojaVirtual> vendaPorCpfClienteLike(String cpf);


    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.pessoa.cpf)) = ?1")
    List<VendaCompraLojaVirtual> vendaPorCpfClienteIgual(String cpf);

    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false" +
            " and upper(trim(i.vendaCompraLojaVirtual.enderecoCobranca.logradouro)) "
            + " like %?1%")
    List<VendaCompraLojaVirtual> vendaPorEndereCobranca(String enderecoCobranca);



    @Query(value="select distinct (i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false" +
            " and upper(trim(i.vendaCompraLojaVirtual.enderecoEntrega.logradouro)) "
            + " like %?1% order by 10")
    List<VendaCompraLojaVirtual> vendaPorEnderecoEntrega(String enderecoEntrega);


    @Query(value="select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
            + " where i.vendaCompraLojaVirtual.excluido = false " +
            "and i.vendaCompraLojaVirtual.dataVenda BETWEEN ?1 and ?2 order by i.vendaCompraLojaVirtual.dataVenda desc ")
    List<VendaCompraLojaVirtual> consultaVendaFaixaData(Date data1, Date data2);

    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update vd_cp_loja_virt set codigo_etiqueta = ?1 where id = ?2")
    void updateEtiqueta(String idEtiqueta, Long idVenda);

    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update vd_cp_loja_virt set url_imprime_etiqueta = ?1 where id = ?2")
    void updateURLEtiqueta(String urlEtiqueta, Long id);


    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update vd_cp_loja_virt set status_venda_loja_virtual = 'FINALIZADA' where id = ?1")
    void updateFinalizaVenda(Long id);
}
