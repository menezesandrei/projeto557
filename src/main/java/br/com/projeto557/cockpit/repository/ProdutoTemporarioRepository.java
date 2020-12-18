package br.com.projeto557.cockpit.repository;

import br.com.projeto557.cockpit.entity.ProdutoTemporarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoTemporarioRepository extends CrudRepository<ProdutoTemporarioEntity, Integer> {
}
