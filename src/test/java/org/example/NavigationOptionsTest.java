package org.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NavigationOptionsTest {
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("admin"));
    }

    @Test
    public void menuTest() {
        //Логин
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
          //Проверили все основные айтемы списка
        int items = driver.findElements(By.cssSelector("li#app-")).size();
        for (int i = 0; i < items; i++) {
            List<WebElement> mainTitle = driver.findElements(By.cssSelector("li#app-"));
            var item = mainTitle.get(i);
            String itemText = item.getText();
            System.out.println(item.getText());
            item.click();
            driver.findElement(By.cssSelector("h1")).getText().equals(itemText);
              //Проверили все вложенные айтемы списка
            int minorItems = driver.findElements(By.cssSelector("[id^=doc]")).size();
            if (minorItems>0){
                for (int l = 0; l < minorItems; l++) {
                    List<WebElement> minorTitle = driver.findElements(By.cssSelector("[id^=doc]"));
                    var minorItem = minorTitle.get(l);
                    String minorItemText = minorItem.getText();
                    System.out.println(minorItem.getText());
                    minorItem.click();
                    driver.findElement(By.cssSelector("h1")).getText().equals(minorItemText);
                }
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}
