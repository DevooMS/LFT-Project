import java.io.*; 
//((NumberTok)look).value

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) { 
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
		int expr_val;
		
		switch(look.tag){
			//<start> -> <expr>
			case '(':
			case Tag.NUM:
				expr_val = expr();
				match(Tag.EOF);
				System.out.println(expr_val);	
				break;
			default:
				error("Error in grammar <start>");
		}
    }

    private int expr() { 
		int term_val, exprp_val;
		
		switch(look.tag){
			//<expr> -> <term><exprp>
			case '(':
			case Tag.NUM:
				term_val = term();
				exprp_val = exprp(term_val);
				break;
			default:
				error("Error in grammar <expr>");
				return Integer.parseInt(null);
		}
		
		return exprp_val;
    }

    private int exprp(int exprp_i) {
		int term_val, exprp_val;
		
		switch (look.tag) {
			case '+':
				//<expr> -> +<term><exprp>
				match('+');
				term_val = term();
				exprp_val = exprp(exprp_i + term_val);
				break;
			case '-':
				//<expr> -> -<term><exprp>
				match('-');
				term_val = term();
				exprp_val = exprp(exprp_i - term_val);
				break;
			case ')':
			case Tag.EOF:
				exprp_val = exprp_i;
				break;
			default:
				error("Error in grammar <exprp>");
				return Integer.parseInt(null);
		}
		
		return exprp_val;
    }

    private int term() { 
		int fact_val, termp_val;
		
		switch(look.tag){
			//<term> -> <fact><termp>
			case '(':
			case Tag.NUM:
				fact_val = fact();
				termp_val = termp(fact_val);
				break;
			default:
				error("Error in grammar <term>");
				return Integer.parseInt(null);
		}
		
		return termp_val;
    }
    
    private int termp(int termp_i) { 
		int fact_val, termp_val;
		
		switch(look.tag){
			case '*':
				//<termp> -> *<fact><termp>
				match('*');
				fact_val = fact();
				termp_val = termp(termp_i * fact_val);
				break;
			case '/':
				//<termp> -> /<fact><termp>
				match('/');
				fact_val = fact();
				termp_val = termp(termp_i / fact_val);
				break;
			case '+':
			case '-':
			case ')':
			case Tag.EOF:
				termp_val = termp_i;
				break;
			default:
				error("Error in grammar <termp>");
				return Integer.parseInt(null);
		}
		
		return termp_val;
    }
    
    private int fact() { 
		int fact_val, expr_val;
		
		switch(look.tag){
			case '(':
				//<fact> -> (<expr>)
				match('(');
				expr_val = expr();
				fact_val = expr_val;
				match(')');
				break;
			case Tag.NUM:
				//<fact> -> (<num>)
				fact_val = ((NumberTok)look).val;
				match(Tag.NUM);
				break;
			default:
				error("Error in grammar <fact>");
				return Integer.parseInt(null);
		}
		
		return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./prova1.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}