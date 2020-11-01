package ch.fhnw.digibp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class AbstractSeleniumTest extends AbstractIntegrationTest {

    private WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    protected void openUrl(String relativePath) {
        driver.get(getUrl() + relativePath);
    }

    protected WebElement findElementByCss(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    @AfterEach
    public void afterEach() {
        driver.close();
    }
}
