package com.votacion.sistema_votacion.model;

import jakarta.persistence.*;

/**
 * Entidad que representa a un votante dentro del sistema.
 */
@Entity
@Table(name = "votantes")
public class Votante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_votante")
    private Long idVotante;

    // DNI único del votante
    @Column(nullable = false, unique = true)
    private String dni;

    // Datos personales
    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    private String celular;

    // Correo del votante
    @Column(unique = true)
    private String email;

    // Contraseña de acceso
    private String password;

    // Estado del voto
    private boolean yaVoto = false;


    // Constructor vacío requerido por JPA
    public Votante() {
    }


    // Constructor con datos principales
    public Votante(String dni, String nombres, String apellidos, String celular) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
    }


    // Getters y Setters

    public Long getIdVotante() {
        return idVotante;
    }

    public void setIdVotante(Long idVotante) {
        this.idVotante = idVotante;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }


    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isYaVoto() {
        return yaVoto;
    }

    public void setYaVoto(boolean yaVoto) {
        this.yaVoto = yaVoto;
    }
}