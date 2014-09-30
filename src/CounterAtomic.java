import java.util.concurrent.atomic.AtomicLong;

public class CounterAtomic extends AtomicLong implements Counter
{
	CounterAtomic () { super(0); }
	CounterAtomic (long init) { super(init); }

	public final String type () { return "AtomicLong"; }

	public long getIncr () { return getAndIncrement(); }
	public long incrGet () { return incrementAndGet(); }
	public long getDecr () { return getAndDecrement(); }
	public long decrGet () { return decrementAndGet(); }
}
