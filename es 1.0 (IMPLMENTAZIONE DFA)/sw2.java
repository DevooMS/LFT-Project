public class sw2 {
    public static void main(String[] args)
    {
	System.out.println(scan(" ") ? "OK" : "NOPE"); // se fossi scan (args) prende il valore in questo modo
	                                                // java nomefile "valore" sul terminale
    }
    
    public static boolean scan (String s){
       int i = 0;
       int state = 0;
       

       while(state >=0 && s.length()>i){
            final char c = s.charAt(i);
            
            switch(state){
                case 0:
                    if((c>= 'a' && c<= 'z') || (c>= 'A' && c<= 'Z')){
                        state = 1;
                    }else if(c=='_'){
                        state = 0;
                    }
                    break;
                case 1:
                    if(c >= '0' && c <= '9'){
                        state =2;
                    }
                    break;
            }
   
            i++;
       }
       return state == 2 ;
    }
}