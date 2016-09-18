package com.luqili.lutest.commonlang;

import java.math.BigInteger;
import java.util.Map;

public class TestCode {
	private static final int keySize=50;
	private static final int keyPreSize=2;
	public static void main(String[]args){
		String c=encode(Consanst.number, Consanst.key, Consanst.name);
		System.out.println(c);
		System.out.println(Consanst.key.length());
		System.out.println(c.length());
	}
	public static Map<String, String> decode(String code,String panme){
		return null;
	}
	public static String encode(String number,String key,String pname){
		String pre_key=key.substring(0,keyPreSize);
		String body_key=key.substring(keyPreSize);
		BigInteger bnumber=new BigInteger(number);
		String s_number=bnumber.toString(16);
		char[] operNumber=s_number.toCharArray();
		char[] operKey=body_key.toCharArray();
		int random=getStringSize(pname)%(keySize-keyPreSize);
		StringBuffer sum = new StringBuffer(Integer.toHexString(s_number.length()));
		for(int i=0;i<random;i++){
			if(i<operNumber.length){
				sum.append(operNumber[i]);
			}
			if(i<operKey.length){
				sum.append(operKey[i]);
			}
		}
		if(random<operNumber.length){
			sum.append(s_number.substring(random));
		}
		if(random<operKey.length){
			sum.append(body_key.substring(random));
		}
		BigInteger b_sum=new BigInteger(sum.toString(),16);
		return pre_key+b_sum.toString(36);
	}
	public static int getStringSize(String pname){
		int size=0;
		for(byte b:pname.getBytes()){
			size+=b;
		}
		
		return Math.abs(size);
	}
}
