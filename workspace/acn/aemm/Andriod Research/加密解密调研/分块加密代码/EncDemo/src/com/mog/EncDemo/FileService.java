package com.mog.EncDemo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.util.Log;
/*
 * read and write file . 
 */
public class FileService 
{
    public static final String TAG = "FileService";
    
    public static void save(String fileName, String content) throws Exception 
    {
        // ����ҳ������Ķ����ı���Ϣ�����Ե��ļ���������.txt��׺����βʱ���Զ�����.txt��׺
        if (!fileName.endsWith(".mec") && !fileName.endsWith(".txt"))
        {
                return ; 
        }
        String strTem = EncUtil.getSDPath();
        if(EncUtil.isEmpty(strTem))
        {
        	Log.e(TAG, "no SDCard Found");
        	return ; 
        }
        //fileName = strTem + "/" + fileName ; 
        byte[] buf = fileName.getBytes("iso8859-1");
        Log.e(TAG, new String(buf,"utf-8"));
        fileName = new String(buf,"utf-8");
        Log.e(TAG, fileName);
        FileOutputStream fos = new FileOutputStream(fileName) ; 
        fos.write(content.getBytes());
        fos.close();
    }

    public static String read(String fileName) throws Exception 
    {
        // ����ҳ������Ķ����ı���Ϣ�����Ե��ļ���������.txt��׺����βʱ���Զ�����.txt��׺
       /* if (!fileName.endsWith(".txt")) 
        {
            fileName = fileName + ".txt";
        }
        String strTem = EncUtil.getSDPath();
        if(EncUtil.isEmpty(strTem))
        {
        	Log.e(TAG, "no SDCard Found");
        	return null; 
        }*/
        FileInputStream fis = new FileInputStream(fileName); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        //����ȡ������ݷ������ڴ���---ByteArrayOutputStream
        while ((len = fis.read(buf)) != -1) 
        {
            baos.write(buf, 0, len);
        }
        fis.close();
        baos.close();
        //�����ڴ��д洢������
        return baos.toString();
    }
}