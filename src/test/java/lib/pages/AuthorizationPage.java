package lib.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPage extends CorePage{
    public AuthorizationPage(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String
            LOGIN_INPUT = "id:wpName1",
            PASSWORD_INPUT = "id:wpPassword1",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    @Step("Insert login '{login}'")
    public void fillLoginField(String login) {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find login field", 5);
    }

    @Step("Insert password '{password}'")
    public void fillPasswordField(String password) {
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find pass field", 5);
    }

    @Step("Click on button to submit login")
    public void clickLoginSubmitButton() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find submit button", 5);
    }

    @Step("Authorize on wiki by login '{0}' and password '{1}'")
    public void authorize(String login, String password) {
        fillLoginField(login);
        fillPasswordField(password);
        clickLoginSubmitButton();
    }

}
