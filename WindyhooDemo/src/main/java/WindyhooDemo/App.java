package WindyhooDemo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println(App.getBodyPid("pid=&daemonName=daemon"));
    }
    	
	public static String getBodyPid(String str) {
		//pid=117162&daemonName=daemon
		int beginPos=str.indexOf('=');
		int endPos=str.indexOf('&');
		
		System.out.println("beginPos:"+beginPos);
		System.out.println("endPos:"+endPos);
		return str.substring(beginPos+1, endPos);
	}

}
