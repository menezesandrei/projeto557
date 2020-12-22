package br.com.projeto557.cockpit.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrecoLojaIntegradaHeader {
    private ProdutoLojaIntegradaTotais meta;
    List<PrecoLojaIntegrada> objects = null;
}
