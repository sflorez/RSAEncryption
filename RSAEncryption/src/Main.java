import java.math.BigInteger;
import java.util.Random;

public class Main 
{
	public static void main(String[] args) 
	{
		int SIZE = 512;
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
		
		if((e.multiply(d).subtract(BigInteger.ONE)).compareTo(phi) < 0)
		{
			System.out.println("I hope i never see this...");
		}
		
		BigInteger m = new BigInteger("100");
		System.out.println("m: " + m);
		BigInteger cipher;
		
		cipher = m.modPow(publicKey.getE(), publicKey.getN());
		System.out.println("cipher: " + cipher);
		
		BigInteger decrypt;
		decrypt = m.modPow(privateKey.getD(), privateKey.getN());
		System.out.println("decrypt: " + decrypt);
	}
}