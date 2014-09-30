
public interface Counter
{
	public String type ();
	public long get ();
	public long getIncr ();
	public long incrGet ();
	public long getDecr ();
	public long decrGet ();
	public void set (long newVal);
}
