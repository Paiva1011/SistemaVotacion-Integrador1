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
}
