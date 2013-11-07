/**
//VideoIM�ĵ��������ڣ�2005.10.12
//
//(1)������
//�����ƣ�VideoCoolala
//��˵����
//	�ṩ������
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

//(5)����һ������:
���VideoIM.jad�İ汾���õĲ����ʣ����ᵼ���ֻ����ذ�װʱ��ʾ���ļ����𻵡�!
ֻ������Ϊ�Ƚ������ı��硰1.2.2�����֣��ű��ֻ������ˡ�

////////////////////////////////////////////////////////////////////*/

package com.ultrapower.midlet;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.ultrapower.control.GUIController;


/**********************************************************
//VideoCoolala
//
//Class Description:
//	ʵ����Ӧ������MVC�е�View���֣���MIDlet�������档
//Author: 
//     zhengyun@ultrapower 2005.10.12
//
**********************************************************/
public class TrafficView extends MIDlet{
	private Display display;
	private static GUIController controller;
	
	/**
	 * default constructor
	 */
	public TrafficView() {
		super();
		display = Display.getDisplay(this);
	}

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() 
	throws MIDletStateChangeException{
		controller = new GUIController(this);
		try
		{
			controller.init();//��ʼ��RMS,Menu,Forms
		}
		catch(Exception exc)
		{
			setCurrent(
				new Alert(
						"��ʼ������", 
						"����ԭ��Ϊ:"
								+ exc.getMessage() + "/" + exc.getClass(),
								null, AlertType.ERROR));
		}
	}

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
		this.notifyPaused();
	}

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		controller = null;		
	}
	
	/**********************************************************
	//	 VideoCoolala::setCurrent()
	//
	//	 Description:
	//	  ���õ�ǰ��ʾ�Ľ���
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.12
	//
	**********************************************************/
	public void setCurrent(Displayable disp){
		display.setCurrent(disp);
	}	
	public void setCurrent(Alert alert, Displayable disp){
		display.setCurrent(alert, disp);
    }
	
	public Displayable getCurrent(){
		return display.getCurrent();
    }
	
	/**********************************************************
	//	 VideoCoolala::getCurrentDisplay()
	//
	//	 Description:
	//	  ��ȡ��ǰ��Display�������������������ܹ�����Ҫ��ʾʲô
	//
	//	 Parameters:
	//	 Return Values:
	//	 Author:
	//	      zhengyun@ultrapower 2005.10.12
	//
	**********************************************************/
	public Display getCurrentDisplay(){
		return display;
    }
	
	public void exit(boolean arg0){
		try{
			destroyApp(arg0);
			notifyDestroyed();
		}catch(MIDletStateChangeException e){
			//
		}
	}
}
