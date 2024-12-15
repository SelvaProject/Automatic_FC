package main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetupPage {
	WebDriver driver;
	WebDriverWait wait;

	PrintMessages printMsg = new PrintMessages();

	By qckFnd = By.id("setupSearch");
	By apxClsLink = By.id("ApexClasses_font");
	By compileClsLink = By.linkText("Compile all classes");
	By compileprgrs = By.id("all_classes_page:theTemplate:calcStatus.start");
	By clsSuccessMsg = By.id("all_classes_page:theTemplate:messagesForm:j_id54:j_id55:j_id58");
	By orglockdMsg = By.xpath("//span[text()='Organization Administration Locked']");
	By apxTrgLnk = By.xpath("//*[@id=\"ApexTriggers_font\"]");
	By compileTrgLink = By.id("all_triggers_page:theTemplate:messagesForm:compileAll");
	By trgSuccessMsg = By.id("all_triggers_page:theTemplate:messagesForm:j_id24:j_id25:j_id27");

	public SetupPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void enterQuickFindSearch(String searchText) {
		WebElement quickFind = wait.until(ExpectedConditions.presenceOfElementLocated(qckFnd));
		quickFind.clear();
		quickFind.sendKeys(searchText);
	}

	public void clickApexClasses() {
		WebElement apexClasses = wait.until(ExpectedConditions.visibilityOfElementLocated(apxClsLink));
		apexClasses.click();
	}

	public void clickCompileAllClasses() {
		WebElement compileAllClasses = wait.until(ExpectedConditions.presenceOfElementLocated(compileClsLink));
		compileAllClasses.click();
	}

	public void checkCompilationProgress() {
		WebElement compilationProgress = wait.until(ExpectedConditions.visibilityOfElementLocated(compileprgrs));
	}

	public void checkAndPrintClassSuccessMessage() {
		WebElement classSuccessMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(clsSuccessMsg));
		printMsg.printMessage(classSuccessMessage);
	}

	public void checkAndPrintOrgLockedMessage() {
		WebElement orgLockedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(orglockdMsg));
		printMsg.printMessage(orgLockedMessage);
	}

	public void clickApexTriggers() {
		WebElement apexTriggers = wait.until(ExpectedConditions.presenceOfElementLocated(apxTrgLnk));
		apexTriggers.click();
	}

	public void clickCompileAllTriggers() {
		WebElement compileAllTriggers = wait.until(ExpectedConditions.presenceOfElementLocated(compileTrgLink));
		compileAllTriggers.click();
	}

	public void checkAndPrintTriggerSuccessMessage() {
		WebElement triggerSuccessMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(trgSuccessMsg));
		printMsg.printMessage(triggerSuccessMessage);
	}

	public void startClassCompilation(String srchtxt) {
		enterQuickFindSearch(srchtxt);
		clickApexClasses();
		clickCompileAllClasses();
	}

	public void refresh() {
		driver.navigate().refresh();
		printMsg.pageRefresh();
	}

	public void refreshAndRecompileClasses() {
		refresh();
		clickCompileAllClasses();
	}

	public void startTriggerCompilation(String srchtxt) {
		enterQuickFindSearch(srchtxt);
		clickApexTriggers();
		clickCompileAllTriggers();
	}

	public void refreshAndRecompileTriggers() {
		refresh();
		clickCompileAllTriggers();
	}

}
