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
  
  BigInteger getD()
  {
	 return d; 
  }
  
  BigInteger getN()
  {
	  return n;
  }
}