package com.act.sctc.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.act.sctc.BaseActivity;
import com.act.sctc.R;
import com.act.sctc.been.PhoneColor;

public class ProductPhoneActivity extends BaseActivity implements OnCheckedChangeListener, OnClickListener{
	private ScrollerMenu mScrollerMenu;
	private PhoneColorManager mPhoneColorManager;
			//title
//			private RadioGroup mRadioGroup;
			private RadioButton mRadioButton1;
			private RadioButton mRadioButton2;
			private RadioButton mRadioButton3;
			private RadioButton mRadioButton4;
			//title
			private ImageButton home_iv;
			private ImageButton customer_info_ib;
			private ImageButton shopping_cart_ib;
			
			private List<PhoneColor>colors;
			private int currentColor;
			private String phoneNo;
			
			
//			private ImageView mImageView;
//			private float mCurrentCheckedRadioLeft;//��ǰ��ѡ�е�RadioButton�������ľ���
			private HorizontalScrollView mHorizontalScrollView;//�����ˮƽ�����ؼ�
			//Content
			private ViewPager mViewPager;	//�·��Ŀɺ����϶��Ŀؼ�
			private ArrayList<View> mViews;//��������·�������layout(layout_1,layout_2,layout_3)
			
			private GridView colors_gv;
			
			private ImageButton recommend_btn;
			private LinearLayout recommend_layout;
			private Button select_setmeal_btn;
			
		    @Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
				mScrollerMenu=new ScrollerMenu(this, R.layout.phone_info_page);
				colors=new ArrayList<PhoneColor>();
				for(int i=0;i<10;i++){
					PhoneColor pc=new PhoneColor();
					pc.setColor(0xffff0000+0xff*i*16);
					pc.setName("color "+i);
					colors.add(pc);
				}
				setContentView(mScrollerMenu.getLayout());
		        iniController();
		        iniListener();
		        iniVariable();
		        
