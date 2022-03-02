package com.adriano.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.springframework.http.HttpStatus;
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
        Categoria cat = rCateg.getByNome(categoria);

        if (bot.postarImagem(perfil, gera, cat)) {

            return ResponseEntity.ok("ok");
        }

        return ResponseEntity.ok("erro");

    }

    @GetMapping("/cadastrarGanhar/{username}")
    public ResponseEntity<String> cadastrarGanharnoInsta(@PathVariable(value = "username") String username) {

        Status status = rStatus.getByStatus("Cadastrado");
        Perfil perfil = rPerfil.getByUsername(username);
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        perfil = bot.verificaConta(perfil);

        if (perfil != null && perfil.getNumeroPublicacao() >= 4 && perfil.getNumeroSeguidor() >= 15) {

            System.err.println("começa cadastro");
            if (bot.cadastrarGanhaInsta(perfil)) {

                perfil.setStatus(status);
                perfil.setDataCadastro(formatador.format(data));

                this.rPerfil.save(perfil);

                return ResponseEntity.ok("ok");

            }
        }

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Erro ao Cadastrar");

    }


    @GetMapping("/realizaracoes/{username}")
    public ResponseEntity<String> realizarAcoes(@PathVariable(value = "username") String username) {

        Perfil perfil = rPerfil.getByUsername(username);

        
        int qtsAcoes = 1;

        // tempo em segundos
        int tempoEntreAcoes = 1;

        int qtsAcoesParaStores  = 1;

        //tempo em minutos
        int tempoStores = 1;

        bot.realizarTarefa(perfil, qtsAcoes, tempoEntreAcoes, qtsAcoesParaStores, tempoStores);

        return ResponseEntity.ok("ok");

    }

    @GetMapping("/teste/{username}")
    public ResponseEntity<String> teste(@PathVariable(value = "username") String username) {

        Perfil perfil = rPerfil.getByUsername(username);

        bot.realizarTarefa(perfil, 5, 60, 2, 1);

        return ResponseEntity.ok("ok");

    }

}
