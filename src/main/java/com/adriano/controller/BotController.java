package com.adriano.controller;

import com.adriano.bot.Bot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/bot")
@AllArgsConstructor
public class BotController {

   private Bot bot;

    @GetMapping("/varificarcontas")
    public ResponseEntity<String> verificarContas() {

     
        bot.verificarContas();
        
        return ResponseEntity.ok("ok");
    }

}
