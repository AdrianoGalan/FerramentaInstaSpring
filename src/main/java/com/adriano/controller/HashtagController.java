package com.adriano.controller;

import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import com.adriano.model.Categoria;
import com.adriano.model.Hashtag;
import com.adriano.repositotory.HashtagRepository;
import com.adriano.utilitario.Gerador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/hashtag")
@AllArgsConstructor
public class HashtagController {

    private final HashtagRepository rHas;
    private final CategoriaController catControl;

    private final Gerador gerador;

    @GetMapping
    public List<Hashtag> list() {
        return rHas.findAll();
    }

    @DeleteMapping("/deletar/{idCategoria}")
    public ResponseEntity<String> deletaHashtag(@PathVariable(value = "idCategoria") int id_categoria) {

        this.rHas.deleteById(id_categoria);

        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<String> insertHashtage(@Valid @RequestBody Hashtag h) {

        String hash = h.getNome();
        Categoria categoria = catControl.getByNome(h.getCategoria().getNome());

        String[] hashSeparado = hash.split(" ");
        for (String hashtag : hashSeparado) {
            try {
                Hashtag has = new Hashtag();
                has.setNome(hashtag.trim());
                has.setCategoria(categoria);
                rHas.save(has);
            } catch (Exception e) {
                System.err.println("Erro ao salvar " + e.toString());
            }

        }
        return ResponseEntity.ok("Hashtag salva");

    }

    @GetMapping("/gerar/{categoria}")
    public ResponseEntity<String> gerar(@PathVariable(value = "categoria") int id_categoria) {

        String hashtag = gerador.geraHashtagCategoria(id_categoria);

        return ResponseEntity.ok(hashtag);

    }

}
