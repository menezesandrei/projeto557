package br.com.projeto557.cockpit.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;
    private String tipo;
    private String modelo;
    private BigDecimal valor;
    private int quantidade;
    private Timestamp data_cadastro;
    private String descricao;
    private boolean ativo;
    private String lg;
    private String esp;
    private String peso;
    private String tamanho;
    private String profundidade;

}
