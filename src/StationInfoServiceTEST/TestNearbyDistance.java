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
import com.edianniu.mis.bean.car.NearbyDistanceStationsResult;
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

    private NearbyDistanceStations nearbyDistanceStations=new NearbyDistanceStations();
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
		Object[][] test = new Object[rsRows-1][rsColumns];
		for (int i = 1; i < rsRows; i++) 
		{
			NearbyDistanceStations temp=new NearbyDistanceStations();
			int resulttemp=0;
			for (int j = 0; j < rsColumns; j++) 
			{
				Cell cell = readsheet.getCell(j, i);
				if (j == 2)
				{
					test[i-1][j] = Integer.parseInt(cell.getContents());
					System.out.print(test[i-1][j]);
				}
				else if(j == 4)
					test[i-1][j] = Integer.parseInt(cell.getContents());
				else
					test[i-1][j] = cell.getContents();

			}
		readwb.close();
		}
		return Arrays.asList(test);

   } 
    
   // 构造函数，对变量进行初始化 
   public  TestNearbyDistance(String latitude,String longitude,int distance,String secretkey,int exceptcodetemp )  {
	   System.out.println(latitude);
	   this.nearbyDistanceStations.setLatitude(latitude);
	   this.nearbyDistanceStations.setLongitude(longitude);
	   this.nearbyDistanceStations.setDistance(distance);
	   this.nearbyDistanceStations.setSecretkey(secretkey);
       this.exceptcode=exceptcodetemp;
  } 
   
   
	@Before
	public void setUp() throws Exception
	{
		System.out.println("sssssss");
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
		NearbyDistanceStationsResult Result=stationinfo.getStations(nearbyDistanceStations);
        System.out.println("message="+Result.getResultMessage());
        assertEquals(exceptcode,Result.getResultCode());
	}

}
