package com.adriano.controller;

import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import com.adriano.model.Categoria;
import com.adriano.model.Hashtag;
import com.adriano.repositotory.HashtagRepository;

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

    @GetMapping
    public List<Hashtag> list(){
        return rHas.findAll();
    }
    
    @DeleteMapping("/deletar/{idCategoria}")
    public ResponseEntity<String> deletaHashtag(@PathVariable(value = "idCategoria") Long id_categoria){


        this.rHas.deleteById(id_categoria);

        return ResponseEntity.ok("ok");
    }

    @PostMapping
	public ResponseEntity<String> insertHashtage(@Valid @RequestBody Hashtag h){
		
        String hash = h.getNome();
         Categoria categoria =  catControl.getByNome(h.getCategoria().getNome());

        String[] hashSeparado = hash.split(" ");
        for (String hashtag : hashSeparado) {

            Hashtag has = new Hashtag();
            has.setNome(hashtag.trim());
            has.setCategoria(categoria);
            rHas.save(has);           
        }
		return ResponseEntity.ok("Hashtag salva");

	}


    @GetMapping("/gerar/{categoria}")
    public ResponseEntity<String> gerar(@PathVariable(value = "categoria") Long id_categoria){

        
        List<Hashtag> has = rHas.byCategoria(id_categoria);
        String retorno = "";

        if(!has.isEmpty()){
        
        int tamanho = has.size();
        Random gerador = new Random();
       
       retorno = has.get(gerador.nextInt(tamanho)).getNome();

       for(int i = 0; i <  5; i++){

           retorno = retorno + " " + has.get(gerador.nextInt(tamanho)).getNome();

       }
    }
        return ResponseEntity.ok(retorno);
    }


    
    
}
