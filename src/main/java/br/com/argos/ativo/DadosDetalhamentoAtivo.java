package br.com.argos.ativo;

import br.com.argos.evento.DadosDetalhamentoEvento;

import java.util.List;

public record DadosDetalhamentoAtivo(
        Long id,
        String nome,
        String empresa,
        String localizacao,
        Double scoreRisco,
        List<DadosDetalhamentoEvento> eventos
) {
    public DadosDetalhamentoAtivo(AtivoCorporativo a) {
        this(
                a.getId(),
                a.getNome(),
                a.getEmpresa(),
                a.getLocalizacao(),
                a.getScoreRisco(),
                a.getEventos().stream().map(DadosDetalhamentoEvento::new).toList()
        );
    }
}
