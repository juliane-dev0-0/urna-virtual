package urna.virtual.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apuracao {

    // Esta classe deverá conter o total de votos (inteiro),
    // uma lista de objetos dos candidatos a prefeito
    // e uma lista de objetos dos candidatos a vereador.

    private List<Candidato> vereadores;
    private List<Candidato> prefeitos;
    private Long TotalVotos;

}
