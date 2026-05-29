package br.com.argos.ativo;

public record DadosAtualizarAtivo(
        Long id,
        String nome,
        String empresa,
        String localizacao,
        Double scoreRisco
) {}
