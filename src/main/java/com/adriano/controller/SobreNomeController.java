package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Sobrenomes;
import com.adriano.repositotory.SobreNomeRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/sobrenome")
@AllArgsConstructor
public class SobreNomeController {

    private final SobreNomeRepository rSobrenome;

    @PostMapping
    public ResponseEntity<String> insertSobrenome(@Valid @RequestBody Sobrenomes sobrenome) {

        this.rSobrenome.save(sobrenome);

        return ResponseEntity.ok("ok");

    }

    @GetMapping
    public List<Sobrenomes> list() {
        return this.rSobrenome.findAll();
    }

}
