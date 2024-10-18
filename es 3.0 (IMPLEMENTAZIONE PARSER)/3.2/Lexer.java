import java.io.*; 

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
	boolean comment = false;
	
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') 
				line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

			// ... gestire i casi di (, ), {, }, +, -, *, /, ; ... //
			
			case '(':
                peek = ' ';
                return Token.lpt;
				
			case ')':
                peek = ' ';
                return Token.rpt;
			
			case '{':
                peek = ' ';
                return Token.lpg;
				
			case '}':
                peek = ' ';
                return Token.rpg;
			
			case '+':
                peek = ' ';
                return Token.plus;
			
			case '-':
                peek = ' ';
                return Token.minus;
			
			case '*':
                peek = ' ';
                return Token.mult;
				
			case '/':
                readch(br);
                if(peek=='/'){										//gestisce il caso // 
					comment = true;
					while(comment){
						readch(br);
						if(peek == '\n' || peek == (char) -1)
							comment = false;
					}

					peek = ' ';
					return lexical_scan(br);
                }else if(peek=='*'){								//gestisce il caso /**/ 
					comment = true;
					
					readch(br);
					while(comment && peek != (char) -1){
						if(peek == '*'){
							readch(br);
							if(peek == '/'){
								comment = false;
							}
						}else
							readch(br);
					}
					
					if(!comment){
						peek = ' ';
						return lexical_scan(br);
					}//else if(peek == (char) -1)
						
					return null;
                }else{                                                    
                    return Token.div; 
                } 
				
			case ';':
                peek = ' ';
                return Token.semicolon;
				
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

			// ... gestire i casi di ||, <, >, <=, >=, ==, <>, = ... //
			case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }
				
			case '<':
                readch(br);
                if (peek == '='){
					peek = ' ';
					return Word.le;
				} else if (peek == '>'){
					peek = ' ';
					return Word.ne;
				} else
                    return Word.lt;
				
			case '>':
                readch(br);
                if (peek == '='){
					peek = ' ';
					return Word.ge;
				} else 
                    return Word.gt;
			
			case '=':
                readch(br);
				if (peek == '='){
					peek = ' ';
					return Word.eq;
				} else  
                    return Token.assign;
				
            case (char)-1:
                return new Token(Tag.EOF);

            default:
				String s = "";
                if (Character.isLetter(peek)) {
					// ... gestire il caso degli identificatori e delle parole chiave /
					s=s+Character.toString(peek);                                
					readch(br);
					
					while(Character.isDigit(peek) || Character.isLetter(peek) || peek=='_'){
						s=s+Character.toString(peek);
						readch(br);
					}

					//peek = ' ';
					
					if(s.equals(Word.cond.lexeme))
						return Word.cond;
					else if(s.equals(Word.when.lexeme))
						return Word.when;
					else if(s.equals(Word.then.lexeme))
						return Word.then;
					else if(s.equals(Word.elsetok.lexeme))
						return Word.elsetok;
					else if(s.equals(Word.whiletok.lexeme))
						return Word.whiletok;
					else if(s.equals(Word.dotok.lexeme))
						return Word.dotok;
					else if(s.equals(Word.seq.lexeme))
						return Word.seq;
					else if(s.equals(Word.print.lexeme))
						return Word.print;
					else if(s.equals(Word.read.lexeme))
						return Word.read;
					else
						return new Word(Tag.ID,s);
                } else if (Character.isDigit(peek)) {
					// ... gestire il caso dei numeri ... //
					s=s+Character.toString(peek);
					readch(br);
					
					if(s.length()==1 && Integer.parseInt(s)==0 && Character.isDigit(peek)){
                        System.err.println("Erroneous character: " + peek );
                        return null;
					}
					
					while(Character.isDigit(peek)){
						s=s+Character.toString(peek);
						readch(br);
					}

					return new NumberTok(Tag.NUM,Integer.parseInt(s));
                } else {
                        System.err.println("Erroneous character: " + peek );
                        return null;
                }
         }
	}
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
				if(tok != null)
					System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
            System.out.println("Errore di sintassi alla riga "+line+". Lettura terminata.");
        }
    }

}
