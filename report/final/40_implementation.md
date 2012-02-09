## Implementation

* Description of the system components
* The implementation section should allow a skilled programmer to maintain the software

* __Remember, the most important documentation of the implementation is comments in the code!__

### Probability

### Correlation

Correlation calculates the probability that a sensor is correlated a switch. It scans the database, and looks at the interval just after a switch is triggered. Since it most likely that the user who turned on the switch, intend to enter that room. The probability that a \\( sensor_i \\) is triggered, given that a \\( switch_j \\) was turned on atmost \\( \Delta t \\) ago. 

The table of correlation confirmations in the database, is then added to the statistical probability, to give the final correlation table.

### Event patterns

As each sensor and switch event is received by the system, a list of the most recent event is maintained in an EventList. EventList calculates if the lastest event is part of the pattern, and determines if a zone event has occured (if set to use zone events). 

### Correlation correction

When a switch is turned on, a timer is started for that switch. If a correlated sensor is triggered, it timer is extended. The duration is determined by the correlation between the sensor and the switch, higher correlation gives longer timeouts. If the switch is turned off, the timer is stopped. If the timer runsout a timeoutevent is triggered, and the light is turned off, and a new timer is started, to verify that no manual overrides occur. If the a manual override occurs (e.g. the user turns the switch on again, while the timer is running), the system is "punished". The system increases the timeout time, by increasing the correlation between the switch and the first sensor triggered after the switch was turned off. If no manual override occurs, the system was correct in turning off the light, and lowers the timeout time, by reducing the correlation between switch and the last seen sensor before the switch was turned off.

These correlation corrections are stored in a separate table in the database. The correlation use for the timeout is based on both the statistical correlation, and these correlation corrections. The correlation corrections increase or reduce the correlation by 10 percent points. The system allows correlations higher than 100%, this gives the intended behavoir that a switch may have a longer timeout than what is default.

### Timers and timeout

Timers are implemented in the Timer and Sleeper class. Sleeper is a fairly simple class, it sleeps starts a new thread, sleeps for a given time, then fires a timeout event to a given timeout listener. Timer simply holds a map, where each switch can set a timeout. Timer creates a sleeper object, and puts in the map. The sleepers can then easily be monitored and interrupted if needed. 

To received the timeout events the SmartHouse class implements TimeoutListener. 

