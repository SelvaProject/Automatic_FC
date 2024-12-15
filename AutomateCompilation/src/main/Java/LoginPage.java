package main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	WebDriver driver;
	WebDriverWait wait;

	PrintMessages printMsg = new PrintMessages();

	By usernameFld = By.id("username");
	By passwordFld = By.id("password");
	By loginBtn = By.id("Login");
	By loginerr = By.xpath("//*[@id=\"error\"]");

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void enterUsername(String username) {
		WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld));
		usernameField.sendKeys(username);
	}

	public void enterPassword(String password) {
		WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld));
		passwordField.sendKeys(password);
	}

	public void clickLogin() {
		WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn));
		loginButton.click();
	}

	public void loginToSalesforce(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLogin();
	}

	public void checkLoginError() {
		WebElement loginError = wait.until(ExpectedConditions.visibilityOfElementLocated(loginerr));
		printMsg.loginFailure(loginError);
	}

}
