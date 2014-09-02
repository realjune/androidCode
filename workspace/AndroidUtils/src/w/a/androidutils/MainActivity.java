package w.a.androidutils;

import w.a.animation.MyAnimation;
import w.a.view.PlaneView;
import w.a.view.PlaneView2;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;

public class MainActivity extends Activity {
	
	View plane;
	Button button1;
	
	PlaneView planeView;
	PlaneView2 plane_view2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		planeView=(PlaneView) findViewById(R.id.plane_view);
		plane_view2=(PlaneView2) findViewById(R.id.plane_view2);
		plane=findViewById(R.id.plane);
		button1=(Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start();
			}
		});
	}
	
	public void start(){
		Animation mAnimation=new MyAnimation();
//		plane.setAnimation(mAnimation);
//		mAnimation.start();
		plane.startAnimation(mAnimation);
		planeView.start();
		plane_view2.start();
	}
}
