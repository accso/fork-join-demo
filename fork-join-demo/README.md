Contents
========

Some examples that show the usage of the Fork-Join framework which was introduced with Java 7. The repository also contains a test driver that performs a simple microbenchmark which can be used to gain some qualitative insights into the performance of the framework when parameterized with different settings. 

Package overview
================

de.accso.testbed.forkjoin.mandelbrot
------------------------------------
This package contains a Swing-based visualization of the Mandelbrot fractal. Use the main class Mandelbrot.java to start the GUI and play around with the visualization. The computation of the fractal relies on the Fork/Join framework. For details, please consult the code in MandelbrotAction.java.

de.accso.testbed.forkjoin.primes
--------------------------------
This package contains the implementation of a prime counting strategy that uses the Fork/Join framework. Use the main class ParallelPrimeWorker.java to start a console-based demonstration. For details on the Fork/Join-specific parts, please consult CountPrimesTask.java.

de.accso.testbed.forkjoin.microbench
------------------------------------
This package contains the implementation of a microbenchmark for both the Mandelbrot and prime number computations. We use this microbenchmark as a tool for finding suitable parameterizations of Fork/Join pool size and maximum job size. The maximum job size defines the maximum workload that gets carried out sequentially on a single thread. A given task is divided into smaller subtasks unless the individual task size <= maximum job size.
