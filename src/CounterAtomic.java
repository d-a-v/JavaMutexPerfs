import java.util.concurrent.atomic.AtomicLong;


public class CounterAtomic implements Counter
{
	private AtomicLong counter = new AtomicLong(0);
	
	CounterAtomic () { }
	CounterAtomic (long init) { counter.set(init); }

	public String type () { return "AtomicLong"; }

	public long get () { return counter.get(); }
	public long getIncr () { return counter.getAndIncrement(); }
	public long incrGet () { return counter.incrementAndGet(); }
	public long getDecr () { return counter.getAndDecrement(); }
	public long decrGet () { return counter.decrementAndGet(); }
	public void set (long newVal) { counter.set(newVal); }
}
