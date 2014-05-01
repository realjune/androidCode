package com.image.indicator.activity;

import com.image.indicator.R;
import com.image.indicator.utility.DimensionUtility;
import com.image.indicator.utility.ImageAnimatioin;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * Androidʵ�־ֲ�ͼƬ����ָ��Ч��
 * @Description: ʵ�����¹��ܣ�
 * 1����������ͼƬ��������������
 * 2����ָ����
 * 3����������������ͼƬ��������ҳ�棬�����ͼ�����ݲ����� 
 * 4�����������ſͻ��˵Ĺ���

 * @File: MainActivity.java

 * @Package com.image.indicator

 * @Author Hanyonglu

 * @Date 2012-6-17 ����11:28:43

 * @Version V1.0
 */
public class MainActivity extends ActivityGroup {
	// ѡ�е�������Ŀ
	private TextView tvSelectedItem = null;
	// ͷ��������Ŀ��Layout
	private RelativeLayout rlHeader = null;
	// �м����������Layout
	private RelativeLayout rlNewsMain = null;
	private LayoutParams params = null;
	// ���ŷ���
	private TextView tvNewsItem = null;
	private TextView tvInfoItem = null;
	private TextView tvBlogItem = null;
	private TextView tvMagezineItem = null;
	private TextView tvDomainItem = null;
	private TextView tvMoreItem = null;
	
	// ���ŷ�����ÿ������Ŀ��
	private int itemWidth = 0;
	// ��Ŀ�����ƶ���ʼλ��
	private int startX = 0;
	
	private Intent intent = null;
	// ������������
	private View vNewsMain = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ��ʼ���ؼ�
        initeViews();
    }
    
    /**
     * ��ʼ���ؼ�
     */
    private void initeViews(){
    	tvNewsItem = (TextView) findViewById(R.id.tv_title_news);
    	tvInfoItem = (TextView) findViewById(R.id.tv_title_info);
    	tvBlogItem = (TextView) findViewById(R.id.tv_title_blog);
    	tvMagezineItem = (TextView) findViewById(R.id.tv_title_magazine);
    	tvDomainItem = (TextView) findViewById(R.id.tv_title_domain);
    	tvMoreItem = (TextView) findViewById(R.id.tv_title_more);
    	
    	tvNewsItem.setOnClickListener(new ItemOnclickListener());
    	tvInfoItem.setOnClickListener(new ItemOnclickListener());
    	tvBlogItem.setOnClickListener(new ItemOnclickListener());
    	tvMagezineItem.setOnClickListener(new ItemOnclickListener());
    	tvDomainItem.setOnClickListener(new ItemOnclickListener());
    	tvMoreItem.setOnClickListener(new ItemOnclickListener());

    	// ����ѡ����Ŀ����
    	tvSelectedItem = new TextView(this);
    	tvSelectedItem.setText(R.string.title_news_category_tops);
    	tvSelectedItem.setTextColor(Color.WHITE);
    	tvSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
    	tvSelectedItem.setGravity(Gravity.CENTER);
    	tvSelectedItem.setWidth((getScreenWidth() - DimensionUtility.dip2px(this, 20)) / 6);
    	tvSelectedItem.setBackgroundResource(R.drawable.slidebar);
    	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
    			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
    	
    	rlHeader = (RelativeLayout) findViewById(R.id.layout_title_bar);
    	rlHeader.addView(tvSelectedItem, param);
    	
    	// ����ͷ����������
    	intent = new Intent(MainActivity.this, TopicNews.class);
    	vNewsMain = getLocalActivityManager().startActivity(
    			"TopicNews", intent).getDecorView();
    	params = new LayoutParams(
    			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    	rlNewsMain = (RelativeLayout) findViewById(R.id.layout_news_main);
    	rlNewsMain.addView(vNewsMain, params);
    }
    
    /**
     * ��ȡ��Ļ�Ŀ��
     * @return
     */
    private int getScreenWidth(){
    	WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		return screenWidth;
    }
    
    // ���ŷ����¼�������
    private class ItemOnclickListener implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		itemWidth = findViewById(R.id.layout).getWidth();
    		
    		switch (v.getId()) {
			case R.id.tv_title_news:
				ImageAnimatioin.SetImageSlide(tvSelectedItem, startX, 0, 0, 0);
				startX = 0;
				tvSelectedItem.setText(R.string.title_news_category_tops);
				
				// ��ʾͷ����Ϣ
				intent.setClass(MainActivity.this, TopicNews.class);
				vNewsMain = getLocalActivityManager().startActivity(
		    			"TopicNews", intent).getDecorView();
				break;
			case R.id.tv_title_info:
				ImageAnimatioin.SetImageSlide(tvSelectedItem, startX, itemWidth, 0, 0);
				startX = itemWidth;
				tvSelectedItem.setText(R.string.title_news_category_info);
				
				// ��ʾ��Ѷ��Ϣ
				intent.setClass(MainActivity.this, InfoNews.class);
				vNewsMain = getLocalActivityManager().startActivity(
		    			"InfoNews", intent).getDecorView();
				break;
			case R.id.tv_title_blog:
				ImageAnimatioin.SetImageSlide(tvSelectedItem, startX, itemWidth * 2, 0, 0);
				startX = itemWidth * 2;
				tvSelectedItem.setText(R.string.title_news_category_blog);
				
				// ��ʾ������Ϣ
				intent.setClass(MainActivity.this, BlogNews.class);
				vNewsMain = getLocalActivityManager().startActivity(
		    			"BlogNews", intent).getDecorView();
				break;
			case R.id.tv_title_magazine:
				ImageAnimatioin.SetImageSlide(tvSelectedItem, startX, itemWidth * 3, 0, 0);
				startX = itemWidth * 3;
				tvSelectedItem.setText(R.string.title_news_category_magazine);
				
				// ��ʾ��־��Ϣ
				intent.setClass(MainActivity.this, MagazineNews.class);
				vNewsMain = getLocalActivityManager().startActivity(
		    			"MagazineNews", intent).getDecorView();
				break;
			case R.id.tv_title_domain:
				ImageAnimatioin.SetImageSlide(tvSelectedItem, startX, itemWidth * 4, 0, 0);
				startX = itemWidth * 4;
				tvSelectedItem.setText(R.string.title_news_category_domain);
				// ��ʾҵ����Ϣ
				intent.setClass(MainActivity.this, DomainNews.class);
				vNewsMain = getLocalActivityManager().startActivity(
		    			"DomainNews", intent).getDecorView();
				break;
			case R.id.tv_title_more:
				ImageAnimatioin.SetImageSlide(tvSelectedItem, startX, itemWidth * 5, 0, 0);
				startX = itemWidth * 5;
				tvSelectedItem.setText(R.string.title_news_category_more);
				
				// ��ʾ������Ϣ
				intent.setClass(MainActivity.this, MoreNews.class);
				vNewsMain = getLocalActivityManager().startActivity(
		    			"MoreNews", intent).getDecorView();
				break;
			default:
				break;
			}
    		
    		// ����Layout�е���������
    		rlNewsMain.removeAllViews();
    		rlNewsMain.addView(vNewsMain, params);
    	}
    }
}