### HW3 Feedback

**CSE 331 18sp**

**Name:** Shi Ting Nie (nies)

**Graded By:** <Weifan Jiang> (cse331-staff@cs.washington.edu)

### Score: 90/100
---

**Problem 3 - HolaWorld:** 5/5


**Problem 4 - RandomHello:** 9/10

- -1: should use nextInt(array.length) instead of nextInt(5) in getGreeting() since if array length changes, there is one less thing to update.

**Problem 5 - Testing (Fibonacci) Java Code with JUnit :** 5/5


**Problem 6 - Answering Questions about the (Fibonacci) Code:** 8/15

- Question 1: 5/5
- Question 2: 0/5
  - -5: Actually, testBaseCase fails because it throws exception when n = 0, so the fix in part a actually solves this problem.
- Question 3: 3/5
  - -2: Missing another cause of the problem such that n = 2 was treated as a base case but actually an inductive case.

**Problem 7 - Implementation:** 63/65

- Ball.java: 5/5
- BallContainer.java: 25/25
- Box.java: 33/35
  - -2: add() fails to work properly when the added balls exceed capacity.
