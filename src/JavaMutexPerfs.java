
import java.util.concurrent.atomic.AtomicLong;

public class JavaMutexPerfs implements Runnable
{
	private long iterationsAllTogether;
	private long iterationsSelf;
	private long iterationsNotAllTogether;
	private int id;
	
	private static Counter sharedCounter_s;
	private static AtomicLong aliveCounter_s;
	private static int threadNumber_s;

	private static volatile Boolean stop_s; // volatile is MANDATORY here
	private static volatile long timeStartMS_s;
	private static volatile long timeStopMS_s;
	
	public JavaMutexPerfs (int id)
	{
		this.id = id;
		iterationsSelf = 0;
		iterationsAllTogether = 0;
		iterationsNotAllTogether = 0;
	}
	
	public void run ()
	{
		// me, thread, has just come to life
		aliveCounter_s.incrementAndGet();

		// any newcoming thread reinitialize start time
		timeStartMS_s = System.currentTimeMillis();
		
		while (!stop_s)
		{
			// increment shared counter
			sharedCounter_s.incrementAndGet();	
			
			// increment my number of iteration
			++iterationsSelf;
			
			// count number of iteration only
			// when all threads are running concurrently
			if (aliveCounter_s.get() == threadNumber_s)
				++iterationsAllTogether;
			else
				++iterationsNotAllTogether;
		}

		if (timeStopMS_s == 0)
			// only the first finishing thread initialize stop time
			// race possible but not hurting here
			timeStopMS_s = System.currentTimeMillis();
		
		// me, thread, is not alive anymore
		aliveCounter_s.decrementAndGet();
	}
	
	public int getId ()
	{
		return id;
	}

	public long getIterationsSelf ()
	{
		return iterationsSelf;
	}
	
	public long getIterationsAllTogether ()
	{
		return iterationsAllTogether;
	}
	
	public long getIterationsNotAllTogether ()
	{
		return iterationsNotAllTogether;
	}

	public static void test (Counter counter, int threadNumber, int timeMS, boolean prio) throws InterruptedException
	{
		stop_s = false;
		timeStartMS_s = timeStopMS_s = 0;
		aliveCounter_s = new AtomicLong(0);
		sharedCounter_s = counter;
		threadNumber_s = threadNumber;
				
		System.out.println("------------ using " + sharedCounter_s.type() + " ----------------------");
		if (prio)
			System.out.println("-- priority is set to MAX for last the thread only (among 0.." + (threadNumber - 1) + ") and MIN for the others");
		System.out.println("Starting " + threadNumber + " threads for " + ((timeMS + 999) / 1000) + " seconds");
		System.out.println("...");
		
		JavaMutexPerfs counters[] = new JavaMutexPerfs [threadNumber];
		Thread threads[] = new Thread [threadNumber];
		for (int i = 0; i < threadNumber; i++)
			threads[i] = new Thread(counters[i] = new JavaMutexPerfs(i));
	
		if (prio) 
			for (int i = 0; i < threadNumber; i++)
			{
				//threads[i].getThreadGroup().setMaxPriority(Thread.MAX_PRIORITY);
				threads[i].setPriority((i == (threadNumber - 1))? Thread.MAX_PRIORITY: Thread.MIN_PRIORITY);
			}
		
                // start them all
		for (int i = 0; i < threadNumber; i++)
			threads[i].start();

		// wait required time
		Thread.sleep(timeMS);
		
		// stop all threads
		stop_s = true;
		// wait for them to really stop
		for (int i = 0; i < threadNumber; i++)
			threads[i].join();
			
		long total = 0;
		long totalAllTogether = 0;
		long minOOB = -1;
		long maxOOB = -1;
		float maxRatio = 0;
		for (int i = 0; i < threadNumber; i++)
		{
			total += counters[i].getIterationsSelf();
			totalAllTogether += counters[i].getIterationsAllTogether();			
			if (minOOB == -1 || counters[i].getIterationsNotAllTogether() < minOOB)
				minOOB = counters[i].getIterationsNotAllTogether();
			if (maxOOB == -1 || counters[i].getIterationsNotAllTogether() > maxOOB)
			{
				maxOOB = counters[i].getIterationsNotAllTogether();
				maxRatio = 1.0f * maxOOB / counters[i].getIterationsSelf();
			}
		}
		System.out.println("total in-threads iterations = " + total);
		System.out.println("total iterations while running all threads: " + totalAllTogether + " (~" + (100 - (100 * (total - totalAllTogether) / total)) + "% of total)");
		System.out.println("out-of-band iterations per thread: min = " + minOOB + " - max = " + maxOOB + " (~" + maxRatio + "%)");
		System.out.println("shared counter result = " + sharedCounter_s.get() + " (should be " + total + ")");
		if (total != sharedCounter_s.get())
			System.out.println("THIS IS BAD (local <> shared)");
		else
			System.out.println("Seems to be OK (local = shared)");			
		
		System.out.println("all together duration: " + (timeStopMS_s - timeStartMS_s) / 1000.0 + " seconds");
		float perSec = totalAllTogether * 1000.0f / (timeStopMS_s - timeStartMS_s);
		System.out.println("Iterations per seconds: " + (perSec / 1000000.0f) + " M/s");
		
		String ratios = new String();
		float ratioTotal = 0;
		for (int i = 0; i < threadNumber; i++)
		{
			float ratio = ((1f * threadNumber * counters[i].getIterationsAllTogether()) / totalAllTogether);
			ratioTotal += ratio;
			ratios += (int)((100f * ratio) + 0.5f) + " ";
		}
		System.out.println("execution time ratios: " + ratios);
		System.out.println("ratio average = " + (100f * ratioTotal / threadNumber));
	}

	public static void help ()
	{
	        System.out.println("usage:");
	        System.out.println("	-h|--help");
	        System.out.println("	-n <#>		- thread number");
	        System.out.println("	-p <0|1>	- whether to use prio (1) or not (0)");
	        System.out.println("	-t <#>		- running duration per test in ms");
	}

	public static void main (String args[]) throws InterruptedException
	{
		int n = 50;
		int timeMS = 10000;
		boolean prio = true;
		
		for (int i = 0; i < args.length; i++)
		{
		        if (args[i].equals("-n"))
		                n = new Integer(args[++i]).intValue();
                        else if (args[i].equals("-p"))
                                prio = args[++i].charAt(0) != '0';
                        else if (args[i].equals("-t"))
                                timeMS = new Integer(args[++i]).intValue();
                        else if (args[i].equals("-h") || args[i].equals("--help"))
                                help();
                        else
                        {
                                System.err.println("unrecognized option '" + args[i] + "'");
                                help();
                                return;
                        }
                }
                
                System.out.println("running with " + n + " threads during " + timeMS + "ms with" + (prio? "": "out") + " priority on last thread.");
		
		System.out.println("Doing stats for " + n + " threads during + " + ((timeMS + 999) / 1000) + " seconds");
		System.out.println("");
		
		test(new CounterNoLock(), n, timeMS, prio);
		test(new CounterAtomic(), n, timeMS, prio);
		test(new CounterLock(), n, timeMS, prio);
		test(new CounterSyncProc(), n, timeMS, prio);
		test(new CounterSyncObj(), n, timeMS, prio);
		
		System.out.println("");
		System.out.println("stopped");
	}
}
