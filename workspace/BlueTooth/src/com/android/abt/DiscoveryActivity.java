package com.android.abt;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DiscoveryActivity extends ListActivity {
	private static final String TAG = DiscoveryActivity.class.getSimpleName();
	private Button discovery_btn;
	private TextView msg_tv;
	private boolean isReDiscovery;
	/* ȡ��Ĭ�ϵ����������� */
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	/* �����洢�������������豸 */
	private List<BluetoothDevice> btList = new ArrayList<BluetoothDevice>();
	private BtListAdapter mBtAdapter;

	/* ע������� */
	IntentFilter btIntentFilter = new IntentFilter();
	/**
	 * ������ �����������豸���ʱ����
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			if (Constant.DEBUG){
				Log.d(TAG, "recever <-" + intent.getAction());
			}
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
				/* ��intent��ȡ������������� */
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (Constant.DEBUG)
					Log.d(TAG,
							"find:" + device.getAddress() + "--"
									+ device.getBondState() + "--"
									+ device.getName() + "--"
									+ device.toString());
				/* �������ӵ��б��� */
				btList.add(device);
				/* ��ʾ�б� */
				mBtAdapter.notifyDataSetChanged();
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent
					.getAction())) {
				if (!isReDiscovery) {
					if (Constant.DEBUG)
						Log.d(TAG, "end discomver");
				} else {
					reStartDiscovery();
				}
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		/* �������������û�д򿪣����� */
		if (!bluetoothAdapter.isEnabled()) {
			Toast.makeText(this, "����������", Toast.LENGTH_LONG).show();
			if (Constant.DEBUG)
				Log.d(TAG, "bt is disable");
			finish();
			return;
		}
		setContentView(R.layout.discovery);
		btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		btIntentFilter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
		btIntentFilter.addAction(BluetoothDevice.ACTION_UUID);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		btIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(mReceiver, btIntentFilter);

		mBtAdapter = new BtListAdapter(this, btList);
		this.setListAdapter(mBtAdapter);
		discovery_btn = (Button) findViewById(R.id.discovery_btn);
		msg_tv=(TextView) findViewById(R.id.msg_tv);
		discovery_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bluetoothAdapter.isDiscovering()) {
					cancelDiscovery();
				} else {
					startDiscovery();
				}
			}
		});
	}
	
	protected void startDiscovery(){
		btList.clear();
		mBtAdapter.notifyDataSetChanged();
		isReDiscovery = true;
		if (Constant.DEBUG)
			Log.d(TAG, "to start discovery");
		bluetoothAdapter.startDiscovery();
		discovery_btn.setText("ֹͣ����");
		msg_tv.setText("�����С�����");
	}
	
	protected void reStartDiscovery(){
		if (Constant.DEBUG)
			Log.d(TAG, "restart discover");
		bluetoothAdapter.startDiscovery();
//		btList.clear();
//		/* ��ʾ�б� */
//		mBtAdapter.notifyDataSetChanged();
	}
	
	protected void cancelDiscovery(){
		isReDiscovery = false;
		if (Constant.DEBUG)
			Log.d(TAG, "to stop discovery");
		bluetoothAdapter.cancelDiscovery();
		discovery_btn.setText("��������");
		msg_tv.setText("");
	}

	@Override
	protected void onDestroy() {
		/* ж��ע��Ľ����� */
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		BluetoothDevice btd=btList.get(position);
		if(Constant.DEBUG){
			Log.d(TAG,"onSelect:"+position+" "+btd.getAddress()+" "+btd.getName());
		}
		Intent result = new Intent();
		result.putExtra(BluetoothDevice.EXTRA_DEVICE, btList.get(position));
		setResult(RESULT_OK, result);
		finish();
	}
}
