package ssh;

/**
 * <p>Title: StringUtils</p>
 * <p>Description 提供䶿些针对字符串处理的工具方汿</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Eshore</p>
 * @author 
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtils {

	private static Object initLock = new Object();

	private static MessageDigest digest = null;

	private static final String commonWords[] = { "a", "and", "as", "at", "be",
			"do", "i", "if", "in", "is", "it", "so", "the", "to" };

	private static Map commonWordsMap = null;

	private static Random randGen = null;

	private static char numbersAndLetters[] = null;

	private static SimpleDateFormat datetimeformatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
 
	public StringUtils() {
	}

	public static String datetimetostr(Date d, String sFormat) {
		if (d == null) {

			return "";

		} else {

			SimpleDateFormat dateformatter = new SimpleDateFormat(sFormat);
			String s = dateformatter.format(d);
			dateformatter = null;
			return s;
		}
	}

	public static String dateTimeToStr(Timestamp d, String sFormat) {
		if (d == null) {
			return "";
		} else {
			SimpleDateFormat dateformatter = new SimpleDateFormat(sFormat);
			String s = dateformatter.format(d);
			return s;
		}

	}

	public static Date strtodate(String s) {
		try {
			Date date = new Date(datetimeformatter.parse(s).getTime());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date1 = null;
		return date1;
	}

	// 桿测字符串是否存在中文字符
	public static boolean isChineseChar(String s) {
		boolean flag = false;
		if (s == null)
			return true;
		byte abyte0[] = s.getBytes();
		for (int i = 0; i < abyte0.length; i++) {
			if (abyte0[i] >= 0)
				continue;
			flag = true;
			break;
		}

		return flag;
	}

	// 把空字符串转换为empty
	public static final String nullToEmptyOfStr(String s) {
		if (s != null)
			return s.trim();
		else
			return "";
	}

	public static final String nullToEmptyOfObject(Object o) {
		if (o != null)
			return o.toString();
		else
			return "";
	}

	public static final Date strCSTtoDate(String data) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.LONG);
		Date date = null;
		try {
			date = df.parse(data);
		} catch (ParseException e) {
		}
		return date;
	}
	
	/**
	 * 将字符串转换成int，如果异常则返回0
	 * @param str
	 * @return
	 */
	public static final int stringToInt(String str){
		int i=0;
		if(str!=null){
			str=str.trim();
			try{
				i=Integer.parseInt(str);
			}catch(Exception e){}
		}
		return i;
	}
	
	public static final short stringToShort(String str){
		short i=0;
		if(str!=null){
			str=str.trim();
			try{
				i=Short.parseShort(str);
			}catch(Exception e){}
		}
		return i;
	}

	/**
	 * "" -->0
	 * 
	 * @param o
	 * @return
	 */
	public static final int nullTo0OfObject(Object o) {
		if (o != null) {
			if (o.toString().equals("")) {
				return 0;
			} else {
				return Integer.parseInt(o.toString());
			}
		} else
			return 0;
	}

	public static final String intTostr(Object o) {
		int i = nullTo0OfObject(o);
		return Integer.toString(i);
	}

	public static final String escapeErrorChar(String s) {
		String s1 = null;
		s1 = s;
		if (s1 == null) {
			return s1;
		} else {
			s1 = replace(s1, "\\", "\\\\");
			s1 = replace(s1, "\"", "\\\"");
			return s1;
		}
	}

	// 桿测字符串是否为数字串
	public static final boolean strIsDigital(String s) {
		boolean flag1 = true;
		char ac[] = s.toCharArray();
		for (int i = 0; i < ac.length;) {
			if (!Character.isDigit(ac[i]))
				flag1 = false;
			break;
		}

		return flag1;
	}

	// 桿测身份证
	public static final boolean checkIdCard(String s, String s1) {
		boolean flag = true;
		String s2 = "";
		String s3 = "";
		String s4 = "";
		String s5 = "";
		if (s.length() != 15 && s.length() != 18 || !isDate(s1))
			flag = false;
		else if (s.length() == 15) {
			if (!strIsDigital(s)) {
				flag = false;
			} else {
				String s6 = "19" + s.substring(6, 8);
				String s8 = s.substring(8, 10);
				String s10 = s.substring(10, 12);
				String s12 = s6 + "-" + s8 + "-" + s10;
				if (!s12.equals(s1))
					flag = false;
			}
		} else if (s.substring(17, 18).equals("X")
				|| s.substring(17, 18).equals("x")) {
			if (!strIsDigital(s.substring(0, 18)))
				flag = false;
		} else if (!strIsDigital(s)) {
			flag = false;
		} else {
			String s7 = s.substring(6, 10);
			String s9 = s.substring(10, 12);
			String s11 = s.substring(12, 14);
			String s13 = s7 + "-" + s9 + "-" + s11;
			if (!s13.equals(s1))
				flag = false;
		}
		return flag;
	}

	// 日期判断
	public static boolean isDate(String s) {
		boolean flag = false;
		DateFormat dateformat = DateFormat.getDateInstance();
		if (s == null)
			flag = false;
		try {
			java.util.Date date = dateformat.parse(s);
			flag = true;
		} catch (Exception exception) {
			flag = false;
		}
		return flag;
	}

	public static String isInputError(int i, int j) {
		if ((i & j) > 0)
			return "Error";
		else
			return "True";
	}

	// 把字符串转换为向酿
	public static Vector splite(String s, String s1) {
		Vector vector = new Vector();
		Object obj = null;
		Object obj1 = null;
		boolean flag = false;
		int i;
		for (; s.length() >= 0; s = s.substring(i + s1.length(), s.length())) {
			i = s.indexOf(s1);
			if (i < 0) {
				vector.addElement(s);
				break;
			}
			String s2 = s.substring(0, i);
			vector.addElement(s2);
		}

		return vector;
	}

	// 字符串替拿 s 搜索字符䶿 s1 要查找字符串 s2 要替换字符串
	public static final String replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}

			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String replaceStr(String preString, String preStr,
			String replacingStr) {
		int indent = preStr.length();
		if (preString.equals("")) {
			return "";
		} else {
			String newString = "";
			String oldString = preString;
			String tempStr = "";
			int tempIndex = 0;
			while (oldString.indexOf(preStr) >= 0) {
				int newIndex = oldString.indexOf(preStr);
				newString = oldString.substring(0, newIndex);
				tempStr = tempStr + newString + replacingStr;
				tempIndex = tempIndex + newString.length() + indent;
				oldString = oldString.substring(newIndex + indent);
			}
			tempStr = tempStr + oldString;
			return tempStr;
		}
	}

	public static final String replaceIgnoreCase(String s, String s1, String s2) {
		if (s == null)
			return null;
		String s3 = s.toLowerCase();
		String s4 = s1.toLowerCase();
		int i = 0;
		if ((i = s3.indexOf(s4, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s3.indexOf(s4, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}

			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String replace(String s, String s1, String s2, int ai[]) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			int j = 0;
			j++;
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int k = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += k;
			int l;
			for (l = i; (i = s.indexOf(s1, i)) > 0; l = i) {
				j++;
				stringbuffer.append(ac, l, i - l).append(ac1);
				i += k;
			}

			stringbuffer.append(ac, l, ac.length - l);
			ai[0] = j;
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String formatInputStr(String s) {
		String s1 = s;
		s1 = nullToEmptyOfStr(s1);
		s1 = escapeHTMLTags(s1);
		return s1;
	}

	public static final String escapeHTMLTags(String s) {
		if (s == null || s.length() == 0)
			return s;
		StringBuffer stringbuffer = new StringBuffer(s.length());
		byte byte0 = 32;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '<')
				stringbuffer.append("&lt;");
			else if (c == '>')
				stringbuffer.append("&gt;");
			else
				stringbuffer.append(c);
		}

		return stringbuffer.toString();
	}

	public static final synchronized String hash(String s) {
		if (digest == null)
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
				System.err
						.println("Failed to load the MD5 MessageDigest. Jive will be unable to function normally.");
				nosuchalgorithmexception.printStackTrace();
			}
		digest.update(s.getBytes());
		return toHex(digest.digest());
	}

	public static final String toHex(byte abyte0[]) {
		StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
		for (int i = 0; i < abyte0.length; i++) {
			if ((abyte0[i] & 0xff) < 16)
				stringbuffer.append("0");
			stringbuffer.append(Long.toString(abyte0[i] & 0xff, 16));
		}

		return stringbuffer.toString();
	}

	public static final String[] toLowerCaseWordArray(String s) {
		if (s == null || s.length() == 0)
			return new String[0];
		StringTokenizer stringtokenizer = new StringTokenizer(s, " ,\r\n.:/\\+");
		String as[] = new String[stringtokenizer.countTokens()];
		for (int i = 0; i < as.length; i++)
			as[i] = stringtokenizer.nextToken().toLowerCase();

		return as;
	}

	public static final String[] removeCommonWords(String as[]) {
		if (commonWordsMap == null)
			synchronized (initLock) {
				if (commonWordsMap == null) {
					commonWordsMap = new HashMap();
					for (int i = 0; i < commonWords.length; i++)
						commonWordsMap.put(commonWords[i], commonWords[i]);

				}
			}
		ArrayList arraylist = new ArrayList(as.length);
		for (int j = 0; j < as.length; j++)
			if (!commonWordsMap.containsKey(as[j]))
				arraylist.add(as[j]);

		return (String[]) arraylist.toArray(new String[arraylist.size()]);
	}

	public static final String randomString(int i) {
		if (i < 1)
			return null;
		if (randGen == null)
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.toCharArray();
				}
			}
		char ac[] = new char[i];
		for (int j = 0; j < ac.length; j++)
			ac[j] = numbersAndLetters[randGen.nextInt(71)];

		return new String(ac);
	}

	public static final String chopAtWord(String s, int i) {
		if (s == null)
			return s;
		char ac[] = s.toCharArray();
		int j = s.length();
		if (i < j)
			j = i;
		for (int k = 0; k < j - 1; k++) {
			if (ac[k] == '\r' && ac[k + 1] == '\n')
				return s.substring(0, k);
			if (ac[k] == '\n')
				return s.substring(0, k);
		}

		if (ac[j - 1] == '\n')
			return s.substring(0, j - 1);
		if (s.length() < i)
			return s;
		for (int l = i - 1; l > 0; l--)
			if (ac[l] == ' ')
				return s.substring(0, l).trim();

		return s.substring(0, i);
	}

	public static final String highlightWords(String s, String as[], String s1,
			String s2) {
		if (s == null || as == null || s1 == null || s2 == null)
			return null;
		for (int i = 0; i < as.length; i++) {
			String s3 = s.toLowerCase();
			char ac[] = s.toCharArray();
			String s4 = as[i].toLowerCase();
			int j = 0;
			if ((j = s3.indexOf(s4, j)) >= 0) {
				int k = s4.length();
				StringBuffer stringbuffer = new StringBuffer(ac.length);
				boolean flag = false;
				char c = ' ';
				if (j - 1 > 0) {
					c = ac[j - 1];
					if (!Character.isLetter(c))
						flag = true;
				}
				boolean flag1 = false;
				char c1 = ' ';
				if (j + k < ac.length) {
					c1 = ac[j + k];
					if (!Character.isLetter(c1))
						flag1 = true;
				}
				if (flag && flag1 || j == 0 && flag1) {
					stringbuffer.append(ac, 0, j);
					if (flag && c == ' ')
						stringbuffer.append(c);
					stringbuffer.append(s1);
					stringbuffer.append(ac, j, k).append(s2);
					if (flag1 && c1 == ' ')
						stringbuffer.append(c1);
				} else {
					stringbuffer.append(ac, 0, j);
					stringbuffer.append(ac, j, k);
				}
				j += k;
				int l;
				for (l = j; (j = s3.indexOf(s4, j)) > 0; l = j) {
					boolean flag2 = false;
					char c2 = ac[j - 1];
					if (!Character.isLetter(c2))
						flag2 = true;
					boolean flag3 = false;
					if (j + k < ac.length) {
						c1 = ac[j + k];
						if (!Character.isLetter(c1))
							flag3 = true;
					}
					if (flag2 && flag3 || j + k == ac.length) {
						stringbuffer.append(ac, l, j - l);
						if (flag2 && c2 == ' ')
							stringbuffer.append(c2);
						stringbuffer.append(s1);
						stringbuffer.append(ac, j, k).append(s2);
						if (flag3 && c1 == ' ')
							stringbuffer.append(c1);
					} else {
						stringbuffer.append(ac, l, j - l);
						stringbuffer.append(ac, j, k);
					}
					j += k;
				}

				stringbuffer.append(ac, l, ac.length - l);
				s = stringbuffer.toString();
			}
		}

		return s;
	}

	public static final String escapeForXML(String s) {
		if (s == null || s.length() == 0)
			return s;
		char ac[] = s.toCharArray();
		StringBuffer stringbuffer = new StringBuffer(ac.length);
		for (int i = 0; i < ac.length; i++) {
			char c = ac[i];
			if (c == '<')
				stringbuffer.append("&lt;");
			else if (c == '>')
				stringbuffer.append("&gt;");
			else if (c == '"')
				stringbuffer.append("&quot;");
			else if (c == '&')
				stringbuffer.append("&amp;");
			else
				stringbuffer.append(c);
		}

		return stringbuffer.toString();
	}

	public static final String unescapeFromXML(String s) {
		s = replace(s, "&lt;", "<");
		s = replace(s, "&gt;", ">");
		s = replace(s, "&amp;", "&");
		return replace(s, "&quot;", "\"");
	}

	public static final String formatTextArea(String s, int i) {
		if (s == null)
			return "";
		String s1 = s.trim();
		Vector vector = null;
		int j = i;
		s1 = s1 + "\n";
		vector = splite(s1, "\n");
		boolean flag = false;
		boolean flag1 = false;
		String s2 = "";
		String s4 = "";
		String s6 = "";
		char ac[] = s1.toCharArray();
		StringBuffer stringbuffer = new StringBuffer(ac.length);
		for (int k = 0; k < vector.size() - 1; k++) {
			String s5 = vector.elementAt(k).toString();
			s5 = replace(s5, "\r", "");
			if (s5.length() > j) {
				String s7 = s5;
				for (int l = 0; l < s5.length() / j; l++) {
					String s3 = s7.substring(0, j) + "\n";
					stringbuffer.append(s3.toCharArray(), 0,
							s3.toCharArray().length);
					s7 = s7.substring(j, s7.length());
				}

				stringbuffer.append((s7 + "\n").toCharArray(), 0, (s7 + "\n")
						.toCharArray().length);
			} else {
				stringbuffer.append((s5 + "\n").toCharArray(), 0, (s5 + "\n")
						.toCharArray().length);
			}
		}

		return stringbuffer.toString().trim();
	}

	/**
	 * author wjun
	 * 
	 * @param value
	 *            原㽿
	 * @param precision
	 *            小数精度
	 * @return
	 */
	public static String doubleToString(double value, int precision) {
		String val = Double.toString(value);
		if (val.indexOf(".") > 0) {// 如果有小数点,就按精度来取
			if ((val.substring(val.indexOf("."))).length() >= precision) {// 如果小数点位数大于精度，则按精度来取
				double add = 0.5;// 附加值，用于四舍五入
				for (int i = 0; i < precision; i++) {
					add = add / 10;
				}
				double resource = value + add;
				String res = Double.toString(resource);
				String pre = res.substring(0, res.indexOf("."));
				String post = res.substring(res.indexOf("."), res.indexOf(".")
						+ 1 + precision);
				val = pre + post;
			}
		}
		return val;
	}

	/**
	 * 将传入的字符串转换为中文字符串，并将空字符串转换䶿""
	 *
	 * @param strOrigin 原始字符䶿
	 * @return 中文字符䶿
	 */
	public static final String toChinese(String strOrigin) {
		if (strOrigin == null || strOrigin.equals(null)) {
			strOrigin = "";
		} else {
			strOrigin = strOrigin.trim();
		}

		try {
			strOrigin = new String(strOrigin.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
		}
		return strOrigin;
	}

	public static final String toGB2312(String m_str) {
		return toChinese(m_str);
	}

	public static final String toBig5(String m_str) {
		String return_str = "";
		try {
			byte m_bstr[] = m_str.getBytes("8859_1");
			return_str = new String(m_bstr, "Big5");
		} catch (Exception e) {
			System.err.println("toBig5 error caught: ".concat(String
					.valueOf(String.valueOf(e.getMessage()))));
		}
		return return_str;
	}




	public static final String toISO8859(String m_str) {
		String return_str = "";
		try {
			byte m_bstr[] = m_str.getBytes("GB2312");
			return_str = new String(m_bstr, "8859_1");
		} catch (Exception e) {
			System.err.println("toChinese error caught: ".concat(String
					.valueOf(String.valueOf(e.getMessage()))));
		}
		return return_str;
	}

	public static final String toDisplay(String m_str) {
		String return_str = "";
		try {
			byte m_bstr[] = m_str.getBytes("ISO8859_1");
			return_str = new String(m_bstr, "GBK");
		} catch (Exception e) {
			System.err.println("toChinese error caught: ".concat(String
					.valueOf(String.valueOf(e.getMessage()))));
		}
		return return_str;
		// return new String(m_str.getBytes("ISO8859_1"), "GBK");
	}
	
	public static String getToday() {
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateformatter.format(new Date());
	}

	public static String getTime() {
		SimpleDateFormat dateformatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformatter.format(new Date());
	}
/*
	public static String getHostAddress() {
		if (!ManagerConst.MANAGER_IP.equals("")) {
			return ManagerConst.MANAGER_IP;
		}
		String hostIpAddress = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			if (!address.isLoopbackAddress())
				hostIpAddress = address.getHostAddress();
			else {
				Enumeration netInterfaces = NetworkInterface
						.getNetworkInterfaces();
				InetAddress ip = null;
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					String niName = ni.getName();
					ip = (InetAddress) ni.getInetAddresses().nextElement();
					if (ip.isSiteLocalAddress() && !niName.startsWith("ppp")) {
						UmccLogger.info("Local Host Ip Address:"
								+ ip.getHostAddress());
						hostIpAddress = ip.getHostAddress();
						break;
					} else {
						ip = null;
					}
				}
			}
		} catch (Exception ex) {
			UmccLogger.severe("获取本机地址异常\n" + ex.getMessage());
		}
		if (hostIpAddress == null) {
			UmccLogger
					.severe("无法通过配置文件或系统获取Manager布署的本机IP地址！请在Manager启动的配置文乿(manager-config.xml)中配置主机IP参数響");
		}
		return hostIpAddress;
	}
*/
	/**
	 * i=1 K i=2 M
	 * 
	 * @param numbervalue
	 * @param i
	 * @return
	 */
	public static String getCapacity(String numbervalue, int i, int des) {
		try {

			int pos = numbervalue.indexOf("e");
			if (pos < 0) {
				pos = numbervalue.indexOf("E");
			}

			double pre = Double.parseDouble(numbervalue.substring(0, pos));
			String afterstr = numbervalue.substring(pos + 1, numbervalue
					.length());
			/*
			 * boolean isadd=true; if (afterstr.indexOf("-")>=0){ isadd=false;
			 * afterstr.replaceAll("-",""); }else{ afterstr.replaceAll("+",""); }
			 */
			double tub = Math.pow(10, Double.parseDouble(afterstr));
			double lastvalue = pre * tub / (Math.pow(1024, i));
			// System.out.println(lastvalue);

			return doubleToString(lastvalue, des);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 消除过期的数组对象元素的引用
	 * 
	 * @param objArys
	 */
	public static void eliminateObsoleteReferenceOfArray(Object[] objArys){
		for (int i = 0; i < objArys.length; i++)  
			objArys[i] = null;
	}
	
	public static String readFileToString(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}

	
	/**
	 * 根据行分隔符和列分隔符将字符串结果分隔成二维数组
	 * @param value
	 * @param lineSeparate
	 * @param listSeparate
	 * @return
	 */
	public static String[][] splitPlanar(String value,String lineSeparate,String columnSeparate){
		String[] rowValues=value.split(lineSeparate);
		int columnCount=1;
		for(String rowValue:rowValues){
			String[] columnValues=rowValue.split(columnSeparate);
			if(columnValues.length>columnCount)columnCount=columnValues.length;
		}
		String[][] results=new String[rowValues.length][columnCount];
		int i=0;
		for(String rowValue:rowValues){
			int j=0;
			String[] columnValues=rowValue.split(columnSeparate);
			for(String columnValue:columnValues){
				results[i][j]=columnValue.trim();
				j++;
			}
			i++;
		}
		return results;
	}
	
	
	//移除前后空行
	public static String trimLine(String s) {
		if (s.startsWith("\r\n"))
			s = s.substring(2);
		if (s.endsWith("\r\n"))
			s = s.substring(0, s.length() - 2 );
		return s;
	}
	
	public static Vector toVector(String cmdLine, String cmdResult, String separator) {
		StringTokenizer st = new StringTokenizer(cmdResult, separator);
		Vector vector = new Vector();
		String s1;
		while (st.hasMoreTokens()) {
			s1 = st.nextToken();
			if (s1.charAt(s1.length() - 1) == '\r')
				s1 = s1.substring(0, s1.length() - 1);
				System.out.println("s1："+s1);
			if (trimWhiteSpace(s1).indexOf(trimWhiteSpace(cmdLine)) > -1) {
				System.out.println("continue");
				continue;
			}
			vector.add(s1);
		}
		return vector;
	}
	
	private static String trimWhiteSpace(String str){
		return str.replaceAll("\\s*","");   // 替换字符串中的空白字窿
	}
	
	public static String parseNumber(List<Number> numbers, String separator) {
		StringBuilder sb = new StringBuilder();
		for(Number number : numbers) {
			String numberStr = number.toString();
			sb.append(numberStr).append(separator);
		}
		
		sb.deleteCharAt(sb.lastIndexOf(separator));
		
		return sb.toString();
	}
	
	public static String parseNumber(Number[] numbers, String separator) {
		List<Number> numberList = new ArrayList<Number>();
		Collections.addAll(numberList, numbers);
		return parseNumber(numberList, separator);
	}
	
	public static void main(String args[]) {
		Integer[] ids = new Integer[] {1,2,3};
		System.out.println(StringUtils.parseNumber(ids, ","));
	}

}