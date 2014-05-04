/**
//�ĵ��������ڣ�2005.10.07
//
//(1)������
//�����ƣ�MainMenuList
//��˵����
//�ṩ�˵������У�
 *   ��ҪMobileWebCam
 *   ����MobileWebCam
 *   ����MobileWebCam
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
			3:����MobileWebCam
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

import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

import com.ultrapower.common.CommandResources;
import com.ultrapower.control.GUIController;

/**********************************************************
//MainMenuList
//
//Class Description:
//	�ṩ�˵������У�
 *   ��ҪMobileWebCam
 *   ����MobileWebCam
 *   ����MobileWebCam
//
//Author:
//zhengyun@ultrapower 2005.10.12
//
**********************************************************/
public class MainMenuList extends javax.microedition.lcdui.List{
    private GUIController 	 controller;
	public static MainMenuList currentList = null;
	
	private Command commandOK;
	private static Image m_imgCommand = CommandResources.getImage(
			CommandResources.IMG_COMMAND);
	
	private static final boolean m_bSelectCommands[] = {
        false, false, false, false
    };
	
	private Vector items;
		
    public MainMenuList(String title, GUIController control){
    	super(title, List.IMPLICIT);
    	
    	controller = control;
    	this.setCommandListener(new MainMenuListListener());
    	
		this.append(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_I_WANTWEBCAM))
						, m_imgCommand);
		this.append(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_I_SETTINGS))
				, m_imgCommand);
		this.append(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_I_ABOUT))
				, m_imgCommand);
		this.append(String.valueOf(CommandResources.getChars(
				CommandResources.TXT_I_EXIT))
				, m_imgCommand);
		
        commandOK = new Command(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_C_OK)), 8, 10);
        addCommand(commandOK);
        setCommandListener(new MainMenuListListener());
        setSelectedFlags(m_bSelectCommands);
        setSelectCommand(null);
        setFitPolicy(0);
		currentList = this;
    }
    
	/*
     * �ڲ�����������������������Command�¼��������¼���Ӧ�Ƴ����ÿ���������
     */
    private class MainMenuListListener implements CommandListener{ 
    	public void commandAction(Command command, Displayable disp){
    			if(command == commandOK || (command == List.SELECT_COMMAND)){
					
					System.out.println("Enter MainMenuListListener");
    	            int ind = ((List)disp).getSelectedIndex();
    	            switch(ind)
    	            {
					case 0:
						controller.handleEvent(GUIController.EventID.EVENT_SHOWWEBCAM,null);
						break;
					case 1:
						System.out.println("EVENT_SETTINGS");
						controller.handleEvent(GUIController.EventID.EVENT_SETTINGS,null);
						break;
					case 2:
						controller.handleEvent(GUIController.EventID.EVENT_ABOUT,null);
						break;
					case 3:
						controller.handleEvent(GUIController.EventID.EVENT_EXIT,null);
						break;
					default:
						return;
    	            }
    			}
    	}
    }
}
