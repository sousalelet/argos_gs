package br.com.argos.ativo;

import br.com.argos.evento.DadosCadastroEvento;

import java.util.List;

public record DadosCadastroAtivo(
        String nome,
        String empresa,
        String localizacao,
        Double scoreRisco,
        List<DadosCadastroEvento> eventos
) {}
