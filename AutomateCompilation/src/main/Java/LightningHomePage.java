package main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LightningHomePage {
	WebDriver driver;
	WebDriverWait wait;

	By avtr = By.className("uiImage");
	By swtchToClsc = By.linkText("Switch to Salesforce Classic");

	public LightningHomePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void clickAvatar() {
		WebElement avatar = wait.until(ExpectedConditions.visibilityOfElementLocated(avtr));
		avatar.click();
	}

	public void clickSwitchToClassic() {
		WebElement switchToClassic = wait.until(ExpectedConditions.visibilityOfElementLocated(swtchToClsc));
		switchToClassic.click();
	}

	public void switchFromLightningToClassic() {
		clickAvatar();
		clickSwitchToClassic();
	}

}