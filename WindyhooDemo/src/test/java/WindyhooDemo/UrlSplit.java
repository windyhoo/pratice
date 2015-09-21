package WindyhooDemo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UrlSplit {

	public static Map<String, String> toMap(String url) {
        Map<String, String> map = null;
        url=url.substring(url.indexOf("?")+1);
        if (url != null && url.indexOf("&") > -1 && url.indexOf("=") > -1) {
            map = new HashMap<String, String>();
             
            String[] arrTemp = url.split("&");          
            for (String str : arrTemp) {
                String[] qs = str.split("=");
                map.put(qs[0], qs[1]);
            }
        }
         
        return map;
    }
  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String, String> result=
			UrlSplit.toMap("http://132.121.92.211:8080/imonitorweb/daemonMgr/downLoadFile.action?fileName=bash_gnc.sh&filePath=/data/mondev/");
		System.out.println(result.size());
		Iterator i=result.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry e=(Map.Entry)i.next();
			
			System.out.println("key:"+(String)e.getKey());
			System.out.println("values:"+(String)e.getValue());

		}
	}

}
