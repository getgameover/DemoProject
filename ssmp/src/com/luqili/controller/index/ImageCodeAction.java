package com.luqili.controller.index;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luqili.service.ImgCodeService;

/**
 * 验证码请求类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/pb")
public class ImageCodeAction {
	@Autowired
	private ImgCodeService imgCodeService;
	public static String[] dictionary=new String[]{"1","2","3","4","5","6","7","8","9"
			,"A","B","C","D","E","F","H","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
			"a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};
	@RequestMapping("/img-code")
	public void getCodeimage(HttpServletResponse response, HttpSession session) {
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		
		for (int i = 0; i < 155; i++) {
			g.setColor(getRandColor(140, 250));
			int x = getRanNumber(width);
			int y = getRanNumber(height);
			int xl = getRanNumber(100);
			int yl = getRanNumber(100);
			g.drawLine(x, y, x + xl, y + yl);
		}
		StringBuffer sRand = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = getRandString();
			sRand.append(rand);
			g.setFont(new Font("Times New Roman", getRanNumber(4), 19));
			g.setColor(new Color(20 + getRanNumber(110), 20 + getRanNumber(110), 20 + getRanNumber(110)));
			g.drawString(rand, 13 * i + getRanNumber(6), 10+getRanNumber(6));
		}
		imgCodeService.saveCode(sRand.toString(), session);
		// 将认证码存入SESSION
		g.dispose();
		response.setContentType("image/jpeg");
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得一个随机字符
	 * @author lu
	 * 2015-7-5 上午09:10:37
	 * @return
	 */
	private String getRandString(){
		return dictionary[getRanNumber(dictionary.length-1)];
	}
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + getRanNumber(bc - fc);
		int g = fc + getRanNumber(bc - fc);
		int b = fc + getRanNumber(bc - fc);
		return new Color(r, g, b);
	}
	private int getRanNumber(int max){
		Random random = new Random();
		return random.nextInt(max+1);
	}
}
