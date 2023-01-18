import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.EngineerPage;
import pages.LoginPage;
import pages.ScientistPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScientistTests {
    public static LoginPage loginPage;

    public static ScientistPage scientistPage;

    public static WebDriver driver;

    static String userEmail;
    //profilePage = new ProfilePage(driver);

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "static/chromedriver.exe");
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        scientistPage = new ScientistPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(ConfProperties.getProperty("loginpage"));

        userEmail = "anton99910+"+Math.random()+"@gmai.com";

    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        scientistPage.logOff();
        driver.close();
        driver.quit();
    }





    @Test
    public  void changeScenario() throws InterruptedException {
        scientistLogIn();
        driver.get("http://localhost:4200/#/scientist/scenario");
        assertEquals("Успешно сохранено",scientistPage.changeScenario(
                "1122334465",
                "10"
               ));
    }



    void scientistLogIn(){
        loginPage.inputEmail(ConfProperties.getProperty("scientistLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("scientistPwd"));
        loginPage.logIn();
        String user = scientistPage.getUserName();
        assertEquals(ConfProperties.getProperty("scientistLogin"), user);
    }
}
