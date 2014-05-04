package com.android.wjx.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

/**
 * �̳���Gallery����дgetChildStaticTransformation����������item��view����λ�ã�Զ����ͬ����������
 * ���в�ͬ�̶ȵı仯����
 * 
 * <pre>
 * <font color='red'>ItemView��LayoutParmar��Ҫָ���߿�,������ת�����,������...</font>
 * �������CoverFlow�࣬˵�����㣺
 * 1. ��Ա����
 * mCamera����������3DЧ������,����z�᷽���ϵ�ƽ��,��y�����ת��
 * mMaxRotationAngle��ͼƬ��y�������ת�Ƕ�,Ҳ������Ļ�����������ͼƬ����ת�Ƕ�
 * mMaxZoom��ͼƬ��z��ƽ�Ƶľ���,�Ӿ��Ͽ��������ǷŴ���С��Ч��.
 * �����ı�������������
 * setStaticTransformationsEnabled(true)Ҳ����˵������������true��ʱ��ÿ��viewGroup(��Gallery��Դ��Ϳ��Կ������Ǵ�ViewGroup��Ӽ̳й�����)
 * �����»�����child��ʱ�򶼻�ٷ�getChildStaticTransformation�������,��������ֻ��Ҫ�������������ȥ��
 * ����ת�ͷŴ�Ĳ����Ϳ�����
 * ������getter��setter��������������
 * </pre>
 * 
 * @author junxu.wang
 * 
 */
public class CoverFlow extends Gallery {
	/** ����������3DЧ������,����z�᷽���ϵ�ƽ��,��y�����ת�� */
	private static final Camera mCamera = new Camera();

	private static final PaintFlagsDrawFilter filter = new PaintFlagsDrawFilter(
			0, Paint.FILTER_BITMAP_FLAG);

	/** ��ͼƬ��y�������ת�Ƕ�,Ҳ������Ļ�����������ͼƬ����ת�Ƕ� */
	private float mMaxRotationAngleY = 55;

	/** ��z������ƽ�Ƶľ���,�Ӿ��Ͽ��������ǷŴ���С��Ч�� */
	private float mMaxTranslateValueZ = 255;

	/** Y��任,��ֱЧ�� */
	private float mMaxTranslateValueY = -55;

	/** X��任,��ֱЧ�� */
	private float mMaxTranslateValueX = 55;

	/** X��������λ�� */
	private float mCoveflowCenter;

	/** z��任(����[+,-])Ч�� */
	private boolean isTranslateModeZ = false;

	/** ͸����Ч�� */
	private boolean isAlphaMode = false;

	/** Y��ƽ��,��ֱЧ�� ,ʹ��Camera�任��,����¼�λ����uiλ�ô�λ,���޸� */
	private boolean isTranslateModeY = false;

	/** X��ƽ�� * */
	private boolean isTranslateModeX = false;

	/** Y����ת * */
	private boolean isRotationModeY = false;

	public CoverFlow(Context context) {

		super(context);
		init();
	}

	public CoverFlow(Context context, AttributeSet attrs) {

		super(context, attrs);
		parseAttributes(context, attrs);
		init();

	}

	public CoverFlow(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		parseAttributes(context, attrs);
		init();

	}

	private void init() {

		/*
		 * Ҳ����˵������������true��ʱ��ÿ��viewGroup(��Gallery��Դ��Ϳ��Կ������Ǵ�ViewGroup��Ӽ̳й�����)
		 * �����»�����child��ʱ�򶼻�ٷ�getChildStaticTransformation�������
		 * ,��������ֻ��Ҫ�������������ȥ������ת�ͷŴ�Ĳ����Ϳ�����
		 */
		this.setStaticTransformationsEnabled(true);

		setSpacing(-50);
	}

	
	public float getMaxRotationAngleY() {
		return mMaxRotationAngleY;
	}

	public float getMaxTranslateValueZ() {
		return mMaxTranslateValueZ;
	}

	public float getMaxTranslateValueY() {
		return mMaxTranslateValueY;
	}

