/**
//VideoIM�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�FormSetting
//��˵����
//	�ṩ���ù��ڱ�Ӧ������Ҫ�ļ���������Form
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
package com.ultrapower.view;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import com.ultrapower.common.CommandResources;
import com.ultrapower.control.GUIController;
import com.ultrapower.midlet.TrafficView;
import com.ultrapower.model.VideoSettings;


/**********************************************************
//FormSetting
//
//Class Description:
//	�ṩ���ù��ڱ�Ӧ������Ҫ�ļ���������Form
//
//Author:
// zhengyun@ultrapower 2005.10.12
//
**********************************************************/
public final class FormSetting extends Form
{
	private GUIController controller;
	
    private TextField m_txtDownload3gpFileSiteURL;
	private TextField m_txtMyConvertServerURL;
	private TextField m_txtLiveMediaTimeLimit;
	private VideoSettings settings;

    private static final Command m_cmdBack = 
		new Command(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_C_BACK)), 3, 0);
    private static final Command m_cmdSave = 
		new Command(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_C_SAVESETTINGS)), 4, 1);

    public FormSetting(GUIController control)
    {
        super(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_T_SETTINGS)));
		controller	=	control;
    	this.setCommandListener(new FormSettingListener());
        
        addCommand(m_cmdBack);
        addCommand(m_cmdSave);
    }
	
	/*
     * �ڲ�����������������������Command�¼��������¼���Ӧ�Ƴ����ÿ���������
     */
    private class FormSettingListener implements CommandListener{ 
		public void commandAction(Command command, Displayable disp){
			if(command == m_cmdBack){
				controller.handleEvent(GUIController.EventID.EVENT_SETTINGS_BACK, null);
	        }
			else if(command == m_cmdSave){
				System.out.println("FormSettingListener::cmdSave");
	            String file3gpURL		=	m_txtDownload3gpFileSiteURL.getString();
				//System.out.println("1" + type);
	            String convertURL	=	m_txtMyConvertServerURL.getString();
				//System.out.println("2" + width);
				String timeLimit	=	m_txtLiveMediaTimeLimit.getString();
               
				Object[] args = {file3gpURL, convertURL, timeLimit};
				controller.handleEvent(GUIController.EventID.EVENT_SETTINGS_SAVE, args);    	                    	            
			}//end else
		}
    }//end inner class

    public void initSettingsTextFields()
    {
		System.out.println("Enter FormSetting::initSettingsTextFields");
		settings = VideoSettings.getInstance();
		m_txtDownload3gpFileSiteURL = new TextField("����3gp��ý���������ַ:", 
				String.valueOf(settings.getDownload3gpFileSiteURL()), 64, TextField.URL);
		m_txtMyConvertServerURL = new TextField("ת��3gp��������ַ:", 
				String.valueOf(settings.getMyConvertServerURL()), 64, TextField.URL);
		
		m_txtLiveMediaTimeLimit = new TextField("ʵ��¼�������[������ΪС��10��������,��λ:��]:", 
				String.valueOf(settings.getLiveMediaTimeLimit()), 2, TextField.DECIMAL);
				
        TextField SettingsFields[] = {
				m_txtDownload3gpFileSiteURL,
				m_txtMyConvertServerURL,
				m_txtLiveMediaTimeLimit,
        };
		
        for(int i = 0; i < SettingsFields.length; i++)
            append(SettingsFields[i]);

    }

}
