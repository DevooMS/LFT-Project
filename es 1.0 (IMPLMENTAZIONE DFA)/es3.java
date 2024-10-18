public class es3
{
    public static void main(String[] args)
    {
	System.out.println(scan("321") ? "OK" : "NOPE"); // se fossi scan (args) prende il valore in questo modo
	                                                // java nomefile "valore" sul terminale
    }
    
    public static boolean scan (String s){
       int i = 0;
       int state = 0;
       final char c = s.charAt(i);
       int j = 1;

       for(;state >=0 && s.length()>i;i++ ){
            if((c>= 'a' && c<= 'z') || (c>= 'A' && c<= 'Z') ){
               
                return false;

            }else{

                if (c >= '0' && c <= '9'&& i!=s.length()-1) {
                    
                    while(s.length()>i&&j<=s.length()){
                        
                        if(c>s.charAt(j)){
                            System.out.println("While");
                        }
                        i++;
                    }
                }
            }

            
        }
       return false;
      
    }

}
