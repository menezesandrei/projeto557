package br.com.projeto557.cockpit.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "preco_loja_integrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PrecoLojaIntegradaEntity {
    @Id
    private Integer id_preco;
    private Integer id_produto;
    private BigDecimal preco_venda;
    private BigDecimal preco_custo;
}
