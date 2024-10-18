import java.io.*;

public class Parser {
	private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
		lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else 
			error("syntax error");
    }

    public void start() {
		switch(look.tag){
			//<expr>
			case '(':
			case Tag.NUM:
				expr();
				match(Tag.EOF);
				break;
			default:
				error("Error in grammar <start>");
		}
    }

    private void expr() {
		switch(look.tag){
			//<expr> -> <term><exprp>
			case '(':
			case Tag.NUM:
				term();
				exprp();
				break;
			default:
				error("Error in grammar <expr>");
		}
    }

    private void exprp() {
		switch (look.tag) {
			case '+':
				//<expr> -> +<term><exprp>
				match('+');
				term();
				exprp();
				break;
			case '-':
				//<expr> -> -<term><exprp>
				match('-');
				term();
				exprp();
				break;
			case ')':
			case Tag.EOF:
				break;
			default:
				error("Error in grammar <exprp>");
		}
    }

    private void term() {
        switch(look.tag){
			//<term> -> <fact><termp>
			case '(':
			case Tag.NUM:
				fact();
				termp();
				break;
			default:
				error("Error in grammar <term>");
		}
    }

    private void termp() {
        switch(look.tag){
			case '*':
				//<termp> -> *<fact><termp>
				match('*');
				fact();
				termp();
				break;
			case '/':
				//<termp> -> /<fact><termp>
				match('/');
				fact();
				termp();
				break;
			case '+':
			case '-':
			case ')':
			case Tag.EOF:
				break;
			default:
				error("Error in grammar <termp>");
		}
    }

    private void fact() {
        switch(look.tag){
			case '(':
				//<fact> -> (<expr>)
				match('(');
				expr();
				match(')');
				break;
			case Tag.NUM:
				//<fact> -> (<num>)
				match(Tag.NUM);
				break;
			default:
				error("Error in grammar <fact>");
		}
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}