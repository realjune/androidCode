package com.apk.infos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.xml.sax.SAXException;

import test.AXMLPrinter2;

public class GetInfo {

	/** ȫ��APK������Ϣ */
	private static ArrayList<ArrayList<String>> listAPKInfo = null;
	/** ÿ��APK������Ϣ */
	private static ArrayList<String> ApkInfo = null;
	/** ��ѹ�� */
	private static ZipFile zFile;
//	/** ��ʽ��֮ǰ��ַ */
//	private final static String strBeforeFilePath = "d:/AndroidManifest.xml";
//	/** ��ʽ��֮���ַ */
//	private final static String strAfterFilePath = "d:/cmdAfter.xml";
//	/** ���������� */
//	private static String strcmd = "cmd /c resolveManifest.bat";
	/** ͳ�ƻ�ȡʧ�ܵ����� */
	private static int errCount;
	/** APK���� */
	public static String strpackage = "";
	/** APK�汾 */
	public static String strversion = "";
	/** APK�汾�� */
	public static String strVersionCode = "";
	
	public static InputStream stream;
	
	static ArrayList<String> list = new ArrayList<String>();

	public GetInfo() {

	}

	public static ArrayList<ArrayList<String>> GetApkInfoAll(String strpath) throws IOException {
		errCount = 0;
		list = getFiles(strpath);
		listAPKInfo = new ArrayList<ArrayList<String>>();
		if (list != null) {
			int listCount = list.size();
			for (int i = 0; i < listCount; i++) {
				resolve(getApkInfo(list.get(i)), list.get(i));
				stream.close();
			}
			list.clear();
			list = null;
//			File file01 = new File(strBeforeFilePath);
//			if (file01.exists()) {
//				file01.delete();
//			}
//			File file02 = new File(strAfterFilePath);
//			if (file02.exists()) {
//				file02.delete();
//			}
			IndexShowUI.strResult = "����APK�ļ���" + listCount + "\n��ȡ�ɹ���"
					+ listAPKInfo.size() + "��\n��ȡʧ�ܣ�" + errCount + "��";
		} else {
			IndexShowUI.strResult = "Ŀ���ļ�����û��APK�ļ�";
		}
		return listAPKInfo;
	}

	/**
	 * �õ�ѹ������Ϣ
	 * 
	 * @param apkpath
	 */
	private static InputStream getApkInfo(String apkpath) {
		try {
			zFile = new ZipFile(apkpath);
			ZipEntry entry = zFile.getEntry("AndroidManifest.xml");
			entry.getComment();
			entry.getCompressedSize();
			entry.getCrc();
			entry.isDirectory();
			entry.getSize();
			entry.getMethod();
			stream = zFile.getInputStream(entry);
			return stream;
		} catch (IOException e) {
			errCount++;
			System.out.println("IOEXCEPTIONss");
		}
		return null;
	}

	/**
	 * ����XML�ļ�����ȡAPK�������汾
	 */
	private static void resolve(InputStream stream, String apkpath) {
		try {
			ApkInfo = new ArrayList<String>();
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setValidating(false);
			parserFactory.setNamespaceAware(false);
			MyXmlResolve MySaxParserInstance = new MyXmlResolve();
			
			String strContent = AXMLPrinter2.parse(stream);
			byte[] by = strContent.getBytes("utf-8");
			ByteArrayInputStream bais = new ByteArrayInputStream(by);
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(bais, MySaxParserInstance);
			bais.close();
			parser = null;
			ApkInfo.add(apkpath.substring(apkpath.lastIndexOf("\\") + 1));
			ApkInfo.add(strpackage);
			ApkInfo.add(strversion);
			ApkInfo.add(strVersionCode);
			listAPKInfo.add(ApkInfo);
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
			errCount++;
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			errCount++;
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException");
			errCount++;
		} catch (SAXException e) {
			System.out.println("SAXException");
			errCount++;
		} catch (IOException e) {
			System.out.println("IOEXCEPTION");
			errCount++;
		}finally{
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡ�ļ����µ��ļ���
	 */
	private static ArrayList<String> getFiles(String path) {
		ArrayList<String> listAPKFiles = null;
		File files = null;
		try {
			files = new File(path);
			String[] strFiles = files.list(new FilterAPKGetWithCMD());
			if (strFiles.length > 0) {
				listAPKFiles = new ArrayList<String>();
				for (int i = 0; i < strFiles.length; i++) {
					String strFile = strFiles[i];
					if (strFile.length() > 0) {
						listAPKFiles.add(path + strFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		files = null;
		return listAPKFiles;
	}
}

/**
 * �����ļ���׺
 * 
 * @author Administrator
 * 
 */
class FilterAPKGetWithCMD implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		boolean isapk = false;
		if (name.indexOf(".apk") != -1) {
			isapk = true;
		}
		return isapk;
	}
}
