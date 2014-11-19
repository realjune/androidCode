package com.mog.EncDemo;

public class TimerRecord implements Runnable {
	 //����һ���߳�
	 Thread thread;
	 //����ֹͣ�̵߳ı�־
	 private boolean flag=true;
	 private long time = 0 ; 
	 public TimerRecord()
	 {
		 thread=new Thread(this);
	 }
	 //��Ϊ����ʵ����Runnable���ͱ�����run���������߳�����ʱ����������run����
	 public void run()
	 {
		 //��õ�ǰ��ʱ�䣬����Ϊ��λ
		 long beginTime=System.currentTimeMillis();
		 //�����ѹ�ȥ��ʱ��
		 time=0;
		 while(flag)
		 {
		   //����дʵ�ּ�ʱ�Ĵ���
		   //���������ѹ�ȥ��ʱ��
		   time=System.currentTimeMillis()-beginTime;
		   //System.out.println("�ѹ�"+time+"����");
		   
		   //��ͣ�߳�1����,����ͣ�Ļ����԰��������ȥ��
		   try{
		    Thread.sleep(1);
		   }catch(InterruptedException e1){
		    e1.printStackTrace();
		   }
	  	}
	 }
	 //�����������̵߳ķ�����Ҳ���������߳�
	 public void start()
	 {
		 thread.start();
	 }
	 //��������ͣ�ķ�������ͣ�߳�
	 public void Pause()
	 {
		 try
		 {
			 thread.wait();
		 }
		 catch(InterruptedException e)
		 {
			 e.printStackTrace();
		 }
	 }
	 //�����Ǽ����ķ���,�����߳�
	 public void Resume()
	 {
		 thread.notifyAll();
	 }
	 //ֹͣ�߳�
	 public long stop()
	 {
	  //��flag���false������run�е�whileѭ���ͻ�ֹͣѭ��
		 flag=false;
		 return time ; 
	 }
}
