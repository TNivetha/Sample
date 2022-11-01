package util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
public class TestUtil {
	static Workbook book;
	static XSSFSheet sheet;
	
	public static String Sheet_Path= "D:\\Shubham_N\\Automation march2022\\qcoe-acm-acm\\src\\test\\java\\testData\\TaskEscalationSLA.xlsx";
	public static Object[][] getTestData(String sheetname)
	
	{
		FileInputStream file = null;
		try {
			file= new FileInputStream(FilenameUtils.getName(Sheet_Path));
			System.out.println(Sheet_Path);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book= WorkbookFactory.create(file);
		}
		catch(InvalidFormatException e) {
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		sheet= (XSSFSheet) book.getSheet(sheetname);
		Object[][] data= new Object[((XSSFSheet) sheet).getLastRowNum()][((XSSFSheet) sheet).getRow(0).getLastCellNum()];
		
		System.out.println(sheet.getLastRowNum());
		for (int i=0; i<sheet.getLastRowNum(); )
		{
		
			for (int k=0;k<sheet.getRow(0).getLastCellNum();k++)
			{
				data[i][k]=sheet.getRow(i+1).getCell(k).toString();
				System.out.println(data[i][k]);
			}
			i++;
			
		}
		
		return data;
	}
	public static ArrayList<Object[]> getTestDataSLA(){
		ArrayList<Object[]> myData=new ArrayList<Object[]>();
		FileInputStream file = null;
		try {
			file= new FileInputStream("D:\\Shubham_N\\Automation march2022\\CMOD_CCD 28th Apr\\qcoe-acm-acm\\src\\test\\java\\testData\\TaskEscalationSLA.xlsx");
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book= WorkbookFactory.create(file);
		}
		catch(InvalidFormatException e) {
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		XSSFSheet sheet1;
		sheet1=(XSSFSheet) book.getSheet("TaskEscalationCaseTestData");
		int rows=sheet1.getLastRowNum();
		System.out.println("rows: "+rows);
		for (int i=1;i<=rows;i++)
		{
			String reason=sheet1.getRow(i).getCell(0).getStringCellValue();
			String cause=sheet1.getRow(i).getCell(1).getStringCellValue();
			String consignmentNo=sheet1.getRow(i).getCell(2).getStringCellValue();
			Object ob[]= {reason,cause,consignmentNo};
			myData.add(ob);
		}
		
		return myData;
	}
	public static ArrayList<String[]> getTestDataSLAFedExOvergoods(){
		ArrayList<String[]> myData=new ArrayList<String[]>();
		FileInputStream file = null;
		try {
			file= new FileInputStream("D:\\Shubham_N\\Automation march2022\\CMOD_CCD 28th Apr\\qcoe-acm-acm\\src\\test\\java\\testData\\TaskEscalationSLA.xlsx");
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book= WorkbookFactory.create(file);
		}
		catch(InvalidFormatException e) {
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		XSSFSheet sheet1;
		sheet1=(XSSFSheet) book.getSheet("TaskEscalationFedExOvergoods");
		int rows=sheet1.getLastRowNum();
		System.out.println("rows: "+rows);
		for (int i=1;i<=rows;i++)
		{
			String country=sheet1.getRow(i).getCell(0).getStringCellValue();
			String location=sheet1.getRow(i).getCell(1).getStringCellValue();
			String assignto=sheet1.getRow(i).getCell(2).getStringCellValue();
			String consignmentNo=sheet1.getRow(i).getCell(3).getStringCellValue();
			String str[]= {country,location,assignto,consignmentNo};
			myData.add(str);
		}
		
		return myData;
	}
}
