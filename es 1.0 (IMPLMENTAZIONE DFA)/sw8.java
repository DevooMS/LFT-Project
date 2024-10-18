
/*Esercizio 1.8. Progettare e implementare un DFA che riconosca il linguaggio di stringhe che
contengono il tuo nome e tutte le stringhe ottenute dopo la sostituzione di un carattere del nome
con un altro qualsiasi. Ad esempio, nel caso di uno studente che si chiama Paolo, il DFA deve
accettare la stringa “Paolo” (cioe il nome scritto correttamente), ma anche le stringhe “ ` Pjolo”,
“caolo”, “Pa%lo”, “Paola” e “Parlo” (il nome dopo la sostituzione di un carattere), ma non
“Eva”, “Perro”, “Pietro” oppure “P*o*o”.
*/

public class sw8
{
	public static boolean scan(String s){
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char c = s.charAt(i++);
				
				switch (state) {
                    case 0:
                        if(c=='M'){
                            state = 1;
                        }else if((c>= 'a' && c<= 'l') || (c>= 'n' && c<= 'z')||(c>= 'A' && c<= 'L')||(c>= 'N' && c<= 'Z')||(c== '%')){
                            state = 5;
                        }
                        break;
                    case 1:
                        if(c== 'i'){
                            state = 2;
                        }
                        else if((c>= 'a' && c<= 'h') || (c>= 'j' && c<= 'z')||(c>= 'A' && c<= 'H')||(c>= 'J' && c<= 'Z')||(c== '%')){
                            state = 6;
                        }
                        break;
                    case 2:
                        if(c=='n'){
                            state = 3;
                         }
                        else if((c>= 'a' && c<= 'm') || (c>= 'o' && c<= 'z')||(c>= 'A' && c<= 'M')||(c>= 'O' && c<= 'Z')||(c== '%')){
                            state = 7;
                        }
                        break;
                    case 3:
                        if((c>= 'a' && c<= 'z') || (c>= 'A' && c<= 'Z') || (c== '%')){
                            state = 4;
                        }
                        break;
                    case 4:
                        break;
                    case 5:  
                        if(c=='i'){
                            state = 6;
                        }
                        break; 
                    case 6:  
                        if(c=='n'){
                            state = 7;
                        }
                        break; 
                    case 7:  
                        if(c=='g'){
                            state = 4;
                        }
                        break; 
                }
            
            }  
        return state ==4;
    }	
	public static void main(String[] args)
	{
		System.out.println(scan("Ming") ? "OK" : "NOPE");
        System.out.println(scan("Mina") ? "OK" : "NOPE");
        System.out.println(scan("Mi%g") ? "OK" : "NOPE");
        System.out.println(scan("Eva") ? "OK" : "NOPE");
        System.out.println(scan("M*i*n") ? "OK" : "NOPE");
		
	}
}