package com.android.abt;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * <pre>
 * ʹ�������豸��Ҫ����Manifest�п���Ȩ��
 * // ʹ�������豸��Ȩ��  
 *     < uses-permission android:name="android.permission.BLUETOOTH" />  
 *     // ���������豸��Ȩ��  
 *     < uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
 * @author junxu.wang
 * 
 */
public class BtUtils {
	BluetoothAdapter adapter;
	String name;
	/**
	 * ������ ���������������android.bluetooth.BluetoothAdapter���������豸�Ƿ�֧�����������֧�֣��ʹ�������
	 * 
	 * @param context
	 */
	public void enableBt(Context context) {
		// ����豸�Ƿ�֧������
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter == null) {
			// �豸��֧������
			return;
		}
		// ������
		if (!adapter.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			// ���������ɼ��ԣ����300��
			intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			context.startActivity(intent);
		}
	}

	/**
	 * ��ȡ����Ե������豸��android.bluetooth.BluetoothDevice��
	 * �״�����ĳ�����豸��Ҫ����ԣ�һ����Գɹ������豸����Ϣ�ᱻ���棬�Ժ�����ʱ��������ԣ���������Ե��豸��һ���������ӵġ�
	 */
	public void bondedDevices() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> devices = adapter.getBondedDevices();
		for (int i = 0; i < devices.size(); i++) {
			BluetoothDevice device = (BluetoothDevice) devices.iterator()
					.next();
			System.out.println(device.getName());
		}
	}

	/**
	 * ������Χ�������豸
	 * ���������������豸�󽫽���Թ㲥��ʽ����ȥ��������Ҫ�Զ���һ���̳й㲥���࣬��onReceive�����л�ò����������豸�����������
	 */
	public void discovery(Context context) {
		// ���ù㲥��Ϣ����
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		// ע��㲥�����������ղ������������
		context.registerReceiver(receiver, intentFilter);
		// Ѱ�������豸��android�Ὣ���ҵ����豸�Թ㲥��ʽ����ȥ
		adapter.startDiscovery();
	}
	
		/**�����豸����Ժ�״̬����
		 */
		private BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		    		// ��ȡ���ҵ��������豸
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					System.out.println(device.getName());
					// ������ҵ����豸����Ҫ���ӵ��豸������
					if (device.getName().equalsIgnoreCase(name)) {
						// ���������豸�Ĺ���ռ����Դ�Ƚ϶࣬һ���ҵ���Ҫ���ӵ��豸����Ҫ��ʱ�ر�����
					    adapter.cancelDiscovery();
					    // ��ȡ�����豸������״̬
					   int  connectState = device.getBondState();
					    switch (connectState) {
		   			        // δ���
					    	case BluetoothDevice.BOND_NONE:
							    // ���
							    try {
								    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
								    createBondMethod.invoke(device);
							    } catch (Exception e) { 
								    e.printStackTrace();
							    }
							    break;
						    // �����
						    case BluetoothDevice.BOND_BONDED:
							    try {
							    	// ����
							    	connect(device);
							    } catch (IOException e) {
							    	e.printStackTrace();
							    }
							    break;
					    }
				    }
		       } else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
					// ״̬�ı�Ĺ㲥
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					if (device.getName().equalsIgnoreCase(name)) { 
						int connectState = device.getBondState();
						switch (connectState) {
							case BluetoothDevice.BOND_NONE:
								break;
							case BluetoothDevice.BOND_BONDING:
								break;
							case BluetoothDevice.BOND_BONDED:
							    try {
								    // ����
		                            connect(device);
		                        } catch (IOException e) {
		                            e.printStackTrace();
		                        }
		                        break;
		                }
		            }
		        }
		    }
		};
		
		/**�����豸������
		 * @param device
		 * @throws IOException
		 */
		public void connect(BluetoothDevice device) throws IOException {
			// �̶���UUID
			final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
			UUID uuid = UUID.fromString(SPP_UUID);
		    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
		    socket.connect();
		}

}
