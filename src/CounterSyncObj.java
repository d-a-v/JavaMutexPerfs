public class CounterSyncObj implements Counter
{
	private long counter;

	CounterSyncObj () { counter = 0; }
	CounterSyncObj (long init) { counter = init; }
	
	public final String type () { return "Synchronized(this)"; }

	public long get () { return counter; }
	public long getIncr () { long ret; synchronized(this) { ret = counter++; } return ret; }
	public long incrGet () { long ret; synchronized(this) { ret = ++counter; } return ret; }
	public long getDecr () { long ret; synchronized(this) { ret = counter--; } return ret; }
	public long decrGet () { long ret; synchronized(this) { ret = --counter; } return ret; }
	public void set (long newVal) { counter = newVal; }
}
