package jk.kamoru.test;

import java.text.MessageFormat;

public class MessageFormatTEST {
	StringBuffer s = new StringBuffer();

	public void q(){
		w(s);
		System.out.println(s);
	}
	
	public void w(StringBuffer s1){
		s1.append("sss");
		System.out.println(s1);
	}
	
	public static void main(String[] args){
        String DOCINFO = "<hsReceiverDeptID>{0}</hsReceiverDeptID>\n<nApprovalStatus>{1}</nApprovalStatus>\n<cWordType>{2}</cWordType>\n<bNotifyCheck>{3}</bNotifyCheck>\n<hsSenderID>{4}</hsSenderID>\n<hsReceiverUserID>{5}</hsReceiverUserID>\n{6}";
        String bodyFirst = MessageFormat.format(DOCINFO, new Object[] {
                "1", 
                "2", 
                "3", 
                "4", 
                "5", 
                "6", 
                "7"
            });
        System.out.println(bodyFirst);
        System.out.println(
		MessageFormat.format("<szIP>{0}</szIP>\n", new Object[] {
                "1111"
            })
        );
        MessageFormatTEST test = new MessageFormatTEST();
        test.q();
	}
}
