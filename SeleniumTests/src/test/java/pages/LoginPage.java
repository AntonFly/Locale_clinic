package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/login
public class LoginPage {

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement button;

    @FindBy(xpath = "//*[@id='mat-input-1']")
    public WebElement password;

    @FindBy(xpath = "//*[@id='mat-input-0']")
    public WebElement email;

    @FindBy(xpath = "//span[text()='Восстановить пароль']")
    public WebElement forgotpassSpan;

    @FindBy(xpath = "//span[text()='Да']")
    public WebElement resetConfirmationButton;


    @FindBy(css = ".form-ok")
    public WebElement formOkSpan;

    public void inputEmail(String email){

        this.email.sendKeys(email);
    }

    public void inputPwd(String pwd){
        this.password.sendKeys(pwd);
    }

    public void logIn(){
        this.button.click();
    }
    
    public String resetPassword() throws InterruptedException {
        this.forgotpassSpan.click();
        this.resetConfirmationButton.click();
        Thread.sleep(1000);
        return  this.formOkSpan.getText();
    }

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}