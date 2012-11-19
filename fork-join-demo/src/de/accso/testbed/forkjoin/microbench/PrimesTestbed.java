package de.accso.testbed.forkjoin.microbench;

import java.util.concurrent.ForkJoinPool;

import de.accso.testbed.forkjoin.primes.CountPrimesTask;

public class PrimesTestbed extends Testbed {
	
	private final int REPETITIONS = 10;

	private long measure(int forkJoinPoolSize, int maximumJobSize) {
		CountPrimesTask task = new CountPrimesTask(0l, 1000000l, (long) maximumJobSize);
		ForkJoinPool pool = new ForkJoinPool(forkJoinPoolSize);
		long start = System.currentTimeMillis();
		pool.invoke(task);
		long end = System.currentTimeMillis();
		long time = end - start;
		return time;
	}
	
	@Override
	public void simulate() {
		final int[] poolSizes = {1, 1, 2, 4, 8, 16, 32, 64, 128 };
		final int[] maxJobSizes = {1, 10, 100, 1000, 10000, 100000, 1000000 };
		for (int i = 0; i < poolSizes.length; i++) {
			for (int j = 0; j < maxJobSizes.length; j++) {
				final long[] times = new long[REPETITIONS];
				for (int k = 0; k < REPETITIONS; k++) {
					times[k] = measure(poolSizes[i], maxJobSizes[j]);
					collectGarbage();
				}
				final double mean = mean(times);
				final double stddev = stddev(times);
				System.out.println(poolSizes[i] + "\t" + maxJobSizes[j] + "\t" + mean + "\t" + stddev);				
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		new PrimesTestbed().simulate();
	}
}