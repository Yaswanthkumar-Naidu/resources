package report_utilities.ExtentReport;


public class ExtentTestSteps
 {
     public String testStepName;
     public String testStepDesc;
     public ExtentConstants.TestStepStatus testStepStatus;

     public ExtentConstants.TestStepStatus getTestStepStatus()
     {
         return testStepStatus;
     }

     public void setTestStepStatus(ExtentConstants.TestStepStatus testStepStatus)
     {
         this.testStepStatus = testStepStatus;
     }

     public String getTestStepName()
     {
         return testStepName;
     }

     public void setTestStepName(String testStepName)
     {
         this.testStepName = testStepName;
     }


     public String getTestStepDesc()
     {
         return testStepDesc;
     }

     public void setTestStepDesc(String testStepDesc)
     {
         this.testStepDesc = testStepDesc;
     }
 }
