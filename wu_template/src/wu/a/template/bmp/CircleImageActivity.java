package wu.a.template.bmp;

import wu.a.template.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class CircleImageActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circleimage_layout);
		
		CircleImageViewA cia=(CircleImageViewA) findViewById(R.id.cia);
		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.face);
		cia.setImageBitmap(bmp);
		

		CircleImageViewB cib=(CircleImageViewB) findViewById(R.id.cib);
		bmp=BitmapFactory.decodeResource(getResources(), R.drawable.face);
		cib.setImageBitmap(bmp);
		
		ImageView img=(ImageView) findViewById(R.id.imageView1);
		bmp=BitmapFactory.decodeResource(getResources(), R.drawable.face);
		bmp=ImageUtils.getRoundedCornerBitmap(bmp,1f);
		img.setImageBitmap(bmp);
	}

}
