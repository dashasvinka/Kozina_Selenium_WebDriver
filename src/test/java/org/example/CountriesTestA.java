package org.example;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountriesTestA {
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("countries"));
    }

    @Test
    public void countries() {
        //Логинимся
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //Ищем все страны
        List<WebElement> countries = driver.findElements(By.xpath(".//tr//td[5]"));
        int quantity = countries.size();
        System.out.println(quantity);
        ArrayList<String> actualCountryName = new ArrayList<>();
        ArrayList<String> expectedCountryName = new ArrayList<>();
        for (int b = 0; b < quantity; b++) {
            //Сохраняем наименование страны
            String countryName = countries.get(b).getText();
            //Добавляем в список
            actualCountryName.add(b, countryName);
            expectedCountryName.add(b, countryName);
            System.out.println(actualCountryName.size());
        }
        Collections.sort(expectedCountryName);
        for (int p = 0; p < quantity;p++) {
            //Вытаскиваем фактическое наименовение
            String actualName = actualCountryName.get(p);
            //Вытаскиваем ожидаемое наименование
            String expectedName = expectedCountryName.get(p);
            //Сравниваем
            Assert.assertEquals(actualName, expectedName);
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
