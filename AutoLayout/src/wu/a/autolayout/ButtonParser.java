package wu.a.autolayout;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class ButtonParser extends ViewParser<TextView> {
	

	public ButtonParser(Context context) {
		super(new Button(context));
	}

	@Override
	public TextView parse(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String serialize(TextView books) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextView parse(XmlPullParser parser) {
//		if("TextView".equals(parser.getName())){
			for(int i=0,e=parser.getAttributeCount();i<e;i++){
				String an=parser.getAttributeName(i);
				String av=parser.getAttributeValue(i);
				Log.d("ddd",i+" att:"+an+"="+av);
				if("text".equals(an)){
					view.setText(av);
				}else 
					super.parse(an,av);
				
			}
			
			
			return view;
//		}
//		retusrn null;
	}

}
