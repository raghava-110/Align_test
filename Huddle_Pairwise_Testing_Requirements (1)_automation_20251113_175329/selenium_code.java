package com.selenium.dataprovider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class HuddlePairWiseTest {

    // ========== LOCATORS - GENERATED BASED ON REQUIREMENTS AND MAPPINGS ==========
    private static final String[] emailInputLocators = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//input[contains(@aria-label, 'Email') or contains(@aria-label, 'email')]",
        "//label[contains(.,'Email')]/following-sibling::input",
        "//span[contains(@class,'k-widget')]//input[@id='usernameField']"
    };

    private static final String[] continueButtonLocators = {
        "//button[normalize-space()='Continue']",
        "//input[@value='Continue']",
        "//a[normalize-space()='Continue']",
        "//button[contains(., 'Continue')]",
        "//span[normalize-space()='Continue']/ancestor::button[1]",
        "//button[@type='submit']"
    };

    private static final String[] passwordInputLocators = {
        "//input[@type='password']",
        "//input[@name='password']",
        "//input[contains(@id, 'password') or contains(@id, 'Password')]",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//input[contains(@aria-label, 'Password') or contains(@aria-label, 'password')]",
        "//label[contains(.,'Password')]/following-sibling::input"
    };

    private static final String[] loginButtonLocators = {
        "//button[normalize-space()='Login']",
        "//button[normalize-space()='Sign In']",
        "//input[@value='Login']",
        "//input[@value='Sign In']",
        "//button[contains(., 'Login') or contains(., 'Sign In')]",
        "//span[normalize-space()='Login']/ancestor::button[1]",
        "//button[@type='submit'][not(contains(.,'Continue'))]"
    };

    private static final String[] huddlesMenuLocators = {
        "//input[@id='navigation-header-dropdown']",
        "//a[normalize-space()='Huddles']",
        "//span[normalize-space()='Huddles']",
        "//div[contains(@class, 'nav')]//*[normalize-space()='Huddles']",
        "//button[contains(., 'Huddles')]",
        "//input[contains(@data-bind,'value: headerNavigation')]"
    };

    private static final String[] addTaskButtonLocators = {
        "//button[normalize-space()='Add Task']",
        "//a[normalize-space()='Add Task']",
        "//button[contains(., 'Add Task')]",
        "//span[normalize-space()='Add Task']/ancestor::button[1]",
        "//i[contains(@class, 'plus')]/ancestor::button[contains(., 'Task')]",
        "//button[@aria-label='Add Task']"
    };

    private static final String[] taskNameInputLocators = {
        "//input[@data-bind='value: promoCode.code']", // From mappings, likely incorrect but included
        "//textarea[contains(@placeholder, 'Task')]",
        "//input[contains(@placeholder, 'Task')]",
        "//label[contains(.,'Task')]/following-sibling::textarea",
        "//label[contains(.,'Task')]/following-sibling::input",
        "//input[@name='taskName']",
        "//div[contains(@class, 'task-modal')]//input[@type='text']"
    };

    private static final String[] calendarIconLocators = {
        "//span[@title='Calendar']",
        "//span[contains(@class,'icon') and contains(@class,'calendar')]",
        "//i[contains(@class, 'calendar')]",
        "//button[@aria-label='Open calendar']",
        "//button[contains(., 'Date')]"
    };

    private static final String[] saveTaskButtonLocators = {
        "//button[normalize-space()='Save']",
        "//input[@value='Save']",
        "//button[contains(., 'Save')]",
        "//div[contains(@class, 'modal-footer')]//button[normalize-space()='Save']",
        "//button[@type='submit'][contains(., 'Save')]"
    };

    private static final String[] myUpdatesSectionLocators = {
        "//h2[normalize-space()='My Updates']",
        "//h3[normalize-space()='My Updates']",
        "//div[contains(@class, 'section-header') and contains(., 'My Updates')]",
        "//div[@id='my-updates-section']"
    };

    private static final String[] topPriorityInputLocators = {
        "//h3[normalize-space()='Top Priority']/following-sibling::div[@contenteditable='true']",
        "//label[normalize-space()='Top Priority']/following-sibling::div[@contenteditable='true']",
        "//div[contains(@class, 'my-updates')]//div[@contenteditable='true']",
        "//div[@aria-label='Top Priority']",
        "//div[@role='textbox'][contains(@aria-label, 'Priority')]"
    };

    private static final String[] profileMenuLocators = {
        "//div[contains(@class, 'profile-menu')]",
        "//button[contains(@aria-label, 'profile') or contains(@aria-label, 'user')]",
        "//img[contains(@alt, 'avatar') or contains(@alt, 'profile')]",
        "//div[contains(@class, 'user-menu-trigger')]",
        "//button[@id='profile-menu-btn']"
    };

    private static final String[] signOutButtonLocators = {
        "//a[normalize-space()='Sign Out']",
        "//button[normalize-space()='Sign Out']",
        "//li[normalize-space()='Sign Out']",
        "//span[normalize-space()='Sign Out']/ancestor::button[1]",
        "//div[contains(@class, 'dropdown-menu')]//*[normalize-space()='Sign Out']",
        "//a[contains(@href, 'logout')]"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "path/to/your/test-data.xlsx";
    // ==================================================
    private static final String BASE_URL = "[Application URL]";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 50;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    // ========== CLASS VARIABLES ==========
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // ========== EXCEL READER UTILITY ==========
    public static class ExcelReader {
        public static List<Map<String, String>> readExcel(String filePath) {
            List<Map<String, String>> testData = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(new File(filePath));
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) return testData;

                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(getCellValueAsString(cell));
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row currentRow = sheet.getRow(i);
                    if (currentRow == null) continue;

                    Map<String, String> rowData = new LinkedHashMap<>();
                    boolean isRowEmpty = true;
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String cellValue = (cell == null) ? "" : getCellValueAsString(cell);
                        rowData.put(headers.get(j), cellValue);
                        if (!cellValue.isEmpty()) {
                            isRowEmpty = false;
                        }
                    }
                    if (!isRowEmpty) {
                        testData.add(rowData);
                    }
                }
            } catch (IOException e) {
                System.err.println("ERROR: Failed to read Excel file at " + filePath);
                e.printStackTrace();
            }
            return testData;
        }

        private static String getCellValueAsString(Cell cell) {
            if (cell == null) {
                return "";
            }
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue());
                    }
                    double num = cell.getNumericCellValue();
                    if (num == Math.floor(num)) {
                        return String.valueOf((long) num);
                    }
                    return String.valueOf(num);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // Fallback for formula resulting in non-string
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // ========== HELPER METHODS ==========
    private void log(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("WARN: Sleep interrupted.");
        }
    }

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return driver.findElement(By.xpath(xpath));
            } catch (Exception e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("ERROR: Element not found with any of the provided locators. First locator tried: " + xpaths[0]);
    }

    private WebElement waitVisible(String[] xpaths) {
        return wait.until(driver -> findElementWithFallbacks(xpaths));
    }

    private WebElement waitClickable(String[] xpaths) {
        WebElement element = waitVisible(xpaths);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) {
            log("WARN: Skipping sendKeys as text is null or empty.");
            return;
        }
        WebElement element = waitVisible(xpaths);
        element.clear();
        element.sendKeys(text);
    }
    
    private void typeSlowly(WebElement element, String text) {
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void clickElement(String[] xpaths) {
        WebElement element = waitClickable(xpaths);
        try {
            element.click();
        } catch (Exception e) {
            log("WARN: Standard click failed. Retrying with JavaScript click.");
            jsClick(element);
        }
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }


    private void selectCompanyByName(String companyName) {
        log("Attempting to select company: " + companyName);
        try {
            String companySpanXpath = String.format("//span[normalize-space()='%s']", companyName);
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

            // Try to find a clickable parent card/div
            String[] parentSelectors = {
                "/ancestor::div[contains(@class, 'card') or contains(@class, 'item')][1]",
                "/ancestor::a[1]",
                "/ancestor::button[1]",
                "/.."
            };

            for (String selector : parentSelectors) {
                try {
                    WebElement parent = companySpan.findElement(By.xpath("." + selector));
                    log("Found clickable parent for company. Clicking it.");
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", parent);
                    sleep(500);
                    jsClick(parent);
                    waitForPageLoad();
                    return;
                } catch (Exception e) {
                    // Parent not found, try next selector
                }
            }

            // Fallback to clicking the span directly
            log("WARN: Could not find a preferred parent element. Clicking the company name span directly.");
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", companySpan);
            sleep(500);
            jsClick(companySpan);
            waitForPageLoad();

        } catch (Exception e) {
            log("WARN: Could not select company '" + companyName + "'. The test might fail if this is a required step. " + e.getMessage());
        }
    }

    private String getOrDefault(Map<String, String> map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("ERROR: No test data found in the Excel file or the file could not be read.");
            return;
        }

        for (int i = 0; i < testData.size(); i += 2) {
            if (i + 1 >= testData.size()) {
                log("WARN: Skipping last record as it does not have a pair: " + testData.get(i));
                continue;
            }
            Map<String, String> user1Data = testData.get(i);
            Map<String, String> user2Data = testData.get(i + 1);

            log("\n--- STARTING TEST CASE: E2E Pair-Wise Verification ---");
            log("User Pair: " + getOrDefault(user1Data, "Email", "N/A") + " & " + getOrDefault(user2Data, "Email", "N/A"));

            try {
                executePairWiseWorkflow(user1Data, user2Data);
                log("--- TEST CASE PASSED for User Pair ---\n");
            } catch (Exception e) {
                log("--- !!! TEST CASE FAILED for User Pair !!! ---");
                log("ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void executePairWiseWorkflow(Map<String, String> user1Data, Map<String, String> user2Data) {
        // Extract all data upfront
        String user1Email = getOrDefault(user1Data, "Email", "");
        String user1Pass = getOrDefault(user1Data, "Password", "");
        String user1Task = getOrDefault(user1Data, "TaskName", "");
        String user1Date = getOrDefault(user1Data, "DueDate", "");
        String user1Prio = getOrDefault(user1Data, "TopPriority", "");
        String user1Huddle = getOrDefault(user1Data, "huddleName", "");
        String user1Company = getOrDefault(user1Data, "Company_Name", "");

        String user2Email = getOrDefault(user2Data, "Email", "");
        String user2Pass = getOrDefault(user2Data, "Password", "");
        String user2Task = getOrDefault(user2Data, "TaskName", "");
        String user2Date = getOrDefault(user2Data, "DueDate", "");
        String user2Prio = getOrDefault(user2Data, "TopPriority", "");
        String user2Huddle = getOrDefault(user2Data, "huddleName", "");
        String user2Company = getOrDefault(user2Data, "Company_Name", "");

        // ==================================================================
        // PHASE 1: First User (User1) Login and Setup
        // ==================================================================
        log("PHASE 1: User1 (" + user1Email + ") logs in and creates items.");

        // Step 1: Navigate to Application
        log("Step 1: Navigating to application URL.");
        driver.get(BASE_URL);
        waitForPageLoad();

        // Step 2: Enter Email Address
        log("Step 2: Entering User1's email and clicking Continue.");
        sendKeysToElement(emailInputLocators, user1Email);
        clickElement(continueButtonLocators);

        // Step 3: Enter Password
        log("Step 3: Entering User1's password and clicking Login.");
        sendKeysToElement(passwordInputLocators, user1Pass);
        clickElement(loginButtonLocators);
        waitForPageLoad();

        // Step 4: Select Company
        log("Step 4: Selecting company '" + user1Company + "'.");
        selectCompanyByName(user1Company);

        // Step 5: Navigate to Huddles
        log("Step 5: Clicking Huddles menu.");
        clickElement(huddlesMenuLocators);
        sleep(ACTION_PAUSE_MS);

        // Step 6: Select Huddle
        log("Step 6: Selecting huddle '" + user1Huddle + "'.");
        String huddleXpath = String.format("//li[normalize-space()='%s'] | //div[normalize-space()='%s'] | //a[normalize-space()='%s']", user1Huddle, user1Huddle, user1Huddle);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(huddleXpath))).click();
        waitForPageLoad();

        // Step 7: Add Task
        log("Step 7: Clicking 'Add Task' and entering task name '" + user1Task + "'.");
        clickElement(addTaskButtonLocators);
        sendKeysToElement(taskNameInputLocators, user1Task);

        // Step 8: Set Due Date
        log("Step 8: Setting due date to '" + user1Date + "'.");
        clickElement(calendarIconLocators);
        sleep(500);
        String day = user1Date.split("/")[1];
        String dayXpath = String.format("//td[not(contains(@class, 'k-other-month'))]/a[normalize-space()='%s']", day);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

        // Step 9: Save Task
        log("Step 9: Clicking Save button for the task.");
        clickElement(saveTaskButtonLocators);
        sleep(PAGE_SETTLE_MS);
        log("VERIFY: Task '" + user1Task + "' should be visible.");
        String taskVerifyXpath = String.format("//*[contains(text(), '%s')]", user1Task);
        waitVisible(new String[]{taskVerifyXpath});

        // Step 10-12: Add Top Priority
        log("Step 10-12: Adding Top Priority '" + user1Prio + "'.");
        WebElement myUpdates = waitVisible(myUpdatesSectionLocators);
        js.executeScript("arguments[0].scrollIntoView(true);", myUpdates);
        WebElement priorityInput = waitClickable(topPriorityInputLocators);
        priorityInput.click();
        priorityInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE); // Clear field
        typeSlowly(priorityInput, user1Prio);
        myUpdates.click(); // Click outside to trigger auto-save
        sleep(3000); // Wait for auto-save
        log("VERIFY: Priority '" + user1Prio + "' should be visible.");
        String prioVerifyXpath = String.format("//*[contains(text(), '%s')]", user1Prio);
        waitVisible(new String[]{prioVerifyXpath});

        // Step 13: Logout
        log("Step 13: Logging out User1.");
        js.executeScript("window.scrollTo(0, 0);");
        clickElement(profileMenuLocators);
        sleep(500);
        clickElement(signOutButtonLocators);
        waitForPageLoad();
        driver.manage().deleteAllCookies();

        // ==================================================================
        // PHASE 2: Second User (User2) Login and Verification
        // ==================================================================
        log("PHASE 2: User2 (" + user2Email + ") logs in, verifies User1's items, and creates own items.");

        // Step 14: Login Second User
        log("Step 14: Logging in as User2.");
        driver.get(BASE_URL); // Navigate again for clean session
        sendKeysToElement(emailInputLocators, user2Email);
        clickElement(continueButtonLocators);
        sendKeysToElement(passwordInputLocators, user2Pass);
        clickElement(loginButtonLocators);
        waitForPageLoad();
        selectCompanyByName(user2Company);
        clickElement(huddlesMenuLocators);
        sleep(ACTION_PAUSE_MS);
        String huddle2Xpath = String.format("//li[normalize-space()='%s'] | //div[normalize-space()='%s'] | //a[normalize-space()='%s']", user2Huddle, user2Huddle, user2Huddle);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(huddle2Xpath))).click();
        waitForPageLoad();

        // Step 15: Verify Current User Position (Implicitly verified by being on their page)
        log("Step 15: Verifying User2 is on their own page.");
        waitVisible(myUpdatesSectionLocators);

        // Step 16: View First User Updates
        log("Step 16: Clicking on User1 in the members panel to view their updates.");
        String user1NameInPanel = user1Email.split("@")[0]; // Assuming name is derived from email
        String user1PanelXpath = String.format("//div[contains(@class, 'member-list')]//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", user1NameInPanel.toLowerCase());
        clickElement(new String[]{user1PanelXpath});
        sleep(PAGE_SETTLE_MS);

        // Step 17: Verify Task Visible
        log("Step 17: Verifying User1's task is visible.");
        String user1TaskVerifyXpath = String.format("//*[contains(text(), '%s')]", user1Task);
        waitVisible(new String[]{user1TaskVerifyXpath});

        // Step 18: Verify Top Priority Visible
        log("Step 18: Verifying User1's priority is visible.");
        String user1PrioVerifyXpath = String.format("//*[contains(text(), '%s')]", user1Prio);
        waitVisible(new String[]{user1PrioVerifyXpath});

        // Step 19-20: Return to Current User and Verify
        log("Step 19-20: Returning to User2's view.");
        String user2NameInPanel = user2Email.split("@")[0];
        String user2PanelXpath = String.format("//div[contains(@class, 'member-list')]//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", user2NameInPanel.toLowerCase());
        clickElement(new String[]{user2PanelXpath});
        sleep(PAGE_SETTLE_MS);
        waitVisible(myUpdatesSectionLocators); // Confirms it's current user's view

        // Step 21: Add Own Task
        log("Step 21: User2 adds their own task '" + user2Task + "'.");
        clickElement(addTaskButtonLocators);
        sendKeysToElement(taskNameInputLocators, user2Task);
        clickElement(calendarIconLocators);
        sleep(500);
        String day2 = user2Date.split("/")[1];
        String day2Xpath = String.format("//td[not(contains(@class, 'k-other-month'))]/a[normalize-space()='%s']", day2);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(day2Xpath))).click();
        clickElement(saveTaskButtonLocators);
        sleep(PAGE_SETTLE_MS);
        String task2VerifyXpath = String.format("//*[contains(text(), '%s')]", user2Task);
        waitVisible(new String[]{task2VerifyXpath});

        // Step 22: Add Own Priority
        log("Step 22: User2 adds their own priority '" + user2Prio + "'.");
        WebElement myUpdates2 = waitVisible(myUpdatesSectionLocators);
        js.executeScript("arguments[0].scrollIntoView(true);", myUpdates2);
        WebElement priorityInput2 = waitClickable(topPriorityInputLocators);
        priorityInput2.click();
        priorityInput2.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        typeSlowly(priorityInput2, user2Prio);
        myUpdates2.click();
        sleep(3000);
        String prio2VerifyXpath = String.format("//*[contains(text(), '%s')]", user2Prio);
        waitVisible(new String[]{prio2VerifyXpath});

        // Step 23: Logout
        log("Step 23: Logging out User2.");
        js.executeScript("window.scrollTo(0, 0);");
        clickElement(profileMenuLocators);
        sleep(500);
        clickElement(signOutButtonLocators);
        waitForPageLoad();
        driver.manage().deleteAllCookies();

        // ==================================================================
        // PHASE 3: First User Re-login and Cross-Verification
        // ==================================================================
        log("PHASE 3: User1 (" + user1Email + ") logs back in to verify User2's items.");

        // Step 24: Re-login First User
        log("Step 24: Re-logging in as User1.");
        driver.get(BASE_URL);
        sendKeysToElement(emailInputLocators, user1Email);
        clickElement(continueButtonLocators);
        sendKeysToElement(passwordInputLocators, user1Pass);
        clickElement(loginButtonLocators);
        waitForPageLoad();
        selectCompanyByName(user1Company);
        clickElement(huddlesMenuLocators);
        sleep(ACTION_PAUSE_MS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(huddleXpath))).click();
        waitForPageLoad();

        // Step 25: View Second User Updates
        log("Step 25: Clicking on User2 in the members panel.");
        clickElement(new String[]{user2PanelXpath});
        sleep(PAGE_SETTLE_MS);

        // Step 26: Verify Task Created
        log("Step 26: Verifying User2's task is visible.");
        waitVisible(new String[]{task2VerifyXpath});

        // Step 27: Verify Priority Created
        log("Step 27: Verifying User2's priority is visible.");
        waitVisible(new String[]{prio2VerifyXpath});

        // Step 28: Logout
        log("Step 28: Logging out User1.");
        js.executeScript("window.scrollTo(0, 0);");
        clickElement(profileMenuLocators);
        sleep(500);
        clickElement(signOutButtonLocators);
        waitForPageLoad();
        driver.manage().deleteAllCookies();
    }

    // ========== SETUP AND TEARDOWN ==========
    public void setUp() {
        log("Setting up WebDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    public void cleanup() {
        log("Cleaning up WebDriver...");
        if (driver != null) {
            driver.quit();
        }
    }

    // ========== MAIN METHOD ==========
    public static void main(String[] args) {
        HuddlePairWiseTest test = new HuddlePairWiseTest();
        try {
            test.setUp();
            test.testDataDrivenWorkflow();
        } catch (Exception e) {
            System.err.println("FATAL ERROR: An unexpected error occurred during the test execution.");
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}