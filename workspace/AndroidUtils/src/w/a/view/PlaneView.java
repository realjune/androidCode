package w.a.view;

import w.a.androidutils.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class PlaneView extends View{

	Bitmap plane;
	Matrix matrix;
	float cy=400,cx=400,sx,sy,ex,ey;
	float scaleX=1,scaleY=1;
	long frame;
	Bitmap bmp;
	Paint paint;
	public PlaneView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public PlaneView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PlaneView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		frame=1000/60;
		matrix=new Matrix();
		plane=BitmapFactory.decodeResource(getResources(), R.drawable.sex_select_icon);
		
		bmp=Bitmap.createBitmap(1080, 1920, Config.ARGB_8888);
		

		paint=new Paint();
		paint.setColor(0xffff0000);
		paint.setStrokeWidth(6);
		
	}
	
	class CThread extends Thread{
		int x=0;
		int y=1920;
		public void run() {

			Canvas c=new Canvas(bmp);
			while(true){
				try {
					Thread.sleep(80);
				} catch (Exception e) {
					e.printStackTrace();
				}
				x+=1;
				if(x>1080){
					x=0;
				}
				y=1920-(x*x)/2*2;
				c.drawPoint(x, y, paint);
			}
		}
	};
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(plane, matrix, null);
		canvas.drawBitmap(bmp, 0, 0,null);
	}
	
	Handler mHandler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			sendEmptyMessageDelayed(1, frame);

			matrix.setScale(scaleX,scaleY);
			matrix.postTranslate(cx, cy);
			invalidate();
		}
	};
	
	public void start(){
		new CThread().start();
		mHandler.removeMessages(1);
//		setScaleCenter(0.2f,0.2f);
		cy+=10;
		cx+=10;
		mHandler.sendEmptyMessage(1);
	}
	private void setScaleCenter(float x,float y){
		scaleX+=x;
		scaleY+=y;
		cx-=plane.getWidth()*x/2;
		cy-=plane.getHeight()*y/2;
	}

	private Path createPath(){
		Path p=new Path();
		p.moveTo(0, 1920);
		p.quadTo(200, 500, 1080, 0);
		p.close();
		return p;
	}
}
