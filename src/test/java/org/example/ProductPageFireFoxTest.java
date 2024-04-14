package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ProductPageFireFoxTest {
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.gecko.driver", ConfProperties.getProperty("firefoxdriver"));
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("lite"));
    }

    @Test
    public void checkInfoOnPage() {
        // Нашли первую карточку товара в разделе Campaigns
        WebElement product = driver.findElement(By.cssSelector("[id='box-campaigns'] li:first-child"));
        // Получили название товара из каталога
        String nameOnCatalog = product.findElement(By.cssSelector("[class='name']")).getText();
        // Получили обычную цену из каталога
        String regularPriceOnCatalog = product.findElement(By.cssSelector("[class='regular-price']")).getText();
        // Получили акционную цену из каталога
        String campaignPriceOnCatalog = product.findElement(By.cssSelector("[class='campaign-price']")).getText();
        System.out.println(nameOnCatalog);
        System.out.println(regularPriceOnCatalog);
        System.out.println(campaignPriceOnCatalog);


        // Получили размер обычной цены из каталога
        String regularPriceSizeOnCatalog = product.findElement(By.cssSelector("[class='regular-price']")).getCssValue("font-size");
        // Получили размер акционной цены из каталога
        String campaignPriceSizeOnCatalog = product.findElement(By.cssSelector("[class='campaign-price']")).getCssValue("font-size");
        System.out.println(regularPriceSizeOnCatalog);
        System.out.println(campaignPriceSizeOnCatalog);
        var indResultReg = regularPriceSizeOnCatalog.lastIndexOf("px");
        var indResultCamp = campaignPriceSizeOnCatalog.lastIndexOf("px");
        String resultReg = regularPriceSizeOnCatalog.substring(0, indResultReg);
        String resultCamp = campaignPriceSizeOnCatalog.substring(0, indResultCamp);
        System.out.println(resultReg);
        System.out.println(resultCamp);
        Double regSizeCut = Double.valueOf(resultReg);
        Double camSizeCut = Double.valueOf(resultCamp);
        // д) акционная цена крупнее, чем обычная (в каталоге)
        Assert.assertTrue(camSizeCut > regSizeCut);


        // Получили цвет обычной цены из каталога
        String regularPriceColorOnCatalog = product.findElement(By.cssSelector("[class='regular-price']")).getCssValue("color");
        var start = regularPriceColorOnCatalog.lastIndexOf("(");
        var end = regularPriceColorOnCatalog.indexOf(")");
        String resultSub = regularPriceColorOnCatalog.substring(start+1, end);
        String resultRep = resultSub.replace(" ","");
        System.out.println(resultRep);
        String[] resultSplit = resultRep.split(",");
        // в) обычная цена в каталоге серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        Assert.assertEquals(resultSplit[0], resultSplit[1]);
        Assert.assertEquals(resultSplit[1], resultSplit[2]);


        // Получили оформление обычной цены из каталога
        String regularPriceTextDecorationOnCatalog = product.findElement(By.cssSelector("[class='regular-price']")).getCssValue("text-decoration-line");
        System.out.println(regularPriceTextDecorationOnCatalog);
        // в) обычная цена зачёркнутая в каталоге
        Assert.assertEquals("line-through", regularPriceTextDecorationOnCatalog);


        // Получили цвет акционной цены из каталога
        String campaignPriceColorOnCatalog = product.findElement(By.cssSelector("[class='campaign-price']")).getCssValue("color");
        var startColCat = campaignPriceColorOnCatalog.lastIndexOf("(");
        var endColCat = campaignPriceColorOnCatalog.indexOf(")");
        String resultSubColCat = campaignPriceColorOnCatalog.substring(startColCat+1, endColCat);
        String resultRepColCat = resultSubColCat.replace(" ","");
        System.out.println(resultRepColCat);
        String[] resultSplitColCat = resultRepColCat.split(",");
        int g = Integer.parseInt(resultSplitColCat[1]);
        int b = Integer.parseInt(resultSplitColCat[2]);
        // г) акционная цена красная в каталоге (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
        Assert.assertEquals(0, g);
        Assert.assertEquals(0, b);


        // Получили выделение акционной цены из каталога
        String campaignPriceWeightOnCatalog = product.findElement(By.cssSelector("[class='campaign-price']")).getTagName();
        System.out.println(campaignPriceWeightOnCatalog);
        // г) акционная цена в каталоге жирная
        Assert.assertEquals("strong", campaignPriceWeightOnCatalog);


        // Перешли на страницу товара
        product.findElement(By.cssSelector("[class='image']")).click();
        //driver.findElement(By.linkText(nameOnCatalog)).click();
        // Получили название товара со страницы продукта
        String nameOnPage = driver.findElement(By.cssSelector("[itemprop='name']")).getText();
        System.out.println(nameOnPage);
        // а) на главной странице и на странице товара совпадает текст названия товара
        Assert.assertEquals(nameOnPage, nameOnCatalog);


        // Получили обычную цену из каталога
        String regularPriceOnPage = driver.findElement(By.cssSelector("[class='regular-price']")).getText();
        // Получили акционную цену из каталога
        String campaignPriceOnPage = driver.findElement(By.cssSelector("[class='campaign-price']")).getText();
        System.out.println(regularPriceOnPage);
        System.out.println(campaignPriceOnPage);
        // б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        Assert.assertEquals(regularPriceOnCatalog, regularPriceOnPage);
        Assert.assertEquals(campaignPriceOnCatalog, campaignPriceOnPage);


        // Получили размер обычной цены на странице продукта
        String regularPriceSizeOnPage = driver.findElement(By.cssSelector("[class='regular-price']")).getCssValue("font-size");
        // Получили размер акционной цены на странице продукта
        String campaignPriceSizeOnPage = driver.findElement(By.cssSelector("[class='campaign-price']")).getCssValue("font-size");
        System.out.println(regularPriceSizeOnPage);
        System.out.println(campaignPriceSizeOnPage);
        var indResultRegPg = regularPriceSizeOnPage.lastIndexOf("px");
        var indResultCampPg = campaignPriceSizeOnPage.lastIndexOf("px");
        String resultRegPg = regularPriceSizeOnPage.substring(0, indResultRegPg);
        String resultCampPg = campaignPriceSizeOnPage.substring(0, indResultCampPg);
        System.out.println(resultRegPg);
        System.out.println(resultCampPg);
        Double regSizePg = Double.valueOf(resultRegPg);
        Double camSizePg = Double.valueOf(resultCampPg);
        // д) акционная цена крупнее, чем обычная (на странице)
        Assert.assertTrue(camSizePg > regSizePg);


        // Получили цвет обычной цены на странице продукта
        String regularPriceColorOnPage = driver.findElement(By.cssSelector("[class='regular-price']")).getCssValue("color");
        var startPg = regularPriceColorOnPage.lastIndexOf("(");
        var endPg = regularPriceColorOnPage.indexOf(")");
        String resultSubPg = regularPriceColorOnPage.substring(startPg+1, endPg);
        String resultRepPg = resultSubPg.replace(" ","");
        System.out.println(resultRepPg);
        String[] resultSplitPg = resultRepPg.split(",");
        // в) обычная цена на странице продукта серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        Assert.assertEquals(resultSplitPg[0], resultSplitPg[1]);
        Assert.assertEquals(resultSplitPg[1], resultSplitPg[2]);


        // Получили цвет акционной цены на странице продукта
        String campaignPriceColorOnPage = driver.findElement(By.cssSelector("[class='campaign-price']")).getCssValue("color");
        var startColPg = campaignPriceColorOnPage.lastIndexOf("(");
        var endColPg = campaignPriceColorOnPage.indexOf(")");
        String resultSubColPg = campaignPriceColorOnPage.substring(startColPg+1, endColPg);
        String resultRepColPg = resultSubColPg.replace(" ","");
        System.out.println(resultRepColPg);
        String[] resultSplitColPg = resultRepColPg.split(",");
        int gP = Integer.parseInt(resultSplitColPg[1]);
        int bp = Integer.parseInt(resultSplitColPg[2]);
        // г) акционная цена красная на странице продукта (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
        Assert.assertEquals(0, gP);
        Assert.assertEquals(0, bp);


        // Получили оформление обычной цены на странице продукта
        String regularPriceTextDecorationOnPage = driver.findElement(By.cssSelector("[class='regular-price']")).getCssValue("text-decoration-line");
        System.out.println(regularPriceTextDecorationOnPage);
        // в) обычная цена зачёркнутая на странице продукта
        Assert.assertEquals("line-through", regularPriceTextDecorationOnPage);


        // Получили выделение акционной цены на странице продукта
        String campaignPriceWeightOnPage = driver.findElement(By.cssSelector("[class='campaign-price']")).getTagName();
        System.out.println(campaignPriceWeightOnPage);
        // г) акционная цена на странице продукта жирная
        Assert.assertEquals("strong", campaignPriceWeightOnPage);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
