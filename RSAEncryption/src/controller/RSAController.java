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
		myModel = new RSA();
		
		String userPublicDisplayKey = myModel.getCurrentPublicKey();
		String userPrivateDisplayKey = myModel.getCurrentPrivateKey();
		
		myView = new RSAUserInterface(this);
		myView.setPublicKeyDisplay(userPublicDisplayKey);
		myView.setPrivateKeyDisplay(userPrivateDisplayKey);
	}
	
	public Vector<byte[]> encryptMessage(String input)
	{
		Vector<byte[]> encryption = myModel.encrypt(input);
		return encryption;
	}
	
	public String decryptMessage(Vector<byte[]> encryption)
	{
		byte[] decrypt = myModel.decrypt(encryption);
        String message = myModel.removeMessagePadding(decrypt);
        return message;
	}
}