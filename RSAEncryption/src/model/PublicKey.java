/*
 * Class designed to store the information associated with the user's public key
 */
package model;
import java.math.BigInteger;

public class PublicKey
{
	 private BigInteger n, e;
	 
	 public PublicKey(BigInteger n, BigInteger e)
	 {
	     this.e = e;
	     this.n = n;
	 }
	 
	 public void setN(BigInteger myN)
	 {
       	      n = myN;
	 }
	  
	 public void setE(BigInteger myE)
	 {
	      e = myE;
	 }
	  
	 public BigInteger getE()
	 {
	      return e; 
	 }
	  
	 public BigInteger getN()
	 {
	      return n;
	 }
}
