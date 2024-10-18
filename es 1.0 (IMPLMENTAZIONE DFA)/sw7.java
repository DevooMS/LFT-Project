
/*Progettare e implementare un DFA con alfabeto {a, b} che riconosca il linguaggio
delle stringhe tali che a occorre almeno una volta in una delle ultime tre posizioni della stringa.
Come nell’esercizio 1.6, il DFA deve accettare anche stringhe che contengono meno di tre simboli
(ma almeno uno dei simboli deve essere a).
Esempi di stringhe accettate: “abb”, “bbaba”, “baaaaaaa”, “aaaaaaa”, “a”, “ba”, “bba”,
“aa”, “bbbababab”
Esempi di stringhe non accettate: “abbbbbb”, “bbabbbbbbbb”, “b”
*/

public class sw7
{
	public static boolean scan(String s){
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char c = s.charAt(i++);
				
				switch (state) {
                    case 0:
                        if(c== 'b'){
                            state = 0;
                        }else if(c=='a'){
                            state = 1;
                        }
                        break;
                    case 1:
                        if(c== 'a'){
                        state = 1;
                        }
                        else if (c=='b'){
                        state = 2;
                        }
                        break;
                    case 2:
                        if(c=='a'){
                        state = 1;
                         }
                        else if(c== 'b'){
                        state = 3;
                         }
                         break;
                    case 3:
                        if(c=='a'){
                        state =1;
                        }else if(c== 'b'){
                        state =0;
                        }
                        break;
                        
            
                }  
            }
        return state ==1||state ==2||state ==3;    
        
    }	
	public static void main(String[] args)
	{
		System.out.println(scan("abbbbb") ? "OK" : "NOPE");
		System.out.println(scan("bbabbbabbb") ? "OK" : "NOPE");
		System.out.println(scan("b") ? "OK" : "NOPE");
		System.out.println(scan("abb") ? "OK" : "NOPE");
        System.out.println(scan("bbaba") ? "OK" : "NOPE");
        System.out.println(scan("baaaaaaa”") ? "OK" : "NOPE");
        System.out.println(scan("aaaa”") ? "OK" : "NOPE");
    }
    
}