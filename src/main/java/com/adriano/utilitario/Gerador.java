package com.adriano.utilitario;

import java.util.List;
import java.util.Random;

import com.adriano.model.Biografia;
import com.adriano.model.Hashtag;
import com.adriano.model.Sobrenomes;
import com.adriano.repositotory.BiografiaRepository;
import com.adriano.repositotory.HashtagRepository;
import com.adriano.repositotory.SobreNomeRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class Gerador {

    private HashtagRepository rHas;
    private SobreNomeRepository rSobrenome;
    private BiografiaRepository rBiografia;
    

    public String geraHastag(){

        Random garaNum = new Random();

        List<Hashtag> has = rHas.findAll();
        String retorno = "";
    
        if (!has.isEmpty()) {
    
            int tamanho = has.size();
           
    
            retorno = has.get(garaNum.nextInt(tamanho)).getNome();
    
            for (int i = 0; i < 5; i++) {
    
                retorno = retorno + " " + has.get(garaNum.nextInt(tamanho)).getNome();
    
            }
        }
    
        return retorno;

    }
    
    public String geraHashtagCategoria(int id_categoria){
    
        Random garaNum = new Random();

        List<Hashtag> has = rHas.byCategoria(id_categoria);
        String retorno = "";
    
        if (!has.isEmpty()) {
    
            int tamanho = has.size();
           
    
            retorno = has.get(garaNum.nextInt(tamanho)).getNome();
    
            for (int i = 0; i < 5; i++) {
    
                retorno = retorno + " " + has.get(garaNum.nextInt(tamanho)).getNome();
    
            }
        }
    
        return retorno;
    }
    
    public String gerarSobrenome(){
    
        Random garaNum = new Random();
        
        List<Sobrenomes> sobrenomes = rSobrenome.findAll();
    
        String primeiroSobrenome = sobrenomes.get(garaNum.nextInt(sobrenomes.size())).getSobrenome();
        String segundoSobrenome = sobrenomes.get(garaNum.nextInt(sobrenomes.size())).getSobrenome();
    
        while (primeiroSobrenome.equals(segundoSobrenome)) {
    
            segundoSobrenome = sobrenomes.get(garaNum.nextInt(sobrenomes.size())).getSobrenome();
        }
    
        return primeiroSobrenome + " " + segundoSobrenome;
    
        
    }
    
    public String geraBio(){
    
        Random garaNum = new Random();

        List<Biografia> bios = rBiografia.findAll();
    
        if (bios.size() > 0) {
            String primeiraBio = bios.get(garaNum.nextInt(bios.size())).getBio();
            String segundaBio = bios.get(garaNum.nextInt(bios.size())).getBio();
    
            while (primeiraBio.equals(segundaBio)) {
                segundaBio = bios.get(garaNum.nextInt(bios.size())).getBio();
            }
            return primeiraBio + " " + segundaBio;
        }
    
       return "";
    }
    
    public String gerarData() {
    
        Random garaNum = new Random();
    
        int dia = garaNum.nextInt(27) + 1;
        int mes = garaNum.nextInt(11) + 1;
        int ano = garaNum.nextInt(20) + 1980;
    
        return ano + "-" + mes + "-" + dia;
    }

    public int geraInt(int inicio, int fim){

        Random garaNum = new Random();

        return garaNum.nextInt(fim - inicio)+ inicio;
    }



}
