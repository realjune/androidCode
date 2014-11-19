package com.android.accenture.webclip;

import com.android.accenture.webclip1.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author abc
 *
 */
public class Main extends Activity implements OnClickListener {
	private static final String EXTRA_SHORTCUT_DUPLICATE = "duplicate";
	private TextView meg_tv;
	private EditText name_et;
	private EditText dress_et;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        meg_tv=(TextView) findViewById(R.id.meg_tv);
        name_et=(EditText) findViewById(R.id.name_et);
        dress_et=(EditText) findViewById(R.id.url_et);
        ((Button)findViewById(R.id.add_btn)).setOnClickListener(this);
        ((Button)findViewById(R.id.del_btn)).setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
	    /*ͨ���ı������icon id�����Բ�ͬ��ʽ��iconͼ��*/
		switch(v.getId()){
		case R.id.add_btn:
			addUrlShort(name_et.getText().toString().trim(), dress_et.getText().toString().trim(),R.drawable.icon);
			break;
		case R.id.del_btn:
			delUrlShort(name_et.getText().toString().trim(), dress_et.getText().toString().trim(),R.drawable.icon);
			break;
		}
		//finish();
	}
	static public Bitmap rotateBmp(Bitmap bmp, int degree){
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		bmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
		return bmp;
	}
	static public Bitmap scaleBmp(Bitmap bmp, int w, int h){
		return Bitmap.createScaledBitmap(bmp, w, h, false);
	}
	/*�ı�shortcut��ͼ��ķ�ʽһ������ͨ��BitmapFactory.decodeResource���԰������ʽ����ԴͼƬת��ΪBitmap�����������ԶԴ�Bitmap��������Ч����
     * ����Ҫ��ԭʼ��̬ͼƬ�����δ���ʱ����ʹ�����ַ�ʽ */
    public void assembleIntentIcon_1(Intent installShortCut, int iconId) {
        Bitmap bmpIcon = BitmapFactory.decodeResource(this.getResources(), iconId);
        bmpIcon = scaleBmp(bmpIcon, 120, 150);
        installShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmpIcon);
    }
    
    /*�ı�shortcut��ͼ��ķ�ʽ����ֱ�Ӱ���Դid������*/
    public void assembleIntentIcon_2(Intent installShortCut, int iconId) {
      Parcelable icon = Intent.ShortcutIconResource.fromContext(this,iconId);
      installShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
    }
	/**��ӿ�ݷ�ʽ*/
	public void addUrlShort(String name,String url,int iconId){
		Uri myBlogUri = Uri.parse(url);
		Intent urlIntent= new Intent(Intent.ACTION_VIEW, myBlogUri);
		
		Intent installShortCut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		installShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		installShortCut.putExtra(EXTRA_SHORTCUT_DUPLICATE, false);
		
		assembleIntentIcon_2(installShortCut, iconId);
		installShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,urlIntent);
		sendBroadcast(installShortCut);
	}
	/**ɾ����ݷ�ʽ*/
	public void delUrlShort(String name,String url,int iconId){
		Uri myBlogUri = Uri.parse(url);
		Intent urlIntent= new Intent(Intent.ACTION_VIEW, myBlogUri);
		
		Intent installShortCut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		installShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		installShortCut.putExtra(EXTRA_SHORTCUT_DUPLICATE, false);
		Parcelable icon = Intent.ShortcutIconResource.fromContext(this,iconId);
		installShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		installShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,urlIntent);
		sendBroadcast(installShortCut);
	}
}