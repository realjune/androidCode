package w.a.animation;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/*
 Androidʵ��ͼƬ�ĵ�ӰЧ��  Android �� Matrix ����һ
 Android �Զ���Animation����  2011-08-19 10:30:59|  ���ࣺ android���� |  ��ǩ�� |�ٱ� |�ֺŴ�
 ��
 С ���� 
 ͨ����дAnimation�� applyTransformation (float interpolatedTime, Transformation t)������ʵ���Զ��嶯��Ч����
 ����һ��Ҳ��ʵ�� initialize (int width, int height, int parentWidth, int parentHeight)������
 ����һ���ص���������AnimationĿ��View�Ĵ�С��������������Գ�ʼ��һЩ��صĲ�����
 �������ö�������ʱ�䡢����Interpolator�����ö����Ĳο���ȡ��ڻ��ƶ����Ĺ����лᷴ���ĵ���applyTransformation ������
 ÿ�ε��ò���interpolatedTimeֵ����仯���ò�����0����Ϊ1�����ò���Ϊ1ʱ��������������
 ͨ������Transformation ����ȡ�任�ľ���matrix����ͨ���ı����Ϳ���ʵ�ָ��ָ��ӵ�Ч����
 */
public class MyAnimation extends Animation {
	int mCenterX, mCenterY;

	public MyAnimation() {
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// ͨ��Matrix.setScale���������ţ��ú�����������������X��Y���������ӣ�����interpolatedTime�Ǵ�0��1�仯��������ʵ�ֵ�Ч�����ǿؼ�����С�𽥱仯�����
		Matrix matrix = t.getMatrix();
		matrix.setScale(interpolatedTime, interpolatedTime);
		// Matrix ����ʵ�ָ��ָ��ӵı任

		// preTranslate������������ǰ�ƶ���postTranslate����������ɺ��ƶ���
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		// ��ʼ���м�����
		mCenterX = width / 2;
		mCenterY = height / 2;

		// ���ñ任������ʱ��2500���룬Ȼ������InterpolatorΪLinearInterpolator������FillAfterΪtrue���������ڶ���������ʱ�򱣳ֶ����������ԡ�
		setDuration(2000);
		setFillAfter(true);
		setInterpolator(new LinearInterpolator());
	}
}
