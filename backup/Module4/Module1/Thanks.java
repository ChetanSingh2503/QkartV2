package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Thanks {

    RemoteWebDriver driver;
    public Thanks(RemoteWebDriver driver) {
        this.driver = driver;
    }
    public Boolean verifyAdvertisementsPresent(WebDriver driver, String[] adNames){
        Boolean status;
        List<WebElement> frames = driver.findElements(By.xpath("//iframe"));
        status = (frames.size()==3)? true: false;
        if(status){
            int index=0;
            for(WebElement frame: frames){
                driver.switchTo().frame(frame);
                String adName = driver.findElement(By.xpath("//body")).getText();
                // System.out.println(adName);
                status = (adName.contains(adNames[index]))? true: false;
                index++;
                driver.switchTo().defaultContent();
            }
        }
        return status;
    }

    public Boolean verifyButtonsInQkartAd(WebDriver driver, String adName){
        Boolean status = false;
        List<WebElement> frames = driver.findElements(By.xpath("//iframe"));                    
        String parentHandle = driver.getWindowHandle();
            for(WebElement frame: frames){
                driver.switchTo().frame(frame);
                String adNameActual = driver.findElement(By.xpath("//body")).getText();
                if(adNameActual.contains(adName)){
                    // System.out.println("matched ad "+adName);
                    List<WebElement> buttons = driver.findElements(By.xpath("//button"));
                    for(WebElement button: buttons){
                        status = button.isEnabled(); 
                        if(button.getText().equals("Buy Now")){
                            System.out.println("clicking Buy Now button");
                            button.click();
                            driver.switchTo().defaultContent();                            
                            Set<String> handles = driver.getWindowHandles();
                            if(handles.size()>1){
                                for(String handle: handles){
                                    if(!handle.equals(parentHandle)){
                                        driver.switchTo().window(handle);
                                        String currentURL = driver.getCurrentUrl();
                                        status = currentURL.endsWith("/checkout")? true: false;
                                        System.out.println(adName+ " URL ends with checkout? "+status);
                                        driver.close();
                                        driver.switchTo().window(parentHandle);
                                        driver.switchTo().parentFrame();                            
                                    }
                                }
                            }
                            else{
                                    String currentURL = driver.getCurrentUrl();
                                    status = currentURL.endsWith("/checkout")? true: false;
                                    System.out.println(adName+ " URL ends with checkout? "+status);
                                    driver.navigate().back();
                                    driver.switchTo().parentFrame();                            
                            }
                            driver.switchTo().defaultContent();                            
                        }                         
                    }
                    driver.switchTo().defaultContent();                    
                    break;                   
                }  
                driver.switchTo().defaultContent();                              
            } 
                
        return status;
    }

}