package kamoru.test.serialization.customClass;

public class Test implements java.io.Serializable

{

	public String str;

	public transient int ivalue;

	public Test(String s, int i)

	{

		str = s;

		ivalue = i;

	}

}