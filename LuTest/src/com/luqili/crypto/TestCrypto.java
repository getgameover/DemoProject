package com.luqili.crypto;

import org.apache.commons.crypto.Crypto;
import org.apache.commons.crypto.jna.OpenSslJna;
import org.apache.commons.crypto.utils.Utils;

public class TestCrypto {
	public static void main(String[]args){
		//org.apache.commons.crypto.cipher.OpenSslCipher
		System.out.println(Crypto.isNativeCodeLoaded());
		System.out.println(Crypto.getComponentName());
		System.out.println(Crypto.getComponentVersion());
		
	}
}
