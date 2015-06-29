package wu.a.wuliu;

import com.droid.Activity01;
import com.droid.City;

import wu.a.activity.TitleFooterActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import baidumapsdk.demo.R;

public class BookActivity extends TitleFooterActivity implements OnClickListener {
	private BookMain bookMain;
	
	public static void start(Activity activity){
		activity.startActivity(new Intent(activity,BookActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bookMain=new BookMain(this);
		setContentLayout(bookMain.getView());
		setMenuStatus(this,MENU_BOOK, false);
//		setTitleText("下单");
//		setTitleLeftButtonText(R.string.select_city);
	}
	
	@Override
	public void onTitleLeftButtonClick(View v) {
//		super.onTitleLeftButtonClick(v);
		Activity01.start(this, 12);
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

}
