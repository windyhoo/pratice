package WindyhooDemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class httptest {
	
	public static void main(String[] args) {
		HttpURLConnection con = null; 
		try {
			URL url = new URL("http://127.0.0.1:8080/imonitorweb/daemonMgr/clientCommand.action");
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			
			con.setInstanceFollowRedirects(true);
			con.setRequestMethod("POST");
			con.addRequestProperty("Content-type","application/x-www-form-urlencoded");
			con.setUseCaches(false);
			con.connect();
			
			StringBuffer sbContent = new StringBuffer();
			sbContent.append("hello test");
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(sbContent.toString());
			out.flush();
			out.close();
			
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null)  
            {  
                sb.append(line);  
            }
            con.disconnect();  
            System.out.println(sb.toString());  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				con=null;
			}
		}
	}

}
