package com.luqili.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestT1 {
	public static File file1 = new File("/mnt/data1/temp/t.jpg");
	public static File file2=new File("/mnt/data1/temp/t_2.jpg");
	public static void t1() throws IOException{
		BufferedImage image=ImageIO.read(file1);
		System.out.println(image.getWidth());
		System.out.println(image.getHeight());
	}
	public static void t2() throws IOException{
		BufferedImage image=ImageIO.read(file2);
		System.out.println(image.getWidth());
		System.out.println(image.getHeight());
	}
	public static void t3() throws IOException{
	}
	public static void main(String[] args) throws Exception {
		t1();
		t2();
	}
}
