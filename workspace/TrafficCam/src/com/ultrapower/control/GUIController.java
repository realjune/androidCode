/*////////////////////////////////////////////////////////////////////
//�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�GUIController
//��˵����
//       MVC�еĿ��������֣���������¼��Ĵ����Լ���������ʾ��һ��Form��
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
package com.ultrapower.control;

import java.io.InputStream;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

import com.ultrapower.common.CommandResources;
import com.ultrapower.facade.RmsFacade;
import com.ultrapower.midlet.TrafficView;
import com.ultrapower.model.VideoSettings;
import com.ultrapower.view.About;
import com.ultrapower.view.FormSetting;
import com.ultrapower.view.FormTrafficCam;
import com.ultrapower.view.MainMenuList;
import com.ultrapower.view.WaitFlash;

/**********************************************************
//	 GUIController
//
//	 Class Description:
//	 	MVC�еĿ��������֣���������¼��Ĵ����Լ���������ʾ��һ��Form��
//
//	 Author:
//	      zhengyun@ultrapower 2005.10.07
//
**********************************************************/
public class GUIController {
	public TrafficView	 		m_trafficcamMidlet;
	private VideoSettings 		m_Settings;
	private MainMenuList 		m_listMenu;
	private FormSetting 		m_settingForm;
	public  FormTrafficCam   	m_camForm;
	private WaitFlash 			m_waitFlash;
	private Alert 				m_alert;
	private About 				m_about;
	private VideoControl       m_video;
	
	//////////////////////////////////////////////////////////
	// ������4��������ר��Ϊ�˺�̨�����̴߳������ݽ�����������׼����
	// ���Ա�֤��4��set����Ϊ�߳�ͬ����
	/*
	 * ��Ҫ���͵�ͼ�����ݵ����ֽ���Ŀ
	 */
	private String m_sPostDataTotalLength;
	public synchronized void setPostDataTotalLength(String value)
	{
		m_sPostDataTotalLength = value;
	}
	/*
	 * ��ʣ���ͼ���ֽ���Ŀ
	 */
	private String m_sPostingDataLength;
	public synchronized void setPostingDataLength(String value)
	{
		m_sPostingDataLength = value;
	}
	/*
	 * ��Զ�˷������������Ĵ����
	 */
	private String m_sRemoteServerResponse;
	public synchronized void setRemoteServerResponse(String value)
	{
		m_sRemoteServerResponse = value;
	}
	/*
	 * �����ز�����ʱ�������쳣����
	 */
	private String m_sPlayerException;
	public synchronized void setPlayerException(String value)
	{
		m_sPlayerException = value;
	}
	/*
	 * ֪ͨ�������ݵ��߳��˳�,
	 * �������ַ�ʽ��ʹ�ú�̨�̺߳ͽ���ͬ���˳�
	 */
	private boolean m_bStopDownloadData = false;
	public synchronized boolean getStopDownloadData()
	{
		return m_bStopDownloadData;
	}
	/*
	 * �ں�̨�߳���New����һ��VideoControl��Ȼ�󴫳�������������
	 * �������ö�Video���Ų������ڿ�������
	 */
	public synchronized void setVideoControl(VideoControl value)
	{
		m_video = value;
	}
	/////////////////////////////////////////////////////////////
	
	/** Nbr of keys */
	protected static final int	NBR_OF_KEYS			= 6;
	
	public GUIController(TrafficView midletMain){
		m_trafficcamMidlet = midletMain;
	}
	
