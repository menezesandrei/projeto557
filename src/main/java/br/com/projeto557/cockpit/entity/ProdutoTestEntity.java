package br.com.projeto557.cockpit.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "produto_test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProdutoTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;
    private int prod_id_loja;
    private String tipo;
    private String modelo;
    private BigDecimal preco_venda;
    private BigDecimal preco_custo;
    private int quantidade;
    private Timestamp data_cadastro;
    private String tamanho;
}
