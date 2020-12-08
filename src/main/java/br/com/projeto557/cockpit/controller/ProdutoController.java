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

    @GetMapping(path = "/listarEstoque/consolidado")
    public ResponseEntity<Iterable<Object>> buscarProdutosDoEstoqueConsolidado(){
        return new ResponseEntity<>(produtoService.buscarProdutosDoEstoqueConsolidados(), HttpStatus.OK);
    }

    @PostMapping(path = "/cadastrarProduto")
    public ResponseEntity<String> salvarProduto(@RequestBody ProdutoEntity produtoEntity) {
        produtoService.salvarProdutoEstoque(produtoEntity);
        return new ResponseEntity<>("Produto Cadastrado com sucesso", HttpStatus.OK);
    }
}
