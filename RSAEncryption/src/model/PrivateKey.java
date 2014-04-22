/*
 * Class designed to store the information associated with the user's private key
 */
package model;
import java.math.BigInteger;

public class PrivateKey 
{
  private BigInteger n, d;
  
  public PrivateKey(BigInteger n, BigInteger d)
  {
	  this.n = n;
	  this.d = d;
  }
  
  public void setN(BigInteger myN)
  {
	  n = myN;
  }
  
  public void setD(BigInteger myD)
  {
	  d = myD;
  }
  
  public BigInteger getD()
  {
	 return d; 
  }
  
  public BigInteger getN()
  {
	  return n;
  }
}
