package com.android.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.android.log.CLog;

/**
 * <pre>
 * һ����Ҫ�õ��ĳ���
 
��jQuery��ʹ��$.post()�Ϳ��Է���ķ���һ��post������android��������ʱҲҪ�ӷ�������ȡһЩ���ݣ���Ҳ�����ʹ��post�����ˡ�
 
������Ҫ�õ�����Ҫ��
 
��android��ʹ��post������ҪҪ�õ�������HttpPost��HttpResponse��EntityUtils 
 
������Ҫ˼·
 
1������HttpPostʵ����������Ҫ�����������url��
 
2��Ϊ������HttpPostʵ�����ò�������������ʱʹ�ü�ֵ�Եķ�ʽ�õ�NameValuePair�ࡣ
 
3������post�����ȡ����ʵ��HttpResponse
 
4��ʹ��EntityUtils�Է���ֵ��ʵ����д�������ȡ�÷��ص��ַ�����Ҳ����ȡ�÷��ص�byte���飩

����˵һ��get��post������

get����ʽ�ǽ��ύ�Ĳ���ƴ����url��ַ���棬����http://www.baidu.com/index.jsp?num=23&jjj=888;
 ����������ʽ�������ֱȽ���˽�Ĳ����ǲ��ʺϵģ����Ҳ����Ĵ�СҲ�������Ƶģ�һ����1K���Ұɣ������ϴ��ļ�
 �Ͳ��Ǻ��ʺϡ�

post����ʽ�ǽ�����������Ϣ���ڽ��䷢�͵������������ԶԴ�Сû�����ƣ�������˽������Ҳ�ȽϺ��ʡ�
 ����Post����
 POST /LoginCheck HTTP/1.1
 Accept: text/html, application/xhtml+xml, * /*
 Referer: http://192.168.2.1/login.asp
 Accept-Language: zh-CN
 User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)
 Content-Type: application/x-www-form-urlencoded
 Accept-Encoding: gzip, deflate
 Host: 192.168.2.1
 Content-Length: 39
 Connection: Keep-Alive
 Cache-Control: no-cache
 Cookie: language=en

Username=admin&checkEn=0&Password=admin   //����λ��
 

 * @author junxu.wang
 *
 */
public class PostUtils {
	private static final CLog cLog=new CLog(PostUtils.class.getSimpleName());
	
