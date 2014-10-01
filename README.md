
test code for different kind of mutex performances and priority influence with java.

N threads are started at the same time, no synchronisation barrier is used for that.
Each thread register itself in a protected running-counter, then in an infinite loop
increments (stopped after a fixed duration):
- a self counter,
- a shared counter (protected or not, with different implementations),
- another self counter only if running-counter is at max (all threads registered)

Then, stats are done:
- tell if the shared counter is protected (of course they are except for the not protected test)
- show the number of operations per seconds (hopefully someone explains me the huge differences)
- show the relative percentage of threads activity with priorities enabled
  (priorities are not implemented by all environment, but for the others,
   someone hopefully explains me why it works with AtomicLong and not with "synchronized")

main() takes options in the command line. Try with -h
