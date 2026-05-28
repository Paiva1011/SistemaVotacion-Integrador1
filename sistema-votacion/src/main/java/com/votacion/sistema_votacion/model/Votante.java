package com.votacion.sistema_votacion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "votantes")
public class Votante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dni;

    private String nombre;

    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private boolean yaVoto = false;

    public Votante() {
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isYaVoto() {
        return yaVoto;
    }

    public void setYaVoto(boolean yaVoto) {
        this.yaVoto = yaVoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}