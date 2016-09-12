/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.luqili.http.ssl;

import java.io.File;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

    public final static void main(String[] args) throws Exception {
    	String sslKeyStorePath = "/mnt/data2/clientkey/370900.pfx";
		String sslKeyStorePassword = "370900";
		String sslKeyStoreType = "PKCS12"; // 密钥库类型，有JKS PKCS12等
		String sslTrustStore = "/mnt/data2/clientkey/PengeSoftOARoot.cer";
		String sslTrustStorePassword = "";
		String url="https://123.57.205.217:8082/Service/UpReportInfoServiceSvr.assx/UpReportInfo";
		String content="Token=&CityCodes=370900&CountyCodes=&DealDate=2016-06-16T00:00:00&Reportor=成都市&NewBusinessArea=1000.88&NewBusinessAmount=9898&NewHouseArea=5000&NewHouseAmount=8000&NewHouseCount=20&OldReportor=李四&OldBusinessArea=1234.56&OldBusinessAmount=80000&OldHouseArea=12580&OldHouseAmount=99999&OldHouseCount=100";
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(new File(sslKeyStorePath), sslKeyStorePassword.toCharArray(), new TrustSelfSignedStrategy())
                .loadKeyMaterial(new File(sslKeyStorePath), sslKeyStorePassword.toCharArray(), sslKeyStorePassword.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext, new String[] {"TLSv1" }, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpPost httppost = new HttpPost(url);
            
            System.out.println("Executing request " + httppost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {  
                	String result=EntityUtils.toString(entity, "UTF-8").trim();
                	if(!"True".toUpperCase().equals(result.toUpperCase())){
                		   System.out.println(result);
                	}
                }  
             
                System.out.println(response.getStatusLine());
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}