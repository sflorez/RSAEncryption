import java.math.BigInteger;
import java.util.Random;

public class RSA 
{
    private int BLOCK_SIZE, SIZE = 512;
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
   
	public byte[] encrypt(String message)
	{
		originalMessage = message;
		message = addMessagePadding(message);
	    byte[] byteMessage = message.getBytes();
		return (new BigInteger(byteMessage)).modPow(publicKey.getE(), publicKey.getN()).toByteArray();
	}
	
	public byte[] decrypt(byte [] message)
	{
		return (new BigInteger(message)).modPow(privateKey.getD(), privateKey.getN()).toByteArray();
	}
	
	public String removeMessagePadding(byte[] message)
	{
		String decryption = new String(message);
		int loopUntil = BLOCK_SIZE - originalMessage.length();
		decryption = decryption.substring(0, (decryption.length() - loopUntil));
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