package br.com.projeto557.cockpit.controller;

import br.com.projeto557.cockpit.dto.TipoReturn;
import br.com.projeto557.cockpit.entity.ProdutoEntity;
import br.com.projeto557.cockpit.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/estoque")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService ;

    @GetMapping(path = "/listarEstoque")
    public ResponseEntity<Iterable<ProdutoEntity>> buscarTodosProdutosDoEstoque(){
        return new ResponseEntity<>(produtoService.buscarTodosProdutosDoEstoque(), HttpStatus.OK);
    }

    @GetMapping(path = "/listarEstoque/tipo")
    public ResponseEntity<Iterable<Object>> buscarTodosProdutosDoEstoquePorTipo(){
        return new ResponseEntity<>(produtoService.buscarTodosProdutosDoEstoqueSeparadosPorTipo(), HttpStatus.OK);
    }

    @GetMapping(path = "/listarEstoque/donut")
    public ResponseEntity<Iterable<Object>> buscarTodosPrecosDoEstoquePorTipo(){
        return new ResponseEntity<>(produtoService.buscarTodosPrecosDoEstoqueSeparadosPorTipo(), HttpStatus.OK);
    }

    @GetMapping(path = "/listarEstoque/preco")
    public ResponseEntity<Iterable<Object>> buscarTodosPrecosDoEstoque(){
        return new ResponseEntity<>(produtoService.buscarTodosPrecosDoEstoque(), HttpStatus.OK);
    }

    @GetMapping(path = "/listarEstoque/totais")
    public ResponseEntity<Iterable<Object>> buscarProdutosDoEstoqueConsolidado(){
        return new ResponseEntity<>(produtoService.buscarTotaisProdutosDoEstoque(), HttpStatus.OK);
    }

    @PostMapping(path = "/cadastrarProduto")
    public ResponseEntity<String> salvarProduto(@RequestBody ProdutoEntity produtoEntity) {
        produtoService.salvarProdutoEstoque(produtoEntity);
        return new ResponseEntity<>("Produto Cadastrado com sucesso", HttpStatus.OK);
    }

    @GetMapping(path = "/sincronizar/lojaIntegrada")
    public ResponseEntity<String> sincronizarLojaIntegrada() {
        produtoService.sincronizarLojaIntegrada();
        return new ResponseEntity<>("SincronizadoComSucesso", HttpStatus.OK);
    }
}
