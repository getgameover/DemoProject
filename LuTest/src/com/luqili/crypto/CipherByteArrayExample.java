package com.luqili.crypto;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.Crypto;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;

public class CipherByteArrayExample {
	 public static void main(String[] args) throws Exception {
		 	//	/home/luqili/lib
		 
		 	//System.load("/home/luqili/lib/libcommons-crypto.so");
		 	
		 	System.out.println(Crypto.isNativeCodeLoaded());
			System.out.println(Crypto.getComponentName());
			System.out.println(Crypto.getComponentVersion());
		 	
		 	
	        final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"),"AES");
	        final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));

	        Properties properties = new Properties();
	        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.OPENSSL.getClassName());
	        //Creates a CryptoCipher instance with the transformation and properties.
	        final String transform = "AES/CBC/PKCS5Padding";
	        CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
	        System.out.println("Cipher:  " + encipher.getClass().getCanonicalName());

	        final String sampleInput = "hello world!";
	        System.out.println("input:  " + sampleInput);

	        byte[] input = getUTF8Bytes(sampleInput);
	        byte[] output = new byte[32];

	        //Initializes the cipher with ENCRYPT_MODE, key and iv.
	        encipher.init(Cipher.ENCRYPT_MODE, key, iv);
	        //Continues a multiple-part encryption/decryption operation for byte array.
	        int updateBytes = encipher.update(input, 0, input.length, output, 0);
	        System.out.println(updateBytes);
	        //We must call doFinal at the end of encryption/decryption.
	        int finalBytes = encipher.doFinal(input, 0, 0, output, updateBytes);
	        System.out.println(finalBytes);
	        //Closes the cipher.
	        encipher.close();

	        System.out.println(Arrays.toString(Arrays.copyOf(output, updateBytes+finalBytes)));

	        // Now reverse the process using a different implementation with the same settings
	        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.JCE.getClassName());
	        CryptoCipher decipher = Utils.getCipherInstance(transform, properties);
	        System.out.println("Cipher:  " + encipher.getClass().getCanonicalName());

	        decipher.init(Cipher.DECRYPT_MODE, key, iv);
	        byte [] decoded = new byte[32];
	        decipher.doFinal(output, 0, updateBytes + finalBytes, decoded, 0);

	        System.out.println("output: " + new String(decoded, StandardCharsets.UTF_8));
	    }

	    /**
	     * Converts String to UTF8 bytes
	     *
	     * @param input the input string
	     * @return UTF8 bytes
	     */
	    private static byte[] getUTF8Bytes(String input) {
	        return input.getBytes(StandardCharsets.UTF_8);
	    }
}
