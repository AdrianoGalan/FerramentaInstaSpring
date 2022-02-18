package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Nomes;
import com.adriano.repositotory.NomeRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/nome")
@AllArgsConstructor
public class NomeController {

    private final NomeRepository rNome;

 

    @PostMapping
    public ResponseEntity<String> insertNome(@Valid @RequestBody Nomes nome){
		

        this.rNome.save(nome);

        return ResponseEntity.ok("ok");

	}

    
    @GetMapping
    public List<Nomes> list(){
        return this.rNome.findAll();
    }

      
}
