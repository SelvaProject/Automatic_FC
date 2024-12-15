package main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClassicHomePage {
	WebDriver driver;
	WebDriverWait wait;

	By usrPrfl = By.id("globalHeaderNameMink");
	By usrLabl = By.id("userNavLabel"); // Added for Minimum Access profile
	By setUp = By.linkText("Setup");
	By devConsole = By.linkText("Developer Console");
	By lgout = By.linkText("Logout");

	public ClassicHomePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void clickUserProfile() {
		WebElement userProfile = wait.until(ExpectedConditions.presenceOfElementLocated(usrPrfl));
		userProfile.click();
	}

	// Added for Minimum Access profile
	public void clickUserLabel() {
		WebElement userLabel = wait.until(ExpectedConditions.presenceOfElementLocated(usrLabl));
		userLabel.click();
	}

	public void clickSetup() {
		WebElement setup = wait.until(ExpectedConditions.presenceOfElementLocated(setUp));
		setup.click();
	}

	public void clickLogout() {
		WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(lgout));
		logout.click();
	}

	public void goToSetup() {
//		clickUserProfile();		// Edited for Minimum Access profile
		clickSetup();
	}

	public void logoutOfSalesforce() {
//		clickUserProfile();		// Edited for Minimum Access profile
		clickUserLabel(); // Added for Minimum Access profile
		clickLogout();
	}

}
