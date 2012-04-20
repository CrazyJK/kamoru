package kamoru.util.crypto;

import java.io.*;
import java.security.*;
import javax.net.ssl.*;

import java.util.regex.*;

public class HelloClientSSL {
	public static void main(String[] args) {

		// Pick all AES algorithms of 256 bits key size
		String patternString = "AES.*256";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher;
		boolean matchFound;

		try {

			SSLSocketFactory sslFact = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket s = (SSLSocket) sslFact.createSocket(args.length == 0 ? "127.0.0.1" : args[0], 8181);

			String str[] = s.getSupportedCipherSuites();

			int len = str.length;
			String set[] = new String[len];

			int j = 0, k = len - 1;
			for (int i = 0; i < len; i++) {
				System.out.println(str[i]);

				// Determine if pattern exists in input
				matcher = pattern.matcher(str[i]);
				matchFound = matcher.find();

				if (matchFound)
					set[j++] = str[i];
				else
					set[k--] = str[i];
			}

			s.setEnabledCipherSuites(set);

			str = s.getEnabledCipherSuites();

			System.out.println("Available Suites after Set:");
			for (int i = 0; i < str.length; i++)
				System.out.println(str[i]);

			OutputStream out = s.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			String mesg = in.readLine();
			System.out.println("Socket message: " + mesg);
			in.close();
		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}