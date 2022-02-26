package com.adriano.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.adriano.model.Nomes;
import com.adriano.model.PerfilCriado;
import com.adriano.repositotory.BiografiaRepository;
import com.adriano.repositotory.NomeRepository;
import com.adriano.repositotory.SobreNomeRepository;
import com.adriano.utilitario.Gerador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil/criar")
@AllArgsConstructor
public class PerfilCriadoController {

    private final NomeRepository rNome;
    private final SobreNomeRepository rSobrenome;
    private final BiografiaRepository rBiografia;
    private Gerador gera;

    @GetMapping("/{genero}")
    public PerfilCriado criar(@PathVariable(value = "genero") String genero) {

        Random gerador = new Random();
        List<Nomes> nomes = rNome.findByGenero(genero);
        PerfilCriado perfil = new PerfilCriado();
        String[] username;

        perfil.setNome(nomes.get(gerador.nextInt(nomes.size())).getNome());
        perfil.setSobrenome(gera.gerarSobrenome());
        perfil.setDataAniver(gera.gerarData());
        perfil.setDataCriacao(dataAtual());
        perfil.setBio(gera.geraBio());
        perfil.setSenha("Senha" + perfil.getNome());

        username = perfil.getSobrenome().split(" ");
        perfil.setUsername(username[1] + perfil.getNome() + username[0]);

        return perfil;
    }



    private String dataAtual() {

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        return formatador.format(data);

    }

}
