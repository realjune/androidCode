package wu.a.wuliu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import baidumapsdk.demo.R;

/**
 * <pre>
 * 优惠活动
 * @author junxu.wang
 * @d2015年7月16日
 * </pre>
 *
 */
public class InfoManager implements OnClickListener {

	private View view;
	private Context context;

	private LayoutInflater lif;

	public InfoManager(Context context) {
		this.context = context;
		lif = LayoutInflater.from(context);
		view = LayoutInflater.from(context).inflate(R.layout.info_layout,
				null);

		loadData();
	}

	private void loadData() {
	}

	public View getView() {
		return view;
	}

	public String getTitle() {
		return "优惠活动";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.feedback_tv:
		// context.startActivity(new Intent(context,FeedBackActivity.class));
		// break;
		case R.id.cargo_goods:
			break;
		case R.id.cargo_home:

			break;

		default:
			break;
		}
	}


}
