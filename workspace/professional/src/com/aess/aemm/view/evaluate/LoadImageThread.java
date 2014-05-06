package com.aess.aemm.view.evaluate;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

public class LoadImageThread implements Runnable{
	private String imageUrl;
	private Handler mHandler;
	HashMap<String,Bitmap> cache;
	LoadImageThread(String url,HashMap<String,Bitmap> cache,Handler handler){
		this.imageUrl = url;
		this.cache = cache;
		mHandler=handler;
	}
	public void run(){
		try {
			System.out.println("��ʼ����"+imageUrl);
			//��ָ����·���µ�ͼƬ���ص������cache����ȥ
			InputStream imageStream = new URL(imageUrl).openStream();
			Bitmap map = BitmapFactory.decodeStream(imageStream);
			cache.put(imageUrl, map);
			mHandler.sendEmptyMessage(1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}