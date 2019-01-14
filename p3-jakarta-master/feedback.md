# Project 3 (Chess) Feedback #
## CSE 332 Autumn 2018 ##

Team: Shi Nie (nies) and Charles Yao (yaoc2)
**Graded By:** Chris Choi (cjc61@cs.washington.edu)
<br>

## Unit Tests ##

**Minimax**  `(4/4)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>

**ParallelMinimax**  `(15/15)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>
> ✓ Passed *depth4* <br>

**AlphaBeta**  `(9/9)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>
> ✓ Passed *depth4* <br>

**Jamboree**  `(20/20)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>
> ✓ Passed *depth4* <br>
> ✓ Passed *depth5* <br>

## Clamps Tests ##

*Score*
`(7.5/8)`


--------

## Miscellaneous ##

`(-4/0)`
Your ParallelSearcher computes, then does all of its forks in the divide cutoff
section. This isn't quite as bad as computing all of the threads, but it does lead
to a lot of slowdown.

`(-3/0)`
In the divide-and-conquer cutoff, your
JamboreeSearcher should save one move to compute in
the current thread (instead of forking it)


--------

## Write-Up ##

**Project Enjoyment**
`(3/3)`
Glad you had fun!

**Chess Server**
`(2/2)`

### Experiments ###

**Chess Game**
`(4/6)`
You should discuss your conclusion a little more in depth. Why do you think
Jamboree checks more nodes than Alphabeta?

**Sequential Cut-Offs**
`(2/7)`
Your graph doesn't have units and you don't discuss your results.

**Number of Processors**
`(2/7)`
Again, your graph and tables don't have units and you don't discuss your
results. Why do you think the fastest number of processors is 32? In addition,
why didn't you test any of the odd numbers for processors?

**Comparing the Algorithms**
`(4/7)`
Your table and graph lack units.

### Traffic ###

**Beating Traffic**
`(4/4)`
Nice work!

### Above and Beyond ###

**Above and Beyond**
`(EX: 0)`
