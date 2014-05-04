// Copyright (c) 2005 Sony Ericsson Mobile Communications AB
//
// This software is provided "AS IS," without a warranty of any kind. 
// ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, 
// INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A 
// PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. 
//
// THIS SOFTWARE IS COMPLEMENTARY OF JAYWAY AB (www.jayway.se)

/**
//VideoIM�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�CommandResources
//��˵����
//	�ṩ��Ӧ������Ҫ�ĸ�����Դ��Mapper�Ĺ��ܣ�����BlueGammon�����������
* 
//������ϵͳ��VideoIM
//
//ϵͳ������:
	    �����ṩ��VideoIM�ֻ��Զ������ϴ���J2ME�汾[��Դ]��
	    һ���������ص��ֻ�(����Nokia7610�Ѿ�ȷʵ�������ذ�װ����������)��Ӧ�ó���
	    �����Զ������ֻ�����ͷ��ʱ����,����̨��JPEGͼ��(��С����Լ��KB)�ϴ����������ϣ�
	    �����Ϳ��԰�������ϵͳ����������PC���ϵ�MSN Messenger���Ժ�����ƶ�MSN Messenger
	    ͨ�����ַ�ʽ��Ƶ���죬�Է�����ÿ��ʮ�����ӿ�������ֻ��������Ļ����ˡ�

	 ��ϵͳ������
		VideoIM�Ĺ����б�
			1:��ҪMobileWebCam
				����MobileWebCam
				ֹͣMobileWebCam
			2:MobileWebCam����
			3:������
			4:�˳�


//(2)��ʷ��¼:
//�޸���: ֣��(2005.10.12)
//��ϵ��: Google Talk >> zhengyun@gmail.com
//Blogs:    http://blog.csdn.net/zhengyun_ustc/�Լ�http://www.cnblogs.com/zhengyun_ustc

//(3)��Ȩ����:
//����������汾��VideoIM�ֻ��Զ������ϴ���Ҳ�ǻ���Mowecam�������������ϸı�����ģ�
//���Ծ�������GPLЭ��Ĵ��⿪��Դ���룬���������ɴ������޸ģ�������GPLЭ���Լ��������ǰ���¡�

//(4)�����Դ:
1����[J2ME]VideoIM�ֻ��Զ������ϴ�����Դ˵����
2����[J2ME]VideoIM�ֻ��Զ������ϴ������˵����
3������Դ���룺

////////////////////////////////////////////////////////////////////*/
package com.ultrapower.common;

import java.io.InputStream;
import java.util.Hashtable;

import javax.microedition.lcdui.Image;

/**
 * Primitive resource mapper.
 * 
 * @author Peter Andersson
 */
public class CommandResources
{
  // Text keys
  private static int TXTID = 0;
  // text / page titles
  public static final int TXT_T_APP            = TXTID++;
  // RMS Name
  public static final int TXT_RMS_STORENAME    = TXTID++;
  
  public static final int TXT_T_STOPWEBCAM     = TXTID++;
  public static final int TXT_T_STARTWEBCAM    = TXTID++;
  public static final int TXT_T_SETTINGS       = TXTID++;
  public static final int TXT_T_GETSTREAM      = TXTID++;
  public static final int TXT_T_ABOUT          = TXTID++;
  // text / item labels
  public static final int TXT_I_WANTWEBCAM     = TXTID++;
  public static final int TXT_I_SETTINGS       = TXTID++;
  public static final int TXT_I_ABOUT          = TXTID++;
  public static final int TXT_I_EXIT           = TXTID++;
  // text / commands
  public static final int TXT_C_START_WEBCAM   = TXTID++;
  public static final int TXT_C_STOP_WEBCAM    = TXTID++;
  public static final int TXT_C_OK             = TXTID++;
  public static final int TXT_C_CANCEL         = TXTID++;
  public static final int TXT_C_BACK           = TXTID++;
  public static final int TXT_C_SAVESETTINGS   = TXTID++;
  public static final int TXT_C_QUIT           = TXTID++;
  
  // text / other stuff
  public static final int TXT_ABOUT            = TXTID++;
  public static final int TXT_WAITTITLE        = TXTID++;
  public static final int TXT_WAITMESSAGE        = TXTID++;
  
  public static final int TXT_WEBCAMTIPS       = TXTID++;
  public static final int TXT_POSTERROR_TITLE  = TXTID++;
  public static final int TXT_POSTERROR_MSG    = TXTID++;
  public static final int TXT_POSTSUCC_TITLE   = TXTID++;
  public static final int TXT_POSTSUCC_MSG     = TXTID++;
  
  /*
   * �ҵ�ת�������������ط�������ַ
   */
  public static final int TXT_MY_3GP_SERVER_URL  		 = TXTID++;
  public static final int TXT_DOWNLOAD3GPFILE_SERVER_URL  = TXTID++;
  