	public float getMaxTranslateValueX() {
		return mMaxTranslateValueX;
	}

	public boolean isTranslateModeZ() {
		return isTranslateModeZ;
	}

	public boolean isAlphaMode() {
		return isAlphaMode;
	}

	public boolean isTranslateModeY() {
		return isTranslateModeY;
	}

	public boolean isTranslateModeX() {
		return isTranslateModeX;
	}

	public boolean isRotationModeY() {
		return isRotationModeY;
	}

	public void setMaxRotationAngleY(float mMaxRotationAngleY) {
		this.mMaxRotationAngleY = mMaxRotationAngleY;
	}

	public void setMaxTranslateValueZ(float mMaxTranslateValueZ) {
		this.mMaxTranslateValueZ = mMaxTranslateValueZ;
	}

	public void setMaxTranslateValueY(float mMaxTranslateValueY) {
		this.mMaxTranslateValueY = mMaxTranslateValueY;
	}

	public void setMaxTranslateValueX(float mMaxTranslateValueX) {
		this.mMaxTranslateValueX = mMaxTranslateValueX;
	}

	public void setTranslateModeZ(boolean isTranslateModeZ) {
		this.isTranslateModeZ = isTranslateModeZ;
	}

	public void setTranslateModeY(boolean isTranslateModeY) {
		this.isTranslateModeY = isTranslateModeY;
	}

	public void setTranslateModeX(boolean isTranslateModeX) {
		this.isTranslateModeX = isTranslateModeX;
	}

	public void setRotationModeY(boolean isRotationModeY) {
		this.isRotationModeY = isRotationModeY;
	}

	/**
	 * ����ImageView��Ч
	 * 
	 * @param isAlpha
	 */
	@Deprecated
	public void setAlphaMode(boolean isAlpha) {

		isAlphaMode = isAlpha;

	}

	private int getCenterOfCoverflow() {

		// return (getWidth() - getPaddingLeft() - getPaddingRight())/2 +
		// getPaddingLeft();
		return (getWidth() + getPaddingLeft() - getPaddingRight()) >> 1;

	}

	private static int getCenterOfView(View view) {

		return view.getLeft() + (view.getWidth() >> 1);

	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		canvas.setDrawFilter(filter);
		return super.drawChild(canvas, child, drawingTime);
	}

	protected int getChildDrawingOrder(int childCount, int i) {
		// int selectedIndex = mSelectedPosition - mFirstPosition;
		int selectedIndex = this.getSelectedItemPosition()
				- this.getFirstVisiblePosition();
		// Just to be safe
		if (selectedIndex < 0)
			return i;

		if (i == childCount - 1) {
			// Draw the selected child last
			return selectedIndex;
		} else if (i >= selectedIndex) {
			// Move the children after the selected child earlier one
			return childCount - (i - selectedIndex) - 1;
			// return i + 1;
		} else {
			// Keep the children before the selected child the same
			return i;
		}
	}

	/**
	 * 
	 * �������ν���ڴ�С�Ĳ���ʱ,��һ�۵��Ѿ������˸ı䡣��� ��ֻ����ӵ���ͼ���,���˽���ɵĹ��� ��ֵ��Ϊ0��
	 * 
	 * @param w
	 *            Current width of this view.
	 * @param h
	 *            Current height of this view.
	 * 
	 * @param oldw
	 *            Old width of this view.
	 * @param oldh
	 *            Old height of this view.
	 */

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		mCoveflowCenter = getCenterOfCoverflow();

