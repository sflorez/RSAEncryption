import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;

public class RSA 
{
    private int BLOCK_SIZE, numberOfBlocks, remainder, zerosToAdd, SIZE = 64;
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
			System.out.println("Block size: " + BLOCK_SIZE);
			remainder = (originalMessage.length() % BLOCK_SIZE);
			zerosToAdd = (BLOCK_SIZE - remainder);
			System.out.println("Remainder: " + remainder);
			if( remainder != 0 )
			{
				for( int i = 0; i < zerosToAdd ; i ++)
				{
					originalMessage += "0";
				}
			}
			
			System.out.println("the message after padding: " + originalMessage);
			numberOfBlocks = (originalMessage.length() / BLOCK_SIZE);
			System.out.println(numberOfBlocks);
			
			for( int i = 0 ; i < numberOfBlocks; i++ )
			{
				String toEncrypt = originalMessage.substring(0, BLOCK_SIZE);
				System.out.println( " to Encrypt:" + toEncrypt);
				byteMessage = toEncrypt.getBytes();
				originalMessage = originalMessage.substring(BLOCK_SIZE, originalMessage.length());
				System.out.println("Message after removal: " + originalMessage);
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
//		return (new BigInteger(message)).modPow(privateKey.getD(), privateKey.getN()).toByteArray();
		return decryption.getBytes();
	}
	
	public String removeMessagePadding(byte[] message)
	{
		String decryption = new String(message);
//		int loopUntil = BLOCK_SIZE - originalMessage.length();
		decryption = decryption.substring(0, (decryption.length() - zerosToAdd));
		return decryption;
	}
	
	public String addMessagePadding(String input)
	{
		int loopUntil = BLOCK_SIZE - input.length();
		
		for(int i = 0; i < loopUntil; i++)
		{
			input+="0";
		}
		return input;
	}
}