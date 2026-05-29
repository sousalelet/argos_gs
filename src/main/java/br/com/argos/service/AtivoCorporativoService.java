package br.com.argos.service;

import br.com.argos.ativo.DadosAtualizarAtivo;
import br.com.argos.ativo.DadosCadastroAtivo;
import br.com.argos.ativo.DadosDetalhamentoAtivo;
import br.com.argos.ativo.DadosListagemAtivo;
import br.com.argos.ativo.AtivoCorporativo;
import br.com.argos.evento.EventoSuspeito;
import br.com.argos.repository.AtivoCorporativoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtivoCorporativoService {

    private final AtivoCorporativoRepository repository;

    @Transactional
    public DadosDetalhamentoAtivo cadastrar(DadosCadastroAtivo dados) {
        if (repository.existsByNomeAndAtivoTrue(dados.nome()))
            throw new IllegalStateException("Já existe um ativo com esse nome.");

        var ativo = new AtivoCorporativo();
        ativo.setNome(dados.nome());
        ativo.setEmpresa(dados.empresa());
        ativo.setLocalizacao(dados.localizacao());
        ativo.setScoreRisco(dados.scoreRisco());

        if (dados.eventos() != null) {
            for (var e : dados.eventos()) {
                var evento = new EventoSuspeito();
                evento.setTipo(e.tipo());
                evento.setDescricao(e.descricao());
                evento.setNivelAmeaca(e.nivelAmeaca());
                evento.setFonteDeteccao(e.fonteDeteccao());
                evento.setAtivo_corporativo(ativo);
                ativo.getEventos().add(evento);
            }
        }

        repository.save(ativo);
        return new DadosDetalhamentoAtivo(ativo);
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemAtivo> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemAtivo::new);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoAtivo detalhar(Long id) {
        var ativo = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
        return new DadosDetalhamentoAtivo(ativo);
    }

    @Transactional
    public DadosDetalhamentoAtivo atualizar(DadosAtualizarAtivo dados) {
        var ativo = repository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));

        if (dados.nome() != null && repository.existsByNomeAndAtivoTrueAndIdNot(dados.nome(), dados.id()))
            throw new IllegalStateException("Já existe outro ativo com esse nome.");

        if (dados.nome() != null)        ativo.setNome(dados.nome());
        if (dados.empresa() != null)     ativo.setEmpresa(dados.empresa());
        if (dados.localizacao() != null) ativo.setLocalizacao(dados.localizacao());
        if (dados.scoreRisco() != null)  ativo.setScoreRisco(dados.scoreRisco());

        return new DadosDetalhamentoAtivo(ativo);
    }

    @Transactional
    public void excluir(Long id) {
        var ativo = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
        ativo.setAtivo(false);
        ativo.getEventos().forEach(e -> e.setAtivo(false));
    }
}
