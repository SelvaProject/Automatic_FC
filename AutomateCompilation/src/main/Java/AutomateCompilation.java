package main;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

public class AutomateCompilation {

	public static void main(String[] args) throws InterruptedException, IOException {

//		String chromePath = ReadProperties.getChromepath();

//		Initialization of configFile
		String configFile = System.getProperty("configFile");

		System.out.println("Config file received");

		if (configFile == null || configFile.isEmpty())
		{
			System.err.println("ERROR: 'configFile' system property is empty.");
			System.exit(1);
		}
		System.out.println("Loading config file");

		try
		{
			ReadProperties.loadProperties(configFile);
		}
		catch (Exception e)
		{
			System.err.println("Failed to load properties");
        	e.printStackTrace();
        	System.exit(1);
		}

//		Loading the Properties
//		ReadProperties.loadProperties(configFile);
		
		String sfUrl = ReadProperties.getUrl();
		String sfUsername = ReadProperties.getUsername();
		String sfPassword = ReadProperties.getPassword();

		String apxCls = "Apex Classes", apxTrg = "Apex Triggers";
		long startTime = System.currentTimeMillis(), maxWaitTime = 120 * 60 * 1000;
		boolean successMessageAppeared = false;
		int retry = 0, maxRetry = 5;

		// *****LAUNCH CHROME*****
//		System.setProperty("webdriver.chrome.driver", chromePath);
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//		System.setProperty("webdriver.chrome.driver", "chromedriver");
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // Run in headless mode
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems

		WebDriver driver = new ChromeDriver(options);

		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

		LoginPage loginpage = new LoginPage(driver, wait);
		LightningHomePage lightninghompage = new LightningHomePage(driver, wait);
		ClassicHomePage classichomepage = new ClassicHomePage(driver, wait);
		SetupPage setuppage = new SetupPage(driver, wait);
		PrintMessages printmessages = new PrintMessages();

		// *****OPEN ARCADIA*****
		driver.get(sfUrl);
		loginpage.loginToSalesforce(sfUsername, sfPassword);

		// Check if login failed
		try {
			loginpage.checkLoginError();
			driver.quit();
			printmessages.closeBrowser();
		} catch (TimeoutException e) {
			// GET THE URL AND VERIFY THE VIEW
			String currentUrl = driver.getCurrentUrl();
			printmessages.loginSuccess(currentUrl);

			// ACCEPT MAINTENANCE ALERT IF PRESENT - NOT TESTED
			try {
				Alert alert = driver.switchTo().alert();
				String alertMsg = alert.getText();
				printmessages.alertMessage(alertMsg);
				alert.accept();
				printmessages.alertAccept();
			} catch (NoAlertPresentException e1) {
			}

			// SWITCH TO CLASSIC
			try {
				lightninghompage.switchFromLightningToClassic();
				printmessages.switchClassic();
			} catch (TimeoutException e1) {
			}

			// GO TO SETUP
			classichomepage.goToSetup();

			// *****PERFORM COMPILE ALL CLASSES*****
			setuppage.startClassCompilation(apxCls);
			printmessages.start(apxCls);

			while (System.currentTimeMillis() - startTime < maxWaitTime) {
				try {
					setuppage.checkCompilationProgress();
				} catch (TimeoutException e1) {
					try {
						setuppage.checkAndPrintClassSuccessMessage();
						successMessageAppeared = true;
						break;
					} catch (TimeoutException e2) {
						try {
							setuppage.checkAndPrintOrgLockedMessage();
							setuppage.refreshAndRecompileClasses();
						} catch (TimeoutException e3) {
							printmessages.progressStop(apxCls);
							setuppage.refreshAndRecompileClasses();
						}
					}
				}
			}

			// DISPLAY APEX CLASS COMPILATION SUCCESS/FAILURE MESSAGE
			if (successMessageAppeared) {
				printmessages.success(apxCls);
			} else {
				printmessages.failure(apxCls);
			}

			// *****PERFORM COMPILE ALL TRIGGERS*****
			setuppage.startTriggerCompilation(apxTrg);
			printmessages.start(apxTrg);

			// REFRESH & RETRY MAX 5 TIMES IF SUCCESS MESSAGE IS NOT VISIBLE
			while (retry < maxRetry) {
				startTime = System.currentTimeMillis();
				maxWaitTime = 7 * 60 * 1000;
				successMessageAppeared = false;

				// CHECKING FOR APEX TRIGGER COMPILATION SUCCESS MESSAGE UNTIL MAXWAITTIME
				while (System.currentTimeMillis() - startTime < maxWaitTime) {
					try {
						setuppage.checkAndPrintTriggerSuccessMessage();
						successMessageAppeared = true;
						break;
					} catch (TimeoutException e1) {
					}
				}
				// REFRESH THE PAGE AND COMPILE IF PROGRESS IS NOT VISIBLE
				if (successMessageAppeared) {
					printmessages.success(apxTrg);
					break;
				} else {
					printmessages.progressStop(apxTrg);
					setuppage.refreshAndRecompileTriggers();
					retry++;

					// STOP RETRYING & DISPLAY FAILURE MESSAGE IF THE MAX LIMIT REACHED
					if (retry >= maxRetry) {
						printmessages.failure(apxTrg);
						break;
					}
				}
			}

			// *****LOGOUT*****
			classichomepage.logoutOfSalesforce();
			printmessages.logout();

			// *****CLOSE BROWSER*****
			driver.quit();
			printmessages.closeBrowser();
		}

	}
}
