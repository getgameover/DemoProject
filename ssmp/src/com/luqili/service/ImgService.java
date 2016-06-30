package com.luqili.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luqili.db.beans.base.Img;
import com.luqili.db.dao.ImgDAO;
import com.luqili.db.dao.base.ImgMapper;
import com.luqili.tool.ConstantFile;
import com.luqili.tool.FileUtils;
import com.luqili.tool.LuException;
import com.luqili.tool.enums.FileType;
import com.luqili.tool.enums.Status;
import com.luqili.tool.page.Page;

/**
 * 上传文件服务类
 * @author 46155
 *
 */
@Service
public class ImgService {
	@Autowired
	private ImgMapper imgMapper;
	@Autowired
	private ImgDAO imgDAO;
	
	public Img getImgById(Integer id){
		return imgMapper.selectByPrimaryKey(id);
	}
	/**
	 * 获取指定的图片URL
	 * @param imgId
	 * @return
	 */
	public String getImgUrl(Integer imgId){
		Img img=this.getImgById(imgId);
		return this.getImgUrl(img);
	}
	/**
	 * 获取有效的图片URL
	 * @param img
	 * @return
	 */
	public String getImgUrl(Img img){
		if(img==null||!Status.Enabled.equalsValue(img.getStatus())){
			return "";
		}else{
			return this.formatImgUrl(img.getUrl());
		}
	}
	@Transactional
	public Img saveImg(Integer imgType,String file,String name,String fileName,Integer userId){
		if(!FileType.validateFileTypeValue(imgType)){
			throw new LuException("文件类型有误");
		}
		String url="/upfile"+FileUtils.getFileNameRandomByTime(fileName);
		File fileImg = new File(ConstantFile.RootPath+url);
		FileUtils.writeAsDataURL(file, fileImg);
		Long size=fileImg.length();
		if(StringUtils.isBlank(name)){
			name=fileName;
		}
		Img img = new Img();
		img.setAddTime(new Timestamp(System.currentTimeMillis()));
		img.setAddUserId(userId);
		img.setExt(FileUtils.getFileExt(fileName));
		img.setName(name);
		img.setFilename(fileImg.getName());
		img.setImgType(imgType);
		img.setPath(fileImg.getPath());
		img.setSize(size);
		img.setStatus(Status.Enabled.getStatusValue());
		img.setUrl(url);
		imgMapper.insertSelective(img);
		return img;
	}
	/**
	 * 修改一个文件信息
	 * @param id
	 * @param name
	 * @param status
	 * @param userId
	 * @return
	 */
	public Img updateImg(Integer id,String name,Integer status,Integer userId){
		if(!Status.validateStatusValue(status)){
			throw new LuException("状态类型有误");
		}
		Img img=this.getImgById(id);
		if(img==null){
			throw new LuException("未查询到相关文件信息");
		}
		if(!img.getAddUserId().equals(userId)){
			throw new LuException("无权修改该文件信息");
		}
		img.setName(name);
		img.setStatus(status);
		imgMapper.updateByPrimaryKeySelective(img);
		return img;
	}
	public void deleImg(Integer id,Integer userId){
		Img img=this.getImgById(id);
		if(img==null){
			throw new LuException("未查询到相关文件信息,或已删除!");
		}
		if(!img.getAddUserId().equals(userId)){
			throw new LuException("无权删除该文件信息");
		}
		File file = new File(img.getPath());
		if(file.isFile()){
			if(file.canWrite()){
				file.delete();
			}
		}else{
			file=new File(ConstantFile.RootPath+img.getUrl());
			if(file.isFile()&&file.canWrite()){
				file.delete();
			}
		}
		imgMapper.deleteByPrimaryKey(img.getId());
	}
	/**
	 * 查询全部用户信息
	 * @param page
	 */
	public void selectImgListPage(Page page,Integer userid){
		imgDAO.selectImgListPage(page, userid);
	}
	public void formatImgUrlByMap(Map<String, Object> imgMsg){
		String url=(String)imgMsg.get("url");
		imgMsg.put("url", formatImgUrl(url));
	}
	public String formatImgUrl(String url){
		if(StringUtils.isNotBlank(url)){
			return ConstantFile.ImgUrlHost+url;
		}else{
			return url;
		}
	}
	
	
}
