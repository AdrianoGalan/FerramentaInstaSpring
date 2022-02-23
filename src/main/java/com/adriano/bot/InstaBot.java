package com.adriano.bot;

import java.util.Random;

import com.adriano.model.Perfil;
import com.microsoft.playwright.Page;

public class InstaBot {

    public boolean fazerLogin(Page page, Perfil perfil) {
        try {

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
                return false;
            }

            if (page.title().contains("Diretrizes")) {

                page.click("text=Agora não");

                this.pausa(page, 2, 5);

                return true;
            }

            // Click text=Agora não
            page.click("text=Agora não");

            if (this.verificaBloqueio(page)) {
                return false;
            }

            this.pausa(page, 2, 5);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean assistirStores(Page page, int tempo) {

        tempo = tempo * 60000;
        int aux = 0;

        try {

            // assistir stores
            page.click("div[role=\"button\"]", new Page.ClickOptions()
                    .setPosition(2, 6));

            this.pausa(page, 2, 3);

            while (aux < tempo) {

                // avançar stores
                // Click div[role="button"] >> :nth-match(button, 4)
                page.click("div[role=\"button\"] >> :nth-match(button, 4)");
                aux = aux + 10000;
                pausa(page, 10, 11);

            }

            // Click header button
            page.click("header button");

            return true;

        } catch (Exception e) {
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

    private boolean pausa(Page page, int minimo, int maximo) {

        minimo = minimo * 1000;
        maximo = maximo * 1000;

        Random gerador = new Random();

        page.waitForTimeout(gerador.nextInt(maximo - minimo) + minimo);

        return true;
    }

}
