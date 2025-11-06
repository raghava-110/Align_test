import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class AlignMetricAutomation {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Test Data from TC001
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final String TEST_USER_EMAIL = "sudhirbd@gmail.com";
    private static final String TEST_USER_PASSWORD = "Mindlinks2025#";
    private static final String COMPANY_NAME = "Mindlinks";
    private static final String METRIC_NAME_TC001 = "Weekly Formula Metric TC001";
    private static final String FIRST_METRIC = "Existing Metric A";
    private static final String SECOND_METRIC = "Existing Metric B";
    private static final List<Integer> CUSTOM_TARGET_VALUES = Arrays.asList(400, 300, 200, 100);

    /**
     * Sets up the WebDriver and WebDriverWait instances before each test.
     */
    public void setUp() {
        // WebDriverManager can be used here for automatic driver management.
        // For this example, assuming chromedriver is in the system PATH.
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Increased wait time to 30 seconds as requested
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    /**
     * Tears down the WebDriver instance after each test.
     */
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Finds an element using a list of fallback XPath locators.
     * Implements a retry mechanism to handle timing issues.
     * @param xpaths Varargs of XPath strings to try.
     * @return The found WebElement.
     * @throws NoSuchElementException if the element cannot be found with any of the provided XPaths.
     */
    private WebElement findElementWithFallbacks(String... xpaths) {
        NoSuchElementException lastException = null;
        for (int i = 0; i < 3; i++) { // Retry loop
            for (String xpath : xpaths) {
                try {
                    return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                } catch (TimeoutException | NoSuchElementException e) {
                    lastException = new NoSuchElementException("Could not find element with XPath: " + xpath, e);
                }
            }
            try {
                Thread.sleep(500); // Small delay before retrying
            } catch (InterruptedException ignored) {}
        }
        throw new NoSuchElementException("Element not found after retries with provided XPaths: " + Arrays.toString(xpaths), lastException);
    }
    
    /**
     * Finds a list of elements using a list of fallback XPath locators.
     */
    private List<WebElement> findElementsWithFallbacks(String... xpaths) {
        for (String xpath : xpaths) {
            try {
                // Wait for at least one element to be present
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                return driver.findElements(By.xpath(xpath));
            } catch (TimeoutException | NoSuchElementException e) {
                // Continue to the next xpath
            }
        }
        return new ArrayList<>();
    }

    /**
     * A robust click method that waits for clickability and uses JavaScript fallback.
     * Implements retry logic for StaleElementReferenceException.
     * @param element The WebElement to click.
     */
    private void robustClick(WebElement element) {
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                return;
            } catch (StaleElementReferenceException e) {
                // Re-finding the element is handled by the caller's structure.
                // This catch block just allows the retry.
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                js.executeScript("arguments[0].click();", element);
                return;
            } catch (Exception e) {
                if (i == 2) throw e;
            }
        }
    }

    /**
     * Clicks an element found by its fallback locators.
     * @param xpaths The fallback XPath locators for the element.
     */
    private void findAndClick(String... xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        robustClick(element);
    }
    
    /**
     * Sends keys to an element after ensuring it is visible and clear.
     * @param element The WebElement to send keys to.
     * @param text The text to send.
     */
    private void sendKeysToElement(WebElement element, String text) {
         for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                element.clear();
                element.sendKeys(text);
                return;
            } catch (StaleElementReferenceException e) {
                 // Allow retry to re-find the element in the calling method
            }
        }
    }

    /**
     * Finds an element and sends keys to it.
     * @param text The text to send.
     * @param xpaths The fallback XPath locators for the element.
     */
    private void findAndSendKeys(String text, String... xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        sendKeysToElement(element, text);
    }
    
    /**
     * Helper method for selecting an option from a Kendo UI style dropdown.
     */
    private void selectKendoDropdownOption(String dropdownTriggerXpath, String optionText) {
        findAndClick(dropdownTriggerXpath);
        String optionXpath = String.format("//li[text()='%s']", optionText);
        findAndClick(optionXpath);
    }

    /**
     * Main test method executing the flow from TC001.
     */
    public void testCreateFormulaMetricWithCustomTarget() throws InterruptedException {
        // Step 1: Navigate to the application URL.
        driver.get(BASE_URL);

        // Step 2: Enter email and continue.
        findAndSendKeys(TEST_USER_EMAIL, "//input[@id='usernameField']", "//input[@name='username']");
        findAndClick("//button[@id='continueButton']", "//button[contains(.,'Continue')]");

        // Step 3: Enter password and login.
        findAndSendKeys(TEST_USER_PASSWORD, "//input[@id='passwordField']", "//input[@name='password']");
        findAndClick("//button[@id='loginButton']", "//button[contains(.,'Login')]");

        // Step 4: Select the company.
        String companyXpath = String.format("//div[@class='company-name' and text()='%s']", COMPANY_NAME);
        findAndClick(companyXpath);

        // Step 5: Verify dashboard page is displayed.
        wait.until(ExpectedConditions.urlContains("/Dashboard/My"));
        findElementWithFallbacks("//div[contains(@class,'my-kpis-header-title') and contains(text(),'My KPIs')]");

        // Step 6: Click on 'Metrics' in the navigation menu.
        findAndClick("//a[@href='/Metrics']", "//span[text()='Metrics']/ancestor::a");

        // Step 7: Click the 'Add Metric' button.
        findAndClick("//button[contains(.,'Add Metric')]", "//a[contains(.,'Add Metric')]");

        // Step 8: Enter the metric name.
        findAndSendKeys(METRIC_NAME_TC001, "//input[@placeholder='Name of the Metric']", "//input[contains(@data-bind,'MetricName')]");
        
        // Step 9: Select 'Formula' as Value Source.
        selectKendoDropdownOption("(//label[contains(text(),'Value Source')]/..//span[@class='k-select'])[1]", "Formula");

        // Step 10: Verify the 'Formula Builder' interface is visible.
        findElementWithFallbacks("//div[@class='formula-builder']", "//div[text()='Formula Builder']");

        // Step 11: Select the first metric for the formula.
        findAndSendKeys(FIRST_METRIC, "//input[@placeholder='Search Metric']");
        findAndClick(String.format("//div[@class='metric-name' and text()='%s']", FIRST_METRIC));
        
        // Step 12: Click on a mathematical operator button.
        findAndClick("//button[text()='+']");

        // Step 13: Select the second metric for the formula.
        findAndSendKeys(SECOND_METRIC, "//input[@placeholder='Search Metric']");
        findAndClick(String.format("//div[@class='metric-name' and text()='%s']", SECOND_METRIC));

        // Step 14 & 17: Verify formula and click the green checkmark.
        findElementWithFallbacks("//div[@class='formula-string' and contains(., '"+FIRST_METRIC+"') and contains(., '"+SECOND_METRIC+"')]");
        findAndClick("//span[@title='Validate and Calculate']");

        // Step 15: Select 'Weekly' cadence.
        selectKendoDropdownOption("(//label[contains(text(),'Cadence')]/..//span[@class='k-select'])[1]", "Weekly");

        // Step 16: Select 'Resets On' day.
        selectKendoDropdownOption("(//label[contains(text(),'Resets On')]/..//span[@class='k-select'])[1]", "Monday");
        
        // Step 18: Click the 'Save' button.
        findAndClick("//button[@id='saveCompanyMetric']", "//button[contains(., 'Save')]");
        
        // Step 19: Navigate to 'My Dashboard'.
        findAndClick("//a[@href='/Dashboard/My']", "//span[text()='My Dashboard']/ancestor::a");

        // Step 20: Click the 'Edit KPI' icon.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'my-kpis-header-title')]")));
        findAndClick("//span[@title='Edit Kpi']", "//div[contains(@class,'my-kpis')]//span[contains(@class,'ico-edit')]");
        
        // Step 21 & 22: Locate and add the new metric.
        String newMetricInModalXpath = String.format("//div[contains(@class,'k-item-text') and contains(text(),'%s')]", METRIC_NAME_TC001);
        findAndClick(newMetricInModalXpath);

        // Step 23: Save the KPI selection.
        findAndClick("//div[contains(@class,'modal-content')]//button[contains(text(), 'Save')]");

        // Step 24: Locate the correct metric card on the dashboard.
        String metricCardXpath = String.format("//div[contains(@class,'metric-card-name') and @title='%s']/ancestor::div[contains(@class,'metric-card-container')]", METRIC_NAME_TC001);
        WebElement metricCard = findElementWithFallbacks(metricCardXpath);
        
        // Step 25-27: Hover, click three-dot menu, and click 'Edit'.
        Actions actions = new Actions(driver);
        actions.moveToElement(metricCard).perform();
        Thread.sleep(500); // Wait for menu to appear
        WebElement menuButton = metricCard.findElement(By.xpath(".//button[contains(@class,'k-menu-button')]"));
        robustClick(menuButton);
        findAndClick("//div[contains(@class,'k-popup')]//li[contains(.,'Edit')]");

        // Step 28: Wait for Edit page and select 'Target' tab.
        wait.until(ExpectedConditions.urlContains("/Metric/Edit"));
        findAndClick("//a[text()='Target']", "//li/a[contains(text(),'Target')]");
        
        // Step 29: Click the 'Custom' radio button.
        findAndClick("//label[text()='Custom']/preceding-sibling::input[@type='radio']");

        // Step 30: Enter custom target values.
        for (int i = 0; i < CUSTOM_TARGET_VALUES.size(); i++) {
            String levelInputXpath = String.format("//label[contains(text(),'Level %d')]/following-sibling::span//input", i + 1);
            findAndSendKeys(String.valueOf(CUSTOM_TARGET_VALUES.get(i)), levelInputXpath);
        }
        
        // Step 31: Click 'Save'.
        findAndClick("//button[@id='saveCompanyMetric']", "//div[@class='edit-metric-view']//button[contains(text(),'Save')]");

        // Step 32: Verify navigation back to dashboard and presence of the card.
        wait.until(ExpectedConditions.urlContains("/Dashboard/My"));
        WebElement finalMetricCard = findElementWithFallbacks(metricCardXpath);
        wait.until(ExpectedConditions.visibilityOf(finalMetricCard));
        System.out.println("TC001: Test Completed Successfully!");
    }


    public static void main(String[] args) {
        AlignMetricAutomation test = new AlignMetricAutomation();
        try {
            test.setUp();
            test.testCreateFormulaMetricWithCustomTarget();
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.tearDown();
        }
    }
}
