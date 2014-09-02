package w.a.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class PlaneView2 extends View{
	
	Paint p;
	float ox=1080/2,oy=-1920/2;
	float x;
	float y;
	
	Bitmap bmp;

	public PlaneView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public PlaneView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public PlaneView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		p=new Paint();
		p.setColor(0xffff0000);
		p.setStrokeWidth(6);
		bmp=Bitmap.createBitmap(1080,1920, Config.ARGB_8888);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(bmp, 0, 0, null);
	}
	
	Handler mHandler=new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			
			invalidate();
		}
	};
	
	public void start(){
		mHandler.sendEmptyMessage(1);
		new MThread().start();
	}
	
	private void initCalculate(){
		x=-1080/2;
	}
	float a=-1100f/(400*400);
	boolean run=true;
	private void calculate(){
		x++;
		y=a*x*x;
		if(x<1080/2){
			mHandler.sendEmptyMessageDelayed(1, 60);
		}else{
			run=false;
		}
	}
	class MThread extends Thread{
		
		public void run(){
			Canvas c=new Canvas(bmp);
			initCalculate();
			while(run){
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					// TODO: handle exception
				}
				calculate();

				c.drawPoint(ox+x, -y, p);
			}
		}
	}

}
