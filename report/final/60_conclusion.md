
## Conclusion

* summarises all the result of the project
	* what was the problem?
	* what has been achieved?
* presents final conclusions
	*summary of provisional conclusions
	* further conclusions drawn from the sum of evidence
* presents directions for future work
	* new problems identified through the project
	* outline the possible evolution curve of the software

### writing good conclusions

* What was the problem?
    - Remind the reader of the context and project goals
* What was the proposed solution?
    -Remind the reader of the proposed solution
        -what was done in the project
* How did we evaluate the proposed solution?
    -Summarize results of indicidual experiments.
        -this inclydes any testing of software in development projects
    -Draw conclusions on the endicidual experiments
* What did we learn?
    -Present overall conclusions of the project
    - Outline ideas for future work
    
    
### Future work

#### Learning and Evolution stage

The next phase of development for the project would be to get ready for the learning and evolution stage. It would be necesary to create a fully functional installation of sensors and switches in a home, so in the system is able to manipulate the light, and monitor the system's interaction with the user. 

#### Switch and sensor correlation

We base our statistical correlation table on the assumption, that a user will most likely turn on the light where he is, and look at the interval just after a switch is turned on. A way to augment that analysis, is by flipping the assumption on it's head, that the user will most likely turn off the light where he isn't. The user is most likely not going to be where the lights are off, so any sensors activated when the lights are off, are most likely not in the same room as the switch. 

#### Decision matrix persistency

The longer back in time the system looks for user data, the more likely it is too see each pattern multiple times. The more times the system sees a given pattern, the more confident the system can be in the probabilities for that pattern. However the system should also be able to react to changes in user behavior, so there is a limit to how long back in time the system should look. 

To be able to best react changes, the system should only keep the most recent data. But this would drasicly reduce the systems confidence in the decision matrix. A static way to solve the problem would be to always look a fixed period of time back, attempting to strike a balance between the systems confidence and ability to react.

A dynamic way to solve the problem would be to compare the most recent patterns to the old patterns. As long as there is a resonably low discrepency, the system can keep using old data. And if the discrepency gets too big, the system base it decisions purely on recent data, to better react to the changes in user behavior.

#### Only looking at patterns when moving between rooms

The system could use the statistical correlation table to only look at event patterns where the user moves between two different room.