		super.onSizeChanged(w, h, oldw, oldh);

	}

	/**
	 * ChildView �仯���� (non-Javadoc)
	 * 
	 * @see android.widget.Gallery#getChildStaticTransformation(android.view.View,
	 *      android.view.animation.Transformation)
	 */
	protected boolean getChildStaticTransformation(View child, Transformation t) {

		final int childCenter = getCenterOfView(child);

		float rotationAngle = 0;

		t.clear();

		t.setTransformationType(Transformation.TYPE_MATRIX);// �����任ΪMatrix

		if (childCenter == mCoveflowCenter) {// view�����Ĳ���仯

			transformImageBitmap(child, t, 0);

		} else {

			/* ������Ҫ�仯���������ļ��/��View��ȣ�*��󻡶� */
			rotationAngle = ((float) (mCoveflowCenter - childCenter) / mCoveflowCenter);

			transformImageBitmap(child, t, rotationAngle);

		}

		return true;

	}

	/**
	 * ����view�ľ���任ʵ��
	 * 
	 * @param child
	 *            Item view
	 * @param t
	 * @param rotationAngle
	 */
	private void transformImageBitmap(View child, Transformation t,
			float rotationAngle) {

		mCamera.save();

		final Matrix imageMatrix = t.getMatrix();

		final int imageHeight = child.getLayoutParams().height;

		final int imageWidth = child.getLayoutParams().width;

		final float rotation = Math.abs(rotationAngle);

		// ��Z���������ƶ�camera���ӽǣ�ʵ��Ч��Ϊ�Ŵ�ͼƬ��
		// �����Y�����ƶ�����ͼƬ�����ƶ���X���϶�ӦͼƬ�����ƶ���

		if (isTranslateModeZ) {// z����
			float zoomAmount = mMaxTranslateValueZ * rotation;
			mCamera.translate(0.0f, 0.0f, zoomAmount);
		}
		if (isTranslateModeX) {// xƽ��
			float zoomAmount = mMaxTranslateValueX * rotationAngle;
			mCamera.translate(zoomAmount, 0.0f, 0.0f);
		}

		if (isTranslateModeY) {// yƽ��
			float zoomAmount = mMaxTranslateValueY * rotation;
			mCamera.translate(0.0f, zoomAmount, 0.0f);
		}

		if (isAlphaMode) {// 0͸����255Ϊȫ��͸��
		// ((ImageView) (child)).setAlpha((int) (255 - rotation * 2.5));
		}

		// ��Y������ת����ӦͼƬ�������﷭ת��
		// �����X������ת�����ӦͼƬ��X��������﷭ת��
		// ��ͬ����ϲ�ͬ��Ч��
		if (isRotationModeY) {
			float zoomAmount = mMaxRotationAngleY * rotationAngle
					* mCoveflowCenter / imageWidth;
			zoomAmount = Math.max(zoomAmount, -mMaxRotationAngleY);
			zoomAmount = Math.min(zoomAmount, mMaxRotationAngleY);
			mCamera.rotateY(zoomAmount);
		}

		mCamera.getMatrix(imageMatrix);

		// ����ƽ�Ʋ���
		imageMatrix.preTranslate(-(imageWidth >> 1), -(imageHeight >> 1));

		imageMatrix.postTranslate((imageWidth >> 1), (imageHeight >> 1));

		mCamera.restore();

	}
	private void parseAttributes(final Context context, final AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CoverFlow);
        try {
        	mMaxRotationAngleY = a.getDimension(R.styleable.CoverFlow_mMaxRotationAngleY, mMaxRotationAngleY);
        	mMaxTranslateValueZ = a.getDimension(R.styleable.CoverFlow_mMaxTranslateValueZ, mMaxTranslateValueZ);
        	mMaxTranslateValueY = a.getDimension(R.styleable.CoverFlow_mMaxTranslateValueY, mMaxTranslateValueY);
        	mMaxTranslateValueX = a.getFloat(R.styleable.CoverFlow_mMaxTranslateValueX,mMaxTranslateValueX);
        	isTranslateModeZ = a.getBoolean(R.styleable.CoverFlow_isTranslateModeZ, false);
        	isTranslateModeY = a.getBoolean(R.styleable.CoverFlow_isTranslateModeY, false);
        	isTranslateModeX = a.getBoolean(R.styleable.CoverFlow_isTranslateModeX, false);
        	isRotationModeY = a.getBoolean(R.styleable.CoverFlow_isRotationModeY, false);
        } finally {
            a.recycle();
        }
    }

}