package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isSuccess(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Usep");
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("10"); 
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/product/list"));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(baseUrl + "/product/list", currentUrl);
        WebElement productNameInList = driver.findElement(By.xpath("//table//td[text()='Sampo Cap Usep']"));
        assertEquals("Sampo Cap Usep", productNameInList.getText());
    }
}