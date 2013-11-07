package com.android.wjx.customview.activity;

import com.android.wjx.customview.ShadeDrawable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**自定义 Drawable测试 
 * @author junxu.wang
 *
 */
public class DrawableActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		init();
		setTitle("Drawble");
	}
	
	private void init(){
		ScrollView sl=new ScrollView(this);
		LinearLayout contentView=new LinearLayout(this);
		contentView.setOrientation(LinearLayout.VERTICAL);
		sl.addView(contentView);
		TextView tv=new TextView(this);
		tv.setText("TextView控件fa;ldkfjalksfjalskdfjaklsdfjaklsdfjkalsdfjaslkfdfjalksdfjalsfkjalskfjlasfjasl");
		Button btn=new Button(this);
		btn.setText("Button按钮TextView控件fa;ldkfjalksfjalskdfjaklsdfjaklsdfjkalsdfjaslkfdfjalksdfjalsfkjalskfjlasfjasl");
		EditText et=new EditText(this);
		et.setHint("inputText");
		EditText et1=new EditText(this);
		et1.setText("hello");
		
		contentView.addView(tv);
		contentView.addView(btn);
		contentView.addView(et);
		contentView.addView(et1);
		setContentView(sl);
		
		btn.setBackgroundDrawable(new ShadeDrawable(this));
		
		
	}

}
