
public class CounterNoLock implements Counter
{
	private long counter;

	CounterNoLock () { counter = 0; }
	CounterNoLock (long init) { counter = init; }

	public String type () { return "No-Locking"; }
	
	public long get () { return counter; }
	public long getIncr () { return counter++; }
	public long incrGet () { return ++counter; }
	public long getDecr () { return counter--; }
	public long decrGet () { return --counter; }
	public void set (long newVal) { counter = newVal; }
}
