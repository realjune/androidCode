/**
//VideoIM�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�ThreadPostVideo
//��˵����
//	�ṩץ����Ƭ�����͵Ĺ���
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
//������: ֣��(2005.10.12)
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
package com.ultrapower.model;

import java.io.InputStream;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Item;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;

import com.ultrapower.common.CommandResources;
import com.ultrapower.control.GUIController;

/**********************************************************
//ThreadPostVideo
//
//Class Description:
//	�ṩץ����Ƭ�����͵Ĺ���
//
//Author:
// zhengyun@ultrapower 2005.10.12
//
**********************************************************/
public class ThreadGetVideo
	implements Runnable {
	
	private GUIController 	m_controller = null;
	private VideoControl 	m_video = null;
	private Player         m_player = null;
	private boolean 		m_bDownloadVideo;
	private VideoSettings 	m_Settings = VideoSettings.getInstance();
	private InputStream    m_isInputMedia; 
		
	public ThreadGetVideo(GUIController controll)
    {
		System.out.println("/** Enter ThreadPostVideo Constractor!");
		m_controller 	 = controll;
		m_bDownloadVideo = false;
    }
	
	public synchronized void setDownloadVideo(boolean value)
	{
		m_bDownloadVideo = value;
	}
	
	public final void run()
    {
        /* Use networking if necessary */
        long lngStart;
        long lngTimeTaken;
		String sProgressWAITTITLE = String.valueOf(
				CommandResources.getChars(CommandResources.TXT_WAITTITLE));
        
		while(true)
		{
	        try
	        {
				//  ����������m_bDownloadVideo���ж��Ƿ�ȥ�õ�3gp�����ص�.
				//  �����ǰm_bDownloadVideo��false,��ô���߳̾�ֻ����˯��һ��ʱ����.
	            if( m_bDownloadVideo ) 
				{
					System.out.println("/* StartWebcamDownload::��ʼ����ʵ��¼��!");
		            
		            /*
		             *  Change to run locally on developer machine
		             */
					String serviceNamespace = 
						String.valueOf(m_controller.getSettings().getMyConvertServerURL());
							//String.valueOf(
							//		CommandResources.getChars(
							//				CommandResources.TXT_MY_3GP_SERVER_URL));
					String methodName = 
						String.valueOf(
								CommandResources.getChars(
										CommandResources.TXT_MY_3GP_SERVER_METHOD));
					String methodReturnName = 
						String.valueOf(
								CommandResources.getChars(
										CommandResources.TXT_MY_3GP_SERVER_METHOD_RETURN));
					String methodResponseName = 
						String.valueOf(
								CommandResources.getChars(
										CommandResources.TXT_MY_3GP_SERVER_METHOD_RESPONSE));
					String downloadSiteURL = 
						String.valueOf(m_controller.getSettings().getDownload3gpFileSiteURL());
						//String.valueOf(
						//		CommandResources.getChars(
						//				CommandResources.TXT_DOWNLOAD3GPFILE_SERVER_URL));
					String serviceUrl = 
						serviceNamespace + "?wsdl";
		
					System.out.println("Try add ClassMap.....");
		            //ClassMap classMap = new ClassMap(Soap.VER11);
					//classMap.prefixMap = Soap.prefixMap[2];

					System.out.println("/* Try new SoapObject */\n" + 
							"	serviceNamespace:" + serviceNamespace);
		            SoapObject request = new SoapObject(serviceNamespace, methodName );
					SoapSerializationEnvelope envelope =
						new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.bodyOut = request;
					/*envelope.addMapping(serviceNamespace, 
							methodResponseName, 
							PropertyInfo.VECTOR_CLASS);*/
					//(new MarshalBase64()).register(envelope);
					/*envelope.addMapping(
							"http://xml.apache.org/xml-soap",
							"Vector",
							PropertyInfo.VECTOR_CLASS); 
					envelope.addMapping(
							"http://schemas.xmlsoap.org/soap/encoding/",
							"string",
							PropertyInfo.STRING_CLASS); 
					envelope.addMapping(
							"http://schemas.xmlsoap.org/soap/encoding/",
							"int",
							PropertyInfo.INTEGER_CLASS);*/
					
					/*
					 * ��� web service���������
					 */
					String strMMSURL = 
						String.valueOf(m_controller.getSettings().getMyConvertServerURL());
						//String.valueOf(
						//		CommandResources.getChars(
						//				CommandResources.TXT_URL_MMS_SERVER_1));
					request.addProperty("url", 
							strMMSURL);
					System.out.println("/* ��ý�������:" + strMMSURL);
					String strTimeLimit = 
						String.valueOf(m_controller.getSettings().getLiveMediaTimeLimit());
						//String.valueOf(VideoSettings.getInstance().getLiveMediaTimeLimit());
					request.addProperty("timeVideo", 
							strTimeLimit);
					System.out.println("/* ʱ������:" + strTimeLimit);
					String str3gpFileName = 						
						String.valueOf(
							BuildLongFromString(
									GetCID() + "thisisasalt1234")) + ".3gp";
					request.addProperty("FileName", 
							str3gpFileName);
					System.out.println("/* �ļ���:" + str3gpFileName);
					
		
					System.out.println("/* Try new HttpTransport \n" + 
							"	serviceUrl:" + serviceUrl + "	;method:" + methodName);
		            //HttpTransport tx = new HttpTransport(serviceUrl, methodName);
		            //tx.setClassMap( classMap );
		            
					HttpTransport tx = 
						new HttpTransport(serviceUrl);
					tx.debug = true;
							            
					System.out.println("Try call Streaming web service");	
					tx.call(serviceNamespace + "#" + methodName,
							envelope);
					//Object Response = (Object)tx.call(request);
					System.out.println("End call Streaming web service");				
					
					KvmSerializable o3gpResponse = (KvmSerializable)envelope.bodyIn;
					if(o3gpResponse != null)
					{
						PropertyInfo info = new PropertyInfo();
						Hashtable properties = new Hashtable();
						//String str3gpResponse;
						
				        int cnt = o3gpResponse.getPropertyCount();
						for (int i = 0; i < cnt; i++) {
							o3gpResponse.getPropertyInfo(i, properties, info);
							System.out.println("info name:" + info.name);
							if(methodReturnName.equalsIgnoreCase(info.name))
							{
								System.out.println("����������Ҫ�ҵķ���ֵ!" );
								//str3gpResponse = (String)o3gpResponse.getProperty(i);
								//System.out.println("���������ߵ�URLΪ:" + str3gpResponse.getStringAt(0));
							}
						}
						
						downloadSiteURL = downloadSiteURL + str3gpFileName;
						System.out.println("���������ߵ�URLΪ:" + downloadSiteURL);
						
						HttpConnection conn = 
							(HttpConnection)Connector.open(downloadSiteURL);
						conn.setRequestMethod(HttpConnection.GET);
						conn.setRequestProperty("Content-Length", "0");
						
						conn.setRequestProperty("Accept-Language", "zh-cn");
						conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
						
						conn.setRequestProperty("Connection", "close"); 
						System.out.println("1");
						m_isInputMedia = 
							(InputStream)conn.openInputStream();
						System.out.println("2");
						m_player = (Player)Manager.createPlayer(m_isInputMedia, "video/3gpp");
						m_player.realize();
						System.out.println("3");
						m_video = (VideoControl)m_player.getControl("VideoControl");
						System.out.println("4");
						if(m_video != null)
				        {
							// the player can start with the smallest latency
							m_player.prefetch();
							
							System.out.println("/** �Ѿ��õ���VideoControl!");
						
							// Display Video as a Form item
							//
							Item itemVideoWindow;
							// ��USE_GUI_PRIMITIVEģʽ�£�Ĭ���ǿɼ��ģ�����USE_DIRECT_VIDEOģʽ�£�
							// Ĭ���ǲ��ɼ��ģ���Ҫͨ������setVisible(boolean visible)������
							(itemVideoWindow = (Item)m_video.initDisplayMode
								(VideoControl.USE_GUI_PRIMITIVE, null)).setLayout(3);
							m_controller.m_camForm.append(itemVideoWindow);
							m_player.start();
							System.out.println("/** ����Player!");
		
				        }
				        
					}
					
					m_bDownloadVideo = false;
				}
				
				lngStart = System.currentTimeMillis();
                lngTimeTaken = System.currentTimeMillis() - lngStart;
                if(lngTimeTaken < 100)
                    Thread.sleep(75 - lngTimeTaken);
	        
	        }
			catch(Exception exc)
	        {
				System.out.println("/** ����ʵ��¼��ʱ�����쳣!");
				m_bDownloadVideo = false;
				exc.printStackTrace();
				////////////////////////////////////////////
				// ���߿�����,���ܹ�����¼�񲢲���
				m_controller.setPlayerException(exc.getMessage()
						 + "/" + exc.getClass());
				m_controller.handleEventNoThrows(GUIController.EventID.EVENT_DOWNLOADDATA_ERROR, 
						null);
				////////////////////////////////////////////
	        }
		}
    }
	
	private String GetCID()
    {
        return "cid" + System.currentTimeMillis();
    }
	
	private long BuildLongFromString(String strMagic)
    {
        int j = 0;
        for(int k = 0; k < strMagic.length(); k++)
            if((k & 0x1) == 0)
                j ^= j << 7 ^ strMagic.charAt(k) ^ j >> 3;
            else
                j ^= ~(j << 11 ^ strMagic.charAt(k) ^ j >> 5);

        return (long)(j & 0x7fffffff);
    }
	
	public synchronized void PlayAgain()
	{
		System.out.println("/** PlayAgain!");
		try
		{
			if(m_player != null)
			{
				//if(m_player.getState() == m_player.CLOSED)
				{
					m_player.start();
				}
			}
		}
		catch(Exception exc)
		{
			
		}
		finally
		{
		}
	}
	
	public synchronized void StopPlayer()
	{
		System.out.println("/** StopPlayer!");
		try
		{
			if(m_player != null)
			{
				//if(m_player.getState() == m_player.STARTED)
				{
					m_player.stop();
				}
			}
		}
		catch(Exception exc)
		{
			
		}
		finally
		{
		}
	}
	
	public synchronized void DeleteVideoControl()
	{
		System.out.println("/** DeleteVideoControl!");
		try
		{
			if(m_player != null)
			{
				//if(m_player.getState() == m_player.STARTED)
				{
					m_player.stop();
				}
				m_player.deallocate();
				m_player = null;
			}
		}
		catch(Exception exc)
		{
			
		}
		finally
		{
			if(m_video != null)
			{
				m_video.setVisible(false);
				m_video = null;
			}
		}
	}

}
