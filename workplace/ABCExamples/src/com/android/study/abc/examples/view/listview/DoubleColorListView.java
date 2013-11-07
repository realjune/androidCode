package com.android.study.abc.examples.view.listview;

import java.util.Arrays;
import java.util.List;

import com.android.study.abc.examples.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DoubleColorListView extends Activity {

	ListView lv_DoubleColorListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doublecolorlistview);
		lv_DoubleColorListView=(ListView) findViewById(R.id.lv_doublecolorlistview);
		String[]strarr={"������(��)","�����(��)","�ӱ�ʡ(��)","ɽ��ʡ(��)","���ɹ�������(���ɹ�)","����ʡ(��)","����ʡ(��)","������ʡ(��)","�Ϻ���(��)","����ʡ(��)","�㽭ʡ(��)","����ʡ(��)","����ʡ(��)","����ʡ(��)","ɽ��ʡ(³)","����ʡ(ԥ)","����ʡ(��)","����ʡ(��)","�㶫ʡ(��)","����׳��������(��)","����ʡ(��)","������(��)","�Ĵ�ʡ(������)","����ʡ(ǭ����)","����ʡ(�ᡢ��)","����������(��)","����ʡ(�¡���)","����ʡ(�ʡ�¤)","�ຣʡ(��)","���Ļ���������(��)","̨��ʡ(̨)","�½�ά���������(��)","����ر�������(��)","�����ر�������(��)"};
		List list=Arrays.asList(strarr);
		lv_DoubleColorListView.setAdapter(new DoubleColorListViewAdapter(this, list));
	}
}
/**
 * ����ͨ�������б�������
 * 
 * @author Administrator
 * 
 */
class DoubleColorListViewAdapter extends BaseAdapter {
	private List list;
	LayoutInflater layoutInflater;
	Context context;
	
	public void setList(List l){
		list=l;
	}

	public DoubleColorListViewAdapter(Context context, List list) {
		this.list = list;
		this.context=context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String str = (String) list.get(position);
		LinearLayout linearLayout;
		if (convertView == null) {
			layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.item_doublecolorlistview, null);
		} else {
			linearLayout = (LinearLayout) convertView;
		}
		TextView tvListName= ((TextView) linearLayout.findViewById(R.id.TextView01));
		if (str != null)
			tvListName.setText(str);
		return linearLayout;
	}
}
