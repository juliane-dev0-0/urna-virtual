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
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank
    @Column(unique = true)
    private String numeroCanditado;

    @NotNull
    // 1 é prefeito, 2 é vereador !
    private int funcao;

    @Transient
    private Long votosApurados;

    @Override
    public String toString() {
        return "{ nome: " + this.getNome() +
                ", cpf: " + this.getCpf() +
                ", numero: " + numeroCanditado +
                ", funcao: " + funcao +
                ", status: " + getStatus() +
                ", votosApurados: " + votosApurados + " }";
    }

    public Candidato(String nome, String cpf, Status status, String numeroCanditado, int funcao, Long votosApurados) {
        super(nome, cpf, status);
        this.numeroCanditado = numeroCanditado;
        this.funcao = funcao;
        this.votosApurados = votosApurados;
    }

    public Candidato(Long id, String nome, String cpf, Status status, String numeroCanditado, int funcao, Long votosApurados) {
        super(id ,nome, cpf, status);
        this.numeroCanditado = numeroCanditado;
        this.funcao = funcao;
        this.votosApurados = votosApurados;
    }

}
