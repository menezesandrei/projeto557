package br.com.projeto557.cockpit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueLojaIntegrada {

    private boolean gerenciado;
    private Integer id;
    private String produto;
    private Integer quantidade;
    private Integer quantidade_disponivel;
    private Integer quantidade_reservada;
    private String resource_uri;
    private Integer situacao_em_estoque;
    private Integer situacao_sem_estoque;
}
