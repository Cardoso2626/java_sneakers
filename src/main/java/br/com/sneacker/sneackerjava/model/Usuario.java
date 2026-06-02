package br.com.sneacker.sneackerjava.model;

import br.com.sneacker.sneackerjava.security.UsuarioRole;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "nome_completo")
    private String nomeCompleto;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "senha")
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Sneaker> sneakers;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UsuarioRole role;



    public Usuario() {

    }

    public Usuario(String email, String nomeCompleto, String cpf, String senha, List<Sneaker> sneakers, UsuarioRole role) {
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.senha = senha;
        this.sneakers = sneakers;
        this.role = role;
    }

    public Usuario(Long id, String email, String nomeCompleto, String cpf, String senha, List<Sneaker> sneakers, UsuarioRole role) {
        this.id = id;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.senha = senha;
        this.sneakers = sneakers;
        this.role = role;
    }


    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    public List<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(List<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsuarioRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
