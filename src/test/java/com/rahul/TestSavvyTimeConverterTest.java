package com.rahul;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
//@Listeners(com.rahul.ListenerTest.class)
public class TestSavvyTimeConverterTest {
    WebDriver webDriver;

    @BeforeClass
    public void loadBrowser() {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        webDriver = new ChromeDriver();

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://savvytime.com");
        webDriver.manage().window().maximize();
    }
    @BeforeMethod
    public void loadPage(){
        List<WebElement> elements = webDriver.findElements(By.xpath("//ul[@class='nav navbar-nav']//li"));
        elements.get(1).click();
        webDriver.findElement(By.id("time-search")).sendKeys("Hyderabad");
        List<WebElement> elements1 = webDriver.findElements(By.xpath("//div[@id='converter-quick-search-result']//a"));
        elements1.get(0).click();
        webDriver.findElement(By.id("time-search")).sendKeys("London");
        List<WebElement> elements2 = webDriver.findElements(By.xpath("//div[@id='converter-quick-search-result']//a"));
        elements2.get(0).click();
    }

   /* @Test(priority=1,description = "Tests whether Converter button working")
    public void navigateToConvertor() {
        List<WebElement> elements = webDriver.findElements(By.xpath("//ul[@class='nav navbar-nav']//li"));
        elements.get(1).click();
    }*/

    @Test(description = "Tests whether Correct page is loaded")
    public void verifyConvertorPage() {
        WebElement element = webDriver.findElement(By.xpath("//h5[@class='logo-text']"));
        String foundText = element.getText();
        Assert.assertTrue(foundText.contains("Savvy Time"), "Expected text contains Savvy Time but found " + foundText);
    }

    @Test(description = "entering First city")
    public void setFirstCity() {
        //System.out.println("Gogle Search");
        //webDriver.findElement(By.id("time-search")).sendKeys("Hyderabad");
        //WebDriverWait wait=new WebDriverWait(webDriver,60,2);

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='converter-quick-search-result']")));
        //List<WebElement> elements = webDriver.findElements(By.xpath("//div[@id='converter-quick-search-result']//a"));
        //elements.get(0).click();

        WebElement element = webDriver.findElement(By.xpath("//h1[@class='title']"));
        Assert.assertTrue(element.getText().contains("Hyderabad"),"Expected text contains Hyderabad but found"+element.getText());
    }

    @Test(description = "entering second city")
    public void setSecondCity() {
//        webDriver.findElement(By.id("time-search")).sendKeys("London");
//        List<WebElement> elements = webDriver.findElements(By.xpath("//div[@id='converter-quick-search-result']//a"));
//        elements.get(0).click();

        WebElement element = webDriver.findElement(By.xpath("//h1[@class='title']"));
        Assert.assertTrue(element.getText().contains("London"),"Expected text contains London but found"+element.getText());

    }

    @Test(description = "fetching time of given city")
    public void fetchTime() throws ParseException {
        List<WebElement> time = webDriver.findElements
                (By.xpath("//input[@class='time ampm format12 form-control ui-timepicker-input']"));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm aa");
        Date time1 = simpleDateFormat.parse(time.get(0).getAttribute("value"));
        Date time2 = simpleDateFormat.parse(time.get(1).getAttribute("value"));
        System.out.println(time1);
        System.out.println(time2);
        int timeDiff=(int)(Math.abs(time1.getTime()-Math.abs(time2.getTime()))/60000);
        Assert.assertEquals(timeDiff,330,"Expected result is 330 but found"+timeDiff);

    }
    @Test(description = "Testing swap button")
    public void verifySwapButton() {
        //String beforeSwap=webDriver.getTitle();
        List<WebElement> elementsBeforeSwap=webDriver.findElements(By.xpath("//div[@class='table-time row']"));
        List<String> stringBeforeSwap=new ArrayList<String>(elementsBeforeSwap.size());
        for(int i=0;i<elementsBeforeSwap.size();i++)
            stringBeforeSwap.add(elementsBeforeSwap.get(i).getText());
        webDriver.findElement(By.className("icon-exchange")).click();
        //String afterSwap=webDriver.getTitle();
        List<WebElement> elementsAfterSwap=webDriver.findElements(By.xpath("//div[@class='table-time row']"));
        List<String> stringAfterSwap=new ArrayList<String>(elementsAfterSwap.size());
        for(int i=0;i<elementsAfterSwap.size();i++)
            stringAfterSwap.add(elementsAfterSwap.get(i).getText());
        Collections.reverse(stringAfterSwap);
        Assert.assertEquals(stringBeforeSwap,stringAfterSwap);

    }
    @Test(description = "Verifying delete button")
    public void verifyDeleteButton(){
        List<WebElement> elements=webDriver.findElements(By.xpath("//div[@class='table-time row']"));
        int beforeDelete=elements.size();
        int afterDelete;
        elements.get(0).click();
        webDriver.findElement(By.xpath("//a[@class='delete-btn btn']")).click();
        List<WebElement> elementsafterdelete=webDriver.findElements(By.xpath("//div[@class='table-time row']"));
        afterDelete=elementsafterdelete.size();
        Assert.assertEquals(beforeDelete,afterDelete+1);
    }
//    @Test(priority = 8)
//    public void calenderDate(){
//        webDriver.findElement(By.className("icon-table")).click();
//        webDriver.findElement(By.xpath("//table[@class=' table-condensed']//tr[3]//td[3]")).click();
//        WebElement date=webDriver.findElement(By.className("tz-date"));
//        Assert.assertTrue(date.getText().contains("14"));
//    }
    @Test(description = "Changing Date")
    public void calender(){
        webDriver.findElement(By.className("icon-table")).click();
        webDriver.findElement(By.className("datepicker-switch")).click();
        webDriver.findElement(By.xpath("//table[@class='table-condensed']//td//span[@class='month'][2]")).click();
        webDriver.findElement(By.xpath("//div[@class='datepicker-days']//tbody//tr[3]//td[@class='day'][3]")).click();
        WebElement date=webDriver.findElement(By.className("tz-date"));
        Assert.assertTrue(date.getText().contains("Tue, Mar 10"));
    }
    @AfterClass
    //Testing closing of browser
    public void closeBrowser() {
        webDriver.quit();
    }
}