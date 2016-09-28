package test.other;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class TestCrypto {
	public static void main(String[]args)throws Exception{
		TextEncryptor ttt= Encryptors.noOpText();
		
		TextEncryptor te1= Encryptors.delux("passworda", "1235");
		String t=te1.encrypt("这里是一个加密的算法吗");
		System.out.println(t);
		TextEncryptor te2= Encryptors.delux("password", "1235");
		System.out.println(te2.decrypt(t));
		
		SCryptPasswordEncoder scpe = new SCryptPasswordEncoder();
		String pwd=scpe.encode("passwpasswoaardord");
		boolean ifcheck=scpe.matches("passwpasswoaardord", pwd);
		System.out.println(pwd);
		System.out.println(ifcheck);
		//org.bouncycastle.crypto.generators.SCrypt
	}
}
