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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DiscoveryActivity extends ListActivity {
	private static final String TAG = DiscoveryActivity.class.getSimpleName();
	private Button info_btn;
	private TextView msg_tv;
	private boolean isDiscovery;
	private Handler _handler = new Handler();
	/* ȡ��Ĭ�ϵ����������� */
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	/* �����洢�������������豸 */
	private List<BluetoothDevice> _devices = new ArrayList<BluetoothDevice>();
	private BtAdapter mBtAdapter;

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
				_devices.add(device);
				/* ��ʾ�б� */
				showDevices();
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent
					.getAction())) {
				if (Constant.DEBUG)
					Log.d(TAG, "finished!");
				if (!isDiscovery) {
					/* ж��ע��Ľ����� */
					unregisterReceiver(mReceiver);
					if (Constant.DEBUG)
						Log.d(TAG, "end discomver");
					// _discoveryFinished = true;
				} else {
					if (Constant.DEBUG)
						Log.d(TAG, "restart discover");
					_bluetooth.startDiscovery();
					_devices.clear();
					showDevices();
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
			if (Constant.DEBUG)
				Log.d(TAG, "bt is disable");
			finish();
			return;
		}
		btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		btIntentFilter
				.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		btIntentFilter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
		btIntentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
		btIntentFilter.addAction(BluetoothDevice.ACTION_UUID);

		mBtAdapter = new BtAdapter(this, _devices);
		info_btn = (Button) findViewById(R.id.info_btn);
		msg_tv=(TextView) findViewById(R.id.msg_tv);
		info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (_bluetooth.isDiscovering()) {
					_devices.clear();
					isDiscovery = false;
					if (Constant.DEBUG)
						Log.d(TAG, "to stop discovery");
					_bluetooth.cancelDiscovery();
					info_btn.setText("��ʼ����");
					msg_tv.setText("������ֹͣ");
					showDevices();
				} else {
					isDiscovery = true;
					if (Constant.DEBUG)
						Log.d(TAG, "to start discovery");
					registerReceiver(mReceiver, btIntentFilter);
					_bluetooth.startDiscovery();
					info_btn.setText("ֹͣ����");
					msg_tv.setText("��������������");
				}
			}
		});
	}

	/* ��ʾ�б� */
	protected void showDevices() {
		_handler.post(new Runnable() {
			public void run() {
				setListAdapter(mBtAdapter);
			}
		});
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent result = new Intent();
		result.putExtra(BluetoothDevice.EXTRA_DEVICE, _devices.get(position));
		setResult(RESULT_OK, result);
		finish();
	}

	class BtAdapter extends BaseAdapter {
		List<BluetoothDevice> datas;
		Context context;
		LayoutInflater lf;

		public BtAdapter(Context context, List<BluetoothDevice> data) {
			this.context = context;
			this.datas = data;
			lf = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return datas == null ? 0 : datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(context);
			}
			BluetoothDevice btd = datas.get(position);
			((TextView) convertView).setText(btd.getName() + "\n"
					+ btd.getAddress());
			return convertView;
		}

	}
}
