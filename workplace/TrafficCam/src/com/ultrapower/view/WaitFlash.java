/**
//�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ� WaitFlash
//��˵����
//�8�4	 �������ȴ����桱��Canvas����
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
//	������: ���Ľ�
//	�޸���: ֣��(2005.10.12)
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

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

import com.ultrapower.common.CommandResources;
import com.ultrapower.control.GUIController;

/*
 * Title: WaitingWebService
 * Description:��������ڳ�ʱ�����ʱ�ĵȴ�����
 * Copyright: Copyright (c) 2005
 * Company: ultrapower
 * 
 * @author ���Ľ�
 * @version 1.0
 */
public class WaitFlash extends Canvas {

	private static WaitFlash m_flash;
	/**
     * Singleton pattern is used to return 
     * only one instance of VideoSettings
     */
    public static synchronized WaitFlash getInstance(GUIController controller){
        if( m_flash == null ) {
			System.out.println("!-- WaitFlash::getInstance --!\n");
			m_flash = new WaitFlash(controller);
        }
		return m_flash;
    }
	
	private int mCount, mMaxinum;

	private int mInterval;

	private int mWidth, mHeight, mx, my, mRadius;

	private String m_sTitle = "";
	private String m_sMsg = "";
	
	private GUIController m_controller;
	
	private static final Command m_cmdBack = 
		new Command(
				String.valueOf(CommandResources.getChars(
						CommandResources.TXT_C_BACK)), 3, 0);

	protected void paint(Graphics g) {
		int theta = -(mCount * 360 / mMaxinum);

		///////////////
		// ����setColor������Ҫ���嶯���ı���ɫ��		
		//g.setColor(255, 255, 255); // ���ǰ�ɫ
		//g.setColor(8,65,99); // ��������ɫ
		g.setColor(222,235,198); // ����ǳ��ɫ
		g.fillRect(0, 0, mWidth, mHeight);
		///////////////

		///////////////
		// ����setColor������Ҫ���嶯����Բ������ı�Եɫ��
		//g.setColor(0x00ff0000); // ���Ǻ�ɫ
		g.setColor(173,195,41); // ������ɫ
		///////////////		
		g.drawArc(mx, my, mRadius, mRadius, 0, 360);
		
		///////////////
		// ����setColor������Ҫ���嶯����Բ����������ɫ��
		//g.setColor(255,154,49);// ��ɫ
		g.setColor(115,166,49);// ����ɫ
		///////////////
		g.fillArc(mx, my, mRadius, mRadius, theta + 90, 90);
		g.fillArc(mx, my, mRadius, mRadius, theta + 270, 90);

		/*
		 * ����д������ʾ��Ϣ�ı���
		 */
		if (m_sTitle != null) {
			g.drawString(m_sTitle, mWidth / 2, my+mRadius+20, Graphics.BOTTOM
					| Graphics.HCENTER);
		}
		
		/*
		 * ����д������ʾ��Ϣ����ϸ����
		 */
		if (m_sMsg != null) {
			g.drawString(m_sMsg, mWidth / 2, my+mRadius+30, Graphics.BOTTOM
					| Graphics.HCENTER);
		}
	}

	/*
     * �ڲ�����������������������Command�¼��������¼���Ӧ�Ƴ����ÿ���������
     */
    private class FormPostProgressListener implements CommandListener{ 
		public void commandAction(Command command, Displayable disp){
			if(command == m_cmdBack){
				m_controller.handleEvent(GUIController.EventID.EVENT_STOPWEBCAM, null);
	        }
		}
    }
	
	public WaitFlash(GUIController control) {
		m_controller = control;
		this.setCommandListener(new FormPostProgressListener());
        
		if(m_cmdBack != null)
			addCommand(m_cmdBack);
		
		mCount = 0;
		mMaxinum = 36;
		mInterval = 50;
		mWidth = getWidth();
		mHeight = getHeight();

		int halfWidth = (mWidth - mRadius) / 2;
		int halfHeight = (mHeight - mRadius) / 2;
		mRadius = Math.min(halfWidth, halfWidth)-10;

		mx = halfWidth - mRadius / 2;
		my = halfHeight - mRadius / 2;

		TimerTask task = new TimerTask() {
			public void run() {
				mCount = (mCount + 1) % mMaxinum;
				repaint();
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, 0, mInterval);
		
		m_flash = this;
	}

	public final void setMessage(String title, String message) {
		//System.out.println("Enter setMessage>>" + title + "/" + message);
		m_sTitle = title;
		m_sMsg = message;
		repaint();
	}
	
	public final void setTitle(String title) {
		//System.out.println("Enter setTitle>>" + title);
		m_sTitle = title;
		repaint();
	}
}
