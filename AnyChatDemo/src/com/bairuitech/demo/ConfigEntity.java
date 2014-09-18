package com.bairuitech.demo;

public class ConfigEntity {
	public static final int VIDEO_MODE_SERVERCONFIG = 0;	// ��������Ƶ��������
	public static final int VIDEO_MODE_CUSTOMCONFIG = 1;	// �Զ�����Ƶ��������
	
	public static final int VIDEO_QUALITY_NORMAL = 2;		// ��ͨ��Ƶ����
	public static final int VIDEO_QUALITY_GOOD = 3;			// �е���Ƶ����
	public static final int VIDEO_QUALITY_BEST = 4;			// �Ϻ���Ƶ����
	
	public boolean IsSaveNameAndPw;
	public String name = "";
	public String password = "";

	public String ip = "";
	public int port;
	
	public int configMode = VIDEO_MODE_SERVERCONFIG;
	public int resolution_width = 0;
	public int resolution_height = 0;
	
	public int videoBitrate = 0;
	public int videoFps = 0;
	public int videoQuality = VIDEO_QUALITY_GOOD;
	public int videoPreset = 1;
	public int videoOverlay = 1;							// ������Ƶ�Ƿ����Overlayģʽ
	public int videorotatemode = 0;							// ������Ƶ��תģʽ
	
	public int enableP2P = 1;
	public int useARMv6Lib = 0;								// �Ƿ�ǿ��ʹ��ARMv6ָ���Ĭ�����ں��Զ��ж�
	public int enableAEC = 0;								// �Ƿ�ʹ�û�����������
	public int useHWCodec = 0;								// �Ƿ�ʹ��ƽ̨����Ӳ���������
	public int smoothPlayMode = 0;							// �Ƿ�ʹ��ƽ������ģʽ����ʱ���Ÿ�ƽ��������ʱ����������ˣ��ر�ʱ�������������[Ĭ��]��������ʱ�������ݿ�ס��
}
