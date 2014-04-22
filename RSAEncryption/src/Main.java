import java.util.Scanner;
import java.util.Vector;


public class Main 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		String input = "";
		System.out.println("Enter a value: ");
		input = in.nextLine();
		
		RSA rsa = new RSA();
	    
		Vector<byte[]> encryption = rsa.encrypt(input);
//		System.out.println(encryption);
		byte[] decrypt = rsa.decrypt(encryption);
//		System.out.println(decrypt);
        String message = rsa.removeMessagePadding(decrypt);
		System.out.println("Decrypted message: " + message);
		
		RSAUserInterface ui = new RSAUserInterface();
	}
}