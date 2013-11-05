package com.act.sctc.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.act.sctc.R;

public class ScrollerMenu extends BaseManager implements OnCheckedChangeListener{
	public static final String TAG=ScrollerMenu.class.getSimpleName();
	//title
	private RadioGroup mRadioGroup;
	private ImageView mImageView;
	private float mCurrentCheckedRadioLeft;//��ǰ��ѡ�е�RadioButton�������ľ���
	private HorizontalScrollView mHorizontalScrollView;//�����ˮƽ�����ؼ�
	private RadioGroup.OnCheckedChangeListener onCheckedChangeListener;
	
    public ScrollerMenu(Context context,int layoutId) {
    	this(context,(ViewGroup)LayoutInflater.from(context).inflate(layoutId, null));
    }
    
    public ScrollerMenu(Context context,ViewGroup layout){
    	this.layout=layout;
    	iniController();
    	iniListener();
//    	mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft(mRadioGroup.getCheckedRadioButtonId());
    }
    
    private void moImg(float xs,float xe,float ys,float ye){
    	AnimationSet _AnimationSet = new AnimationSet(true);
		TranslateAnimation _TranslateAnimation;
		_TranslateAnimation = new TranslateAnimation(xs, xe, ys, ye);
		_AnimationSet.addAnimation(_TranslateAnimation);
		_AnimationSet.setFillBefore(true);
		_AnimationSet.setFillAfter(true);
		_AnimationSet.setDuration(100);
		mImageView.startAnimation(_AnimationSet);
    }
    
    /**
	 * RadioGroup���CheckedChanged����
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		
		float toX=getCurrentCheckedRadioLeft(checkedId);
//		if (checkedId == R.id.btn1) {
//			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, toX, 0f, 0f);
//			_AnimationSet.addAnimation(_TranslateAnimation);
//			_AnimationSet.setFillBefore(true);
//			_AnimationSet.setFillAfter(true);
//			_AnimationSet.setDuration(100);
//			
//			mImageView.startAnimation(_AnimationSet);//��ʼ������ɫ����ͼƬ�Ķ����л�
//		}else if (checkedId == R.id.btn2) {
//			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, toX, 0f, 0f);
//
//			_AnimationSet.addAnimation(_TranslateAnimation);
//			_AnimationSet.setFillBefore(true);
//			_AnimationSet.setFillAfter(true);
//			_AnimationSet.setDuration(100);
//
//			//mImageView.bringToFront();
//			mImageView.startAnimation(_AnimationSet);
//			
//		}else if (checkedId == R.id.btn3) {
//			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft,toX, 0f, 0f);
//			
//			_AnimationSet.addAnimation(_TranslateAnimation);
//			_AnimationSet.setFillBefore(true);
//			_AnimationSet.setFillAfter(true);
//			_AnimationSet.setDuration(100);
//			
//			//mImageView.bringToFront();
//			mImageView.startAnimation(_AnimationSet);
//			
//		}else if (checkedId == R.id.btn4) {
//			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, toX, 0f, 0f);
//			
//			_AnimationSet.addAnimation(_TranslateAnimation);
//			_AnimationSet.setFillBefore(true);
//			_AnimationSet.setFillAfter(true);
//			_AnimationSet.setDuration(100);
//			
//			//mImageView.bringToFront();
//			mImageView.startAnimation(_AnimationSet);
//		} else {
			
moImg(mCurrentCheckedRadioLeft, toX, 0f, 0f);
			// mImageView.bringToFront();
//		}

		mCurrentCheckedRadioLeft = toX;
//		mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft, 0);
		if(onCheckedChangeListener!=null){
			onCheckedChangeListener.onCheckedChanged(group, checkedId);
		}
	}
    
	/**
     * return radiomenu left px
     */
	private float getCurrentCheckedRadioLeft(int checkedId) {
		for(int i=mRadioGroup.getChildCount()-1;i>=0;i--){
			View v=mRadioGroup.getChildAt(i);
			if(((RadioButton)v).isChecked()){
				return v.getLeft();
			}
		}
		return 0;
	}

	public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener onCheckedChangeListener){
		this.onCheckedChangeListener=onCheckedChangeListener;//mRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
	}
	
	boolean hasMeasured=false;

	private void iniController() {
		mRadioGroup = (RadioGroup)layout.findViewById(R.id.radioGroup);
		mImageView = (ImageView)layout.findViewById(R.id.img1);
		mHorizontalScrollView = (HorizontalScrollView)layout.findViewById(R.id.horizontalScrollView);
		
        ViewTreeObserver vto = mHorizontalScrollView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            public boolean onPreDraw()
            {
                if (hasMeasured == false)
                {
                	Log.d(TAG," add on pre draw listener");
                	//获取到宽度和高度后，可用于计算                    
//                    int height = mHorizontalScrollView.getMeasuredHeight();
                	int width = mHorizontalScrollView.getMeasuredWidth()/mRadioGroup.getChildCount();
                    LayoutParams lp=mImageView.getLayoutParams();
                    lp.width=width;
                    mImageView.setLayoutParams(lp);
                    for(int i=mRadioGroup.getChildCount()-1;i>=0;i--){
                    	View childView=mRadioGroup.getChildAt(i);
                    	LayoutParams lp2= childView.getLayoutParams();
                    	lp2.width=width;
                    	childView.setLayoutParams(lp2);
                    	if(((RadioButton)childView).isChecked()){
                    		moImg(mCurrentCheckedRadioLeft, width*i, 0f, 0f);
                    		mCurrentCheckedRadioLeft=width*i;
                    	}
                    }
                    hasMeasured = true;
                }
                return true;
            }
        });
	}

	private void iniListener() {
		mRadioGroup.setOnCheckedChangeListener(this);
	}
	
	public void addView(View child) {
		hasMeasured=false;
        mRadioGroup.addView(child, -1);
    }
	public void addView(View child, int index) {
		hasMeasured=false;
		mRadioGroup.addView(child, index);
	}

	public View getLayout() {
		return layout;
	}

}
