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
	  
	  BigInteger getE()
	  {
		 return e; 
	  }
	  
	  BigInteger getN()
	  {
		  return n;
	  }
}