package com.selenium.standalone;

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignTaskAutomation {

    // ========== LOCATORS ==========
    private static final String[] EMAIL_INPUT = {
        "//input[@id='usernameField']",
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@data-bind,'value: username')]",
        "//span[contains(@class,'k-widget')]//input[@id='usernameField']",
        "//input[@placeholder='Email']"
    };
    private static final String[] CONTINUE_BUTTON = {
        "//button[normalize-space()='Continue']",
        "//input[@value='Continue']",
        "//button[@id='continueButton']",
        "//span[normalize-space()='Continue']/ancestor::button[1]"
    };
    private static final String[] PASSWORD_INPUT = {
        "//input[@id='passwordField']",
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@data-bind,'value: password')]",
        "//span[contains(@class,'k-widget')]//input[@id='passwordField']",
        "//input[@placeholder='Password']"
    };
    private static final String[] LOGIN_BUTTON = {
        "//button[normalize-space()='Login']",
        "//button[@id='loginButton']",
        "//input[@value='Login']",
        "//button[@type='submit']",
        "//span[normalize-space()='Login']/ancestor::button[1]"
    };
    private static final String[] ACTION_ITEMS_MENU = {
        "//span[@class='title'][normalize-space()='Action Items']",
        "//li[@role='menuitem']//span[normalize-space()='Action Items']",
        "//a[.//span[normalize-space()='Action Items']]",
        "//span[contains(text(),'Action Items')]/ancestor::li[1]"
    };
    private static final String[] TASKS_SUBMENU = {
        "//span[@class='title'][normalize-space()='Tasks']",
        "//div[contains(@class,'k-animation-container')]//span[normalize-space()='Tasks']",
        "//a[.//span[normalize-space()='Tasks']]",
        "//ul[@role='menu']//span[contains(text(),'Tasks')]"
    };
    private static final String[] NEW_TASK_BUTTON = {
        "//button[normalize-space()='New Task']",
        "//a[normalize-space()='New Task']",
        "//span[normalize-space()='New Task']/ancestor::button[1]",
        "//div[@id='task-list-container']//button[contains(.,'New Task')]"
    };
    private static final String[] SHORT_TASK_NAME_INPUT = {
        "//input[@name='TaskName']",
        "//input[@data-bind='value: TaskName']",
        "//label[contains(text(),'Short Task Name')]/following-sibling::div//input",
        "//input[@placeholder='Short Task Name']"
    };
    private static final String[] DUE_DATE_INPUT = {
        "//input[@name='DueDate']",
        "//input[@data-bind='value: DueDate']",
        "//span[@aria-controls='taskDueDate_dateview']//input",
        "//label[contains(text(),'Due Date')]/following-sibling::div//input"
    };
    private static final String[] ASSIGN_TO_ALL_USERS_CHECKBOX = {
        "//label[normalize-space()='Assign to all Users']/preceding-sibling::input[@type='checkbox']",
        "//input[@data-bind='checked: AssignToAll']",
        "//label[contains(text(),'Assign to all Users')]/..//input"
    };
    private static final String[] ASSIGN_TO_ALL_ADMINS_CHECKBOX = {
        "//label[normalize-space()='Assign to all Admins']/preceding-sibling::input[@type='checkbox']",
        "//input[@data-bind='checked: AssignToAllAdmins']",
        "//label[contains(text(),'Assign to all Admins')]/..//input"
    };
    private static final String[] SEARCH_TEAM_MEMBERS_INPUT = {
        "//input[@placeholder='Search or Invite Team Members']",
        "//input[@aria-label='Search or Invite Team Members']",
        "//div[contains(@class,'assignment-section')]//input[contains(@class,'k-input')]"
    };
    private static final String[] SEARCH_PRIORITIES_INPUT = {
        "//input[@placeholder='Start typing to search Priorities']",
        "//div[@data-bind='with: priorityAlignments']//input",
        "//label[contains(text(),'Priorities')]/..//input"
    };
    private static final String[] SEARCH_HUDDLES_INPUT = {
        "//input[@placeholder='Start typing to search Huddles']",
        "//div[@data-bind='with: huddleAlignments']//input",
        "//label[contains(text(),'Huddles')]/..//input"
    };
    private static final String[] ADD_DOCUMENT_BUTTON = {
        "//div[contains(@class,'documents-section')]//i[contains(@class,'ico-plus-circle')]",
        "//h5[normalize-space()='Documents']/following-sibling::div//i[contains(@class,'plus')]",
        "//button[@aria-label='Add Document']"
    };
    private static final String[] DOC_NAME_INPUT = {
        "//div[@class='k-window-content']//input[@name='DocumentName']",
        "//input[@placeholder='Enter document name']"
    };
    private static final String[] DOC_DESCRIPTION_INPUT = {
        "//div[@class='k-window-content']//textarea[@name='Description']",
        "//textarea[@placeholder='Enter a description']"
    };
    private static final String[] DOC_TYPE_DROPDOWN = {
        "//div[@class='k-window-content']//span[@aria-owns='documentType_listbox']",
        "//label[normalize-space()='Type']/following-sibling::span//span[@class='k-select']"
    };
    private static final String[] DOC_TYPE_LINK_OPTION = {
        "//ul[@id='documentType_listbox']/li[normalize-space()='Link']"
    };
    private static final String[] DOC_URL_INPUT = {
        "//div[@class='k-window-content']//input[@name='Url']",
        "//input[@placeholder='Enter Link...']"
    };
    private static final String[] HIDDEN_FILE_INPUT = {
        "//input[@type='file' and @name='files']",
        "//div[@class='k-window-content']//input[@type='file']"
    };
    private static final String[] DOC_SAVE_BUTTON = {
        "//div[@class='k-window-content']//button[normalize-space()='Save']",
        "//div[contains(@class,'k-dialog')]//button[contains(text(),'Save')]"
    };
    private static final String[] MAIN_SAVE_BUTTON = {
        "//div[@class='task-form-buttons']//button[normalize-space()='Save']",
        "//button[@data-bind='click: saveTask, enable: canSave']"
    };

    // ========== CONFIGURATION ==========
    private static final String EXCEL_FILE_PATH = "C:/Users/sudhi/Downloads/Align_Test_Data.xlsx";
    private static final String FILE_UPLOAD_BASE_PATH = "C:/Users/sudhi/Downloads/";
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    // ========== CLASS VARIABLES ==========
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    // ========== EXCEL READER ==========
    private static class ExcelReader {
        public List<Map<String, String>> readExcel(String filePath) throws IOException {
            List<Map<String, String>> testData = new ArrayList<>();
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                workbook.close();
                fis.close();
                return testData;
            }

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
            workbook.close();
            fis.close();
            return testData;
        }

        private String getCellValueAsString(Cell cell) {
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
                        return String.format("%d", (long) num);
                    } else {
                        return String.valueOf(num);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
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
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("Sleep interrupted: " + e.getMessage());
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
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private WebElement waitVisible(String[] xpaths) {
        return wait.until(driver -> findElementWithFallbacks(xpaths));
    }

    private WebElement waitClickable(String[] xpaths) {
        return wait.until(ExpectedConditions.elementToBeClickable(findElementWithFallbacks(xpaths)));
    }

    private void clickElement(String[] xpaths) {
        try {
            WebElement element = waitClickable(xpaths);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            sleep(200);
            element.click();
        } catch (Exception e) {
            log("Standard click failed for " + xpaths[0] + ". Trying JavaScript click. Error: " + e.getMessage());
            try {
                WebElement element = findElementWithFallbacks(xpaths);
                jsClick(element);
            } catch (Exception jsEx) {
                log("JavaScript click also failed for " + xpaths[0] + ". Error: " + jsEx.getMessage());
                takeScreenshot("click_failure");
                throw jsEx;
            }
        }
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(xpaths);
        element.clear();
        element.sendKeys(text);
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
            log("Company Name is not provided. Skipping selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            String[] companyCardXPaths = {
                String.format("//div[contains(@class,'company-name')][normalize-space()='%s']/ancestor::div[contains(@class,'company-card')]", companyName),
                String.format("//div[contains(text(),'%s')]/ancestor::a", companyName)
            };
            clickElement(companyCardXPaths);
            waitForPageLoad();
        } catch (Exception e) {
            log("WARNING: Could not select company '" + companyName + "'. Continuing... Error: " + e.getMessage());
        }
    }
    
    private void takeScreenshot(String context) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String screenshotPath = "screenshots/";
            Files.createDirectories(Paths.get(screenshotPath));
            File destFile = new File(screenshotPath + context + "_" + timestamp + ".png");
            Files.copy(srcFile.toPath(), destFile.toPath());
            log("Screenshot saved to: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            log("Failed to take screenshot: " + e.getMessage());
        }
    }

    // ========== TEST EXECUTION FLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData;
        try {
            testData = new ExcelReader().readExcel(EXCEL_FILE_PATH);
            log("Successfully read " + testData.size() + " records from Excel.");
        } catch (IOException e) {
            log("FATAL: Could not read Excel file at " + EXCEL_FILE_PATH + ". Error: " + e.getMessage());
            return;
        }

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> rowData = testData.get(i);
            log("\n--- Processing Record " + (i + 1) + "/" + testData.size() + ": Task Name '" + getOrDefault(rowData, "Task_Name", "N/A") + "' ---");
            try {
                executeWorkflow(rowData);
            } catch (Exception e) {
                log("ERROR processing record " + (i + 1) + ". Task may not have been created. Error: " + e.getMessage());
                takeScreenshot("record_failure_" + (i + 1));
                // Recovery attempt
                try {
                    log("Attempting to recover by navigating back to Tasks page...");
                    driver.get(BASE_URL + "Tasks"); // A more direct navigation might be needed
                    waitForPageLoad();
                    waitVisible(NEW_TASK_BUTTON);
                    log("Recovery successful. Continuing with next record.");
                } catch (Exception recoveryEx) {
                    log("FATAL: Recovery failed. Aborting test. Error: " + recoveryEx.getMessage());
                    break; // Stop the loop if recovery fails
                }
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // PHASE 1: User Authentication (First Task Only)
        if (isFirstRecord) {
            log("Executing PHASE 1: User Authentication");
            driver.get(BASE_URL);
            waitForPageLoad();

            sendKeysToElement(EMAIL_INPUT, getOrDefault(data, "Email", ""));
            log("Entered Email.");
            sleep(ACTION_PAUSE_MS);

            try {
                clickElement(CONTINUE_BUTTON);
                log("Clicked Continue button.");
                sleep(ACTION_PAUSE_MS);
            } catch (Exception e) {
                log("Continue button not found or not needed. Proceeding to password.");
            }

            sendKeysToElement(PASSWORD_INPUT, getOrDefault(data, "Password", ""));
            log("Entered Password.");
            sleep(ACTION_PAUSE_MS);

            clickElement(LOGIN_BUTTON);
            log("Clicked Login button.");
            waitForPageLoad();

            selectCompanyByName(getOrDefault(data, "Company_Name", ""));
            log("Company selected.");

            clickElement(ACTION_ITEMS_MENU);
            log("Clicked 'Action Items' menu.");
            sleep(ACTION_PAUSE_MS);

            clickElement(TASKS_SUBMENU);
            log("Clicked 'Tasks' submenu.");
            waitForPageLoad();
        }

        // Verify we are on the Manage Tasks page before creating a new task
        log("Verifying presence on Manage Tasks page.");
        waitVisible(NEW_TASK_BUTTON);
        log("On Manage Tasks page. Proceeding to create task.");

        // PHASE 2: Create Task (Repeats for Each Excel Row)
        log("Executing PHASE 2: Create Task");
        clickElement(NEW_TASK_BUTTON);
        log("Clicked 'New Task' button.");
        waitForPageLoad();

        sendKeysToElement(SHORT_TASK_NAME_INPUT, getOrDefault(data, "Task_Name", ""));
        log("Entered Task Name.");
        sleep(ACTION_PAUSE_MS);

        WebElement dueDateInput = waitVisible(DUE_DATE_INPUT);
        dueDateInput.clear();
        dueDateInput.sendKeys(getOrDefault(data, "Due_Date", ""));
        dueDateInput.sendKeys(Keys.ENTER);
        log("Entered Due Date.");
        sleep(ACTION_PAUSE_MS);

        // Handle Assignments
        String assignedTo = getOrDefault(data, "Assigned_To", "");
        if (assignedTo.equalsIgnoreCase("Assign to all Users")) {
            clickElement(ASSIGN_TO_ALL_USERS_CHECKBOX);
            log("Selected 'Assign to all Users'.");
        } else if (assignedTo.equalsIgnoreCase("Assign to all Admins")) {
            clickElement(ASSIGN_TO_ALL_ADMINS_CHECKBOX);
            log("Selected 'Assign to all Admins'.");
        } else if (!assignedTo.isEmpty()) {
            String[] emails = assignedTo.split(",");
            for (String email : emails) {
                String trimmedEmail = email.trim();
                if (trimmedEmail.isEmpty()) continue;
                WebElement searchInput = waitVisible(SEARCH_TEAM_MEMBERS_INPUT);
                searchInput.sendKeys(trimmedEmail);
                log("Searching for user: " + trimmedEmail);
                sleep(2500); // Wait for autocomplete
                try {
                    String[] userOption = {String.format("//ul[contains(@id,'_listbox')]//li[contains(.,'%s')]", trimmedEmail)};
                    clickElement(userOption);
                    log("Selected user '" + trimmedEmail + "' from dropdown.");
                } catch (Exception e) {
                    log("User '" + trimmedEmail + "' not found in dropdown. Leaving as typed text.");
                    searchInput.clear(); // Clear for next user
                }
                sleep(ACTION_PAUSE_MS);
            }
        }

        // Handle Priority Alignment
        String alignPriority = getOrDefault(data, "Align_Priority", "");
        if (!alignPriority.isEmpty()) {
            WebElement priorityInput = waitVisible(SEARCH_PRIORITIES_INPUT);
            priorityInput.sendKeys(alignPriority);
            log("Searching for Priority: " + alignPriority);
            sleep(2500); // Wait for dropdown
            try {
                String[] priorityOption = {String.format("//ul[contains(@id,'_listbox')]//li[contains(.,'%s')]", alignPriority)};
                clickElement(priorityOption);
                log("Selected Priority '" + alignPriority + "'.");
            } catch (Exception e) {
                log("Priority '" + alignPriority + "' not found in dropdown.");
            }
            sleep(ACTION_PAUSE_MS);
        }

        // Handle Huddle Alignment
        String alignHuddle = getOrDefault(data, "Align_Huddle", "");
        if (!alignHuddle.isEmpty()) {
            WebElement huddleInput = waitVisible(SEARCH_HUDDLES_INPUT);
            huddleInput.sendKeys(alignHuddle);
            log("Searching for Huddle: " + alignHuddle);
            sleep(2500); // Wait for dropdown
            try {
                String[] huddleOption = {String.format("//ul[contains(@id,'_listbox')]//li[contains(.,'%s')]", alignHuddle)};
                clickElement(huddleOption);
                log("Selected Huddle '" + alignHuddle + "'.");
            } catch (Exception e) {
                log("Huddle '" + alignHuddle + "' not found in dropdown.");
            }
            sleep(ACTION_PAUSE_MS);
        }

        // Handle Document Upload
        String docName = getOrDefault(data, "Document_Name", "");
        if (!docName.isEmpty()) {
            clickElement(ADD_DOCUMENT_BUTTON);
            log("Opened Document Upload dialog.");
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(DOC_NAME_INPUT, docName);
            log("Entered Document Name.");

            String docDesc = getOrDefault(data, "Document_Description", "");
            if (!docDesc.isEmpty()) {
                WebElement descInput = waitVisible(DOC_DESCRIPTION_INPUT);
                js.executeScript("arguments[0].value = arguments[1];", descInput, docDesc);
                log("Entered Document Description via JS.");
            }

            String docType = getOrDefault(data, "Document_Type", "File");
            if (docType.equalsIgnoreCase("Link")) {
                clickElement(DOC_TYPE_DROPDOWN);
                sleep(500);
                clickElement(DOC_TYPE_LINK_OPTION);
                log("Selected Document Type 'Link'.");
                sendKeysToElement(DOC_URL_INPUT, getOrDefault(data, "Document_URL", ""));
                log("Entered Document URL.");
            } else { // Default to "File"
                String docFile = getOrDefault(data, "Document_File", "");
                if (!docFile.isEmpty()) {
                    String fullPath = FILE_UPLOAD_BASE_PATH + docFile;
                    WebElement fileInput = findElementWithFallbacks(HIDDEN_FILE_INPUT);
                    js.executeScript("arguments[0].style.display='block';", fileInput);
                    fileInput.sendKeys(fullPath);
                    log("Uploading file: " + fullPath);
                    sleep(2000); // Wait for upload to process
                }
            }
            clickElement(DOC_SAVE_BUTTON);
            log("Saved document attachment.");
            sleep(3000); // Wait for dialog to close
        }

        // Submit Task
        clickElement(MAIN_SAVE_BUTTON);
        log("Clicked main Save button to create task.");
        waitForPageLoad();

        // Verify return to Manage Tasks page
        waitVisible(NEW_TASK_BUTTON);
        log("Task created successfully. Returned to Manage Tasks page.");
    }

    // ========== SETUP & TEARDOWN ==========
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
            test.log("An unexpected error occurred during the test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}