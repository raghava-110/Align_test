package com.task.automation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskCreationDataDrivenTest {

    // ========== LOCATORS - Generated with Fallback Strategies ==========
    private static final String[] EMAIL_INPUT_LOCATORS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//label[normalize-space()='Email']/following-sibling::input",
        "//input[contains(@data-bind,'value: username')]",
        "//span[contains(@class,'k-widget')]//input[@id='usernameField']"
    };
    private static final String[] CONTINUE_BUTTON_LOCATORS = {
        "//button[normalize-space()='Continue']",
        "//input[@value='Continue']",
        "//a[normalize-space()='Continue']",
        "//button[contains(.,'Continue')]",
        "//span[normalize-space()='Continue']/ancestor::button",
        "//button[@id='continue-button']",
        "//button[@type='submit']"
    };
    private static final String[] PASSWORD_INPUT_LOCATORS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//label[normalize-space()='Password']/following-sibling::input",
        "//input[contains(@data-bind,'value: password')]",
        "//span[contains(@class,'k-widget')]//input[@id='passwordField']"
    };
    private static final String[] LOGIN_BUTTON_LOCATORS = {
        "//button[normalize-space()='Login']",
        "//button[normalize-space()='Log In']",
        "//input[@value='Login']",
        "//button[@type='submit']",
        "//button[contains(.,'Login') or contains(.,'Sign In')]",
        "//span[normalize-space()='Login']/ancestor::button",
        "//button[@id='login-button']"
    };
    private static final String[] ACTION_ITEMS_MENU_LOCATORS = {
        "//a[normalize-space()='Action Items']",
        "//span[normalize-space()='Action Items']/ancestor::a",
        "//li/a[contains(.,'Action Items')]",
        "//div[@role='navigation']//*[normalize-space()='Action Items']",
        "//input[@id='navigation-header-dropdown']" // Fallback to dropdown if text fails
    };
    private static final String[] TASKS_SUBMENU_LOCATORS = {
        "//a[normalize-space()='Tasks']",
        "//span[normalize-space()='Tasks']/ancestor::a",
        "//ul[contains(@class,'submenu') or contains(@style,'display: block')]//a[normalize-space()='Tasks']",
        "//li/a[contains(.,'Tasks')]",
        "//div[@role='menu']//*[normalize-space()='Tasks']"
    };
    private static final String[] NEW_TASK_BUTTON_LOCATORS = {
        "//button[normalize-space()='New Task']",
        "//a[normalize-space()='New Task']",
        "//button[contains(.,'New Task')]",
        "//span[normalize-space()='New Task']/ancestor::button",
        "//a[contains(@class,'btn') and contains(.,'New Task')]",
        "//button[@id='new-task-btn' or @id='btnNewTask']"
    };
    private static final String[] SHORT_TASK_NAME_INPUT_LOCATORS = {
        "//input[@data-bind='value: promoCode.code']", // From mappings, likely incorrect but kept for fidelity
        "//label[contains(text(),'Short Task Name')]/following-sibling::input",
        "//input[@placeholder='Short Task Name']",
        "//input[contains(@id,'taskName') or contains(@name,'taskName')]",
        "//textarea[contains(@id,'taskName') or contains(@name,'taskName')]",
        "//div[label[contains(text(),'Short Task Name')]]//input"
    };
    private static final String[] DUE_DATE_INPUT_LOCATORS = {
        "//label[contains(text(),'Due Date')]/following-sibling::div//input",
        "//input[@placeholder='MM/DD/YYYY']",
        "//input[contains(@class, 'datepicker') or contains(@class, 'date-picker')]",
        "//input[contains(@id,'dueDate') or contains(@name,'dueDate')]",
        "//span[contains(@class,'k-datepicker')]//input"
    };
    private static final String[] ASSIGNED_TO_INPUT_LOCATORS = {
        "//input[@placeholder='Search or Invite Team Members']",
        "//input[@id='companyMetricOwner']",
        "//label[contains(text(),'Assigned To')]/..//input",
        "//div[contains(@class,'assignee-selector')]//input",
        "//input[contains(@data-bind,'value: owner')]",
        "//input[contains(@placeholder,'Search or ')]"
    };
    private static final String[] ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Users']/preceding-sibling::input[@type='checkbox']",
        "//input[@type='checkbox']/following-sibling::label[normalize-space()='Assign to all Users']",
        "//div[contains(., 'Assign to all Users')]//input[@type='checkbox']",
        "//input[@name='assignAllUsers']"
    };
    private static final String[] ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Admins']/preceding-sibling::input[@type='checkbox']",
        "//input[@type='checkbox']/following-sibling::label[normalize-space()='Assign to all Admins']",
        "//div[contains(., 'Assign to all Admins')]//input[@type='checkbox']",
        "//input[@name='assignAllAdmins']"
    };
    private static final String[] ALIGN_PRIORITY_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Priorities']",
        "//label[contains(text(),'Priority')]/..//input",
        "//div[contains(@class,'priority-alignment')]//input",
        "//input[contains(@id,'priority') or contains(@name,'priority')]"
    };
    private static final String[] ALIGN_HUDDLE_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Huddles']",
        "//label[contains(text(),'Huddle')]/..//input",
        "//div[contains(@class,'huddle-alignment')]//input",
        "//input[contains(@id,'huddle') or contains(@name,'huddle')]"
    };
    private static final String[] ADD_DOCUMENT_ICON_LOCATORS = {
        "//h3[normalize-space()='Documents']/..//button[contains(@class, 'add') or contains(.,'+')]",
        "//div[contains(@class,'documents-section')]//i[contains(@class, 'plus') or contains(@class, 'add')]",
        "//a[@title='Add Document']",
        "//span[normalize-space()='Documents']/following-sibling::button"
    };
    private static final String[] DOCUMENT_NAME_INPUT_LOCATORS = {
        "//input[@placeholder='Enter document name']",
        "//div[@role='dialog']//label[contains(text(),'Document Name')]/..//input",
        "//input[contains(@id,'docName') or contains(@name,'docName')]"
    };
    private static final String[] DOCUMENT_DESCRIPTION_INPUT_LOCATORS = {
        "//textarea[@placeholder='Description']",
        "//div[@role='dialog']//label[contains(text(),'Description')]/..//textarea",
        "//textarea[contains(@id,'docDesc') or contains(@name,'docDesc')]"
    };
    private static final String[] DOCUMENT_TYPE_DROPDOWN_LOCATORS = {
        "//label[contains(text(),'Document Type')]/..//select",
        "//label[contains(text(),'Document Type')]/..//input[contains(@class, 'dropdown')]",
        "//div[@role='dialog']//span[contains(@class,'k-dropdown')]"
    };
    private static final String[] DOCUMENT_URL_INPUT_LOCATORS = {
        "//input[@placeholder='Enter Link...']",
        "//div[@role='dialog']//label[contains(text(),'URL')]/..//input",
        "//input[contains(@id,'docUrl') or contains(@name,'docUrl')]"
    };
    private static final String[] HIDDEN_FILE_INPUT_LOCATORS = {
        "//input[@type='file']",
        "//div[contains(@class,'file-upload')]//input[@type='file']",
        "//form[contains(@enctype,'multipart/form-data')]//input[@type='file']"
    };
    private static final String[] DOCUMENT_SAVE_BUTTON_LOCATORS = {
        "//div[contains(@class,'dialog') or @role='dialog']//button[normalize-space()='Save']",
        "//div[contains(@class,'modal-footer')]//button[contains(text(),'Save')]"
    };
    private static final String[] MAIN_SAVE_BUTTON_LOCATORS = {
        "//div[not(contains(@class,'dialog')) and not(@role='dialog')]//button[@type='submit'][normalize-space()='Save']",
        "//div[@class='form-actions']//button[contains(text(),'Save')]",
        "//button[normalize-space()='Save']",
        "//input[@value='Save']"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/testdata.xlsx";
    // ==================================================
    private static final String FILE_UPLOAD_BASE_PATH = "C:\\Users\\sudhi\\Downloads\\";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;
    private static final int DROPDOWN_WAIT_MS = 2500;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    // ========== EXCEL READER UTILITY ==========
    public static class ExcelReader {
        public static List<Map<String, String>> readExcel(String filePath) {
            List<Map<String, String>> testData = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(new File(filePath));
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                if (!rowIterator.hasNext()) return testData;

                Row headerRow = rowIterator.next();
                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(getCellValueAsString(cell));
                }

                while (rowIterator.hasNext()) {
                    Row currentRow = rowIterator.next();
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        Cell currentCell = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        rowData.put(headers.get(i), getCellValueAsString(currentCell));
                    }
                    testData.add(rowData);
                }
            } catch (IOException e) {
                System.err.println("Error reading Excel file: " + filePath);
                e.printStackTrace();
            }
            return testData;
        }

        private static String getCellValueAsString(Cell cell) {
            if (cell == null) {
                return "";
            }
            DataFormatter formatter = new DataFormatter();
            String cellValue = formatter.formatCellValue(cell);
            // Handle numeric values that POI might format as decimals (e.g., 123.0)
            if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
                if (cellValue.endsWith(".0")) {
                    return cellValue.substring(0, cellValue.length() - 2);
                }
            }
            return cellValue.trim();
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
            log("Sleep interrupted");
        }
    }

    private WebElement findElementWithFallbacks(String[] locators) {
        for (String locator : locators) {
            try {
                return driver.findElement(By.xpath(locator));
            } catch (NoSuchElementException e) {
                // Ignore and try the next locator
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided locators. First locator tried: " + locators[0]);
    }

    private WebElement waitVisible(String[] locators) {
        return wait.until(driver -> findElementWithFallbacks(locators));
    }

    private WebElement waitClickable(String[] locators) {
        WebElement element = waitVisible(locators);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void sendKeysToElement(String[] locators, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(locators);
        element.clear();
        element.sendKeys(text);
    }

    private void clickElement(String[] locators) {
        WebElement element = waitClickable(locators);
        try {
            element.click();
        } catch (Exception e) {
            log("Standard click failed, trying JavaScript click.");
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

    private String getOrDefault(Map<String, String> map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    private void selectCompanyByName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            log("No company name provided, skipping company selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            String companySpanXpath = String.format("//span[normalize-space()='%s']", companyName);
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

            // Try to find a more clickable parent element
            WebElement clickableParent = null;
            try {
                clickableParent = companySpan.findElement(By.xpath("./ancestor::div[contains(@class,'card') or contains(@class,'item')][1]"));
            } catch (Exception e) {
                log("Could not find a standard clickable parent card for the company. Will try clicking the span directly.");
            }

            if (clickableParent != null) {
                log("Found clickable parent element. Clicking it.");
                clickableParent.click();
            } else {
                log("WARNING: Clicking company span directly. This might be unstable.");
                companySpan.click();
            }
            waitForPageLoad();
        } catch (Exception e) {
            log("ERROR: Could not select company '" + companyName + "'. Continuing with test. " + e.getMessage());
        }
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        log("Starting test execution...");
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("No test data found in Excel file. Exiting.");
            return;
        }
        log("Found " + testData.size() + " test case(s) to execute.");

        for (int i = 0; i < testData.size(); i++) {
            log("\n" + "=".repeat(50));
            log("Executing Record #" + (i + 1));
            log("=".repeat(50));
            try {
                executeWorkflow(testData.get(i));
            } catch (Exception e) {
                log("FATAL ERROR during execution of record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract all data from the map
        String baseUrl = getOrDefault(data, "Base_URL", "");
        String email = getOrDefault(data, "Email", "");
        String password = getOrDefault(data, "Password", "");
        String companyName = getOrDefault(data, "Company_Name", "");
        String taskName = getOrDefault(data, "Task_Name", "");
        String dueDate = getOrDefault(data, "Due_Date", "");
        String assignedTo = getOrDefault(data, "Assigned_To", "");
        String alignPriority = getOrDefault(data, "Align_Priority", "");
        String alignHuddle = getOrDefault(data, "Align_Huddle", "");
        String documentName = getOrDefault(data, "Document_Name", "");
        String documentDescription = getOrDefault(data, "Document_Description", "");
        String documentType = getOrDefault(data, "Document_Type", "");
        String documentFile = getOrDefault(data, "Document_File", "");
        String documentUrl = getOrDefault(data, "Document_URL", "");

        // PHASE 1: User Authentication (First Task Only)
        if (isFirstRecord) {
            log("Executing PHASE 1: User Authentication for the first record.");
            if (baseUrl.isEmpty()) {
                log("ERROR: Base_URL is missing for the first record. Cannot proceed.");
                return;
            }
            driver.get(baseUrl);
            waitForPageLoad();

            // Enter Email (Robust Pattern)
            try {
                log("Entering email: " + email);
                WebElement emailInput = waitVisible(EMAIL_INPUT_LOCATORS);
                emailInput.clear();
                sleep(500);
                emailInput.sendKeys(email);
                sleep(1000);
                String enteredValue = emailInput.getAttribute("value");
                if (!Objects.equals(enteredValue, email)) {
                    log("WARNING: Email not entered correctly, retrying character by character...");
                    emailInput.clear();
                    sleep(500);
                    for (char c : email.toCharArray()) {
                        emailInput.sendKeys(String.valueOf(c));
                        sleep(50);
                    }
                }
            } catch (Exception e) {
                log("ERROR: Failed to enter email. Page: " + driver.getCurrentUrl());
                throw new RuntimeException("Could not enter email", e);
            }

            // Click Continue
            log("Clicking Continue button.");
            clickElement(CONTINUE_BUTTON_LOCATORS);
            sleep(ACTION_PAUSE_MS);

            // Enter Password
            log("Entering password.");
            sendKeysToElement(PASSWORD_INPUT_LOCATORS, password);

            // Click Login
            log("Clicking Login button.");
            clickElement(LOGIN_BUTTON_LOCATORS);
            waitForPageLoad();

            // Select Company
            selectCompanyByName(companyName);

            // Navigate to Tasks Page
            log("Navigating to Tasks page...");
            clickElement(ACTION_ITEMS_MENU_LOCATORS);
            sleep(ACTION_PAUSE_MS);
            clickElement(TASKS_SUBMENU_LOCATORS);
            waitForPageLoad();
            log("Successfully navigated to Manage Tasks page.");
        } else {
            log("Skipping PHASE 1 (Login) for subsequent record.");
            try {
                waitVisible(NEW_TASK_BUTTON_LOCATORS);
                log("Confirmed user is on the Manage Tasks page.");
            } catch (Exception e) {
                log("WARNING: Not on Manage Tasks page. Attempting to re-navigate.");
                clickElement(ACTION_ITEMS_MENU_LOCATORS);
                sleep(ACTION_PAUSE_MS);
                clickElement(TASKS_SUBMENU_LOCATORS);
                waitForPageLoad();
            }
        }

        // PHASE 2: Create Task (Repeats for Each Record)
        log("Executing PHASE 2: Create Task for task: '" + taskName + "'");

        // Open Create Task Form
        log("Clicking 'New Task' button.");
        clickElement(NEW_TASK_BUTTON_LOCATORS);
        sleep(ACTION_PAUSE_MS);

        // Enter Task Name
        log("Entering task name: " + taskName);
        sendKeysToElement(SHORT_TASK_NAME_INPUT_LOCATORS, taskName);

        // Select Due Date
        log("Entering due date: " + dueDate);
        WebElement dateInput = waitVisible(DUE_DATE_INPUT_LOCATORS);
        dateInput.clear();
        dateInput.sendKeys(dueDate);
        dateInput.sendKeys(Keys.ENTER);
        sleep(500);

        // Assign Team Members
        if (!assignedTo.isEmpty()) {
            log("Processing assignment: " + assignedTo);
            WebElement mainForm = driver.findElement(By.tagName("body")); // For scrolling context
            js.executeScript("arguments[0].scrollIntoView(false);", waitVisible(ASSIGNED_TO_INPUT_LOCATORS));
            sleep(500);

            if (assignedTo.equalsIgnoreCase("Assign to all Users")) {
                clickElement(ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS);
                log("Selected 'Assign to all Users'.");
            } else if (assignedTo.equalsIgnoreCase("Assign to all Admins")) {
                clickElement(ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS);
                log("Selected 'Assign to all Admins'.");
            } else {
                String[] emails = assignedTo.split(",");
                for (String userEmail : emails) {
                    userEmail = userEmail.trim();
                    if (userEmail.isEmpty()) continue;
                    log("Assigning to individual user: " + userEmail);
                    sendKeysToElement(ASSIGNED_TO_INPUT_LOCATORS, userEmail);
                    sleep(DROPDOWN_WAIT_MS);
                    try {
                        String userOptionXpath = String.format("//div[contains(@class,'dropdown-menu') or contains(@class,'k-list-scroller')]//li[contains(.,'%s')]", userEmail);
                        WebElement userOption = driver.findElement(By.xpath(userOptionXpath));
                        jsClick(userOption);
                        log("Selected user '" + userEmail + "' from dropdown.");
                    } catch (Exception e) {
                        log("WARNING: User '" + userEmail + "' not found in dropdown. Leaving as typed text.");
                    }
                    sleep(500);
                }
            }
        }

        // Align to Priority (Optional)
        if (!alignPriority.isEmpty()) {
            log("Aligning to Priority: " + alignPriority);
            js.executeScript("arguments[0].scrollIntoView(false);", waitVisible(ALIGN_PRIORITY_INPUT_LOCATORS));
            sendKeysToElement(ALIGN_PRIORITY_INPUT_LOCATORS, alignPriority);
            sleep(DROPDOWN_WAIT_MS);
            try {
                String priorityOptionXpath = String.format("//div[contains(@class,'dropdown-menu') or contains(@class,'k-list-scroller')]//li[contains(.,'%s')]", alignPriority);
                WebElement priorityOption = driver.findElement(By.xpath(priorityOptionXpath));
                jsClick(priorityOption);
                log("Selected priority '" + alignPriority + "' from dropdown.");
            } catch (Exception e) {
                log("WARNING: Priority '" + alignPriority + "' not found in dropdown.");
            }
        }

        // Align to Huddle (Optional)
        if (!alignHuddle.isEmpty()) {
            log("Aligning to Huddle: " + alignHuddle);
            js.executeScript("arguments[0].scrollIntoView(false);", waitVisible(ALIGN_HUDDLE_INPUT_LOCATORS));
            sendKeysToElement(ALIGN_HUDDLE_INPUT_LOCATORS, alignHuddle);
            sleep(DROPDOWN_WAIT_MS);
            try {
                String huddleOptionXpath = String.format("//div[contains(@class,'dropdown-menu') or contains(@class,'k-list-scroller')]//li[contains(.,'%s')]", alignHuddle);
                WebElement huddleOption = driver.findElement(By.xpath(huddleOptionXpath));
                jsClick(huddleOption);
                log("Selected huddle '" + alignHuddle + "' from dropdown.");
            } catch (Exception e) {
                log("WARNING: Huddle '" + alignHuddle + "' not found in dropdown.");
            }
        }

        // Upload Document (Optional)
        if (!documentName.isEmpty()) {
            log("Attaching document: " + documentName);
            js.executeScript("arguments[0].scrollIntoView(false);", waitVisible(ADD_DOCUMENT_ICON_LOCATORS));
            clickElement(ADD_DOCUMENT_ICON_LOCATORS);
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(DOCUMENT_NAME_INPUT_LOCATORS, documentName);
            sendKeysToElement(DOCUMENT_DESCRIPTION_INPUT_LOCATORS, documentDescription);

            if ("File".equalsIgnoreCase(documentType) && !documentFile.isEmpty()) {
                log("Uploading file: " + documentFile);
                WebElement fileInput = findElementWithFallbacks(HIDDEN_FILE_INPUT_LOCATORS);
                js.executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';", fileInput);
                String fullPath = Paths.get(FILE_UPLOAD_BASE_PATH, documentFile).toString();
                fileInput.sendKeys(fullPath);
                sleep(2000); // Wait for upload to process
            } else if ("Link".equalsIgnoreCase(documentType) && !documentUrl.isEmpty()) {
                log("Attaching link: " + documentUrl);
                clickElement(DOCUMENT_TYPE_DROPDOWN_LOCATORS);
                sleep(500);
                String linkOptionXpath = "//ul[contains(@class,'k-list')]//li[normalize-space()='Link']";
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkOptionXpath))).click();
                sleep(500);
                sendKeysToElement(DOCUMENT_URL_INPUT_LOCATORS, documentUrl);
            }

            clickElement(DOCUMENT_SAVE_BUTTON_LOCATORS);
            log("Saved document attachment.");
            sleep(3000); // Wait for dialog to close and attachment to appear
        }

        // Submit Task Creation
        log("Submitting the new task.");
        WebElement saveButton = waitClickable(MAIN_SAVE_BUTTON_LOCATORS);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
        sleep(500);
        jsClick(saveButton); // Use JS click for reliability on forms
        waitForPageLoad();

        // Verify Task Creation
        waitVisible(NEW_TASK_BUTTON_LOCATORS);
        log("Task '" + taskName + "' created successfully. Returned to Manage Tasks page.");
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
        log("Cleaning up and closing WebDriver.");
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        TaskCreationDataDrivenTest test = new TaskCreationDataDrivenTest();
        try {
            test.setUp();
            test.testDataDrivenWorkflow();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during the test run: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}