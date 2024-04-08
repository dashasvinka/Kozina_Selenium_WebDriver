package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StickersTest{

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
    public void checkStickers() {
        //Ищем все карточки товара
        List<WebElement> products = driver.findElements(By.cssSelector(".product"));
        int sizeProducts = products.size();
        for (int b = 0; b < sizeProducts; b++) {
            //Ищем стикеры в карточке
            List<WebElement> stickers = products.get(b).findElements(By.cssSelector(".sticker"));
            //Проверяем, что в карточке только один стикер
            int stickersQuantityExpected = 1;
            int stickersQuantityActual = stickers.size();
            Assert.assertEquals(stickersQuantityExpected, stickersQuantityActual);
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}
