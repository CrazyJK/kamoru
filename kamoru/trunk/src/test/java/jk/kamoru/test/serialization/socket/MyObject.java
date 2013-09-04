package jk.kamoru.test.serialization.socket;

import java.io.*;

public class MyObject implements Serializable {

	String name;
	int count;

	MyObject() {
		setName();
	}

	public void setName() {
		count++;
		name = "MyObject" + count;
	}

	public String toString() {
		return name;
	}
}
