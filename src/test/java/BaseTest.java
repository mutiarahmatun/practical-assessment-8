import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import test.ApplicationConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class BaseTest {

    private static Properties environment;
    private ApplicationConfig cfg = ConfigFactory.create(ApplicationConfig.class);


    @BeforeSuite(description = "Configure URI")
    public void SetUpSuite(){
        RestAssured.baseURI = cfg.host();
        RestAssured.basePath = cfg.base();
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterSuite
    public void TearDownSuite() {
        environment = new Properties();
        environment.put("OS name", System.getProperty("os.name"));
        environment.put("OS version", System.getProperty("os.version"));
        environment.put("URL", cfg.host());

        saveEnvironment();
    }

    private void saveEnvironment() {
        File resultsFolder = new File("./build/allure-results");
        if (!resultsFolder.exists()) {
            resultsFolder.mkdirs();
        }
        try (OutputStream out = new FileOutputStream("./build/allure-results/environment.properties")){
            environment.store(out, "Allure environment properties");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
