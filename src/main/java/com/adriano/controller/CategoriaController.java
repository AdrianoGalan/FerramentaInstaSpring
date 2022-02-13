package com.adriano.controller;

import java.util.List;

import com.adriano.model.Categoria;
import com.adriano.repositotory.CategoriaRepository;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaRepository rCat;

    public Categoria insertCategoria(  String nomeCategoria){
		
        Categoria cat = new Categoria();
        cat.setNome(nomeCategoria);

        this.rCat.save(cat);

        return cat;

	}

    public Categoria getByNome( String nomeCategoria){
		

        Categoria cat = this.rCat.getByNome(nomeCategoria);

        if(cat == null){

            return this.insertCategoria(nomeCategoria);
        }
        
        return cat;

	}

    @GetMapping
    public List<Categoria> list(){
        return this.rCat.findAll();
    }
    
}
