
public class Test {
	public static void main(String[]args){
		System.out.println("5+0xffffffff="+((~-5)));
		System.out.println("5+0xffffffff="+((byte)0xff));
		System.out.println("getx="+getX());
		
	}
	
	public static int getX(){
		int x=1;
		try{
			System.out.println("intry i="+x);
//			throw new Exception();
			return x;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			++x;
			System.out.println("in finally x="+x);
			return x;
		}
//		System.out.println("before end x="+x);
//		return x;
	}
}
