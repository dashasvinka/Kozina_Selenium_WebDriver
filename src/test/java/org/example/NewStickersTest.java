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

public class NewStickersTest {
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
        //Логинимся
        driver.findElement(By.name("email")).sendKeys("dkhmelkova@mail.ru");
        driver.findElement(By.name("password")).sendKeys("dkhmelkova");
        driver.findElement(By.name("login")).click();
        //Ищем все карточки товара
        List<WebElement> products = driver.findElements(By.cssSelector("[class^=product]"));
        int sizeProducts = products.size();
        for (int b = 0; b < sizeProducts; b++) {
            // Ищем стикеры NEW и SALE в карточке
            List<WebElement> stickersSale = products.get(b).findElements(By.cssSelector(".sticker.sale"));
            List<WebElement> stickersNew = products.get(b).findElements(By.cssSelector(".sticker.new"));
            // Проверяем, что стикер только один, либо NEW, либо SALE в каждой карточке
            if (stickersSale.size() != 0) {
                Assert.assertEquals(stickersSale.size(), 1);
                Assert.assertEquals(stickersNew.size(), 0);
            }
            else {
                Assert.assertEquals(stickersSale.size(), 0);
                Assert.assertEquals(stickersNew.size(), 1);
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}
