package com.adriano.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String senha;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 150, nullable = false)
    private String sobreNome;

    @Column(length = 150)
    private String dispositivo;

    @Column
    private String dataCriacao;

    @Column
    private String dataCadastro;

    @Column
    private String dataBloqueio;

    @Column
    private String dataInicioTrabalho;

    @Column
    private int numeroSeguidor;

    @Column
    private int numeroSeguindo;

    @Column
    private String genero;

    @OneToOne(targetEntity = Email.class)
	@JoinColumn(name = "id_email")
    private Email email;

    @OneToOne(targetEntity = Status.class)
	@JoinColumn(name = "id_status")
    private Status status;

    
    
}
