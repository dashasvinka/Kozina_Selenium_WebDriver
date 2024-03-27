package org.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LogInTest {
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("lite"));
    }

    @Test
    public void loginUserTest() {
        driver.findElement(By.name("email")).sendKeys("dkhmelkova@mail.ru");
        driver.findElement(By.name("password")).sendKeys("dkhmelkova");
        driver.findElement(By.name("login")).click();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}
