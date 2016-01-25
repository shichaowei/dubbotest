package StationInfoServiceTEST;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.edianniu.mis.bean.car.GetStationResult;
import com.edianniu.mis.bean.car.NearbyDistanceStations;
import com.edianniu.mis.service.dubbo.StationInfoService;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.edianniu.mis.bean.LoginResult;
import com.edianniu.mis.bean.car.GetStationResult;
import com.edianniu.mis.service.dubbo.StationInfoService;
import com.edianniu.mis.service.dubbo.UserInfoService;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import  java.util.Arrays;

import  java.util.Collection;
import  org.junit.runner.RunWith;

import  org.junit.runners.Parameterized;

import  org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class )
public class TestNearbyDistance
{
	ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath*:Test.xml");
    StationInfoService  stationinfo= (StationInfoService) ctx.getBean("stationInfoService");

    private NearbyDistanceStations nearbyDistanceStations;
    private int exceptcode;
    


   @Parameters
    public   static  Collection data() throws BiffException, IOException
   {
	   //ParamXml testdata=new ParamXml();
	   
	   jxl.Workbook readwb = null;

		InputStream instream = new FileInputStream("F:/javaExcel/nearbyDistanceStations.xls");
		readwb = Workbook.getWorkbook(instream);
		Sheet readsheet = readwb.getSheet(0);
		int rsColumns = readsheet.getColumns();
		int rsRows = readsheet.getRows();
		NearbyDistanceStations[] test = new NearbyDistanceStations[rsRows-1];
		for (int i = 1; i < rsRows; i++) {
			for (int j = 0; j < rsColumns; j++) {
				Cell cell = readsheet.getCell(j, i);
				if (j == 0)
				{
					
				}
				else if(j == 2)
					test[i-1] = Integer.parseInt(cell.getContents());
				else
					test[i-1].setLatitude(cell.getContents());
			}
		}
		readwb.close();
		
		return Arrays.asList(test);
	   
	   
	
		
	   


   } 
    
   // 构造函数，对变量进行初始化 
   public  TestNearbyDistance()  {
       this.id  =  id;
       this.secretkey=secretkey;
       this.exceptcode=exceptcodetemp;
  } 
   
   
	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void TestgetStation()
	{
		/*
		String mobile="17091613448";
        String password="123456";
        int terminalType=1;
        String secretKey="";
       */
		GetStationResult Result=stationinfo.getStation(id,secretkey);
        System.out.println("message="+Result.getResultMessage());
        assertEquals(exceptcode,Result.getResultCode());
	}

}
