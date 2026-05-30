package br.com.argos.ativo;

import br.com.argos.evento.DadosCadastroEvento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DadosCadastroAtivo(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,

        @NotBlank
        @Size(max = 100)
        String empresa,

        @NotBlank
        @Size(max = 200)
        String localizacao,

        @NotNull
        @PositiveOrZero
        Double scoreRisco,

        @Valid
        List<DadosCadastroEvento> eventos
) {}
