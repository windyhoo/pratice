package WindyhooDemo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JsonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		HashMap<String,String> paramMap=new HashMap<String,String>();
//		paramMap.put("daemonName", "daemon_127.0.0.1");
//		paramMap.put("actionInstId", "6");
//
//		String jStr=JSON.toJSONString(paramMap);
//		System.out.println("jStr:"+jStr);


//		String str="{\"actionInstId\":\"6\",\"daemonName\":\"daemon_127.0.0.1\"}";
//		Map<String,String> remap=JSON.parseObject(jStr, Map.class);
//		System.out.println(remap.size());
//		System.out.println(remap.get("daemonName"));
//		System.out.println(remap.get("actionInstId"));
		
//		String arr[]=new String[]{"1","2"};
//		System.out.println();
		JsonTest.getNetDate("127.0.0.1");
		
	}
    private static Date getNetDate(String hostName) {  
        Date date = null;  
        long result = 0;  
        try {  
            System.out.println("1");
            Socket socket = new Socket(hostName, 37);
            System.out.println("2");
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream(),  
                    socket.getReceiveBufferSize());
            System.out.println("3");
            int b1 = bis.read();  
            System.out.println(b1);

            int b2 = bis.read();  
            System.out.println(b2);

            int b3 = bis.read();  
            System.out.println(b3);

            int b4 = bis.read();  
            System.out.println(b4);

            if ((b1 | b2 | b3 | b3) < 0) {  
                return null;  
            }
            result = (((long) b1) << 24) + (b2 << 16) + (b3 << 8) + b4;
            System.out.println(result);
//            date = new Date(result * 1000 - EPOCH_OFFSET_MILLIS);  
            socket.close();  
        } catch (UnknownHostException ex) {  
//            Logger.getLogger(SyncTime.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (IOException ex) {  
//            Logger.getLogger(SyncTime.class.getName()).log(Level.SEVERE, null, ex);  
        }  
        return date;  
    }  
}
