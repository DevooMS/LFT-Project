
public class es2
{
    public static void main(String[] args)
    {
	System.out.println(scan("_") ? "OK" : "NOPE"); // se fossi scan (args) prende il valore in questo modo
	                                                // java nomefile "valore" sul terminale
    }
    
    public static boolean scan (String s){
       int i = 0;
       int state = 0;
       

       for(;state >=0 && s.length()>i;i++ ){

            final char c = s.charAt(i);

            if((c>= 'a' && c<= 'z') || (c>= 'A' && c<= 'Z')){
                    state = 1;

            }else if((c >= '0' && c <= '9') && i==0){
                
                    state = -1;

            }else if (c=='_'){
                    state = 0;
            }
         }
       return state==1;
      
    }

}

   