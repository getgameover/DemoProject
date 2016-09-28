package com.luqili.crypto;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.crypto.Crypto;
import org.apache.commons.crypto.jna.OpenSslJna;
import org.apache.commons.crypto.random.CryptoRandom;
import org.apache.commons.crypto.random.CryptoRandomFactory;
import org.apache.commons.crypto.utils.Utils;

public class TestCrypto {
	 public static void main(String []args) throws Exception {
	        // Constructs a byte array to store random data.
	        byte[] key = new byte[16];
	        byte[] iv = new byte[32];

	        Properties properties = new Properties();
	        properties.put(CryptoRandomFactory.CLASSES_KEY,
	            CryptoRandomFactory.RandomProvider.OPENSSL.getClassName());

	        // Gets the 'CryptoRandom' instance.
	        try (CryptoRandom random = CryptoRandomFactory.getCryptoRandom(properties)) {

	            // Show the actual class (may be different from the one requested)
	            System.out.println(random.getClass().getCanonicalName());

	            // Generate random bytes and places them into the byte arrays.
	            random.nextBytes(key);
	            random.nextBytes(iv);

	        }

	        // Show the generated output
	        System.out.println(Arrays.toString(key));
	        System.out.println(Arrays.toString(iv));
	    }
}
