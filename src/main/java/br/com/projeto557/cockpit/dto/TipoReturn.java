package br.com.projeto557.cockpit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoReturn {

    private String tipo;
    private int quantidade;

}
