package br.com.argos.evento;

import br.com.argos.ativo.AtivoCorporativo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eventos_suspeitos")
@Getter
@Setter
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
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private AtivoCorporativo ativo_corporativo;
}
