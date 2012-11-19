package de.accso.testbed.forkjoin.primes;
public class PrimeProblem {
	public boolean isPrime(long n) {
		if (n<=1) return false;
		if (n==2) return true;
		if (n%2 == 0) return false;
		for (int i=3; i<=Math.sqrt(n); i+=2) {
			if (n%i==0) return false;
		}
		return true;
	}
}
