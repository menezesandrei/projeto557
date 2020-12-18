package br.com.projeto557.cockpit.repository;

import br.com.projeto557.cockpit.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProdutoRepository extends CrudRepository<ProdutoEntity, Integer> {

    @Query(
            value = "select tipo as tipo, sum(quantidade) as quantidade from produto group by tipo",
            nativeQuery = true)
    Collection<Object> findCountTypesNative();


    @Query(
            value = "select count(quantidade) as total,\n" +
                    "    count(case when quantidade > 10  or quantidade = 10 then 1 else null end) as verde,\n" +
                    "    count(case when (quantidade < 10 and quantidade > 1) or quantidade = 1 then 1 else null end) as amarelo,\n" +
                    "    count(case when quantidade = 0 then 1 else null end) as vermelho\n" +
                    "    from produto",
            nativeQuery = true)
    Collection<Object> findConsolidatedValues();

}
