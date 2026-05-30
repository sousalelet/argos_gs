package br.com.argos.evento;

import br.com.argos.ativo.AtivoCorporativo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eventos_suspeitos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EventoSuspeito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descricao;
    private String nivelAmeaca;
    private String fonteDeteccao;
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private AtivoCorporativo ativoCorporativo;

    public EventoSuspeito(DadosCadastroEvento dados, AtivoCorporativo ativoCorporativo) {
        this.tipo = dados.tipo();
        this.descricao = dados.descricao();
        this.nivelAmeaca = dados.nivelAmeaca();
        this.fonteDeteccao = dados.fonteDeteccao();
        this.ativo = true;
        this.ativoCorporativo = ativoCorporativo;
    }

    public void excluirEvento() {
        this.ativo = false;
    }
}
