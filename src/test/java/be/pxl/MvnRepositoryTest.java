package be.pxl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by kimprzybylski on 22/04/18.
 */
public class MvnRepositoryTest {

    private WebDriver driver;

    // Dit wordt uitgevoerd voor de testen gestart worden
    @Before
    public void setUp() {
        // Download de testdriver van de browser die je wil gebruiken
        // Voeg een referentie toe naar de testdriver die je gedownload hebt
        System.setProperty("webdriver.chrome.driver", "/Users/kimprzybylski/GitHub/SeleniumTest/chromedriver");

        // Initialiseerd de driver en kiest de browser waar de test uitgevoerd wordt
        driver = new ChromeDriver();

        // zorgt dat de driver naar de website gaat
        driver.navigate().to("https://mvnrepository.com");
    }

    @Test
    public void testIsHomePage(){
        // controleerd of de huidige url == "https://www.pxl.be
        Assert.assertEquals(driver.getCurrentUrl(), "https://mvnrepository.com/");
    }

    @Test
    public void testHasHeaders() {
        // Zoekt naar het element met xpath en controleerd of de inhoud Indexed Artifacts is
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"left\"]/div[1]/h3/a")).getText().equals("Indexed Artifacts (9.03M)"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"left\"]/div[2]/h3/a")).getText().equals("Popular Categories"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/h1")).getText().equals("What's New in Maven"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"right\"]/div[1]/h3/a")).getText().equals("Indexed Repositories (344)"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"right\"]/div[2]/h3/a")).getText().equals("Popular Tags"));
    }

    @Test
    public void testIndexedArtifactsShowsTopIndexedRepositories() {
        driver.findElement(By.xpath("//*[@id=\"left\"]/div[1]/h3/a")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/h1")).getText(), "Top Indexed Repositories (20 / 344)");
        Assert.assertEquals(driver.getCurrentUrl(), "https://mvnrepository.com/repos");
    }

    @Test
    public void testPopularCategoriesShowsTopCategories() {
        driver.findElement(By.xpath("//*[@id=\"left\"]/div[2]/h3/a")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/h1")).getText(), "Top Categories");
        Assert.assertEquals(driver.getCurrentUrl(), "https://mvnrepository.com/open-source");
    }

    @Test
    public void testPopularTagsShowsTagCloud() {
        driver.findElement(By.xpath("//*[@id=\"right\"]/div[2]/h3/a")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/h1")).getText(), "Maven Repository - Tag Cloud");
        Assert.assertEquals(driver.getCurrentUrl(), "https://mvnrepository.com/tags");
    }

    @Test
    public void testSearch() {
        // type selenium java in de searchbox
        driver.findElement(By.xpath("//*[@id=\"query\"]")).sendKeys("selenium java");
        // klik op de search knop
        driver.findElement(By.xpath("//*[@id=\"search\"]/form/input[2]")).click();

        // controleerd url
        Assert.assertEquals(driver.getCurrentUrl(), "https://mvnrepository.com/search?q=selenium+java");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[2]/div[1]/h2/a[1]")).getText(), "Selenium Java");
    }

    @Test
    public void testCanClickOnListItemAndGetNewPage() {
        // gaat naar meegegeven url
        driver.get("https://mvnrepository.com/search?q=selenium+java");
        // klikt op eerste item in lijst
        driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[2]/div[1]/h2/a[1]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[2]/div[1]/h2/a")).getText(), "Selenium Java");
    }

    @Test
    public void testIfLatestVersionOfSeleniumIs3_11_0() {
        driver.get("https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"snippets\"]/div/div/div/table/tbody[1]/tr/td[2]/a")).getText(), "3.10.0");
    }

    @Test
    public void testIfClickingOnVersionGoesToDependency() {
        driver.get("https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java");
        driver.findElement(By.xpath("//*[@id=\"snippets\"]/div/div/div/table/tbody[1]/tr/td[2]/a")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"maven-a\"]")).getText(), "" +
                "<!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->\n" +
                "<dependency>\n" +
                "    <groupId>org.seleniumhq.selenium</groupId>\n" +
                "    <artifactId>selenium-java</artifactId>\n" +
                "    <version>3.11.0</version>\n" +
                "</dependency>");
    }

    // Wordt uitgevoerd na alle testen
    @After
    public void tearDown() {
        // Sluit de webdriver af
        driver.close();
    }
}

