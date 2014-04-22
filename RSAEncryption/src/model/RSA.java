package model;
import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;

public class RSA 
{
    private int BLOCK_SIZE, numberOfBlocks, remainder, zerosToAdd = 0, SIZE = 2500;
    private BigInteger p,q,n,phi,d,e;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String originalMessage;
    
    public RSA()
    {
    	p = new BigInteger(SIZE, 15, new Random());
		q = new BigInteger(SIZE, 15, new Random());
		n = p.multiply(q);
		BLOCK_SIZE = (n.bitLength()/8)-11;
		
		phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
		e = BigInteger.probablePrime(SIZE/2, new Random());
		
		while( !(phi.gcd(e).equals(BigInteger.ONE) && 
			   e.compareTo(BigInteger.ONE) > 0 && 
			   e.compareTo(phi) < 0) )
		{
			e.add(BigInteger.ONE);
		}
		
		d = e.modInverse(phi); 
		privateKey = new PrivateKey(n,d);
		publicKey = new PublicKey(n,e);
    }
   
	public Vector<byte[]> encrypt(String message)
	{
		originalMessage = message;
		byte [] byteMessage;
		Vector <byte[]> encryption = new Vector<byte[]>();
		if( originalMessage.length() > BLOCK_SIZE)
		{
			remainder = (originalMessage.length() % BLOCK_SIZE);
			if( remainder != 0 )
			{
				zerosToAdd = (BLOCK_SIZE - remainder);
				for( int i = 0; i < zerosToAdd ; i ++)
				{
					originalMessage += "0";
				}
			}
			
			numberOfBlocks = (originalMessage.length() / BLOCK_SIZE);
			for( int i = 0 ; i < numberOfBlocks; i++ )
			{
				String toEncrypt = originalMessage.substring(0, BLOCK_SIZE);
				byteMessage = toEncrypt.getBytes();
				originalMessage = originalMessage.substring(BLOCK_SIZE, originalMessage.length());
				encryption.add(((new BigInteger(byteMessage)).modPow(publicKey.getE(), publicKey.getN()).toByteArray()));			
			}
			
			return encryption;
		}
		else
		{
		    message = addMessagePadding(message);
		    byteMessage = message.getBytes();
		    encryption.add((new BigInteger(byteMessage)).modPow(publicKey.getE(), publicKey.getN()).toByteArray());
		    return encryption;
		}
	}
	
	public byte[] decrypt(Vector<byte[]> message)
	{
		String decryption = "";
		for ( int i = 0 ; i < message.size(); i ++ )
		{
			decryption += new String((new BigInteger(message.elementAt(i))).modPow(privateKey.getD(), privateKey.getN()).toByteArray());
		}

		return decryption.getBytes();
	}
	
	public String removeMessagePadding(byte[] message)
	{
		String decryption = new String(message);
		decryption = decryption.substring(0, (decryption.length() - zerosToAdd));
		return decryption;
	}
	
	public String addMessagePadding(String input)
	{
		zerosToAdd = BLOCK_SIZE - input.length();
		
		for(int i = 0; i < zerosToAdd; i++)
		{
			input+="0";
		}
		return input;
	}
	
	public String getCurrentPublicKey()
	{
		String output = "";
		output = "\n" + "E is: " + publicKey.getE() + "\n\n" + "N is: " + publicKey.getN();
		return output;
	}
	
	public String getCurrentPrivateKey()
	{
		String output = "";
		output = "N is: " + privateKey.getN();
		return output;
	}
}