import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.EngineerPage;
import pages.LoginPage;
import pages.MedicSupportPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EngineerTests {
    public static LoginPage loginPage;

    public static EngineerPage engineerPage;

    public static WebDriver driver;

    static String userEmail;
    //profilePage = new ProfilePage(driver);

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "static/chromedriver.exe");
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        engineerPage =  new EngineerPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(ConfProperties.getProperty("loginpage"));

        userEmail = "anton99910+"+Math.random()+"@gmai.com";

    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        engineerPage.logOff();
        driver.close();
        driver.quit();
    }


    @Test
    public void gettingSupportScript() throws InterruptedException {
        engineerLogIn();
        driver.get("http://localhost:4200/#/engineer/main");
        assertTrue(engineerPage.getScenario("1122334465","10"));
    }


    @Test
    public  void uploadGenome() throws InterruptedException {
        engineerLogIn();
        driver.get("http://localhost:4200/#/engineer/main");
        assertTrue(engineerPage.uploadGenome(
                "1122334465",
                "10",
                "C:\\Users\\antonAdmin\\OneDrive\\Рабочий стол\\test_genome.txt"));
    }



    void engineerLogIn(){
        loginPage.inputEmail(ConfProperties.getProperty("engineerLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("engineerPwd"));
        loginPage.logIn();
        String user = engineerPage.getUserName();
        assertEquals(ConfProperties.getProperty("engineerLogin"), user);
    }


}
