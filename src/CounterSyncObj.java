public class CounterSyncObj implements Counter
{
	private long counter;
	private Object o = new Object();

	CounterSyncObj () { counter = 0; }
	CounterSyncObj (long init) { counter = init; }
	
	public final String type () { return "Synchronized(Object)"; }

	public long get () { return counter; }
	public long getAndIncrement () { synchronized(o) { return counter++; } }
	public long incrementAndGet () { synchronized(o) { return ++counter; } }
	public long getAndDecrement () { synchronized(o) { return counter--; } }
	public long decrementAndGet () { synchronized(o) { return --counter; } }
	public void set (long newVal) { counter = newVal; }
}
