package UserInfoServiceTEST;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.edianniu.mis.bean.LoginResult;
import com.edianniu.mis.service.dubbo.UserInfoService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestLogin {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:Test.xml");
	UserInfoService userinfoService = (UserInfoService) ctx
			.getBean("userInfoService");

	private String mobile;
	private String password;
	private int terminalType;
	private String secretKey;
	private int exceptcode;

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection data() throws BiffException, IOException {
		// ParamXml testdata=new ParamXml();

		// 测试数据以二位对象数组形式存储
		jxl.Workbook readwb = null;

		InputStream instream = new FileInputStream("F:/javaExcel/login.xls");
		readwb = Workbook.getWorkbook(instream);
		Sheet readsheet = readwb.getSheet(0);
		int rsColumns = readsheet.getColumns();
		int rsRows = readsheet.getRows();
		Object[][] test = new Object[rsRows][rsColumns];
		for (int i = 1; i < rsRows; i++) {
			for (int j = 0; j < rsColumns; j++) {
				Cell cell = readsheet.getCell(j, i);
				if (j == 2 || j == 4)
					test[i][j] = Integer.parseInt(cell.getContents());
				else
					test[i][j] = cell.getContents();
			}
		}
		for (int i = 1; i < rsRows; i++) {
			for (int j = 0; j < rsColumns; j++) {
				System.out.print(test[i][j] + " ");
			}
			System.out.println();
		}

		readwb.close();
		// String[][] temp=testdata.getparam("./testxml/test.xml");
		// System.out.print(test);
		return Arrays.asList(test);
		// String[][] temp=testdata.getparam("./testxml/test.xml");
		// System.out.print(test);

	}

	// 构造函数，对变量进行初始化
	public TestLogin(String mobile, String password, int terminalType,
			String secretKey, int exceptcodetemp) {
		this.mobile = mobile;
		// System.out.print(mobile);
		this.password = password;
		this.terminalType = terminalType;
		this.secretKey = secretKey;
		this.exceptcode = exceptcodetemp;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testlogin() {
		/*
		 * String mobile="17091613448"; String password="123456"; int
		 * terminalType=1; String secretKey="";
		 */
		LoginResult loginresult = userinfoService.login(mobile, password,
				terminalType, secretKey);
		System.out.println("message=" + loginresult.getResultMessage());
		assertEquals(exceptcode, loginresult.getResultCode());
	}

}
