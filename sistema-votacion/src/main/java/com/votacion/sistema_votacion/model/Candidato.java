package com.votacion.sistema_votacion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "candidatos")

public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String partido;

    @Column(columnDefinition = "TEXT")
    private String propuesta;

    @Column(nullable = false)
    private int totalVotos = 0;

    //Constructores
    public Candidato(){}

    public Candidato(String nombre, String apellido, String partido, String propuesta){
        this.nombre = nombre;
        this.apellido = apellido;
        this.partido = partido;
        this.propuesta = propuesta;
        this.totalVotos = 0;
    }

    //Getters & Setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public String getPartido(){
        return partido;
    }
    public void setPartido(String partido){
        this.partido = partido;
    }
    public String getPropuesta(){
        return propuesta;
    }
    public void setPropuesta(String propuesta){
        this.propuesta = propuesta;
    }
    public int getTotalVotos(){
        return totalVotos;
    }
    public void setTotalVotos(int totalVotos){
        this.totalVotos = totalVotos;
    }
}
