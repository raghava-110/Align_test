package com.selenium.generated;

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
import java.util.NoSuchElementException;

public class AlignTaskCreationTest {

    // ========== LOCATORS - DO NOT CHANGE ==========
    private static final String[] EMAIL_INPUT_LOCATORS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email')]",
        "//label[contains(.,'Email')]/following-sibling::*/input",
        "//input[contains(@data-bind,'value: username')]"
    };
    private static final String[] CONTINUE_BUTTON_LOCATORS = {
        "//button[normalize-space()='Continue']",
        "//input[@type='submit' and @value='Continue']",
        "//button[contains(., 'Continue')]",
        "//a[normalize-space()='Continue']",
        "//div[@role='button' and contains(., 'Continue')]"
    };
    private static final String[] PASSWORD_INPUT_LOCATORS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]",
        "//label[contains(.,'Password')]/following-sibling::*/input",
        "//input[contains(@data-bind,'value: password')]"
    };
    private static final String[] LOGIN_BUTTON_LOCATORS = {
        "//button[normalize-space()='Login']",
        "//input[@type='submit' and @value='Login']",
        "//button[contains(., 'Login')]",
        "//a[normalize-space()='Login']",
        "//div[@role='button' and contains(., 'Login')]"
    };
    private static final String[] ACTION_ITEMS_MENU_LOCATORS = {
        "//a[normalize-space()='Action Items']",
        "//span[normalize-space()='Action Items']/ancestor::a",
        "//div[contains(@class, 'nav')]//span[text()='Action Items']",
        "//li/a[contains(., 'Action Items')]"
    };
    private static final String[] TASKS_SUBMENU_LOCATORS = {
        "//a[normalize-space()='Tasks']",
        "//li//span[text()='Tasks']/ancestor::a",
        "//div[contains(@class, 'submenu')]//a[text()='Tasks']",
        "//ul//a[contains(., 'Tasks')]"
    };
    private static final String[] NEW_TASK_BUTTON_LOCATORS = {
        "//button[normalize-space()='New Task']",
        "//a[normalize-space()='New Task']",
        "//button[contains(., 'New Task')]",
        "//span[normalize-space()='New Task']/ancestor::button",
        "//div[@role='button' and contains(., 'New Task')]"
    };
    private static final String[] SHORT_TASK_NAME_INPUT_LOCATORS = {
        "//label[contains(., 'Short Task Name')]/following-sibling::input",
        "//input[@placeholder='Short Task Name']",
        "//input[contains(@id, 'task-name')]",
        "//input[contains(@data-bind, 'TaskName')]"
    };
    private static final String[] DUE_DATE_INPUT_LOCATORS = {
        "//label[contains(., 'Due Date')]/..//input",
        "//input[contains(@placeholder, 'MM/DD/YYYY')]",
        "//input[contains(@id, 'due-date')]",
        "//span[contains(@class, 'datepicker')]//input"
    };
    private static final String[] ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Users']/preceding-sibling::input[@type='checkbox']",
        "//input[@type='checkbox']/following-sibling::label[normalize-space()='Assign to all Users']",
        "//span[text()='Assign to all Users']/../input[@type='checkbox']",
        "//div[contains(., 'Assign to all Users')]//input[@type='checkbox']"
    };
    private static final String[] ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Admins']/preceding-sibling::input[@type='checkbox']",
        "//input[@type='checkbox']/following-sibling::label[normalize-space()='Assign to all Admins']",
        "//span[text()='Assign to all Admins']/../input[@type='checkbox']",
        "//div[contains(., 'Assign to all Admins')]//input[@type='checkbox']"
    };
    private static final String[] SEARCH_TEAM_MEMBERS_INPUT_LOCATORS = {
        "//input[@placeholder='Search or Invite Team Members']",
        "//label[contains(., 'Assigned To')]/..//input",
        "//input[contains(@data-bind, 'dsSearchPersons')]",
        "//div[contains(@class, 'assign-members')]//input[@type='text']"
    };
    private static final String[] SEARCH_PRIORITIES_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Priorities']",
        "//label[contains(., 'Priorities')]/..//input",
        "//div[contains(@class, 'align-priorities')]//input"
    };
    private static final String[] SEARCH_HUDDLES_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Huddles']",
        "//label[contains(., 'Huddles')]/..//input",
        "//div[contains(@class, 'align-huddles')]//input"
    };
    private static final String[] DOCUMENTS_PLUS_ICON_LOCATORS = {
        "//h3[normalize-space()='Documents']/..//span[contains(@class, 'plus') or text()='+']",
        "//div[contains(@class, 'documents-section')]//button[contains(., '+')]",
        "//span[contains(@class, 'add-document')]"
    };
    private static final String[] DOCUMENT_NAME_INPUT_LOCATORS = {
        "//div[contains(@class, 'modal-body') or contains(@class, 'dialog-content')]//input[@placeholder='Enter document name']",
        "//label[text()='Document Name']/..//input",
        "//input[contains(@id, 'doc-name')]"
    };
    private static final String[] DOCUMENT_DESCRIPTION_INPUT_LOCATORS = {
        "//div[contains(@class, 'modal-body') or contains(@class, 'dialog-content')]//textarea[contains(@placeholder, 'Description')]",
        "//label[text()='Description']/..//textarea",
        "//textarea[contains(@id, 'doc-desc')]"
    };
    private static final String[] DOCUMENT_TYPE_DROPDOWN_LOCATORS = {
        "//label[text()='Type']/..//select",
        "//div[contains(@class, 'doc-type')]//span[contains(@class, 'dropdown')]",
        "//div[contains(@class, 'modal-body') or contains(@class, 'dialog-content')]//input[contains(@data-bind, 'DocumentType')]"
    };
    private static final String[] DOCUMENT_TYPE_LINK_OPTION_LOCATORS = {
        "//ul[contains(@class, 'k-list') or contains(@class, 'dropdown-menu')]//li[normalize-space()='Link']",
        "//option[normalize-space()='Link']"
    };
    private static final String[] DOCUMENT_FILE_INPUT_LOCATORS = {
        "//input[@type='file' and (contains(@style, 'display: none') or contains(@class, 'hidden'))]",
        "//div[contains(@class, 'upload-area')]//input[@type='file']",
        "//input[@type='file']"
    };
    private static final String[] DOCUMENT_URL_INPUT_LOCATORS = {
        "//div[contains(@class, 'modal-body') or contains(@class, 'dialog-content')]//input[@placeholder='Enter Link...']",
        "//label[text()='URL']/..//input",
        "//input[contains(@id, 'doc-url')]"
    };
    private static final String[] DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS = {
        "//div[contains(@class, 'modal-footer') or contains(@class, 'dialog-buttons')]//button[normalize-space()='Save']",
        "//div[@role='dialog']//button[contains(., 'Save')]"
    };
    private static final String[] FINAL_TASK_SAVE_BUTTON_LOCATORS = {
        "//div[contains(@class, 'form-footer') or contains(@class, 'button-panel')]//button[normalize-space()='Save']",
        "//form[contains(@id, 'task-form')]//button[@type='submit' and contains(., 'Save')]",
        "//button[@type='submit' and normalize-space()='Save']"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:\\path\\to\\your\\testdata.xlsx";
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
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
                System.err.println("Error reading Excel file: " + filePath);
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
                    if (num == Math.floor(num)) {
                        return String.valueOf((long) num);
                    }
                    return String.valueOf(num);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        return cell.getStringCellValue();
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

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

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return driver.findElement(By.xpath(xpath));
            } catch (NoSuchElementException e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided locators. Last tried: " + xpaths[xpaths.length - 1]);
    }

    private WebElement waitVisible(String[] xpaths) {
        return wait.until(driver -> findElementWithFallbacks(xpaths));
    }

    private WebElement waitClickable(String[] xpaths) {
        return wait.until(ExpectedConditions.elementToBeClickable(findElementWithFallbacks(xpaths)));
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(xpaths);
        element.clear();
        element.sendKeys(text);
    }

    private void clickElement(String[] xpaths) {
        WebElement element = waitClickable(xpaths);
        element.click();
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
            log("WARNING: Company name is empty, skipping selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            String companySpanXpath = String.format("//*[normalize-space()='%s']", companyName);
            WebElement companyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));
            
            // Try to find a more robust clickable parent
            try {
                WebElement parentCard = companyElement.findElement(By.xpath("./ancestor::div[contains(@class, 'company-card') or contains(@class, 'selectable-item') or @role='button'][1]"));
                log("Found clickable parent card for company. Clicking it.");
                parentCard.click();
            } catch (Exception e) {
                log("WARNING: Could not find a specific parent card. Clicking the company name element directly.");
                companyElement.click();
            }
            waitForPageLoad();
        } catch (Exception e) {
            log("ERROR: Could not select company '" + companyName + "'. " + e.getMessage());
        }
    }

    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log("Found " + testData.size() + " test records in the Excel file.");
        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("==================================================");
            log("Executing Test Record " + (i + 1) + ": " + getOrDefault(data, "Task_Name", "N/A"));
            try {
                executeWorkflow(data);
                log("SUCCESS: Test Record " + (i + 1) + " completed successfully.");
            } catch (Exception e) {
                log("ERROR: Test Record " + (i + 1) + " failed. " + e.getMessage());
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from map
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
        String fileUploadBasePath = getOrDefault(data, "File_Upload_Base_Path", "C:\\Users\\sudhi\\Downloads\\");

        if (isFirstRecord) {
            log("Executing login sequence for the first record.");
            driver.get(BASE_URL);
            waitForPageLoad();

            // Enter Email with robust pattern
            try {
                log("Entering email: " + email);
                WebElement emailField = waitVisible(EMAIL_INPUT_LOCATORS);
                emailField.clear();
                sleep(500);
                emailField.sendKeys(email);
                sleep(1000);
                String enteredValue = emailField.getAttribute("value");
                if (!enteredValue.equals(email)) {
                    log("WARNING: Email not entered correctly, retrying character by character...");
                    emailField.clear();
                    sleep(500);
                    for (char c : email.toCharArray()) {
                        emailField.sendKeys(String.valueOf(c));
                        sleep(50);
                    }
                }
            } catch (Exception e) {
                log("FATAL: Could not enter email. Page URL: " + driver.getCurrentUrl() + ", Title: " + driver.getTitle());
                throw e;
            }
            
            clickElement(CONTINUE_BUTTON_LOCATORS);
            sleep(ACTION_PAUSE_MS);

            log("Entering password.");
            sendKeysToElement(PASSWORD_INPUT_LOCATORS, password);
            
            clickElement(LOGIN_BUTTON_LOCATORS);
            waitForPageLoad();

            selectCompanyByName(companyName);
            
            log("Navigating to Tasks page.");
            clickElement(ACTION_ITEMS_MENU_LOCATORS);
            sleep(ACTION_PAUSE_MS);
            clickElement(TASKS_SUBMENU_LOCATORS);
            waitForPageLoad();
        } else {
            log("Skipping login. Verifying user is on the Tasks page.");
            try {
                waitVisible(NEW_TASK_BUTTON_LOCATORS);
                log("User is on the Tasks page.");
            } catch (Exception e) {
                log("WARNING: Not on Tasks page. Attempting to re-navigate.");
                clickElement(ACTION_ITEMS_MENU_LOCATORS);
                sleep(ACTION_PAUSE_MS);
                clickElement(TASKS_SUBMENU_LOCATORS);
                waitForPageLoad();
            }
        }

        // --- Task Creation ---
        log("Starting task creation for: " + taskName);
        clickElement(NEW_TASK_BUTTON_LOCATORS);
        sleep(ACTION_PAUSE_MS);

        log("Entering task name.");
        sendKeysToElement(SHORT_TASK_NAME_INPUT_LOCATORS, taskName);

        log("Entering due date: " + dueDate);
        WebElement dueDateInput = waitVisible(DUE_DATE_INPUT_LOCATORS);
        dueDateInput.clear();
        dueDateInput.sendKeys(dueDate);
        dueDateInput.sendKeys(Keys.ENTER);
        sleep(500);

        // --- Assignment ---
        log("Processing assignment: " + assignedTo);
        if ("Assign to all Users".equalsIgnoreCase(assignedTo)) {
            clickElement(ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS);
        } else if ("Assign to all Admins".equalsIgnoreCase(assignedTo)) {
            clickElement(ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS);
        } else if (!assignedTo.isEmpty()) {
            String[] users = assignedTo.split(",");
            for (String user : users) {
                String trimmedUser = user.trim();
                if (trimmedUser.isEmpty()) continue;
                log("Assigning user: " + trimmedUser);
                WebElement searchInput = waitClickable(SEARCH_TEAM_MEMBERS_INPUT_LOCATORS);
                searchInput.click();
                searchInput.sendKeys(trimmedUser);
                sleep(2000); // Wait for autocomplete
                try {
                    String userOptionXpath = String.format("//ul[contains(@class, 'k-list') or contains(@class, 'autocomplete')]//li[contains(., '%s')]", trimmedUser);
                    WebElement userOption = driver.findElement(By.xpath(userOptionXpath));
                    userOption.click();
                    log("Selected " + trimmedUser + " from dropdown.");
                } catch (Exception e) {
                    log("WARNING: User '" + trimmedUser + "' not found in dropdown. Leaving as typed text.");
                }
                sleep(500);
            }
        }
        
        // --- Alignments ---
        if (!alignPriority.isEmpty()) {
            log("Aligning to priority: " + alignPriority);
            WebElement priorityInput = waitClickable(SEARCH_PRIORITIES_INPUT_LOCATORS);
            priorityInput.sendKeys(alignPriority);
            sleep(2500);
            try {
                String priorityOptionXpath = String.format("//ul[contains(@class, 'k-list')]//li[contains(., '%s')]", alignPriority);
                driver.findElement(By.xpath(priorityOptionXpath)).click();
                log("Selected priority from dropdown.");
            } catch (Exception e) {
                log("WARNING: Priority '" + alignPriority + "' not found in dropdown.");
            }
        }

        if (!alignHuddle.isEmpty()) {
            log("Aligning to huddle: " + alignHuddle);
            WebElement huddleInput = waitClickable(SEARCH_HUDDLES_INPUT_LOCATORS);
            huddleInput.sendKeys(alignHuddle);
            sleep(2500);
            try {
                String huddleOptionXpath = String.format("//ul[contains(@class, 'k-list')]//li[contains(., '%s')]", alignHuddle);
                driver.findElement(By.xpath(huddleOptionXpath)).click();
                log("Selected huddle from dropdown.");
            } catch (Exception e) {
                log("WARNING: Huddle '" + alignHuddle + "' not found in dropdown.");
            }
        }

        // --- Document Upload ---
        if (!documentName.isEmpty()) {
            log("Adding document: " + documentName);
            clickElement(DOCUMENTS_PLUS_ICON_LOCATORS);
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(DOCUMENT_NAME_INPUT_LOCATORS, documentName);
            
            if (!documentDescription.isEmpty()) {
                WebElement descInput = waitVisible(DOCUMENT_DESCRIPTION_INPUT_LOCATORS);
                js.executeScript("arguments[0].value = arguments[1];", descInput, documentDescription);
            }

            if ("File".equalsIgnoreCase(documentType) && !documentFile.isEmpty()) {
                log("Uploading file: " + documentFile);
                WebElement fileInput = findElementWithFallbacks(DOCUMENT_FILE_INPUT_LOCATORS);
                js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", fileInput);
                String fullPath = fileUploadBasePath + documentFile;
                fileInput.sendKeys(fullPath);
                sleep(2000);
            } else if ("Link".equalsIgnoreCase(documentType) && !documentUrl.isEmpty()) {
                log("Attaching link: " + documentUrl);
                clickElement(DOCUMENT_TYPE_DROPDOWN_LOCATORS);
                sleep(500);
                clickElement(DOCUMENT_TYPE_LINK_OPTION_LOCATORS);
                sleep(500);
                sendKeysToElement(DOCUMENT_URL_INPUT_LOCATORS, documentUrl);
            }
            
            clickElement(DOCUMENT_DIALOG_SAVE_BUTTON_LOCATORS);
            sleep(3000); // Wait for dialog to close and attachment to register
        }

        // --- Final Save ---
        log("Saving the task.");
        WebElement saveButton = findElementWithFallbacks(FINAL_TASK_SAVE_BUTTON_LOCATORS);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
        sleep(500);
        try {
            saveButton.click();
        } catch (Exception e) {
            log("WARNING: Standard click failed. Retrying with JavaScript click.");
            jsClick(saveButton);
        }
        
        waitForPageLoad();
        log("Task creation submitted. Verifying return to Manage Tasks page.");
        waitVisible(NEW_TASK_BUTTON_LOCATORS); // Confirmation
    }

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
        AlignTaskCreationTest test = new AlignTaskCreationTest();
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