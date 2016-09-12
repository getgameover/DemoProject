package com.luqili.http.ssl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.client.ClientProtocolException;

public class Test {
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post方式请求服务器(https协议)
	 *
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 */
	public static byte[] post(String url, String content, String charset)
			throws NoSuchAlgorithmException, KeyManagementException, IOException, NoSuchProviderException {
		try {
			// 证书地址
			String certification = "/mnt/data2/clientkey/370900.pfx";
			// 证书密码
			String certificationPassword = "370900";
			// PengeSoftOARoot.cer生成后的[PATH:\\file]
			String pengeSoftOARootKeystore = "/mnt/data2/clientkey/PengeSoftOARoot.cer";
			// 生成pengeSoftOARootKeystore时的–storepass 后的密码参数
			String pengeSoftOARootKeystorePassword = "";
			KeyStore keystore = GetKeyStore(certification, certificationPassword, "PKCS12");
			KeyStore keystore2 = GetKeyStore(pengeSoftOARootKeystore, pengeSoftOARootKeystorePassword, "JKS");
			keystore.load(new FileInputStream(certification), certificationPassword.toCharArray());
			KeyManagerFactory keymanagerfactory = KeyManagerFactory.getInstance("SunX509");
			keymanagerfactory.init(keystore, certificationPassword.toCharArray());
			KeyManager akeymanager[] = keymanagerfactory.getKeyManagers();
			TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance("SunX509");
			trustmanagerfactory.init(keystore2);
			TrustManager atrustmanager[] = trustmanagerfactory.getTrustManagers();
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(akeymanager, atrustmanager, null);
			SSLSocketFactory sslSocketFactory = sslcontext.getSocketFactory();
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sslcontext.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setSSLSocketFactory(sslSocketFactory);
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(content.getBytes(charset));
			// 刷新、关闭
			out.flush();
			InputStream is = conn.getInputStream();
			if (is != null) {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				return outStream.toByteArray();
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取KeyStore
	 *
	 * @param keyStorePath
	 *            密匙库路径
	 * @param password
	 *            密匙库密码
	 * @param InstanceType
	 *            解析编码
	 * @return KeyStore
	 */
	private static KeyStore GetKeyStore(String keyStorePath, String password, String InstanceType) throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance(InstanceType);
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		try {
			byte[] result = post("https://123.57.205.217:8082/Service/UpReportInfoServiceSvr.assx/UpReportInfo",
					"Token=&CityCodes=510100&CountyCodes=&DealDate=2016-06-16T00:00:00&Reportor=成都市&NewBusinessArea=1000.88&NewBusinessAmount=9898&NewHouseArea=5000&NewHouseAmount=8000&NewHouseCount=20&OldReportor=李四&OldBusinessArea=1234.56&OldBusinessAmount=80000&OldHouseArea=12580&OldHouseAmount=99999&OldHouseCount=100",
					"UTF-8");
			System.out.println(new String(result, "utf-8"));
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}

}