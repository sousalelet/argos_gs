package br.com.argos.ativo;

import br.com.argos.evento.EventoSuspeito;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ativos_corporativos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AtivoCorporativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String empresa;
    private String localizacao;
    private Double scoreRisco;
    private Boolean ativo;

    @OneToMany(mappedBy = "ativoCorporativo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoSuspeito> eventos = new ArrayList<>();

    public AtivoCorporativo(DadosCadastroAtivo dados) {
        this.nome = dados.nome();
        this.empresa = dados.empresa();
        this.localizacao = dados.localizacao();
        this.scoreRisco = dados.scoreRisco();
        this.ativo = true;

        if (dados.eventos() != null) {
            for (var e : dados.eventos()) {
                this.eventos.add(new EventoSuspeito(e, this));
            }
        }
    }

    public void atualizarAtivo(@Valid DadosAtualizarAtivo dados) {
        if (dados.nome() != null && !dados.nome().isBlank())
            this.nome = dados.nome();
        if (dados.empresa() != null && !dados.empresa().isBlank())
            this.empresa = dados.empresa();
        if (dados.localizacao() != null && !dados.localizacao().isBlank())
            this.localizacao = dados.localizacao();
        if (dados.scoreRisco() != null)
            this.scoreRisco = dados.scoreRisco();
    }

    public void excluirAtivo() {
        this.ativo = false;
        this.eventos.forEach(EventoSuspeito::excluirEvento);
    }
}
