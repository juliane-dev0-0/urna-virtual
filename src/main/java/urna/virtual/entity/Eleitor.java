package urna.virtual.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private String profissão;

    @NotBlank
    private String celular;

    private String telefoneFixo;

    @Email(message = "O e-mail deve ser válido.")
    private String email;




}
