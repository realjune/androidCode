
public class Test {
<<<<<<< HEAD
	public static void main(String[] args) {
		 System.out.printf("%d=%x=%s=\n",((1)),((1)),Integer.toBinaryString((1)));
		 System.out.printf("%d=%x=%s=\n",((-1)),((-1)),Integer.toBinaryString((-1)));
		 System.out.printf("%d=%x=%s=\n",((2)),((2)),Integer.toBinaryString((2)));
		 System.out.printf("%d=%x=%s=\n",((-2)),((-2)),Integer.toBinaryString((-2)));
		 System.out.printf("%d=%x\n",((byte)0xfe),((byte)0xfe));
		 
		 System.out.println("暗香".compareTo("春暖")+ " "+CharacterParser.getInstance().getSelling("暗香")+" "+CharacterParser.getInstance().getSelling("春暖"));
		 
		 System.out.println("a".compareTo("b"));
=======
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
>>>>>>> e169391046ca576dfad307e8ed35a44f24e94871
	}
}
