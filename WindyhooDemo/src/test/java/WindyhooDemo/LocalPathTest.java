package WindyhooDemo;

public class LocalPathTest {

	public static void getClusterNameByHostPath(String hostPath,String hostIp,String clusterName) {
		if(hostPath.charAt(hostPath.length()-1)=='/') {
			hostPath=hostPath.substring(0, hostPath.length()-1);
		}
		
		int hostBeginPos=hostPath.lastIndexOf('/');
		hostIp=hostPath.substring(hostBeginPos+1);
		
		int clusterBeginPos=hostPath.lastIndexOf('/',hostBeginPos-1);
		clusterName=hostPath.substring(clusterBeginPos+1,hostBeginPos);
	}

	public static void main(String[] args) {		
		String clusterPath="/data/mondev/iMonitor/ganglia/rrds/server-51-109/";
		if(clusterPath.charAt(clusterPath.length()-1)=='/') {
			clusterPath=clusterPath.substring(0, clusterPath.length()-1);
		}
		String clusterName=clusterPath.substring(clusterPath.lastIndexOf('/')+1,clusterPath.length());
		System.out.println(clusterPath);
		System.out.println(clusterName);
		
		clusterPath="/data/mondev/iMonitor/ganglia/rrds/server-51-109/127.0.0.1";
		String one = "";
		String two = "";
		getClusterNameByHostPath(clusterPath,one,two);
		System.out.println(one);
		System.out.println(two);
	}

}
