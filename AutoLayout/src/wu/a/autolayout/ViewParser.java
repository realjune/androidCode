package wu.a.autolayout;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public abstract class ViewParser<T extends View> implements XmlParser<T> {
	private static final String LAYOUT_MARGIN_TOP = "layout_marginTop";
	private static final String LAYOUT_MARGIN_LEFT = "layout_marginLeft";
	private static final String PX = "px";
	private static final String DP = "dp";
	private static final String FILL_PARENT = "fill_parent";
	private static final String MATCH_PARENT = "match_parent";
	private static final String WRAP_CONTENT = "wrap_content";
	private static final String REGULAR_EXPRESSION = "/";
	private static final String PREFIX_AT = "@";
	private static final String PREFIX_NUM = "#";
	public static final String PREFIX_DRAWABLE="@drawable/";

	private static final String ATT_BACKGROUND = "background";
	private static final String ATT_LAYOUT_Y = "layout_y";
	private static final String ATT_LAYOUT_X = "layout_x";
	private static final String ATT_LAYOUT_MARGIN = "layout_margin";
	private static final String ATT_LAYOUT_HEIGHT = "layout_height";
	private static final String ATT_LAYOUT_WIDTH = "layout_width";
	public static final String ATT_SRC="src";
	public static final String ATT_TEXT="text";
	public static final String ATT_ID="id";
	
	T view;
	FrameLayout.LayoutParams lp;

	public ViewParser(T view) {
		this.view = view;
		lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);
	}

	/** (non-Javadoc)
	 * @see wu.a.autolayout.XmlParser#parse(java.io.InputStream)
	 */
	@Override
	public T parse(InputStream is) throws Exception {
		return null;
	}

	@Override
	public String serialize() throws Exception {
		return null;
	}

	/**
	 * <pre>
	 * 属性解析
	 * @param parser
	 * @return
	 * </pre>
	 */
	public T parse(XmlPullParser parser) {
		for (int i = 0, e = parser.getAttributeCount(); i < e; i++) {
			String an = parser.getAttributeName(i);
			String av = parser.getAttributeValue(i);
			Log.d("ddd", i + " att:" + an + "=" + av);
			parse(an, av);
		}
		return view;
	}

	/**
	 * <pre>
	 * 属性解析
	 * @param an
	 * @param av
	 * </pre>
	 */
	void parse(String an, String av) {
		if (ATT_ID.equals(an)) {
			// view.setId(Integer.parseInt(av));
		} else if (ATT_LAYOUT_WIDTH.equals(an)) {
			view.getLayoutParams().width = getPx(av);
		} else if (ATT_LAYOUT_HEIGHT.equals(an)) {
			view.getLayoutParams().height = getPx(av);
		} else if (ATT_LAYOUT_MARGIN.equals(an)) {
			int px=getPx(av);
			 lp.setMargins(px,px,px,px);
		} else if (LAYOUT_MARGIN_LEFT.equals(an)) {
			lp.leftMargin=getPx(av);
		} else if (LAYOUT_MARGIN_TOP.equals(an)) {
			lp.topMargin=getPx(av);
		} else if (ATT_BACKGROUND.equals(an)) {
			if (av != null && av.startsWith(PREFIX_NUM)) {
				view.setBackgroundColor(0xff00ff00/*Integer.decode(av.substring(1))*/);
			} else if (av.startsWith(PREFIX_AT)) {
				String[] strs = av.substring(1).split(REGULAR_EXPRESSION);
				int id = view.getResources().getIdentifier(strs[1], strs[0],
						null);
				view.setBackgroundResource(id);
			}
		}
	}

	/**
	 * <pre>
	 * Android 硬件运行环境单位转换
	 * @param av
	 * @return
	 * </pre>
	 */
	public int getPx(String av) {
		int px = 0;
		if (WRAP_CONTENT.equals(av)) {
			px = LayoutParams.WRAP_CONTENT;
		} else if (MATCH_PARENT.equals(av) || FILL_PARENT.equals(av)) {
			px = LayoutParams.MATCH_PARENT;
		} else if (av != null && av.endsWith(DP)) {
			int dp = Integer.parseInt(av.substring(0, av.length() - 2));
			px = (int) (view.getResources().getDisplayMetrics().density * dp);
		} else if (av != null && av.endsWith(PX)) {
			px = Integer.parseInt(av);
		}
		return px;
	}

}
