public class NumberTok extends Token {
	/* classe NumberTok: è una sottoclasse di Token, la quale, 
	oltre che ereditare il parametro tag da token, aggiunge un ulteriore parametro lexeme*/
	public final int val;
	
	public NumberTok(int tag, int v) {    //costruttore di Number: richiama il costruttore in Token con la chiamata super() e inizializza in nuovo campo lexeme*/
		super(tag); 
		val=v; 
	}
	
	public String toString() { 
		return "<" + tag + ", " + val + ">"; 
	}
}