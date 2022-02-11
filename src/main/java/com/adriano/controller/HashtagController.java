package com.adriano.controller;

import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import com.adriano.model.Hashtag;
import com.adriano.repositotory.hashtagRepository;

import org.springframework.http.ResponseEntity;
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

    private final hashtagRepository rHas;

    @GetMapping
    public List<Hashtag> list(){
        return rHas.findAll();
    }

    @PostMapping
	public ResponseEntity<String> insertsetor(@Valid @RequestBody Hashtag h){
		
        String hash = h.getHash();
        String categoria = h.getCategoria();

        String[] hashSeparado = hash.split(" ");
        for (String hashtag : hashSeparado) {

            Hashtag has = new Hashtag();
            has.setHash(hashtag.trim());
            has.setCategoria(categoria);
            rHas.save(has);           
        }
		return ResponseEntity.ok("Hashtag salva");

	}


    @GetMapping("/gerar/{categoria}")
    public ResponseEntity<String> gerar(@PathVariable(value = "categoria") String categoria){

        
        List<Hashtag> has = rHas.byCategoria(categoria);
        String retorno = "";

        if(!has.isEmpty()){
        
        int tamanho = has.size();
        Random gerador = new Random();
       
       retorno = has.get(gerador.nextInt(tamanho)).getHash();

       for(int i = 0; i <  5; i++){

           retorno = retorno + " " + has.get(gerador.nextInt(tamanho)).getHash();

       }
    }
        System.out.println(retorno);
        return ResponseEntity.ok(retorno);
    }


    
    
}
