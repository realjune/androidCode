/**
//VideoIM�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�VideoSettings
//��˵����
//	�ṩ�洢�Ͷ�ȡRMS�й��ڱ�Ӧ������Ҫ�ļ��������Ĺ���
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

import javax.microedition.rms.RecordStoreException;

import com.ultrapower.common.CommandResources;
import com.ultrapower.facade.RmsFacade;

/**********************************************************
//VideoSettings
//
//Class Description:
//	�ṩ�洢�Ͷ�ȡRMS�й��ڱ�Ӧ������Ҫ�ļ��������Ĺ���
//
//Author:
//   zhengyun@ultrapower 2005.10.12
//
**********************************************************/
public class VideoSettings {

	private static VideoSettings 	 m_settings;
    /**
     * Singleton pattern is used to return 
     * only one instance of VideoSettings
     */
    public static synchronized VideoSettings getInstance(){
        if( m_settings == null ) {
			System.out.println("!-- VideoSettings::getInstance --!\n");
			m_settings = new VideoSettings();
			try
	        {			
				m_settings.LoadSettings();
				System.out.println("ѡ���RMS�ж�ȡ����");
	        }
	        catch(Exception exc)
	        {	
				m_settings.InitDefaultSettings();
				System.out.println("��RMS���޷���ȡ,��ΪĬ����ֵ");
	        }
        }
        return m_settings;
    }
	
  /** ������ý���ļ��ĵ�ַ,����ת������֮���ṩ���ļ����ص�ַ����ת��3gp�ķ�����url��һ�� */
  protected char[] m_sDownload3gpFileSiteURL;
  /** "��ȡ��ý��ʵ��¼�������" */
  protected int m_nLiveMediaTimeLimit;
  /** "�ҵ�ת��mmsΪ3gp�ķ������ĵ�ַ" */
  protected char[] m_sMyConvertServerURL;
  
  ///////////////////////////////////////////////////////////////////
  
  /**
   * Returns "������ý���ļ��ĵ�ַ,����ת������֮���ṩ���ļ����ص�ַ����ת��3gp�ķ�����url��һ��".
   * @return	������ý���ļ��ĵ�ַ.
   */
  public char[] getDownload3gpFileSiteURL()
  {
    return m_sDownload3gpFileSiteURL;
  }
  
  /**
   * Returns "��ȡ��ý��ʵ��¼�������".
   * @return	��ȡ��ý��ʵ��¼�������.
   */
  public int getLiveMediaTimeLimit()
  {
    return m_nLiveMediaTimeLimit;
  }
  
  /**
   * Returns "�ҵ�ת��mmsΪ3gp�ķ������ĵ�ַ".
   * @return	�ҵ�ת��mmsΪ3gp�ķ������ĵ�ַ.
   */
  public char[] getMyConvertServerURL()
  {
    return m_sMyConvertServerURL;
  }
  
  ///////////////////////////////////////////////////////////////////
  
  /**
   * set "������ý���ļ��ĵ�ַ,����ת������֮���ṩ���ļ����ص�ַ����ת��3gp�ķ�����url��һ��".
   * @param ������ý���ļ��ĵ�ַ.
   */
  public void setDownload3gpFileSiteURL(char[] value)
  {
    m_sDownload3gpFileSiteURL = value;
  }
  
  /**
   * set "��ȡ��ý��ʵ��¼�������".
   * @param	 ��ȡ��ý��ʵ��¼�������.
   */
  public void setLiveMediaTimeLimit(int value)
  {
    m_nLiveMediaTimeLimit = value;
  }
  
  /**
   * set "�ҵ�ת��mmsΪ3gp�ķ������ĵ�ַ".
   * @param	 �ҵ�ת��mmsΪ3gp�ķ������ĵ�ַ.
   */
  public void setMyConvertServerURL(char[] value)
  {
	  m_sMyConvertServerURL = value;
  }
  
  /////////////////////////////////////////////////////////////
  
  /**
   * ����ǰ�û����õĸ������д�뵽RMS��.
   */
  public void save()
  {
    RmsFacade.setChars(
			CommandResources.RMS_KEY_DOWN3GP_SERVER_URL,
			m_sDownload3gpFileSiteURL);
    RmsFacade.setInt(CommandResources.RMS_KEY_MMS_TIMELIMIT,
			m_nLiveMediaTimeLimit);
    RmsFacade.setChars(CommandResources.RMS_KEY_MY_3GP_SERVER_URL,
			m_sMyConvertServerURL);
  }
  
  /**
   * ����ǰ�û����õĸ������д�뵽RMS��.
   */
  public void save(String sDownload3gpFileSiteURL,
		  String sLiveMediaTimeLimit,
		  String sMyConvertServerURL)
  {
	System.out.println("Enter VideoSettings::save");
	m_sDownload3gpFileSiteURL = sDownload3gpFileSiteURL.toCharArray();
    RmsFacade.setChars(CommandResources.RMS_KEY_DOWN3GP_SERVER_URL,
			m_sDownload3gpFileSiteURL);	
	
	m_nLiveMediaTimeLimit = Integer.valueOf(sLiveMediaTimeLimit).intValue();
    RmsFacade.setInt(CommandResources.RMS_KEY_MMS_TIMELIMIT,
			m_nLiveMediaTimeLimit);
		
	m_sMyConvertServerURL = sMyConvertServerURL.toCharArray();
    RmsFacade.setChars(CommandResources.RMS_KEY_MY_3GP_SERVER_URL,
			m_sMyConvertServerURL);
  }
  
  /**
   * ֱ�Ӵ�RMS�����������ȡ����
   */
  public void LoadSettings()
  throws RecordStoreException
  {
	  m_sDownload3gpFileSiteURL = RmsFacade.getChars(CommandResources.RMS_KEY_DOWN3GP_SERVER_URL);
	  if(m_sDownload3gpFileSiteURL.length > 0)
		  System.out.println("������:" + m_sDownload3gpFileSiteURL);
	  else
	  {
		  // ˵��û��RMS��¼,�����ǵ�һ�����б�Ӧ��
		  throw new RecordStoreException();
	  }
		 
	  m_nLiveMediaTimeLimit = RmsFacade.getInt(CommandResources.RMS_KEY_MMS_TIMELIMIT);
	  
	  m_sMyConvertServerURL = RmsFacade.getChars(CommandResources.RMS_KEY_MY_3GP_SERVER_URL);
	  
  }
  
  /**
   * Initializes this videoim
   */
  public void InitDefaultSettings()
  {
	  m_sDownload3gpFileSiteURL = CommandResources.getChars(CommandResources.TXT_DOWNLOAD3GPFILE_SERVER_URL);
	  m_sMyConvertServerURL = CommandResources.getChars(CommandResources.TXT_MY_3GP_SERVER_URL);
	  m_nLiveMediaTimeLimit = 3;
	  
	  // ��Ĭ�ϵ���ֵд��RMS
	  save();
  }
}
