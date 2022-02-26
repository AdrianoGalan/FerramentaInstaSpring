package com.adriano.controller;

import java.io.IOException;
import java.util.List;

import com.adriano.bot.Bot;
import com.adriano.gerenciaplanilhas.GerenciadorPerfil;
import com.adriano.model.Categoria;
import com.adriano.model.Perfil;
import com.adriano.model.Status;
import com.adriano.repositotory.CategoriaRepository;
import com.adriano.repositotory.PerfilRepository;
import com.adriano.repositotory.StatusRepository;
import com.adriano.utilitario.Gerador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private CategoriaRepository rCateg;
    private Gerador gera;

    @GetMapping("/verificarcontas/{username}")
    public ResponseEntity<String> verificarContas(@PathVariable(value = "username") String username) {

        Status status = rStatus.getByStatus("Bloqueado");
        int idStatus = status.getId();
        GerenciadorPerfil gp = new GerenciadorPerfil(rPerfil);

        List<Perfil> perfis = rPerfil.findByStatusDIfBlo(idStatus);
        Perfil conta = new Perfil();
        conta.setId(-1);

        for (Perfil perfil : perfis) {

            if (perfil.getUsername().equalsIgnoreCase(username)) {

                conta = perfil;
                break;
            }

        }
        if (conta.getId() == -1) {
            conta = perfis.get(perfis.size() - 1);
        }

        perfis = bot.verificarContas(perfis, conta, status);

        for (Perfil perfil : perfis) {
            rPerfil.save(perfil);
        }

        // salva na planilha
        try {
            gp.salvarPerfilPlanilha();
        } catch (IOException e) {
            System.err.println("Erro ao salvar na planilha " + e.toString());
        }

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/postar/{categoria}/{username}")
    public ResponseEntity<String> postarmagemPerfil(@PathVariable(value = "username") String username,
            @PathVariable(value = "categoria") String categoria) {

        Perfil perfil = rPerfil.getByUsername(username);
        
        Categoria cat =rCateg.getByNome(categoria);

        bot.postarImagem(perfil, gera, cat);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/teste")
    public ResponseEntity<String> teste() {

        bot.teste();

        return ResponseEntity.ok("ok");

    }

}
