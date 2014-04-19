import java.math.BigInteger;
import java.util.Random;

public class Main 
{
	public static void main(String[] args) 
	{
		int SIZE = 512, BLOCK_SIZE;
		BigInteger p = new BigInteger(SIZE, 15, new Random());
		BigInteger q = new BigInteger(SIZE, 15, new Random());
		BigInteger n = p.multiply(q);
		System.out.println("n: " + n);
		
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
		BigInteger e = BigInteger.probablePrime(SIZE/2, new Random());
		
		while( !(phi.gcd(e).equals(BigInteger.ONE) && 
			   e.compareTo(BigInteger.ONE) > 0 && 
			   e.compareTo(phi) < 0) )
		{
			e.add(BigInteger.ONE);
		}
		System.out.println("gcd: " + phi.gcd(e));
		
		BigInteger d = e.modInverse(phi); //secret key
		PrivateKey privateKey = new PrivateKey(n,d);
		PublicKey publicKey = new PublicKey(n,e);
		
		if(e.multiply(d).mod(phi).compareTo(BigInteger.ONE) == 0)
		{
			System.out.println("I hope i see this...");
		}
	    
		String input = "";
		System.out.println("Enter a value: ");
		input = "I am a test value.";
	    

        BLOCK_SIZE = (n.bitLength()/8)-11;

		int loopUntil = BLOCK_SIZE - input.length();
		for(int i = 0; i < loopUntil; i++)
		{
			input+="O";
		}
		

		System.out.println(input.length());

		byte[] m = input.getBytes();
		System.out.println("m: " + new String(m));
		byte[] encrypt;
		
		encrypt = encrypt( m , publicKey);
		System.out.println("cipher: " + encrypt);
		
		byte[] decrypt;
		decrypt = decrypt(encrypt , privateKey);
		
		String decryption = new String(decrypt);
		decryption = decryption.substring(0, (decryption.length() - loopUntil));
		System.out.println("decrypt: " + decryption);
		
		
		System.out.println( "Number of digits in N: " + BLOCK_SIZE );
	}
	
	public static byte[] encrypt(byte [] messege , PublicKey key)
	{
		return (new BigInteger(messege)).modPow(key.getE(), key.getN()).toByteArray();
	}
	
	public static byte[] decrypt(byte [] messege , PrivateKey key)
	{
		return (new BigInteger(messege)).modPow(key.getD(), key.getN()).toByteArray();
	}
}
