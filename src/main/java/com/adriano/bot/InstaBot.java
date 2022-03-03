package com.adriano.bot;

import java.nio.file.Paths;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adriano.model.Perfil;
import com.adriano.utilitario.Constantes;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;

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

        page.navigate("https://www.instagram.com/");

        if (!verificaBloqueio(page)) {

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
        log.info("Perfil bloqueado: ");
        return false;

    }

    public boolean verificaBloqueio(Page page) {

        log.info("Verificando bloqueio");

        pausa(page, 2, 3);

        if (page.isVisible("text=Confirme que")) {
            page.close();
            return true;
        }

        if (page.isVisible("text=telefone")) {
            page.close();
            return true;
        }

        log.info("Perfil ok");
        return false;

    }

    public boolean sair(Page page, Perfil perfil) {

        try {

            page.click("div:nth-child(5) .gKAyB .ewxsm");

            this.pausa(page, 1, 3);

            if (!page.title().contains(perfil.getUsername())) {
                page.navigate("https://www.instagram.com/" + perfil.getUsername());
            }

            this.pausa(page, 1, 3);

            page.click("nav button");

            this.pausa(page, 1, 3);

            page.click("text=Sair");

            this.pausa(page, 1, 3);
            // Click button:has-text("Sair")
            page.click("button:has-text(\"Sair\")");

        } catch (Exception e) {
            return false;
        }
        this.pausa(page, 1, 3);
        return true;
    }

    public Perfil verificaPerfil(Page page, String usernanme) {

        if (!verificaBloqueio(page)) {
            Perfil perfil = new Perfil();

            try {

                page.navigate("http://www.instagram.com");

                this.pausa(page, 1, 3);
                log.info("verifica perfil " + usernanme);
                page.navigate("https://www.instagram.com/" + usernanme);

                this.pausa(page, 2, 5);

                if (page.title().contains(usernanme)) {

                    String[] seguidor = page.innerText("text=seguidor").split("\\n");
                    perfil.setNumeroSeguidor(Integer.parseInt(seguidor[0]));

                    log.info("seguidores " + seguidor[0]);

                    String[] seguindo = page.innerText("text=seguindo").split("\\n");
                    perfil.setNumeroSeguindo(Integer.parseInt(seguindo[0]));

                    log.info("seguindo " + seguindo[0]);

                    String[] publicacao = page.innerText("text=publica").split("\\n");
                    perfil.setNumeroPublicacao(Integer.parseInt(publicacao[0]));

                    log.info("publicação " + publicacao[0]);

                    page.navigate("https://www.instagram.com");
                    return perfil;

                } else {

                    log.info("perfil bloqueado " + usernanme);

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

        log.info("perfil bloqueado");
        return null;

    }

    public boolean postar(Page page, Perfil perfil, String hashtag, String caminhoImagem) {

        if (!verificaBloqueio(page)) {

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

        log.info("perfil bloqueado");
        return false;
    }

    public boolean cadastrarGanhaInsta(Perfil perfil) {

        try {

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

                String cod = "";
                String bio = "";

                log.info("Inicia cadastro no ganhar no insta");
                // Open new page
                Page page = context.newPage();

                page.navigate("https://www.instagram.com");

                if (!verificaBloqueio(page)) {

                    page.navigate("https://www.ganharnoinsta.com/painel/?pagina=adicionar_conta");

                    page.waitForTimeout(3000);
                    // Click a:has-text("Depois")
                    page.click("a:has-text(\"Depois\")");

                    page.waitForTimeout(1000);
                    page.click(
                            "text=Adicionar Conta Informações Importantes:Ao adicionar uma nova conta, ela ficará  >> img");
                    // Click text=Próxima Etapa
                    page.waitForTimeout(1000);
                    page.click("text=Próxima Etapa");

                    // Click [placeholder="@nomedeusuario"]
                    page.click("[placeholder=\"@nomedeusuario\"]");

                    page.waitForTimeout(1000);
                    log.info("Adiciona usuario " + perfil.getUsername());

                    // Fill [placeholder="@nomedeusuario"]
                    page.fill("[placeholder=\"@nomedeusuario\"]", perfil.getUsername());

                    if (perfil.getGenero().equalsIgnoreCase("M")) {

                        log.info("Seleciona genero masculino");
                        page.selectOption("select[name=\"sexo\"]", "1");

                    } else {

                        log.info("Seleciona genero Faminino");
                        page.selectOption("select[name=\"sexo\"]", "2");
                    }

                    page.waitForTimeout(1000);
                    page.waitForNavigation(() -> {
                        page.click("text=Próxima Etapa");
                    });

                    page.waitForTimeout(1000);
                    cod = page.inputValue("input[type=\"text\"]");

                    log.info(" Codigo copiado: " + cod);

                    // Open new page
                    Page page1 = context.newPage();

                    // Go to https://www.instagram.com/
                    page1.navigate("https://www.instagram.com/" + perfil.getUsername());

                    if (this.verificaBloqueio(page1)) {
                        return false;
                    }

                    pausa(page1, 2, 3);
                    // Click text=Editar perfil
                    page1.click("text=Editar perfil");
                    // assert page1.url().equals("https://www.instagram.com/accounts/edit/");

                    pausa(page1, 2, 3);

                    // Click text=luta e gloria sempre em frente
                    bio = page1.inputValue("textarea");

                    log.info("Biografica compiada");

                    pausa(page1, 2, 3);
                    // Fill text=luta e gloria sempre em frente
                    page1.fill("textarea", cod);

                    pausa(page1, 2, 3);
                    // Click text=Enviar
                    page1.click("text=Enviar");

                    pausa(page1, 2, 3);

                    page1.navigate("https://www.instagram.com/");

                    page.waitForTimeout(1000);
                    page.waitForNavigation(() -> {
                        page.click("button:has-text(\"Validar Conta\")");
                    });

                    page.waitForTimeout(1000);
                    // Click span img[alt="homepage"]
                    page.click("span img[alt=\"homepage\"]");
                    // assert page.url().equals("https://www.ganharnoinsta.com/painel/");

                    page.waitForTimeout(3000);

                    page1.navigate("https://www.instagram.com/" + perfil.getUsername());

                    page.waitForTimeout(3000);
                    // Click text=Editar perfil
                    page1.click("text=Editar perfil");
                    // assert page1.url().equals("https://www.instagram.com/accounts/edit/");

                    page.waitForTimeout(3000);
                    // Click text=luta e gloria sempre em frente da37f1
                    page1.click("textarea");

                    page.waitForTimeout(3000);
                    // Fill text=luta e gloria sempre em frente da37f1
                    page1.fill("textarea", bio);

                    page.waitForTimeout(3000);
                    // Click text=Enviar
                    page1.click("text=Enviar");

                    // Go to https://www.instagram.com/
                    page1.navigate("https://www.instagram.com/");
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            log.error("Erro ao cadastrar", e);
            return false;
        }

    }

    public boolean realizarTarefa(Page page, Perfil perfil, int numeroAcoes, int tempoEntreTarefas, int qtsAcoesParaStores,
            int tempoStores) {

        log.info(perfil.getUsername() + " inicia realização de tarefas");

        tempoEntreTarefas = tempoEntreTarefas * 1000;

        page.navigate("https://www.instagram.com");

        if (!verificaBloqueio(page)) {

            try {

                // Go to https://www.ganharnoinsta.com/painel/?pagina=sistema
                page.navigate("https://www.ganharnoinsta.com/painel/?pagina=sistema");

                page.waitForTimeout(1500);

                // Click a:has-text("Depois")
                // page.click("a:has-text(\"Depois\")");

                page.waitForTimeout(1500);

                page.selectOption("select[name=\"contaig\"]",
                        new SelectOption().setLabel(perfil.getUsername().toLowerCase()));

                page.waitForTimeout(1500);

                // Click text=Iniciar Sistema
                page.click("text=Iniciar Sistema");

                page.waitForTimeout(1500);

                for (int i = 1; i <= numeroAcoes;) {

                    // espera nova tarefa
                    log.info(perfil.getUsername() + " procurando nova tarefa");
                    
                    while (!page.isVisible("text=Seguir Perfil") && !page.isVisible("text=Curtir Publicação")) {

                        page.waitForTimeout(5000);
                    }

                    if (page.isVisible("text=Seguir Perfil")) {

                        log.info(perfil.getUsername() + " tarefa de seguir perfil");

                        Page page1 = page.waitForPopup(() -> {
                            page.click("a:has-text(\"Acessar Perfil\")");
                        });

                        pausa(page, 1, 2);

                        if (!verificaBloqueio(page)) {
                            try {

                                pausa(page1, 1, 2);
                                // Click button:has-text("Seguir")
                                page1.click("button:has-text(\"Seguir\")");
                                pausa(page1, 1, 2);
                                // Close page
                                page1.close();

                                page.waitForTimeout(1000);

                                page.click("button:has-text(\"Confirmar Ação\")");

                                log.info(perfil.getUsername() + " " + i + " tarefa seguir realizada");

                                log.info(perfil.getUsername() + " Tempo par aproxima tarefa " + tempoEntreTarefas / 1000
                                        + " segundos");

                                page.waitForTimeout(tempoEntreTarefas);

                                i++;
                            } catch (Exception e) {
                                log.error("Erro ao realizar ação " + e);
                            }
                        } else {

                            log.info("Perfil bloqueado");
                            return false;
                           
                        }
                    }

                    if (page.isVisible("text=Curtir Publicação")) {

                        log.info(perfil.getUsername() + " tarefa de curtir");

                        Page page1 = page.waitForPopup(() -> {
                            page.click("a:has-text(\"Acessar Publicação\")");
                        });

                        pausa(page, 1, 2);

                        if (!verificaBloqueio(page)) {
                            try {

                                pausa(page1, 1, 2);

                                page1.pause();

                                // Click button:has-text("curtir") Curtir
                                page1.click("[aria-label='Curtir']");

                                pausa(page1, 1, 2);

                                // Close page
                                page1.close();

                                page.waitForTimeout(1000);

                                page.click("button:has-text(\"Confirmar Ação\")");

                                log.info(perfil.getUsername() + " " + i + " tarefa curtir realizada");

                                log.info(perfil.getUsername() + " Tempo par aproxima tarefa " + tempoEntreTarefas / 1000
                                        + " segundos");

                                page.waitForTimeout(tempoEntreTarefas);

                                i++;

                            } catch (Exception e) {
                                log.error("Erro ao realizar ação " + e);
                            }

                        } else {

                            log.info("Perfil bloqueado");
                            return false;
                        }

                    }

                     // Assiste stores
                    if (i % qtsAcoesParaStores == 0) {

                        // Click text=Pausar Sistema
                        page.click("text=Pausar Sistema");

                        assistirStores(page, tempoStores);

                        page.navigate("https://www.ganharnoinsta.com/painel/?pagina=sistema");

                        page.waitForTimeout(1500);


                        page.selectOption("select[name=\"contaig\"]",
                                new SelectOption().setLabel(perfil.getUsername().toLowerCase()));

                        page.waitForTimeout(1500);

                        // Click text=Iniciar Sistema
                        page.click("text=Iniciar Sistema");

                    }

                }

                // Click text=Pausar Sistema
                page.click("text=Pausar Sistema");

                page.close();

                log.info(perfil.getUsername() + " realizar ações finalizado");


                return true;

               
            } catch (Exception e) {
                log.error("Erro ao realizar tarefa ", e);
            }
        } else {

            log.info("Perfil bloqueado " + perfil.getUsername());
            return false;

        }

        return false;

    }

    public boolean pausa(Page page, int minimo, int maximo) {

        minimo = minimo * 1000;
        maximo = maximo * 1000;

        Random gerador = new Random();

        page.waitForTimeout(gerador.nextInt(maximo - minimo) + minimo);

        return true;
    }

}
