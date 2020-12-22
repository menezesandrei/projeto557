package br.com.projeto557.cockpit;

import br.com.projeto557.cockpit.dto.EstoqueLojaIntegradaHeader;
import br.com.projeto557.cockpit.dto.ProdutoLojaIntegradaHeader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Projeto557ApplicationTests {

    @Test
    public void consumerProdutoLojaIntegradaTest() {
//https://api.awsli.com.br/v1/produto/?format=json&chave_api=13bde734dd6b99b08293&chave_aplicacao=b2761f38-783e-4c10-9fe7-413fe7952dba&limit=20

        RestTemplate movieTemplate = new RestTemplate();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.awsli.com.br")
                .path("v1/produto")
                .queryParam("format", "json")
                .queryParam("chave_api", "13bde734dd6b99b08293")
                .queryParam("chave_aplicacao", "b2761f38-783e-4c10-9fe7-413fe7952dba")
                .queryParam("offset", "220")
                .queryParam("limit", "20")
                .build();

        ResponseEntity<ProdutoLojaIntegradaHeader> response = movieTemplate.getForEntity(uri.toUriString(), ProdutoLojaIntegradaHeader.class);
        System.out.println(response.getBody());
    }

    @Test
    public void consumerEstoqueLojaIntegradaTest() {
//https://api.awsli.com.br/v1/produto_estoque/?format=json&chave_api=13bde734dd6b99b08293&chave_aplicacao=b2761f38-783e-4c10-9fe7-413fe7952dba

        RestTemplate movieTemplate = new RestTemplate();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.awsli.com.br")
                .path("v1/produto_estoque")
                .queryParam("format", "json")
                .queryParam("chave_api", "13bde734dd6b99b08293")
                .queryParam("chave_aplicacao", "b2761f38-783e-4c10-9fe7-413fe7952dba")
                .queryParam("offset", "0")
                .queryParam("limit", "20")
                .build();

        ResponseEntity<EstoqueLojaIntegradaHeader> response = movieTemplate.getForEntity(uri.toUriString(), EstoqueLojaIntegradaHeader.class);
        System.out.println(response.getBody());
    }

    @Test
    public void testarIteracao() {
        for (int i = 0; i < 200; i += 20) {
            System.out.println(i);
        }
    }

}
