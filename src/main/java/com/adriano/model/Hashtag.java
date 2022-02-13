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
@NamedNativeQuery(name = "Hashtag.byCategoria", query = "SELECT id,nome,id_categoria FROM HASHTAG  WHERE id_categoria = ?1", 
				resultClass = Hashtag.class)
public class Hashtag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String nome;

    @OneToOne(targetEntity = Categoria.class)
	@JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
