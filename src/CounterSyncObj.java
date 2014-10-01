public class CounterSyncObj implements Counter
{
	private long counter;

	CounterSyncObj () { counter = 0; }
	CounterSyncObj (long init) { counter = init; }
	
	public final String type () { return "Synchronized(this)"; }

	public long get () { return counter; }
	public long getAndIncrement () { long ret; synchronized(this) { ret = counter++; } return ret; }
	public long incrementAndGet () { long ret; synchronized(this) { ret = ++counter; } return ret; }
	public long getAndDecrement () { long ret; synchronized(this) { ret = counter--; } return ret; }
	public long decrementAndGet () { long ret; synchronized(this) { ret = --counter; } return ret; }
	public void set (long newVal) { counter = newVal; }
}
