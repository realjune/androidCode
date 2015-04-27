package wu.a.autolayout;

import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class XMLParserSerializerActivity extends Activity {

	private static final String TAG = "XML";

	private XmlParser<View> parser;
	private View parserView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_parser_serialize_layout);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sax_parser_btn:
			try {
				InputStream is = getAssets().open("absolutelayout.xml");
				parser = new AutoViewParser(this); // 创建SaxBookParser实例
				parserView = parser.parse(is); // 解析输入�?
				setContentView(parserView);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.sax_serializer:
			try {
				String xml = parser.serialize(parserView); // 序列�?
				FileOutputStream fos = openFileOutput("books.xml",
						Context.MODE_PRIVATE);
				fos.write(xml.getBytes("UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(parserView!=null){
			setContentView(R.layout.xml_parser_serialize_layout);
			parserView=null;
		}
	}
}
