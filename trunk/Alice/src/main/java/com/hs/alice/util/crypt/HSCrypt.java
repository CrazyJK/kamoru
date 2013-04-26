package com.hs.alice.util.crypt;

import com.hs.hip.common.CodeCrypt;

public class HSCrypt implements AliceCrypt {

	private CodeCrypt codeCrypt;
	
	public HSCrypt() {
		try {
			this.codeCrypt = ((CodeCrypt)Class.forName("com.hs.hip.common.HDIdeaCipher").newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String decrypt(String encryptString) {
		try {
			return codeCrypt.Decode(encryptString);
		} catch (Exception e) {
			return encryptString;
		}
	}

	@Override
	public String encrypt(String string) {
		try {
			return codeCrypt.Encode(string);
		} catch (Exception e) {
			return string;
		}
	}

}
