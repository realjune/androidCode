package wu.a.autolayout;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;

public abstract class ViewParser<T extends View> implements XmlParser<T> {
	T view;
	AbsoluteLayout.LayoutParams lp;
	public ViewParser(T view){
		this.view=view;
		lp=new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,0,0);
		view.setLayoutParams(lp);
	}
	

	@Override
	public T parse(InputStream is) throws Exception {
		return null;
	}

	@Override
	public String serialize(T books) throws Exception {
		return null;
	}
	
	public abstract T parse(XmlPullParser parser);
	
	void parse(String an,String av){
		if("id".equals(an)){
//			view.setId(Integer.parseInt(av));
		}else if("layout_width".equals(an)){
			view.getLayoutParams().width=getPx(av);
		}else if("layout_height".equals(an)){
			view.getLayoutParams().height=getPx(av);
		}else if("layout_margin".equals(an)){
//			lp.setMargins(getPx(av), getPx(av), getPx(av), getPx(av));
		}else if("layout_x".equals(an)){
			lp.x=getPx(av);
		}else if("layout_y".equals(an)){
			lp.y=getPx(av);
		}else if("background".equals(an)){
			if(av!=null&&av.startsWith("#")){
				view.setBackgroundColor(Integer.parseInt(av.substring(1),16));
			}else if(av.startsWith("@")){
				String[]strs=av.substring(1).split("/");
				int id=view.getResources().getIdentifier(strs[1], strs[0], null);
				view.setBackgroundResource(id);
			}
		}
	}
	
	public int getPx(String av){
		int px=0;
		if("wrap_content".equals(av)){
			px=LayoutParams.WRAP_CONTENT;
		}else if("match_parent".equals(av)||"fill_parent".equals(av)){
			px=LayoutParams.MATCH_PARENT;
		}else if(av!=null&&av.endsWith("dp")){
			int dp=Integer.parseInt(av.substring(0, av.length()-2));
			px=(int) (view.getResources().getDisplayMetrics().density*dp);
		}else if(av!=null && av.endsWith("px")){
			px=Integer.parseInt(av);
		}
		return px;
	}


}
