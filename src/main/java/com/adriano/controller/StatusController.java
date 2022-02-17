package com.adriano.controller;

import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Status;
import com.adriano.repositotory.StatusRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/email/status")
@AllArgsConstructor
public class StatusController {

    private final StatusRepository rStatus;

 

    @PostMapping
    public ResponseEntity<String> insertStatus(@Valid @RequestBody Status status){
		

        this.rStatus.save(status);

        return ResponseEntity.ok("ok");

	}

    @GetMapping("/{status}")
    public Status getByStatus(@PathVariable(value = "status") String status){

        return this.rStatus.getByStatus(status);
    }

  
    @GetMapping
    public List<Status> list(){
        return this.rStatus.findAll();
    }
    
}
