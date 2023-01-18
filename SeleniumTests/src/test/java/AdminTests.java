
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AddUserPage;
import pages.LoginPage;
import pages.RecoverPassPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminTests {
    public static LoginPage loginPage;
    public static AddUserPage addUserPage;
    public static RecoverPassPage recoverPassPage;

    public static WebDriver driver;

    static String userEmail;
    //profilePage = new ProfilePage(driver);

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "static/chromedriver.exe");
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        addUserPage = new AddUserPage(driver);
        recoverPassPage = new RecoverPassPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(ConfProperties.getProperty("loginpage"));

        userEmail = "anton99910+"+Math.random()+"@gmai.com";

    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        addUserPage.logOff();
        driver.close();
        driver.quit();
    }


    @Test
    public void createNewUser() throws InterruptedException {
        adminLogIn();
        String status = addUserPage.createUser(String.valueOf((long)(Math.random()*Math.pow(10,10))),
                "04.05.2022",
                userEmail,
                "FrontTestName",
                "FrontTestSurname",
                "FrontTestPat");
        assertEquals("Пользователь успешно добавлен", status);
    }

    @Test
    public void resetPassportAcceptTest() throws InterruptedException {
        adminLogIn();
        String status = addUserPage.createUser(String.valueOf((long)(Math.random()*Math.pow(10,10))),
                "04.05.2022",
                userEmail,
                "FrontTestName",
                "FrontTestSurname",
                "FrontTestPat");
        assertEquals("Пользователь успешно добавлен", status);
        addUserPage.logOff();
        Thread.sleep(1000);
        driver.get(ConfProperties.getProperty("loginpage"));
        loginPage.inputEmail(userEmail);
        loginPage.inputPwd(ConfProperties.getProperty("wrongPWD"));
        loginPage.logIn();


        String resetStatus = loginPage.resetPassword();
        assertEquals("Запрос на сброс пароля успешно отправлен, " +
                "после сброса пароля на указанную почту прийдет новый пароль.", resetStatus);
        Thread.sleep(5000);
        adminLogIn();
        driver.get("http://localhost:4200/#/admin/recoverPass");
        status = recoverPassPage.resetPassword(userEmail);

        assertEquals("Пароль успешно сгенерирован", status);
        Thread.sleep(4000);

    }
    void adminLogIn(){
        loginPage.inputEmail(ConfProperties.getProperty("adminLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("adminPwd"));
        loginPage.logIn();
        String user = addUserPage.getUserName();
        assertEquals(ConfProperties.getProperty("adminLogin"), user);
    }
}
