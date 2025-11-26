package com.taskautomation.selenium;

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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class AlignTaskAutomation {

    // ========== LOCATORS - DO NOT CHANGE ==========
    private static final String[] EMAIL_INPUT_LOCATORS = {
        "//input[@id='usernameField']",
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//label[contains(.,'Email')]/following-sibling::span//input",
        "//input[contains(@class, 'email-input')]",
        "//form//input[1]"
    };
    private static final String[] CONTINUE_BUTTON_LOCATORS = {
        "//button[normalize-space()='Continue']",
        "//input[@value='Continue']",
        "//a[normalize-space()='Continue']",
        "//button[@type='submit' and contains(., 'Continue')]",
        "//div[@role='button' and contains(., 'Continue')]",
        "//button[contains(@class, 'continue-btn')]",
        "//form//button[1]"
    };
    private static final String[] PASSWORD_INPUT_LOCATORS = {
        "//input[@id='passwordField']",
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//label[contains(.,'Password')]/following-sibling::span//input",
        "//input[contains(@class, 'password-input')]",
        "//form//input[@type='password']"
    };
    private static final String[] LOGIN_BUTTON_LOCATORS = {
        "//button[normalize-space()='Login']",
        "//input[@value='Login']",
        "//a[normalize-space()='Login']",
        "//button[@type='submit' and (contains(., 'Login') or contains(., 'Sign In'))]",
        "//div[@role='button' and (contains(., 'Login') or contains(., 'Sign In'))]",
        "//button[contains(@class, 'login-btn')]",
        "//form//button[@type='submit']"
    };
    private static final String[] ACTION_ITEMS_MENU_LOCATORS = {
        "//span[@class='k-input-inner']/input[@id='navigation-header-dropdown']/ancestor::span[@aria-controls='navigation-header-dropdown_listbox']",
        "//a[normalize-space()='Action Items']",
        "//span[normalize-space()='Action Items']",
        "//div[contains(@class, 'nav')]//a[contains(., 'Action Items')]",
        "//li[contains(., 'Action Items')]",
        "//button[normalize-space()='Action Items']",
        "//div[@role='menuitem' and contains(., 'Action Items')]"
    };
    private static final String[] TASKS_SUBMENU_LOCATORS = {
        "//div[@id='navigation-header-dropdown-list']//ul/li/div[normalize-space()='Tasks']",
        "//a[normalize-space()='Tasks']",
        "//span[normalize-space()='Tasks']",
        "//div[contains(@class, 'submenu')]//a[contains(., 'Tasks')]",
        "//li[contains(., 'Tasks') and not(contains(., 'New Task'))]",
        "//div[@role='menuitem' and normalize-space()='Tasks']",
        "//ul[contains(@class, 'menu')]//li[contains(., 'Tasks')]"
    };
    private static final String[] NEW_TASK_BUTTON_LOCATORS = {
        "//a[normalize-space()='New Task']",
        "//button[normalize-space()='New Task']",
        "//span[normalize-space()='New Task']",
        "//div[contains(@class, 'btn') and contains(., 'New Task')]",
        "//a[contains(@href, 'Task/Create')]",
        "//i[contains(@class, 'plus')]/ancestor::button",
        "//div[@role='button' and contains(., 'New Task')]"
    };
    private static final String[] SHORT_TASK_NAME_INPUT_LOCATORS = {
        "//input[@name='TaskName']",
        "//input[@data-bind='value: TaskName']",
        "//label[contains(., 'Short Task Name')]/following-sibling::input",
        "//input[contains(@placeholder, 'Task Name')]",
        "//textarea[@name='TaskName']",
        "//div[./label[contains(., 'Short Task Name')]]//input"
    };
    private static final String[] DUE_DATE_INPUT_LOCATORS = {
        "//input[@name='DueDate']",
        "//input[@data-bind='value: DueDate']",
        "//label[contains(., 'Due Date')]/following-sibling::span//input",
        "//input[contains(@placeholder, 'MM/DD/YYYY')]",
        "//div[./label[contains(., 'Due Date')]]//input",
        "//span[@aria-controls='taskDueDate_dateview']//input"
    };
    private static final String[] ASSIGNED_TO_INPUT_LOCATORS = {
        "//input[@placeholder='Search or Invite Team Members']",
        "//label[contains(., 'Assigned To')]/following-sibling::div//input",
        "//input[contains(@data-bind, 'dsSearchPersons')]",
        "//div[contains(@class, 'user-search')]//input",
        "//input[@id='companyMetricOwner']"
    };
    private static final String[] ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Users']/preceding-sibling::input[@type='checkbox']",
        "//label[normalize-space()='Assign to all Users']",
        "//input[@data-bind='checked: AssignToAll']",
        "//div[contains(., 'Assign to all Users')]//input[@type='checkbox']"
    };
    private static final String[] ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Admins']/preceding-sibling::input[@type='checkbox']",
        "//label[normalize-space()='Assign to all Admins']",
        "//input[@data-bind='checked: AssignToAllAdmins']",
        "//div[contains(., 'Assign to all Admins')]//input[@type='checkbox']"
    };
    private static final String[] ALIGN_PRIORITY_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Priorities']",
        "//label[contains(., 'Priority')]/following-sibling::div//input",
        "//div[contains(@class, 'priority-search')]//input",
        "//input[contains(@data-bind, 'dsPriorities')]"
    };
    private static final String[] ALIGN_HUDDLE_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Huddles']",
        "//label[contains(., 'Huddle')]/following-sibling::div//input",
        "//div[contains(@class, 'huddle-search')]//input",
        "//input[contains(@data-bind, 'dsHuddles')]"
    };
    private static final String[] ADD_DOCUMENT_ICON_LOCATORS = {
        "//h3[normalize-space()='Documents']/following-sibling::div//a[contains(@class, 'plus')]",
        "//a[@title='Add Document']",
        "//i[contains(@class, 'ico-plus-circle')]/ancestor::a",
        "//div[contains(., 'Documents')]//span[contains(@class, 'plus')]"
    };
    private static final String[] DOCUMENT_NAME_INPUT_LOCATORS = {
        "//input[@name='DocumentName']",
        "//input[@placeholder='Enter document name']",
        "//div[contains(@class, 'modal')]//label[contains(., 'Name')]/following-sibling::input",
        "//input[@data-bind='value: DocumentName']"
    };
    private static final String[] DOCUMENT_DESCRIPTION_INPUT_LOCATORS = {
        "//textarea[@name='DocumentDescription']",
        "//textarea[@placeholder='Enter a description...']",
        "//div[contains(@class, 'modal')]//label[contains(., 'Description')]/following-sibling::textarea",
        "//textarea[@data-bind='value: DocumentDescription']"
    };
    private static final String[] DOCUMENT_TYPE_DROPDOWN_LOCATORS = {
        "//label[normalize-space()='Type']/following-sibling::span//span[@class='k-select']",
        "//input[@name='DocumentType_input']/ancestor::span",
        "//div[contains(@class, 'modal')]//span[contains(@aria-label, 'select')]"
    };
    private static final String[] DOCUMENT_URL_INPUT_LOCATORS = {
        "//input[@name='DocumentUrl']",
        "//input[@placeholder='Enter Link...']",
        "//input[@data-bind='value: DocumentUrl']",
        "//div[contains(@class, 'modal')]//label[contains(., 'Link')]/following-sibling::input"
    };
    private static final String[] HIDDEN_FILE_INPUT_LOCATORS = {
        "//input[@type='file' and @name='files']",
        "//input[@type='file' and contains(@style, 'display: none')]",
        "//div[contains(@class, 'upload')]//input[@type='file']"
    };
    private static final String[] DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS = {
        "//div[contains(@class, 'modal-footer')]//button[normalize-space()='Save']",
        "//div[@aria-labelledby='documentUploadDialog_wnd_title']//button[contains(., 'Save')]",
        "//div[@role='dialog']//button[@type='submit']"
    };
    private static final String[] MAIN_SAVE_BUTTON_LOCATORS = {
        "//div[@class='footer-container']//button[normalize-space()='Save']",
        "//button[@type='submit' and normalize-space()='Save']",
        "//form[@id='createTaskForm']//button[contains(., 'Save')]",
        "//div[not(contains(@class, 'modal'))]//button[normalize-space()='Save']"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/Users/sudhi/Downloads/Align_Test_Data.xlsx";
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final String FILE_UPLOAD_BASE_PATH = "C:\\Users\\sudhi\\Downloads\\";
    // ==================================================
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    private static class ExcelReader {
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
                    Map<String, String> rowData = new HashMap<>();
                    boolean hasData = false;
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String cellValue = (cell == null) ? "" : getCellValueAsString(cell);
                        rowData.put(headers.get(j), cellValue);
                        if (!cellValue.isEmpty()) {
                            hasData = true;
                        }
                    }
                    if (hasData) {
                        testData.add(rowData);
                    }
                }
            } catch (IOException e) {
                log("ERROR reading Excel file: " + e.getMessage());
                e.printStackTrace();
            }
            return testData;
        }

        private static String getCellValueAsString(Cell cell) {
            if (cell == null) return "";
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue());
                    }
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
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

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return driver.findElement(By.xpath(xpath));
            } catch (NoSuchElementException e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided locators. First locator tried: " + xpaths[0]);
    }

    private WebElement waitVisible(String[] xpaths) {
        return wait.until(driver -> findElementWithFallbacks(xpaths));
    }

    private WebElement waitClickable(String[] xpaths) {
        WebElement element = waitVisible(xpaths);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(xpaths);
        element.clear();
        element.sendKeys(text);
    }

    private void clickElement(String[] xpaths) {
        WebElement element = waitClickable(xpaths);
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

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void log(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message);
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    private void selectCompanyByName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            log("WARNING: Company name is empty, skipping selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            String companySpanXpath = String.format("//span[normalize-space()='%s']", companyName);
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));
            
            // Try to find a clickable parent card/container
            String[] parentSelectors = {
                "/ancestor::div[contains(@class, 'company-card') or contains(@class, 'selectable')][1]",
                "/ancestor::a[1]",
                "/ancestor::li[1]",
                "/.."
            };

            for (String selector : parentSelectors) {
                try {
                    WebElement parent = companySpan.findElement(By.xpath("." + selector));
                    log("Found clickable parent for company. Clicking parent.");
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", parent);
                    sleep(500);
                    jsClick(parent);
                    waitForPageLoad();
                    return;
                } catch (Exception e) {
                    // Parent not found, continue to next selector
                }
            }

            log("WARNING: No clickable parent found for company span. Clicking the span directly.");
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", companySpan);
            sleep(500);
            jsClick(companySpan);
            waitForPageLoad();

        } catch (Exception e) {
            log("WARNING: Could not select company '" + companyName + "'. " + e.getMessage());
        }
    }

    private String getOrDefault(Map<String, String> map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue).trim();
    }

    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log("Found " + testData.size() + " test records in the Excel file.");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("==================================================");
            log("Executing record " + (i + 1) + " for Task: " + data.get("Task_Name"));
            try {
                executeWorkflow(data);
                log("SUCCESS: Record " + (i + 1) + " processed successfully.");
            } catch (Exception e) {
                log("ERROR processing record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
        log("==================================================");
        log("All test records processed.");
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract all data at the beginning
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

        if (isFirstRecord) {
            log("Executing login sequence for the first record.");
            driver.get(BASE_URL);
            waitForPageLoad();

            // Step 2: Enter Email
            sendKeysToElement(EMAIL_INPUT_LOCATORS, email);
            sleep(ACTION_PAUSE_MS);

            // Step 3: Click Continue
            clickElement(CONTINUE_BUTTON_LOCATORS);
            waitForPageLoad();

            // Step 4: Enter Password
            sendKeysToElement(PASSWORD_INPUT_LOCATORS, password);
            sleep(ACTION_PAUSE_MS);

            // Step 5: Click Login
            clickElement(LOGIN_BUTTON_LOCATORS);
            waitForPageLoad();

            // Step 6: Select Company
            selectCompanyByName(companyName);

            // Step 7 & 8: Navigate to Tasks Page
            log("Navigating to Tasks page...");
            clickElement(ACTION_ITEMS_MENU_LOCATORS);
            sleep(ACTION_PAUSE_MS);
            clickElement(TASKS_SUBMENU_LOCATORS);
            waitForPageLoad();
            log("Successfully navigated to Tasks page.");
        } else {
            log("Session active. Verifying if on Tasks page.");
            try {
                waitVisible(NEW_TASK_BUTTON_LOCATORS);
                log("Confirmed on Tasks page.");
            } catch (Exception e) {
                log("Not on Tasks page. Re-navigating...");
                clickElement(ACTION_ITEMS_MENU_LOCATORS);
                sleep(ACTION_PAUSE_MS);
                clickElement(TASKS_SUBMENU_LOCATORS);
                waitForPageLoad();
            }
        }

        // PHASE 2: Create Task (Repeats for each record)
        log("Starting task creation for: " + taskName);

        // Step 1: Open Create Task Form
        clickElement(NEW_TASK_BUTTON_LOCATORS);
        waitForPageLoad();
        log("New Task form opened.");

        // Step 2: Enter Task Name
        sendKeysToElement(SHORT_TASK_NAME_INPUT_LOCATORS, taskName);
        log("Entered Task Name: " + taskName);

        // Step 3: Select Due Date
        if (!dueDate.isEmpty()) {
            WebElement dateInput = waitVisible(DUE_DATE_INPUT_LOCATORS);
            dateInput.clear();
            dateInput.sendKeys(dueDate);
            dateInput.sendKeys(Keys.ENTER);
            log("Entered Due Date: " + dueDate);
        }
        sleep(ACTION_PAUSE_MS);

        // Step 4: Assign Team Members
        if (!assignedTo.isEmpty()) {
            log("Processing assignments for: " + assignedTo);
            WebElement assignmentSection = findElementWithFallbacks(ASSIGNED_TO_INPUT_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", assignmentSection);
            sleep(500);

            if (assignedTo.equalsIgnoreCase("Assign to all Users")) {
                clickElement(ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS);
                log("Selected 'Assign to all Users'.");
            } else if (assignedTo.equalsIgnoreCase("Assign to all Admins")) {
                clickElement(ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS);
                log("Selected 'Assign to all Admins'.");
            } else {
                String[] users = assignedTo.split(",");
                for (String user : users) {
                    String userEmail = user.trim();
                    if (userEmail.isEmpty()) continue;
                    
                    WebElement searchInput = waitVisible(ASSIGNED_TO_INPUT_LOCATORS);
                    searchInput.clear();
                    searchInput.sendKeys(userEmail);
                    log("Searching for user: " + userEmail);
                    sleep(2000); // Wait for autocomplete

                    try {
                        String userOptionXpath = String.format("//ul[contains(@id, 'listbox')]//li[contains(., '%s')]", userEmail);
                        WebElement userOption = driver.findElement(By.xpath(userOptionXpath));
                        jsClick(userOption);
                        log("Selected user from dropdown: " + userEmail);
                    } catch (Exception e) {
                        log("WARNING: User '" + userEmail + "' not found in autocomplete. Leaving as typed text.");
                    }
                    sleep(1000);
                }
            }
        }

        // Step 5: Align to Priority (Optional)
        if (!alignPriority.isEmpty()) {
            log("Aligning to Priority: " + alignPriority);
            WebElement priorityInput = waitVisible(ALIGN_PRIORITY_INPUT_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", priorityInput);
            sleep(500);
            priorityInput.sendKeys(alignPriority);
            sleep(2500); // Wait for dropdown
            try {
                String priorityOptionXpath = String.format("//ul[contains(@id, 'listbox')]//li[contains(., '%s')]", alignPriority);
                WebElement priorityOption = driver.findElement(By.xpath(priorityOptionXpath));
                jsClick(priorityOption);
                log("Selected Priority: " + alignPriority);
            } catch (Exception e) {
                log("WARNING: Priority '" + alignPriority + "' not found in dropdown.");
            }
        }

        // Step 6: Align to Huddle (Optional)
        if (!alignHuddle.isEmpty()) {
            log("Aligning to Huddle: " + alignHuddle);
            WebElement huddleInput = waitVisible(ALIGN_HUDDLE_INPUT_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", huddleInput);
            sleep(500);
            huddleInput.sendKeys(alignHuddle);
            sleep(2500); // Wait for dropdown
            try {
                String huddleOptionXpath = String.format("//ul[contains(@id, 'listbox')]//li[contains(., '%s')]", alignHuddle);
                WebElement huddleOption = driver.findElement(By.xpath(huddleOptionXpath));
                jsClick(huddleOption);
                log("Selected Huddle: " + alignHuddle);
            } catch (Exception e) {
                log("WARNING: Huddle '" + alignHuddle + "' not found in dropdown.");
            }
        }

        // Step 7: Upload Document (Optional)
        if (!documentName.isEmpty()) {
            log("Attaching document: " + documentName);
            WebElement addDocIcon = waitVisible(ADD_DOCUMENT_ICON_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addDocIcon);
            sleep(500);
            clickElement(ADD_DOCUMENT_ICON_LOCATORS);
            log("Document upload dialog opened.");
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(DOCUMENT_NAME_INPUT_LOCATORS, documentName);
            log("Entered Document Name: " + documentName);

            if (!documentDescription.isEmpty()) {
                WebElement descInput = waitVisible(DOCUMENT_DESCRIPTION_INPUT_LOCATORS);
                js.executeScript("arguments[0].value = arguments[1];", descInput, documentDescription);
                log("Entered Document Description.");
            }

            if (documentType.equalsIgnoreCase("File") && !documentFile.isEmpty()) {
                log("Processing file upload for: " + documentFile);
                WebElement fileInput = findElementWithFallbacks(HIDDEN_FILE_INPUT_LOCATORS);
                js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", fileInput);
                Path filePath = Paths.get(FILE_UPLOAD_BASE_PATH, documentFile);
                fileInput.sendKeys(filePath.toString());
                log("Sent file path to input: " + filePath);
                sleep(2000); // Wait for upload to process
            } else if (documentType.equalsIgnoreCase("Link") && !documentUrl.isEmpty()) {
                log("Processing link attachment for: " + documentUrl);
                clickElement(DOCUMENT_TYPE_DROPDOWN_LOCATORS);
                sleep(1000);
                String linkOptionXpath = "//ul[contains(@id, 'listbox')]//li[normalize-space()='Link']";
                clickElement(new String[]{linkOptionXpath});
                sendKeysToElement(DOCUMENT_URL_INPUT_LOCATORS, documentUrl);
                log("Entered Document URL.");
            }

            WebElement docSaveBtn = waitVisible(DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView(true);", docSaveBtn);
            sleep(500);
            clickElement(DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS);
            log("Saved document attachment.");
            sleep(3000); // Wait for dialog to close
        }

        // Step 8 & 9: Submit Task and Verify
        log("Submitting the task...");
        WebElement mainSaveBtn = waitVisible(MAIN_SAVE_BUTTON_LOCATORS);
        js.executeScript("arguments[0].scrollIntoView(true);", mainSaveBtn);
        sleep(500);
        clickElement(MAIN_SAVE_BUTTON_LOCATORS);
        waitForPageLoad();

        waitVisible(NEW_TASK_BUTTON_LOCATORS);
        log("Task creation submitted. Returned to Manage Tasks page.");
    }

    public void setUp() {
        log("Setting up WebDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
        log("WebDriver setup complete.");
    }

    public void cleanup() {
        if (driver != null) {
            log("Closing WebDriver.");
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AlignTaskAutomation test = new AlignTaskAutomation();
        try {
            test.setUp();
            test.testDataDrivenWorkflow();
        } catch (Exception e) {
            System.err.println("A critical error occurred during the test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}