package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/admin/addUser
public class AddUserPage {
    @FindBy(xpath = "//span[@class='user-email']")
    public WebElement userName;

    @FindBy(xpath = "//mat-icon[text()='list']")
    public WebElement MatIconList;

    @FindBy(xpath = "//button[text()='Выйти']")
    public WebElement MenuItemButtonLogOff;

    @FindBy(xpath = "//*[@id='mat-input-2']")
    public WebElement passportInput;

    @FindBy(xpath = "//*[@id='mat-input-3']")
    public WebElement dateInput;

    @FindBy(xpath = "//*[@id='mat-input-4']")
    public WebElement emailInput;

    @FindBy(xpath = "//*[@id='mat-input-5']")
    public WebElement nameInput;

    @FindBy(xpath = "//*[@id='mat-input-6']")
    public WebElement surnameInput;

    @FindBy(xpath = "//*[@id='mat-input-7']")
    public WebElement patInput;

    @FindBy(xpath = "//span[text()='Администратор']")
    public WebElement roleSelector;

    @FindBy(xpath = "//span[normalize-space(text()) = 'Менеджер']")
    public WebElement managerRole;

    @FindBy(xpath = "//span[text()='Создать']")
    public WebElement createUserButton;

    @FindBy(css = ".form-ok")
    public WebElement status;


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

    public String createUser(String passport, String date, String email, String name,
                             String surname, String patronymic) throws InterruptedException {
        passportInput.sendKeys(passport);
        dateInput.sendKeys(date);
        emailInput.sendKeys(email);
        nameInput.sendKeys(name);
        surnameInput.sendKeys(surname);
        patInput.sendKeys(patronymic);
        roleSelector.click();
        managerRole.click();
        createUserButton.click();
        Thread.sleep(5000);
        return status.getText();
    }

    public AddUserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}