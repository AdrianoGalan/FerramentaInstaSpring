package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Email;
import com.adriano.model.Perfil;
import com.adriano.repositotory.EmailRepository;
import com.adriano.repositotory.PerfilRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class PerfilController {

    private final PerfilRepository rPerfil;

    @PostMapping
    public ResponseEntity<String> insertPerfil(@Valid @RequestBody Perfil perfil) {

    
        this.rPerfil.save(perfil);

        return ResponseEntity.ok("ok");

    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarPerfil(@Valid @RequestBody Perfil perfil) {

        this.rPerfil.save(perfil);

        return ResponseEntity.ok("ok");

    }

    @GetMapping("/{username}")
    public Perfil getById(@PathVariable(value = "username") String username) {

        return this.rPerfil.getByUsername(username);
    }

    @GetMapping
    public List<Perfil> list() {
        return this.rPerfil.findAll();
    }

}
