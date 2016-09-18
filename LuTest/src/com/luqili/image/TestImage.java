package com.luqili.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.stream.ImageInputStream;

import com.twelvemonkeys.imageio.plugins.jpeg.JPEGImageReader;


public class TestImage {
	public static File file1 = new File("/mnt/data1/temp/t.jpg");
	public static File file2=new File("/mnt/data1/temp/t_2.jpg");
	public static void t1() throws IOException{
		ImageInputStream stream = ImageIO.createImageInputStream(file1);
	      Iterator<ImageReader> iter=ImageIO.getImageReaders(stream);
	      ImageReader reader = (ImageReader)iter.next();
	      System.out.println(reader.getClass());
          ImageReadParam param = reader.getDefaultReadParam();
          
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
	public static byte[] readFileContent() throws Exception{
		//FileOutputStream fo = new FileOutputStream("/mnt/data1/temp/t.jpg");
		File file = new File("/mnt/data1/temp/t.jpg");
		FileInputStream fi = new FileInputStream(file);
		int size=(int)file.length();
		byte[] fc = new byte[size];
		fi.read(fc);
		fi.close();
		return fc;
	}

}
