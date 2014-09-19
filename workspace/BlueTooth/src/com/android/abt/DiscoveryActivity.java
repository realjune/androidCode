package com.android.abt;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DiscoveryActivity extends ListActivity {
	private Button info_btn;
	private boolean isDiscovery;
	private Handler _handler = new Handler();
	/* ȡ��Ĭ�ϵ����������� */
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	/* �����洢�������������豸 */
	private List<BluetoothDevice> _devices = new ArrayList<BluetoothDevice>();
	/* �Ƿ�������� */
	private volatile boolean _discoveryFinished;
//	private Runnable _discoveryWorkder = new Runnable() {
//		public void run() {
//			/* ��ʼ���� */
//			_bluetooth.startDiscovery();
//			while(!_discoveryFinished) {
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//	};
	/**
	 * ������ �����������豸���ʱ����
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			if(BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
			/* ��intent��ȡ������������� */
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			/* �������ӵ��б��� */
			_devices.add(device);
			/* ��ʾ�б� */
			showDevices();
			}else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
				if(!isDiscovery){
				/* ж��ע��Ľ����� */
				unregisterReceiver(mReceiver);
				_discoveryFinished = true;
				}else{
					_bluetooth.startDiscovery();
					_devices.clear();showDevices();
				}
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.discovery);
		/* �������������û�д򿪣����� */
		if (!_bluetooth.isEnabled()) {

			Toast.makeText(this, "����������", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		/* ע������� */
		IntentFilter discoveryFilter = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, discoveryFilter);
		IntentFilter foundFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, foundFilter);
		/* ��ʾһ���Ի���,�������������豸 */
//		SamplesUtils.indeterminate(DiscoveryActivity.this, _handler,
//				"Scanning...", _discoveryWorkder, new OnDismissListener() {
//					public void onDismiss(DialogInterface dialog) {
//
//						for (; _bluetooth.isDiscovering();) {
//
//							_bluetooth.cancelDiscovery();
//						}
//
//						_discoveryFinished = true;
//					}
//				}, false);
		

		info_btn=(Button) findViewById(R.id.info_btn);
		info_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(_bluetooth.isDiscovering()){
					_devices.clear();
					isDiscovery=false;
					_bluetooth.cancelDiscovery();
					info_btn.setText("��ʼ����");showDevices();
				}else{
					isDiscovery=true;
					_bluetooth.startDiscovery();
					info_btn.setText("ֹͣ����");
				}
			}
		});
	}

	/* ��ʾ�б� */
	protected void showDevices() {
		List<String> list = new ArrayList<String>();
		for (int i = 0, size = _devices.size(); i < size; ++i) {
			StringBuilder b = new StringBuilder();
			BluetoothDevice d = _devices.get(i);
			b.append(d.getAddress());
			b.append('\n');
			String name=d.getName();
			if(name!=null&&name.length()>0&&!name.equals("null")){
				b.append(d.getName());
			}
			String s = b.toString();
			list.add(s);
		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		_handler.post(new Runnable() {
			public void run() {

				setListAdapter(adapter);
			}
		});
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent result = new Intent();
		result.putExtra(BluetoothDevice.EXTRA_DEVICE, _devices.get(position));
		setResult(RESULT_OK, result);
		finish();
	}
}
