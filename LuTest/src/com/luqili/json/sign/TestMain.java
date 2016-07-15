package com.luqili.json.sign;

public class TestMain {
	
	public static void main(String[] args) {
		String pwd="mima";
		RequestBean rb = new RequestBean();
		rb.setAccount("zhangsan");
		rb.setService("houseserver");
		rb.setTime(System.currentTimeMillis());
		rb.setSign(null);
		Param param = new Param();
		param.setId("ID7788");
		param.setBiz("BZ002231");
		param.setNo("BN123356");
		rb.setParam(param);
		String json=SignUtil.objToJson(rb);
		String sign=SignUtil.signRequest(rb,pwd);
		System.out.println("REQUEST:"+sign);
		System.out.println("REQUEST:"+json);
		
		ResponseBean res = new ResponseBean();
		res.setIsSuccess(true);
		res.setService("houseserver");
		res.setTime(System.currentTimeMillis());
		res.setMessage("Success");
		res.setSign(null);
		Result result = new Result();
		result.setLocal("泰安市环山路xxx号");
		result.setSize("125平");
		res.setResult(result);
		System.out.println("RESPONSE:"+SignUtil.objToJson(res));
		System.out.println("RESPONSE:"+SignUtil.signResponse(res, pwd));

	}

}
