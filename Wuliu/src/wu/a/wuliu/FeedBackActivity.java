package wu.a.wuliu;

import wu.a.activity.ProgressDialogUtils;
import wu.a.activity.TitleFooterActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import baidumapsdk.demo.R;

public class FeedBackActivity extends TitleFooterActivity implements OnClickListener{
	private Handler mHandler=new Handler();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.feedback);
		
		findViewById(R.id.commit_btn).setOnClickListener(this);
		setTitleLeftButton(View.VISIBLE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.commit_btn){
			ProgressDialogUtils.showProgressDialog(this, R.string.submiting);
			new Handler().postDelayed(new Runnable(){
				public void run(){
					((EditText)findViewById(R.id.feedback_content_tv)).setText("");
					ProgressDialogUtils.dismissProgressDialog();
				}
			}, 3000);
		}
	}

}
