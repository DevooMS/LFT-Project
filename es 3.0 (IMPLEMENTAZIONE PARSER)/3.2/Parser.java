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

    public void prog() {
		switch (look.tag){
			case '=':
			case '{':
			case Tag.PRINT:
			case Tag.READ:
			case Tag.COND:
			case Tag.WHILE:
				statlist();
				match(Tag.EOF);
				break;
			default:
				error("Error in grammar <prog>");
		}
    }

    private void statlist() {
		switch (look.tag){
			case '=':
			case '{':
			case Tag.PRINT:
			case Tag.READ:
			case Tag.COND:
			case Tag.WHILE:
				stat();
				statlistp();
				break;
			default:
				error("Error in grammar <statlist>");
		}
    }

    private void statlistp() {
		switch (look.tag) {
			case ';':
				match(';');
				stat();
				statlistp();
				break;
			case Tag.EOF:
			case '}':
				break;
			default:
				error("Error in grammar <statlistp>");
		}
    }

    private void stat() {
		switch (look.tag) {
			case '=':
				match('=');
				match(Tag.ID);
				expr();
				break;
			case Tag.PRINT:
				match(Tag.PRINT);
				match('(');
				exprlist();
				match(')');
				break;
			case Tag.READ:
				match(Tag.READ);
				match('(');
				match(Tag.ID);
				match(')');
				break;
			case Tag.COND:
				match(Tag.COND);
				whenlist();
				match(Tag.ELSE);
				stat();
				break;
			case Tag.WHILE:
				match(Tag.WHILE);
				match('(');
				bexpr();
				match(')');
				stat();
				break;
			case '{':
				match('{');
				statlist();
				match('}');
				break;
			default:
				error("Error in grammar <stat>");
		}
	}

    private void whenlist() {
		switch (look.tag) {
			case Tag.WHEN:
				whenitem();
				whenlistp();
				break;
			default:
				error("Error in grammar <whenlist>");
		}
    }

    private void whenlistp() {
		switch (look.tag) {
			case Tag.WHEN:
				whenitem();
				whenlistp();
				break;
			case Tag.ELSE:
				break;
			default:
				error("Error in grammar <whenlistp>");
		}
    }
	
	private void whenitem() {
		switch (look.tag) {
			case Tag.WHEN:
				match(Tag.WHEN);
				match('(');
				bexpr();
				match(')');
				match(Tag.DO);
				stat();
				break;
			default:
				error("Error in grammar <whenitem>");
		}
    }
		
	private void bexpr() {
		switch (look.tag) {
			case Tag.RELOP:
				match(Tag.RELOP);
				expr();
				expr();
				break;
			default:
				error("Error in grammar <bexpr>");
		}
    }
	
	private void expr() {
		switch (look.tag) {
			case '+':
				match('+');
				match('(');
				exprlist();
				match(')');
				break;
			case '-':
				match('-');
				expr();
				expr();
				break;
			case '*':
				match('*');
				match('(');
				exprlist();
				match(')');
				break;
			case '/':
				match('/');
				expr();
				expr();
				break;
			case Tag.NUM:
				match(Tag.NUM);
				break;
			case Tag.ID:
				match(Tag.ID);
				break;
			default:
				error("Error in grammar <expr>");
		}
    }
	
	private void exprlist() {
		switch (look.tag) {
			case '+':
			case '*':
			case '-':
			case '/':
			case Tag.NUM:
			case Tag.ID:
				expr();
				exprlistp();
				break;
			default:
				error("Error in grammar <exprlist>");
		}
    }
	
	private void exprlistp() {
		switch (look.tag) {
			case '+':
			case '*':
			case '-':
			case '/':
			case Tag.NUM:
			case Tag.ID:
				expr();
				exprlistp();
				break;
			case ')':
				break;
			default:
				error("Error in grammar <exprlistp>");
		}
    }
	
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./prova5.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}