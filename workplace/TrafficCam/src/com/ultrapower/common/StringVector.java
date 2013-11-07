/**
 * Vector��Ҫ��������������͵Ķ��󣨰�����ͬ���ͺͲ�ͬ���͵Ķ��󣩡�
 * ������һЩ�����ʹ�û��������������ϵ�Ӱ�졣����Ҫ����Vector��������ص��������ġ�
 * ��һ��Vector�ṩ���̵߳İ�ȫ�������ܡ���ʹVector���е���෽��ͬ����
 * ����������Ѿ�ȷ�����Ӧ�ó����ǵ��̣߳���Щ������ͬ������ȫ����Ҫ�ˡ�
 * �ڶ�����Vector���Ҵ洢�ĸ��ֶ���ʱ������Ҫ���ܶ��ʱ��������͵�ƥ�䡣
 * ������Щ������ͬһ����ʱ����Щƥ�����ȫ����Ҫ�ˡ�
 * ��ˣ��б�Ҫ���һ�����̵߳ģ������ض����Ͷ������򼯺������Vector��
 */
package com.ultrapower.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author VictorZheng
 *
 */
public class StringVector 
{
	// �����transient��ʾ������Բ���Ҫ�Զ����л�
	private transient String[] data; 
	private int count; 
	public int size()
	{
		return data.length;//count;
	}
	public StringVector() 
	{ 
	    // default size is 10
		this(10);   
	}
	public StringVector(int initialSize) 
	{ 
		count=initialSize;
		data = new String[initialSize]; 
	} 
	public void add(String str) 
	{ 
		//	 ignore null strings 
		if(str == null) { return; } 
		ensureCapacity(count + 1); 
		data[count++] = str; 
	} 

	private void ensureCapacity(int minCapacity) 
	{ 
		int oldCapacity = data.length; 
		if (minCapacity > oldCapacity) 
		{ 
			String oldData[] = data; 
			int newCapacity = oldCapacity * 2; 
			data = new String[newCapacity]; 
			System.arraycopy(oldData, 0, data, 0, count); 
		} 
	} 
	public void remove(String str) 
	{ 
		if(str == null) 
		{
			return; // ignore null str   
		}
		for(int i = 0; i < count; i++) 
		{ 
			//	 check for a match 
			if(data[i].equals(str)) 
			{ 
				System.arraycopy(data,i+1,data,i,count-1); // copy data 
				//	 allow previously valid array element be gc'd 
				data[--count] = null; 
				return; 
			} 
		} 
	}
	
	public final String getStringAt(int index) 
	{ 
		if(index < 0) 
		{ return null; } 
		else if(index > count) 
		{ 
			return null; // index is > # strings 
		} 
		else 
		{ 
			return data[index]; // index is good  
		}
	}
	
	public synchronized void writeObject(java.io.DataOutputStream s) 
	throws java.io.IOException  
	{  
		//	 Write out array length 
		s.writeInt(count);  
		//	 Write out all elements in the proper order.   
		for (int i=0; i<count; i++) 
			s.writeUTF(data[i]);  
	} 
	
	public synchronized void readObject(java.io.DataInputStream s) 
	throws java.io.IOException, ClassNotFoundException   
	{
		System.out.println("Enter readObject");
		//	 Read in array length and allocate array   
		int arrayLength = s.readInt(); 
		System.out.println("StringVector count=" + arrayLength);
		data = new String[arrayLength];
		// ͬ��data�Ĵ�С
		count = arrayLength;
		//	 Read in all elements in the proper order.  
		for (int i=0; i<arrayLength; i++) 
		{
			data[i] = s.readUTF();
			System.out.println("����:" + data[i]);
		} 
	}
	
	
	public byte[] serialize()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try
		{
			writeObject(dos);
			baos.close();
			dos.close();
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
		}

		return baos.toByteArray();
	}
	
	public static StringVector deserialize(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		StringVector sv = new StringVector();
		
		try
		{
			sv.readObject(dis);
	
			bais.close();
			dis.close();
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
			sv = null;
		}
		finally
		{
		}
		return sv;
	}
}
