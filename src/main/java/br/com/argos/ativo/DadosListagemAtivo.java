package br.com.argos.ativo;

public record DadosListagemAtivo(
        Long id,
        String nome,
        String empresa,
        String localizacao,
        Double scoreRisco
) {
    public DadosListagemAtivo(AtivoCorporativo a) {
        this(a.getId(), a.getNome(), a.getEmpresa(), a.getLocalizacao(), a.getScoreRisco());
    }
}
