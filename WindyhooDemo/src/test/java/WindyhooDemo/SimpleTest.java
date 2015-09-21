package WindyhooDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimpleTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
//		String url="C:/iMonitor/java/";
//
//		url=url.replaceAll("\\/", "\\\\");
//		System.out.println(url);
		
//		Runtime rt = Runtime.getRuntime();
//		Process proc = rt.exec("cmd.exe /c type C:\\iMonitor\\ganglia\\gmond_pid.txt");
//		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//		String line;
//		int result = proc.waitFor();
//		System.out.println("result:"+result);
//		while ((line = reader.readLine()) != null) {
//			System.out.println(line);
//		}
		
//		List<String> partitionName=new ArrayList<String>();
//		partitionName.add("/hello");
//		partitionName.add("/boot");
//		System.out.println(partitionName.toString());
		
		String menuList=",";
		System.out.println(menuList.length());

		String[] re=menuList.split(",");
		System.out.println(re.length);
		for(int i=0;i<re.length;++i) {
			System.out.println(re[i]);
		}
	}

}
