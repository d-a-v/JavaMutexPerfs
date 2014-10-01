import java.util.concurrent.atomic.AtomicLong;

public class CounterAtomic extends AtomicLong implements Counter
{
	static final long serialVersionUID = 1l;

	CounterAtomic () { super(0); }
	CounterAtomic (long init) { super(init); }

	public final String type () { return "AtomicLong"; }
}
