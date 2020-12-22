package br.com.projeto557.cockpit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrecoLojaIntegrada {

    private Integer id;
    private String cheio;
    private String custo;
    private String produto;
    private String promocional;
    private String resource_uri;
}
