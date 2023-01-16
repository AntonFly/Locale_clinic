package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/admin/recoverPass
public class RecoverPassPage {
    @FindBy(xpath = "//span[normalize-space(text()) = 'Сгенерировать']")
    public WebElement generateButton;


    

    // No page elements added
    private WebDriver driver;


    @FindBy(css = ".result-msg")
    public WebElement resultMsgDiv;

    public String resetPassword(String email){
        driver.findElement(By.xpath(String.format("//span[text()='%s']", email))).click();
        generateButton.click();
        return resultMsgDiv.getText();

    }

    public RecoverPassPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}