package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/#/engineer/main
public class EngineerPage {

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

    @FindBy(xpath = "//span[normalize-space(text()) = 'Сценарий модификации']")
    public WebElement getScenarioBtn;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Заменить']")
    public WebElement replaceGenomeBtn;

    @FindBy(xpath = "//*[@id='fileDropRef']")
    public WebElement fileDropInput;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Подтвердить']")
    public WebElement confirmGenome;


    public EngineerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public boolean getScenario(String passport, String order) throws InterruptedException {
        clientIdInput.sendKeys(passport);
        Thread.sleep(1000);
        new Actions(driver).doubleClick(driver.findElement(By.xpath(String.format("//td[normalize-space(text()) = '%s']", order)))).perform();
        Thread.sleep(1000);
        getScenarioBtn.click();
        return driver.findElements(By.className("button-err")).size()<1;
    }
    
    public boolean uploadGenome(String passport, String order, String filepath) throws InterruptedException {
        clientIdInput.sendKeys(passport);
        Thread.sleep(1000);
        new Actions(driver).doubleClick(driver.findElement(By.xpath(String.format("//td[normalize-space(text()) = '%s']", order)))).perform();
        Thread.sleep(1000);
        replaceGenomeBtn.click();
        fileDropInput.sendKeys(filepath);
        Thread.sleep(1000);
        confirmGenome.click();
        return driver.findElements(By.className("upload-err")).size()<1;
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