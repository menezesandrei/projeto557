package br.com.projeto557.cockpit.consumes;

import br.com.projeto557.cockpit.Utils.Utils;
import br.com.projeto557.cockpit.dto.*;
import br.com.projeto557.cockpit.entity.EstoqueLojaIntegradaEntity;
import br.com.projeto557.cockpit.entity.PrecoLojaIntegradaEntity;
import br.com.projeto557.cockpit.entity.ProdutoTemporarioEntity;
import br.com.projeto557.cockpit.repository.EstoqueLojaIntegradaRepositoty;
import br.com.projeto557.cockpit.repository.PrecoLojaIntegradaRepository;
import br.com.projeto557.cockpit.repository.ProdutoRepository;
import br.com.projeto557.cockpit.repository.ProdutoTemporarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ApiLojaIntegrada {

    @Autowired
    ProdutoTemporarioRepository produtoTemporarioRepository;

    @Autowired
    EstoqueLojaIntegradaRepositoty estoqueLojaIntegradaRepositoty;

    @Autowired
    PrecoLojaIntegradaRepository precoLojaIntegradaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    public void sincronizarEstoque() {
        consumirProdutos("0", "20");
        consumirEstoque("0", "20");
        consumirValores("0", "20");
        produtoRepository.executaProcedureSincronizarComLojaIntegrada();
    }

    public void consumirProdutos(String offset, String limit) {


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

        if (headerResponse.isPresent()) {
            Integer contadorOffset = new Integer(offset);
            if (contadorOffset <= headerResponse.get().getTotal_count()) {
                contadorOffset += new Integer(limit);
                consumirProdutos(contadorOffset.toString(), limit);
            }
        }

        //return response.getBody();
    }

    public void consumirEstoque(String offset, String limit) {

        RestTemplate movieTemplate = new RestTemplate();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.awsli.com.br")
                .path("v1/produto_estoque")
                .queryParam("format", "json")
                .queryParam("chave_api", "13bde734dd6b99b08293")
                .queryParam("chave_aplicacao", "b2761f38-783e-4c10-9fe7-413fe7952dba")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .build();

        ResponseEntity<EstoqueLojaIntegradaHeader> response = movieTemplate.getForEntity(uri.toUriString(), EstoqueLojaIntegradaHeader.class);
        Optional<ProdutoLojaIntegradaTotais> headerResponse = Optional.ofNullable(response.getBody().getMeta());
        Optional<List<EstoqueLojaIntegrada>> estoqueLojaIntegradaList = Optional.ofNullable(response.getBody().getObjects());
        saveTemporaryEstoque(estoqueLojaIntegradaList);

        if (headerResponse.isPresent()) {
            Integer contadorOffset = new Integer(offset);
            if (contadorOffset <= headerResponse.get().getTotal_count()) {
                contadorOffset += new Integer(limit);
                consumirEstoque(contadorOffset.toString(), limit);
            }
        }
    }

    public void consumirValores(String offset, String limit) {

        RestTemplate movieTemplate = new RestTemplate();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.awsli.com.br")
                .path("v1/produto_preco")
                .queryParam("format", "json")
                .queryParam("chave_api", "13bde734dd6b99b08293")
                .queryParam("chave_aplicacao", "b2761f38-783e-4c10-9fe7-413fe7952dba")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .build();

        ResponseEntity<PrecoLojaIntegradaHeader> response = movieTemplate.getForEntity(uri.toUriString(), PrecoLojaIntegradaHeader.class);
        Optional<ProdutoLojaIntegradaTotais> headerResponse = Optional.ofNullable(response.getBody().getMeta());
        Optional<List<PrecoLojaIntegrada>> precoLojaIntegradaList = Optional.ofNullable(response.getBody().getObjects());
        saveTemporaryPreco(precoLojaIntegradaList);

        if (headerResponse.isPresent()) {
            Integer contadorOffset = new Integer(offset);
            if (contadorOffset <= headerResponse.get().getTotal_count()) {
                contadorOffset += new Integer(limit);
                consumirValores(contadorOffset.toString(), limit);
            }
        }
    }

    public void saveTemporaryProduct(Optional<List<ProdutoLojaIntegrada>> produtoLojaIntegradaList) {
        if (produtoLojaIntegradaList.isPresent()) {
            for (ProdutoLojaIntegrada produto : produtoLojaIntegradaList.get()) {
                //salvando produto pai
                if (produto.getFilhos() != null) {
                    //produtoTemporarioRepository.save(montaProdutoTemporario(produto));
                    //salvando ou atualizando produtos filhos
                    saveProdutosFilhos(produto);
                } else {
                    if (!produtoTemporarioRepository.existsById(produto.getId())) {
                        try {
                            produtoTemporarioRepository.save(
                                    ProdutoTemporarioEntity.builder()
                                            .id_prod_loja(produto.getId())
                                            .tamanho(produto.getSku().substring(10).toUpperCase()).build()
                            );
                        } catch (StringIndexOutOfBoundsException ex) {
                            produtoTemporarioRepository.save(
                                    ProdutoTemporarioEntity.builder()
                                            .id_prod_loja(produto.getId())
                                            .tipo(Utils.getArrayString(produto.getNome(), "-")[0].toUpperCase())
                                            .modelo(Utils.getArrayString(produto.getNome(), "-")[1].toUpperCase()).build()
                            );
                            continue;
                        }
                    }
                }
            }
        }
    }

    public void saveTemporaryEstoque(Optional<List<EstoqueLojaIntegrada>> estoqueLojaIntegradaList) {
        if (estoqueLojaIntegradaList.isPresent()) {
            for (EstoqueLojaIntegrada estoque : estoqueLojaIntegradaList.get()) {
                String[] idProduto = Utils.getArrayString(estoque.getProduto(), "/");
                estoqueLojaIntegradaRepositoty.save(EstoqueLojaIntegradaEntity
                        .builder()
                        .id_estoque(estoque.getId())
                        .id_produto(new Integer(idProduto[4]))
                        .quantidade(estoque.getQuantidade()).build());
            }
        }
    }

    public void saveTemporaryPreco(Optional<List<PrecoLojaIntegrada>> precoLojaIntegradaList) {
        if (precoLojaIntegradaList.isPresent()) {
            for (PrecoLojaIntegrada preco : precoLojaIntegradaList.get()) {
                String[] idProduto = Utils.getArrayString(preco.getProduto(), "/");
                try{
                precoLojaIntegradaRepository.save(
                        PrecoLojaIntegradaEntity
                                .builder()
                                .id_preco(preco.getId())
                                .id_produto(new Integer(idProduto[4]))
                                .preco_venda(new BigDecimal(preco.getCheio()))
                                .preco_custo(new BigDecimal(preco.getCusto())).build());
                }catch (NullPointerException e){
                    precoLojaIntegradaRepository.save(
                            PrecoLojaIntegradaEntity
                                    .builder()
                                    .id_preco(preco.getId())
                                    .id_produto(new Integer(idProduto[4]))
                                    .preco_venda(BigDecimal.ZERO)
                                    .preco_custo(BigDecimal.ZERO).build());
                    continue;
                }
            }
        }
    }

    private void saveProdutosFilhos(ProdutoLojaIntegrada produto) {
        Optional<List<String>> produtosFilhos = Optional.ofNullable(produto.getFilhos());
        if (produtosFilhos.isPresent()) {
            for (String id : produtosFilhos.get()) {
                String[] idCompleto = Utils.getArrayString(id, "/");
                Optional<ProdutoTemporarioEntity> produtoTemporarioEntity = produtoTemporarioRepository.findById(new Integer(idCompleto[idCompleto.length - 1]));
                if (produtoTemporarioEntity.isPresent()) {
                    try {
                        produtoTemporarioEntity.get().setTipo(Utils.getArrayString(produto.getNome(), "-")[0].toUpperCase());
                        produtoTemporarioEntity.get().setModelo(Utils.getArrayString(produto.getNome(), "-")[1].toUpperCase() + " - " + Utils.getArrayString(produto.getNome(), "-")[2].toUpperCase());
                        produtoTemporarioRepository.save(
                                produtoTemporarioEntity.get()
                        );
                    } catch (ArrayIndexOutOfBoundsException ex) {
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

    private ProdutoTemporarioEntity montaProdutoTemporario(ProdutoLojaIntegrada produto) {
        return ProdutoTemporarioEntity.builder()
                .id_prod_loja(produto.getId())
                .tamanho(produto.getSku().substring(10))
                .tipo(Utils.getArrayString(produto.getNome(), "-")[0].toUpperCase())
                .modelo(Utils.getArrayString(produto.getNome(), "-")[1].toUpperCase()).build();
    }

}
