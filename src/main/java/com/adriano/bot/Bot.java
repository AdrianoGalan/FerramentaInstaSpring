package com.adriano.bot;

import java.beans.JavaBean;
import java.io.File;
import java.nio.file.Paths;

import com.adriano.model.Perfil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;



@Repository
public class Bot {

    private static Logger log = LoggerFactory.getLogger(Bot.class);

    public void verificarContas() {

        File file1 = new File("src/main/resources/context/torquatopaulomessia.json");

        if(file1.isFile()){

            System.out.println("existe");
        }else{
            System.out.println("não existe");
        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(500));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(Paths.get("src/main/resources/context/torquatopaulomessias.json"))
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 812)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(-23.558934, -46.627823)
                    .setTimezoneId("America/Sao_Paulo"));

            log.info("Inicia Robo");
            // Open new page
            Page page = context.newPage();

            page.navigate("https://www.google.com");

            page.pause();

        }

    }

    public void iniciaBot() {

        InstaBot ib = new InstaBot();

        Perfil perfil = new Perfil();
        perfil.setUsername("torquatopaulomessias");
        perfil.setSenha("jdh2WbvqC2W5Pqd");

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(500));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 812)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(-23.558934, -46.627823)
                    .setTimezoneId("America/Sao_Paulo"));

            log.info("Inicia Robo");
            // Open new page
            Page page = context.newPage();

            page.navigate("https://www.instagram.com");

            page.waitForTimeout(3000);
            // Click text=Entrar
            page.click("text=Entrar");

            page.waitForTimeout(5000);

            if (page.title().equalsIgnoreCase("Instagram")) {
                log.info("inicia login");
                if (ib.fazerLogin(page, perfil)) {

                    context.storageState(new BrowserContext.StorageStateOptions()
                            .setPath(Paths.get("src/main/resources/context/" + perfil.getUsername() + ".json")));

                    log.info("Assiste Stores");
                    ib.assistirStores(page, 1);

                    log.info("saindo");
                    if (ib.sair(page, perfil)) {

                        page.close();
                    }
                    page.close();

                }
            }

        }
    }

    public void salvaContext() {

        InstaBot ib = new InstaBot();

        Perfil perfil = new Perfil();
        perfil.setUsername("torquatopaulomessias");
        perfil.setSenha("jdh2WbvqC2W5Pqd");

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(200));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 812)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(-23.558934, -46.627823)
                    .setTimezoneId("America/Sao_Paulo"));

            log.info("Inicia Robo");
            // Open new page
            Page page = context.newPage();

            page.navigate("https://www.instagram.com");

            page.waitForTimeout(3000);
            // Click text=Entrar
            page.click("text=Entrar");

            page.waitForTimeout(5000);

            if (page.title().equalsIgnoreCase("Instagram")) {
                log.info("inicia login");
                if (ib.fazerLogin(page, perfil)) {

                    page.waitForTimeout(500);

                    context.storageState(new BrowserContext.StorageStateOptions()
                            .setPath(Paths.get("src/main/resources/context/" + perfil.getUsername() + ".json")));

                    page.waitForTimeout(500);

                    log.info("Assiste Stores");
                    ib.assistirStores(page, 1);
                    page.close();

                }
            }

        }
    }

    public void teste() {

        Perfil perfil = new Perfil();
        perfil.setUsername("torquatopaulomessias");
        perfil.setSenha("jdh2WbvqC2W5Pqd");

    }

}
