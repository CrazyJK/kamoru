package jk.kamoru.test.serialization;

import java.io.*;
import java.util.*;

public class Sread {
	public static void main(String args[])

	{

		try {

			FileInputStream in = new FileInputStream("tmp");

			ObjectInput s = new ObjectInputStream(in);

			String today = (String) s.readObject();

			Date date = (Date) s.readObject();

			System.out.println(today);

			System.out.println(date);

		}

		catch (IOException e) {
		}

		catch (ClassNotFoundException e) {
		}

	}
}
