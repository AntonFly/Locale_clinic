package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/manager/requests
public class ManagerOrderPage {
    // No page elements added
    @FindBy(css = ".client-passport")
    WebElement passportInputField;

    @FindBy(css = "[aria-label = 'Специализация']")
    public WebElement specInputField;

    @FindBy(xpath = "//div[normalize-space(text()) = 'Пособность построения математических моделей']")
    public WebElement mod;

    @FindBy(xpath = "//*[@id='cdk-drop-list-1']")
    public WebElement modDest;

    @FindBy(xpath = "//span[text()='Создать заявку']")
    public WebElement createOrderBtn;

    @FindBy(css = ".form-ok")
    public WebElement status;

    WebDriver driver;

    @FindBy(xpath = "//h3[text()='Выберете специализацию']")
    public WebElement elementH;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Ком. предложение']")
    public WebElement getCommercialBtn;

    @FindBy(css = ".button-err")
    public WebElement statusCom;

    @FindBy(xpath = "//div[text()='Просмотр заявок']")
    public WebElement ordersBtn;
    
    public ManagerOrderPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String createOrder(String passport) throws InterruptedException {
        Thread.sleep(1000);
        passportInputField.clear();
        Thread.sleep(1000);
        passportInputField.sendKeys(passport);
        Thread.sleep(1000);
        specInputField.sendKeys("Агент");
        elementH.click();
        Thread.sleep(1000);
        new Actions(driver).clickAndHold(mod)
                        .moveToElement(modDest)
                                .release(modDest)
                                        .build().perform();

        Thread.sleep(5000);
        createOrderBtn.click();

        return status.getText();
    }
    public void openClient(String passport){
        new Actions(driver).doubleClick(driver.findElement(By.xpath(String.format("//td[text()='%s']", passport)))).perform();
    }

    public boolean getCommercial(String passport) throws InterruptedException {
        ordersBtn.click();
        openClient(passport);
        getCommercialBtn.click();
        Thread.sleep(5000);
        return driver.findElements(By.className("button-err")).size()<1;


    }
}