package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountriesTestB {
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
    public void countriesOnPage() {
        //Логинимся
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //Ищем указатели для определения размера коллекции
        List<WebElement> checkQuantity = driver.findElements(By.xpath(".//tr//td[6]"));;
        int quantity = checkQuantity.size();
        System.out.println(quantity);
        for (int b = 0; b < quantity; b++) {
            List<WebElement> countriesQuantity = driver.findElements(By.xpath(".//tr//td[6]"));
            List<WebElement> countries = driver.findElements(By.xpath(".//tr//td[5]"));
            //Сохраняем наименование страны
            String actualQuantity = countriesQuantity.get(b).getText();
            int number = Integer.parseInt(actualQuantity);
            System.out.println(number);
            if (number != 0){
                String link = countries.get(b).getText();
                driver.findElement(By.linkText(link)).click();
                List<WebElement> countriesOnPage = driver.findElements(By.cssSelector("td:nth-child(3)>input[type='hidden']"));
                int q = countriesOnPage.size();
                ArrayList<String> actual = new ArrayList<>();
                ArrayList<String> expected = new ArrayList<>();
                for (int w = 0; w < q; w++) {
                    //Сохраняем наименование страны
                    String name = countriesOnPage.get(w).getText();
                    //Добавляем в список
                    actual.add(w, name);
                    expected.add(w, name);
                    System.out.println(actual.size());
                }
                Collections.sort(expected);
                for (int l = 0; l < q; l++) {
                    //Вытаскиваем фактическое наименовение
                    String actName = actual.get(l);
                    //Вытаскиваем ожидаемое наименование
                    String expdName = expected.get(l);
                    //Сравниваем
                    Assert.assertEquals(actName, expdName);
                }
                driver.navigate().back();
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
