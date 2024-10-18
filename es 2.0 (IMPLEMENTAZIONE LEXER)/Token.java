public class Token {  										// campo intero di token 'tag'
    public final int tag;
    public Token(int t) { tag = t;  }					    //costruttore della classe Token: inizializza tag
	public String toString() {return "<" + tag + ">";}		//metodo toString per l'output
	
	/* elenco di Token riservati: rappresentano gli operatori unari. Durante la traduzione da parte del Lexer, ogni volta
	che si incontra uno dei seguenti token viene ritornato il valore intero corrispondente alla codifica ASCII  relativa 
	a quel simbolo specifico */
	public static final Token
	not = new Token('!'),
	lpt = new Token('('),
	rpt = new Token(')'),
	lpg = new Token('{'),
	rpg = new Token('}'),
	plus = new Token('+'),
	minus = new Token('-'),
	mult = new Token('*'),
	div = new Token('/'),
	semicolon = new Token(';'),
	assign = new Token('=');
}
