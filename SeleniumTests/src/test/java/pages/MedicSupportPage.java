package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// page_url = http://localhost:4200/medic/support
public class MedicSupportPage {

    @FindBy(xpath = "//span[@class='user-email']")
    public WebElement userName;

    @FindBy(xpath = "//mat-icon[text()='list']")
    public WebElement MatIconList;

    @FindBy(xpath = "//button[text()='Выйти']")
    public WebElement MenuItemButtonLogOff;

    @FindBy(xpath = "//form")
    public WebElement exampleForm;

    @FindBy(xpath = "//*[@id='client-id-input']")
    public WebElement clientIdInput;


    @FindBy(xpath = "//*[@id='script-header']")
    public WebElement supportWindowHeader;

    // No page elements added
    WebDriver driver;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Сгенерировать сценарий']")
    public WebElement generateButton;

    @FindBy(xpath = "//mat-icon[text()='close']")
    public WebElement closeButton;

    @FindBy(css = ".search-field")
    public WebElement clientSearchField;

    @FindBy(xpath = "//mat-icon[text()='search']")
    public WebElement searchBnt;

    @FindBy(css = ".search-icon")
    public WebElement searchIconMat;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Импланты']")
    public WebElement implantsButton;

    @FindBy(xpath = "//mat-icon[text()='add']")
    public WebElement addImplantBtn;

    @FindBy(xpath = "//tr[.//span[normalize-space(text()) = '17.01.2023']]//span[normalize-space(text()) = 'Изменить']")
    public WebElement matButtonWrapperSpan;

    @FindBy(xpath = "//*[@id='mat-input-1']")
    public WebElement implantNameInput;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Готово']")
    public WebElement doneImplantBtn;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Сохранить']")
    public WebElement saveImplantBtn;

    @FindBy(css = ".form-ok")
    public WebElement status;

    public MedicSupportPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    
    public String getSupportScript(String passport, String order) throws InterruptedException {
        clientIdInput.sendKeys(passport);
        Thread.sleep(1000);
        new Actions(driver).click(driver.findElement(By.xpath(String.format("//td[normalize-space(text()) = '%s']", order)))).perform();
        generateButton.click();
        Thread.sleep(1000);
        String status = supportWindowHeader.getText();
        closeButton.click();
        return status;


        
    }

    public String addImplant(String passport, String name, String order) throws InterruptedException {
        clientSearchField.clear();
        Thread.sleep(5000);
        clientSearchField.sendKeys(passport+ Keys.ENTER);
        clientSearchField.sendKeys(Keys.ENTER);
        searchIconMat.click();
        openClient(passport);
        new Actions(driver).click(driver.findElement(By.xpath(String.format("//td[normalize-space(text()) = '%s']", order)))).perform();
        implantsButton.click();
        Thread.sleep(1000);
        addImplantBtn.click();

        new Actions(driver).doubleClick(driver.findElement(By.xpath(String.format("//tr[.//span[normalize-space(text()) = '%s']]//span[normalize-space(text()) = 'Изменить']",
                       DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now())))))
                .perform();
        Thread.sleep(1000);
        implantNameInput.sendKeys(name);
        Thread.sleep(1000);
        doneImplantBtn.click();
        Thread.sleep(1000);
        saveImplantBtn.click();
        return  status.getText();
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

    public void openClient(String passport){
        new Actions(driver).doubleClick(driver.findElement(By.xpath(String.format("//td[text()='%s']", passport)))).perform();
    }
}