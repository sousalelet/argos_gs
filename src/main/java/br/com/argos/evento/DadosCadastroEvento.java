package br.com.argos.evento;

public record DadosCadastroEvento(
        String tipo,
        String descricao,
        String nivelAmeaca,
        String fonteDeteccao
) {}
