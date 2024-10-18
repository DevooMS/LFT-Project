import java.io.*;
import java.util.*;

public class Translator {
		private Lexer lex;
		private BufferedReader pbr;
		private Token look;
		
		SymbolTable st = new SymbolTable();
		CodeGenerator code = new CodeGenerator();
		int count=0;

		public Translator(Lexer l, BufferedReader br) {
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
					int lnext_prog = code.newLabel();			//generazione etichetta fine programma
					statlist(lnext_prog);
					code.emitLabel(lnext_prog);					//emissione etichetta fine programma
					match(Tag.EOF);
					try {
						code.toJasmin();
					}
					catch(java.io.IOException e) {
						System.out.println("IO error\n");
					};
					break;
				default:
					error("Error in grammar <prog>");
			}
		}
		
		private void statlist(int lnext_prog){
			switch (look.tag){
				case '=':
				case '{':
				case Tag.PRINT:
				case Tag.READ:
				case Tag.COND:
				case Tag.WHILE:
					stat(lnext_prog);
					statlistp(lnext_prog);
					break;
				default:
					error("Error in grammar <statlist>");
			}
		}

		private void statlistp(int lnext_prog) {
			switch (look.tag) {
				case ';':
					match(';');
					stat(lnext_prog);
					statlistp(lnext_prog);
					break;
				case Tag.EOF:
				case '}':
					break;
				default:
					error("Error in grammar <statlistp>");
			}
		}
		
		private void stat(int lnext_prog) {
			int l_next,l_false,l_true,l_ris,i;
			List next;
			switch(look.tag) {
				case '=':												//gestione dell'assegnazione di un valore a una variabile
					match('=');					
					if (look.tag==Tag.ID) {				
						int id_addr = st.lookupAddress(((Word)look).lexeme);		//ricerca se la variabile è presente e le assegna il valore
						System.out.println("provaStringa "+((Word)look).lexeme);
						lnext_prog=code.newLabel();
						code.emitLabel(lnext_prog);
						if (id_addr==-1) {									//se non è presente crea un nuovo indirizzo	e le assegna il valore
							id_addr = count;
							st.insert(((Word)look).lexeme,count++);
						}                    
						match(Tag.ID);
						expr(lnext_prog);
						System.out.println("TESTPROVA"+id_addr);
						code.emit(OpCode.istore, id_addr);
					}else
						error("Error in grammar (stat) after =( with " + look);
					break;
				case Tag.PRINT:											//gestione stampa di una variabile
					match(Tag.PRINT);
					match('(');
					exprlist(lnext_prog, null);
					match(')');
					code.emit(OpCode.invokestatic,1);
					break;
				case Tag.READ:											//gestione lettura di un valore e assegnamento a una variabile
					match(Tag.READ);
					System.out.println("STO LEGGENDO");
					match('(');
					if (look.tag==Tag.ID) {
						int id_addr = st.lookupAddress(((Word)look).lexeme);	
						if (id_addr==-1) {
							id_addr = count;
							st.insert(((Word)look).lexeme,count++);
						}                    
						match(Tag.ID);
						match(')');
						
						code.emit(OpCode.invokestatic,0);
						code.emit(OpCode.istore,id_addr);   
					}
					else
						error("Error in grammar (stat) after read( with " + look);
					break;	
				case Tag.COND:
					match(Tag.COND);
					next = new ArrayList<Integer>();				//attributo ereditato di whenlist per salvare una o più etichette dopo l'istruzione cond
					l_next=whenlist(lnext_prog, next);   
					match(Tag.ELSE);
					stat(lnext_prog);
					for(i=0;i<next.size();i++)
						code.emitLabel((Integer)next.get(i));	//emissione delle etichette a cui saltare dopo l'istruzione cond
					break;
				case Tag.WHILE:
					match(Tag.WHILE);
					lnext_prog=code.newLabel();
					System.out.println("ProvaLNEXT "+ lnext_prog);		//etichetta inizio while
					code.emitLabel(lnext_prog);
					l_next = code.newLabel();		//generazione etichetta salto
					match('(');		
					l_false=bexpr(l_next);			//generazione etichetta uscita (condizione falsa)
					match(')');
					code.emitLabel(l_next);
					stat(lnext_prog);
					code.emit(OpCode.GOto,lnext_prog);		//salto a inizio while
					code.emitLabel(l_false);				//etichetta uscita while
					break;
				case '{':
					match('{');
					statlist(lnext_prog);
					match('}');
					break;
				default:
					error("Error in grammar <stat>");
			}
		 }

		private int whenlist(int lnext_prog, List next) {
			
			int l_next;
			switch (look.tag) {
				case Tag.WHEN:
				
					lnext_prog=code.newLabel();
					System.out.println("ProvaLNEXT1 "+ lnext_prog);
					l_next=whenitem(lnext_prog);
					next.add(l_next);
					whenlistp(lnext_prog, next);
					
					break;
				default:
					error("Error in grammar <whenlist>");
					l_next=0;
			}
			return l_next;
		}
	
		private void whenlistp(int lnext_prog, List next) {
			switch (look.tag) {
				case Tag.WHEN:	
					lnext_prog = code.newLabel();
					lnext_prog=whenitem(lnext_prog); 
					next.add(lnext_prog);
					whenlistp(lnext_prog, next);
					break;
				case Tag.ELSE:
					break;
				default:
					error("Error in grammar <whenlistp>");
			}
		}
		
		private int whenitem(int lnext_prog) {
			
			int l_next;
			switch (look.tag) {
				case Tag.WHEN:
					match(Tag.WHEN);
					match('(');
					l_next=bexpr(lnext_prog);
					code.emitLabel(lnext_prog);
					match(')');
					match(Tag.DO);
					lnext_prog = code.newLabel();
					stat(lnext_prog);
					code.emit(OpCode.GOto,lnext_prog);
					code.emitLabel(l_next);
					break;
				default:
					error("Error in grammar <whenitem>");
			}
			return lnext_prog;
			
		}
		
		private int bexpr(int lnext_prog) {
			OpCode op = null;
			switch (look.tag) {
				case Tag.RELOP:
					if (((Word) look).lexeme.compareTo("==")==0)
						op = OpCode.if_icmpeq;
					else if (((Word) look).lexeme.compareTo("<>")==0)
						op = OpCode.ifne;
					else if (((Word) look).lexeme.compareTo("<=")==0)
						op = OpCode.if_icmple;
					else if (((Word) look).lexeme.compareTo(">=")==0)
						op = OpCode.if_icmpge;
					else if (((Word) look).lexeme.compareTo("<")==0)
						op = OpCode.if_icmplt;
					else if (((Word) look).lexeme.compareTo(">")==0)
						op = OpCode.if_icmpgt;
					else 
						error("Error in grammar <bexpr>");
					match(Tag.RELOP);
					expr(lnext_prog);
					expr(lnext_prog);
                    code.emit(op,lnext_prog);
					lnext_prog = code.newLabel();
                    code.emit(OpCode.GOto,lnext_prog);   //<<? condizione di uscita ?
					break;
				default:
					error("Error in grammar <bexpr>");
			}
			return lnext_prog;
		}
		
		private void expr(int lnext_prog) {
			int id_addr;
			switch(look.tag) {
				case '+':
					match('+');
					match('(');
					exprlist(lnext_prog, OpCode.iadd);
					match(')');
					break;
				case '-':
					match('-');
					expr(lnext_prog);
					expr(lnext_prog);
					code.emit(OpCode.isub);
					break;
				case '*':
					match('*');
					match('(');
					exprlist(lnext_prog, OpCode.imul);
					match(')');
					break;
				case '/':
					match('/');
					expr(lnext_prog);
					expr(lnext_prog);
					code.emit(OpCode.idiv);
					break;
				case Tag.NUM:
					id_addr = count;
					System.out.println("NUM "+((NumberTok)look).val+"COUNT "+count);
					st.insert(Integer.toString(((NumberTok)look).val),count++);                  
					code.emit(OpCode.ldc,((NumberTok)look).val); 
					match(Tag.NUM);
					break;
				case Tag.ID:
					id_addr = st.lookupAddress(((Word)look).lexeme);
					if (id_addr==-1) {
						error("Error in grammar <expr>");
					}else{                    
						match(Tag.ID);
						code.emit(OpCode.iload,id_addr); 
					}
					break;
				default:
						error("Error in grammar <expr>");
			}
		}
		
		private void exprlist(int lnext_prog, OpCode label) {
			switch (look.tag) {
				case '+':
				case '*':
				case '-':
				case '/':
				case Tag.NUM:
				case Tag.ID:
					expr(lnext_prog);
					exprlistp(lnext_prog, label);
					break;
				default:
					error("Error in grammar <exprlist>");
			}
		}
	
		private void exprlistp(int lnext_prog, OpCode label) {
			switch (look.tag) {
				case '+':
				case '*':
				case '-':
				case '/':
				case Tag.NUM:
				case Tag.ID:
					expr(lnext_prog);
					if(label == OpCode.iadd)
						code.emit(OpCode.iadd);
					else if(label == OpCode.imul)
						code.emit(OpCode.imul);
					exprlistp(lnext_prog, label);
					break;
				case ')':
					break;
				default:
					error("Error in grammar <exprlistp>");
			}
		}
		
		public static void main(String[] args) {
			Lexer lex = new Lexer();
			String path = "./prova.lft"; // il percorso del file da leggere
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				Translator translator = new Translator(lex, br);
				translator.prog();
				System.out.println("Input OK");
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}