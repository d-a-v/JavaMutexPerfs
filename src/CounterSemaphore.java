import java.util.concurrent.Semaphore;

public class CounterSemaphore extends Semaphore implements Counter
{
	private static final long serialVersionUID = 1L;
	private long counter;

	CounterSemaphore (boolean fair) { super(1, fair); counter = 0; }
	CounterSemaphore (long init, boolean fair) { super(1, fair); counter = init; }
	
	public final String type () { return "Semaphore" + (isFair()? "Fair": "Unfair"); }

	public long get () { return counter; }
	public long getAndIncrement () throws InterruptedException { long ret; acquire(); ret = counter++; release(); return ret; }
	public long incrementAndGet () throws InterruptedException { long ret; acquire(); ret = ++counter; release(); return ret; }
	public long getAndDecrement () throws InterruptedException { long ret; acquire(); ret = counter--; release(); return ret; }
	public long decrementAndGet () throws InterruptedException { long ret; acquire(); ret = --counter; release(); return ret; }
	public void set (long newVal) { counter = newVal; }
}
