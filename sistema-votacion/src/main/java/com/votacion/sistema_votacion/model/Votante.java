package com.votacion.sistema_votacion.model;

import jakarta.persistence.*;

/**
 * Entidad que representa a un votante dentro del sistema.
 * Contiene la información personal y el estado de participación
 * en el proceso de votación.
 */
@Entity
@Table(name = "votantes")
public class Votante {

    // Identificador único del votante
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DNI del votante (único y obligatorio)
    @Column(unique = true, nullable = false)
    private String dni;

    // Nombre del votante
    private String nombre;

    // Apellido del votante
    private String apellido;

    // Correo electrónico del votante (único y obligatorio)
    @Column(unique = true, nullable = false)
    private String email;

    // Contraseña de acceso al sistema
    private String password;

    // Indica si el votante ya emitió su voto
    private boolean yaVoto = false;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Votante() {
    }

    /**
     * Obtiene el identificador del votante.
     * @return id del votante.
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el DNI del votante.
     * @return DNI registrado.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del votante.
     * @param dni DNI a registrar.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del votante.
     * @return nombre del votante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del votante.
     * @param nombre nombre a registrar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del votante.
     * @return apellido del votante.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del votante.
     * @param apellido apellido a registrar.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el correo electrónico del votante.
     * @return correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del votante.
     * @param email correo electrónico a registrar.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Indica si el votante ya emitió su voto.
     * @return true si ya votó, false en caso contrario.
     */
    public boolean isYaVoto() {
        return yaVoto;
    }

    /**
     * Actualiza el estado de votación del votante.
     * @param yaVoto nuevo estado de votación.
     */
    public void setYaVoto(boolean yaVoto) {
        this.yaVoto = yaVoto;
    }

    /**
     * Obtiene la contraseña del votante.
     * @return contraseña registrada.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del votante.
     * @param password contraseña a registrar.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}