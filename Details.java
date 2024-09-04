package reusable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class Details
    {
        private WebDriver driver;
       
        public String[] firstName()
        {
            String[] individualName = { };
            int rowCount1 = driver.findElements(By.xpath("//*[@class='even']")).size();
            int rowCount2 = driver.findElements(By.xpath("//*[@class='odd']")).size();
            int rowCount = rowCount1 + rowCount2;

            for(int i=0; i< rowCount; i++)
            {
                int j = i + 1;
               driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[6]/div/div/div/div[2]/div[1]/form/div/div[1]/div[4]/div[2]/div/table/tbody/tr["+j+"]/td[1]")).getText();
				
            }

            return individualName;
        }
    }
