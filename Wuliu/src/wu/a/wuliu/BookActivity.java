package wu.a.wuliu;

import wu.a.activity.TitleFooterActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import baidumapsdk.demo.R;

import com.droid.Activity01;
import com.droid.City;

public class BookActivity extends TitleFooterActivity implements OnClickListener, OnCheckedChangeListener {
	private BookMain bookMain;
	private BookManager bookManager;
	private SaleManager saleManager;
	
	private RadioGroup footer_menu;
	
	public static void start(Activity activity){
		activity.startActivity(new Intent(activity,BookActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		footer_menu=(RadioGroup) findViewById(R.id.footer_menu);
		footer_menu.setOnCheckedChangeListener(this);
		bookMain=new BookMain(this);
		bookManager=new BookManager(this);
		saleManager=new SaleManager(this);
		footer_menu.check(R.id.menu_book);
//		setTitleText("下单");
//		setTitleLeftButtonText(R.string.select_city);
	}
	
	@Override
	public void onTitleLeftButtonClick(View v) {
//		super.onTitleLeftButtonClick(v);
		Activity01.start(this, 12);
	}
	
	@Override
	public void onTitleRightButtonClick(View v) {
		FeedBackActivity.start(this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==12){
				City city=(City) data.getSerializableExtra("city");
				setTitleLeftButtonText(city.name);
			}
		}
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		}
		// TODO Auto-generated method stub
		
	}
	
	private int status=-1;

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.menu_book:
			if(status!=0){
			setTitleRightButtonText(R.string.feedback);
			setContentLayout(bookMain.getView());
//			setMenuStatus(this,MENU_BOOK, false);
			setTitleImage(R.drawable.title_jlbhuoyun);
			status=0;
			}
			break;
		case R.id.menu_book_manager:
			if(status!=1){
				setTitleRightButton(View.GONE);
				setContentLayout(bookManager.getView());
				setTitleText(R.string.history_book_list);
				status=1;
			}
			break;
		case R.id.menu_activity:
			if(status!=2){
			setTitleRightButton(View.GONE);
			setContentLayout(saleManager.getView());
			setTitleText(R.string.history_book_list);
			status=2;
		}
			break;
		case R.id.menu_user_info:
			break;
		}
		
	}

}
