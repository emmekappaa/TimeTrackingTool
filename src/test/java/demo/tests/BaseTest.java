package demo.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseTest {

    protected WebDriver driver;
    @Before
    public void setUp() {
        WebDriverManager manager = WebDriverManager.firefoxdriver();
        if (driver == null) {
            driver = manager.create();
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}