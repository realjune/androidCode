package com.act.sctc.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.act.sctc.R;
import com.act.sctc.been.PhoneColor;

public class PhoneColorManager extends BaseManager implements
		OnCheckedChangeListener {
	private Context context;
	private ViewGroup layout;
	private TextView info_tv;
	private ImageView phone_img1, phone_img2, phone_img3, phone_img4;

	private GridView colors_gv;

	private List<PhoneColor> colors;

	public static PhoneColorManager getPhoneColorManager(Context context) {
		return new PhoneColorManager(context);
	}

	private PhoneColorManager(Context context) {
		this.context = context;
		layout = (ViewGroup) LayoutInflater.from(context).inflate(
				R.layout.phone_color_page, null);
		colors=new ArrayList<PhoneColor>();
		for(int i=0;i<10;i++){
			PhoneColor pc=new PhoneColor();
			pc.setColor(0xffff0000+0xff*i*16);
			pc.setName("color "+i);
			colors.add(pc);
		}
		iniController();
		iniListener();
		// iniVariable();
	}

	public View getLayout() {
		return layout;
	}

	private void iniController() {
		info_tv = (TextView) layout.findViewById(R.id.info_tv);
		phone_img1 = (ImageView) layout.findViewById(R.id.phone_img1);
		phone_img2 = (ImageView) layout.findViewById(R.id.phone_img2);
		phone_img3 = (ImageView) layout.findViewById(R.id.phone_img3);
		phone_img4 = (ImageView) layout.findViewById(R.id.phone_img4);
		colors_gv = (GridView) layout.findViewById(R.id.colors_gv);

	}

	private void iniListener() {
		colors_gv.setNumColumns(colors.size());

		colors_gv.setAdapter(new MyColorAdapter());
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

	}

	private class MyColorAdapter extends BaseAdapter implements OnClickListener {
		int curSelected = -1;
		View curSelectView = null;

		@Override
		public int getCount() {
			return colors == null ? 0 : colors.size();
		}

		@Override
		public Object getItem(int position) {
			return colors == null ? null : colors.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PhoneColor pcolor = colors.get(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.phone_color_btn_model2, null);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.name_color_tv);
			tv.setText(pcolor.getName());
			LinearLayout colorll = (LinearLayout) convertView
					.findViewById(R.id.bottom_color);
			colorll.setBackgroundColor(pcolor.getColor());
			convertView.setOnClickListener(this);
			convertView.setTag((Integer) position);
			return convertView;
		}

		@Override
		public void onClick(View v) {
			Integer p = (Integer) v.getTag();
			if (curSelectView != null)
				curSelectView.setBackgroundColor(0);
			if (curSelected == p.intValue()) {
				curSelected = -1;
			} else {
				curSelected = p;
				v.setBackgroundColor(0xff78CCEE);
				curSelectView = v;
			}
		}

	}
}
