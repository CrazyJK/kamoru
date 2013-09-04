package kamoru.test.serialization;

import java.io.*;
import java.util.*;

public class Swrite {

	public static void main(String[] args) {
		try {
			FileOutputStream f = new FileOutputStream("tmp");
			ObjectOutput s = new ObjectOutputStream(f);
			s.writeObject("Today");
			s.writeObject(new Date());
			s.flush();
		} catch (Exception e) {
			System.out.println("Today");
			System.out.println(new Date());
		}

	}

}
