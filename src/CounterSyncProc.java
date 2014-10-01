public class CounterSyncProc implements Counter
{
	private long counter;

	CounterSyncProc () { counter = 0; }
	CounterSyncProc (long init) { counter = init; }
	
	public final String type () { return "Synchronized Function"; }

	             public long get () { return counter; }
	synchronized public long getAndIncrement () { return counter++; }
	synchronized public long incrementAndGet () { return ++counter; }
	synchronized public long getAndDecrement () { return counter--; }
	synchronized public long decrementAndGet () { return --counter; }
	             public void set (long newVal) { counter = newVal; }
}
