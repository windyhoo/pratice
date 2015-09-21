package WindyhooDemo;

public class PSystemInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String name    = System.getProperty("os.name");
        String arch    = System.getProperty("os.arch");
        String version = System.getProperty("os.version");

        System.out.println(name);
        System.out.println(arch);
        System.out.println(version);
	}

}
