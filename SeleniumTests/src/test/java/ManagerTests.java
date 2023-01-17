
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AddUserPage;
import pages.LoginPage;
import pages.ManagerOrderPage;
import pages.MangerClientsPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerTests {
    public  LoginPage loginPage;

    public  WebDriver driver;

    public  MangerClientsPage mangerClientsPage;

    public  ManagerOrderPage managerOrderPage;

    static String clientPassport;


    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        mangerClientsPage = new MangerClientsPage(driver);
        managerOrderPage = new ManagerOrderPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        clientPassport = String.valueOf((long)(Math.random()*Math.pow(10,10)));

        driver.get(ConfProperties.getProperty("loginpage"));


    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);

        driver.close();
        driver.quit();
    }


    @Test
    public void createClient() throws InterruptedException {
        managerLogIn();
        String status = mangerClientsPage.createClient(
                clientPassport,
                "05.05.2022",
                "FrontTestClient@email.com",
                "FrontTestClient",
                "FrontTestClient",
                "FrontTestClient"
        );
        assertEquals("Пользователь успешно добавлен",status);
        Thread.sleep(5000);
        mangerClientsPage.logOff();
    }

    @Test
    public void changeUser() throws InterruptedException {
        managerLogIn();
        String status = mangerClientsPage.createClient(
                clientPassport,
                "05.05.2022",
                "FrontTestClient@email.com",
                "FrontTestClient",
                "FrontTestClient",
                "FrontTestClient"
        );
        assertEquals("Пользователь успешно добавлен",status);
        status =  mangerClientsPage.changeClient(clientPassport,"New comment");
        assertEquals("Изменения сохранены",status);

        Thread.sleep(5000);
        mangerClientsPage.logOff();
    }

    @Test
    public void createOrder() throws InterruptedException {
        managerLogIn();
        String status = mangerClientsPage.createClient(
                clientPassport,
                "05.05.2022",
                "FrontTestClient@email.com",
                "FrontTestClient",
                "FrontTestClient",
                "FrontTestClient"
        );
        assertEquals("Пользователь успешно добавлен",status);
        driver.get(ConfProperties.getProperty("managerOrderPage"));

        status = managerOrderPage.createOrder(clientPassport);

        assertEquals("Заявка успешно добавлена", status);

        Thread.sleep(5000);
        mangerClientsPage.logOff();


    }

    @Test
    public void getCommercial() throws InterruptedException {
        managerLogIn();
        driver.get(ConfProperties.getProperty("managerOrderPage"));
        assertTrue(managerOrderPage.getCommercial("1122334465"));


        Thread.sleep(5000);
        mangerClientsPage.logOff();
    }

    void managerLogIn(){
        loginPage.inputEmail(ConfProperties.getProperty("managerLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("managerPwd"));
        loginPage.logIn();
        String user = mangerClientsPage.getUserName();
        assertEquals(ConfProperties.getProperty("managerLogin"), user);
    }
}
