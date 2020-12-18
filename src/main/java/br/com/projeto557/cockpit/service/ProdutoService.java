package br.com.projeto557.cockpit.service;

import br.com.projeto557.cockpit.consumes.ApiLojaIntegrada;
import br.com.projeto557.cockpit.dto.TipoReturn;
import br.com.projeto557.cockpit.entity.ProdutoEntity;
import br.com.projeto557.cockpit.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ApiLojaIntegrada apiLojaIntegrada;

    public Iterable<ProdutoEntity> buscarTodosProdutosDoEstoque(){
        return produtoRepository.findAll();
    }

    public Iterable<Object> buscarTodosProdutosDoEstoqueSeparadosPorTipo(){
        return produtoRepository.findCountTypesNative();
    }
    public Iterable<Object> buscarProdutosDoEstoqueConsolidados(){
        return produtoRepository.findConsolidatedValues();
    }

    public ProdutoEntity salvarProdutoEstoque(ProdutoEntity product){
        return produtoRepository.save(product);
    }

    public void sincronizarLojaIntegrada(){
      apiLojaIntegrada.sincronizarEstoque();
    }
}
