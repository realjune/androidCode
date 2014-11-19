package com.terry.device;

//http://labs.chinamobile.com/mblog/532767_73990

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class deviceActivity extends Activity {
    static final int RESULT_ENABLE = 1;
    DevicePolicyManager mDPM;
    ActivityManager mAM;
    ComponentName mDeviceComponentName;
    Button enableAdmin, disableAdmin, force_lock, btn_time_out, reset;
    EditText et,word_1_et,word_2_et;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        mDeviceComponentName = new ComponentName(deviceActivity.this,
                deviceAdminReceiver.class);
        setContentView(R.layout.main);

        findView();

        init();

    }

    void findView() {
        enableAdmin = (Button) findViewById(R.id.enable_admin);
        disableAdmin = (Button) findViewById(R.id.disable_admin);
        force_lock = (Button) findViewById(R.id.force_lock);
        btn_time_out = (Button) findViewById(R.id.time_out);
        et = (EditText) findViewById(R.id.et_time_out);
        reset = (Button) findViewById(R.id.reset);
        word_1_et=(EditText) findViewById(R.id.new_word_1);
        word_2_et=(EditText) findViewById(R.id.new_word_2);
    }

    void init() {
        enableAdmin.setOnClickListener(new enableAdminClickEvent());
        disableAdmin.setOnClickListener(new disableAdminClickEvent());
        force_lock.setOnClickListener(new force_lock());
        btn_time_out.setOnClickListener(new timeoutClickEvent());
        reset.setOnClickListener(new resetClickEvent());
        ((Button)findViewById(R.id.clear_word)).setOnClickListener(clickListener);
        ((Button)findViewById(R.id.auto_clear_word)).setOnClickListener(clickListener);
        ((Button)findViewById(R.id.change_word)).setOnClickListener(clickListener);
    }

    void updateButtonState() {
        boolean active = mDPM.isAdminActive(mDeviceComponentName);
        if (active) {
            enableAdmin.setEnabled(false);
            disableAdmin.setEnabled(true);
            force_lock.setEnabled(true);
            btn_time_out.setEnabled(true);
            reset.setEnabled(true);
        } else {
            enableAdmin.setEnabled(true);
            disableAdmin.setEnabled(false);
            force_lock.setEnabled(false);
            btn_time_out.setEnabled(false);
            reset.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        updateButtonState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
        case RESULT_ENABLE:
            if (resultCode == Activity.RESULT_OK) {
                Log.v("DeviceEnable", "deviceAdmin:enable");
            } else {
                Log.v("DeviceEnable", "deviceAdmin:disable");
            }
            break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * �豸������õĵ���¼�
     * 
     * @author terry
     * 
     */
    class enableAdminClickEvent implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    mDeviceComponentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    R.string.extr_add);
            startActivityForResult(intent, RESULT_ENABLE);
        }
    }

    /**
     * �豸�������õĵ���¼�
     * 
     * @author terry
     * 
     */
    class disableAdminClickEvent implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mDPM.removeActiveAdmin(mDeviceComponentName);
            updateButtonState();
        }

    }
//����������������ģ����������������������ֻ��ͣ����ʼģ����������Ҫ������״̬����Ļ����䰵���������룺
    /**
     * ����
     * 
     * @author terry
     * 
     */
    class force_lock implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mAM.isUserAMonkey()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        deviceActivity.this);
                builder.setMessage(R.string.not_manager);
                builder.setPositiveButton("I admit defeat", null);
                builder.show();
                return;
            }
            boolean active = mDPM.isAdminActive(mDeviceComponentName);
            if (active) {
                mDPM.lockNow();
            }
        }
    }
//��Ļ��������Ӧʱ���ƹ�䰵Ч����
    /**
     * ��Ļ�Զ��䰵
     * 
     * @author terry
     * 
     */
    class timeoutClickEvent implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mAM.isUserAMonkey()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        deviceActivity.this);
                builder.setMessage(R.string.not_manager);
                builder.setPositiveButton("I admit defeat", null);
                builder.show();
                return;
            }
            boolean active = mDPM.isAdminActive(mDeviceComponentName);
            if (active) {
                long timeout = 1000L * Long.parseLong(et.getText().toString());
                mDPM.setMaximumTimeToLock(mDeviceComponentName, timeout);
            }
        }
    }

    
    //������ģ�������ָ���������������ݺ󣬽��޷����¿��������������������ӣ����������û������ģ����Ե�ʱ�����С�ģ����⽫���������������ָ��������ô��룺
    /**
     * �ָ���������
     * 
     * @author terry
     * 
     */
    class resetClickEvent implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mAM.isUserAMonkey()) {
                // Don't trust monkeys to do the right thing!
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        deviceActivity.this);
                builder
                        .setMessage("You can't wipe my data because you are a monkey!");
                builder.setPositiveButton("I admit defeat", null);
                builder.show();
                return;
            }

//            AlertDialog.Builder builder = new Builder(deviceActivity.this);
//            builder.setMessage("���������ݣ���ȷ���˲�����");
//            builder.setPositiveButton(android.R.string.ok,
//                    new DialogInterface.OnClickListener() {

//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
//                            AlertDialog.Builder aler = new AlertDialog.Builder(
//                                    deviceActivity.this);
//                            aler.setMessage("ɾ�����ݺ�ϵͳ������������.ȷ����");
//                            aler.setPositiveButton(android.R.string.ok,
//                                    new DialogInterface.OnClickListener() {

//                                        @Override
//                                        public void onClick(
//                                                DialogInterface dialog,
//                                                int which) {
                                            // TODO Auto-generated method stub
                                            boolean active = mDPM
                                                    .isAdminActive(mDeviceComponentName);
                                            if (active) {
                                                mDPM.wipeData(0);
                                            }
//                                        }
//                                    });
//                            aler
//                                    .setNeutralButton(android.R.string.cancel,
//                                            null);
//                            aler.show();
                        }
//                    });
//            builder.setNeutralButton(android.R.string.cancel, null);
//            builder.show();
        }
//    }
    
    private OnClickListener clickListener=new OnClickListener(){
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.clear_word:
				mDPM.resetPassword("", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
				break;
			case R.id.auto_clear_word:
                new Thread(){
                	public void run(){
                		try{ 
                			sleep(60000);
                			 mDPM.resetPassword("", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                		}catch(Exception e){
                			
                		}
                	}
                }.start();
				break;
			case R.id.change_word:
				if(word_1_et.getText().toString().trim().equals(word_2_et.getText().toString().trim()))
					mDPM.resetPassword(word_1_et.getText().toString().trim(), DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
				else{
					  AlertDialog.Builder builder = new AlertDialog.Builder(
		                        deviceActivity.this);
		                builder.setMessage(R.string.word_no_same);
		                builder.setPositiveButton(R.string.re_input, null);
		                builder.show();
		                word_1_et.setText("");
		                word_2_et.setText("");
				}
				break;
			}
		}
    };

}

