package de.accso.testbed.forkjoin.primes;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;

public class ParallelPrimeWorker {
	public static void checkResultSequentially(long from, long to, long result) {
		long seqresult=0l;
		for (long i=from; i<=to; i++) {
			seqresult+= (new PrimeProblem().isPrime(i)) ? 1 : 0;
		}
		if (seqresult!=result) 
			throw new RuntimeException("Something's wrong: Sequential result: " + seqresult + " is not the parallel result: " + result);
	}
	
	public static void main(String[] args) {
		System.out.println("Now: " + new Date());
		
		int MAXPROCESSORS = Runtime.getRuntime().availableProcessors();
		int NUMRUNS = 3;

		long[] froms = new long[] { 0l };
		long[] tos   = new long[] { 
				1000l, 10000l, 100000l, 
				//1_000_000l, 
				//10_000_000l, 
				//20_000_000l, 30_000_000l, 40_000_000l, 50_000_000l, 100_000_000l
				};

		long[] thresholds = new long[] { 1l, 10l, 100l, 1000l, 10000l, 100000l, 1000000l, 10000000l, 100000000l };
		
		for (long from: froms) {
		for (long to: tos) {
			for (long threshold: thresholds) {
				for (int numprocessors=1; numprocessors<=MAXPROCESSORS; numprocessors++) {
					try {
						long[] timesForAllRuns = new long[NUMRUNS];
						long result=0l;
						
						for (int run=0; run<NUMRUNS; run++) {
							CountPrimesTask task = new CountPrimesTask(from, to, threshold);
	
							ForkJoinPool pool = new ForkJoinPool(numprocessors);
							StopWatch stopWatch = new StopWatch();
							pool.invoke(task);
							stopWatch.stop();
							long timeAsMS = stopWatch.getElapsedTime();
							timesForAllRuns[run] = timeAsMS;
							result = task.result;
							
							// checkResultSequentially(from, to, result);
							// System.out.println("Computed Result: Number of primes between " + from + " to " + to + " is: " + result);
							// System.out.println("Elapsed Time using " + numprocessors + " processors: "+ (timeAsMS/1000.0d) + " s");
						}
						
						double avTimes=0d;
						for (long time: timesForAllRuns) {
							avTimes+=time;
						}
						avTimes/=NUMRUNS;
						avTimes/=1000.0f; // ms2s
						
						System.out.print(numprocessors +","+ threshold +","+ from +","+ to +","+ result +",");
						System.out.printf(Locale.US, "%.3f", avTimes);
						for (long time: timesForAllRuns) {
							System.out.print("," + time);
						}
						System.out.println(result);
						System.out.println();
					}
					catch (Throwable t) {
						t.printStackTrace(System.err);
						// ignore all errors
					}
				}
			}
		}}

		System.out.println("Now: " + new Date());
	}
}
