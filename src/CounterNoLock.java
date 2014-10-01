
public class CounterNoLock implements Counter
{
	private long counter;

	CounterNoLock () { counter = 0; }
	CounterNoLock (long init) { counter = init; }

	public final String type () { return "No-Locking"; }
	
	public long get () { return counter; }
	public long getAndIncrement () { return counter++; }
	public long incrementAndGet () { return ++counter; }
	public long getAndDecrement () { return counter--; }
	public long decrementAndGet () { return --counter; }
	public void set (long newVal) { counter = newVal; }
}
