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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignTaskAutomation {

    // ========== LOCATORS - DO NOT CHANGE ==========
    private static final String[] EMAIL_INPUT_LOCATORS = {
        "//input[@id='usernameField']",
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'Username')]",
        "//label[contains(normalize-space(),'Email')]/following-sibling::span//input",
        "//input[contains(@data-bind,'value: username')]"
    };

    private static final String[] CONTINUE_BUTTON_LOCATORS = {
        "//button[normalize-space()='Continue']",
        "//input[@value='Continue']",
        "//button[@id='continueButton']",
        "//button[contains(., 'Continue')]",
        "//div[@role='button' and contains(., 'Continue')]"
    };

    private static final String[] PASSWORD_INPUT_LOCATORS = {
        "//input[@id='passwordField']",
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]",
        "//label[contains(normalize-space(),'Password')]/following-sibling::span//input",
        "//input[contains(@data-bind,'value: password')]"
    };

    private static final String[] LOGIN_BUTTON_LOCATORS = {
        "//button[normalize-space()='Login']",
        "//button[normalize-space()='Sign In']",
        "//input[@value='Login']",
        "//button[@id='loginButton']",
        "//button[@type='submit']",
        "//div[@role='button' and (contains(., 'Login') or contains(., 'Sign In'))]"
    };

    private static final String[] ACTION_ITEMS_MENU_LOCATORS = {
        "//span[@class='k-input-inner' and text()='Action Items']",
        "//span[contains(@class, 'k-dropdown-wrap')]//span[text()='Action Items']",
        "//a[normalize-space()='Action Items']",
        "//div[normalize-space()='Action Items']",
        "//span[normalize-space()='Action Items']"
    };

    private static final String[] TASKS_SUBMENU_LOCATORS = {
        "//div[contains(@class, 'k-list-item-content') and normalize-space()='Tasks']",
        "//li[normalize-space()='Tasks']",
        "//a[normalize-space()='Tasks']",
        "//span[normalize-space()='Tasks']"
    };

    private static final String[] NEW_TASK_BUTTON_LOCATORS = {
        "//button[normalize-space()='New Task']",
        "//a[normalize-space()='New Task']",
        "//span[normalize-space()='New Task']/ancestor::button[1]",
        "//div[contains(@class, 'new-task-button')]",
        "//button[contains(., 'New Task')]"
    };

    private static final String[] SHORT_TASK_NAME_INPUT_LOCATORS = {
        "//input[@data-placeholder='Short Task Name']",
        "//label[normalize-space()='Short Task Name']/following-sibling::input",
        "//input[contains(@id, 'taskName')]",
        "//input[@name='taskName']",
        "//textarea[contains(@placeholder, 'Short Task Name')]"
    };

    private static final String[] DUE_DATE_PICKER_LOCATORS = {
        "//input[@data-placeholder='Select Date']",
        "//label[normalize-space()='Due Date']/following-sibling::span//input",
        "//input[contains(@id, 'dueDate')]",
        "//span[@aria-controls='taskDueDate_dateview']//input"
    };

    private static final String[] SEARCH_TEAM_MEMBERS_INPUT_LOCATORS = {
        "//input[@placeholder='Search or Invite Team Members']",
        "//label[contains(., 'Assign To')]/following-sibling::div//input",
        "//input[contains(@id, 'teamMembers') or contains(@id, 'assignee')]"
    };

    private static final String[] ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Users']/preceding-sibling::input[@type='checkbox']",
        "//label[normalize-space()='Assign to all Users']",
        "//input[@data-bind='checked: isAllUsersSelected']"
    };

    private static final String[] ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS = {
        "//label[normalize-space()='Assign to all Admins']/preceding-sibling::input[@type='checkbox']",
        "//label[normalize-space()='Assign to all Admins']",
        "//input[@data-bind='checked: isAllAdminsSelected']"
    };

    private static final String[] PRIORITIES_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Priorities']",
        "//label[contains(., 'Priorities')]/following-sibling::div//input"
    };

    private static final String[] HUDDLES_INPUT_LOCATORS = {
        "//input[@placeholder='Start typing to search Huddles']",
        "//label[contains(., 'Huddles')]/following-sibling::div//input"
    };

    private static final String[] DOCUMENTS_PLUS_ICON_LOCATORS = {
        "//h3[normalize-space()='Documents']/following-sibling::div//span[contains(@class, 'plus-circle')]",
        "//span[contains(@class, 'add-document-button')]",
        "//div[contains(@class, 'documents-section')]//a[contains(@class, 'add')]"
    };

    private static final String[] DOCUMENT_NAME_INPUT_LOCATORS = {
        "//input[@placeholder='Enter document name']",
        "//label[normalize-space()='Document Name']/following-sibling::input"
    };

    private static final String[] DOCUMENT_DESCRIPTION_INPUT_LOCATORS = {
        "//textarea[@placeholder='Enter a description...']",
        "//label[normalize-space()='Description']/following-sibling::textarea"
    };

    private static final String[] DOCUMENT_TYPE_DROPDOWN_LOCATORS = {
        "//label[normalize-space()='Type']/following-sibling::span[contains(@class, 'k-dropdown')]",
        "//input[@name='documentType_input']"
    };

    private static final String[] DOCUMENT_URL_INPUT_LOCATORS = {
        "//input[@placeholder='Enter Link...']",
        "//label[normalize-space()='URL']/following-sibling::input"
    };

    private static final String[] HIDDEN_FILE_INPUT_LOCATORS = {
        "//input[@type='file' and contains(@style, 'display: none')]",
        "//input[@type='file' and not(@class)]",
        "//input[@type='file']"
    };

    private static final String[] SAVE_DOCUMENT_BUTTON_LOCATORS = {
        "//div[contains(@class, 'document-dialog')]//button[normalize-space()='Save']",
        "//div[@aria-labelledby='documentUploadDialog_wnd_title']//button[normalize-space()='Save']"
    };

    private static final String[] SAVE_TASK_BUTTON_LOCATORS = {
        "//div[@class='task-form-footer']//button[normalize-space()='Save']",
        "//button[@id='saveTaskButton']",
        "//form[contains(@class, 'task-form')]//button[@type='submit' and contains(., 'Save')]"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/Users/sudhi/Downloads/AlignTestData.xlsx";
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    // ==================================================
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

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
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return new DecimalFormat("#").format(numericValue);
                    }
                    return String.valueOf(numericValue);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // Fallback for formula resulting in numeric
                        try {
                            return String.valueOf(cell.getNumericCellValue());
                        } catch (Exception ex) {
                            return "";
                        }
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    private void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(timestamp + " - " + message);
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
        return map.getOrDefault(key, defaultValue).trim();
    }

    private void selectCompanyByName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            log("WARNING: Company name is empty, skipping selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            sleep(2000); // Wait for company list to render
            String companySpanXpath = String.format("//span[normalize-space()='%s']", companyName);
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));
            
            try {
                // Try to find a clickable parent card/container
                WebElement parentCard = companySpan.findElement(By.xpath("./ancestor::div[contains(@class, 'company-card') or contains(@class, 'list-item') or @role='button'][1]"));
                log("Found clickable parent container for company. Clicking it.");
                parentCard.click();
            } catch (Exception e) {
                log("WARNING: Could not find a specific parent container. Clicking the company name span directly.");
                companySpan.click();
            }
            waitForPageLoad();
        } catch (Exception e) {
            log("WARNING: Could not select company '" + companyName + "'. Continuing with test. Error: " + e.getMessage());
        }
    }

    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log("Found " + testData.size() + " records in the Excel file.");
        for (int i = 0; i < testData.size(); i++) {
            log("==================================================");
            log("Executing record #" + (i + 1));
            log("==================================================");
            try {
                executeWorkflow(testData.get(i));
            } catch (Exception e) {
                log("ERROR processing record #" + (i + 1) + ": " + e.getMessage());
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

        // PHASE 1: User Authentication (First Task Only)
        if (isFirstRecord) {
            log("Executing login flow for the first record.");
            driver.get(BASE_URL);
            waitForPageLoad();

            // Enter Email
            try {
                log("Entering email: " + email);
                WebElement emailInput = waitVisible(EMAIL_INPUT_LOCATORS);
                emailInput.clear();
                sleep(500);
                emailInput.sendKeys(email);
                sleep(1000);
                String enteredValue = emailInput.getAttribute("value");
                if (!enteredValue.equals(email)) {
                    log("WARNING: Email not entered correctly, retrying character by character...");
                    emailInput.clear();
                    sleep(500);
                    for (char c : email.toCharArray()) {
                        emailInput.sendKeys(String.valueOf(c));
                        sleep(50);
                    }
                }
            } catch (Exception e) {
                log("FATAL: Could not enter email. Page URL: " + driver.getCurrentUrl() + ", Title: " + driver.getTitle());
                throw e;
            }

            // Click Continue
            try {
                log("Clicking 'Continue' button.");
                WebElement continueButton = findElementWithFallbacks(CONTINUE_BUTTON_LOCATORS);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", continueButton);
                sleep(500);
                try {
                    continueButton.click();
                } catch (Exception e) {
                    log("Click failed, trying JS click for 'Continue' button.");
                    jsClick(continueButton);
                }
                sleep(3000);
            } catch (NoSuchElementException e) {
                log("INFO: 'Continue' button not found, assuming single-page login.");
            }

            // Enter Password
            log("Entering password.");
            sendKeysToElement(PASSWORD_INPUT_LOCATORS, password);
            sleep(ACTION_PAUSE_MS);

            // Click Login
            log("Clicking 'Login' button.");
            clickElement(LOGIN_BUTTON_LOCATORS);
            waitForPageLoad();

            // Select Company
            selectCompanyByName(companyName);

            // Navigate to Tasks Page
            log("Navigating to Tasks page.");
            clickElement(ACTION_ITEMS_MENU_LOCATORS);
            sleep(ACTION_PAUSE_MS);
            clickElement(TASKS_SUBMENU_LOCATORS);
            waitForPageLoad();
        } else {
            log("Skipping login, session should be active.");
            try {
                waitVisible(NEW_TASK_BUTTON_LOCATORS);
                log("Confirmed on 'Manage Tasks' page.");
            } catch (Exception e) {
                log("WARNING: Not on 'Manage Tasks' page. Attempting to re-navigate.");
                clickElement(ACTION_ITEMS_MENU_LOCATORS);
                sleep(ACTION_PAUSE_MS);
                clickElement(TASKS_SUBMENU_LOCATORS);
                waitForPageLoad();
            }
        }

        // PHASE 2: Create Task (Repeats for Each Excel Row)
        if (taskName.isEmpty()) {
            log("Skipping task creation as Task_Name is empty.");
            return;
        }

        log("Starting creation of task: " + taskName);
        clickElement(NEW_TASK_BUTTON_LOCATORS);
        waitForPageLoad();

        log("Entering task name.");
        sendKeysToElement(SHORT_TASK_NAME_INPUT_LOCATORS, taskName);

        log("Entering due date: " + dueDate);
        WebElement datePicker = waitVisible(DUE_DATE_PICKER_LOCATORS);
        datePicker.clear();
        datePicker.sendKeys(dueDate);
        datePicker.sendKeys(Keys.ENTER);
        sleep(ACTION_PAUSE_MS);

        log("Processing team member assignment: " + assignedTo);
        WebElement assignmentSection = waitVisible(SEARCH_TEAM_MEMBERS_INPUT_LOCATORS);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", assignmentSection);
        sleep(500);

        if (assignedTo.equalsIgnoreCase("Assign to all Users")) {
            log("Assigning to all users.");
            clickElement(ASSIGN_TO_ALL_USERS_CHECKBOX_LOCATORS);
        } else if (assignedTo.equalsIgnoreCase("Assign to all Admins")) {
            log("Assigning to all admins.");
            clickElement(ASSIGN_TO_ALL_ADMINS_CHECKBOX_LOCATORS);
        } else if (!assignedTo.isEmpty()) {
            String[] emails = assignedTo.split(",");
            for (String userEmail : emails) {
                userEmail = userEmail.trim();
                if (userEmail.isEmpty()) continue;
                log("Assigning to user: " + userEmail);
                WebElement searchInput = waitClickable(SEARCH_TEAM_MEMBERS_INPUT_LOCATORS);
                searchInput.sendKeys(userEmail);
                sleep(2000); // Wait for autocomplete
                try {
                    String userOptionXpath = String.format("//li[contains(., '%s')]", userEmail);
                    WebElement userOption = driver.findElement(By.xpath(userOptionXpath));
                    userOption.click();
                    log("Selected user from dropdown.");
                } catch (Exception e) {
                    log("WARNING: User '" + userEmail + "' not found in dropdown. Leaving as typed text.");
                }
                searchInput.clear();
                sleep(500);
            }
        }

        if (!alignPriority.isEmpty()) {
            log("Aligning to priority: " + alignPriority);
            WebElement priorityInput = waitVisible(PRIORITIES_INPUT_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", priorityInput);
            priorityInput.sendKeys(alignPriority);
            sleep(2500);
            try {
                String priorityOptionXpath = String.format("//li[normalize-space()='%s']", alignPriority);
                driver.findElement(By.xpath(priorityOptionXpath)).click();
                log("Selected priority from dropdown.");
            } catch (Exception e) {
                log("WARNING: Priority '" + alignPriority + "' not found in dropdown.");
            }
        }

        if (!alignHuddle.isEmpty()) {
            log("Aligning to huddle: " + alignHuddle);
            WebElement huddleInput = waitVisible(HUDDLES_INPUT_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", huddleInput);
            huddleInput.sendKeys(alignHuddle);
            sleep(2500);
            try {
                String huddleOptionXpath = String.format("//li[normalize-space()='%s']", alignHuddle);
                driver.findElement(By.xpath(huddleOptionXpath)).click();
                log("Selected huddle from dropdown.");
            } catch (Exception e) {
                log("WARNING: Huddle '" + alignHuddle + "' not found in dropdown.");
            }
        }

        if (!documentName.isEmpty()) {
            log("Adding document: " + documentName);
            WebElement plusIcon = waitClickable(DOCUMENTS_PLUS_ICON_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", plusIcon);
            plusIcon.click();
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(DOCUMENT_NAME_INPUT_LOCATORS, documentName);

            if (!documentDescription.isEmpty()) {
                log("Adding document description.");
                WebElement descInput = waitVisible(DOCUMENT_DESCRIPTION_INPUT_LOCATORS);
                js.executeScript("arguments[0].value = arguments[1];", descInput, documentDescription);
            }

            if (documentType.equalsIgnoreCase("Link") && !documentUrl.isEmpty()) {
                log("Attaching a link: " + documentUrl);
                clickElement(DOCUMENT_TYPE_DROPDOWN_LOCATORS);
                sleep(500);
                String linkOptionXpath = "//li[normalize-space()='Link']";
                driver.findElement(By.xpath(linkOptionXpath)).click();
                sendKeysToElement(DOCUMENT_URL_INPUT_LOCATORS, documentUrl);
            } else if (documentType.equalsIgnoreCase("File") && !documentFile.isEmpty()) {
                log("Uploading a file: " + documentFile);
                String fullPath = fileUploadBasePath + documentFile;
                WebElement fileInput = findElementWithFallbacks(HIDDEN_FILE_INPUT_LOCATORS);
                js.executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';", fileInput);
                fileInput.sendKeys(fullPath);
                sleep(2000); // Wait for upload to process
            }

            log("Saving document attachment.");
            WebElement saveDocButton = waitClickable(SAVE_DOCUMENT_BUTTON_LOCATORS);
            js.executeScript("arguments[0].scrollIntoView(true);", saveDocButton);
            saveDocButton.click();
            sleep(3000); // Wait for dialog to close
        }

        log("Submitting the task.");
        WebElement saveTaskButton = findElementWithFallbacks(SAVE_TASK_BUTTON_LOCATORS);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveTaskButton);
        sleep(500);
        try {
            saveTaskButton.click();
        } catch (Exception e) {
            log("Click failed, trying JS click for 'Save Task' button.");
            jsClick(saveTaskButton);
        }
        waitForPageLoad();

        log("Verifying return to 'Manage Tasks' page.");
        waitVisible(NEW_TASK_BUTTON_LOCATORS);
        log("Task '" + taskName + "' created successfully.");
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
        log("Cleaning up and closing WebDriver.");
        if (driver != null) {
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