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

public class GeoZonesTest {
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("geozones"));
    }

    @Test
    public void checkZones() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        List<WebElement> geoZones = driver.findElements(By.cssSelector("td:nth-child(3)>a"));
        int quantity = geoZones.size();
        for (int b = 0; b < quantity; b++) {
            List<WebElement> geoZonesNewCheck = driver.findElements(By.cssSelector("td:nth-child(3)>a"));
            String link = geoZonesNewCheck.get(b).getText();
            driver.findElement(By.linkText(link)).click();
            List<WebElement> zones = driver.findElements(By.cssSelector("td:nth-child(3)>select[name*='zones']>option[selected='selected']"));
                int q = zones.size();
                ArrayList<String> actual = new ArrayList<>();
                ArrayList<String> expected = new ArrayList<>();
                for (int w = 0; w < q; w++) {
                    String name = zones.get(w).getText();
                    actual.add(w, name);
                    expected.add(w, name);
                }
                Collections.sort(expected);
                for (int l = 0; l < q; l++) {
                    String actName = actual.get(l);
                    System.out.println(actName);
                    String expdName = expected.get(l);
                    System.out.println(expdName);
                    Assert.assertEquals(actName, expdName);
                }
                System.out.println("////////////");
                driver.navigate().back();
            }
        }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
