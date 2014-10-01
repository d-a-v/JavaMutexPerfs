import java.util.concurrent.locks.ReentrantLock;

public class CounterLock extends ReentrantLock implements Counter
{
	private static final long serialVersionUID = 1L;
	private long counter;

	CounterLock () { counter = 0; }
	CounterLock (long init) { counter = init; }
	
	public final String type () { return "ReentrantLock"; }

	public long get () { return counter; }
	public long getAndIncrement () { long ret; lock(); ret = counter++; unlock(); return ret; }
	public long incrementAndGet () { long ret; lock(); ret = ++counter; unlock(); return ret; }
	public long getAndDecrement () { long ret; lock(); ret = counter--; unlock(); return ret; }
	public long decrementAndGet () { long ret; lock(); ret = --counter; unlock(); return ret; }
	public void set (long newVal) { counter = newVal; }
}
