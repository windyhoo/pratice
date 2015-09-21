package WindyhooDemo;

import java.io.UnsupportedEncodingException;

public class UrlCodeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name="%E5%85%B6%E4%BB%96";
		try {
			String decodeParams = java.net.URLDecoder.decode(name , "UTF-8");
			System.out.println(decodeParams);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
