package br.com.argos.evento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroEvento(
        @NotBlank
        @Size(max = 60)
        String tipo,

        @Size(max = 255)
        String descricao,

        @NotBlank
        @Size(max = 60)
        String nivelAmeaca,

        @NotBlank
        @Size(max = 100)
        String fonteDeteccao
) {}
