package br.com.projeto557.cockpit.consumes;

import br.com.projeto557.cockpit.Utils.Utils;
import br.com.projeto557.cockpit.dto.ProdutoLojaIntegrada;
import br.com.projeto557.cockpit.dto.ProdutoLojaIntegradaHeader;
import br.com.projeto557.cockpit.dto.ProdutoLojaIntegradaTotais;
import br.com.projeto557.cockpit.entity.ProdutoTemporarioEntity;
import br.com.projeto557.cockpit.repository.ProdutoTemporarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class ApiLojaIntegrada {

@Autowired
ProdutoTemporarioRepository produtoTemporarioRepository;

public void sincronizarEstoque(){
    consumirProdutos("0","20");
}

public void consumirProdutos(String offset, String limit){



    RestTemplate movieTemplate = new RestTemplate();
    UriComponents uri = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.awsli.com.br")
            .path("v1/produto")
            .queryParam("format", "json")
            .queryParam("chave_api", "13bde734dd6b99b08293")
            .queryParam("chave_aplicacao", "b2761f38-783e-4c10-9fe7-413fe7952dba")
            .queryParam("offset", offset)
            .queryParam("limit", limit)
            .build();

    ResponseEntity<ProdutoLojaIntegradaHeader> response = movieTemplate.getForEntity(uri.toUriString(), ProdutoLojaIntegradaHeader.class);
    Optional<ProdutoLojaIntegradaTotais> headerResponse = Optional.ofNullable(response.getBody().getMeta());
    Optional<List<ProdutoLojaIntegrada>> produtoLojaIntegradaList = Optional.ofNullable(response.getBody().getObjects());
    saveTemporaryProduct(produtoLojaIntegradaList);

    if(headerResponse.isPresent()) {
         Integer contadorOffset = new Integer(offset);
        if ( contadorOffset <= headerResponse.get().getTotal_count()){
            contadorOffset += new Integer(limit);
            consumirProdutos(contadorOffset.toString(),limit);
        }
    }

    //return response.getBody();
}
public void consumirEstoque(){}
public void consumirValores(){}

public void saveTemporaryProduct(Optional<List<ProdutoLojaIntegrada>> produtoLojaIntegradaList){
    if(produtoLojaIntegradaList.isPresent()){
        for (ProdutoLojaIntegrada produto:produtoLojaIntegradaList.get()) {
            //salvando produto pai
            if(produto.getFilhos() != null){
                //produtoTemporarioRepository.save(montaProdutoTemporario(produto));
                //salvando ou atualizando produtos filhos
                saveProdutosFilhos(produto);
            }else{
                if(!produtoTemporarioRepository.existsById(produto.getId())){
                   try {
                       produtoTemporarioRepository.save(
                               ProdutoTemporarioEntity.builder()
                                       .id_prod_loja(produto.getId())
                                       .tamanho(produto.getSku().substring(10).toUpperCase()).build()
                       );
                   }catch (StringIndexOutOfBoundsException ex){
                       produtoTemporarioRepository.save(
                               ProdutoTemporarioEntity.builder()
                                       .id_prod_loja(produto.getId())
                                       .tipo(Utils.getArrayString(produto.getNome(),"-")[0].toUpperCase())
                                       .modelo(Utils.getArrayString(produto.getNome(), "-")[1].toUpperCase()).build()
                       );
                       continue;
                   }
                }
            }
            }
        }
    }


private void saveProdutosFilhos(ProdutoLojaIntegrada produto){
    Optional<List<String>> produtosFilhos = Optional.ofNullable(produto.getFilhos());
    if(produtosFilhos.isPresent()){
        for (String id : produtosFilhos.get()) {
            String[] idCompleto = Utils.getArrayString(id,"/");
            Optional<ProdutoTemporarioEntity> produtoTemporarioEntity = produtoTemporarioRepository.findById(new Integer(idCompleto[idCompleto.length - 1 ]));
            if(produtoTemporarioEntity.isPresent()) {
                try {
                    produtoTemporarioEntity.get().setTipo(Utils.getArrayString(produto.getNome(), "-")[0].toUpperCase());
                    produtoTemporarioEntity.get().setModelo(Utils.getArrayString(produto.getNome(), "-")[1].toUpperCase() + " - " + Utils.getArrayString(produto.getNome(), "-")[2].toUpperCase());
                    produtoTemporarioRepository.save(
                            produtoTemporarioEntity.get()
                    );
                }catch (ArrayIndexOutOfBoundsException ex){
                    produtoTemporarioEntity.get().setTipo(Utils.getArrayString(produto.getNome(), "-")[0].toUpperCase());
                    produtoTemporarioEntity.get().setModelo(Utils.getArrayString(produto.getNome(), "-")[1].toUpperCase());
                    produtoTemporarioRepository.save(
                            produtoTemporarioEntity.get()
                    );
                    continue;
                }
            }
        }
    }
}

private ProdutoTemporarioEntity montaProdutoTemporario(ProdutoLojaIntegrada produto){
 return  ProdutoTemporarioEntity.builder()
            .id_prod_loja(produto.getId())
            .tamanho(produto.getSku().substring(10))
            .tipo(Utils.getArrayString(produto.getNome(),"-")[0].toUpperCase())
            .modelo(Utils.getArrayString(produto.getNome(),"-")[1].toUpperCase()).build();
}

}
