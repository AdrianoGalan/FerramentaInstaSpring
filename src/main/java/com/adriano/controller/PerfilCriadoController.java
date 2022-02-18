package com.adriano.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.adriano.model.Biografia;
import com.adriano.model.Nomes;
import com.adriano.model.PerfilCriado;
import com.adriano.model.Sobrenomes;
import com.adriano.repositotory.BiografiaRepository;
import com.adriano.repositotory.NomeRepository;
import com.adriano.repositotory.SobreNomeRepository;

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

   


  
    @GetMapping("/{genero}")
    public PerfilCriado criar(@PathVariable(value = "genero") String genero) {

        Random gerador = new Random();

        List<Nomes> nomes = rNome.findByGenero(genero);

        PerfilCriado perfil = new PerfilCriado();

        perfil.setNome(nomes.get(gerador.nextInt(nomes.size())).getNome());
        perfil.setSobrenome(gerarSobrenome());
        perfil.setDataAniver(gerarData());
        perfil.setDataCriacao(dataAtual());
        perfil.setBio(gerarBio());
       

        return perfil;
    }

    private String gerarBio() {

        Random gerador = new Random();
        List<Biografia> bios = rBiografia.findAll();

        if (bios.size() > 0) {
            String primeiraBio = bios.get(gerador.nextInt(bios.size())).getBio();
            String segundaBio = bios.get(gerador.nextInt(bios.size())).getBio();

            while (primeiraBio.equals(segundaBio)) {
                segundaBio = bios.get(gerador.nextInt(bios.size())).getBio();
            }
            return primeiraBio + " " + segundaBio;
        }

        return bios.get(0).getBio();

    }

    private String gerarSobrenome() {

        Random gerador = new Random();
        List<Sobrenomes> sobrenomes = rSobrenome.findAll();

        String primeiroSobrenome = sobrenomes.get(gerador.nextInt(sobrenomes.size())).getSobrenome();
        String segundoSobrenome = sobrenomes.get(gerador.nextInt(sobrenomes.size())).getSobrenome();

        while (primeiroSobrenome.equals(segundoSobrenome)) {

            segundoSobrenome = sobrenomes.get(gerador.nextInt(sobrenomes.size())).getSobrenome();
        }

        return primeiroSobrenome + " " + segundoSobrenome;
    }

    private String gerarData() {

        Random gerador = new Random();

        int dia = gerador.nextInt(28);
        int mes = gerador.nextInt(11) + 1;
        int ano = gerador.nextInt(20) + 1980;

        return dia + "/" + mes + "/" + ano;
    }

    private String dataAtual() {

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        return formatador.format(data);

    }

}
