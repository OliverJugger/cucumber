package fr.edu.aix.ecrin.cucumber;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import fr.edu.aix.ecrin.cucumber.stepdefs.EcrinProperties;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"html:target/cucumber", "json:target/cucumber_report/cucumber.json",
                "pretty:target/cucumber_report/cucumber-pretty.txt", "usage:target/cucumber_report/cucumber-usage.json",
                "junit:target/cucumber_report/cucumber-results.xml"}, features = "src/test/features")
public class RunEcrinTest {

    private static final Logger logger = Logger.getLogger(RunEcrinTest.class);
    private static String PATH;
    private static FirefoxDriver compteRendu;
    private static long debutExecution;

    @BeforeClass
    public static void init() {
        debutExecution = System.currentTimeMillis();
        logger.info("**************************************************** ");
        logger.info("**************************************************** ");
        logger.info("*********      NOUVELLE SERIE DE TEST      ********* ");
        logger.info("*********                                  ********* ");
        logger.info("*********               DEBUT              ********* ");
        // System.setProperty("webdriver.chrome.driver",
        // "src/main/resources/chromedriver_win32/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver-v0.20.0-win32/geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "browser.log");

    }

    @AfterClass
    public static void compteRendu() {
        long temps = System.currentTimeMillis() - debutExecution;
        logger.info("Programme executé en : " + temps / 1000 + " secondes.");
        logger.info(PATH + EcrinProperties.getString(EcrinProperties.PATH_COMPTE_RENDU));

        File currentDirFile = new File("");
        PATH = currentDirFile.getAbsolutePath();
        compteRendu = new FirefoxDriver();
        compteRendu.get("file://" + PATH + EcrinProperties.getString(EcrinProperties.PATH_COMPTE_RENDU));
        compteRendu.manage().window().maximize();
    }
}
