package br.com.projeto557.cockpit.repository;

import br.com.projeto557.cockpit.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface ProdutoRepository extends CrudRepository<ProdutoEntity, Integer> {

    @Query(
            value = "select tipo,sum(quantidade) as quantidade, sum(quantidade_ideal) as qtd_ideal from (\n" +
                    " select lower(tipo) as tipo,quantidade,\n" +
                    " CASE \n" +
                    " When tipo = 'CAMISETA' and tamanho = 'EXG' then 2\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'GG' then 6\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'G' then 7\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'M' then 7\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'P' then 6\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'EXG' then 5\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'GG' then 7\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'G' then 7\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'M' then 7\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'P' then 4\n" +
                    "  When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'EXG' then 5\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'GG' then 7\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'G' then 7\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'M' then 7\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'P' then 4\n" +
                    " else 30 end as quantidade_ideal\n" +
                    " from produto ) as produtos_totais group by tipo",
            nativeQuery = true)
    Collection<Object> findCountTypesNative();


    @Query(
            value = " select \n" +
                    " tipo,tamanho,\n" +
                    " sum(quantidade_ideal) as qtd_ideal,\n" +
                    " sum(quantidade) as qtd,sum(quantidade) - sum(quantidade_ideal) as controle,\n" +
                    "preco_custo,preco_venda,preco_custo*sum(quantidade) as total_preco_custo, \n" +
                    "preco_venda*sum(quantidade) as total_preco_venda\n" +
                    "from ( \n" +
                    " select tipo,tamanho,quantidade,preco_custo,preco_venda,\n" +
                    " CASE \n" +
                    " When tipo = 'CAMISETA' and tamanho = 'EXG' then 2\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'GG' then 6\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'G' then 7\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'M' then 7\n" +
                    " When tipo = 'CAMISETA' and tamanho = 'P' then 6\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'EXG' then 5\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'GG' then 7\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'G' then 7\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'M' then 7\n" +
                    " When tipo = 'BABY LOOK' and tamanho = 'P' then 4\n" +
                    "  When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'EXG' then 5\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'GG' then 7\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'G' then 7\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'M' then 7\n" +
                    " When tipo = 'BABY LOOK COM PEDRARIAS' and tamanho = 'P' then 4\n" +
                    " else 30 end as quantidade_ideal\n" +
                    " from produto ) as produtos_totais group by tipo,tamanho,preco_custo,preco_venda",
            nativeQuery = true)
    Collection<Object> findTotaisEstoque();

    @Query(
            value = " select tipo, sum(total)   from \n" +
                    "(select tipo , preco_custo * quantidade as total from produto ) as prod group by tipo ",
            nativeQuery = true)
    Collection<Object> findPrecoTotalPorTipo();

    @Query(
            value = "  select sum(total_custo) as total_custo, sum(total_venda) as total_venda , sum(total_venda) - sum(total_custo) as total from\n" +
                    " (select preco_custo * quantidade as total_custo, preco_venda * quantidade as total_venda\n" +
                    " from produto) as prod",
            nativeQuery = true)
    Collection<Object> findPrecoTotalGeral();

    @Transactional
    @Procedure(procedureName = "sincronizar_estoque_com_loja_integrada")
    void executaProcedureSincronizarComLojaIntegrada();
}
