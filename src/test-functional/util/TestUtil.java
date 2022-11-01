package util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
}
