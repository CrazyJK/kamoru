
public class SpilitTest {

	public void splittest(){
	}
	public static void main(String args[]){
		String str = "/dev/dsk/c4t102d0s0  70501706 1109296 68687393     2%    /array5";
		String[] strArr = str.split(" ");
		java.util.ArrayList splitList = new java.util.ArrayList(); 
		for(int i=0 ; i<strArr.length ; i++) {
			if(!strArr[i].equals("")){
				splitList.add(strArr[i]);
			}
		}
		
		for(int i=0 ; i<splitList.size() ; i++){
			System.out.println(i + "_" + splitList.get(i));
		}
	}
}