  public static final int TXT_MY_3GP_SERVER_METHOD  	 = TXTID++;
  public static final int TXT_MY_3GP_SERVER_METHOD_RETURN  	 = TXTID++;
  public static final int TXT_MY_3GP_SERVER_METHOD_RESPONSE  	 = TXTID++;
  
  public static final int TXT_DEMO_3GPFILE  	 = TXTID++;
  
  public static final int TXT_URL_MMS_SERVER_1  	 = TXTID++;
  
  
  public static final int RMS_KEY_DOWN3GP_SERVER_URL  	 = 0;
  public static final int RMS_KEY_MMS_TIMELIMIT  			 = 1;
  public static final int RMS_KEY_MY_3GP_SERVER_URL  		 = 2;

  // Image keys
  private static final int OFFSET_IMG          = 100;
  public static final int IMG_COMMAND   	      = 100;
  public static final int IMG_ABOUT	          = 101;
  
  // Text data
  protected static final char[][] TEXTBUF = {
    "��ͨ·��".toCharArray(),
    "TrafficViewer".toCharArray(),
    
    "��ͨ·��ֹͣ".toCharArray(),
	"��ͨ·������".toCharArray(),
    "����".toCharArray(),
	"��ȡʵʱ¼����".toCharArray(),
    "����".toCharArray(),
    
	"TrafficView".toCharArray(),
    "Settings".toCharArray(),
    "About".toCharArray(),
    "Exit".toCharArray(),

	"StartMonitor".toCharArray(),
    "StopMonitor".toCharArray(),
    "OK".toCharArray(),
	"Cancel".toCharArray(),
    "Back".toCharArray(),
    "Save Current".toCharArray(),
	"Exit".toCharArray(),
    
    ("��ͨ·��������-ʵʱ��ȡ������֣����д,��������ɸ�д������,���뾡������ԭ������Ϣ��\n" +
		"Ӧ����֧�ֲ���3gpý���ʽ���ֻ�����ȡ����������ͨҪ����������ʵʱ¼�񲢲��š�  \n" +
        "GoogleTalk>> zhengyun@gmail.com  \n" +
        "Blog>> http://zhengyun_ustc.cnblogs.com/ \n" +
        "Version>> 1.1.1 \n" +
        "Date>> 2005.10.21").toCharArray(),
        
     "��������¼��".toCharArray(),
	 "����¼��...".toCharArray(),
     ("Tips:������ѡ��˵��ϵġ������������������Ž�ͨҪ����������ʵʱ¼��").toCharArray(),
     
     
     "����¼��ʱ�����˴���".toCharArray(),
     "����ʵ��¼��ʱ�����쳣:".toCharArray(),
     
     "����¼��ɹ�".toCharArray(),
     "׼������ʵ��¼��!".toCharArray(),
     
	 "http://219.238.168.183:8080/TrafficStream/services/Streaming".toCharArray(),
	 "http://219.238.168.183/traffic/".toCharArray(),
	 "getstream".toCharArray(),
	 "getstreamReturn".toCharArray(),
	 "getstreamResponse".toCharArray(),
	 
	 "/res/3gp/demo.3gp".toCharArray(),
	 
	 "mms://220.194.63.17/cebeijing10".toCharArray(),
  };
  
  /** Image cache */
  protected static Hashtable m_images = new Hashtable();
  
  // Image resource names
  protected static final String[] IMGNAME_MAP ={
    "command.png",
    "about.png",
  };
  
  /**
   * Returns specified text as character array.
   * @param id  The id of the text.
   * @return    A text as char array.
   */
  public static char[] getChars(int id)
  {
    return TEXTBUF[id];
  }
  
  /**
   * Returns specified text as string.
   * @param id  The id of the text.
   * @return    A text as String.
   */  
  public static String getString(int id)
  {
    return new String(getChars(id));
  }
  
  /**
   * Returns specified image.
   * @param id  The id of the image.
   * @return    An image.
   */
  public static synchronized Image getImage(int id)
  {
    id -= OFFSET_IMG;
    Image img = (Image)m_images.get(new Integer(id));
    if (img == null)
    {
      try
      {
		  /*
		   * ע��ԭ������д����"/" +��ʼ���޷�����ͼ��
		   * �����Ҹ�Ϊ��"/res/" + 
		   */
        img = Image.createImage("/res/icons/" + IMGNAME_MAP[id]);
		System.out.println("get image:/res/icons/" + IMGNAME_MAP[id]);
        m_images.put(new Integer(id), img);
      }
      catch (Exception e)
      {
        System.out.println("Error getting resource img " + IMGNAME_MAP[id]
           + ">>" + e.getMessage());
        e.printStackTrace();
      }
    }
    return img;
  }
}
