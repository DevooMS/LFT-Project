public class es1
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
		if (ch == '1')
		    state = 1;
		else if (ch == '0')
		    state = 0;
		else
		    state = -1;
		break;

	    case 1:
		if (ch == '1')
		    state = 2;
		else if (ch == '0')
		    state = 0;
		else
		    state = -1;
		break;

	    case 2:
		if (ch == '1')
		    state = 3;
		else if (ch == '0')
		    state = 0;
		else
		    state = -1;
		break;

	    case 3:
		if (ch == '0' || ch == '1')
		    state = 3;
		else
		    state = -1;
		break;
	    }
	}
	return state == 3;
    }

    public static void main(String[] args)
    {
	System.out.println(scan("aaa") ? "OK" : "NOPE"); // se fossi scan (args) prende il valore in questo modo
													// java nomefile "valore" sul terminale
    }
}