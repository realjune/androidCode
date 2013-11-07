package com.math;


public class Value_scale {

	 /**�������ݷ�Χ����������̶�
	  * <pre>
	  * ��λ�̶�:һ��
	  * ���̶�����:7��
	  * <1��:(step:1)0,1
	  * <=7��:(step:1)1,2,3,4,5,6,7
	  * 8��:(step:2)1,3,5,7,9
	  * 29��:(step:5)1,6,11,16,21,26,31
	  * >��:(��)
	  * 3����:(step:15)1,16,31,56,71,96,111 ����(step:��):1,2,3
	  * ������:(step:n��)
	  * 
	  * 
	 * @param start		������ʼλ��
	 * @param end		���ݽ���λ��
	 * @param scaleBase	��λ�̶�
	 * @param isRound	 ��Ե�Ƿ����̶�
	 * @param maxScales	���̶�����
	 * @return
	 */
	public static long[] getListTime_scale(long start,long end,long scaleBase,boolean isRound,int maxScales){
		long datas[];
		long dataArea=end-start;
		long step=(dataArea)/maxScales;
		if(step<scaleBase){
			step=scaleBase;
		}
		step=step/10*10;//getDateField(step,10);
		datas=new long[(int) ((dataArea+step)/(step))];
//		System.out.println(" step: "+step);
		for(int i=0;start<end;++i,start+=step){
			datas[i]=start;
//			System.out.println(start);
		}
//		System.out.println("date:"+formatter.format(fromCalendar.getTimeInMillis()));
		return datas;
	}

}
