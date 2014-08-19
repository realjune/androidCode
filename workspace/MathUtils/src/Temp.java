import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Temp {

	public static void main(String[] args) { 
		//����һ�������ù̶��߳������̳߳� 
//		ExecutorService pool = Executors.newFixedThreadPool(2); 
//		ExecutorService pool = Executors.newSingleThreadExecutor(); 
//		ExecutorService pool = Executors.newCachedThreadPool();  
//		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2); 
		ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor(); 

		//����ʵ����Runnable�ӿڶ���Thread����ȻҲʵ����Runnable�ӿ� 
		Thread t1 = new MyThread(1); 
		Thread t2 = new MyThread(2); 
		Thread t3 = new MyThread(3); 
		Thread t4 = new MyThread(4); 
		Thread t5 = new MyThread(5); 
		//���̷߳�����н���ִ�� 
		pool.execute(t1); 
		pool.execute(t2); 
		pool.execute(t3); 
		pool.execute(t4); 
		pool.execute(t5); 
		pool.execute(t1);
		pool.schedule(t4, 1000, TimeUnit.MILLISECONDS); 
		pool.schedule(t5, 2000, TimeUnit.MILLISECONDS); 
		//�ر��̳߳� 
		pool.shutdown(); 
		} 
		}
class MyThread extends Thread{ 
	int n;
	MyThread(int n){
		this.n=n;
	}
	@Override 
	public void run() { 
	System.out.println(Thread.currentThread().getName()+"����ִ�С�����"+n); 
	} 
	} 
