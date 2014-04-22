/*
 * Our project encrypts data using blocks of bytes. The size of our block is (n.bitLength()/8)-11, n being the
 * bit length of the key. Our public key is randomly generated up to the bit length, which is currently set to
 * 1500 to improve user experience, but it can be much larger for security purposes.
 * 
 */
package model;
import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;

public class RSA 
{
    private int BLOCK_SIZE, numberOfBlocks, remainder, zerosToAdd = 0, SIZE = 1500;
    private BigInteger p,q,n,phi,d,e;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String originalMessage;
    
    public RSA()
    {
    	//generating p and q to be large primes 
    	p = new BigInteger(SIZE, 15, new Random());
		q = new BigInteger(SIZE, 15, new Random());
		n = p.multiply(q);
		BLOCK_SIZE = (n.bitLength()/8)-11;
		
		//generating phi and e to be used in calculations
		phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
		e = BigInteger.probablePrime(SIZE/2, new Random());
		
		//while e is not within the necessary bounds keep generating one.
		while( !(phi.gcd(e).equals(BigInteger.ONE) && 
			   e.compareTo(BigInteger.ONE) > 0 && 
			   e.compareTo(phi) < 0) )
		{
			e.add(BigInteger.ONE);
		}
		
		//generating the secret key for decryption
		d = e.modInverse(phi); 
		
		//setting them to their objects to be used throughout program
		privateKey = new PrivateKey(n,d);
		publicKey = new PublicKey(n,e);
    }
   
    /*
     * Encrypt method that takes in a string message and encrypts it in blocks. It adds those blocks to
     * a vector of byte arrays to be used for decryption.
     * 
     * @params the message that is to be encrypted
     * 
     * @returns the vector of byte arrays containing the encrypted message
     */
	public Vector<byte[]> encrypt(String message)
	{
		originalMessage = message;
		byte [] byteMessage;
		Vector <byte[]> encryption = new Vector<byte[]>();
		
		//if the message needs to be divided because it is bigger than the blocksize
		if( originalMessage.length() > BLOCK_SIZE)
		{
			//calculates if the message can be perfectly divided by the blocksize otherwise it pads it with zeros
			remainder = (originalMessage.length() % BLOCK_SIZE);
			if( remainder != 0 )
			{
				zerosToAdd = (BLOCK_SIZE - remainder);
				for( int i = 0; i < zerosToAdd ; i ++)
				{
					originalMessage += "0";
				}
			}
			
			//calculates the number of blocks that the message needs to be divided into
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
	
	/*
	 * The decrypt method that takes in the vector of byte arrays that needs to be decrypted
	 * 
	 * @params the message to be decrypted
	 * 
	 * @returns the string message that has been decrypted
	 */
	public byte[] decrypt(Vector<byte[]> message)
	{
		// loops through the vector of byte arrays that contains the encrypted message and concatenate each
		// block once decrypted to the end of the decryption string 
		String decryption = "";
		for ( int i = 0 ; i < message.size(); i ++ )
		{
			decryption += new String((new BigInteger(message.elementAt(i))).modPow(privateKey.getD(), privateKey.getN()).toByteArray());
		}

		return decryption.getBytes();
	}
	
	/*
	 * Method that removes the padding from the end of a decrypted message
	 * 
	 * @params the byte array of the message to be altered
	 * 
	 * @return the decrpyted message without padding
	 */
	public String removeMessagePadding(byte[] message)
	{
		String decryption = new String(message);
		decryption = decryption.substring(0, (decryption.length() - zerosToAdd));
		return decryption;
	}
	
	
	/*
	 * Method that adds padding at the end of a message as needed by the encryption
	 * 
	 * @params the string to be padded
	 * 
	 * @return the padded message
	 */
	public String addMessagePadding(String input)
	{
		zerosToAdd = BLOCK_SIZE - input.length();
		
		for(int i = 0; i < zerosToAdd; i++)
		{
			input+="0";
		}
		return input;
	}
	
	/*
	 * Method that returns the values of the public key to be displayed by the gui
	 * 
	 * @return the public key information
	 */
	public String getCurrentPublicKey()
	{
		String output = "";
		output = "\n" + "E is: " + publicKey.getE() + "\n\n" + "N is: " + publicKey.getN();
		return output;
	}
	
	/*
	 * Method that return the private key information to be displayed by the gui
	 * 
	 * @return the private key info
	 */
	public String getCurrentPrivateKey()
	{
		String output = "";
		output = "N is: " + privateKey.getN();
		return output;
	}
}
