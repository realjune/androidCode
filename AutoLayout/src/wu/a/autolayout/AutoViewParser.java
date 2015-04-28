package wu.a.autolayout;

import java.io.InputStream;
import java.io.StringWriter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class AutoViewParser implements XmlParser<View>{
	private Context context;
	
	public AutoViewParser(Context context){
		this.context=context;
	}
	
    public View parse(InputStream is) throws Exception {
		View obj = null;
		View child = null;
		ViewGroup parrent = null;
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
				child=parrent;
				obj=parrent;
				break;
			case XmlPullParser.START_TAG:
				Log.d("ddd","START_TAG="+parser.getName());
				if (parser.getName().equals("TextView")) {
					child = new TextViewParser(context).parse(parser);
				} else if (parser.getName().equals("Button")) {
					child = new ButtonParser(context).parse(parser);
				} else{
					child = null;
				}
				break;
			case XmlPullParser.END_TAG:
				Log.d("ddd","END_TAG");
				if(child!=null){
					parrent.addView(child);
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
}