		        mRadioButton1.setChecked(true);
		        mViewPager.setCurrentItem(0);
//		        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft(mRadioGroup.getCheckedRadioButtonId());
		    }
		    
		    private void iniVariable() {
				// TODO Auto-generated method stub
		    	mViews = new ArrayList<View>();
		    	mViews.add(mPhoneColorManager.getLayout());
		    	mViews.add(getLayoutInflater().inflate(R.layout.phone_color_page, null));
		    	mViews.add(getLayoutInflater().inflate(R.layout.phone_color_page, null));
		    	mViews.add(getLayoutInflater().inflate(R.layout.phone_color_page, null));
		    	
		    	mViewPager.setAdapter(new MyPagerAdapter());//����ViewPager��������
		    	
		    	colors_gv.setAdapter(new MyColorAdapter());
			}
		    
		    /**
			 * RadioGroup���CheckedChanged����
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.btn1) {
					mViewPager.setCurrentItem(0);//���·�ViewPager���������HorizontalScrollView�л�
				}else if (checkedId == R.id.btn2) {
					mViewPager.setCurrentItem(1);
				}else if (checkedId == R.id.btn3) {
					mViewPager.setCurrentItem(2);
				}else if (checkedId == R.id.btn4) {
					mViewPager.setCurrentItem(3);
				}
			}
		    
			private void iniListener() {
				mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
				mScrollerMenu.setOnCheckedChangeListener(this);
				recommend_btn.setOnClickListener(this);
				select_setmeal_btn.setOnClickListener(this);
			}
			
			boolean hasMeasured=false;

			private void iniController() {
				mRadioButton1 = (RadioButton)findViewById(R.id.btn1);
				mRadioButton2 = (RadioButton)findViewById(R.id.btn2);
				mRadioButton3 = (RadioButton)findViewById(R.id.btn3);
				mRadioButton4 = (RadioButton)findViewById(R.id.btn4);
				
				mViewPager = (ViewPager)findViewById(R.id.pager);
				
				mPhoneColorManager=PhoneColorManager.getPhoneColorManager(this);
				
				colors_gv=(GridView) findViewById(R.id.colors_gv);
				recommend_btn=(ImageButton) findViewById(R.id.recommend_btn);
				recommend_layout=(LinearLayout) findViewById(R.id.recommend_layout);
				recommend_layout.setVisibility(View.GONE);
				select_setmeal_btn=(Button) findViewById(R.id.select_setmeal_btn);
			}
			
			private class MyColorAdapter extends BaseAdapter implements OnClickListener{
				int curSelected=-1;
				View curSelectView=null;

				@Override
				public int getCount() {
					return colors==null?0:colors.size();
				}

				@Override
				public Object getItem(int position) {
					return colors==null?null:colors.get(position);
				}

				@Override
				public long getItemId(int position) {
					return position;
				}

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					PhoneColor pcolor=colors.get(position);
					if(convertView==null){
						convertView=LayoutInflater.from(ProductPhoneActivity.this).inflate(R.layout.phone_color_btn_model, null);
					}
					TextView tv=(TextView) convertView.findViewById(R.id.name_color_tv);
					tv.setText(pcolor.getName());
					LinearLayout colorll=(LinearLayout) convertView.findViewById(R.id.bottom_color);
					colorll.setBackgroundColor(pcolor.getColor());
					convertView.setOnClickListener(this);
					convertView.setTag((Integer)position);

					return convertView;
				}

				@Override
				public void onClick(View v) {
					Integer p=(Integer) v.getTag();
					if(curSelectView!=null)
						curSelectView.setBackgroundColor(0);
					if(curSelected==p.intValue()){
						curSelected=-1;
					}else{
						curSelected=p;
						v.setBackgroundColor(0xff78CCEE);
						curSelectView=v;
					}
				}
				
			}

			/**
			 * ViewPager��������
			 */
			private class MyPagerAdapter extends PagerAdapter{

				@Override
				public void destroyItem(View v, int position, Object obj) {
					// TODO Auto-generated method stub
					((ViewPager)v).removeView(mViews.get(position));
				}

				@Override
				public void finishUpdate(View arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return mViews.size();
				}

				@Override
				public Object instantiateItem(View v, int position) {
					((ViewPager)v).addView(mViews.get(position));
					return mViews.get(position);
				}

				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					// TODO Auto-generated method stub
					return arg0 == arg1;
				}

				@Override
				public void restoreState(Parcelable arg0, ClassLoader arg1) {
					// TODO Auto-generated method stub
				}

				@Override
				public Parcelable saveState() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void startUpdate(View arg0) {
					// TODO Auto-generated method stub
				}
				
			}
			/**
			 * ViewPager��PageChangeListener(ҳ��ı�ļ�����)
			 */
			@SuppressLint("NewApi")
			private class MyPagerOnPageChangeListener implements OnPageChangeListener{

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub			
				}
				/**
				 * ����ViewPager��ʱ��,���Ϸ���HorizontalScrollView�Զ��л�
				 */
				@Override
				public void onPageSelected(int position) {
					// TODO Auto-generated method stub
					Log.i("zj", "position="+position);
					
					if (position == 0) {
						mRadioButton1.performClick();
					}else if (position == 1) {
						mRadioButton2.performClick();
					}else if (position == 2) {
						mRadioButton3.performClick();
//						RadioButton r5=(RadioButton) ProductPhone.this.getLayoutInflater().inflate(R.layout.scrollmenu_btn, null);
//						r5.setText("Im new");
//						r5.setId(100010);
//						r5.setBackgroundColor(0xffff00ff);
//						
//						mScrollerMenu.addView(r5);
					}else if (position == 3) {
						mRadioButton4.performClick();
					}
				}
			}
			
			@Override
			public void onClick(View v) {
				if(v==recommend_btn){
					if(isShow){
						hideRecommend();
					}else{
						showRecommend();
					}
				}else if(v==select_setmeal_btn){
					Intent i=new Intent(this, SelectSetMealActivity.class);
					this.startActivity(i);
				}
			}
			boolean isShow=false;
			private void showRecommend(){
				if(!isShow){
					recommend_layout.setVisibility(View.VISIBLE);
		    	AnimationSet _AnimationSet = new AnimationSet(true);
				TranslateAnimation _TranslateAnimation;
				_TranslateAnimation = new TranslateAnimation(- recommend_layout.getWidth(),0, 0f, 0f);
				_AnimationSet.addAnimation(_TranslateAnimation);
				_AnimationSet.setFillBefore(true);
				_AnimationSet.setFillAfter(true);
				_AnimationSet.setDuration(100);
				recommend_layout.startAnimation(_AnimationSet);
				isShow=!isShow;
				}
		    }
			private void hideRecommend(){
				if(isShow){
		    	AnimationSet _AnimationSet = new AnimationSet(true);
				TranslateAnimation _TranslateAnimation;
				_TranslateAnimation = new TranslateAnimation(recommend_layout.getLeft(), -recommend_layout.getWidth(), 0f, 0f);
				_AnimationSet.addAnimation(_TranslateAnimation);
				_AnimationSet.setFillBefore(true);
				_AnimationSet.setFillAfter(true);
				_AnimationSet.setDuration(100);
				recommend_layout.startAnimation(_AnimationSet);
				isShow=!isShow;
				}
		    }

}
