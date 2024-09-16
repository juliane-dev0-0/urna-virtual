package urna.virtual.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @ManyToOne
    private Candidato prefeito;

    @NotNull
    @ManyToOne
    private Candidato vereador;

    @NotBlank
    private String hash;

    @Override
    public String toString() {
        return "Voto { \n" +
                "  id =" + id + "\n" +
                "  dataVoto =" + dataHora + "\n" +
                "  prefeito =" + (prefeito != null ? "\n    " + prefeito.toString() : "Nenhum prefeito") + "\n" +
                "  vereador =" + (vereador != null ? "\n    " + vereador.toString() : "Nenhum vereador") + "\n" +
                "}";
    }



}
