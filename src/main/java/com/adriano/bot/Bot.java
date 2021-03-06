package com.adriano.bot;

import java.beans.JavaBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.adriano.gerenciaplanilhas.GerenciadorPerfil;
import com.adriano.model.Categoria;
import com.adriano.model.Perfil;
import com.adriano.model.Status;
import com.adriano.repositotory.PerfilRepository;
import com.adriano.utilitario.Constantes;
import com.adriano.utilitario.Gerador;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.MouseButton;

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

            File file = new File("context/" + conta.getUsername() + ".json");

            if (!file.isFile()) {

                this.salvaContext(conta);

            }

            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false).setSlowMo(500));

                BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                        .setStorageStatePath(
                                Paths.get("context/" + conta.getUsername() + ".json"))
                        .setUserAgent(
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                        .setIsMobile(true)
                        .setViewportSize(375, 650)
                        .setHasTouch(true)
                        .setLocale("pt-BR")
                        .setGeolocation(Constantes.s, Constantes.w)
                        .setTimezoneId("America/Sao_Paulo"));

                log.info("Inicia Robo");
                // Open new page
                Page page = context.newPage();

                page.navigate("http://www.instagram.com");

                int total = perfis.size();

                log.info("ferificando " + total + "perfis");

                for (int i = 0; i < total; i++) {
                    Perfil pr = new Perfil();
                    pr = ib.verificaPerfil(page, perfis.get(i).getUsername());
                    ib.assistirStores(page, 1);

                    if (pr == null) {

                        perfis.get(i).setStatus(statusBloqueado);

                    } else if (pr.getId() != -1) {
                        perfis.get(i).setNumeroSeguidor(pr.getNumeroSeguidor());
                        perfis.get(i).setNumeroSeguindo(pr.getNumeroSeguindo());
                        perfis.get(i).setNumeroPublicacao(pr.getNumeroPublicacao());
                    }

                    log.info("falta " + (total - i) + "perfis");
                }

            }

        }

        return perfis;
    }

    public Perfil verificaConta(Perfil perfil) {

        Perfil pr = new Perfil();

        File file = new File("context/" + perfil.getUsername() + ".json");

        if (!file.isFile()) {

            this.salvaContext(perfil);

        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(500));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(
                            Paths.get("context/" + perfil.getUsername() + ".json"))
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 650)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(Constantes.s, Constantes.w)
                    .setTimezoneId("America/Sao_Paulo"));

            log.info("Inicia Verifica????o da conta");
            // Open new page
            Page page = context.newPage();

            page.navigate("http://www.instagram.com");

            if (!ib.verificaBloqueio(page)) {

                pr = ib.verificaPerfil(page, perfil.getUsername());

                perfil.setNumeroSeguidor(pr.getNumeroSeguidor());
                perfil.setNumeroSeguindo(pr.getNumeroSeguindo());
                perfil.setNumeroPublicacao(pr.getNumeroPublicacao());

                return perfil;

            }
            log.info("conta bloqueada");
            return null;
        }

    }

    public void salvaContext(Perfil perfil) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(200));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(
                            Paths.get("context/ganharInsta.json"))
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 812)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(Constantes.s, Constantes.w)
                    .setTimezoneId("America/Sao_Paulo"));

            log.info("Inicia Salvar Constext");
            // Open new page
            Page page = context.newPage();

            page.navigate("https://www.instagram.com");

            if (!ib.verificaBloqueio(page)) {

                page.waitForTimeout(3000);
                // Click text=Entrar
                page.click("text=Entrar");

                page.waitForTimeout(5000);

                if (page.title().equalsIgnoreCase("Instagram")) {
                    log.info("inicia login");
                    if (ib.fazerLogin(page, perfil)) {

                        page.waitForTimeout(500);

                        context.storageState(new BrowserContext.StorageStateOptions()
                                .setPath(Paths.get("context/" + perfil.getUsername() + ".json")));

                        page.waitForTimeout(500);

                        log.info("Assiste Stores");
                        ib.assistirStores(page, 1);
                        page.close();

                    }
                }

            }
        }
        log.info("Perfil bloqueado");

    }

    public boolean postarImagem(Perfil perfil, Gerador gera, Categoria categoria) {

        File pastaImagem = new File("imagem/" + categoria.getNome());
        File[] imagens = pastaImagem.listFiles();

        int idImagem = gera.geraInt(0, imagens.length);
        String caminhoImagem = "imagem/" + categoria.getNome() + "/" + imagens[idImagem].getName();

        if (perfil != null) {

            File file = new File("context/" + perfil.getUsername() + ".json");

            if (!file.isFile()) {

                this.salvaContext(perfil);

            }

            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false).setSlowMo(700));

                BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                        .setStorageStatePath(
                                Paths.get("context/" + perfil.getUsername() + ".json"))
                        .setUserAgent(
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                        .setIsMobile(true)
                        .setViewportSize(375, 650)
                        .setHasTouch(true)
                        .setLocale("pt-BR")
                        .setGeolocation(Constantes.s, Constantes.w)
                        .setTimezoneId("America/Sao_Paulo"));

                log.info("Inicia Robo");
                // Open new page -23.558934, -46.627823
                Page page = context.newPage();

                page.navigate("http://www.instagram.com");

                return ib.postar(page, perfil, gera.geraHashtagCategoria(categoria.getId()), caminhoImagem);

            }

        }

        return false;

    }

    public boolean cadastrarGanhaInsta(Perfil perfil) {

        log.info("inicio cadastro perfil " + perfil.getUsername());

        File file = new File("context/" + perfil.getUsername() + ".json");

        if (!file.isFile()) {

            this.salvaContext(perfil);

        }

        return ib.cadastrarGanhaInsta(perfil);
    }

    public void realizarTarefa(Perfil perfil, PerfilRepository rPerfil, int qtsAcoes, int tempoEntreAcoes, int qtsAcoesParaStores, int tempoStores) {

        File file = new File("context/" + perfil.getUsername() + ".json");

        if (!file.isFile()) {

            this.salvaContext(perfil);

        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(500));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(
                            Paths.get("context/" + perfil.getUsername() + ".json"))
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 650)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(Constantes.s, Constantes.w)
                    .setTimezoneId("America/Sao_Paulo"));

            // Open new page
            Page page = context.newPage();

            if(ib.realizarTarefa(page, perfil, qtsAcoes, tempoEntreAcoes, qtsAcoesParaStores, tempoStores)){

                Date data = new Date();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
                perfil.setDataUltimoTrabalho(formatador.format(data));

                GerenciadorPerfil gp = new GerenciadorPerfil(rPerfil);

                rPerfil.save(perfil);
                try {
                    gp.salvarPerfilPlanilha();

                } catch (IOException e) {
                   log.error("Erro ao salvar planilha", e);
                }
            }

        }

    }

    public void teste(Perfil perfil) {

        File file = new File("context/" + perfil.getUsername() + ".json");

        if (!file.isFile()) {

            this.salvaContext(perfil);

        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(500));

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(
                            Paths.get("context/" + perfil.getUsername() + ".json"))
                    .setUserAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                    .setIsMobile(true)
                    .setViewportSize(375, 650)
                    .setHasTouch(true)
                    .setLocale("pt-BR")
                    .setGeolocation(Constantes.s, Constantes.w)
                    .setTimezoneId("America/Sao_Paulo"));

            // Open new page
            Page page = context.newPage();

          //  ib.realizarTarefa(page, perfil);

            page.pause();

        }

    }

}
