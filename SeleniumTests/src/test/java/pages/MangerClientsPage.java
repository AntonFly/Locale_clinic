package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/manager/clients
public class MangerClientsPage {

    @FindBy(xpath = "//span[@class='user-email']")
    public WebElement userName;

    @FindBy(xpath = "//mat-icon[text()='list']")
    public WebElement MatIconList;

    @FindBy(xpath = "//button[text()='Выйти']")
    public WebElement MenuItemButtonLogOff;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Создать клиента']")
    public WebElement createClientButton;

    @FindBy(xpath = "//*[@id='mat-input-3']")
    public WebElement passportInput;

    @FindBy(xpath = "//*[@id='mat-input-4']")
    public WebElement nameInput;

    @FindBy(xpath = "//*[@id='mat-input-5']")
    public WebElement surnameInput;

    @FindBy(xpath = "//*[@id='mat-input-6']")
    public WebElement patronymicInput;

    @FindBy(xpath = "//*[@id='mat-input-7']")
    public WebElement emailInput;

    @FindBy(xpath = "//*[@id='mat-input-8']")
    public WebElement dateInput;

    @FindBy(xpath = "//span[text()='Создать']")
    public WebElement createButton;

    @FindBy(css = ".form-ok")
    public WebElement status;


    // No page elements added

    public MangerClientsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
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

    public String createClient(String passport, String date, String email, String name,
                               String surname, String patronymic){
        createClientButton.click();
        passportInput.sendKeys(passport);
        dateInput.sendKeys(date);
        emailInput.sendKeys(email);
        nameInput.sendKeys(name);
        surnameInput.sendKeys(surname);
        patronymicInput.sendKeys(patronymic);
        createButton.click();

        return status.getText();

    }
}