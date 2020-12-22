package br.com.projeto557.cockpit.repository;

import br.com.projeto557.cockpit.entity.PrecoLojaIntegradaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecoLojaIntegradaRepository extends CrudRepository<PrecoLojaIntegradaEntity, Integer> {
}
