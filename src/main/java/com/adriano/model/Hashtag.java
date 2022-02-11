package com.adriano.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

import lombok.Data;

@Data
@Entity
@NamedNativeQuery(name = "Hashtag.byCategoria", query = "SELECT id,hash,categoria FROM HASHTAG  WHERE categoria = ?1", 
				resultClass = Hashtag.class)
public class Hashtag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String hash;

    @Column(length = 50, nullable = false)
    private String categoria;
}
