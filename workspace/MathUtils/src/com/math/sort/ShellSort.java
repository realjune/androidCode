package com.math.sort;

/**
 * ϣ�������˼���ҾͲ������ˣ��������Javaʵ�֣���������������Ƚϡ���Ϊϣ�������ǻ��ڲ������������������µ����ԣ��������˲��������ִ��Ч�ʣ�
 * �����������£���������֮��ʱ��ıȽ�
 * 
 * @author junxu.wang
 * 
 */
public class ShellSort {

	public static void main(String[] args) {
		int[] a = new int[100000];
		for (int i = 0; i < 100000; i++) {
			a[i] = (int) (Math.random() * 1000000);
		}

		long begin = System.currentTimeMillis();
		shellSort(a);
		long end = System.currentTimeMillis();
		System.out.println("ϣ�����򣬶�100000�������У��ķ�ʱ��Ϊ��" + (end - begin) + "s");
		// ��������
		int[] b = new int[100000];
		for (int i = 0; i < 100000; i++) {
			b[i] = (int) (Math.random() * 1000000);
		}
		begin = System.currentTimeMillis();
		insertSort(b);
		end = System.currentTimeMillis();
		System.out.println("�������򣬶�100000�������У��ķ�ʱ��Ϊ��" + (end - begin) + "s");
		// for(int i=0;i<a.length;i++){
		// System.out.println(a[i]);
		// }
	}

	/**
	 * ϣ�������ǻ��ڲ������������������µ����ԣ��������˲��������ִ��Ч��
	 * 
	 * @param a
	 * @return
	 */
	private static void shellSort3(int[] a) {
		int h = 1;
		int temp;
		int inner, outer;
		while (h <= a.length / 3)
			h = h * 3 + 1;
		while (h > 0) {
			for (outer = h; outer < a.length; outer++) {
				temp = a[outer];
				inner = outer;
				while (inner > h - 1 && a[inner - h] >= temp) {
					a[inner] = a[inner - h];
					inner -= h;
				}
				a[inner] = temp;
			}
			h = (h - 1) / 3;
		}
	}

	/**
	 * insert sort
	 * 
	 * @param b
	 */
	public static void insertSort(int b[]) {
		int temp, outer, inner;
		for (outer = 1; outer < b.length; outer++) {
			temp = b[outer];
			inner = outer;
			while (inner > 0 && b[inner - 1] > temp) {
				b[inner] = b[inner - 1];
				inner--;
			}
			b[inner] = temp;
		}
	}

	/**
	 * ���뷨insertion sort ���뷨��Ϊ���ӣ����Ļ�������ԭ���ǳ���ƣ���ǰ�������Ѱ����Ӧ��λ�ò��룬Ȼ�������һ��
	 * 
	 * @param pData
	 * @param Count
	 */
	void InsertSort(int[] pData, int Count) {
		int iTemp;
		int iPos;
		for (int i = 1; i < Count; i++) {
			iTemp = pData[i];// ����Ҫ�������
			iPos = i - 1;// ��������������ָ���
			while ((iPos >= 0) && (iTemp < pData[iPos])) {// �����һ����������֣���ʼ�Աȣ�������������������λ
				pData[iPos + 1] = pData[iPos];
				iPos--;
			}
			pData[iPos + 1] = iTemp;// �������ֵ�λ��
		}
	}

	public static void shellSort(int[] a) {
		// ϣ������
		int d = a.length;
		while (true) {
			d = d / 2;
			for (int x = 0; x < d; x++) {
				for (int i = x + d; i < a.length; i = i + d) {
					int temp = a[i];
					int j;
					for (j = i - d; j >= 0 && a[j] > temp; j = j - d) {
						a[j + d] = a[j];
					}
					a[j + d] = temp;
				}
			}
			if (d == 1) {
				break;
			}
		}
		System.out.println();
		System.out.println("����֮��");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + "");
		}
	}
}
