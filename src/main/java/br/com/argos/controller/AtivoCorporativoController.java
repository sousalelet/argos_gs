package br.com.argos.controller;

import br.com.argos.ativo.DadosAtualizarAtivo;
import br.com.argos.ativo.DadosCadastroAtivo;
import br.com.argos.ativo.DadosListagemAtivo;
import br.com.argos.service.AtivoCorporativoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/ativos")
@RequiredArgsConstructor
public class AtivoCorporativoController {

    private final AtivoCorporativoService service;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroAtivo dados, UriComponentsBuilder uriBuilder) {
        try {
            var detalhamento = service.cadastrar(dados);
            var uri = uriBuilder.path("/ativos/{id}").buildAndExpand(detalhamento.id()).toUri();
            return ResponseEntity.created(uri).body(detalhamento); // 201
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409
        }
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAtivo>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable)); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.detalhar(id)); // 200
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody DadosAtualizarAtivo dados) {
        try {
            return ResponseEntity.ok(service.atualizar(dados)); // 200
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
