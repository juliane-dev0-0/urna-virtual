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

    private int totalVotos;
    private List<HashMap<Candidato, Integer>> ListaVereadorVotos;
    private List<HashMap<Candidato, Integer>> ListaPrefeitoVotos;

}
