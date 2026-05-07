package br.com.sneacker.sneackerjava.dto;

import br.com.sneacker.sneackerjava.model.Sneaker;
import br.com.sneacker.sneackerjava.security.UsuarioRole;
import jakarta.validation.constraints.*;

import java.util.List;

public class UsuarioRequest {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve estar no formato correto!")
    private String email;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve conter pelo menos 3 caracteres!")
    private String nomeCompleto;

    @NotBlank(message = "O cpf deve ser obrigatório!")
    @Size(min = 11, max = 11, message = "O cpf deve conter 11 caracteres!")
    private String cpf;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, max = 8, message = "A senha deve conter entre 5 a 8 caracteres!")
    private String senha;

    private List<Sneaker> sneakers;

    public UsuarioRole role;

    public List<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(List<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
