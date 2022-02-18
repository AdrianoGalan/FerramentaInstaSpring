package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Biografia;

import com.adriano.repositotory.BiografiaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/biografia")
@AllArgsConstructor
public class BiografiaController {

    private final BiografiaRepository rBiografia;

    @PostMapping
    public ResponseEntity<String> insertBiografia(@Valid @RequestBody Biografia bio) {

        this.rBiografia.save(bio);

        return ResponseEntity.ok("ok");

    }

    @GetMapping
    public List<Biografia> list() {
        return this.rBiografia.findAll();
    }

}
