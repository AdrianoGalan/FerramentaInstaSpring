package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Email;
import com.adriano.repositotory.EmailRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {

    private final EmailRepository rEmail;

 

    @PostMapping
    public ResponseEntity<String> insertEmail(@Valid @RequestBody Email email){
		

        this.rEmail.save(email);

        return ResponseEntity.ok("ok");

	}

  

    @GetMapping
    public List<Email> list(){
        return this.rEmail.findByOrderByEmail();
    }
    
}
