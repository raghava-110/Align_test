package com.aligntest;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignTestDataDriven {

    // ========== LOCATORS - GENERATED WITH FALLBACKS ==========
    private static final String[] email_input = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']", "//input[contains(@data-bind,'value: username')]", "//input[@id='usernameField']", "//span[contains(@class,'k-widget')]//input[@id='usernameField']", "//input[@name='username']", "//input[@type='email']", "//input[@placeholder='Email Address']", "//input[contains(@aria-label, 'Email')]"
    };
    private static final String[] continue_button = {
        "//button[contains(text(),'Continue')]", "//button[@id='continueButton']", "//button[@type='submit']"
    };
    private static final String[] password_input = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']", "//input[contains(@data-bind,'value: password')]", "//input[@id='passwordField']", "//span[contains(@class,'k-widget')]//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']", "//input[@placeholder='Password']", "//input[contains(@aria-label, 'Password')]"
    };
    private static final String[] login_button = {
        "//button[contains(text(),'Login')]", "//button[@id='loginButton']", "//button[@type='submit']"
    };
    private static final String[] administration_link = {
        "//div[@id='header-navigation']//a[normalize-space()='Administration']", "//a[contains(., 'Administration') and contains(@class, 'nav-link')]", "//li/a[normalize-space()='Administration']"
    };
    private static final String[] manage_users_link = {
        "//a[@href='/Users' and normalize-space()='Manage Users']", "//ul[contains(@class,'dropdown-menu')]//a[normalize-space()='Manage Users']", "//a[contains(text(),'Manage Users')]"
    };
    private static final String[] invite_users_button = {
        "//button[normalize-space()='Invite Users']", "//button[contains(., 'Invite Users')]", "//a[normalize-space()='Invite Users']"
    };
    private static final String[] invite_user_email_input = {
        "//textarea[@id='inviteUserContentPopup_input_emails']", "//textarea[@placeholder='Enter one or more emails, separated by commas']", "//label[contains(text(),'Email')]/following-sibling::*/textarea"
    };
    private static final String[] admin_checkbox = {
        "//label[normalize-space()='Admin']/preceding-sibling::input[@type='checkbox']", "//input[@id='inviteUserContentPopup_input_isAdmin']"
    };
    private static final String[] send_invite_button = {
        "//button[normalize-space()='Send Invite']", "//div[@class='modal-footer']//button[contains(.,'Send Invite')]"
    };
    private static final String[] first_name_input = {
        "//input[@id='firstName']", "//input[@name='firstName']", "//input[@placeholder='First Name']"
    };
    private static final String[] last_name_input = {
        "//input[@id='lastName']", "//input[@name='lastName']", "//input[@placeholder='Last Name']"
    };
    private static final String[] create_password_input = {
        "//input[@id='password']", "//input[@name='password' and @type='password']", "//input[@placeholder='Create Password']"
    };
    private static final String[] accept_invite_button = {
        "//button[normalize-space()='Accept Invite']", "//button[contains(., 'Accept Invite') and @type='submit']"
    };
    private static final String[] metrics_link = {
        "//div[@id='header-navigation']//a[normalize-space()='Metrics']", "//a[@href='/CompanyMetrics' and contains(., 'Metrics')]", "//li/a[normalize-space()='Metrics']"
    };
    private static final String[] add_metric_button = {
        "//button[normalize-space()='Add Metric']", "//a[normalize-space()='Add Metric']", "//button[contains(., 'Add Metric')]"
    };
    private static final String[] metric_name_input = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']", "//input[@placeholder='Name of the Metric']", "//label[normalize-space()='Name']/following-sibling::input"
    };
    private static final String[] value_source_dropdown_trigger = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[@class='k-select']", "//span[@aria-owns='selectedIntegration_listbox']"
    };
    private static final String[] formula_builder_metric_search_input = {
        "//div[@data-bind='with: calculated']//input[@placeholder='Name or Owner']", "//div[contains(@class,'formula-builder')]//input[contains(@placeholder,'Name or Owner')]"
    };
    private static final String[] formula_confirm_button = {
        "//span[@title='Validate and Calculate']", "//div[@class='formula-builder-actions']//span[contains(@class, 'ico-checkmark')]"
    };
    private static final String[] cadence_dropdown_trigger = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[@class='k-select']", "//span[@aria-owns='selectedCadence_listbox']"
    };
    private static final String[] resets_on_dropdown_trigger = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[@class='k-select']", "//span[@aria-owns='selectedResetWeekDay_listbox']"
    };
    private static final String[] save_button = {
        "//button[@id='saveButton']", "//button[normalize-space()='Save']", "//button[contains(., 'Save') and not(@style='display: none;')]"
    };
    private static final String[] dashboards_link = {
        "//div[@id='header-navigation']//a[normalize-space()='Dashboards']", "//a[contains(., 'Dashboards') and contains(@class, 'nav-link')]", "//li/a[normalize-space()='Dashboards']"
    };
    private static final String[] my_dashboard_link = {
        "//a[@href='/Dashboard' and normalize-space()='My Dashboard']", "//ul[contains(@class,'dropdown-menu')]//a[normalize-space()='My Dashboard']"
    };
    private static final String[] edit_kpi_icon = {
        "//span[@title='Edit Kpi']", "//div[@id='my-kpis']//span[contains(@class, 'ico-edit')]"
    };
    private static final String[] edit_kpi_modal_save_button = {
        "//div[contains(@class,'edit-kpi-modal')]//button[normalize-space()='Save']", "//div[@id='editKpiModal']//button[contains(.,'Save')]"
    };
    private static final String[] target_tab = {
        "//a[normalize-space()='Target']", "//ul[contains(@class,'nav-tabs')]//a[contains(text(),'Target')]"
    };
    private static final String[] custom_target_radio = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']", "//input[@name='targetTemplate' and @value='1']"
    };
    private static final String[] time_based_target_radio = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']", "//input[@name='targetTemplate' and @value='2']"
    };
    private static final String[] level_1_input = {
        "//input[@data-bind='value: companyMetric.KPITarget.Level1']", "//label[contains(text(),'Level 1')]/following-sibling::input"
    };
    private static final String[] level_2_input = {
        "//input[@data-bind='value: companyMetric.KPITarget.Level2']", "//label[contains(text(),'Level 2')]/following-sibling::input"
    };
    private static final String[] level_3_input = {
        "//input[@data-bind='value: companyMetric.KPITarget.Level3']", "//label[contains(text(),'Level 3')]/following-sibling::input"
    };
    private static final String[] level_4_input = {
        "//input[@data-bind='value: companyMetric.KPITarget.Level4']", "//label[contains(text(),'Level 4')]/following-sibling::input"
    };
    private static final String[] start_value_input = {
        "//input[@data-bind='value: companyMetric.KPITarget.StartValue']", "//label[normalize-space()='Start']/following-sibling::input"
    };
    private static final String[] target_value_input = {
        "//input[@data-bind='value: companyMetric.KPITarget.TargetValue']", "//label[normalize-space()='Target']/following-sibling::input"
    };
    private static final String[] metric_source_search_input = {
        "//span[@aria-owns='companyMetricSource_listbox']//input", "//input[@placeholder='Search Metrics...']"
    };

    // ========== CONFIGURATION ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/excel/file.xlsx"; // <-- CHANGE THIS PATH
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    // ========== CLASS VARIABLES ==========
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
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        rowData.put(headers.get(j), getCellValueAsString(cell));
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
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
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
                        // Fallback for formula resulting in a number
                        double formulaValue = cell.getNumericCellValue();
                        if (formulaValue == Math.floor(formulaValue)) {
                            return new DecimalFormat("#").format(formulaValue);
                        }
                        return String.valueOf(formulaValue);
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // ========== HELPER METHODS ==========
    private void log(String message) {
        System.out.println(message);
    }

    private void logStart(String stepName) {
        log("--- START: " + stepName + " ---");
    }

    private void logPass(String message) {
        log("--- PASS: " + message + " ---");
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
                return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            } catch (Exception e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    
    private void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].click();", element);
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
    }

    private void typeSlowly(WebElement element, String text) {
        if (text == null || text.isEmpty()) return;
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    private void selectCompanyByName(String companyName) {
        logStart("Selecting company: " + companyName);
        waitForPageLoad();
        String companySpanXpath = String.format("//div[contains(@class,'company-name') and normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class,'company-list-item')]",
            "/ancestor::div[contains(@class,'company-card')]",
            "/ancestor::div[@role='button']",
            "/ancestor::a"
        };

        for (String selector : parentSelectors) {
            try {
                WebElement parent = companySpan.findElement(By.xpath("." + selector));
                log("Found clickable parent container. Clicking it.");
                jsClick(parent);
                logPass("Company selected successfully.");
                return;
            } catch (Exception e) {
                // Parent not found, try next selector
            }
        }

        log("Could not find a preferred parent container. Clicking the company name span directly.");
        jsClick(companySpan);
        logPass("Company selected successfully.");
    }
    
    private void selectDropdownByVisibleText(String[] triggerXpaths, String text) {
        if (text == null || text.isEmpty()) return;
        clickElement(triggerXpaths);
        sleep(500);
        String optionXpath = String.format("//li[normalize-space()='%s']", text);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        jsClick(option);
    }

    private void searchAndSelectFromList(String[] searchInputXpaths, String searchText) {
        if (searchText == null || searchText.isEmpty()) return;
        WebElement searchInput = findElementWithFallbacks(searchInputXpaths);
        typeSlowly(searchInput, searchText);
        sleep(1000); // Wait for search results
        String optionXpath = String.format("//li[contains(normalize-space(),'%s')]", searchText);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        jsClick(option);
    }

    // ========== TEST WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n=============================================================");
            log("Executing Test Case Record " + (i + 1) + ": " + data.getOrDefault("name", "N/A"));
            log("=============================================================");
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                log("!!!!!!!!!! ERROR in record " + (i + 1) + ": " + e.getMessage() + " !!!!!!!!!!");
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from map
        String testUserEmail = data.getOrDefault("test_user_email", "");
        String testUserPassword = data.getOrDefault("test_user_password", "");
        String metricName = data.getOrDefault("Metric_Name", "") + " " + System.currentTimeMillis();
        String valueSource = data.getOrDefault("Value_Source", "");
        String firstMetric = data.getOrDefault("First_Metric", "");
        String operator = data.getOrDefault("Operator", "");
        String secondMetric = data.getOrDefault("Second_Metric", "");
        String metricToSelect = data.getOrDefault("Metric_to_Select", "");
        String cadence = data.getOrDefault("Cadence", "");
        String resetsOn = data.getOrDefault("Resets_On", "");
        String targetType = data.getOrDefault("Target_Type", "");
        String level1 = data.getOrDefault("Level_1", "");
        String level2 = data.getOrDefault("Level_2", "");
        String level3 = data.getOrDefault("Level_3", "");
        String level4 = data.getOrDefault("Level_4", "");
        String startValue = data.getOrDefault("Start_Value", "");
        String targetValue = data.getOrDefault("Target_Value", "");
        String inviteUserEmail = data.getOrDefault("Invite_User_Email", "");
        String admin = data.getOrDefault("Admin", "");
        String userFirstName = data.getOrDefault("User_First_Name", "");
        String userLastName = data.getOrDefault("User_Last_Name", "");
        String userPassword = data.getOrDefault("User_Password", "");

        if (isFirstRecord) {
            logStart("Step 1-4: Login and Company Selection");
            driver.get(BASE_URL);
            sendKeysToElement(email_input, testUserEmail);
            clickElement(continue_button);
            sendKeysToElement(password_input, testUserPassword);
            clickElement(login_button);
            waitForPageLoad();
            selectCompanyByName("Mindlinks QA"); // Company name from analysis
            waitForPageLoad();
            logPass("Login and company selection complete.");
        }

        if (!inviteUserEmail.isEmpty()) {
            logStart("Step 5-10: Invite and Accept New User");
            new Actions(driver).moveToElement(findElementWithFallbacks(administration_link)).perform();
            clickElement(manage_users_link);
            waitForPageLoad();
            clickElement(invite_users_button);
            sendKeysToElement(invite_user_email_input, inviteUserEmail);
            if ("Yes".equalsIgnoreCase(admin)) {
                clickElement(admin_checkbox);
            }
            clickElement(send_invite_button);
            sleep(ACTION_PAUSE_MS);
            log("Invitation sent. Accepting invite...");
            String acceptXpath = String.format("//td[contains(text(),'%s')]/following-sibling::td//span[contains(@class,'ico-checkmark')]", inviteUserEmail);
            jsClick(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(acceptXpath))));
            waitForPageLoad();
            sendKeysToElement(first_name_input, userFirstName);
            sendKeysToElement(last_name_input, userLastName);
            sendKeysToElement(create_password_input, userPassword);
            clickElement(accept_invite_button);
            waitForPageLoad();
            logPass("User invited and accepted successfully.");
        }

        logStart("Step 11-22: Create New Metric");
        clickElement(metrics_link);
        waitForPageLoad();
        clickElement(add_metric_button);
        waitForPageLoad();
        sendKeysToElement(metric_name_input, metricName);

        selectDropdownByVisibleText(value_source_dropdown_trigger, valueSource);
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            log("Configuring Formula...");
            searchAndSelectFromList(formula_builder_metric_search_input, firstMetric);
            String operatorXpath = String.format("//button[normalize-space()='%s']", operator);
            clickElement(new String[]{operatorXpath});
            searchAndSelectFromList(formula_builder_metric_search_input, secondMetric);
            clickElement(formula_confirm_button);
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            log("Configuring based on existing Metric...");
            searchAndSelectFromList(metric_source_search_input, metricToSelect);
        }

        selectDropdownByVisibleText(cadence_dropdown_trigger, cadence);
        if ("Weekly".equalsIgnoreCase(cadence)) {
            selectDropdownByVisibleText(resets_on_dropdown_trigger, resetsOn);
        }
        
        clickElement(save_button);
        waitForPageLoad();
        logPass("Metric '" + metricName + "' created successfully.");

        logStart("Step 23-26: Add Metric to Dashboard");
        new Actions(driver).moveToElement(findElementWithFallbacks(dashboards_link)).perform();
        clickElement(my_dashboard_link);
        waitForPageLoad();
        clickElement(edit_kpi_icon);
        sleep(ACTION_PAUSE_MS);
        String metricInModalXpath = String.format("//div[contains(@class,'available-metrics')]//div[normalize-space()='%s']", metricName);
        jsClick(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricInModalXpath))));
        sleep(500);
        clickElement(edit_kpi_modal_save_button);
        waitForPageLoad();
        logPass("Metric added to 'My KPIs' on the dashboard.");

        if (targetType != null && !targetType.isEmpty() && !"None".equalsIgnoreCase(targetType)) {
            logStart("Step 27-33: Configure Metric Target");
            String cardXpath = String.format("//div[contains(@class,'kpi-card-container')]//span[normalize-space()='%s']/ancestor::div[contains(@class,'kpi-card-container')]", metricName);
            WebElement metricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cardXpath)));
            new Actions(driver).moveToElement(metricCard).perform();
            
            WebElement threeDotMenu = metricCard.findElement(By.xpath(".//span[contains(@class,'ico-threeDots')]"));
            jsClick(threeDotMenu);
            sleep(1000);

            WebElement editOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class,'k-menu-vertical')]//li//span[normalize-space()='Edit']")));
            jsClick(editOption);
            waitForPageLoad();

            clickElement(target_tab);
            sleep(ACTION_PAUSE_MS);

            if ("Custom".equalsIgnoreCase(targetType)) {
                clickElement(custom_target_radio);
                sleep(1000);
                sendKeysToElement(level_1_input, level1);
                sendKeysToElement(level_2_input, level2);
                sendKeysToElement(level_3_input, level3);
                sendKeysToElement(level_4_input, level4);
            } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                clickElement(time_based_target_radio);
                sleep(1000);
                sendKeysToElement(start_value_input, startValue);
                sendKeysToElement(target_value_input, targetValue);
            }

            clickElement(save_button);
            waitForPageLoad();
            logPass("Target configured and saved successfully.");
        }
    }

    // ========== SETUP AND TEARDOWN ==========
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe"); // Uncomment if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AlignTestDataDriven test = new AlignTestDataDriven();
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