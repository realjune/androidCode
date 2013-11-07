package com.test;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.sampletest.MainActivity;
import com.example.sampletest.R;

public class SampleTest extends InstrumentationTestCase {
    private MainActivity sample = null;
    private Button button = null;
    private TextView text = null;
 
    /*
     * ��ʼ����
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()  {
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClassName("com.example.sampletest", MainActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sample = (MainActivity) getInstrumentation().startActivitySync(intent);
        text = (TextView) sample.findViewById(R.id.textView2);
        button = (Button) sample.findViewById(R.id.button1);
    }
 
    /*
     * ������������Դ����
     * @see android.test.InstrumentationTestCase#tearDown()
     */
    @Override
    protected void tearDown()  {
        sample.finish();
        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /*
     * ����ܲ���
     */
    public void testActivity() throws Exception {
        Log.v("testActivity", "test the Activity");
        SystemClock.sleep(1500);
        getInstrumentation().runOnMainSync(new PerformClick(button));
        SystemClock.sleep(3000);
        assertEquals("Hello Android", text.getText().toString());
    }
 
    /*
     * ģ�ⰴť����Ľӿ�
     */
    private class PerformClick implements Runnable {
        Button btn;
        public PerformClick(Button button) {
            btn = button;
        }
 
        public void run() {
            btn.performClick();
        }
    }
 
    /*
     * �������еķ���
     */
    public void testAdd() throws Exception{
        String tag = "testAdd";
        Log.v(tag, "test the method");
        int test = sample.add(1, 1);
        assertEquals(2, test);
    }

}
