package com.aess.aemm.view.evaluate;


import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aess.aemm.R;

public class ImageAdapter extends BaseAdapter{
	private List<String> imgUrl;
	private Context context;
	LayoutInflater inflater;
	public static final String LOGCAT="AsyncAdapter";
	
	public ImageAdapter(Context context,List<String> imgUrl){
		this.imgUrl = imgUrl;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
//		Log.d(LOGCAT,"getCount:"+imgUrl.size());
		return imgUrl.size();
	}

	public Object getItem(int position) {
//		Log.d(LOGCAT,"getItem:"+position+imgUrl.get(position));
		return imgUrl.get(position);
	}

	public long getItemId(int position) {
//		Log.d(LOGCAT,"getItemId:"+position);
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.d(LOGCAT,"getView:"+position);
		ImageView imageView;
		ProgressBar pb;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.async_loader_item, null);
		}
		pb=(ProgressBar) convertView.findViewById(R.id.loader_pb);
		imageView = (ImageView) convertView.findViewById(R.id.imgae);
		//��ǰ�涨���cache���ҵ���·���µ�ͼƬ
		Bitmap map = EnvaluateEditView.cache.get(imgUrl.get(position));
		if(map == null){
			//���û���صģ�����ʾĬ��ͼƬ
			imageView.setImageResource(R.drawable.icon);
			//���û�м��أ��������߳������
			((EnvaluateEditView)context).loadImage(imgUrl.get(position));
			pb.setVisibility(View.VISIBLE);
		}else{
			//��������˵ģ������õ�Imageview
			imageView.setImageBitmap(map);
			pb.setVisibility(View.GONE);
		}
			
		return convertView;
	}

}