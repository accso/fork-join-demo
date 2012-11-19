package de.accso.testbed.forkjoin.primes;

import java.util.concurrent.RecursiveTask;

public class CountPrimesTask extends RecursiveTask<Long> {

	private static final long serialVersionUID = -5980424000062075505L;
	
	private static boolean verbose = false;
	
	private static long THRESHOLD;
	
	private long from, to;
	
	private int depth;

	public static long taskCounter = 0l;
	
	public long result=0l;

	public CountPrimesTask(long to, long threshold) {
		this(0,to,0);
		THRESHOLD = threshold;
	}

	public CountPrimesTask(long from, long to, long threshold) {
		this(from,to,0);
		THRESHOLD = threshold;
	}

	public CountPrimesTask(long from, long to, int depth) {
		this.from=from; this.to=to; this.depth=depth;
		
		taskCounter++;
	}

	@Override
	public Long compute() {
		if (from>to) return 0l;

		long counter = 0l;

		if ((to-from) <= THRESHOLD) {
			for (long i=from; i<=to; i++) {
				counter += (new PrimeProblem().isPrime(i)) ? 1 : 0;
			}
			result+=counter;
		} 
		else {
			long split1,split2;
			split1 = from + ((long)Math.floor((to-from)/2)-1);
			split2 = from + ((long)Math.ceil ((to-from)/2));
			printHelper("Split problem from ["+from+";"+to+"] into ["+from+";"+split1+"] and ["+split2+";"+to+"]");

			CountPrimesTask task1 = new CountPrimesTask(from,   split1, depth+1);
			CountPrimesTask task2 = new CountPrimesTask(split2, to    , depth+1);

			invokeAll(task1, task2);
			this.result = task1.result + task2.result;
		}

		return result;
	}
	
	private void printHelper(String s) {
		if (!verbose) return;
		System.out.print("["+Thread.currentThread().getName()+"]");
		for (int i=0; i<depth; i++) System.out.print("#");
		System.out.println(" " + s);
	}
}
