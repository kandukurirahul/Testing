package com.rahul;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestNGSampleTest {
    @Test
    public void googleSearch(){
        System.out.println("Google Search");
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        WebDriver webDriver=new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        webDriver.get("https://www.google.com");
        webDriver.findElement(By.name("q")).sendKeys("Selenium");
        WebDriverWait wait=new WebDriverWait(webDriver,60,2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']")));

        List<WebElement> elements=webDriver.findElements(By.xpath("//ul[@role='listbox']//li"));
        elements.get(0).click();

        WebElement element=webDriver.findElement(By.id("rcnt"));
        Assert.assertTrue(element.getText().contains("Selenium"));

        webDriver.quit();
    }
}
