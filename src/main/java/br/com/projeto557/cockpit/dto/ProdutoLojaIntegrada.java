package br.com.projeto557.cockpit.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoLojaIntegrada {

    private String apelido;
    private boolean ativo;
    private boolean bloqueado;
    private List<String> categorias= null;
    private String descricao_completa;
    private List<String> filhos= null;
    private List<String> grades= null;
    private String gtin;
    private Integer id;
    private Integer id_externo;
    private String mpn;
    private String ncm;
    private String nome;
    private boolean removido;
    private String resource_uri;
    private String seo;
    private String sku;
    private String tipo;
    private String url;
    private String url_video_youtube;
    private List<String> variacoes= null;
}
