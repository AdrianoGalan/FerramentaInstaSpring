package com.adriano.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.adriano.model.Perfil;
import com.adriano.model.Status;

import com.adriano.repositotory.PerfilRepository;
import com.adriano.repositotory.StatusRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class PerfilController {

    private final PerfilRepository rPerfil;
    private final StatusRepository rStatus;

    @PostMapping
    public ResponseEntity<String> insertPerfil(@Valid @RequestBody Perfil perfil) {

        this.rPerfil.save(perfil);

        return ResponseEntity.ok("ok");

    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarPerfil(@Valid @RequestBody Perfil perfil) {

        this.rPerfil.save(perfil);

        return ResponseEntity.ok("ok");

    }

    @PutMapping("/atualizar/status")
    public ResponseEntity<String> cadastrarPerfil(@Valid @RequestBody Perfil perfil) {

        Status status = rStatus.getByStatus(perfil.getStatus().getStatus());

        switch (status.getStatus()) {
            case "Cadastrado":
                perfil.setDataCadastro(this.dataAtual());
                break;
            case "Trabalhando novo":
                perfil.setDataInicioTrabalho(this.dataAtual());
                break;
            case "Bloqueado":
                perfil.setDataBloqueio(this.dataAtual());
                break;
            default:
                break;
        }

        perfil.setStatus(status);

        this.rPerfil.save(perfil);

        return ResponseEntity.ok("ok");

    }

    @GetMapping("/{username}")
    public Perfil getById(@PathVariable(value = "username") String username) {

        return this.rPerfil.getByUsername(username);
    }

    @GetMapping
    public List<Perfil> list() {
        return this.rPerfil.findByOrderByNumeroSeguidorDesc();
    }

    @GetMapping("/status/{idStatus}")
    public List<Perfil> listByStatus(@PathVariable(value = "idStatus") int idStatus) {

        return this.rPerfil.findByStatusId(idStatus);
    }

    @GetMapping("/status/bloqueado/{idStatus}")
    public List<Perfil> listDifBloqueado(@PathVariable(value = "idStatus") int idStatus) {

        return this.rPerfil.findByStatusDIfBlo(idStatus);
    }

    private String dataAtual() {

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        return formatador.format(data);

    }

}
