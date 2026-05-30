package br.com.argos.controller;

import br.com.argos.ativo.*;
import br.com.argos.repository.AtivoCorporativoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/ativos")
public class AtivoCorporativoController {

    @Autowired
    private AtivoCorporativoRepository ativoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroAtivo dados, UriComponentsBuilder uriBuilder) {
        // verifica duplicidade de nome
        if (ativoRepository.existsByNomeAndAtivoTrue(dados.nome()))
            return ResponseEntity.status(409).body("Já existe um ativo com esse nome."); // 409

        var ativo = new AtivoCorporativo(dados); // cria o ativo (e seus eventos) via construtor
        ativoRepository.save(ativo);

        var uri = uriBuilder.path("/ativos/{id}").buildAndExpand(ativo.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAtivo(ativo)); // 201
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAtivo>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        var page = ativoRepository.findAllByAtivoTrue(pageable).map(DadosListagemAtivo::new);
        return ResponseEntity.ok(page); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var ativo = ativoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ativo não encontrado"
                ));
        return ResponseEntity.ok(new DadosDetalhamentoAtivo(ativo)); // 200
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarAtivo dados) {
        var ativo = ativoRepository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ativo não encontrado"
                ));

        // verifica duplicidade de nome em outro registro
        if (dados.nome() != null && ativoRepository.existsByNomeAndAtivoTrueAndIdNot(dados.nome(), dados.id()))
            return ResponseEntity.status(409).body("Já existe outro ativo com esse nome."); // 409

        ativo.atualizarAtivo(dados); // lógica dentro da própria entidade
        return ResponseEntity.ok(new DadosDetalhamentoAtivo(ativo)); // 200
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        var ativo = ativoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ativo não encontrado"
                ));
        ativo.excluirAtivo(); // exclusão lógica via método da entidade
        return ResponseEntity.noContent().build(); // 204
    }
}
