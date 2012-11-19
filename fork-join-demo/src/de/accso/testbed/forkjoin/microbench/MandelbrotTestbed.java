package de.accso.testbed.forkjoin.microbench;

import java.util.concurrent.ForkJoinPool;

import de.accso.testbed.forkjoin.mandelbrot.MandelbrotAction;

public class MandelbrotTestbed extends Testbed {
	
	private final int REPETITIONS = 10;

	private long measure(int forkJoinPoolSize, int maximumJobSize, int imageWidth, int imageHeight,
			int[] imageData) {
		MandelbrotAction proc = new MandelbrotAction(imageData, 0, imageHeight, maximumJobSize);
		ForkJoinPool forkJoinPool = new ForkJoinPool(forkJoinPoolSize);
		long nStart = System.nanoTime();
		forkJoinPool.invoke(proc);
		long nEnd = System.nanoTime();
		long nTime = nEnd - nStart;
		return nTime;
	}
	
	@Override
	public void simulate() {
		final int[] imageData = new int[2048*2048];
		final int[] poolSizes = new int[] {1, 1, 2, 4, 8, 16, 32, 64, 128 };
		for (int i = 0; i < poolSizes.length; i++) {
			int maximumJobSize = 1;
			while (maximumJobSize <= 2048) {
				final long[] times = new long[REPETITIONS];
				for (int j = 0; j < REPETITIONS; j++) {
					times[j] = measure(poolSizes[i], maximumJobSize, 2048, 2048, imageData);
					collectGarbage();
				}
				final double mean = mean(times);
				final double stddev = stddev(times);
				System.out.println(poolSizes[i] + "\t" + maximumJobSize + "\t" + (mean/1000000) + "\t" + (stddev/1000000));
				maximumJobSize = maximumJobSize << 1;
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		new MandelbrotTestbed().simulate();
	}
}