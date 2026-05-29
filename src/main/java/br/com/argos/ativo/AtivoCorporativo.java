package br.com.argos.ativo;

import br.com.argos.evento.EventoSuspeito;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ativos_corporativos")
@Getter
@Setter
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
    private Boolean ativo = true;

    // um ativo possui vários eventos suspeitos
    @OneToMany(mappedBy = "ativo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoSuspeito> eventos = new ArrayList<>();
}
