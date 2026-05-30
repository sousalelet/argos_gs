package br.com.argos.ativo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record DadosAtualizarAtivo(
        @NotNull
        Long id,

        @Size(min = 3, max = 100)
        String nome,

        @Size(max = 100)
        String empresa,

        @Size(max = 200)
        String localizacao,

        @PositiveOrZero
        Double scoreRisco
) {}
