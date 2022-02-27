package com.adriano.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
@NamedNativeQuery(name = "Perfil.findByStatusId", query = "SELECT id,username,senha,nome,sobre_nome,dispositivo,"+
                         "data_criacao,data_cadastro,data_bloqueio,data_inicio_trabalho,data_ultimo_trabalho,numero_seguidor," +
                         "numero_seguindo,numero_publicacao,genero,qualidade,id_email,id_status  FROM perfil  WHERE id_status = ?1 ORDER by numero_seguidor DESC", 
				resultClass = Perfil.class)
@NamedNativeQuery(name = "Perfil.findByStatusDIfBlo", query = "SELECT id,username,senha,nome,sobre_nome,dispositivo,"+
                         "data_criacao,data_cadastro,data_bloqueio,data_inicio_trabalho,data_ultimo_trabalho,numero_seguidor," +
                         "numero_seguindo,numero_publicacao,genero,qualidade,id_email,id_status  FROM perfil  WHERE id_status != ?1 ORDER by numero_seguidor DESC", 
				resultClass = Perfil.class)
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
    private String dataUltimoTrabalho;

    @Column
    private int numeroSeguidor;

    @Column
    private int numeroSeguindo;

    @Column
    private int numeroPublicacao;

    @Column
    private String genero;

    @Column
    private String qualidade;

    @OneToOne(targetEntity = Email.class)
    @JoinColumn(name = "id_email")
    private Email email;

    @OneToOne(targetEntity = Status.class)
    @JoinColumn(name = "id_status")
    private Status status;

}
