package com.adriano.bot;

import java.beans.JavaBean;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import com.adriano.model.Perfil;
import com.adriano.model.Status;
import com.adriano.repositotory.PerfilRepository;
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

    InstaBot ib = new InstaBot();

    public List<Perfil> verificarContas(List<Perfil> perfis, Perfil conta, Status statusBloqueado) {

        if (conta != null) {
            perfis.remove(conta);

            File file = new File("src/main/resources/context/" + conta.getUsername() + ".json");

            if (!file.isFile()) {
                try (Playwright playwright = Playwright.create()) {
                    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                            .setHeadless(false).setSlowMo(500));

                    BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                            .setUserAgent(
                                    "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                            .setIsMobile(true)
                            .setViewportSize(375, 650)
                            .setHasTouch(true)
                            .setLocale("pt-BR")
                            .setGeolocation(-23.558934, -46.627823)
                            .setTimezoneId("America/Sao_Paulo"));

                    log.info("Inicia Robo");

                    Page page = context.newPage();

                    if (ib.fazerLogin(page, conta)) {
                        context = browser.newContext(
                                new Browser.NewContextOptions().setStorageStatePath(
                                        Paths.get("src/main/resources/context/" + conta.getUsername() + ".json")));
                    }

                    page.close();

                }

            }

            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false).setSlowMo(500));

                BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                        .setStorageStatePath(
                                Paths.get("src/main/resources/context/" + conta.getUsername() + ".json"))
                        .setUserAgent(
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                        .setIsMobile(true)
                        .setViewportSize(375, 650)
                        .setHasTouch(true)
                        .setLocale("pt-BR")
                        .setGeolocation(-23.558934, -46.627823)
                        .setTimezoneId("America/Sao_Paulo"));

                log.info("Inicia Robo");
                // Open new page
                Page page = context.newPage();

                page.navigate("http://www.instagram.com");

                for (int i = perfis.size() - 3; i < perfis.size(); i++) {
                    Perfil pr = new Perfil();
                    pr = ib.verificaPerfil(page, perfis.get(i).getUsername(), log);
                    ib.assistirStores(page, 1);

                    if(pr == null){

                        perfis.get(i).setStatus(statusBloqueado);
                        
                    }else if(pr.getId() != -1){
                        perfis.get(i).setNumeroSeguidor(pr.getNumeroSeguidor());
                        perfis.get(i).setNumeroSeguindo(pr.getNumeroSeguindo());
                    }
                }

            }

        }

        return perfis;
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

                    // salvar context
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
        perfil.setUsername("fariasneis");
        InstaBot ib = new InstaBot();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(500));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(
                            Paths.get("src/main/resources/context/torquatopaulomessias.json"))
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 650)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(-23.558934, -46.627823)
                    .setTimezoneId("America/Sao_Paulo"));

            log.info("Inicia Robo");
            // Open new page
            Page page = context.newPage();

            Perfil rp = ib.verificaPerfil(page, "vianatatianebrito", log);

            if (rp == null) {
                System.err.println("perfil bloqueado");
            } else if (rp.getId() == -1) {
                System.err.println("Erro na execução");
            } else {
                perfil.setNumeroSeguidor(rp.getNumeroSeguidor());
                perfil.setNumeroSeguindo(rp.getNumeroSeguindo());

                System.err.println(perfil);
            }

            // page.pause();
        }

    }

}
