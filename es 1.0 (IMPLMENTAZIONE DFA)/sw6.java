
/*Esercizio 1.6. Progettare e implementare un DFA con alfabeto {a, b} che riconosca il linguaggio
delle stringhe tali che a occorre almeno una volta in una delle prime tre posizioni della stringa.
Il DFA deve accettare anche stringhe che contengono meno di tre simboli (ma almeno uno dei
simboli deve essere a).
Esempi di stringhe accettate: “abb”, “abbbbbb”, “bbaba”, “baaaaaaa”, “aaaaaaa”, “a”, “ba”,
“bba”, “aa”, “bbabbbbbbbb”
Esempi di stringhe non accettate: “bbbababab”, “b”
*/

public class sw6
{
	public static boolean scan(String s){
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char c = s.charAt(i++);
				
				switch (state) {
                    case 0:
                        if(c== 'b'){
                            state = 1;
                        }else if(c=='a'){
                            state = 3;
                        }
                        break;
                    case 1:
                        if(c== 'b'){
                        state = 2;
                        }
                        else if (c=='a'){
                        state = 3;
                        }
                        break;
                    case 2:
                        if(c=='a'){
                        state = 3;
                         }
                        else if(c== 'b'){
                        state = -1;
                         }
                         break;
                    case 3:
                        if(c=='a'||c== 'b')
                        state =3;
                }
            
            }  
        return state ==3;
    }	
	public static void main(String[] args)
	{
		System.out.println(scan("abbb") ? "OK" : "NOPE");
		System.out.println(scan("bba") ? "OK" : "NOPE");
		System.out.println(scan("bbbba") ? "OK" : "NOPE");
		System.out.println(scan("b") ? "OK" : "NOPE");
		System.out.println(scan("aa") ? "OK" : "NOPE");
	}
}