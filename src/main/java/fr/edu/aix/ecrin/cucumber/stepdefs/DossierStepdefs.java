package fr.edu.aix.ecrin.cucumber.stepdefs;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonn�que;
import cucumber.api.java.fr.Lorsque;

public class DossierStepdefs {

    private static final Logger logger = Logger.getLogger(DossierStepdefs.class);
    // private ChromeDriver driver;
    // private NgWebDriver ngWebDriver; pour AngularJS

    private FirefoxDriver driver;
    private String role;
    private String indemniteCree = "";
    private String indemniteCourante = "";
    private String baseUrl = "";

    public static void screenShot(String nameTc, WebDriver driver) {
        // Take screenshot and store as a file format
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // now copy the screenshot to desired location using copyFile
            // //method
            FileUtils.copyFile(src, new File("target\\cucumber\\" + nameTc + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Before
    public void init_suite() throws Exception {
        logger.info("*********               DEBUT DU SCENARIO              ********* ");
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat h = new SimpleDateFormat("hh:mm");
        Date currentTime_1 = new Date();
        String dateString = d.format(currentTime_1);
        String heureString = h.format(currentTime_1);
        // scenario.write("<b>Sc�nario fait le " + dateString + " � " +
        // heureString + "</b>");
        logger.info("Sc�nario fait le " + dateString + " � " + heureString);
        driver = new FirefoxDriver(); // new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("CHEMIN DU COMPTE RENDU :");
        logger.info(EcrinProperties.getString(EcrinProperties.PATH_COMPTE_RENDU));
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {

        logger.info("Etat du sc�nario : " + scenario.getStatus());
        if (scenario.isFailed()) {
            logger.info("Le sc�nario " + scenario.getName() + " a �chou�");
            logger.info("********** SCREENSHOT ********");
            scenario.write("<b> Voici un screenshot de l'erreur : <b> <br/>");
            // Prend un screenshot en cas d'erreur...
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); // ... et l'integre dans le
                                                     // rapport.
        }
        logger.info("*********               FIN DU SCENARIO              ********* ");
        // this.driver.quit();
    }

    @Etantdonn�que("^je suis sur l'environnement \"([^\"]*)\"$")
    public void je_suis_sur_l_environnement(final String environnement) throws InterruptedException {
        if (environnement.equalsIgnoreCase("Qualification") || environnement.equalsIgnoreCase("Qualif")) {
            this.baseUrl = EcrinProperties.getString(EcrinProperties.URL_QUALIF);
        } else if (environnement.equalsIgnoreCase("Int�gration")) {
            this.baseUrl = EcrinProperties.getString(EcrinProperties.URL_INT);
        } else if (environnement.equalsIgnoreCase("D�veloppement") || environnement.equalsIgnoreCase("Dev")) {
            this.baseUrl = EcrinProperties.getString(EcrinProperties.URL_DEV);
        }
        this.driver.get(baseUrl);
    }

    @Etantdonn�que("^je suis connect� en tant que \"([^\"]*)\" avec le mot de passe \"([^\"]*)\"$")
    public void je_suis_authentifi�_en_tant_que_avec_le_mot_de_passe(final String username, final String password) {
        this.driver.findElement(By.id("user")).sendKeys(username);
        this.driver.findElement(By.id("password")).sendKeys(password);
        this.driver.findElementByCssSelector("[value=\"Valider\"]").click();
    }

    @Etantdonn�que("^mon r�le est \"([^\"]*)\"$")
    public void mon_r�le_est(final String role) {
        this.role = role;
    }

    @Lorsque("^je veux chercher l'indemnite \"([^\"]*)\"$")
    public void je_veux_chercher_l_indemnite(final String numeroIndemnite) throws InterruptedException {

        String indemnite;
        if (numeroIndemnite.equalsIgnoreCase("cr�e")) {
            indemnite = this.indemniteCree;
        } else {
            indemnite = numeroIndemnite;
        }
        logger.info("Je veux chercher l'indemnite : " + indemnite);
        this.driver.findElement(By.cssSelector("[placeholder=\"N� Indemnit�\"]")).click();
        this.driver.findElement(By.cssSelector("[placeholder=\"N� Indemnit�\"]")).sendKeys(indemnite);

        logger.info("Je veux cliquer sur l'indemnite : " + indemnite);

        List<WebElement> indemnites = this.driver.findElements(By.cssSelector("span.detailsIndem"));
        for (int i = 0; i < indemnites.size(); i++) {
            WebElement indemniteTrouvee = indemnites.get(i);
            if (indemniteTrouvee.getText().equals(indemnite)) {
                indemniteTrouvee.click();
            }
        }

    }

    @Lorsque("^je veux cliquer sur l'indemnite \"([^\"]*)\"$")
    public void je_veux_cliquer_sur_l_indemnite(final String numeroIndemnite) throws InterruptedException {

        String indemnite;
        if (numeroIndemnite.equalsIgnoreCase("cr�e")) {
            indemnite = this.indemniteCree;
        } else {
            indemnite = numeroIndemnite;
        }
        logger.info("Je veux cliquer sur l'indemnite : " + indemnite);

        List<WebElement> indemnites = this.driver.findElements(By.cssSelector("span.detailsIndem"));
        for (int i = 0; i < indemnites.size(); i++) {
            WebElement indemniteTrouvee = indemnites.get(i);
            if (indemniteTrouvee.getText().equals(indemnite)) {
                indemniteTrouvee.click();
            }
        }

    }
    // this.ngWebDriver.waitForAngularRequestsToFinish();

    @Lorsque("^je veux remplir les champs duplication avec Numero de Decret \"([^\"]*)\" Date d'Ouverture \"([^\"]*)\" Libelle Court \"([^\"]*)\" et Libelle Long \"([^\"]*)\"$")
    public void je_veux_remplir_les_champs_duplication(final String numeroDecret, final String dateOuverture, final String libelleCourt,
        final String libelleLong) throws InterruptedException {

        int random = (int) (Math.random() * (EcrinProperties.INDEMNITE_NUM_MAX - EcrinProperties.INDEMNITE_NUM_MIN + 1))
                        + EcrinProperties.INDEMNITE_NUM_MIN;

        String numeroIndemniterandom = String.valueOf(random);

        this.driver.findElement(By.cssSelector("input#indemnite")).sendKeys(numeroIndemniterandom);
        this.driver.findElement(By.cssSelector("input#decret")).sendKeys(numeroDecret);
        this.driver.findElement(By.cssSelector("input#dateOuvertureIndemnite")).sendKeys(dateOuverture);

        this.driver.findElement(By.cssSelector("input#libelleCourt")).sendKeys(libelleCourt);
        this.driver.findElement(By.cssSelector("input#libelleLong")).sendKeys(libelleLong);

        // Enregistrer
        this.driver.findElement(By.cssSelector("button#savebtnDupliquerIndemnite")).click();

        // Ok sur le "Warning"

        this.driver.findElement(By.cssSelector("[data-bb-handler=\"ok\"]")).click();
    }

    @Lorsque("^je veux cr�er une nouvelle indemnit� avec un Numero d'Indemnite al�atoire, Numero de Decret \"([^\"]*)\" Date d'Ouverture \"([^\"]*)\" Libelle Court \"([^\"]*)\" et Libelle Long \"([^\"]*)\"$")
    public void je_veux_creer_une_nouvelle_indemnite(final String numeroDecret, final String dateOuverture, final String libelleCourt,
        final String libelleLong) throws InterruptedException {

        this.driver.get(baseUrl + "creerIndemnite");

        int random = (int) (Math.random() * (EcrinProperties.INDEMNITE_NUM_MAX - EcrinProperties.INDEMNITE_NUM_MIN + 1))
                        + EcrinProperties.INDEMNITE_NUM_MIN;

        String numeroIndemniterandom = String.valueOf(random);

        logger.info("Cr�ation de l'indemnit� num�ro " + numeroIndemniterandom + "...");
        this.indemniteCree = numeroIndemniterandom;

        //
        this.driver.findElement(By.cssSelector("input#indemnite")).sendKeys(numeroIndemniterandom);
        this.driver.findElement(By.cssSelector("input#decret")).sendKeys(numeroDecret);
        this.driver.findElement(By.cssSelector("input#dateOuvertureIndemnite")).sendKeys(dateOuverture);
        this.driver.findElement(By.cssSelector("input#libelleCourt")).sendKeys(libelleCourt);
        this.driver.findElement(By.cssSelector("input#libelleLong")).sendKeys(libelleLong);

        TimeUnit.SECONDS.sleep(2);
        this.driver.findElement(By.cssSelector("button#savebtnCreerIndemnite")).click();

        //
        this.driver.findElement(By.cssSelector("[data-bb-handler=\"ok\"]")).click();
        //

    }

    @Lorsque("^je veux \"([^\"]*)\" l'indemnit� \"([^\"]*)\"$")
    public void je_veux_controler_l_indemnite(final String nomFonction, final String numeroIndemnite) throws InterruptedException {
        logger.info("action indemnite");
        if (!numeroIndemnite.equalsIgnoreCase("cr�e") && !numeroIndemnite.equalsIgnoreCase("courante")) {
            je_veux_chercher_l_indemnite(numeroIndemnite);
        }
        // sinon je suis deja dessus donc pas besoin de la chercher

        logger.info("je suis deja dessus");
        TimeUnit.SECONDS.sleep(2);

        String id_balise = "";
        if (nomFonction.equalsIgnoreCase("Cr�er une nouvelle version")) {
            id_balise = "btnCloner";
        } else if (nomFonction.equalsIgnoreCase("Dupliquer")) {
            id_balise = "btnDupliquer";

        } else if (nomFonction.equalsIgnoreCase("Contr�ler")) {
            id_balise = "btnControler";

        } else if (nomFonction.equalsIgnoreCase("Valider")) {
            id_balise = "btnValider";

        }
        String elementATrouver = "div#idemnselect button#" + id_balise;
        if (id_balise.equals("")) {
            logger.info("Nom de fonction non valide : " + nomFonction);
        } else {
            logger.info("Je dois cliquer sur : " + elementATrouver);
            WebElement fonction = this.driver.findElement(By.cssSelector(elementATrouver));
            logger.info("element trouve vs fonction demandee : ");
            logger.info(fonction.getText());
            logger.info(nomFonction);
            if (fonction.getText().equalsIgnoreCase(nomFonction)) {
                fonction.click();
            }

        }
        TimeUnit.SECONDS.sleep(2);

        /*
         * TimeUnit.SECONDS.sleep(3); try {
         * this.driver.findElement(By.cssSelector(
         * "[data-bb-handler=\"confirm\"]")).click(); } catch (Exception e) {
         * logger.info("ERREUR �l�ment non trouv� :" + e); }
         */

        try {
            this.driver.findElement(By.cssSelector("button.btn.btn-default.modalButton")).click();
        } catch (Exception e) {
            logger.info("Il n'y a pas le bouton \"fermer\" ?");
            screenShot("erreurPotientielle", driver);
        }
        TimeUnit.SECONDS.sleep(2);
    }

    @Lorsque("^je veux remplir le bloc Informations G�n�rales$")
    public void je_veux_remplir_le_bloc_informations_generales() throws InterruptedException {

        this.driver.findElement(By.cssSelector("a[href=\"#ongletDesc\"]")).click();

        this.driver.findElement(By.cssSelector("tbody#infoGen button#infoGen")).click();

        this.driver.findElement(By.cssSelector("tbody#infoGen input#libEdit")).sendKeys("Test Automatis�");
        this.driver.findElement(By.cssSelector("tbody#infoGen input#indemniteEppNon")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen input#indemniteAgoraOui")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen input#indemniteAgapeNon")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen input#indemniteSupNon")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen input#indemniteEpriveNon")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen input#indemniteApriveNon")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen input#diffusionCentraleOui")).click();
        this.driver.findElement(By.cssSelector("tbody#infoGen button#infoGen")).click();

        this.driver.findElement(By.cssSelector("button#propIndem[title=\"Modifier\"]")).click();

    }

    @Lorsque("^je veux remplir le bloc Propri�t�s de l'indemnit�$")
    public void je_veux_remplir_le_bloc_proprietes_indemnites() throws InterruptedException {

        this.driver.findElement(By.cssSelector("a[href=\"#ongletDesc\"]")).click();

        List<WebElement> selects = this.driver.findElements(By.cssSelector("tbody#propIndem select"));

        logger.info("Il y a " + selects.size() + "elements select dans le tbody propIndem");
        logger.info(selects);

        WebElement select0 = selects.get(0);
        select0.click();

        select0.findElement(By.cssSelector("option[value=\"1\"]")).click();

        WebElement select1 = selects.get(1);
        select1.click();

        select1.findElement(By.cssSelector("option[value=\"A\"]")).click();

        WebElement select2 = selects.get(2);
        select2.click();
        select2.findElement(By.cssSelector("option[value=\"O\"]")).click();

        WebElement select3 = selects.get(3);
        select3.click();
        select3.findElement(By.cssSelector("option[value=\"02\"]")).click();

        // 4
        WebElement select4 = selects.get(4);
        select4.click();
        select4.findElement(By.cssSelector("option[value=\"A\"]")).click();

        // 5

        WebElement select5 = selects.get(5);
        select5.click();
        select5.findElement(By.cssSelector("option[value=\"APN1\"]")).click();

        // 6

        WebElement select6 = selects.get(6);
        select6.click();
        select6.findElement(By.cssSelector("option[value=\"AFF\"]")).click();

        // Enregistrer
        this.driver.findElement(By.cssSelector("button#propIndem[title=\"Enregistrer\"]")).click();

        TimeUnit.SECONDS.sleep(3);
    }

    @Lorsque("^je veux remplir l'onglet description$")
    public void je_veux_remplir_onglet_description() throws InterruptedException {

        je_veux_remplir_le_bloc_informations_generales();
        je_veux_remplir_le_bloc_proprietes_indemnites();

    }

    @Lorsque("^je veux remplir le bloc type de personnel")
    public void je_veux_remplir_type_personnel() throws InterruptedException {

        this.driver.findElement(By.cssSelector("button#typePersonnelId")).click();

        this.driver.findElement(By.cssSelector("tbody#typePerso select")).click();
        this.driver.findElement(By.cssSelector("tbody#typePerso select option[value=\"1\"]")).click();

        this.driver.findElement(By.cssSelector("button#typePersonnel")).click();

    }

    @Lorsque("^je veux remplir le bloc imputation")
    public void je_veux_remplir_imputation() throws InterruptedException {

        TimeUnit.SECONDS.sleep(2);
        this.driver.findElement(By.cssSelector("a[href=\"#ongletControles\"]")).click();

        this.driver.findElement(By.cssSelector("button#imputationsId")).click();

        this.driver.findElement(By.cssSelector("tbody#imputations td.sorting_1 input")).sendKeys("0139**JD");

        this.driver.findElement(By.cssSelector("tbody#imputations select")).click();
        TimeUnit.SECONDS.sleep(2);
        this.driver.findElement(By.cssSelector("tbody#imputations select option[value=\"0126\"]")).click();
        System.out.println("J'ai cliqu�");
        this.driver.findElement(By.cssSelector("input.imputationAfterDatepicker")).sendKeys("05/03/2018");
        TimeUnit.SECONDS.sleep(2);

        List<WebElement> buttons = this.driver.findElements(By.cssSelector("button#imputations"));
        // le boutton validation sera toujours l'avant dernier de ce tableau
        // (voir le HTML)
        buttons.get(buttons.size() - 2).click();

    }

    @Lorsque("^je veux selectionner la premi�re indemnit� que je vois$")
    public void je_veux_selectionner_la_premiere_indemnite() throws InterruptedException {

        List<WebElement> selects = this.driver.findElements(By.cssSelector("div.panel-body span[href=\"#idemnselect\"]"));
        System.out.println("Il y a " + selects.size() + "indemnites visibles"); // 7
        this.indemniteCourante = selects.get(0).getText();
        logger.info("indemnite courante : " + indemniteCourante);
        selects.get(0).click();

    }

    @Lorsque("^je veux remplir les champs de validation$")
    public void je_veux_remplir_les_champs_de_validation() throws InterruptedException {
        this.driver.findElement(By.cssSelector("textarea#champMotifValidation")).sendKeys("TEST AUTOMATISE");
        this.driver.findElement(By.cssSelector("button#btnConfirmerValidation")).click();

    }

    @Lorsque("^je veux chercher une indemnit� avec l'�tat \"([^\"]*)\"$")
    public void je_veux_chercher_une_indemnite_avec_l_etat(final String etat) throws InterruptedException {
        WebElement select = this.driver.findElement(By.cssSelector("select#searchEtat"));
        String value = "";
        if (etat.equalsIgnoreCase("Incoh�rent") || etat.equalsIgnoreCase("Qualif")) {
            value = "0";
        } else if (etat.equalsIgnoreCase("A contr�ler")) {
            value = "1";
        } else if (etat.equalsIgnoreCase("Coh�rent") || etat.equalsIgnoreCase("Dev")) {
            value = "2";
        } else if (etat.equalsIgnoreCase("Valid�") || etat.equalsIgnoreCase("Dev")) {
            value = "3";
        } else if (etat.equalsIgnoreCase("Publi�") || etat.equalsIgnoreCase("Dev")) {
            value = "4";
        } else if (etat.equalsIgnoreCase("Tous") || etat.equalsIgnoreCase("Dev")) {
            value = "5";
        }
        select.click();
        TimeUnit.SECONDS.sleep(2);
        select.findElement(By.cssSelector("option[value=\"" + value + "\"]")).click();
        TimeUnit.SECONDS.sleep(2);
    }

    @Lorsque("^je veux publier les \"([^\"]*)\"$")
    public void je_veux_publier(final String type) throws IllegalArgumentException, InterruptedException {
        String url = "";
        if (type.equals("indemnit�s")) {
            url = "publication/demande";
        } else
            throw new IllegalArgumentException("indemnite ou nomenclature mal ecrit dans le fichier feature");
        this.driver.get(this.baseUrl + url);
        List<WebElement> elementsAPublier = this.driver.findElements(By.cssSelector("div.row div.alert.alert-warning span.marge"));
        String indemnites = elementsAPublier.get(0).getText();
        logger.info("Nombre d'indemnit�s : " + indemnites.substring(0, 1));
        String nomenclatures = elementsAPublier.get(1).getText();
        logger.info("Nombre de nomenclatures : " + nomenclatures.substring(0, 1));
        if (indemnites.substring(0, 1).equals("0")) {
            logger.info("Il n'y a pas d'indemnit� a publier...");
        }
        if (nomenclatures.substring(0, 1).equals("0")) {
            logger.info("Il n'y a pas de nomenclature a publier...");
        }

        this.driver.findElement(By.cssSelector("textarea#champMotifPublication")).sendKeys("TEST AUTOMATISE");
        this.driver.findElement(By.cssSelector("button#confirmPublication")).click();
        this.driver.findElement(By.cssSelector("button#confirmPubButton")).click();
        TimeUnit.SECONDS.sleep(40);

        String success = this.driver.findElement(By.cssSelector("div.bootbox-body")).getText();
        if (!success.equals("Les indemnit�s ont �t� trait�es avec succ�s")) {
            fail("La publication a �chou� ou le delais est trop long");
        } else {
            this.driver.findElement(By.cssSelector("[data-bb-handler=\"ok\"]")).click();
        }
    }

    @Alors("^il n'y a plus d'indemnites valid�s$")
    public void il_n_y_a_pas_d_indemnites_publies() throws InterruptedException {
        this.driver.get(this.baseUrl);

        TimeUnit.SECONDS.sleep(2);
        je_veux_chercher_une_indemnite_avec_l_etat("valid�");

        try {
            this.driver.findElement(By.cssSelector("td.dataTables_empty")).click();
        } catch (Exception e) {
            fail("La liste n'est pas vide");
        }
        assertTrue(true);
    }

    @Alors("^mon role est \"([^\"]*)\"$")
    public void mon_role_est(final String role) {
        WebElement profil = this.driver.findElement(By.cssSelector("span.badge"));
        logger.info("Profil trouv� : " + profil);
        assertThat(profil.getText(), containsString(role));
    }

    @Alors("^l'indemnite \"([^\"]*)\" s'affiche$")
    public void l_indemnite_s_affiche(final String numeroIndemnite) {
        boolean trouv� = false;
        List<WebElement> indemnites = this.driver.findElements(By.cssSelector("span.detailsIndem"));
        for (int i = 0; i < indemnites.size(); i++) {
            WebElement indemnite = indemnites.get(i);
            if (indemnite.getText().equals(numeroIndemnite)) {
                trouv� = true;
            }
        }
        assertTrue(trouv�);
    }

    @Alors("^l'indemnite est a l'�tat \"([^\"]*)\"$")
    public void l_indemnite_est_a_l_etat(final String nomEtat) throws InterruptedException {

        boolean etat_correct = false;
        TimeUnit.SECONDS.sleep(2);
        WebElement indemnite = this.driver.findElement(By.cssSelector("div#idemnselect tbody#indemSel td#idemnselectEtat"));
        if (indemnite.getText().equals(nomEtat)) {
            etat_correct = true;
        }
        logger.info("Etat demand� : " + nomEtat);
        logger.info("Etat present : " + indemnite.getText());
        assertTrue(etat_correct);
    }

    protected String getRoleString() {
        if (this.role != null && "Gestionnaire".equalsIgnoreCase(this.role.trim())) {
            return "ge";
        }

        return "de";
    }
}
