
public class Test {
	public static void main(String[] args) {
		 System.out.printf("%d=%x=%s=\n",((1)),((1)),Integer.toBinaryString((1)));
		 System.out.printf("%d=%x=%s=\n",((-1)),((-1)),Integer.toBinaryString((-1)));
		 System.out.printf("%d=%x=%s=\n",((2)),((2)),Integer.toBinaryString((2)));
		 System.out.printf("%d=%x=%s=\n",((-2)),((-2)),Integer.toBinaryString((-2)));
		 System.out.printf("%d=%x\n",((byte)0xfe),((byte)0xfe));
		 
		 System.out.println("暗香".compareTo("春暖")+ " "+CharacterParser.getInstance().getSelling("暗香")+" "+CharacterParser.getInstance().getSelling("春暖"));
		 
		 System.out.println("a".compareTo("b"));
	}
}
