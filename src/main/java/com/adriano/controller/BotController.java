package com.adriano.controller;

import java.util.List;

import com.adriano.bot.Bot;
import com.adriano.model.Perfil;
import com.adriano.model.Status;
import com.adriano.repositotory.PerfilRepository;
import com.adriano.repositotory.StatusRepository;

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
    private PerfilRepository rPerfil;
    private StatusRepository rStatus;

    @GetMapping("/verificarcontas")
    public ResponseEntity<String> verificarContas() {

        Status status = rStatus.getByStatus("Bloqueado");
        int idStatus = status.getId();

        List<Perfil> perfis = rPerfil.findByStatusDIfBlo(idStatus);
        Perfil conta = new Perfil();
        conta.setId(-1);

        for (Perfil perfil : perfis) {

            if (perfil.getUsername().equalsIgnoreCase("torquatopaulomessias")) {

                conta = perfil;
                break;
            }

        }
        if (conta.getId() == -1) {
            conta = perfis.get(perfis.size() - 2);
        }

        perfis = bot.verificarContas(perfis, conta, status);

        for (Perfil perfil : perfis) {
            rPerfil.save(perfil);
        }

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/teste")
    public ResponseEntity<String> teste() {

        
        bot.teste();

        return ResponseEntity.ok("ok");
    }

}
