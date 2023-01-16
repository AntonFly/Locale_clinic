import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AddUserPage;
import pages.LoginPage;
import pages.MangerClientsPage;

import java.util.concurrent.TimeUnit;

public class ManagerTests {
    public static LoginPage loginPage;

    public static WebDriver driver;

    public static MangerClientsPage mangerClientsPage;


    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        mangerClientsPage = new MangerClientsPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(ConfProperties.getProperty("loginpage"));


    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        mangerClientsPage.logOff();
        driver.close();
        driver.quit();
    }


    @Test
    public void createClient() throws InterruptedException {
        managerLogIn();
        String status = mangerClientsPage.createClient(
                String.valueOf((long)(Math.random()*Math.pow(10,10))),
                "05.05.2022",
                "FrontTestClient@email.com",
                "FrontTestClient",
                "FrontTestClient",
                "FrontTestClient"
        );
        Assert.assertEquals("Пользователь успешно добавлен",status);
        Thread.sleep(5000);
    }

    @Test
    public void resetPassportRequestTest(){
        driver.get(ConfProperties.getProperty("loginpage"));
        loginPage.inputEmail(ConfProperties.getProperty("adminLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("wrongPWD"));
        //нажимаем кнопку входа
        loginPage.logIn();

        //получаем отображаемый логин
        String resetStatus = loginPage.resetPassword();
        //и сравниваем его с логином из файла настроек
        Assert.assertEquals("Запрос на сброс пароля успешно отправлен, " +
                "после сброса пароля на указанную почту прийдет новый пароль.", resetStatus);
    }

    void managerLogIn(){
        loginPage.inputEmail(ConfProperties.getProperty("managerLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("managerPwd"));
        loginPage.logIn();
        String user = mangerClientsPage.getUserName();
        Assert.assertEquals(ConfProperties.getProperty("managerLogin"), user);
    }
}
