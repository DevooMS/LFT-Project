public class sw4
{
	public static boolean scan(String s)
	{
			int state = 0;
			int i = 0;
			while (state >= 0 && i < s.length()) {
				final char ch = s.charAt(i++);
				
				switch (state) {
					case 0:
						if(ch == ' ')
							state = 0;
						else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
							state = 1;
						else if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
							state = 2;
						else
							state = -1;
						break;
						
					case 1:
						if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
							state = 1;
						else if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
							state = 2;
						else if (ch == ' ')
							state = 3;
						else if ( ch >= 'L' && ch <= 'Z')
							state = 5;
						else
							state = -1;
						break;
					case 2:
						if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
							state = 2;
						else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
							state = 1;
						else if (ch == ' ')
							state = 4;
						else if ( ch >= 'A' && ch <= 'K')
							state = 5;
						else
							state = -1;
						break;
					case 3:
						if (ch == ' ')
							state = 3;
						else if(ch >= 'L' && ch <= 'Z')
							state = 5;
						else
							state = -1;
						break;
					case 4:
						if (ch == ' ')
							state = 4;
						else if(ch >= 'A' && ch <= 'K')
							state = 5;
						else
							state = -1;
						break;
					case 5:
						if (ch >= 'a' && ch <= 'z')
							state = 5;
						else if(ch == ' ')
							state = 6;
						else
							state = -1;
						break;
					case 6:
						if ( ch ==' ')
							state = 6;
						else if (ch >= 'A' && ch <= 'Z')
							state =5;
						else state = -1;
				}
			}	
			return state == 5 || state == 6;
	}
	
	public static void main(String[] args)
	{
		System.out.println(scan("654321 Rossi") ? "OK" : "NOPE");
		System.out.println(scan(" 123456 Bianchi ") ? "OK" : "NOPE");
		System.out.println(scan("1234 56Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("123456Bia nchi") ? "OK" : "NOPE");
		System.out.println(scan("123456De Gasperi") ? "OK" : "NOPE");
	}
}