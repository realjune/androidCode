package wu.a.autolayout;

import java.io.InputStream;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewParser extends ViewParser<ImageView> {
	

	public ImageViewParser(Context context) {
		super(new ImageView(context));
	}

	public ImageViewParser(ImageView view) {
		super(view);
	}

	@Override
	public ImageView parse(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String serialize() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	void parse(String an,String av){
		if(ATT_SRC.equals(an)){
			if(av.startsWith(PREFIX_DRAWABLE)){
//				view.setImageBitmap(bm);
			}
		}else 
			super.parse(an,av);
	}

}
