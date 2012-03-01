
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

The more user data the system has about a event pattern in the decision matrix, the more confident the system can be in the correctness of that decision. But the system should also be able to adapt to changes in user behavior. 

To be able to best react changes, the system should only keep the most recent data. But this would drasicly reduce the systems confidence in the decision matrix. 

En måde at fikse problemet på er at tillade system at bruge gammel data, så længe at det stemmer overens med de seneste observationer
A way to fix this problem would be to allow the system to use old data, as long as the user patterns doesn't appear to have changed. If the system observes that the user's behavior starts to differ from the old behavior, the system could the stop using old data. This could 

