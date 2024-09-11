package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidato extends Person{

    @NotBlank
    @Column(unique = true)
    private String numeroCanditado;

    @NotNull
    private int funcao;

    private Status status;

    @Transient
    private Integer votosApurados;





}
