package com.android.abt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ServerSocketActivity extends ListActivity {
	private static final String TAG = ServerSocketActivity.class .getSimpleName();
	/* һЩ��������������������� */
	public static final String PROTOCOL_SCHEME_L2CAP = "btl2cap";
	public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";
	public static final String PROTOCOL_SCHEME_BT_OBEX = "btgoep";
	public static final String PROTOCOL_SCHEME_TCP_OBEX = "tcpobex";
	private Handler _handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		};
	};
	/* ȡ��Ĭ�ϵ����������� */
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	/* ���������� */
	private BluetoothServerSocket _serverSocket;
	/* �ͻ��������б� */
	final List<String> lines = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	/* �߳�-�����ͻ��˵����� */
	private Thread _serverWorker = new Thread() {
		public void run() {
			listen();
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		if (!_bluetooth.isEnabled()) {
			Toast.makeText(this, "����������", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		setContentView(R.layout.server_socket);
		adapter = new ArrayAdapter<String>(
				ServerSocketActivity.this,
				android.R.layout.simple_list_item_1, lines);
		setListAdapter(adapter);
		/* ��ʼ���� */
		_serverWorker.start();
	}
	
	private void addMessage(String msg){
		lines.add(msg);
		_handler.sendEmptyMessage(1);
	}

	protected void onDestroy() {
		super.onDestroy();
		shutdownServer();
	}

	protected void finalize() throws Throwable {
		super.finalize();
		shutdownServer();
	}

	/* ֹͣ������ */
	private void shutdownServer() {
		new Thread() {
			public void run() {
				_serverWorker.interrupt();
				if (_serverSocket != null) {
					try {
						/* �رշ����� */
						_serverSocket.close();
					} catch (IOException e) {
						Log.e(TAG, "", e);
					}
					_serverSocket = null;
				}
			};
		}.start();
	}

	public void onButtonClicked(View view) {
		shutdownServer();
	}

	protected void listen() {
		try {
			/*
			 * ����һ������������ �����ֱ𣺷��������ơ�UUID
			 */
			_serverSocket = _bluetooth.listenUsingRfcommWithServiceRecord(
					PROTOCOL_SCHEME_RFCOMM,
					UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			_handler.post(new Runnable() {
				public void run() {
					addMessage("Rfcomm server started...");
				}
			});
			/* ���ܿͻ��˵��������� */
			if(Constant.DEBUG)
			Log.d(TAG,"socket accept wating...");
			BluetoothSocket socket = _serverSocket.accept();
			/* ������������ */
			if (socket != null) {
				if(Constant.DEBUG)
					Log.d(TAG,"socket accept"+socket.getRemoteDevice().getAddress());
				OutputStream os=socket.getOutputStream();
				os.write("Hi,Client".getBytes());
				ServerSocketActivity.this.addMessage(" <- Hi,Client");
				os.flush();
				InputStream inputStream = socket.getInputStream();
				int read = -1;
				final byte[] bytes = new byte[2048];
				while((read = inputStream.read(bytes))>=0){
					if(Constant.DEBUG){
						Log.d(TAG,read+" -> "+new String(bytes));
					}
					ServerSocketActivity.this.addMessage(new String(bytes));
				}
				os.close();
				inputStream.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "", e);
		} finally {

		}
		ServerSocketActivity.this.addMessage("close");
	}
}
