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

    // KEY = CANDIDATO, VALUE = VOTOS
    private HashMap<Candidato, Long> ListaVereadorVotos;
    private HashMap<Candidato, Long> ListaPrefeitoVotos;

}
