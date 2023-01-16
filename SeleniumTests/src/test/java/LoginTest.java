import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AddUserPage;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

public class LoginTest {

    public static LoginPage loginPage;
    public static AddUserPage addUserPage;

    public static WebDriver driver;

    //profilePage = new ProfilePage(driver);

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "static/chromedriver.exe");
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        addUserPage = new AddUserPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        addUserPage.logOff();
        driver.close();
        driver.quit();
    }


    @Test
    public void successLoginTest() {
        driver.get(ConfProperties.getProperty("loginpage"));
        loginPage.inputEmail(ConfProperties.getProperty("adminLogin"));
        loginPage.inputPwd(ConfProperties.getProperty("adminPwd"));
        //нажимаем кнопку входа
        loginPage.logIn();
        //получаем отображаемый логин
        String user = addUserPage.getUserName();
        //и сравниваем его с логином из файла настроек
        Assert.assertEquals(ConfProperties.getProperty("adminLogin"), user);
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

}
