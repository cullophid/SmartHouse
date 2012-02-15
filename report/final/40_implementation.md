## Implementation

* Description of the system components
* The implementation section should allow a skilled programmer to maintain the software

* __Remember, the most important documentation of the implementation is comments in the code!__

### Event patterns

To make lookups based on the lastest event pattern, each new sensor event needs to be a matched to see if it's part of a pattern.
As each sensor and switch event is received by the system, a list of the most recent event pattern is maintained in an EventList. 

EventList determines if the lastest event is part of the pattern, and determines if a zone event has occured (if set to use zone events). 

The invariant of the EventList, is that after an event is added, the event list contains the current event patttern. This pattern can then be used to determine if any switches should be turned on or off.

### Correlation correction

When a switch is turned on, a timer is started for that switch. If a correlated sensor is triggered, it timer is extended. The duration is determined by the correlation between the sensor and the switch, higher correlation gives longer timeouts. If the switch is turned off, the timer is stopped. If the timer runsout a timeoutevent is triggered, and the light is turned off, and a new timer is started, to verify that no manual overrides occur. If the a manual override occurs (e.g. the user turns the switch on again, while the timer is running), the system is "punished". The system increases the timeout time, by increasing the correlation between the switch and the first sensor triggered after the switch was turned off. If no manual override occurs, the system was correct in turning off the light, and lowers the timeout time, by reducing the correlation between switch and the last seen sensor before the switch was turned off.

These correlation corrections are stored in a separate table in the database. The correlation use for the timeout is based on both the statistical correlation, and these correlation corrections. The correlation corrections increase or reduce the correlation by 10 percent points. The system allows correlations higher than 100%, this gives the intended behavoir that a switch may have a longer timeout than what is default.

### Correlation table generation

Correlation calculates the probability that a sensor is correlated a switch. It scans the database, and looks at the interval just after a switch is triggered. The sensors that triggered in the interval, are counted for that interval, in a way thay they're only counted once per switch event. If a multiple switch event are triggered in the same interval, the sensor events in the overlapping intervals should be counted for each of those switches. Having the number of times each switch is triggered, and each sensors triggeres with the given time interval, it's then calculated the probability that \\( sensor_i \\) is triggered, given that a \\( switch_j \\) was turned on atmost \\( \Delta t \\) ago. This gives the statistical correlation probability table. 

To this the correlation confirmations in the database, is then added. Each row in the database table contains the accululated correlation correction for that switch / sensor pair. The correlation correction is simply added to the correlation based on the statistical data.

The resulting correlation table is allowed to have probabilities above 100%, which is inteded as destribed in **TODO**


### Timers and timeout

Timers are implemented in the Timer and Sleeper class. Sleeper is a fairly simple class, it sleeps starts a new thread, sleeps for a given time, then fires a timeout event to a given timeout listener. Timer simply holds a map, where each switch can set a timeout. Timer creates a sleeper object, and puts in the map. The sleepers can then easily be monitored and interrupted if needed. 

To received the timeout events the SmartHouse class implements TimeoutListener. 

