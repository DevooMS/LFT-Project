//Progettare e implementare un DFA con alfabeto {/, *, a} che riconosca il linguaggio di “commenti” delimitati da /* (all’inizio) e */ (alla fine): cioe l’automa deve accettare le `
//stringhe che contengono almeno 4 caratteri che iniziano con /*, che finiscono con */, e che contengono una sola occorrenza della sequenza */, quella finale (dove l’asterisco della sequenza */
//non deve essere in comune con quello della sequenza /* all’inizio).
//Esempi di stringhe accettate: “/****/”, “/*a*a*/”, “/*a/**/”, “/**a///a/a**/”, “/**/”,
//“/*/*/”
//Esempi di stringhe non accettate: “/*/”, “/**/***/”
public class sw9
{
	public static boolean scan(String s){
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char c = s.charAt(i++);
				
				switch (state) {
                    case 0:
                        if(c== '/'){
                            state = 1;
                        }
                        break;
                    case 1:
                        if(c== '*'){
                        state = 2;
                        }
                        break;
                    case 2:
                        if(c=='a'||c=='/'){
                        state = 2;
                        }else if(c=='*'){
                        state =3;
                        }
                        break;
                    case 3:
                        if(c== '*'){
                        state =3;
                        }else if(c=='/'){
                        state =4;
                        }
                        break;
                    case 4:
                        if(c== '/'){
                            state =0;
                        }
                        break;
                }  
            }
        return state ==4;    
        
    }	
	public static void main(String[] args)
	{
        System.out.println(scan("/*a/**/") ? "OK" : "NOPE");
        System.out.println(scan("/*/") ? "OK" : "NOPE");
        System.out.println(scan("/**/***/") ? "OK" : "NOPE");
        System.out.println(scan("/**a///a/a**/") ? "OK" : "NOPE");
	
    }
    
}