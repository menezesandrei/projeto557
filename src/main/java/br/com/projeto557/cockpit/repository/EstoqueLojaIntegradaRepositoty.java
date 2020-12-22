package br.com.projeto557.cockpit.repository;

import br.com.projeto557.cockpit.entity.EstoqueLojaIntegradaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueLojaIntegradaRepositoty extends CrudRepository<EstoqueLojaIntegradaEntity, Integer> {
}
