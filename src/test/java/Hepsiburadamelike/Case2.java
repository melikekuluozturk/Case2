package Hepsiburadamelike;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Case2 {
    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;
    String selectedFavouriteProduct;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "hepsiburadamelike");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("appPackage", "com.pozitron.hepsiburada");
        caps.setCapability("appActivity", "com.hepsiburada.ui.startup.SplashActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 100);
    }

    @Test
    public void runTestCase1() {
        testCase2();
    }

    public void testCase2(){
        //click dod_all
        WebElement dodAll = wait.until(ExpectedConditions.elementToBeClickable(By.id("dod_all")));
        dodAll.click();

        //Selected product item
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement productListView = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/rv_pl_products")));
        List<WebElement> productElements = productListView.findElements(By.className("android.widget.LinearLayout"));
        WebElement productItem = productElements.get(3);
        productItem.click();

        //Click image container
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement imageContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/productImage")));
        imageContainer.click();

        //Select image and swipe
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement imagePager = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/imagePager")));

        try {
            SwipeScreen(imagePager,driver);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //click left icon
        WebElement leftIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("leftIcon")));
        leftIcon.click();

        //Product Name get
        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/productName")));
        selectedFavouriteProduct = productName.getText();

        //click favourite button
        WebElement favouriteButton =wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("favoriye ekle")));
        favouriteButton.click();

        //selected email area
        WebElement emailArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.EditText")));
        emailArea.sendKeys("testmelike1@outlook.com");

        //click login button
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.Button")));
        loginButton.click();

        WebElement passwordArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@resource-id='txtPassword']")));
        passwordArea.click();
        passwordArea.sendKeys("test123T");

        //click login button
        WebElement loginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.Button")));
        loginButton2.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Check loginpopup isdisplayed
        WebElement loginSuccessPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/parentPanel")));
        if(loginSuccessPopup.isDisplayed()){
            System.out.println("Login başarılı!");
        }

        //Click loginpopup button
        WebElement loginPopupButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        loginPopupButton.click();

        //click left icon
        WebElement leftIconProductDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/leftIcon")));
        leftIconProductDetail.click();

        //My account click
        WebElement accountItem = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Hesabım")));
        accountItem.click();

        //My likes click
        WebElement likeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("account_menu_5")));
        likeMenu.click();

        //favourite productlist
        WebElement productList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ListView")));
        List<WebElement> productChildElements = productList.findElements(By.className("android.view.View"));
        for(WebElement favouriteProductItem: productChildElements){
            WebElement favProductItemName = favouriteProductItem.findElement(By.xpath("//android.view.View/android.view.View[6]"));
            if(selectedFavouriteProduct.equalsIgnoreCase(favProductItemName.getText())){
                System.out.println("Ürün favori ekleme başarılı!");
                return;
            }
        }
    }

    public static void SwipeScreen(WebElement el, WebDriver driver) throws InterruptedException {
        Dimension dimension = el.getSize();

        int Anchor = el.getSize().getHeight()/2;

        double ScreenWidthStart = dimension.getWidth() * 0.8;
        int scrollStart = (int) ScreenWidthStart;

        double ScreenWidthEnd = dimension.getWidth() * 0.2;
        int scrollEnd = (int) ScreenWidthEnd;

        new TouchAction((PerformsTouchActions) driver)
                .press(PointOption.point(scrollStart, Anchor))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(scrollEnd, Anchor))
                .release().perform();

        Thread.sleep(3000);
    }

    @AfterMethod
    public void teardown() {

    }
}
