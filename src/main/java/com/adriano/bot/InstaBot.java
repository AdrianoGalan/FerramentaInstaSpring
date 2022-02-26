package com.adriano.bot;

import java.nio.file.Paths;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adriano.model.Perfil;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;

public class InstaBot {

    private static Logger log = LoggerFactory.getLogger(InstaBot.class);

    public boolean fazerLogin(Page page, Perfil perfil) {
        try {

            this.log.info("Inicia Login perfil: " + perfil.getUsername());

            page.click("[aria-label=\"Telefone, nome de usuário ou email\"]");

            this.pausa(page, 2, 5);

            page.fill("[aria-label=\"Telefone, nome de usuário ou email\"]", perfil.getUsername());

            this.pausa(page, 2, 5);

            page.click("[aria-label=\"Senha\"]");

            this.pausa(page, 2, 5);

            page.fill("[aria-label=\"Senha\"]", perfil.getSenha());

            this.pausa(page, 2, 5);

            page.waitForNavigation(() -> {
                page.click("button:has-text(\"Entrar\")");
            });

            this.pausa(page, 2, 5);

            if (this.verificaBloqueio(page)) {

                log.error("Perfil " + perfil.getUsername() + " Bloqueado");
                return false;
            }

            if (page.title().contains("Diretrizes")) {

                page.click("text=Agora não");

                this.pausa(page, 2, 5);

                log.info("Login efetuado");
                return true;
            }

            // Click text=Agora não
            page.click("text=Agora não");

            if (this.verificaBloqueio(page)) {
                return false;
            }

            this.pausa(page, 2, 5);

            log.info("Login efetuado");
            return true;

        } catch (Exception e) {

            log.error("Erro ao logar", e);
            return false;
        }
    }

    public boolean assistirStores(Page page, int tempo) {

        log.info("Assistir Stores por " + tempo + " minutos");

        tempo = tempo * 60000;
        int aux = 0;
        String titulo = "";

        try {

            // assistir stores
            page.click("div[role=\"button\"]", new Page.ClickOptions()
                    .setPosition(2, 6));

            this.pausa(page, 2, 4);

            while (aux < tempo) {

                titulo = page.title();

                if (titulo.contains("Sto")) {
                    // avançar stores
                    // Click div[role="button"] >> :nth-match(button, 4)
                    page.click("div[role=\"button\"] >> :nth-match(button, 4)");

                }
                aux = aux + 10000;
                pausa(page, 9, 11);

            }

            // Click header button
            page.click("header button");

            return true;

        } catch (Exception e) {

            log.error("Erro ao assistir stores ", e);
            return false;
        }

    }

    public boolean verificaBloqueio(Page page) {

        if (page.title().contains("confirmar que")) {
            return true;
        }

        if (page.title().contains("telefone")) {
            return true;
        }

        return false;

    }

    public boolean sair(Page page, Perfil perfil) {

        try {

            // assert page.url().equals("https://www.instagram.com/");
            // Click div:nth-child(5) .gKAyB .ewxsm
            page.click("div:nth-child(5) .gKAyB .ewxsm");

            this.pausa(page, 1, 3);

            if (!page.title().contains(perfil.getUsername())) {
                page.navigate("https://www.instagram.com/" + perfil.getUsername());
            }

            this.pausa(page, 1, 3);
            // assert page.url().equals("https://www.instagram.com/torquatopaulomessias/");
            // Click nav button
            page.click("nav button");

            this.pausa(page, 1, 3);
            // Click text=Sair
            page.click("text=Sair");

            this.pausa(page, 1, 3);
            // Click button:has-text("Sair")
            page.click("button:has-text(\"Sair\")");
            // assert page.url().equals("https://www.instagram.com/");

        } catch (Exception e) {
            return false;
        }
        this.pausa(page, 1, 3);
        return true;
    }

    public Perfil verificaPerfil(Page page, String usernanme) {

        Perfil perfil = new Perfil();

        try {
            page.navigate("http://www.instagram.com");

            this.pausa(page, 1, 3);

            page.navigate("https://www.instagram.com/" + usernanme);

            this.pausa(page, 1, 3);

            if (page.title().contains(usernanme)) {

                String[] seguidor = page.innerText("text=seguidor").split("\\n");
                perfil.setNumeroSeguidor(Integer.parseInt(seguidor[0]));

                String[] seguindo = page.innerText("text=seguindo").split("\\n");
                perfil.setNumeroSeguindo(Integer.parseInt(seguindo[0]));

                page.navigate("https://www.instagram.com");
                return perfil;

            } else {

                page.navigate("https://www.instagram.com");

                return null;
            }

        } catch (Exception e) {
            log.error("Erro ao verificar ", e);
            perfil.setId(-1);
            page.navigate("https://www.instagram.com");
            return perfil;
        }

    }

    public boolean postar(Page page, Perfil perfil, String hashtag, String caminhoImagem) {

        page.navigate("https://www.instagram.com/" + perfil.getUsername());

        pausa(page, 3, 5);

        try {

            
            FileChooser fileChooser = page.waitForFileChooser(() -> {
                page.click("[aria-label=\"Nova publicação\"]");
            });
            fileChooser.setFiles(Paths.get(caminhoImagem));

          
            pausa(page, 3, 5);

            // Click text=Avançar
            // page.waitForNavigation(new
            // Page.WaitForNavigationOptions().setUrl("https://www.instagram.com/create/details/"),
            // () ->
            page.waitForNavigation(() -> {
                page.click("text=Avançar");
            });

            pausa(page, 3, 5);

           
            // Click [aria-label="Escreva uma legenda..."]
            page.click("[aria-label=\"Escreva uma legenda...\"]");

            pausa(page, 3, 5);

           
            // Fill [aria-label="Escreva uma legenda..."]
            page.fill("[aria-label=\"Escreva uma legenda...\"]", hashtag);

            pausa(page, 3, 5);

            System.err.println(page.title());
            // Click text=Compartilhar
            // page.waitForNavigation(new
            // Page.WaitForNavigationOptions().setUrl("https://www.instagram.com/"), () ->
            page.waitForNavigation(() -> {
                page.click("text=Compartilhar");
            });


       
            pausa(page, 3, 5);

            page.navigate("https://www.instagram.com");

            pausa(page, 3, 5);

            return true;

        } catch (Exception e) {
            log.error("Erro ao verificar ", e);
            page.navigate("https://www.instagram.com");
            
            return false;
        }
    }

    public boolean pausa(Page page, int minimo, int maximo) {

        minimo = minimo * 1000;
        maximo = maximo * 1000;

        Random gerador = new Random();

        page.waitForTimeout(gerador.nextInt(maximo - minimo) + minimo);

        return true;
    }

}
