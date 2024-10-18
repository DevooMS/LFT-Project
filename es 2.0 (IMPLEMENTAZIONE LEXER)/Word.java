// classe word: è una sottoclasse di token, la quale, oltre che ereditare il parametro tag da token, aggiunge un ulteriore parametro lexeme



public class Word extends Token {
    public String lexeme = "";										       //leseme assegnato alla parola
	public Word(int tag, String s) { super(tag); lexeme=s; }		       //costruttore di Word: richiama il costruttore in Token con la chiamata super() e inizializza in nuovo campo lexeme
    public String toString() { return "<" + tag + ", " + lexeme + ">"; }   ///* come per la classe Token, vengono riservate alcune parole per la trsduzione.Nell' invocazione viene specificato il tag corrispondente e il lexeme
	public static final Word
	cond = new Word(Tag.COND, "cond"),
	seq = new Word(Tag.SEQ, "seq"),
	casetok = new Word(Tag.CASE, "case"),
	when = new Word(Tag.WHEN, "when"),
	then = new Word(Tag.THEN, "then"),
	elsetok = new Word(Tag.ELSE, "else"),
	whiletok = new Word(Tag.WHILE, "while"),
	dotok = new Word(Tag.DO, "do"),
	assign = new Word(Tag.ASSIGN, ":="),
	print = new Word(Tag.PRINT, "print"),
	read = new Word(Tag.READ, "read"),
	or = new Word(Tag.OR, "||"),
	and = new Word(Tag.AND, "&&"),
	lt = new Word(Tag.RELOP, "<"),
	gt = new Word(Tag.RELOP, ">"),
	eq = new Word(Tag.RELOP, "=="),
	le = new Word(Tag.RELOP, "<="),
	ne = new Word(Tag.RELOP, "<>"),
	ge = new Word(Tag.RELOP, ">=");   
	
 
}
