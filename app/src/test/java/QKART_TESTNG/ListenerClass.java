
package QKART_TESTNG;

//import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {
    
    public void onTestStart(ITestResult result) {
        QKART_Tests.takeScreenshot("startTestcase", result.getName());
        System.out.println("New Test Started" +result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        QKART_Tests.takeScreenshot("onTestSuccess", result.getName());
        System.out.println("onTestSuccess Method" +result.getName());
    }

    public void onTestFailure(ITestResult result) {
        QKART_Tests.takeScreenshot("onTestFailure", result.getName());
        System.out.println("onTestFailure Method" +result.getName());
    }

   
}