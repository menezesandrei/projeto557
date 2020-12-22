package br.com.projeto557.cockpit.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "estoque_loja_integrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class EstoqueLojaIntegradaEntity {

    @Id
    private Integer id_estoque;
    private Integer id_produto;
    private Integer quantidade;

}
