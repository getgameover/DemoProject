package com.luqili.power;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.luqili.tool.ConstantFile;

public class ProInitListener implements ServletContextListener{
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.debug("工程结束");
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if(StringUtils.isBlank(ConstantFile.RootPath)){
			ConstantFile.RootPath=sce.getServletContext().getRealPath("/");
		}
		if(StringUtils.isBlank(ConstantFile.ImgUrlHost)){
			ConstantFile.ImgUrlHost=sce.getServletContext().getContextPath();
		}
	}
}
