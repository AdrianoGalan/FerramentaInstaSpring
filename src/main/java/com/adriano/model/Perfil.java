package com.adriano.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 200, nullable = false)
    private String email;

    @Column
    private Date dataCriacao;

    @Column
    private Date dataInicioTrabalho;

    @Column
    private String status;

    @Column
    private int numeroSeguidor;

    @Column
    private int numeroSeguindo;

    @Column
    private Date dataBloqueio;

    @Column
    private String genero;
    
}
