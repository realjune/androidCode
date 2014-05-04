package java.lang;

public class Boolean {
	
	private boolean value;

	public static final Boolean TRUE = new Boolean(true);

	public static final Boolean FALSE = new Boolean(false);

	public Boolean(boolean b) {

	}

	public boolean equals(java.lang.Object o) {
		return super.equals(o);
	}
	
	public boolean booleanValue()
	{
		return value;
	}

	public int hashCode() {
		return super.hashCode();
	}

	public String toString() {
		if(value)
			return "true";
		return "false";
	}
}