	/**********************************************************
	//	 GUIController::init()
	//
	//	 Description:
	//	  ��ʼ����������
	 * 		��ʼ������Form����
	 * 		�趨����ʾMainMenu�б����
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.12
	//
	**********************************************************/
	public void init(){
		
		// Initialize the RMS persistence facade
	    RmsFacade.init(NBR_OF_KEYS);
		
		m_Settings = VideoSettings.getInstance();
		// init ui
		m_listMenu		=	new MainMenuList(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_T_APP)), this);
		setCurrent(m_listMenu);
		
		m_about			=	new About(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_T_ABOUT)));
		m_settingForm	=	new FormSetting(this);	
		m_camForm		=	new FormTrafficCam(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_T_STOPWEBCAM))
						,this);
	}


	/**********************************************************
	//	 GUIController::getSettings()
	//
	//	 Description:
	//	  ���ص�ǰ��װ��RMS������Settings����ʵ��
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.07
	//
	**********************************************************/
	public VideoSettings getSettings(){
		return m_Settings;
	}
	
	/**********************************************************
	//	 GUIController::setCurrent()
	//
	//	 Description:
	//	  ���õ�ǰ��ʾ�Ľ���
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.07
	//
	**********************************************************/
	public void setCurrent(Displayable disp){
		m_trafficcamMidlet.setCurrent(disp);
    }
	public void setCurrent(Alert alert, Displayable disp){
		m_trafficcamMidlet.setCurrent(alert, disp);
    }
	
	/**********************************************************
	//	 GUIController::setProgress/setTitle
	//
	//	 Description:
	//	  ����FormProgress�����ϵ���ʾ����/�����
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.07
	//
	**********************************************************/
	/*public void setProgress(String title, String message){
		m_progressForm.setProgress(title, message);
	}
	public void setTitle(String title){
		m_progressForm.setTitle(title);
	}*/
	
	/**********************************************************
	//	 GUIController::EventID
	//
	//	 Description:
	//	  �����¼�ID�ڲ���
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.07
	//
	**********************************************************/
    public static class EventID{
        private EventID(){
        }
        
        public static final byte EVENT_EXIT				= 0;//�˳�
		public static final byte EVENT_SHOWWEBCAM        = 1;//����webcam����
        public static final byte EVENT_STARTWEBCAM 		= 2;//TrafficCam�����"����"��ť
		public static final byte EVENT_STOPWEBCAM 		= 3;//TrafficCam�����"ֹͣ"��ť
		public static final byte EVENT_WEBCAM_BACK 		= 4;//TrafficCam�����"����"��ť
        public static final byte EVENT_SETTINGS_SAVE		= 5;//���ý���ġ����桱��ť
        public static final byte EVENT_SETTINGS_BACK	    = 6;//���ý���ġ����ء���ť
        public static final byte EVENT_ABOUT				= 7;//About����
		public static final byte EVENT_ABOUT_BACK		= 8;//About����ġ����ء���ť
		public static final byte EVENT_SETTINGS		    = 9;//�������ý���
		public static final byte EVENT_DOWNLOADDATA		= 10;//˵����ǰ��ʼ��������
		public static final byte EVENT_DOWNLOADING	    = 11;//˵����ǰ������������
		public static final byte EVENT_DOWNLOADDATA_ERROR    = 12;//˵����ǰ������������ʱ�����˴���
		public static final byte EVENT_DOWNLOADDATA_SUCC     = 13;//˵����ǰ�ɹ���������
		public static final byte EVENT_START_PLAY_3GP     = 14;//��ʼ����3gpý���ļ�
		public static final byte EVENT_PLAYAGAIN    		 = 15;//���²��Ÿղŵ�3gpý���ļ�
    }
	
    
	/**********************************************************
	//	 GUIController::handleEvent
	//
	//	 Description:
	//	  �Դ�����¼����д���
	//
	//	 Parameters:
	//	 Return Values:
	//   Remark:
	 * 
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.07
	//
	**********************************************************/
	public void handleEventNoThrows( int eventID,Object[] args){   
		try{
			handleEvent(eventID, args);
		}
		catch(Exception exc){}
	}
    public void handleEvent( int eventID,Object[] args){   
		System.out.println("Controller::handleEvent eventID>>" + eventID);
		
    	switch (eventID)
        {   
    	    case EventID.EVENT_EXIT:
    	    {
				m_bStopDownloadData = true;
				// ����RMS
				RmsFacade.shutdown();
				System.gc();     // ֪ͨ���������ռ�
				Thread.yield();  // ���߳���ͣһ�£�ʹ��GC�������ϻ�û�������
				m_trafficcamMidlet.exit(false);
    	    	break;
    	    }
    	    case EventID.EVENT_SHOWWEBCAM:
    	    {
				try
				{
					m_camForm.ShowWebcamDownload();
				}
				catch(Exception exc)
				{
				
				}
				setCurrent(m_camForm);
    	        break;
    	    }
			case EventID.EVENT_SETTINGS:
    	    {
				m_settingForm.initSettingsTextFields();
				setCurrent(m_settingForm);
    	        break;
    	    }
    	    case EventID.EVENT_STARTWEBCAM:
    	    {
				m_bStopDownloadData = false;
				m_camForm.StartWebcamDownload();
				//setCurrent(m_camForm);
    	    	break;
    	    }
			case EventID.EVENT_STOPWEBCAM:
    	    {
				m_bStopDownloadData = true;
				m_camForm.StopWebcamDownload();
				setCurrent(m_camForm);
    	    	break;
    	    }
			case EventID.EVENT_WEBCAM_BACK:
			{
				m_bStopDownloadData = true;
				// �����ȱ�֤����ͷ�Ѿ��رյģ����Ҳ���ץȡ����
				m_camForm.StopWebcamDownload();
			}
    	    case EventID.EVENT_ABOUT_BACK:
    	    case EventID.EVENT_SETTINGS_BACK: 
    	    {
    	    	setCurrent(m_listMenu);
    	    	break;
    	    }
    	    case EventID.EVENT_SETTINGS_SAVE:
    	    {
				/*
				 * �洢��ǰ����ҳ���ϵ����в�������RMS
				 */
				String mmsurl = (String)args[0];
				String converturl = (String)args[1];
				String timelimit = (String)args[2];
				
    	        m_Settings.save(mmsurl,
						timelimit,
						converturl);
				setCurrent(m_settingForm);
				
    	        break;
    	    }
    	    case EventID.EVENT_ABOUT:
    	    {
    	        setCurrent(m_about);
    	        break;
    	    }
    	    case EventID.EVENT_DOWNLOADDATA:
			{
				/*
				 * ����FormWebcam�����·������֣�������ǰ����ͼ������ֽ���:
				 */
				try{
					// ��֪�û����ֽ���Ŀ:				
					String sProgress = String.valueOf(
							CommandResources.getChars(CommandResources.TXT_WAITTITLE));
					m_camForm.setProgress(sProgress 
								+ m_sPostDataTotalLength, "");
				}
				catch(Exception exc)
				{
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_WAITTITLE)), 
											exc.getMessage() + "/" + exc.getClass(), null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
				break;
			}
			case EventID.EVENT_DOWNLOADING:
			{
				/*
				 * ����FormWebcam�����·������֣�������ǰ����ͼ��Ľ���
				 * ��֪�û�ʣ���ֽ���Ŀ
				 */
				try{
					String sProgress = String.valueOf(
							CommandResources.getChars(CommandResources.TXT_WAITMESSAGE));
					String sProgressTitle = String.valueOf(
							CommandResources.getChars(CommandResources.TXT_WAITTITLE));
					m_camForm.setProgress(sProgressTitle + m_sPostDataTotalLength,
							sProgress + m_sPostingDataLength);
				}
				catch(Exception exc)
				{
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_WAITTITLE)), 
											exc.getMessage() + "/" + exc.getClass(), null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
				break;
			}
			case EventID.EVENT_DOWNLOADDATA_ERROR:
			{
				try{
					m_camForm.StopWebcamDownload();
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_POSTERROR_TITLE)), 
											String.valueOf(
													CommandResources.getChars(CommandResources.TXT_POSTERROR_MSG))
													+ m_sPlayerException, null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
				catch(Exception exc)
				{
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_WAITTITLE)), 
											exc.getMessage() + "/" + exc.getClass(), null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
				break;
			}
			case EventID.EVENT_DOWNLOADDATA_SUCC:
			{
				try{
					m_camForm.StopWebcamDownload();
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_POSTSUCC_TITLE)), 
											String.valueOf(
													CommandResources.getChars(CommandResources.TXT_POSTSUCC_MSG))
													, null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
				catch(Exception exc)
				{
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_WAITTITLE)), 
											exc.getMessage() + "/" + exc.getClass(), null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
				break;
			}
			case EventID.EVENT_PLAYAGAIN:
			{
				try
				{
					/*InputStream is = 
						getClass().getResourceAsStream
						(String.valueOf(
								CommandResources.getChars(CommandResources.TXT_DEMO_3GPFILE)));

					Player player = (Player)Manager.createPlayer(is, "video/3gpp");
					player.realize();
					System.out.println("3");
					m_video = (VideoControl)player.getControl("VideoControl");
					System.out.println("4");
					if(m_video != null)
			        {
						// the player can start with the smallest latency
						player.prefetch();
						
						System.out.println("/** �Ѿ��õ���VideoControl!");
					
						// Display Video as a Form item
						//
						Item itemVideoWindow;
						// ��USE_GUI_PRIMITIVEģʽ�£�Ĭ���ǿɼ��ģ�����USE_DIRECT_VIDEOģʽ�£�
						// Ĭ���ǲ��ɼ��ģ���Ҫͨ������setVisible(boolean visible)������
						(itemVideoWindow = (Item)m_video.initDisplayMode
							(VideoControl.USE_GUI_PRIMITIVE, null)).setLayout(3);
						m_camForm.append(itemVideoWindow);
						player.start();
						System.out.println("/** ����Player!");
	
			        }
			        */
					m_camForm.PlayAgain();
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
					setCurrent(
							new Alert(
									String.valueOf(
											CommandResources.getChars(CommandResources.TXT_WAITTITLE)), 
											exc.getMessage() + "/" + exc.getClass(), null, AlertType.ERROR),
							(Displayable)m_camForm);
				}
			}
         	default:
         	    break;
        }
    }
    
}
