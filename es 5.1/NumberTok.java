public class NumberTok extends Token {
	// ... completare ...
	public final int val;
	
	public NumberTok(int tag, int v) { 
		super(tag); 
		val=v; 
	}
	
	public String toString() { 
		return "<" + tag + ", " + val + ">"; 
	}
}
