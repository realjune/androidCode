package wu.a.autolayout;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Stack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;

public class AutoViewParser implements XmlParser<View>,OnClickListener,OnCheckedChangeListener{
	private Context context;
	private OnClickListener onClickListener;
	private OnCheckedChangeListener onCheckedChangeListener;
	
	public AutoViewParser(Context context){
		this.context=context;
	}
	
    public View parse(InputStream is) throws Exception {
		View obj = null;
		View child = null;
		ViewGroup parrent = null;
		boolean igoreView=false;
		Stack<ViewGroup>viewGropStatk=new Stack<ViewGroup>();
//		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlPullParser parser = factory.newPullParser();
		
		XmlPullParser parser = Xml.newPullParser();	//��android.util.Xml����һ��XmlPullParserʵ��
    	parser.setInput(is, "UTF-8");				//���������� ��ָ�����뷽ʽ
		int eventType = parser.getEventType();

//				parser=context.getResources().getLayout(R.layout.relativelayout);
//		obj= LayoutInflater.from(context).inflate(parser, null ,false);
		if(obj==null)
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				Log.d("ddd","START_DOCUMENT");
				parrent = new FrameLayout(context);
//				child=parrent;
				obj=parrent;
				break;
			case XmlPullParser.START_TAG:
				Log.d("ddd","START_TAG="+parser.getName());
				igoreView=false;
				if (parser.getName().equals("TextView")) {
					child = new TextViewParser(context).parse(parser);
				} else if (parser.getName().equals("Button")) {
					child = new ButtonParser(context).parse(parser);
				} else if (parser.getName().equals("ImageView")) {
					child = new ImageViewParser(context).parse(parser);
				} else if (parser.getName().equals("wu.a.autolayout.ToggleLayout")) {
					child = new ToggleLayoutParser(context).parse(parser);
				} else{
					igoreView=true;
					child = null;
				}
				if(child instanceof ViewGroup){
					viewGropStatk.push(parrent);
					parrent=(ViewGroup) child;
					child=null;
				}
				break;
			case XmlPullParser.END_TAG:
				Log.d("ddd","END_TAG");
				if(igoreView){
					break;
				}
				if(child==null){
					if(viewGropStatk.size()>0){
						child=parrent;
						parrent=viewGropStatk.pop();
					}
				}
				if(child!=null){
					parrent.addView(child);
					if(child.isClickable()){
						if(child instanceof CompoundButton){
							((CompoundButton)child).setOnCheckedChangeListener(this);
						}else{
							child.setOnClickListener(this);
						}
					}
					child=null;
				}
				break;
			case XmlPullParser.TEXT:
				Log.d("ddd","TEXT="+parser.getText());
				break;
			}
			eventType = parser.next();
		}
		return obj;
	}
    
    public String serialize() throws Exception {
//    	View objParser
////		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
////		XmlSerializer serializer = factory.newSerializer();
//		
    	XmlSerializer serializer = Xml.newSerializer();	//��android.util.Xml����һ��XmlSerializerʵ��
    	StringWriter writer = new StringWriter();
//    	serializer.setOutput(writer);	//�����������Ϊwriter
//		serializer.startDocument("UTF-8", true);
//		serializer.startTag("", "books");
//		for (Book book : books) {
//			serializer.startTag("", "book");
//			serializer.attribute("", "id", book.getId() + "");
//			
//			serializer.startTag("", "name");
//			serializer.text(book.getName());
//			serializer.endTag("", "name");
//			
//			serializer.startTag("", "price");
//			serializer.text(book.getPrice() + "");
//			serializer.endTag("", "price");
//			
//			serializer.endTag("", "book");
//		}
//		serializer.endTag("", "books");
//		serializer.endDocument();
//		
		return writer.toString();
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d("ddd","onCheckedChanged in Auto "+buttonView.toString()+ isChecked);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		Log.d("ddd","onClick in Auto "+v.toString());
		// TODO Auto-generated method stub
		
	}
}
