package com.selenium.generated;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlignTestDataDrivenTest {

    // ========== LOCATORS - MAPPED FROM INPUTS ==========
    private static final String[] EMAIL_INPUT = new String[]{"//input[@id='usernameField']", "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']", "//input[@name='username']"};
    private static final String[] CONTINUE_BUTTON = new String[]{"//button[@id='continueButton']"};
    private static final String[] PASSWORD_INPUT = new String[]{"//input[@id='passwordField']", "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']", "//input[@type='password']"};
    private static final String[] LOGIN_BUTTON = new String[]{"//button[@id='loginButton']"};
    private static final String[] METRICS_LINK = new String[]{"//a[@href='/Metric']"};
    private static final String[] ADD_METRIC_BUTTON = new String[]{"//button[contains(text(), 'Add Metric')]"};
    private static final String[] NAME_INPUT = new String[]{"//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']"};
    private static final String[] VALUE_SOURCE_DROPDOWN = new String[]{"//label[contains(text(),'Value Source')]/following-sibling::span//span[@class='k-select']"};
    private static final String[] CADENCE_DROPDOWN = new String[]{"//label[contains(text(),'Cadence')]/following-sibling::span//span[@class='k-select']"};
    private static final String[] RESETS_ON_DROPDOWN = new String[]{"//label[contains(text(),'Resets On')]/following-sibling::span//span[@class='k-select']"};
    private static final String[] SAVE_BUTTON = new String[]{"//button[@id='saveButton']", "//button[contains(text(),'Save')]", "//button[contains(@class, 'btn-success') and not(@style='display: none;')]"};
    private static final String[] MY_DASHBOARD_LINK = new String[]{"//a[@href='/Dashboard/My']"};
    private static final String[] EDIT_KPI_ICON = new String[]{"//span[@title='Edit Kpi']"};
    private static final String[] FORMULA_SEARCH_METRIC_INPUT = new String[]{"//input[@placeholder='Name or Owner']"};
    private static final String[] FORMULA_OPERATOR_BUTTON = new String[]{"//div[@class='formula-operators']//button[contains(text(), '%s')]"};
    private static final String[] FORMULA_CONFIRM_BUTTON = new String[]{"//span[@title='Validate and Calculate']"};
    private static final String[] METRIC_SEARCH_INPUT = new String[]{"//label[contains(text(),'Metric')]/following-sibling::span//input"};
    private static final String[] TARGET_TAB = new String[]{"//a[contains(text(), 'Target')]"};
    private static final String[] CUSTOM_RADIO_BUTTON = new String[]{"//label[contains(text(), 'Custom')]/preceding-sibling::input[@type='radio']"};
    private static final String[] TIME_BASED_RADIO_BUTTON = new String[]{"//label[contains(text(), 'Time-Based')]/preceding-sibling::input[@type='radio']"};
    private static final String[] CUSTOM_TARGET_LEVEL_1_INPUT = new String[]{"(//input[@data-bind='value: KPITarget.Level1'])[1]"};
    private static final String[] CUSTOM_TARGET_LEVEL_2_INPUT = new String[]{"(//input[@data-bind='value: KPITarget.Level2'])[1]"};
    private static final String[] CUSTOM_TARGET_LEVEL_3_INPUT = new String[]{"(//input[@data-bind='value: KPITarget.Level3'])[1]"};
    private static final String[] CUSTOM_TARGET_LEVEL_4_INPUT = new String[]{"(//input[@data-bind='value: KPITarget.Level4'])[1]"};
    private static final String[] TIME_BASED_START_INPUT = new String[]{"//input[@data-bind='value: KPITarget.TimeBasedStart']"};
    private static final String[] TIME_BASED_TARGET_INPUT = new String[]{"//input[@data-bind='value: KPITarget.TimeBasedTarget']"};

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/temp/selenium/AlignTestData.xlsx";
    // ==================================================
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // ========== EXCEL READER UTILITY ==========
    public static class ExcelReader {
        public static List<Map<String, String>> readExcel(String filePath) {
            List<Map<String, String>> testData = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) return testData;

                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row currentRow = sheet.getRow(i);
                    if (currentRow == null) continue;
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < headers.size(); j++) {
                        Cell currentCell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String cellValue;
                        if (currentCell.getCellType() == CellType.NUMERIC) {
                            cellValue = String.valueOf((long) currentCell.getNumericCellValue());
                        } else {
                            cellValue = currentCell.getStringCellValue();
                        }
                        rowData.put(headers.get(j), cellValue);
                    }
                    testData.add(rowData);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to read Excel file: " + filePath, e);
            }
            return testData;
        }
    }

    // ========== HELPER METHODS ==========
    private WebElement findElementWithFallbacks(String[] locators) {
        for (String locator : locators) {
            try {
                return driver.findElement(By.xpath(locator));
            } catch (NoSuchElementException e) {
                // Ignore and try next locator
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided locators.");
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private WebElement waitVisible(String[] locators) {
        return wait.until(d -> findElementWithFallbacks(locators));
    }

    private WebElement waitClickable(String[] locators) {
        return wait.until(ExpectedConditions.elementToBeClickable(findElementWithFallbacks(locators)));
    }

    private void clickElement(String[] locators) {
        WebElement element = waitClickable(locators);
        element.click();
    }

    private void sendKeysToElement(String[] locators, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(locators);
        element.clear();
        element.sendKeys(text);
    }

    private void assertElementVisible(String[] locators) {
        Assert.assertTrue(waitVisible(locators).isDisplayed(), "Element is not visible.");
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    private void assertUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
        Assert.assertTrue(driver.getCurrentUrl().contains(partialUrl), "URL does not contain: " + partialUrl);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void takeScreenshot(String testCaseId) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path destination = Paths.get("screenshots", testCaseId + "_" + timestamp + ".png");
            Files.createDirectories(destination.getParent());
            Files.copy(srcFile.toPath(), destination);
            log("Screenshot saved to: " + destination);
        } catch (IOException e) {
            log("Failed to take screenshot: " + e.getMessage());
        }
    }

    private void log(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message);
    }

    private void logStart(String testCaseId, String testName) {
        log("======================================================================");
        log("STARTING TEST: " + testCaseId + " - " + testName);
        log("======================================================================");
    }

    private void logPass(String testCaseId) {
        log("======================================================================");
        log("PASSED TEST: " + testCaseId);
        log("======================================================================");
    }

    private void handleTestFailure(String testCaseId, Exception e) {
        log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log("FAILED TEST: " + testCaseId + " - " + e.getMessage());
        e.printStackTrace();
        takeScreenshot(testCaseId);
        log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Assert.fail("Test failed for " + testCaseId, e);
    }

    // ========== TEST EXECUTION ==========
    @Test
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testDataList = ExcelReader.readExcel(EXCEL_FILE_PATH);
        for (Map<String, String> testData : testDataList) {
            String testCaseId = testData.get("id");
            String testName = testData.get("name");
            try {
                logStart(testCaseId, testName);
                executeWorkflow(testData);
                logPass(testCaseId);
            } catch (Exception e) {
                handleTestFailure(testCaseId, e);
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from the map
        String baseUrl = data.get("base_url");
        String email = data.get("test_user_email");
        String password = data.get("test_user_password");
        String companyName = data.get("company_name");
        String metricName = data.get("metric_name");
        String valueSource = data.get("value_source");
        String cadence = data.get("cadence");
        String resetsOn = data.get("resets_on");
        String firstMetric = data.get("first_metric");
        String operator = data.get("operator");
        String secondMetric = data.get("second_metric");
        String metricToSelect = data.get("metric_to_select");
        String targetType = data.get("target_type");
        String[] customTargets = data.get("custom_target_values").split(",");
        String timeBasedStart = data.get("time_based_start_value");
        String timeBasedTarget = data.get("time_based_target_value");

        // Step 1: Navigate to the Application URL.
        log("Step 1: Navigating to " + baseUrl);
        driver.get(baseUrl);
        assertUrlContains("Login");

        // Step 2: Enter email and continue.
        log("Step 2: Entering email and clicking continue.");
        sendKeysToElement(EMAIL_INPUT, email);
        clickElement(CONTINUE_BUTTON);

        // Step 3: Enter password and login.
        log("Step 3: Entering password and clicking login.");
        sendKeysToElement(PASSWORD_INPUT, password);
        clickElement(LOGIN_BUTTON);

        // Step 4: Select the company.
        log("Step 4: Selecting company: " + companyName);
        WebElement companyLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '" + companyName + "')]")));
        companyLink.click();

        // Step 5: Verify dashboard page is displayed.
        log("Step 5: Verifying dashboard is displayed.");
        waitForPageLoad();
        assertUrlContains("Dashboard");

        // Step 6: Click on 'Metrics' in the main navigation menu.
        log("Step 6: Navigating to Metrics page.");
        clickElement(METRICS_LINK);
        waitForPageLoad();
        assertUrlContains("Metric");

        // Step 7: Click the 'Add Metric' button.
        log("Step 7: Clicking 'Add Metric' button.");
        clickElement(ADD_METRIC_BUTTON);
        wait.until(ExpectedConditions.urlContains("Metric/Add"));

        // Step 8: Enter the metric name.
        log("Step 8: Entering metric name: " + metricName);
        sendKeysToElement(NAME_INPUT, metricName);

        // Step 9: Select 'Value Source'.
        log("Step 9: Selecting Value Source: " + valueSource);
        clickElement(VALUE_SOURCE_DROPDOWN);
        WebElement valueSourceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + valueSource + "']")));
        valueSourceOption.click();
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            log("Step 10-14: Building formula.");
            // Step 11: Search for the first metric.
            sendKeysToElement(FORMULA_SEARCH_METRIC_INPUT, firstMetric);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(), '" + firstMetric + "')]"))).click();
            sleep(ACTION_PAUSE_MS);
            // Step 12: Click on the operator.
            clickElement(new String[]{String.format(FORMULA_OPERATOR_BUTTON[0], operator)});
            sleep(ACTION_PAUSE_MS);
            // Step 13: Search for the second metric.
            sendKeysToElement(FORMULA_SEARCH_METRIC_INPUT, secondMetric);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(), '" + secondMetric + "')]"))).click();
            sleep(ACTION_PAUSE_MS);
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            log("Step 10-ALT: Selecting existing metric.");
            sendKeysToElement(METRIC_SEARCH_INPUT, metricToSelect);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(), '" + metricToSelect + "')]"))).click();
            sleep(ACTION_PAUSE_MS);
        }

        // Step 15: Select 'Cadence'.
        log("Step 15: Selecting Cadence: " + cadence);
        clickElement(CADENCE_DROPDOWN);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + cadence + "']"))).click();
        sleep(ACTION_PAUSE_MS);

        // Step 16: Select 'Resets On' if cadence is Weekly.
        if ("Weekly".equalsIgnoreCase(cadence)) {
            log("Step 16: Selecting Resets On: " + resetsOn);
            clickElement(RESETS_ON_DROPDOWN);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + resetsOn + "']"))).click();
            sleep(ACTION_PAUSE_MS);
        }

        // Step 17: Confirm the formula.
        if ("Formula".equalsIgnoreCase(valueSource)) {
            log("Step 17: Confirming formula.");
            clickElement(FORMULA_CONFIRM_BUTTON);
            sleep(ACTION_PAUSE_MS);
        }

        // Step 18: Click the 'Save' button.
        log("Step 18: Saving the new metric.");
        clickElement(SAVE_BUTTON);
        waitForPageLoad();
        assertUrlContains("/Metric");
        Assert.assertTrue(driver.getPageSource().contains(metricName), "New metric not found in the list.");

        // Step 19: Navigate to 'My Dashboard'.
        log("Step 19: Navigating to My Dashboard.");
        clickElement(MY_DASHBOARD_LINK);
        waitForPageLoad();
        assertUrlContains("Dashboard/My");

        // Step 20: Click the 'Edit KPI' icon.
        log("Step 20: Clicking 'Edit KPI' icon.");
        clickElement(EDIT_KPI_ICON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editKpiModal")));

        // Step 21-22: Locate and add the new metric to the dashboard.
        log("Step 21-22: Adding new metric to dashboard.");
        WebElement metricInModal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='availableMetrics']//div[contains(text(), '" + metricName + "')]")));
        metricInModal.click();
        sleep(ACTION_PAUSE_MS);

        // Step 23: Click the 'Save' button in the modal.
        log("Step 23: Saving KPI configuration.");
        WebElement saveKpiButton = driver.findElement(By.xpath("//div[@id='editKpiModal']//button[contains(text(), 'Save')]"));
        saveKpiButton.click();

        // Step 24-25: Wait for refresh and locate the new metric card.
        log("Step 24-25: Locating new metric card on dashboard.");
        waitForPageLoad();
        String metricCardXPath = String.format("//div[contains(@class, 'kpi-card-container')]//span[contains(text(), '%s')]/ancestor::div[contains(@class, 'kpi-card-container')]", metricName);
        List<WebElement> metricCards = wait.until(ExpectedConditions.numberOfMoreThan(By.xpath(metricCardXPath), 0));
        WebElement newMetricCard = metricCards.get(metricCards.size() - 1); // Get the last one, assuming it's the newest
        Assert.assertTrue(newMetricCard.isDisplayed(), "Metric card is not visible on the dashboard.");

        // If no target is to be set, the test ends here for this path.
        if ("None".equalsIgnoreCase(targetType)) {
            log("Step 19 (TC003): Verifying metric card without target.");
            Assert.assertTrue(newMetricCard.isDisplayed(), "Metric card for TC003 is not displayed correctly.");
            return;
        }

        // Step 26-29: Hover, click three-dot menu, and click 'Edit'.
        log("Step 26-29: Opening edit page for the metric from dashboard.");
        Actions actions = new Actions(driver);
        actions.moveToElement(newMetricCard).perform();
        WebElement threeDotMenu = wait.until(ExpectedConditions.elementToBeClickable(newMetricCard.findElement(By.xpath(".//span[contains(@class, 'ico-threeDots1')]"))));
        threeDotMenu.click();
        WebElement editOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@title='Edit']")));
        editOption.click();

        // Step 30-31: Wait for Edit page and select 'Target' tab.
        log("Step 30-31: Waiting for edit page and selecting Target tab.");
        waitForPageLoad();
        assertUrlContains("Metric/Edit");
        clickElement(TARGET_TAB);
        sleep(ACTION_PAUSE_MS);

        if ("Custom".equalsIgnoreCase(targetType)) {
            log("Step 32-33: Configuring Custom Target.");
            // Step 32: Select the 'Custom' radio button.
            jsClick(waitClickable(CUSTOM_RADIO_BUTTON));
            sleep(ACTION_PAUSE_MS);
            // Step 33: Enter the Level 1-4 target values.
            sendKeysToElement(CUSTOM_TARGET_LEVEL_1_INPUT, customTargets[0].trim());
            sendKeysToElement(CUSTOM_TARGET_LEVEL_2_INPUT, customTargets[1].trim());
            sendKeysToElement(CUSTOM_TARGET_LEVEL_3_INPUT, customTargets[2].trim());
            sendKeysToElement(CUSTOM_TARGET_LEVEL_4_INPUT, customTargets[3].trim());
        } else if ("Time-Based".equalsIgnoreCase(targetType)) {
            log("Step 22-24 (TC002): Configuring Time-Based Target.");
            // Step 22: Select the 'Time-Based' radio button.
            jsClick(waitClickable(TIME_BASED_RADIO_BUTTON));
            sleep(ACTION_PAUSE_MS);
            // Step 23-24: Enter Start and Target values.
            sendKeysToElement(TIME_BASED_START_INPUT, timeBasedStart);
            sendKeysToElement(TIME_BASED_TARGET_INPUT, timeBasedTarget);
        }

        // Step 34: Click the 'Save' button.
        log("Step 34: Saving target configuration.");
        clickElement(SAVE_BUTTON);

        // Step 35-36: Verify the metric card on the dashboard displays the target.
        log("Step 35-36: Verifying target indicators on the metric card.");
        waitForPageLoad();
        assertUrlContains("Dashboard/My");
        WebElement updatedMetricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXPath)));
        if ("Custom".equalsIgnoreCase(targetType)) {
            Assert.assertTrue(updatedMetricCard.findElement(By.xpath(".//*[contains(@class, 'target-graph')]")).isDisplayed(), "Custom target graph not visible.");
        } else if ("Time-Based".equalsIgnoreCase(targetType)) {
            Assert.assertTrue(updatedMetricCard.findElement(By.xpath(".//*[contains(@class, 'progress-bar')]")).isDisplayed(), "Time-based progress bar not visible.");
        }
    }

    // ========== SETUP AND CLEANUP ==========
    @BeforeClass
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe"); // Uncomment and set path if not in system PATH
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AlignTestDataDrivenTest test = new AlignTestDataDrivenTest();
        try {
            test.setUp();
            test.testDataDrivenWorkflow();
        } catch (Exception e) {
            System.err.println("An error occurred during test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}