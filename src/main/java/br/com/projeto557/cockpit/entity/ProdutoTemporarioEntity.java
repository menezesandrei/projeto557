package br.com.projeto557.cockpit.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "produto_loja_integrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProdutoTemporarioEntity {

    @Id
    private int id_prod_loja;
    private String tipo;
    private String tamanho;
    private String modelo;
}
