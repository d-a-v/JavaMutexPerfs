public class CounterSyncThis implements Counter
{
	private long counter;

	CounterSyncThis () { counter = 0; }
	CounterSyncThis (long init) { counter = init; }
	
	public final String type () { return "Synchronized(this)"; }

	public long get () { return counter; }
	public long getAndIncrement () { synchronized(this) { return counter++; } }
	public long incrementAndGet () { synchronized(this) { return ++counter; } }
	public long getAndDecrement () { synchronized(this) { return counter--; } }
	public long decrementAndGet () { synchronized(this) { return --counter; } }
	public void set (long newVal) { counter = newVal; }
}
