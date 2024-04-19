package org.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegistrationTest {
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
    public void checkRegistrationPath() {
        // Перешли на страницу товара
        driver.findElement(By.linkText("New customers click here")).click();

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        String firstName = buffer.toString();
        String lastName = buffer.toString();
        String location = "New York";
        String code = "10001";
        String email = buffer.toString() + "@gmail.com";
        String phone = "+17778971234";
        String password = buffer.toString();

        // Зарегистрировались
        driver.findElement(By.cssSelector("[name='firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("[name='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("[name='address1']")).sendKeys(location);
        driver.findElement(By.cssSelector("[name='postcode']")).sendKeys(code);
        driver.findElement(By.cssSelector("[name='city']")).sendKeys(location);
        driver.findElement(By.cssSelector("[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys(phone);
        driver.findElement(By.cssSelector("[name='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("[name='confirmed_password']")).sendKeys(password);
        driver.findElement(By.cssSelector("[role='combobox']")).click();
        driver.findElement(By.cssSelector("input[type='search']")).sendKeys("United States");
        driver.findElement(By.cssSelector("ul[role='tree']>li:first-child")).click();
        driver.findElement(By.cssSelector("[name='create_account']")).click();

        // Разлогинились после регистрации
        driver.findElement(By.linkText("Logout")).click();

        // Залогинились после регистрации
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        // Разлогинились после логина
        driver.findElement(By.linkText("Logout")).click();
    }
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
