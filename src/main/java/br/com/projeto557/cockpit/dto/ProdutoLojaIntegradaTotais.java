package br.com.projeto557.cockpit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoLojaIntegradaTotais {
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total_count;

}
