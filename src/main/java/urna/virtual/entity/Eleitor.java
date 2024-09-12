package urna.virtual.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Eleitor extends Person {

    @NotBlank
    private String profissao;

    @NotBlank
    @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[0-9])[0-9]{3}\\-[0-9]{4}$", message = "Número de telefone inválido")
    private String celular;

    private String telefoneFixo;

    @Email(message = "O e-mail deve ser válido.")
    private String email;

    public Eleitor(String nome, String cpf, Status status, String profissao, String celular, String telefoneFixo, String email) {
        super(nome, cpf, status);
        this.profissao = profissao;
        this.celular = celular;
        this.telefoneFixo = telefoneFixo;
        this.email = email;
    }
}
