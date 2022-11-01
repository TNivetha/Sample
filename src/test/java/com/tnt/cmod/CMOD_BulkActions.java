package com.tnt.cmod;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
public class CMOD_BulkActions extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_Connectivity conectivity = new CCD_Connectivity();
	CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
	CMOD_ReactiveCaseFlow ff= new CMOD_ReactiveCaseFlow();
	@BeforeSuite (alwaysRun=true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@BeforeClass(alwaysRun=true)
	public void beforeClass() {
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!(Keys == null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}
	@BeforeClass(alwaysRun=true)
	public void login()  {
		reusable.support_Login();
		conectivity.CloseTab();
	}
	@BeforeMethod(alwaysRun=true)
	public void Before_method(Method method) throws InterruptedException {
		Test_Initializer.Before_Method(method);
		startTime = 0;
		startTime = System.currentTimeMillis();
	}
	@AfterMethod(alwaysRun=true)
	public void After_Method(ITestResult result) {
		elapsedTime = 0;
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		extent.flush();
	}
//Proactive Cases Bulk Actions
	@Test(groups= {"bulkAction","peCase","singlePEAccept"})
	public void US_B513632_TC1_bulkOperationSinglePEAccept()  {
		reusable.US_B513632_TC1_bulkOperationSinglePEAccept();
	}
	@Test(groups= {"bulkAction","peCase","multiplePEAccept"})
	public void US_B513636_TC2_bulkOperationMultiplePEAccept()  {
		reusable.US_B513636_TC1_bulkOperationMultiplePEAccept();
	}
	@Test(groups= {"bulkAction","peCase","pe_CallBack"})
	public void US_B513636_TC3_bulkOperationMultiplePECallBack()  {
		reusable.US_B513636_TC2_bulkOperationMultiplePECallBack();
	}
	@Test(groups= {"bulkAction","peCase","pe_CaseRemarks"})
	public void US_B513636_TC4_bulkOperationMultiplePECaseRemarks()  {
		reusable.US_B513636_TC3_bulkOperationMultiplePECaseRemarks();
	}
	@Test(groups= {"bulkAction","peCase","pe_Action"})
	public void US_B513636_TC5_bulkOperationMultiplePEAction()  {
		reusable.US_B513636_TC4_bulkOperationMultiplePEAction();
	}
	@Test(groups= {"bulkAction","peCase","pe_RemarksForCust"})
	public void US_B513636_TC6_bulkOperationMultiplePERemarksForCust()  {
		reusable.US_B513636_TC5_bulkOperationMultiplePERemarksForCust();
	}
	//Reactive case Bulk Actions
	@Test(groups= {"bulkAction","bulkReactiveCase","rc_CaseRemarks"})
	public void US_B513636_TC1_bulkOperationMultipleReactiveCase_CaseRemarks()  {
		reusable.US_B513636_TC7_bulkOperationMultipleReactiveCase_CaseRemarks();
	}
	@Test(groups= {"bulkAction","bulkReactiveCase","rc_CallBack"})
	public void US_B513636_TC7_bulkOperationMultipleReactiveCaseCallBack()  {
		reusable.US_B513636_TC6_bulkOperationMultipleReactiveCaseCallBack();
	}
	@Test(groups= {"bulkAction","bulkReactiveCase","rc_RemarksForCust"})
	public void US_B513636_TC9_bulkOperationMultipleReactiveCase_RemarksforCust()  {
		reusable.US_B513636_TC8_bulkOperationMultipleReactiveCase_RemarksForCust();
	}
	@Test(groups= {"bulkAction","bulkReactiveCase","rc_Action"})
	public void US_B513636_TC10_bulkOperationMultipleReactiveCase_Action()  {
		reusable.US_B513636_TC9_bulkOperationMultipleReactiveCase_Action();
	}
	//B486175 X104OKL Date: 4-Oct-21
	@Test(groups= {"changeOIB","changeOIBinListView"})
	public void us_B486175_ChangeOIBinListViewByTL()  {
		reusable.us_B486175_VerifyChangeOIBinListViewByTL();;
	}
	@Test(groups= {"changeOIB","changeOIB_User"})
	public void us_B486175_ChangeOIBinListView_User()  {
		reusable.us_B486175_ChangeOIBinListView_User();;
	}
	@Test(groups= {"changeOIB","changeOIB_Queue"})
	public void us_B486175_ChangeOIBinListView_Queue()  {
		reusable.us_B486175_ChangeOIBinListView_Queue();;
	}
	@Test(groups= {"changeOIB","changeOIB_MergedAction"})
	public void us_B486175_ChangeOIB_MergedActions()  {
		reusable.us_B486175_ChangeOIB_MergedActions();;
	}
	@Test(groups= {"changeOIB","changeOIB_MergedAction","mergeAct_User"})
	public void us_B486175_ChangeOIB_User_MergedActions()  {
		reusable.us_B486175_ChangeOIB_User_MergedActions();;
	}
	@Test(groups= {"changeOIB","changeOIB_MergedAction","mergeAct_Queue"})
	public void us_B486175_ChangeOIB_Queue_MergedActions()  {
		reusable.us_B486175_ChangeOIB_Queue_MergedActions();;
	}
	@Test(groups= {"changeOIB","changeOIB_MergedAction","reactiveCase_User"})
	public void us_B486175_ChangeOIB_User_FromReactiveCase()  {
		reusable.us_B486175_ChangeOIB_User_FromReactiveCase();;
	}
	@Test(groups= {"changeOIB","changeOIB_MergedAction","reactiveCase_Queue"})
	public void us_B486175_ChangeOIB_Queue_FromReactiveCase()  {
		reusable.us_B486175_ChangeOIB_Queue_FromReactiveCase();;
	}
	@AfterMethod(alwaysRun=true)
public void afterMethod()  {
		conectivity.CloseTab();
		getDriver().navigate().refresh();
	}
	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
