# Test Framework

## Structure
*******

The Framework Directory structure is as follows

-> driver 
  - used is chromedriver as it's been catered for as it's used in the project. Other drivers can be implemented as desired.
  
   -> src
   
    -> screenshots 
     - if screenshot is not showing refresh the folder's location printed in console
    -> resources 
     - this where chromedriver is located and application.properties for test data
    -> target 
     - this is where screenshot is stored 
    -> surefire-report
     - check emailable-report.html or index.html for detailed report
     -> testng.xml
     - this must be run as testng suite as test is incorporated here 
     - to get the desired report, testng.xml must be run as this is how to get screenshots on failure as the listener is located here.
     -> pom.xml
     - dependency injection location
    
      -> java
        -> com.bjss
          -> ItemDetails and ItemList this is where details of items shopped for are kept and retrieved for later use
          -> Pages - Location of Page Object Model and tests functionalities in BasePage
          -> tests - Where test is located
          -> Support - test listener that generates screenshot 
          -> Util - NumberUtil for generating numbers and SeleniumBaseTest for instantiating properties and required utilities in test
          
# Note test will always fails at line 135 of EcommerceTest as a result of this: assertion Assert.assertEquals("Maroon",firstProductColor);