package sk.samplers.openfin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class TestOpenFinApp {

    public static void main(String[] args) throws Exception {
        String execPath = System.getProperty("ExecPath");   // path to OpenFinRVM.exe
        String execArgs = System.getProperty("ExecArgs");   // command arguments for RunOpenFin.bat, such as --config="app.json"
        String remoteDriverURL = System.getProperty("RemoteDriverURL");  // URL to Selenium server or chromedriver
        String debuggerAddress = System.getProperty("DebuggerAddress");

        WebDriver driver;

        ChromeOptions options = new ChromeOptions();
        options.setBinary(execPath);
        options.addArguments(execArgs);
        if (debuggerAddress != null) {
            // if devtools_port is set in app.json and ChromeDriver needs to communicate on that port,  set the following propery
            // to be the same as devtools_port
            // if debuggerAddress is set,  ChromeDriver does NOT start "binary" and assumes it is already running,
            // it needs to start separately
            options.setExperimentalOption("debuggerAddress", debuggerAddress);
            launchOpenfinApp(execPath, execArgs);
        }
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY,  options);

        System.out.println("Creating remote driver " + remoteDriverURL);
        driver = new RemoteWebDriver(new URL(remoteDriverURL), capabilities);

        System.out.println("Got the driver " + driver.getCurrentUrl());
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        System.out.println("Setting the price ...");
        sleep(5);

        //driver.switchTo().window("Order Ticket");

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        Random rnd = new Random();
        double minPrice = 20.0;
        double maxPrice = 30.0;
        for (int i=0; i < 10; i++) {

            double price = minPrice + (maxPrice - minPrice) * rnd.nextDouble();

            mainPage.sendPrice(price);
            sleep(2);
        }
        System.out.println("Done. Closing the window.");
        sleep(1);
        driver.close();
    }

    private static void launchOpenfinApp(String path, String args) throws Exception{
        System.out.println(String.format("Starting %s %s", path, args));
        List<String> list = new ArrayList<>();
        list.add("cmd.exe");
        list.add("/C");
        list.add(path);
        list.add(args);
        ProcessBuilder pb = new ProcessBuilder(list);
        pb.inheritIO();
        pb.start();
    }

    private static void sleep(long secs) {
        System.out.println("Sleeping for " + secs + " seconds");
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static class MainPage {
        @FindBy(id="price")
        WebElement priceElement;

        @FindBy(id="send_button")
        WebElement sendButton;

        private void sendPrice(double price) {
            String priceStr = String.format("%.2f", price);
            priceElement.clear();
            priceElement.sendKeys(priceStr);
            sendButton.click();
        }

    }
}
