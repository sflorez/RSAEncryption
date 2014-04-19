import java.math.BigInteger;


public class RSA 
{
    public RSA()
    {
	   
    }
   
	public static byte[] encrypt(byte [] message , PublicKey key)
	{
		return (new BigInteger(message)).modPow(key.getE(), key.getN()).toByteArray();
	}
	
	public static byte[] decrypt(byte [] message , PrivateKey key)
	{
		return (new BigInteger(message)).modPow(key.getD(), key.getN()).toByteArray();
	}
}
