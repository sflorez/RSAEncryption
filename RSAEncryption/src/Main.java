
public class Main 
{
	public static void main(String[] args) 
	{
		String input = "";
		System.out.println("Enter a value: ");
		input = "I am a test value.";
		
		RSA rsa = new RSA();
	    
		byte[] encryption = rsa.encrypt(input);
		byte[] decrypt = rsa.decrypt(encryption);
		String message = rsa.removeMessagePadding(decrypt);
		
		System.out.println(message);
	}
}