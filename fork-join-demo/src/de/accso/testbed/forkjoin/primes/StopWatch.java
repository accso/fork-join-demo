package de.accso.testbed.forkjoin.primes;

public class StopWatch {
	private long start;
	private long end;

	public StopWatch() {
		start = System.currentTimeMillis();
	}

	public void stop() {
		end = System.currentTimeMillis();
	}

	public long getElapsedTime() {
		return end - start;
	}
}
