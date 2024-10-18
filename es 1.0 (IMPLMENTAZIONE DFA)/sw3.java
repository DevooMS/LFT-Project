/*
Progettare e implementare un DFA che riconosca il linguaggio di stringhe che
contengono un numero di matricola seguito (subito) da un cognome, dove la combinazione di
matricola e cognome corrisponde a studenti del turno 2 o del turno 3 del laboratorio di Linguaggi
Formali e Traduttori. Si ricorda le regole per suddivisione di studenti in turni:
• Turno T1: cognomi la cui iniziale e compresa tra A e K, e il numero di matricola ` e dispari; `
• Turno T2: cognomi la cui iniziale e compresa tra A e K, e il numero di matricola ` e pari; `
• Turno T3: cognomi la cui iniziale e compresa tra L e Z, e il numero di matricola ` e dispari; `
• Turno T4: cognomi la cui iniziale e compresa tra L e Z, e il numero di matricola ` e pari. `
Per esempio, “123456Bianchi” e “654321Rossi” sono stringhe del linguaggio, mentre
“654321Bianchi” e “123456Rossi” no. Nel contesto di questo esercizio, un numero di matricola non ha un numero prestabilito di cifre (ma deve essere composto di almeno una cifra). Un
cognome corrisponde a una sequenza di lettere, e deve essere composto di almeno una lettera.
Quindi l’automa deve accettare le stringhe “2Bianchi” e “122B” ma non “654322” e “Rossi”.
Assicurarsi che il DFA sia minimo.
*/
/*
public class sw3 {
    public static void main(String[] args)
    {
    String text="122Ak";
	System.out.println(text+":"+(scan(text) ? "OK" : "NIET"));
	                                              
    
    }
    
    public static boolean scan (String s){
       int i = 0;
       int n = 0;
       int lenght = s.length();
       int state = 0;

       while(state >=0 && s.length()>i){
        
            final char c = s.charAt(i);
            
            switch(state){
                case 0:
                    if(c >= '0' && c <= '9' && i!=s.length()-1){
                        if(s.charAt(i)<=s.charAt(i+1)){
                            state = 1;
                            n=Character.getNumericValue(c);
                        }else{
                            state =-1;
                        }
                       
                    }else if(c=='_'){
                        state = 0;
                    }
                    break;
                case 1:
                    if((c>= 'a' && c<= 'l') || (c>= 'A' && c<= 'L')){
                        if(n%2==0){
                        System.out.println("T2");  
                        }
                        if(n%2==1){
                        System.out.println("T1");
                        }
                        state =2;
                    }else if ((c>= 'l' && c<= 'z') || (c>= 'L' && c<= 'Z')){
                        if(n%2==1){
                        System.out.println("T3");  
                        }
                        if(n%2==0){
                        System.out.println("T4");
                        }
                        state =2;

                    }else if ((c >= '0' && c <= '9')){
                        
                        state = 0;
                    }
                    break;
            

                }   
   
            i++;
       }
       
       return state == 2 ;
    }
}
*/
public class sw3
{
	public static boolean scan(String s)
	{
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char ch = s.charAt(i++);
				
				switch (state) {
					case 0:
						if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
							state = 1;
						else if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
							state = 2;
						else if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
							state = 4;
						else
							state = -1;
						break;
						
					case 1:
						if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
							state = 1;
						else if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
							state = 2;
						else if((ch >= 'l' && ch <= 'z') || (ch >= 'L' && ch <= 'Z'))
							state = 3;
						else if((ch >= 'a' && ch <= 'k') || (ch >= 'A' && ch <= 'K'))
							state = 4;
						else
							state = -1;
						break;
					case 2:
						if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
							state = 2;
						else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
							state = 1;
						else if ((ch >= 'a' && ch <= 'k')||(ch >= 'A' && ch <= 'K'))
							state = 3;
						else if ((ch >= 'l' && ch <= 'z')||(ch >= 'L' && ch <= 'Z'))
							state = 4;
						else
							state = -1;
						break;
					case 3:
						if ((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z'))
							state = 3;
						else if(ch >= '0' && ch <= '9')
							state = 4;
						else
							state = -1;
						break;
					case 4:
						if ((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')||(ch >= '0' && ch <= '9'))
							state = 4;
						else
							state = -1;
						break;
				}
			}	
			return state == 3;
	}
	
	public static void main(String[] args)
	{
		System.out.println(scan("123456Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("654321Rossi") ? "OK" : "NOPE");
		System.out.println(scan("654321Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("123456Rossi") ? "OK" : "NOPE");
		System.out.println(scan("2Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("122B") ? "OK" : "NOPE");
		System.out.println(scan("654322") ? "OK" : "NOPE");
		System.out.println(scan("Rossi") ? "OK" : "NOPE");
	}
}