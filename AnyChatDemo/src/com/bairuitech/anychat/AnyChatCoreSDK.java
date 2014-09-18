package com.bairuitech.anychat;		// �����޸İ�������

import android.view.Surface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class AnyChatCoreSDK
{
	AnyChatBaseEvent		baseEvent;
	AnyChatStateChgEvent	stateChgEvent;
	AnyChatPrivateChatEvent	privateChatEvent;
	AnyChatTextMsgEvent		textMsgEvent;
	AnyChatTransDataEvent	transDataEvent;
	
	static MainHandler mHandler;
	
	private final int HANDLE_TYPE_NOTIFYMSG = 1;		// ��Ϣ֪ͨ
	private final int HANDLE_TYPE_TEXTMSG 	= 2;		// ������Ϣ
	private final int HANDLE_TYPE_TRANSFILE = 3;		// �ļ�����
	private final int HANDLE_TYPE_TRANSBUF	= 4;		// ����������
	private final int HANDLE_TYPE_TRANSBUFEX= 5;		// ��չ����������
	private final int HANDLE_TYPE_SDKFILTER	= 6;		// SDK Filter Data
	
	// ����AnyChat�����¼�֪ͨ�ӿ�
	public void SetBaseEvent(AnyChatBaseEvent e)
	{
		mHandler=new MainHandler();
		RegisterNotify();
		this.baseEvent = e;
	}
	// ����AnyChat״̬�仯�¼�֪ͨ�ӿ�
	public void SetStateChgEvent(AnyChatStateChgEvent e)
	{
		RegisterNotify();
		this.stateChgEvent = e;
	}
	// ����AnyChat˽����Ϣ֪ͨ�ӿ�
	public void SetPrivateChatEvent(AnyChatPrivateChatEvent e)
	{
		RegisterNotify();
		this.privateChatEvent = e;
	}
	// ��������������Ϣ֪ͨ�ӿ�
	public void SetTextMessageEvent(AnyChatTextMsgEvent e)
	{
		RegisterNotify();
		this.textMsgEvent = e;
	}
	// �������ݴ�����Ϣ֪ͨ�ӿ�
	public void SetTransDataEvent(AnyChatTransDataEvent e)
	{
		RegisterNotify();
		this.transDataEvent = e;
	}
	// ��ѯSDK���汾��
	public int GetSDKMainVersion()
	{
		return GetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_MAINVERSION);
	}
	// ��ѯSDK�Ӱ汾��
	public int GetSDKSubVersion()
	{
		return GetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_SUBVERSION);
	}
	// ��ѯSDK����ʱ��
	public String GetSDKBuildTime()
	{
		return GetSDKOptionString(AnyChatDefine.BRAC_SO_CORESDK_BUILDTIME);
	}
    
    // ע����Ϣ֪ͨ
    public native int RegisterNotify();
    
    // ��ʼ��SDK
    public native int InitSDK(int osver, int flags);
    // ���ӷ�����
    public native int Connect(String serverip, int port);
    // ��¼ϵͳ
    public native int Login(String username, String password);
    // ���뷿�䣨����ID��
    public native int EnterRoom(int roomid, String password);
    // ���뷿�䣨�������ƣ�
    public native int EnterRoomEx(String roomname, String password);
    
    // �˳�����
    public native int LeaveRoom(int roomid);
    // ע����¼
    public native int Logout();
    // �ͷ���Դ
    public native int Release();
    
    // ��ȡ�����û��б�
    public native int[] GetOnlineUser();
    // ������Ƶ��ʾλ��
    public native int SetVideoPos(int userid, Surface s, int lef, int top, int right, int bottom);
    // �û�����ͷ����
    public native int UserCameraControl(int userid, int bopen);
    // �û���Ƶ����
    public native int UserSpeakControl(int userid, int bopen);
    
    // ��ȡָ���û����ַ�������״̬
    public native String QueryUserStateString(int userid, int infoname);
    // ��ȡָ���û���˵������(0 ~ 100)
    public native int GetUserSpeakVolume(int userid);
    // ��ȡָ���û�������ͷ״̬
    public native int GetCameraState(int userid);
    // ��ȡָ���û�����Ƶ�豸״̬
	public native int GetSpeakState(int userid);
	// ��ȡָ���û�����Ƶ�ֱ��ʿ��
	public native int GetUserVideoWidth(int userid);
	// ��ȡָ���û�����Ƶ�ֱ��ʸ߶�
	public native int GetUserVideoHeight(int userid);

	// ���÷�������֤����
	public native int SetServerAuthPass(String Password);
	// ����SDK����������ֵ��
	public native int SetSDKOptionInt(int optname, int optvalue);
	// ����SDK�������ַ���ֵ��
	public native int SetSDKOptionString(int optname, String optvalue);
	// ��ѯSDK����������ֵ��
	public native int GetSDKOptionInt(int optname);
	// ��ѯSDK�������ַ���ֵ��
	public native String GetSDKOptionString(int optname);
	
	// ����������Ϣ
	public native int SendTextMessage(int userid, int secret, String message);
	// �����ļ�
	public native int TransFile(int userid, String filepath, int wparam, int lparam, int flags);
	// ͸��ͨ�����ͻ�����
	public native int TransBuffer(int userid, byte[] buf, int len);
	// ͸��ͨ�����ͻ�������չ
	public native int TransBufferEx(int userid, byte[] buf, int len, int wparam, int lparam, int flags);
	// ��ֹ��������
	public native int CancelTransTask(int userid, int taskid);
	// ��ѯ��������״̬
	public native int QueryTransTaskInfo(int userid, int taskid, int infoname);
	// ����SDK Filter ͨ������
	public native int SendSDKFilterData(byte[] buf, int len);
	
	// ������Ƶ�Զ��Խ�
	public void CameraAutoFocus()
	{
		SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FOCUSCTRL, 1);
	}
	// ��ѯָ���û�����
	public String GetUserName(int userid)
	{
		return QueryUserStateString(userid, AnyChatDefine.BRAC_USERSTATE_NICKNAME);
	}
	// ��ѯָ���û�������IP��ַ
	public String GetUserIPAddr(int userid)
	{
		return QueryUserStateString(userid, AnyChatDefine.BRAC_USERSTATE_INTERNETIP);
	}
	
	// ö�ٱ�����Ƶ�ɼ��豸
	public native String[] EnumVideoCapture();
	// ѡ��ָ������Ƶ�ɼ��豸
	public native int SelectVideoCapture(String devicename);
	// ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
	public native String GetCurVideoCapture();
	// ö�ٱ�����Ƶ�ɼ��豸
	public native String[] EnumAudioCapture();
	// ѡ��ָ������Ƶ�ɼ��豸
	public native int SelectAudioCapture(String devicename);
	// ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
	public native String GetCurAudioCapture();
	// ö�ٱ�����Ƶ�����豸
	public native String[] EnumAudioPlayback();
	// ѡ��ָ������Ƶ�����豸
	public native int SelectAudioPlayback(String devicename);
	// ��ȡ��ǰʹ�õ���Ƶ�����豸
	public native String GetCurAudioPlayback();	
    
    // �첽��Ϣ֪ͨ
    public void OnNotifyMsg(int dwNotifyMsg, int wParam, int lParam)
    {
    	switch(dwNotifyMsg)
    	{
		case AnyChatDefine.WM_GV_CONNECT:			
			if(baseEvent != null)
				baseEvent.OnAnyChatConnectMessage(wParam>=1?true:false);
			break;
		case AnyChatDefine.WM_GV_LOGINSYSTEM:
			if(baseEvent != null)
				baseEvent.OnAnyChatLoginMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_ENTERROOM:
			if(baseEvent != null)
				baseEvent.OnAnyChatEnterRoomMessage(wParam, lParam);
			break;
		case AnyChatDefine.WM_GV_USERATROOM:
			if(baseEvent != null)
				baseEvent.OnAnyChatUserAtRoomMessage(wParam,lParam>=1?true:false);
			break;
		case AnyChatDefine.WM_GV_LINKCLOSE:
			if(baseEvent != null)
				baseEvent.OnAnyChatLinkCloseMessage(wParam);
			break;
		case AnyChatDefine.WM_GV_ONLINEUSER:
			if(baseEvent != null)
				baseEvent.OnAnyChatOnlineUserMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_MICSTATECHANGE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatMicStateChgMessage(wParam,lParam==0?false:true);
			break;			
		case AnyChatDefine.WM_GV_CAMERASTATE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatCameraStateChgMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_CHATMODECHG:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatChatModeChgMessage(wParam,lParam==0?true:false);
			break;
		case AnyChatDefine.WM_GV_ACTIVESTATE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatActiveStateChgMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_P2PCONNECTSTATE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatP2PConnectStateMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_VIDEOSIZECHG:
//			OnAnyChatVideoSizeChgMessage(wParam, LOWORD(lParam), HIWORD(lParam));
			break;
		case AnyChatDefine.WM_GV_PRIVATEREQUEST:
			if(privateChatEvent != null)
				privateChatEvent.OnAnyChatPrivateRequestMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_PRIVATEECHO:
			if(privateChatEvent != null)
				privateChatEvent.OnAnyChatPrivateEchoMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_PRIVATEEXIT:
			if(privateChatEvent != null)
				privateChatEvent.OnAnyChatPrivateExitMessage(wParam,lParam);
			break;
		default:
			break;
		}
    }
  
    class MainHandler extends Handler
    {
         public MainHandler(){}
         public MainHandler(Looper L)
         {
             super(L);
         }
         public void handleMessage(Message nMsg)
         {
             super.handleMessage(nMsg);
             Bundle tBundle=nMsg.getData();
             int type = tBundle.getInt("HANDLETYPE");
             if(type == HANDLE_TYPE_NOTIFYMSG)
             {
            	 int msg=tBundle.getInt("MSG");
            	 int wParam=tBundle.getInt("WPARAM");
            	 int lParam=tBundle.getInt("LPARAM");
            	 AnyChatCoreSDK.this.OnNotifyMsg(msg,wParam,lParam);
             }
             else if(type == HANDLE_TYPE_TEXTMSG)
             {
            	 int fromid = tBundle.getInt("FROMUSERID");
                 int toid = tBundle.getInt("TOUSERID");
                 int secret = tBundle.getInt("SECRET");
                 String message = tBundle.getString("MESSAGE");
                 if(AnyChatCoreSDK.this.textMsgEvent != null)
                	 AnyChatCoreSDK.this.textMsgEvent.OnAnyChatTextMessage(fromid, toid, secret!=0?true:false, message);
             }
             else if(type == HANDLE_TYPE_TRANSFILE)
             {
                 int userid = tBundle.getInt("USERID");
                 String filename = tBundle.getString("FILENAME");
                 String tempfile = tBundle.getString("TEMPFILE");
                 int length = tBundle.getInt("LENGTH");
                 int wparam = tBundle.getInt("WPARAM");
                 int lparam = tBundle.getInt("LPARAM");
                 int taskid = tBundle.getInt("TASKID");
                 if(AnyChatCoreSDK.this.transDataEvent != null)
                	 AnyChatCoreSDK.this.transDataEvent.OnAnyChatTransFile(userid, filename, tempfile, length, wparam, lparam, taskid);
             }
             else if(type == HANDLE_TYPE_TRANSBUF)
             {
            	 int userid = tBundle.getInt("USERID");
            	 int length = tBundle.getInt("LENGTH");
            	 byte[] buf = tBundle.getByteArray("BUF");
            	 if(AnyChatCoreSDK.this.transDataEvent != null)
            		 AnyChatCoreSDK.this.transDataEvent.OnAnyChatTransBuffer(userid, buf, length);
             }
             else if(type == HANDLE_TYPE_TRANSBUFEX)
             {
            	 int userid = tBundle.getInt("USERID");
            	 int length = tBundle.getInt("LENGTH");
            	 byte[] buf = tBundle.getByteArray("BUF");
            	 int wparam = tBundle.getInt("WPARAM");
            	 int lparam = tBundle.getInt("LPARAM");
            	 int taskid = tBundle.getInt("TASKID");
            	 if(AnyChatCoreSDK.this.transDataEvent != null)
            		 AnyChatCoreSDK.this.transDataEvent.OnAnyChatTransBufferEx(userid, buf, length, wparam, lparam, taskid);
             }
             else if(type == HANDLE_TYPE_SDKFILTER)
             {
            	 int length = tBundle.getInt("LENGTH");
            	 byte[] buf = tBundle.getByteArray("BUF");
            	 if(AnyChatCoreSDK.this.transDataEvent != null)
            		 AnyChatCoreSDK.this.transDataEvent.OnAnyChatSDKFilterData(buf, length); 
             }
        }
     }
   
    // �첽��Ϣ֪ͨ��AnyChat�ײ������̻߳ص���������Ҫͨ��Msg���ݵ����̣߳�
	private void OnAnyChatNotifyMsg(int dwNotifyMsg, int wParam, int lParam)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_NOTIFYMSG);       
        tBundle.putInt("MSG", dwNotifyMsg);
        tBundle.putInt("WPARAM", wParam);
        tBundle.putInt("LPARAM", lParam);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    // ������Ϣ֪ͨ��AnyChat�ײ������̻߳ص���������Ҫͨ��Msg���ݵ����̣߳�
	private void OnTextMessageCallBack(int dwFromUserid, int dwToUserid, int bSecret, String message)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TEXTMSG);       
        tBundle.putInt("FROMUSERID", dwFromUserid);
        tBundle.putInt("TOUSERID", dwToUserid);
        tBundle.putInt("SECRET", bSecret);
        tBundle.putString("MESSAGE", message);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    // �ļ�����ص���������
	private void OnTransFileCallBack(int userid, String filename, String tempfilepath, int filelength, int wparam, int lparam, int taskid)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TRANSFILE);       
        tBundle.putInt("USERID", userid);
        tBundle.putString("FILENAME", filename);
        tBundle.putString("TEMPFILE", tempfilepath);
        tBundle.putInt("LENGTH", filelength);
        tBundle.putInt("WPARAM", wparam);
        tBundle.putInt("LPARAM", lparam);
        tBundle.putInt("TASKID", taskid);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    // �������ص���������
	private void OnTransBufferCallBack(int userid, byte[] buf, int len)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TRANSBUF);       
        tBundle.putInt("USERID", userid);
        tBundle.putByteArray("BUF", buf);
        tBundle.putInt("LENGTH", len);
         tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    // ��������չ�ص���������
	private void OnTransBufferExCallBack(int userid, byte[] buf, int len, int wparam, int lparam, int taskid)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TRANSBUFEX);       
        tBundle.putInt("USERID", userid);
        tBundle.putByteArray("BUF", buf);
        tBundle.putInt("LENGTH", len);
        tBundle.putInt("WPARAM", wparam);
        tBundle.putInt("LPARAM", lparam);
        tBundle.putInt("TASKID", taskid);
         tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    // ���������͵�SDK Filter Data���ݻص���������
	private void OnSDKFilterDataCallBack(byte[] buf, int len)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_SDKFILTER);       
        tBundle.putByteArray("BUF", buf);
        tBundle.putInt("LENGTH", len);
         tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    
    static {
    	System.loadLibrary("anychatcore");
    }
    
}

