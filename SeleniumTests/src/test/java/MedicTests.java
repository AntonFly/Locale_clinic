import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.MedicSupportPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicTests {
    public static LoginPage loginPage;


    public static MedicSupportPage medicSupportPage;

    public static WebDriver driver;

    static String userEmail;
    //profilePage = new ProfilePage(driver);

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "static/chromedriver.exe");
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        medicSupportPage = new MedicSupportPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(ConfProperties.getProperty("loginpage"));

        userEmail = "anton99910+"+Math.random()+"@gmai.com";

    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        medicSupportPage.logOff();
        driver.close();
        driver.quit();
    }


    @Test
    public void gettingSupportScript() throws InterruptedException {
        medicLogIn();
        String status = medicSupportPage.getSupportScript("1122334465","10");
        assertEquals("Сценарий для специализации Агент", status);
    }

    @Test
    public void addImplant() throws InterruptedException {
        medicLogIn();
        driver.get("http://localhost:4200/medic/reporting");
        String status = medicSupportPage.addImplant("1122334465","FrontTestImplant","10");
        assertEquals("Изменения сохранены", status);
    }


    void medicLogIn(){
        loginPage.inputEmail(ConfProperties.getProperty("medicLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("medicPwd"));
        loginPage.logIn();
        String user = medicSupportPage.getUserName();
        assertEquals(ConfProperties.getProperty("medicLogin"), user);
    }
}
