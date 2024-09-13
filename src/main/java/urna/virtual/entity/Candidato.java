package urna.virtual.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidato extends Person{

    @NotNull
    @CPF
    private String cpf;

    @NotBlank
    @Column(unique = true)
    private String numeroCanditado;

    @NotNull
    // 1 é prefeito, 2 é vereador !
    private int funcao;

    @Transient
    private Integer votosApurados;

    @Override
    public String toString() {
        return "{ nome: " + this.getNome()+ ", " + "numero: " + numeroCanditado + " }";

        // return "Candidato{nome=" + this.getNome() + ", numero='" + numeroCanditado +  "'}";
    }
}
