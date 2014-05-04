package com.math.sort;

import java.util.Arrays;

public class MergeSort {

	public static void mergeSort(int[] data) {
		System.out.println("��ʼ����");
		sort(data, 0, data.length - 1);
	}

	/*
	 * ��������left��right��Χ������Ԫ�ؽ��й鲢���� data ���������� left ����������ĵ�һ��Ԫ������ right
	 * ��������������һ��Ԫ������
	 */
	private static void sort(int[] data, int left, int right) {
		System.out.println("left "+left+" right "+right);
		// TODO Auto-generated method stub
		if (left < right) {
			// �ҳ��м�����
			int center = (left + right)>>1;
			// �����������еݹ�
			sort(data, left, center);
			// ���ұ�������еݹ�
			sort(data, center + 1, right);
			// �ϲ�
			merge(data, left, center, right);

		}
	}

	/**
	 * <pre>
	 * ������������й鲢���鲢ǰ���������Ѿ����򣬹鲢����Ȼ����
	 *  data �������
	 *  	left ������ĵ�һ��Ԫ�ص�����
	 *  	center����������һ��Ԫ�ص�������center+1���������һ��Ԫ�ص����� 
	 *  	right ����������һ��Ԫ�ص�����
	 */
	private static void merge(int[] data, int left, int center, int right) {
		// TODO Auto-generated method stub
		int[] tmpArr = new int[data.length];
		int mid = center + 1;
		// third��¼�м����������
		int third = left;
		int tmp = left;
		while (left <= center && mid <= right) {
			// ������������ȡ����С�ķ����м�����
			if (data[left] <= data[mid]) {
				tmpArr[third++] = data[left++];
			} else {
				tmpArr[third++] = data[mid++];
			}
		}
		// ʣ�ಿ�����η����м�����
		while (mid <= right) {
			tmpArr[third++] = data[mid++];
		}
		while (left <= center) {
			tmpArr[third++] = data[left++];
		}
		// ���м������е����ݸ��ƻ�ԭ����
		while (tmp <= right) {
			data[tmp] = tmpArr[tmp++];
		}
		System.out.println(Arrays.toString(data));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] data = { 21, 30, 49, 30, 16, 9, -16, 10, 25, 18 };
		System.out.println("����֮ǰ��\n" + Arrays.toString(data));
		mergeSort(data);
		System.out.println("����֮��\n" + Arrays.toString(data));
	}

}
