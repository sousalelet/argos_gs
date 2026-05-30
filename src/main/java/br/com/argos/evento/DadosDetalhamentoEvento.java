package br.com.argos.evento;

public record DadosDetalhamentoEvento(
        Long id,
        String tipo,
        String descricao,
        String nivelAmeaca,
        String fonteDeteccao
) {
    public DadosDetalhamentoEvento(EventoSuspeito e) {
        this(e.getId(), e.getTipo(), e.getDescricao(), e.getNivelAmeaca(), e.getFonteDeteccao());
    }
}
