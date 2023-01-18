package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/#/scientist/scenario
public class ScientistPage {
    @FindBy(xpath = "//span[@class='user-email']")
    public WebElement userName;

    @FindBy(xpath = "//mat-icon[text()='list']")
    public WebElement MatIconList;

    @FindBy(xpath = "//button[text()='Выйти']")
    public WebElement MenuItemButtonLogOff;
    @FindBy(xpath = "//*[@id='client-id-input']")
    public WebElement clientIdInput;

    // No page elements added
    WebDriver driver;


    @FindBy(xpath = "//span[text()='Изменить']")
    public WebElement changeScenarioBtn;

    @FindBy(xpath = "//span[contains(text(), 'знания')]")
    public WebElement mod;

    @FindBy(xpath = "//span[contains(text(), 'нижние')]")
    public WebElement destMod;

    @FindBy(xpath = "//span[contains(@style, 'green;')]")
    public WebElement status;

    @FindBy(xpath = "//span[text()='Сохранить']")
    public WebElement saveScenario;

    

    


    public ScientistPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public String changeScenario(String passport, String order) throws InterruptedException {
        clientIdInput.sendKeys(passport);
        Thread.sleep(1000);
        new Actions(driver).doubleClick(driver.findElement(By.xpath(String.format("//td[normalize-space(text()) = '%s']", order)))).perform();
        Thread.sleep(1000);
        changeScenarioBtn.click();
        Thread.sleep(1000);
        new Actions(driver).clickAndHold(mod)
                .moveToElement(destMod)
                .release(destMod)
                .build().perform();
        Thread.sleep(1000);
        saveScenario.click();
        return status.getText();
    }



    public String getUserName(){
        return this.userName.getText();
    }

    private void openMenu(){
        MatIconList.click();
    }

    public void logOff(){
        openMenu();
        MenuItemButtonLogOff.click();
    }

}