
public interface Counter
{
	public String type ();
	public long get ();
	public long getAndIncrement () throws InterruptedException;
	public long incrementAndGet () throws InterruptedException;
	public long getAndDecrement () throws InterruptedException;
	public long decrementAndGet () throws InterruptedException;
	public void set (long newVal);
}
