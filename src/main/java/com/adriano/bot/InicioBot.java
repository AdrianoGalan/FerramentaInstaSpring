package com.adriano.bot;

import com.adriano.model.Perfil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/bot")
@AllArgsConstructor
public class InicioBot {

    @GetMapping("/inicia")
    public ResponseEntity<String> iniciaBot() {

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

            // Open new page
            Page page = context.newPage();

            page.navigate("https://www.instagram.com");

            page.waitForTimeout(3000);
            // Click text=Entrar
            page.click("text=Entrar");

            page.waitForTimeout(5000);

            if (page.title().equalsIgnoreCase("Instagram")) {

                if (ib.fazerLogin(page, perfil)) {

                    ib.assistirStores(page, 1);

                    if(ib.sair(page, perfil)){
                        page.close();
                    }
                    page.close();

                }
            }

            return ResponseEntity.ok("ok");
        }
    }
}
