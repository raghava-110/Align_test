package com.task.automation;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlignTaskAutomation {

    // ========== LOCATORS - GENERATED WITH FALLBACKS ==========
    private static final String[] EMAIL_INPUT_LOCATORS = {
        "//input[@id='usernameField']",
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//label[contains(normalize-space(),'Email')]/following-sibling::*/input",
        "//input[contains(@data-bind,'value: username')]",
        "//span[contains(@class,'k-widget')]//input[@id='usernameField']"
    };
    private static final String[] CONTINUE_BUTTON_LOCATORS = {
        "//button[normalize-space()='Continue']",
        "//input[@value='Continue']",
        "//a[normalize-space()='Continue']",
        "//button[@id='continueButton']",
        "//button[contains(.,'Continue')]",
        "//button[@type='submit']"
    };
    private static final String[] PASSWORD_INPUT_LOCATORS = {
        "//input[@id='passwordField']",
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//label[contains(normalize-space(),'Password')]/following-sibling::*/input",
        "//input[contains(@data-bind,'value: password')]",
        "//span[contains(@class,'k-widget')]//input[@id='passwordField']"
    };
    private static final String[] LOGIN_BUTTON_LOCATORS = {
        "//button[normalize-space()='Login']",
        "//input[@value='Login']",
        "//a[normalize-space()='Login']",
        "//button[@id='loginButton']",
        "//button[contains(.,'Login')]",
        "//button[@type='submit']"
    };
    private static final String[] ACTION_ITEMS_MENU_LOCATORS = {
        "//span[normalize-space()='Action Items']",
        "//a[normalize-space()='Action Items']",
        "//div[normalize-space()='Action Items']",
        "//button[normalize-space()='Action Items']",
        "//li/a[contains(.,'Action Items')]",
        "//span[contains(@class, 'menu') and contains(.,'Action Items')]"
    };
    private static final String[] TASKS_SUBMENU_LOCATORS = {
        "//a[@href='/Task/Manage']",
        "//a[normalize-space()='Tasks']",
        "//li/a[normalize-space()='Tasks']",
        "//div[contains(@class, 'submenu')]//a[contains(.,'Tasks')]",
        "//span[normalize-space()='Tasks']"
    };
    private static final String[] NEW_TASK_BUTTON_LOCATORS = {
        "//a[normalize-space()='New Task']",
        "//button[normalize-space()='New Task']",
        "//span[normalize-space()='New Task']",
        "//a[contains(.,'New Task')]",
        "//button[contains(.,'New Task')]",
        "//div[contains(@class, 'btn') and contains(.,'New Task')]"
    };
    private static final String[] SHORT_TASK_NAME_INPUT_LOCATORS = {
        "//input[@name='TaskName']",
        "//input[@data-bind='value: TaskName']",
        "//label[contains(.,'Short Task Name')]/following-sibling::input",
        "//input[contains(@placeholder, 'Task Name')]",
        "//textarea[@name='TaskName']"
    };
    private static final String[] DUE_DATE_INPUT_LOCATORS = {
        "//input[@name='DueDate']",
        "//input[@data-bind='value: DueDate']",
        "//label[contains(.,'Due Date')]/following-sibling::span//input",
        "//input[contains(@class, 'datepicker')]"
    };
    private static final String[] ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Users']/preceding-sibling::input[@type='checkbox']",
        "//input[@data-bind='checked: AssignToAll']",
        "//label[contains(.,'Assign to all Users')]/input",
        "//input[@id='assignToAllUsers']"
    };
    private static final String[] ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Admins']/preceding-sibling::input[@type='checkbox']",
        "//input[@data-bind='checked: AssignToAllAdmins']",
        "//label[contains(.,'Assign to all Admins')]/input",
        "//input[@id='assignToAllAdmins']"
    };
    private static final String[] SEARCH_TEAM_MEMBERS_INPUT_LOCATORS = {
        "//input[@placeholder='Search or Invite Team Members']",
        "//input[contains(@data-bind,'value: owner')]",
        "//input[@id='companyMetricOwner']",
        "//label[contains(.,'Assigned To')]/following-sibling::div//input",
        "//div[contains(@class, 'user-search')]//input"
    };
    private static final String[] SEARCH_PRIORITIES_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Priorities']",
        "//input[@data-bind='value: selectedPriority']",
        "//div[contains(@class, 'priority-search')]//input"
    };
    private static final String[] SEARCH_HUDDLES_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Huddles']",
        "//input[@data-bind='value: selectedHuddle']",
        "//div[contains(@class, 'huddle-search')]//input"
    };
    private static final String[] ADD_DOCUMENT_ICON_LOCATORS = {
        "//h3[normalize-space()='Documents']/following-sibling::a/span[contains(@class, 'plus')]",
        "//a[@data-bind='click: addDocument']",
        "//span[contains(@class, 'ico-plus-circle')]"
    };
    private static final String[] DOCUMENT_NAME_INPUT_LOCATORS = {
        "//input[@placeholder='Enter document name']",
        "//input[@name='DocumentName']",
        "//label[contains(.,'Document Name')]/following-sibling::input"
    };
    private static final String[] DOCUMENT_DESCRIPTION_INPUT_LOCATORS = {
        "//textarea[@name='DocumentDescription']",
        "//textarea[contains(@placeholder, 'Description')]"
    };
    private static final String[] DOCUMENT_TYPE_DROPDOWN_LOCATORS = {
        "//label[normalize-space()='Type']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//input[@name='DocumentType_input']/../span"
    };
    private static final String[] DOCUMENT_URL_INPUT_LOCATORS = {
        "//input[@placeholder='Enter Link...']",
        "//input[@name='DocumentUrl']"
    };
    private static final String[] HIDDEN_FILE_INPUT_LOCATORS = {
        "//input[@type='file' and contains(@style, 'display: none')]",
        "//input[@type='file' and not(@visible)]",
        "//input[@type='file']"
    };
    private static final String[] DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS = {
        "//div[contains(@class, 'document-dialog')]//button[normalize-space()='Save']",
        "//div[@aria-labelledby='documentUploadDialog']//button[contains(.,'Save')]"
    };
    private static final String[] MAIN_SAVE_BUTTON_LOCATORS = {
        "//div[@class='buttons-group']//button[normalize-space()='Save']",
        "//button[@type='submit' and contains(.,'Save')]",
        "//form[@id='taskForm']//button[normalize-space()='Save']"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:\\Users\\sudhi\\Downloads\\Align_Test_Data.xlsx";
    private static final String FILE_UPLOAD_BASE_PATH = "C:\\Users\\sudhi\\Downloads\\";
    // ==================================================
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;
    private static final int DROPDOWN_WAIT_MS = 2500;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    /**
     * Static nested class for reading data from an Excel file.
     */
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
                System.err.println("Error reading Excel file: " + e.getMessage());
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
                    double num = cell.getNumericCellValue();
                    if (num == (long) num) {
                        return String.valueOf((long) num);
                    } else {
                        return String.valueOf(num);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // Fallback for formula resulting in number
                        return getCellValueAsString(cell.getCachedFormulaResultType(), cell);
                    }
                case BLANK:
                default:
                    return "";
            }
        }
        
        private static String getCellValueAsString(CellType cellType, Cell cell) {
             switch (cellType) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                     double num = cell.getNumericCellValue();
                    if (num == (long) num) {
                        return String.valueOf((long) num);
                    } else {
                        return String.valueOf(num);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
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
        }
    }

    private WebElement findElementWithFallbacks(String[] locators) {
        for (String locator : locators) {
            try {
                return driver.findElement(By.xpath(locator));
            } catch (NoSuchElementException e) {
                // Ignore and try next locator
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
            js.executeScript("arguments[0].click();", element);
        }
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    private String getOrDefault(Map<String, String> map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue).trim();
    }

    private void selectCompanyByName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            log("WARNING: Company Name is not provided. Skipping company selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            sleep(ACTION_PAUSE_MS); // Wait for company list to render
            String companySpanXpath = String.format("//span[normalize-space()='%s']", companyName);
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

            // Try to find a more robust clickable parent element
            WebElement clickableParent = companySpan.findElement(By.xpath("./ancestor::div[contains(@class, 'company-card') or contains(@class, 'list-item') or @role='button'][1]"));
            log("Found clickable parent for company. Clicking it.");
            clickableParent.click();
        } catch (Exception e) {
            log("WARNING: Could not find or click parent of company span. Trying to click the span directly. Error: " + e.getMessage());
            try {
                String companySpanXpath = String.format("//span[normalize-space()='%s']", companyName);
                driver.findElement(By.xpath(companySpanXpath)).click();
            } catch (Exception e2) {
                log("ERROR: Failed to select company '" + companyName + "' by all methods.");
            }
        }
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("ERROR: No test data found in the Excel file or file could not be read.");
            return;
        }
        log("Found " + testData.size() + " test record(s) to process.");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("================================================================");
            log("Processing record " + (i + 1) + " of " + testData.size() + " | Task Name: " + getOrDefault(data, "Task_Name", "N/A"));
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                log("!!!!!!!!!! ERROR processing record " + (i + 1) + ": " + e.getMessage() + " !!!!!!!!!!");
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
                log("================================================================");
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from map
        String baseUrl = getOrDefault(data, "Base_URL", "https://alignwebdev.aligntoday.com/");
        String email = getOrDefault(data, "Email", "");
        String password = getOrDefault(data, "Password", "");
        String companyName = getOrDefault(data, "Company_Name", "");
        String taskName = getOrDefault(data, "Task_Name", "");
        String dueDate = getOrDefault(data, "Due_Date", "");
        String assignedTo = getOrDefault(data, "Assigned_To", "");
        String alignPriority = getOrDefault(data, "Align_Priority", "");
        String alignHuddle = getOrDefault(data, "Align_Huddle", "");
        String docName = getOrDefault(data, "Document_Name", "");
        String docDesc = getOrDefault(data, "Document_Description", "");
        String docType = getOrDefault(data, "Document_Type", "");
        String docFile = getOrDefault(data, "Document_File", "");
        String docUrl = getOrDefault(data, "Document_URL", "");

        // PHASE 1: Login and Navigation (First record only)
        if (isFirstRecord) {
            log("Executing PHASE 1: Login and Navigation.");
            driver.get(baseUrl);
            waitForPageLoad();

            log("Entering email: " + email);
            sendKeysToElement(EMAIL_INPUT_LOCATORS, email);
            sleep(500);
            clickElement(CONTINUE_BUTTON_LOCATORS);
            
            log("Entering password.");
            sendKeysToElement(PASSWORD_INPUT_LOCATORS, password);
            sleep(500);
            clickElement(LOGIN_BUTTON_LOCATORS);
            waitForPageLoad();

            selectCompanyByName(companyName);
            waitForPageLoad();

            log("Navigating to Tasks page.");
            clickElement(ACTION_ITEMS_MENU_LOCATORS);
            sleep(ACTION_PAUSE_MS);
            clickElement(TASKS_SUBMENU_LOCATORS);
            waitForPageLoad();
        } else {
            log("Skipping login, session should be active.");
            try {
                waitVisible(NEW_TASK_BUTTON_LOCATORS);
                log("Confirmed on Manage Tasks page.");
            } catch (Exception e) {
                log("Not on Manage Tasks page. Re-navigating...");
                clickElement(ACTION_ITEMS_MENU_LOCATORS);
                sleep(ACTION_PAUSE_MS);
                clickElement(TASKS_SUBMENU_LOCATORS);
                waitForPageLoad();
            }
        }

        // PHASE 2: Create Task
        log("Executing PHASE 2: Create Task for '" + taskName + "'.");
        clickElement(NEW_TASK_BUTTON_LOCATORS);
        waitForPageLoad();

        log("Entering task name: " + taskName);
        sendKeysToElement(SHORT_TASK_NAME_INPUT_LOCATORS, taskName);

        log("Entering due date: " + dueDate);
        WebElement datePicker = waitVisible(DUE_DATE_INPUT_LOCATORS);
        datePicker.clear();
        datePicker.sendKeys(dueDate);
        datePicker.sendKeys(Keys.ENTER);
        sleep(500);

        // Handle Assignments
        if (!assignedTo.isEmpty()) {
            log("Processing assignments: " + assignedTo);
            if (assignedTo.equalsIgnoreCase("Assign to all Users")) {
                clickElement(ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS);
            } else if (assignedTo.equalsIgnoreCase("Assign to all Admins")) {
                clickElement(ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS);
            } else {
                String[] users = assignedTo.split(",");
                for (String user : users) {
                    user = user.trim();
                    if (user.isEmpty()) continue;
                    log("Assigning to user: " + user);
                    WebElement searchInput = waitVisible(SEARCH_TEAM_MEMBERS_INPUT_LOCATORS);
                    searchInput.sendKeys(user);
                    sleep(DROPDOWN_WAIT_MS);
                    try {
                        String userOptionXpath = String.format("//ul[contains(@style, 'display: block')]//li[contains(.,'%s')]", user);
                        WebElement userOption = driver.findElement(By.xpath(userOptionXpath));
                        userOption.click();
                        log("Selected user from dropdown.");
                    } catch (Exception e) {
                        log("WARNING: User '" + user + "' not found in dropdown. Leaving as typed text.");
                    }
                    sleep(500);
                }
            }
        }

        // Handle Priority Alignment
        if (!alignPriority.isEmpty()) {
            log("Aligning to priority: " + alignPriority);
            WebElement priorityInput = waitVisible(SEARCH_PRIORITIES_INPUT_LOCATORS);
            priorityInput.sendKeys(alignPriority);
            sleep(DROPDOWN_WAIT_MS);
            try {
                String priorityOptionXpath = String.format("//ul[contains(@style, 'display: block')]//li[contains(.,'%s')]", alignPriority);
                driver.findElement(By.xpath(priorityOptionXpath)).click();
                log("Selected priority from dropdown.");
            } catch (Exception e) {
                log("WARNING: Priority '" + alignPriority + "' not found in dropdown.");
            }
        }

        // Handle Huddle Alignment
        if (!alignHuddle.isEmpty()) {
            log("Aligning to huddle: " + alignHuddle);
            WebElement huddleInput = waitVisible(SEARCH_HUDDLES_INPUT_LOCATORS);
            huddleInput.sendKeys(alignHuddle);
            sleep(DROPDOWN_WAIT_MS);
            try {
                String huddleOptionXpath = String.format("//ul[contains(@style, 'display: block')]//li[contains(.,'%s')]", alignHuddle);
                driver.findElement(By.xpath(huddleOptionXpath)).click();
                log("Selected huddle from dropdown.");
            } catch (Exception e) {
                log("WARNING: Huddle '" + alignHuddle + "' not found in dropdown.");
            }
        }

        // Handle Document Upload
        if (!docName.isEmpty()) {
            log("Adding document: " + docName);
            clickElement(ADD_DOCUMENT_ICON_LOCATORS);
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(DOCUMENT_NAME_INPUT_LOCATORS, docName);
            if (!docDesc.isEmpty()) {
                WebElement descArea = waitVisible(DOCUMENT_DESCRIPTION_INPUT_LOCATORS);
                js.executeScript("arguments[0].value = arguments[1];", descArea, docDesc);
            }

            if (docType.equalsIgnoreCase("File") && !docFile.isEmpty()) {
                log("Uploading file: " + docFile);
                WebElement fileInput = findElementWithFallbacks(HIDDEN_FILE_INPUT_LOCATORS);
                js.executeScript("arguments[0].style.display='block';", fileInput);
                String fullPath = FILE_UPLOAD_BASE_PATH + docFile;
                fileInput.sendKeys(fullPath);
                sleep(PAGE_SETTLE_MS); // Wait for upload to process
            } else if (docType.equalsIgnoreCase("Link") && !docUrl.isEmpty()) {
                log("Attaching link: " + docUrl);
                clickElement(DOCUMENT_TYPE_DROPDOWN_LOCATORS);
                sleep(500);
                String linkOptionXpath = "//ul[contains(@style, 'display: block')]//li[normalize-space()='Link']";
                driver.findElement(By.xpath(linkOptionXpath)).click();
                sendKeysToElement(DOCUMENT_URL_INPUT_LOCATORS, docUrl);
            }

            clickElement(DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS);
            sleep(ACTION_PAUSE_MS + 1500); // Extra wait for dialog to close and attachment to register
        }

        // Submit Task
        log("Saving the task.");
        WebElement saveButton = waitClickable(MAIN_SAVE_BUTTON_LOCATORS);
        js.executeScript("arguments[0].scrollIntoView(true);", saveButton);
        sleep(500);
        saveButton.click();
        waitForPageLoad();

        log("Verifying return to Manage Tasks page.");
        waitVisible(NEW_TASK_BUTTON_LOCATORS);
        log("Task '" + taskName + "' creation process completed successfully.");
    }

    // ========== SETUP AND TEARDOWN ==========
    public void setUp() {
        log("Setting up WebDriver.");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
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