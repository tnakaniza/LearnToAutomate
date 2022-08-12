package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import models.CapstoneMethods;
import models.Credential;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CommandsPage;
import restAssured.RestAssuredTests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class RunCapstoneProjectTests {
    WebDriver _driver;
    CapstoneMethods capstoneMethods;
    private static final String RESULT_FILENAME = "Tee_Auto_Tests";
    public   String t = "Transfer Complete!";
    public   String apiExpectedMsg;
    /*@DataProvider(name = "transferTestData")
    public Object[][] createData(@NotNull Method method) {
        return switch (method.getName()) {
            case "test1", "apiTest1" -> new Object[][]{{"12345", "12456", "100"}};
            case "test2", "apiTest2"-> new Object[][]{{"12456", "12567", "500"}};
            case "test3", "apiTest3" -> new Object[][]{{"12345", "12456", "1000000"}};
            default -> null;
        };
    }*/
    @DataProvider(name= "transferTestData")
    public Object[][] tdMethod(){
        return new Object[][] {
                {"12345","12456","100"},
                {"12456","12567","500"},
                {"12345","12456","1000000"}
        };
    }
    @BeforeTest
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        _driver = new ChromeDriver();
        capstoneMethods = new CapstoneMethods(_driver);
        _driver.manage().window().maximize();
        _driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        _driver.navigate().to("https://parabank.parasoft.com/");
        capstoneMethods = new CapstoneMethods(_driver);
        capstoneMethods.navigate_to_Browser_Login();
    }

    @Test(dataProvider = "transferTestData")

    public void test1(String fromAcc, String toAcc, String amount) throws InterruptedException, IOException {
        capstoneMethods.transfer(fromAcc, toAcc, amount);
        Assert.assertEquals(_driver.getPageSource(),t);
    }

    @Test(dataProvider = "transferTestData")
    public void test2(String fromAcc, String toAcc, String amount) throws InterruptedException, IOException {
        capstoneMethods.transfer(fromAcc, toAcc, amount);
        Assert.assertEquals(_driver.getPageSource(),t);
    }

    @Test(dataProvider = "transferTestData")
    public void test3(String fromAcc, String toAcc, String amount) throws InterruptedException, IOException {
        capstoneMethods.transfer(fromAcc, toAcc, amount);
        Assert.assertEquals(_driver.getPageSource(),t);
    }

    public void save_screenshot(WebDriver d) throws IOException {
        System.out.println("_driver Aftertaste  : "  + d);
        File scrFile = ( (TakesScreenshot) d ).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(RESULT_FILENAME));
    }

    @AfterTest
    public void afterTest() throws IOException {
        save_screenshot(_driver);
        _driver.close();
        _driver.quit();
    }

    @Test(dataProvider = "transferTestData")
    public void apiTest1(String fromAcc, String toAcc, String amount) throws IOException {
        apiExpectedMsg  = String.format("Successfully transferred $%s from account #%s to account #%s, with $%s",
                amount,fromAcc,toAcc,amount);
        RestAssuredTests restAssuredTests = new RestAssuredTests();
        //RestAssuredTests capstoneMethods = new R(_driver);
        restAssuredTests.rest_db_via_api();
        var res = restAssuredTests.transferFunds(amount, fromAcc,toAcc);
/*       Assert.assertEquals(res.responsebody, apiExpectedMsg);
       Assert.assertEquals(res.responseCode,200);*/

        //var runApi =  restAssuredTests.transferFunds(new RestAssuredTests(amount, fromAcc,toAcc));

    }
    /*@Test(dataProvider = "transferTestData")
    public void apiTest2(String fromAcc, String toAcc, String amount){
        apiExpectedMsg  = String.format("Successfully transferred $%s from account #%s to account #%s, with $%s",
                amount,fromAcc,toAcc,amount);
        RestAssuredTests restAssuredTests = new RestAssuredTests();
        var res=restAssuredTests.transferFunds(amount, fromAcc,toAcc);
        Assert.assertEquals(res.responsebody, apiExpectedMsg);
        Assert.assertEquals(res.responseCode,200);
    }

    @Test(dataProvider = "transferTestData")
    public void apiTest3(String fromAcc, String toAcc, String amount){
        apiExpectedMsg  = String.format("Successfully transferred $%s from account #%s to account #%s, with $%s",
                amount,fromAcc,toAcc,amount);
        RestAssuredTests restAssuredTests = new RestAssuredTests();
        var res =restAssuredTests.transferFunds(amount, fromAcc,toAcc);
        Assert.assertEquals(res.responsebody, apiExpectedMsg);
        Assert.assertEquals(res.responseCode,200);
        //Assertions.assertEquals(200, restAssuredTests.);
    }*/
}