	public void httpGet(String baseUrl){
		String str = "����";
		String url = baseUrl + "?wd=" + str;
		
		//����һ���������
		HttpGet httpGet = new HttpGet(url);
		//����һ��http�ͻ��˶���
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpResponse httpResponse;
		HttpEntity httpEntity;
		InputStream inputStream=null;
		//��������
		try {
			
			httpResponse = httpClient.execute(httpGet);//������Ӧ
			httpEntity = httpResponse.getEntity();//ȡ����Ӧ
			//�ͻ����յ���Ӧ����Ϣ��
			inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String result = "";
			String line = "";
			while((line = reader.readLine()) != null){
				result = result + line;
			}
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{//���һ��Ҫ�ر�������
			try{
				inputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public  void httpPost(){
		String url = "http://192.168.2.112:8080/JustsyApp/Applet"; 
		// ��һ��������HttpPost���� 
		HttpPost httpPost = new HttpPost(url); 
		
		// ����HTTP POST�������������NameValuePair���� 
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("action", "downloadAndroidApp")); 
		params.add(new BasicNameValuePair("packageId", "89dcb664-50a7-4bf2-aeed-49c08af6a58a")); 
		params.add(new BasicNameValuePair("uuid", "test_ok1")); 
		
		HttpResponse httpResponse = null; 
		try { 
			// ����httpPost������� 
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
			httpResponse = new DefaultHttpClient().execute(httpPost); 
			//System.out.println(httpResponse.getStatusLine().getStatusCode()); 
			if (httpResponse.getStatusLine().getStatusCode() == 200) { 
				// ��������ʹ��getEntity������÷��ؽ�� 
				String result = EntityUtils.toString(httpResponse.getEntity()); 
				cLog.println("result:" + result); 
			} 
		} catch (ClientProtocolException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		cLog.println("end url..."); 
	} 
	
	
	
	
	private String address = "http://192.168.2.101:80/server/loginServlet";
	public boolean httpURLConnectionGet(String address,String username, String password) throws Exception {
		username = URLEncoder.encode(username);// ����������Ҫ����URL����
		password = URLEncoder.encode(password);
		String params = "username=" + username + "&password=" + password; 
		//������ƴ����URl��ַ����
		URL url = new URL(address + "?" + params);
		//ͨ��url��ַ������
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//���ó�ʱʱ��
		conn.setConnectTimeout(3000);
		//��������ʽ
		conn.setRequestMethod("GET");
		//������ص�״̬����200����һ��Ok�����ӳɹ���
		return conn.getResponseCode() == 200;
	}
	
	/**��android��ͨ��post��ʽ�ύ���ݡ�*/
	public boolean httpURLConnectionPost(String username, String password) throws Exception {
		username = URLEncoder.encode(username);// ����������Ҫ����URL����
		password = URLEncoder.encode(password);
		String params = "username=" + username + "&password=" + password; 
		byte[] data = params.getBytes();
		
		URL url = new URL(address);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3000);
		//��������ʽΪPOST
		conn.setRequestMethod("POST");
		//����post�����Ҫ������ͷ
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// ����ͷ, ��������
		conn.setRequestProperty("Content-Length", data.length + "");// ע�����ֽڳ���, �����ַ�����
		
		conn.setDoOutput(true);// ׼��д��
		conn.getOutputStream().write(data);// д������
		
		return conn.getResponseCode() == 200;
	}
	public void toHttpURLConnection() throws IOException{
		String path = "http://192.168.2.115:8080/android-web-server/httpConnectServlet.do?PackageID=89dcb664-50a7-4bf2-aeed-49c08af6a58a"; 
		URL url = new URL(path); 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
		conn.setRequestMethod("POST"); 
		conn.setConnectTimeout(5000); 
		System.out.println(conn.getResponseCode()); 
		
	}
	
	private void httpUrlConnectionPost(){
		String pathUrl = "http://172.20.0.206:8082/TestServelt/login.do";
	}
	private void httpUrlConnectionPost(String pathUrl){
		try{
			
			//��������
			URL url=new URL(pathUrl);
			HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
			
			////������������
			httpConn.setDoOutput(true);//ʹ�� URL ���ӽ������
			httpConn.setDoInput(true);//ʹ�� URL ���ӽ�������
			httpConn.setUseCaches(false);//���Ի���
			httpConn.setRequestMethod("POST");//����URL���󷽷�
			String requestString = "�ͷ���Ҫ��������ʽ���͵�����˵�����...";
			
			//������������
			//��������ֽ����ݣ������������ı��룬���������������˴����������ı���һ��
			byte[] requestStringBytes = requestString.getBytes("UTF-8");
			httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
			httpConn.setRequestProperty("Content-Type", "application/octet-stream");
			httpConn.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
			httpConn.setRequestProperty("Charset", "UTF-8");
			//
			String name=URLEncoder.encode("������","utf-8");
			httpConn.setRequestProperty("NAME", name);
			
			//�������������д������
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();
			//�����Ӧ״̬
			int responseCode = httpConn.getResponseCode();
			if(HttpURLConnection.HTTP_OK == responseCode){//���ӳɹ�
				
				//����ȷ��Ӧʱ��������
				StringBuffer sb = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				//������Ӧ�����������������Ӧ������ı���һ��
				responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				cLog.println(sb.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**��ʽ����HttpURLConnection��URL(import java.net.HttpURLConnection;import java.net.URL;import java.net.URLEncoder;)*/
	public static void httpUrlConnectionPost(String pathUrl,String fileName){
		File file=new File(fileName);
		cLog.println(fileName+"("+file.getName()+") send->"+pathUrl);
		FileInputStream fis=null;
		//�������������д������
		OutputStream outputStream=null;
		try{
			
			//��������
			URL url=new URL(pathUrl);
			HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
			
			////������������
			httpConn.setDoOutput(true);//ʹ�� URL ���ӽ������
			httpConn.setDoInput(true);//ʹ�� URL ���ӽ�������
			httpConn.setUseCaches(false);//���Ի���
			httpConn.setRequestMethod("POST");//����URL���󷽷�
			
			fis=new FileInputStream(file);
			//������������
			//��������ֽ����ݣ������������ı��룬���������������˴����������ı���һ��
			httpConn.setRequestProperty("Content-length", "" + file.length());
			httpConn.setRequestProperty("Content-Type", "audio/mp3");//http://tool.oschina.net/commons
			httpConn.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
			httpConn.setRequestProperty("Charset", "UTF-8");
			//
			String name=URLEncoder.encode(file.getName(),"UTF-8");
			httpConn.setRequestProperty("NAME", name);
			
			outputStream = httpConn.getOutputStream();
			byte []buffer=new byte[fis.available()];
			while (fis.read(buffer)>0) {
				outputStream.write(buffer);
			}
			fis.close();
			fis=null;
			outputStream.close();
			outputStream=null;
			//�����Ӧ״̬
			int responseCode = httpConn.getResponseCode();
			if(HttpURLConnection.HTTP_OK == responseCode){//���ӳɹ�
				
				//����ȷ��Ӧʱ��������
				StringBuffer sb = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				//������Ӧ�����������������Ӧ������ı���һ��
				responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				cLog.println(sb.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try {
				if(fis!=null){
					fis.close();
				}
				if(outputStream!=null){
					outputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * ��android����get��ʽ��������ύ����
 

��androidģ�����з��ʱ����е�tomcat������ʱ��ע�⣺����дlocalhost����Ϊģ������һ���������ֻ�ϵͳ������Ҫд���ǵ�IP��ַ��
 �����޷����ʵ���������
 

//Ҫ���ʵķ�������ַ������Ĵ�����Ҫ��������ύ�û��������룬�ύʱ������Ҫ����URLEncoder���룬��Ϊģ����Ĭ�ϵı����ʽ��utf-8
 //��tomcat�ڲ�Ĭ�ϵı����ʽ��ISO8859-1�������Ƚ��������б��룬����������ύ��
 
	 * 
	 */
	public static boolean httpURLConnectionGet(String pathUrl, String params) throws Exception {
		//������ƴ����URl��ַ����
		URL url = new URL(pathUrl + "?" + params);
		cLog.println("send->"+url.toString());
		//ͨ��url��ַ������
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//���ó�ʱʱ��
		conn.setConnectTimeout(3000);
		//��������ʽ
		conn.setRequestMethod("GET");
		//������ص�״̬����200����һ��Ok�����ӳɹ���
		return conn.getResponseCode() == 200;
	}
	
	public static String sendHttpPostResquest(String path, Map<String, String> params, String encoding) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();      //��װ���������
		if((params != null) && !params.isEmpty()) {
			for(Map.Entry<String, String> param : params.entrySet()) {
				list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encoding);    //���������������URL����
			HttpPost httpPost = new HttpPost(path);           //����һ��POST��ʽ��HttpRequest����
			httpPost.setEntity(entity);                       //����POST��ʽ��������
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);                      //ִ��POST����
			int reponseCode = httpResponse.getStatusLine().getStatusCode();            //��÷���������Ӧ��
			if(reponseCode == HttpStatus.SC_OK) {
				String resultData = EntityUtils.toString(httpResponse.getEntity());    //��÷���������Ӧ����
				return resultData;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * Function  :   ����Post���󵽷�����
	 * Param     :   params���������ݣ�encode�����ʽ
	 * Author    :   ����԰-���ɵ�Ȼ
	 */
	public static String submitPostData(URL url,Map<String, String> params, String encode) {
		
		byte[] data = getRequestData(params, encode).toString().getBytes();//���������
		try {        
			
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
			httpURLConnection.setConnectTimeout(3000);        //�������ӳ�ʱʱ��
			httpURLConnection.setDoInput(true);                  //�����������Ա�ӷ�������ȡ����
			httpURLConnection.setDoOutput(true);                 //����������Ա���������ύ����
			httpURLConnection.setRequestMethod("POST");    //������Post��ʽ�ύ����
			httpURLConnection.setUseCaches(false);               //ʹ��Post��ʽ����ʹ�û���
			//������������������ı�����
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//����������ĳ���
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
			//�����������������д������
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);
			
			int response = httpURLConnection.getResponseCode();            //��÷���������Ӧ��
			if(response == HttpURLConnection.HTTP_OK) {
				InputStream inptStream = httpURLConnection.getInputStream();
				return dealResponseResult(inptStream);                     //�������������Ӧ���
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * Function  :   ��װ��������Ϣ
	 * Param     :   params���������ݣ�encode�����ʽ
	 */
	public static StringBuffer getRequestData(Map<String, String> params, String encode) {
		StringBuffer stringBuffer = new StringBuffer();        //�洢��װ�õ���������Ϣ
		try {
			for(Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey())
				.append("=")
				.append(URLEncoder.encode(entry.getValue(), encode))
				.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //ɾ������һ��"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}
	
	/*
	 * Function  :   �������������Ӧ�������������ת�����ַ�����
	 * Param     :   inputStream����������Ӧ������
	 */
	public static String dealResponseResult(InputStream inputStream) {
		String resultData = null;      //�洢������
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultData = new String(byteArrayOutputStream.toByteArray());    
		return resultData;
	}
}
