package org.example;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CreateNewProductTest {
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("catalog"));
    }
    @Test
    public void createProduct() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

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

        int sizeCatalogStart = driver.findElements(By.cssSelector ("[class='row semi-transparent']")).size();

        // Перешли на страницу товара
        driver.findElement(By.cssSelector("td[id='content']>div>a:nth-child(2)")).click();

        // Заполняем General
        String name = buffer.toString();
        String code = buffer.toString();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys(name);
        driver.findElement(By.cssSelector("input[name='code']")).sendKeys(code);
        driver.findElements(By.cssSelector("[name='product_groups[]']")).get(0).click();
        driver.findElement(By.cssSelector("input[name='quantity']")).clear();
        driver.findElement(By.cssSelector("input[name='quantity']")).sendKeys("100");
        driver.findElement(By.cssSelector("input[name='date_valid_from']")).sendKeys("01052024");
        driver.findElement(By.cssSelector("input[name='date_valid_to']")).sendKeys("01052025");
        WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
        File file = new File("src/test/resources/coolduck.jpeg");
        fileInput.sendKeys(file.getAbsolutePath());

        // Заполняем  Information
        String description = buffer.toString();
        String title = buffer.toString();
        driver.findElements(By.cssSelector("div[class='tabs']>ul[class='index']>li")).get(1).click();
        driver.findElement(By.cssSelector("select[name='manufacturer_id']")).click();
        driver.findElements(By.cssSelector("select[name='manufacturer_id']>option[value]")).get(1).click();
        driver.findElement(By.cssSelector("input[name='keywords']")).sendKeys(description);
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys(description);
        driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys(title);
        driver.findElement(By.cssSelector("input[name='meta_description[en]']")).sendKeys(description);

        // Заполняем  Prices
        driver.findElements(By.cssSelector("div[class='tabs']>ul[class='index']>li")).get(3).click();
        driver.findElement(By.cssSelector("input[name='purchase_price']")).clear();
        driver.findElement(By.cssSelector("input[name='purchase_price']")).sendKeys("15");
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]']")).clear();
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]']")).sendKeys("20");
        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]']")).clear();
        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]']")).sendKeys("22");

        // Сохраняем и проверяем
        driver.findElement(By.cssSelector("button[name='save']")).click();
        int sizeCatalogEnd = driver.findElements(By.cssSelector ("[class='row semi-transparent']")).size();
        Assert.assertEquals(sizeCatalogEnd-1, sizeCatalogStart);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}
