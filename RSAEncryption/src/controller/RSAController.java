package controller;
import model.RSA;
import view.RSAUserInterface;
import java.util.Scanner;
import java.util.Vector;

public class RSAController 
{
	private RSAUserInterface myView;
	private RSA myModel;
	
	public RSAController()
	{
		Scanner in = new Scanner(System.in);
		String input = "";
		System.out.println("Enter a value: ");
		input = in.nextLine();

		myModel = new RSA();

		Vector<byte[]> encryption = myModel.encrypt(input);
		byte[] decrypt = myModel.decrypt(encryption);

        String message = myModel.removeMessagePadding(decrypt);
		System.out.println("Decrypted message: " + message);
		
		String userPublicDisplayKey = myModel.getCurrentPublicKey();
		myView = new RSAUserInterface(this);
		myView.setPublicKeyDisplay(userPublicDisplayKey);
	}
}