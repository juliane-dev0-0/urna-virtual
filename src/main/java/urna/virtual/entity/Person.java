package urna.virtual.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
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

@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @CPF
    private String cpf;

    @NotNull
    //@JsonIgnore
    private Status status;

    public Person(String nome, String cpf, Status status) {
        this.nome = nome;
        this.cpf = cpf;
        this.status = status;
    }

    public void setInativo(){ this.status = Status.INATIVO; }
    public void setApto(){
        this.status = Status.APTO;
    }
    public void setBloqueado(){
        this.status = Status.BLOQUEADO;
    }
    public void setPendente(){
        this.status = Status.PENDENTE;
    }
    public void setVotou(){
        this.status = Status.VOTOU;
    }


    public boolean isInativo(){
        return this.status == Status.INATIVO;
    }

    public boolean isPendente(){
        return this.status == Status.PENDENTE;
    }

    public boolean isBloqueado(){
        return this.status == Status.BLOQUEADO;
    }

    public boolean isVotou(){
        return this.status == Status.VOTOU;
    }

    public boolean isApto(){
        return this.status == Status.APTO;
    }
    public boolean isAtivo(){
        return this.status == Status.ATIVO;
    }


}
