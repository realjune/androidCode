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

public class MainActivity extends TitleFooterActivity implements OnClickListener {
	private View near_cars_msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.book_main);
		setMenuStatus(this,MENU_BOOK, false);
		setTitleText("下单");
		near_cars_msg=findViewById(R.id.near_cars_msg);
		near_cars_msg.setOnClickListener(this);
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
		case R.id.near_cars_msg:
			MapActivity.start(this);
			break;
		}
		// TODO Auto-generated method stub
		
	}

}
