package WindyhooDemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CommandTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ProcessBuilder builder = new ProcessBuilder();
        String[] commandArray = new String[]{"sh","-c","/home/windyhoo/script/test.sh"};
		builder.command(commandArray);
		try {
			Process process = builder.start();
			
			StringBuffer output = new StringBuffer();
	        StringBuffer errOutput = new StringBuffer();
	        String[] resultSet = new String[2];
	        InputStream errorStream = null;
	        InputStream inputStream = null;
	        BufferedReader shellOutput = null;
	        BufferedReader shellErrOutput = null;

			inputStream = process.getInputStream();
			shellOutput = new BufferedReader(new InputStreamReader(inputStream));
			errorStream = process.getErrorStream();
			shellErrOutput = new BufferedReader(new InputStreamReader(errorStream));
			String line;

			while ((line = shellOutput.readLine()) != null) {
				output.append(line).append('\n');
			}
			if(output.length() > 0){
				output.delete(output.length()-1, output.length());
			}
			resultSet[0] = output.toString();
			while ((line = shellErrOutput.readLine()) != null) {
				errOutput.append(line).append('\n');
			}
			resultSet[1] = errOutput.toString();

			int result = process.waitFor();
			System.out.println("result:"+result);
			System.out.println("info:"+resultSet[0]);
			System.out.println("error:"+resultSet[1]);
			output.delete(0, output.length());
			errOutput.delete(0, errOutput.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
