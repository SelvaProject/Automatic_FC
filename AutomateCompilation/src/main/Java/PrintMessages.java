package main;
import org.openqa.selenium.WebElement;

public class PrintMessages {

	// *****TO PRINT MESSAGES*****

	public void loginSuccess(String url) {
		System.out.println("Logged in to Salesforce successfully!\nURL: " + url);
	}

	public void loginFailure(WebElement loginErr) {
		System.out.println("Salesforce Login Failed!\nDisplayed error: " + loginErr.getText());
	}

	public void alertMessage(String alertText) {
		System.out.println("Alert displayed: " + alertText);
	}

	public void alertAccept() {
		System.out.println("Alert Accepted");
	}

	public void switchClassic() {
		System.out.println("Switched from Lightning to Classic");
	}

	public void start(String text) {
		System.out.println(text + " compilation started..");
	}

	public void printMessage(WebElement message) {
		System.out.println("Displayed Message: " + message.getText());
	}

	public void progressStop(String text) {
		System.out.println(text + " compilation progress stopped");
	}

	public void pageRefresh() {
		System.out.println("Refreshing the page..");
	}

	public void success(String text) {
		System.out.println(text + " compilation completed successfully!");
	}

	public void failure(String text) {
		System.out.println(text + " compilation failed! Maximum time limit reached");
	}

	public void logout() {
		System.out.println("Logged out of Salesforce");
	}

	public void closeBrowser() {
		System.out.println("Closed the browser");
	}
}
