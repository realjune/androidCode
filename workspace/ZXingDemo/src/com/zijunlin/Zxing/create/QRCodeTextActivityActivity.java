package com.zijunlin.Zxing.create;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zijunlin.Zxing.Demo.R;

public class QRCodeTextActivityActivity extends Activity {
	/** Called when the activity is first created. */
	Button btn1 = null;
	Button btn2 = null;
	ImageView ivImageView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode_text);
		btn1 = (Button) findViewById(R.id.button1);// ������
		btn2 = (Button) findViewById(R.id.button2);// ��ά��
		ivImageView = (ImageView) findViewById(R.id.imageView1);
		final String strconteString = "112233445566";

		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bitmap mBitmap = null;
				mBitmap = creatBarcode(QRCodeTextActivityActivity.this,
						strconteString, 300, 300, true);
				if (mBitmap != null) {
					ivImageView.setImageBitmap(mBitmap);
				}
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bitmap mBitmap = null;
				try {
					if (!strconteString.equals("")) {
						mBitmap = Create2DCode(strconteString);

						// Bitmap bm =
						// BitmapFactory.decodeResource(getResources(),
						// R.drawable.diagnose1);
						ivImageView.setImageBitmap(createBitmap(
								mBitmap,
								zoomBitmap(BitmapFactory.decodeResource(
										getResources(), R.drawable.icon), 60,60)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Bitmap Create2DCode(String str) throws WriterException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
		 hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// ���ɶ�ά����,����ʱָ����С,��Ҫ������ͼƬ�Ժ��ٽ�������,������ģ������ʶ��ʧ��
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 500, 500, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// ��ά����תΪһά��������,Ҳ����һֱ��������
		int[] pixels = new int[width * height];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffffffff;
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// ͨ��������������bitmap,����ο�api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	public File GetCodePath(String name) {
		String EXTERN_PATH = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) == true) {
			EXTERN_PATH = android.os.Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/";
			File f = new File(EXTERN_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		return new File(EXTERN_PATH + name);
	}

	/**
	 * ͼƬ�����������Ŀհ׵Ŀ��
	 */
	private int marginW = 20;
	/**
	 * ������ı�������
	 */
	private BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

	/**
	 * ����������
	 * 
	 * @param context
	 * @param contents
	 *            ��Ҫ���ɵ�����
	 * @param desiredWidth
	 *            ����������Ŀ��
	 * @param desiredHeight
	 *            ����������ĸ߶�
	 * @param displayCode
	 *            �Ƿ����������·���ʾ����
	 * @return
	 */
	public Bitmap creatBarcode(Context context, String contents,
			int desiredWidth, int desiredHeight, boolean displayCode) {
		Bitmap ruseltBitmap = null;
		if (displayCode) {
			Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
					desiredWidth, desiredHeight);
			Bitmap codeBitmap = creatCodeBitmap(contents, desiredWidth + 2
					* marginW, desiredHeight, context);
			ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
					0, desiredHeight));
		} else {
			ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
					desiredWidth, desiredHeight);
		}

		return ruseltBitmap;
	}

	/**
	 * ������ʾ�����Bitmap
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param context
	 * @return
	 */
	protected Bitmap creatCodeBitmap(String contents, int width, int height,
			Context context) {
		TextView tv = new TextView(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(layoutParams);
		tv.setText(contents);
		tv.setHeight(height);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setWidth(width);
		tv.setDrawingCacheEnabled(true);
		tv.setTextColor(Color.BLACK);
		tv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

		tv.buildDrawingCache();
		Bitmap bitmapCode = tv.getDrawingCache();
		return bitmapCode;
	}

	/**
	 * �����������Bitmap
	 * 
	 * @param contents
	 *            ��Ҫ���ɵ�����
	 * @param format
	 *            �����ʽ
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 * @throws WriterException
	 */
	protected Bitmap encodeAsBitmap(String contents, BarcodeFormat format,
			int desiredWidth, int desiredHeight) {
		final int WHITE = 0xFFFFFFFF;
		final int BLACK = 0xFF000000;

		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result = null;
		try {
			result = writer.encode(contents, format, desiredWidth,
					desiredHeight, null);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		// All are 0, or black, by default
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * ������Bitmap�ϲ���һ��
	 * 
	 * @param first
	 * @param second
	 * @param fromPoint
	 *            �ڶ���Bitmap��ʼ���Ƶ���ʼλ�ã�����ڵ�һ��Bitmap��
	 * @return
	 */
	protected Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
		if (first == null || second == null || fromPoint == null) {
			return null;
		}
		Bitmap newBitmap = Bitmap.createBitmap(
				first.getWidth() + second.getWidth() + marginW,
				first.getHeight() + second.getHeight(), Config.ARGB_4444);
		Canvas cv = new Canvas(newBitmap);
		cv.drawBitmap(first, marginW, 0, null);
		cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();

		return newBitmap;
	}

	/*** ��΢�Ŷ�ά�뿪ʼ ***/
	// ͼƬ����
	public  Bitmap cutBitmap(Bitmap mBitmap, Rect r, Bitmap.Config config) {
		int width = r.width();
		int height = r.height();
		Bitmap croppedImage = Bitmap.createBitmap(width, height, config);
		Canvas cvs = new Canvas(croppedImage);
		Rect dr = new Rect(0, 0, width, height);
		cvs.drawBitmap(mBitmap, r, dr, null);
		return croppedImage;
	}

	/***
	 * �ϲ�ͼƬ
	 * 
	 * @param src
	 * @param watermark
	 * @return
	 */
	private Bitmap createBitmap(Bitmap src, Bitmap watermark) {
		String tag = "createBitmap";
		Log.d(tag, "create a new bitmap");
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas cv = new Canvas(newb);

		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �� 0��0���꿪ʼ����src

		// ��src���м仭watermark
		cv.drawBitmap(watermark, w / 2 - ww / 2, h / 2 - wh / 2, null);// ����ic_launcher��λ��

		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newb;
	}

	/***
	 * ����ͼƬ
	 * 
	 * @param src
	 * @param destWidth
	 * @param destHeigth
	 * @return
	 */
	private Bitmap zoomBitmap(Bitmap src, int destWidth, int destHeigth) {
		String tag = "lessenBitmap";
		if (src == null) {
			return null;
		}
		int w = src.getWidth();// Դ�ļ��Ĵ�С
		int h = src.getHeight();
		// calculate the scale - in this case = 0.4f
		float scaleWidth = ((float) destWidth) / w;// �����С����
		float scaleHeight = ((float) destHeigth) / h;// �߶���С����
		Log.d(tag, "bitmap width is :" + w);
		Log.d(tag, "bitmap height is :" + h);
		Log.d(tag, "new width is :" + destWidth);
		Log.d(tag, "new height is :" + destHeigth);
		Log.d(tag, "scale width is :" + scaleWidth);
		Log.d(tag, "scale height is :" + scaleHeight);
		Matrix m = new Matrix();// ����
		m.postScale(scaleWidth, scaleHeight);// ���þ������
		Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);// ֱ�Ӱ��վ���ı�����Դ�ļ��������
		return resizedBitmap;
	}

}
