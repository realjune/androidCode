package com.zijunlin.Zxing.create;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zijunlin.Zxing.Demo.CaptureActivity;
import com.zijunlin.Zxing.Demo.R;

public class QRCodeTextActivityActivity extends Activity {
	private static final int SCAN_CODE = 1;
	// ɨ��
	TextView result_code_tv;
	Button scan_code_btn;
	// ����
	EditText decode_et;
	Button btn1 = null;
	Button btn2 = null;
	ImageView ivImageView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode_text);
		result_code_tv = (TextView) findViewById(R.id.result_code_tv);
		scan_code_btn = (Button) findViewById(R.id.scan_code_btn);
		scan_code_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				result_code_tv.setText("");
				startActivityForResult(
						new Intent(QRCodeTextActivityActivity.this,
								CaptureActivity.class), SCAN_CODE);
			}
		});
		decode_et = (EditText) findViewById(R.id.decode_et);
		btn1 = (Button) findViewById(R.id.button1);// ������
		btn2 = (Button) findViewById(R.id.button2);// ��ά��
		ivImageView = (ImageView) findViewById(R.id.imageView1);

		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String strconteString = decode_et.getText().toString().trim();
				Bitmap mBitmap = null;
				mBitmap = creatBarcode(QRCodeTextActivityActivity.this,
						strconteString, barcodeFormat, 300, 300, null, true);
				if (mBitmap != null) {
					ivImageView.setImageBitmap(mBitmap);
				}
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bitmap mBitmap = null;
				String strconteString = decode_et.getText().toString().trim();
				if (!TextUtils.isEmpty(strconteString)) {
					Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
					hints.put(EncodeHintType.ERROR_CORRECTION,
							ErrorCorrectionLevel.L); //�ݴ���
					// hints.put(EncodeHintType.CHARACTER_SET, "GBK");
					hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//����
					hints.put(EncodeHintType.MARGIN, 2);//�߿� 
					// ���ɶ�ά����,����ʱָ����С,��Ҫ������ͼƬ�Ժ��ٽ�������,������ģ������ʶ��ʧ��
					mBitmap = creatBarcode(QRCodeTextActivityActivity.this,
							strconteString, BarcodeFormat.QR_CODE, 500, 500,
							hints, true);

					// ��ȡͼ��
					Bitmap bm = BitmapFactory.decodeResource(getResources(),
							R.drawable.icon);
					// ����ͼ��
					bm = zoomBitmap(bm, 60, 60);
					// �ϲ���ά����ͼ��
					mBitmap = createBitmap(mBitmap, bm);
					ivImageView.setImageBitmap(mBitmap);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SCAN_CODE) {
			if (resultCode == RESULT_OK) {
				if (data != null) {
					String code = data
							.getStringExtra(CaptureActivity.SCAN_CODE);
					if (code != null) {
						result_code_tv.setText(code);
					}
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
			BarcodeFormat format, int desiredWidth, int desiredHeight,
			Map<EncodeHintType, ?> hints, boolean displayCode) {
		Bitmap ruseltBitmap = encodeAsBitmap(contents, format, desiredWidth,
				desiredHeight, hints);
		if (displayCode) {
			Bitmap codeBitmap = string2Bitmap(contents, desiredWidth,
					desiredHeight, context);
			ruseltBitmap = mixtureBitmap(ruseltBitmap, codeBitmap, new PointF(
					(ruseltBitmap.getWidth() - codeBitmap.getWidth()) >> 1,
					desiredHeight));
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
	protected Bitmap string2Bitmap(String contents, int width, int height,
			Context context) {
		TextView tv = new TextView(context);
		tv.setText(contents);
		tv.setGravity(Gravity.CENTER);
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
			int desiredWidth, int desiredHeight, Map<EncodeHintType, ?> hints) {
		final int WHITE = 0xFFFFFFFF;
		final int BLACK = 0xFF000000;

		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result = null;
		try {
			result = writer.encode(contents, format, desiredWidth,
					desiredHeight, hints);
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
	 * ������Bitmapƴ�ӳ�һ��ͼƬ
	 * 
	 * @param first
	 *            ԭͼ
	 * @param second
	 *            ����ͼƬ
	 * @param fromPoint
	 *            �ڶ���Bitmap��ʼ���Ƶ���ʼλ�ã�����ڵ�һ��Bitmap��
	 * @return
	 */
	protected Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
		if (first == null || second == null || fromPoint == null) {
			return null;
		}
		int desW = Math.max(first.getWidth(),
				(int) fromPoint.x + second.getWidth());
		int desH = Math.max(first.getHeight(),
				(int) fromPoint.y + second.getHeight());
		Bitmap newBitmap = Bitmap.createBitmap(desW, desH, Config.ARGB_4444);
		Canvas cv = new Canvas(newBitmap);
		cv.drawBitmap(first, 0, 0, null);
		cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();

		return newBitmap;
	}

	/**
	 * <pre>
	 *  ͼƬ����
	 * @param mBitmap ԭͼ
	 * @param r ���к�Ĵ�С
	 * @param config
	 * @return
	 * </pre>
	 */
	public Bitmap cutBitmap(Bitmap mBitmap, Rect r, Bitmap.Config config) {
		int width = r.width();
		int height = r.height();
		Bitmap croppedImage = Bitmap.createBitmap(width, height, config);
		Canvas cvs = new Canvas(croppedImage);
		Rect dr = new Rect(0, 0, width, height);
		cvs.drawBitmap(mBitmap, r, dr, null);
		return croppedImage;
	}

	/***
	 * �����Ķ��뷽ʽ������ʽ�ϲ�ͼƬ
	 * 
	 * @param src
	 *            ��ͼ
	 * @param watermark
	 *            �����ͼ��
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
		cv.drawBitmap(watermark, (w - ww) / 2, (h - wh) / 2, null);// ����ic_launcher��λ��

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
