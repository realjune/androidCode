/*////////////////////////////////////////////////////////////////////
//�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�FormWebcam
//��˵����
//     MVC�е�View���֣���������/ֹͣ�Զ����պ��ϴ���Ƭ������Form��
//
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
//������: ֣��(2005.10.07)
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
package com.ultrapower.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
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
import com.ultrapower.model.ThreadGetVideo;
import com.ultrapower.model.VideoSettings;

/**********************************************************
//FormWebcam
//
//Class Description:
//	MVC�е�View���֣���������/ֹͣ�Զ����պ��ϴ���Ƭ������Form��
//
//Author:
//zhengyun@ultrapower 2005.10.12
//
**********************************************************/
public class FormTrafficCam extends Form
{
	private GUIController 	 controller;
    private static final Command m_cmdBack = new Command("BackMainMenu", 3, 0);
    private static final Command m_cmdStart = new Command("Start", "Download&Play", 4, 1);
    private static final Command m_cmdStop = new Command("Stop", "StopDownload&Play", 6, 2);
	private static final Command m_cmdPlayAgain
		= new Command("Replay", "ReplayCurrent", 6, 2);
	
    private ThreadGetVideo m_threadStreaming;
    public static FormTrafficCam m_formWebcam;
	private WaitFlash 		 m_WaitFlash;
	private Display     	 m_display;          // The display for this MIDlet
	
	// ������ǰTips����ʾ��Դ��ID��:
	private int m_nItemTipsAppendId = -1;

    public FormTrafficCam(String title, GUIController control)
    {
        super(title);
		controller = control;

        addCommand(m_cmdBack);
        addCommand(m_cmdStart);
		//addCommand(m_cmdPlayDemo);
        setCommandListener(new FormTrafficCamListener());
		m_formWebcam = this;
		
		m_display = controller.m_trafficcamMidlet.getCurrentDisplay();
    }

	/*
     * �ڲ�����������������������Command�¼��������¼���Ӧ�Ƴ����ÿ���������
     */
    private class FormTrafficCamListener implements CommandListener{ 
		public void commandAction(Command command, Displayable disp){
			if(command == m_cmdBack){
				controller.handleEvent(GUIController.EventID.EVENT_WEBCAM_BACK, null);
	        }
			else if(command == m_cmdStart){
				controller.handleEvent(GUIController.EventID.EVENT_STARTWEBCAM, null);    	                    	            
			}
			else if(command == m_cmdStop){
				controller.handleEvent(GUIController.EventID.EVENT_STOPWEBCAM, null);    	                    	            
			}
			else if(command == m_cmdPlayAgain){
				controller.handleEvent(GUIController.EventID.EVENT_PLAYAGAIN, null);    	                    	            
			}
			//end else
		}
    }//end inner class

    public final void StopWebcamDownload()
    {
		System.out.println("/** Enter StopWebcamDownload!");
		m_threadStreaming.setDownloadVideo(false);
		m_threadStreaming.StopPlayer();
        removeCommand(m_cmdStop);
        addCommand(m_cmdPlayAgain);
        setTitle(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_T_STOPWEBCAM)));
    }
	
	public final void ShowWebcamDownload()
    {		
		System.out.println("/** Enter ShowWebcamDownload!");
		removeCommand(m_cmdStop);
	    addCommand(m_cmdStart);
		m_threadStreaming = new ThreadGetVideo(controller);
	    Thread thread;
	    (thread = new Thread(m_threadStreaming)).start();
	    setTitle(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_T_STOPWEBCAM)));
    }
	
    public final void StartWebcamDownload()
    {	
		System.out.println("/** Enter StartWebcamDownload!");
		
        removeCommand(m_cmdPlayAgain);
        addCommand(m_cmdStop);
        setTitle(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_T_STARTWEBCAM)));
		
		/*if(m_WaitFlash == null)
		{
			// ��ǰû�еȺ򶯻�ʱ,������Ҫ����һ��
			m_WaitFlash = new WaitFlash(controller);
			m_WaitFlash.setMessage("������",
					"��������ý���ļ������Ե�...");
			m_display.setCurrent(m_WaitFlash);			
		}
		else
		{
			m_display.setCurrent(m_WaitFlash);
		}
		System.out.println("�����˵ȴ�����!");
		*/
		
		m_threadStreaming.DeleteVideoControl();
		m_threadStreaming.setDownloadVideo(true);		
    }
	
	public void PlayAgain()
	{
		removeCommand(m_cmdPlayAgain);
        addCommand(m_cmdStop);
		m_threadStreaming.PlayAgain();
	}
	
	public final synchronized void setProgress(
			String strProgressTitle,
			String strMessage)
    {
		if(m_nItemTipsAppendId < 0)
		{
			m_nItemTipsAppendId = append(new StringItem(strProgressTitle, strMessage));
		}
		else
		{
			set(m_nItemTipsAppendId, new StringItem(strProgressTitle, strMessage));
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
	
	/* Nokia S60 Exception
	 * Exception in thread "JMF thread: com.sun.media.ProcessEngine@11d2572[ com.sun.media.ProcessEngine@11d2572 ] 
	 * ( configureThread)" java.lang.Error: Invalid media file: File Type Box not found
	at com.nokia.phone.sdk.concept.util.mmedia.video.v3gpp.ISOMedia.readStream(ISOMedia.java)
	at com.nokia.phone.sdk.concept.util.mmedia.video.v3gpp.ISOMedia.<init>(ISOMedia.java)
	at com.nokia.phone.sdk.concept.util.mmedia.video.v3gpp.ThreeGPDeMux.getTracks(ThreeGPDeMux.java)
	at com.sun.media.BasicSourceModule.doRealize(BasicSourceModule.java:180)
	 */
}


/*
IOException: SymbianOS error = -28
?

*/