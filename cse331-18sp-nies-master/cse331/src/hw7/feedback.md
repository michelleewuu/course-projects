### HW7 Feedback

**CSE 331 18sp**

**Name:** Shi Ting Nie (nies)

**Graded By:** <Jake Sippy> (cse331-staff@cs.washington.edu)

### Score: 69/77
---
**Problem 1 - Making Your Graph Generic:** 25/30

- Correctness: 18/20
  - Passing all tests for hw5. Good job!
  - 4 of 26 hw6 staff tests failed. (-1 for every 2)
  - Throwing exception in script files tests when finding paths between characters not in the graph
- Style: 9/10
  - It looks like you tried to define the generic parameters for your graph and edge classes but you should be more explicit. In your class descriptions, describe exactly what T and E represent without referencing other classes (-0)
  - You should not refer to private fields in your class comments (-1)

**Problem 2 - Weighted Graphs and Least-Cost Paths:** 25/30

- Correctness: 16/20
  - 4 of 24 staff tests failed. (-1 each)
  - same case as described for the hw6 tests, note that because the case where unknown characters are given to FindPath is allowed in the homework spec, either your implementation must allow it or you must handle this case in the test driver.
- Style: 9/10
  - Missing specification of whether a class is an ADT or not. (-0)
  - Declare variables and arguments with the interface types of ADTs if possible (for instance List instead of ArrayList).  This way your implementation is less sensitive to change. (-1)

**Problem 3 - Testing:** 15/15

- Good Job!

**Turnin:** 2/2

