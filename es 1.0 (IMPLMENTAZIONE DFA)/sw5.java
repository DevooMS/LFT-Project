/*Progettare e implementare un DFA che, come in Esercizio 1.3, riconosca il linguaggio di stringhe che contengono matricola e cognome di studenti del turno 2 o del turno 3 del
laboratorio, ma in cui il cognome precede il numero di matricola (in altre parole, le posizioni del
cognome e matricola sono scambiate rispetto all’Esercizio 1.3). Assicurarsi che il DFA sia minimo*/ 
public class sw5 {
    public static void main(String[] args)
    {
    String text="al123";
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
                case 1:
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
                case 0:
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
                        
                        state = 1;
                    }
                    break;
            

                }   
   
            i++;
       }
       
       return state == 2 ;
    }
}
