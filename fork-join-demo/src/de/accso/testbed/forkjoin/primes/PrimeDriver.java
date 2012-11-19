package de.accso.testbed.forkjoin.primes;

import java.util.concurrent.ForkJoinPool;

public class PrimeDriver {
	public static void main(String[] args) {
		CountPrimesTask task = new CountPrimesTask(0l, 1000l, 10l);
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		pool.invoke(task);
		System.out.println(task.result);
	}
}