
public interface Counter
{
	public String type ();
	public long get ();
	public long getAndIncrement ();
	public long incrementAndGet ();
	public long getAndDecrement ();
	public long decrementAndGet ();
	public void set (long newVal);
}
