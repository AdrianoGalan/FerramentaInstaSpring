package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Categoria;
import com.adriano.repositotory.CategoriaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaRepository rCat;

 

    @PostMapping
    public ResponseEntity<String> insertCategoria(@Valid @RequestBody Categoria cat){
		

        this.rCat.save(cat);

        return ResponseEntity.ok("ok");

	}

    public Categoria getByNome( String nomeCategoria){
		

        Categoria cat = this.rCat.getByNome(nomeCategoria);

        
        return cat;

	}

    @GetMapping
    public List<Categoria> list(){
        return this.rCat.findAll();
    }
    
}
