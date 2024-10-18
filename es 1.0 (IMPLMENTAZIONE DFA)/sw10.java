//Modificare l’automa dell’esercizio precedente in modo che riconosca il linguaggio di stringhe (sull’alfabeto {/, *, a}) che contengono “commenti” delimitati da /* e */, ma con
//la possibilita di avere stringhe prima e dopo come specificato qui di seguito. L’idea ` e che sia `
//possibile avere eventualmente commenti (anche multipli) immersi in una sequenza di simboli
//dell’alfabeto. Quindi l’unico vincolo e che l’automa deve accettare le stringhe in cui un’occorren- `
//za della sequenza /* deve essere seguita (anche non immediatamente) da un’occorrenza della
//sequenza */. Le stringhe del linguaggio possono non avere nessuna occorrenza della sequenza
///* (caso della sequenza di simboli senza commenti). Implementare l’automa seguendo la costruzione vista in Listing 1.
//Esempi di stringhe accettate: “aaa/****/aa”, “aa/*a*a*/”, “aaaa”, “/****/”, “/*aa*/”,
//“*/a”, “a/**/***a”, “a/**/***/a”, “a/**/aa/***/a”
//Esempi di stringhe non accettate: “aaa/*/aa”, “a/**//***a”, “aa/*aa”

public class sw10
{
	public static boolean scan(String s){
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char c = s.charAt(i++);
				
				switch (state) {
                    case 0:
                        if((c== 'a')||(c=='*')){
                        state = 0;
                        }else if((c== '/'))
                        state = 1;
                        break;
                    case 1:
                        if(c== '/'){
                        state = 1;
                        }else if((c== '*')){
                        state = 2;
                        }else if(c=='a'){
                        state = 0;
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
                        }else if(c=='a'){
                        state =2;
                        }else if(c=='/'){
                        state =0;
                        }
                        break;
                }  
            }
        return state ==0||state ==1;    
        
    }	
	public static void main(String[] args)
	{
        System.out.println(scan("aaa/****/aa") ? "OK" : "NOPE");
        System.out.println(scan("aaaa") ? "OK" : "NOPE");
        System.out.println(scan("a/**//***a") ? "OK" : "NOPE");
        System.out.println(scan("aa/*aa") ? "OK" : "NOPE");
	
    }
    
}