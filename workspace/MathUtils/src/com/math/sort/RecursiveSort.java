package com.math.sort;

public class RecursiveSort {
		public static void main(String[] args) {
			String[] a= {"9","8","7","6","5","4","3","2","1"};
			Object[] aux = (Object[])a.clone();
			mergeSort(aux, a);
			for(int i=0;i<a.length;i++){
				System.out.println(a[i]);
			}
		}

	    //ÿ����ֵ��б�Ԫ�ظ���<=3
	    private static final int INSERTIONSORT_THRESHOLD = 3;
	    /**
	     * 
	     * �鲢����(�ǵݹ�ʵ��)
	     */
	    public static void mergeSort(Object[] src,Object[] dest){
	    	int spreCount = INSERTIONSORT_THRESHOLD;	 
	    	int low,mid,high;
	    	int len = src.length;
			if(len <= INSERTIONSORT_THRESHOLD*2){		//���ֻ�ܻ���Ϊ���飬ֱ������
				sort(dest,0,len);
				return;
			}
	    	while(spreCount < len){
		    	for(int i=0;i<len;i=high){	//��������鲢���������б�
		    		low = i;	
		    		high = low + spreCount * 2 ;
		    		mid = low + spreCount;
		    		if(high >= len){
		    			high = len;
		    		}
		    		int l = high - low;
		    		if(l <= INSERTIONSORT_THRESHOLD){
		    			sort(src,low,high);
		    			break;
		    		}

		    		if(spreCount == 3){		//���в�ֵ��б�ֻ����һ������
		    			sort(dest,low,mid);
		    			sort(dest,mid,high);
		    		}
		    		if(l == len)	//���һ�ι鲢��src�д���ĸ���dest
		    			Merge(src,dest,low,mid,high);
		    		else
		    			Merge(dest,src,low,mid,high);

		    	}
		    	spreCount *= 2;
	    	}
	    	
	    }
	    //�Բ�ֵ�ÿ���б��������
	    private static void sort(Object[] dest,int low,int high){
			for (int i = low; i < high; i++){
				for (int j = i; j > low ; j--){
					if(((Comparable) dest[j - 1]).compareTo(dest[j]) > 0){
						swap(dest, j-1, j); 
					}
				}
			}
	    }
	    
	    //�鲢���������б�������dest��
		private static void Merge(Object[] src, Object[] dest, int low, int mid,
				int high) {
	    	int i = low;
	    	int p = low;	//��һ���б�ָ��
	    	int q = mid;    //�ڶ����б�ָ��
	    	while(p < mid && q <high){
	    		if(((Comparable) src[p]).compareTo(src[q]) <= 0){
	    			dest[i++] = src[p++];
	    		}else{
	    			dest[i++] = src[q++];
	    		}
	    	}
	    	//���ʣ���ֵ
	    	while(p < mid && i<high){
	    		dest[i++] = src[p++];
	    	}
	    	while(q < high && i<high){
	    		dest[i++] = src[q++];
	    	}
			
		}
		
	    private static void swap(Object[] x, int a, int b) {
	    	Object t = x[a];
	    	x[a] = x[b];
	    	x[b] = t;
	    }
	 

	}