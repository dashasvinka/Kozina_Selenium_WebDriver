package org.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LogInAdminTest {
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("admin"));
    }

    @Test
    public void loginAdminTest() {
        driver.findElement(By.name("username")).sendKeys("dkhmel");
        driver.findElement(By.name("password")).sendKeys("dkhmel123");
        driver.findElement(By.name("login")).click();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}
