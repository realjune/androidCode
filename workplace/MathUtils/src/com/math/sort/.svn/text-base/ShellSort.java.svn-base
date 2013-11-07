package com.math.sort;

/**
 * 希尔排序的思想我就不叙述了，这里，我用Java实现，并与插入排序做比较。因为希尔排序是基于插入排序，由于增加了新的特性，大大提高了插入排序的执行效率，
 * 完整代码如下，并有两者之间时间的比较
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
		System.out.println("希尔排序，对100000个数排列，耗费时间为：" + (end - begin) + "s");
		// 插入排序
		int[] b = new int[100000];
		for (int i = 0; i < 100000; i++) {
			b[i] = (int) (Math.random() * 1000000);
		}
		begin = System.currentTimeMillis();
		insertSort(b);
		end = System.currentTimeMillis();
		System.out.println("插入排序，对100000个数排列，耗费时间为：" + (end - begin) + "s");
		// for(int i=0;i<a.length;i++){
		// System.out.println(a[i]);
		// }
	}
	/**希尔排序是基于插入排序，由于增加了新的特性，大大提高了插入排序的执行效率
	 * @param a
	 * @return
	 */
	private static void shellSort(int[] a) {
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
	/**insert sort
	 * @param b
	 */
	public static void insertSort(int b[]){
		int temp,outer,inner;
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
}
