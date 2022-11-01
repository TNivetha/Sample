package com.tnt.ccd;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
public class CCD_CMOD_SmokeTest extends Driver
{
	long elapsedTime = 0;
	long startTime = 0;

	@BeforeSuite(alwaysRun=true)
	public void beforeSuite()
	{
		try
		{
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@BeforeClass(alwaysRun=true)
	public void beforeClass()
	{
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!(Keys==null))) {
		{
			Key_Array = Keys.split(",");
			for(int i=0; i<Key_Array.length; i++)
			{
				System.out.println(Key_Array[i]);
			}
		}
	}
	@BeforeClass(alwaysRun=true)
	public void login() throws Exception {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FF_Reusable reusable=new CMOD_FF_Reusable();
		String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
				CCD_CMOD_SmokeTest.Key_Array[6]);
		String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
				CCD_CMOD_SmokeTest.Key_Array[6]);
		reusable.csr_Login(Username, Password);
		connectivity.CloseTab();
	}
	@BeforeMethod(alwaysRun=true)
	public void Before_method(Method method)
	{
		Test_Initializer.Before_Method(method);
	}
	
	@Test(groups= {"smokeTest","listService"})
	public void trackandTrace_PartialConsignmentSearch() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.trackandTrace_PartialConsignmentSearch();
	}
	@Test(groups= {"smokeTest","deliveryArea"})
	public void deliveryAreaInfoService_in_Consignment_Details() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.deliveryAreaInfoService_in_Consignment_Details();
	}
	@Test(groups= {"smokeTest","transitTime"})
	public void consignment_TransitTime_from_CasePage() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.consignment_TransitTime_from_CasePage();
	}
	@Test(groups= {"smokeTest","retriveService"})
	public void trackandTrace_ConsignmentSearch() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.trackandTrace_ConsignmentSearch();
	}
	@Test(groups= {"smokeTest","ratingInvoice"})
	public void consignment_RatingAndInvoicing_from_CasePage() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.consignment_RatingAndInvoicing_from_CasePage();
	}
	@Test(groups= {"smokeTest","proactiveException"})
	public void proactiveException_Service() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.proactiveException_Service();
	}
	
	@Test(groups= {"smokeTest","consignmentNoField"})
	public void trackAndTrace_Validation_on_ConsignmentNo() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.trackAndTrace_Validation_on_ConsignmentNo();
	}
	@Test(groups= {"smokeTest","consignmentNoField_inValidFormat"})
	public void trackAndTrace_ValidationonerrorMessage_when_input_wrong_ConsignmentNo() throws Exception
	{
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.trackAndTrace_ValidationonerrorMessage_when_input_wrong_ConsignmentNo();
	}

	@AfterMethod(alwaysRun = true)
	public void closeTab() throws Exception {		
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		driver.navigate().refresh();
		CCD_Connectivity.CloseTab();		
	}
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		Test_Initializer.After_Method(result);
		extent.flush();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		//driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
