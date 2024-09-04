package reusable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map.Entry;

import testsettings.TestRunSettings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageLoadTimingInterceptor implements InvocationHandler {
	private static final Logger logger =LoggerFactory.getLogger(PageLoadTimingInterceptor.class.getName());
	   

    private WebDriver driver;
    
    public PageLoadTimingInterceptor(WebDriver driver) {
        this.driver = driver;
       
    }
    public static WebDriver createProxy(WebDriver driver) {
        Class<?>[] interfaces = {WebDriver.class, JavascriptExecutor.class,TakesScreenshot.class};
        
        Object proxyInstance = Proxy.newProxyInstance(
                WebDriver.class.getClassLoader(),
                interfaces,
                new PageLoadTimingInterceptor(driver)
        );
        
        if (proxyInstance instanceof WebDriver && proxyInstance instanceof JavascriptExecutor) 
        {
            return (WebDriver) proxyInstance;
        }
        if (proxyInstance instanceof WebDriver) {
            WebDriver proxyDriver = (WebDriver) proxyInstance;

            if (proxyInstance instanceof JavascriptExecutor) {
            	JavascriptExecutor jsExecutor = (JavascriptExecutor) proxyInstance;
                return proxyDriver;
            } 
           
            else {
               
                return proxyDriver;
            }
        } else {
            
            return null;
        }
    }


    private Object handleScreenshotMethod(Method method, Object[] args) {
        return null;
    }
    
    private LinkedHashMap<String, String> methodPreviousUrls = new LinkedHashMap<>();
    private LinkedHashMap<String, String> previousmethodUrls = new LinkedHashMap<>();

    LocalTime previoustime = null;
    String previousUrl = null;
    String previousmethodurl=null;
    @Override
    public  Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        Object result; 
        

            result = method.invoke(driver, args);
            String currentUrl = driver.getCurrentUrl();
          
            ScreenNameFetcher  screenname=new ScreenNameFetcher();
                      
            String previousScreenName = previousUrl;
            String curentScreenName;
            try 
            {

            		curentScreenName=screenname.getScreenName(currentUrl);

            }
            catch(Exception e)
            {
            	 curentScreenName=screenname.getScreenName(currentUrl);
            }

        	if(previousScreenName==null ||previousScreenName.equalsIgnoreCase("null") ) 
        	{
        		previousScreenName="None";
        	}

        	if(curentScreenName==null ||curentScreenName.equalsIgnoreCase("null") ) 
        	{
        		curentScreenName=currentUrl;
        	}
            
        	
            LocalTime currentTime=LocalTime.now();            
            extracted(method, result, currentUrl, previousScreenName, curentScreenName, currentTime);
           
            if(methodPreviousUrls.isEmpty()) 
         	 {
            	previoustime=LocalTime.now();
        		previousmethodUrls.put(method.getName(), currentUrl);

         		 methodPreviousUrls.put(method.getName(), curentScreenName); 
         		previousUrl = methodPreviousUrls.get(method.getName());
         		previousmethodurl = previousmethodUrls.get(method.getName());
         	 }
        
        return result;
    }
	private void extracted(Method method, Object result, String currentUrl, String previousScreenName,
			String curentScreenName, LocalTime currentTime) {
		if (result != null && !implementsTakesScreenshot(result))

		{
		
		    if(!currentUrl.equals(previousmethodurl))
		    {
		    	Duration duration = null ;
		    	long differenceInSeconds=0;
		    	
		    	
		    	if(previoustime != null) 
		    	{
		    		duration= Duration.between(previoustime, currentTime);

		    		differenceInSeconds = duration.getSeconds();
		    	}
		    	
		    	else
		    	{
		    		duration= Duration.between(currentTime, LocalTime.now());

		    		differenceInSeconds = duration.getSeconds();
		    	}
		    	
		        
		    	if(previousUrl!=null && differenceInSeconds>0) 
		    	{ 
		    		
		    		LinkedHashMap<String,String> screenNames=new LinkedHashMap<>();
		    		ArrayList<String> timediff=new ArrayList<>();

		    		screenNames.put(previousScreenName, curentScreenName);
		    		timediff.add(String.valueOf(differenceInSeconds));
		    		
		    		double pageLoadtiming=getPageLoadTimeInSeconds(driver);
		     		double dOMLoadtiming=getDomLoadTime(driver);
		    		
		    		{
		    			appendValue(TestRunSettings.uiperfdata, screenNames , timediff,pageLoadtiming,dOMLoadtiming);
		    		}

		    		previousmethodUrls.put(method.getName(), currentUrl);
		    		 methodPreviousUrls.put(method.getName(), curentScreenName);
		    		 previousUrl = methodPreviousUrls.get(method.getName());
		    		 previousmethodurl = previousmethodUrls.get(method.getName());
		    		 previoustime=LocalTime.now();
		    	}
		
		    }
		    
		   
		}
	}
    
    private static void appendValue(HashMap<HashMap<String, String>,ArrayList<String>> map, HashMap<String, String> key, ArrayList<String> value, double pageLoad, double domLoad) {
    	
    	boolean valuedescision=false;
    	
    	Map.Entry<HashMap<String, String>, ArrayList<String>> lastEntry = null;

        for (Map.Entry<HashMap<String, String>, ArrayList<String>> entry : map.entrySet())
        {
            lastEntry = entry;
        }
        if(lastEntry!=null) 
        {
        	 for(Entry<String, String> keyvalue: key.entrySet()) 
         	{
         		if (lastEntry.getKey().containsValue(keyvalue.getValue())) {
         			valuedescision=true;
                 }
         	}
        }
    	
        if (map != null && map.containsKey(key) && !valuedescision)
        {
            ArrayList<String> existingValue = map.get(key);
            ArrayList<String> appendedValue = new ArrayList<>() ;
            
            int existingvaluesize=existingValue.size();
            for(int i=0; i<existingvaluesize; i++) 
            {
            	appendedValue.addAll(existingValue);
            }
            appendedValue.addAll(value);
            map.put(key, appendedValue);
        } 
        else if (!valuedescision)
        {
        	
            map.put(key, value);
            
            TestRunSettings.pageLoadtime.add(String.valueOf(pageLoad));
    		TestRunSettings.domLoadtime.add(String.valueOf(domLoad));
        }
    }
    
    private static <K, V> Entry<K, V> getLastEntry(Map<K, V> map) {
        // Get the last entry from the map
        Entry<K, V> lastEntry = null;
        for (Entry<K, V> entry : map.entrySet()) {
            lastEntry = entry;
        }
        return lastEntry;
    }
    
    private static <K, V> K getSecondLastKey(Map<K, V> map) {
        // Check if the map has at least two entries
        if (map.size() < 2) {
            return null;
        }

        // Iterate through the entries to find the second-to-last key
        Entry<K, V> secondLastEntry = null;
        Entry<K, V> lastEntry = null;

        for (Entry<K, V> entry : map.entrySet()) {
            secondLastEntry = lastEntry;
            lastEntry = entry;
        }

        return (secondLastEntry != null) ? secondLastEntry.getKey() : null;
    }
   
    private boolean implementsTakesScreenshot(Object object) {
        try {
            Class<?> takesScreenshotClass = Class.forName("org.openqa.selenium.TakesScreenshot");

            return takesScreenshotClass.isAssignableFrom(object.getClass()) ||
                   (Proxy.isProxyClass(object.getClass()) &&
                    ((PageLoadTimingInterceptor) Proxy.getInvocationHandler(object)).driver.getClass().isAssignableFrom(takesScreenshotClass)) ||
                   object.getClass().getMethod("getScreenshotAs", OutputType.class) != null;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return false;
        }
    }

 
    
    private Object unwrapProxy(Object proxy, Class<?> targetInterface) {
        if (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof PageLoadTimingInterceptor) {
                PageLoadTimingInterceptor interceptor = (PageLoadTimingInterceptor) invocationHandler;
                Object targetObject = interceptor.driver;
                if (targetInterface.isAssignableFrom(targetObject.getClass())) {
                    return targetObject;
                }
            }
        }
        return null;
    }
    
    
    

        
        private double getPageLoadTimeInSeconds(WebDriver driver) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            
            long pageLoadTimeMillis = (long) jsExecutor.executeScript(
                    "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart);"
            );
       
            return pageLoadTimeMillis / 1000.0;

        }
        
        public static double getDomLoadTime(WebDriver driver) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;


            Object domLoadTime = jsExecutor.executeScript(
                    "return (window.performance.timing.domContentLoadedEventEnd - window.performance.timing.navigationStart);");

            return (domLoadTime instanceof Long) ? ((Long) domLoadTime) / 1000.0 : 0;
        }
        
        public static double getPageLoadStartTime(WebDriver driver) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            Object pageLoadStartTime = jsExecutor.executeScript(
                    "return window.performance.timing.navigationStart;");

            return (pageLoadStartTime instanceof Long) ? ((Long) pageLoadStartTime)/1000.0: 0;
        }

        public static double getPageReadyTime(WebDriver driver) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            Object pageReadyTime = jsExecutor.executeScript(
                    "return window.performance.timing.domContentLoadedEventEnd;");

            return (pageReadyTime instanceof Long) ? ((Long) pageReadyTime)/1000.0 : 0;
        }

        public static double getRedirectionTime(WebDriver driver, String url) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            jsExecutor.executeScript("window.location.href = arguments[0];", url);

            Object redirectionTime = jsExecutor.executeScript(
                    "return window.performance.timing.redirectEnd - window.performance.timing.redirectStart;");

            return (redirectionTime instanceof Long) ? ((Long) redirectionTime)/1000.0 : 0;
        }
        
        
        private static void createFolder(String folderPath) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }
        }

        
        private static void appendToNotepad(String notepadFilePath, String content) {
        	try (BufferedWriter writer = new BufferedWriter(new FileWriter(notepadFilePath, true))) {
                writer.write(content);
                writer.newLine(); 
            } catch (IOException e) {
            	logger.info("Error while appending:{} ", e.getMessage());
            }
        }
        
        private static void writeHashMapToFile(HashMap<HashMap<String, String>,ArrayList<String>> hashMap, String filePath) 
        {
        	
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            	writer.write("");
            	// Iterate through the entries and write key-value pairs
                for (Map.Entry<HashMap<String, String>,ArrayList<String>> entry : hashMap.entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine(); // Move to the next line for the next entry
                }

                logger.info("Key-value pairs written to notepad successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("Error writing key-value pairs to notepad: {}",e.getMessage());
            }
        }
        
        private boolean hasExecuteScriptMethod(Class<?> clazz) {
            while (clazz != null) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals("executeScript")) {
                        return true;
                    }
                }
                clazz = clazz.getSuperclass();
            }
            return false;
        }
        private boolean implementsJavascriptExecutor(Object object) {
            try {
                Class<?> javascriptExecutorClass = Class.forName("org.openqa.selenium.JavascriptExecutor");
                
                return javascriptExecutorClass.isInstance(object);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        
        private static String formatDuration(LocalDateTime startTime,LocalDateTime endTime) {
    		java.time.Duration duration= java.time.Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        
        
        public double pageloadtimeusingHttp(String url, long startTime) throws IOException 
        {
        	double loadTime = 0;
        	try {
              // Open the connection
              HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
              connection.setRequestMethod("GET");
              
              // Get the response code
              int responseCode = connection.getResponseCode();
              
              // Ensure a successful response code (e.g., 200 OK)
              if (responseCode == HttpURLConnection.HTTP_OK) {
                  long endTime = System.currentTimeMillis();
                   long loadTiming = endTime - startTime;
                   loadTime=loadTiming/1000.0;
              } 
              else 
              {
            	  loadTime = 0;
                  logger.info("Failed to load page. Response Code: {}",responseCode);
              }
        	}
        	catch(Exception e) 
        	{
        		loadTime = 0;
        	}
              
              return loadTime;
        }
        
        public double domloadtimeusingHttp(long startTime) 
        {
        	double loadTime = 0;
        	try {
        		 waitForDOMLoad(driver);

                 long endTime = System.currentTimeMillis();
                 long loadTiming = endTime - startTime;
                 loadTime=loadTiming/1000.0;
        	}
        	catch(Exception e) 
        	{
        		loadTime = 0;
        	}
              
              return loadTime;
        }
        
        private static void waitForDOMLoad(WebDriver driver) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            // Wait until the document.readyState is complete
            jsExecutor.executeScript("return document.readyState").equals("complete");
        }
        
        private static void waitForPageAndDomLoad(WebDriver driver) {
            WebDriverWait wait = new WebDriverWait(driver, 30); // Adjust the timeout as needed

            // Wait for both page load and DOMContentLoaded events
            wait.until((ExpectedCondition<Boolean>) webDriver -> {
                Object loadEventEndObj = ((JavascriptExecutor) webDriver).executeScript("return window.performance.timing.loadEventEnd;");
                Object domContentLoadedEventEndObj = ((JavascriptExecutor) webDriver).executeScript("return window.performance.timing.domContentLoadedEventEnd;");
                
                if (loadEventEndObj instanceof Long && domContentLoadedEventEndObj instanceof Long) {
                    Long loadEventEnd = (Long) loadEventEndObj;
                    Long domContentLoadedEventEnd = (Long) domContentLoadedEventEndObj;

                    return loadEventEnd != null && loadEventEnd != 0 && domContentLoadedEventEnd != null && domContentLoadedEventEnd != 0;
                }
                return false;
            });
        }
}
