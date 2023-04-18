package lib.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPage extends CorePage{
    public AuthorizationPage(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String
            LOGIN_INPUT = "id:wpName1",
            PASSWORD_INPUT = "id:wpPassword1",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public void fillLoginField(String login) {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find login field", 5);
    }

    public void fillPasswordField(String password) {
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find pass field", 5);
    }

    public void clickLoginSubmitButton() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find submit button", 5);
    }

    public void authorize(String login, String password) {
        fillLoginField(login);
        fillPasswordField(password);
        clickLoginSubmitButton();
    }

}
