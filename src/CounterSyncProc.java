public class CounterSyncProc implements Counter
{
	private long counter;

	CounterSyncProc () { counter = 0; }
	CounterSyncProc (long init) { counter = init; }
	
	public String type () { return "Synchronized Function"; }

	               public long get () { return counter; }
	synchronized public long getIncr () { return counter++; }
	synchronized public long incrGet () { return ++counter; }
	synchronized public long getDecr () { return counter--; }
	synchronized public long decrGet () { return --counter; }
	synchronized public void set (long newVal) { counter = newVal; }
}
