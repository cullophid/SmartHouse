
## Implementation

* Description of the system components
* The implementation section should allow a skilled programmer to maintain the software

* __Remember, the most important documentation of the implementation is comments in the code!__


### Training data collection

In order to collect training data, we installed wireless switches and PIR[^pir] sensors in a [home](#Hellebaekgade). The placebo switches were placed next to the normal switches controlling the light for each room, in all cases being the switch closest to the entrance. Each room have one or more PIR sensors, averaging 2 per room, with one lest in the restroom and an additional sensor in the living room. When placing the sensors, the system can obviouly only laern from behavoir in areas covered by sensors. So sensors should provide as close to full coverage as possible, with special emphasis on making sure the entrances are covered.

[^pir] Passive infrared sensors.
![Map of the testing environment with sensor and switch locations][Hellebaekgade]

The wireless nodes we have available communicate using the Zensus Z-Wave protocol. We setup a mini PC with a Z-Wave serial device, and configured all PIR sensor and switches to send notifications to the PC, when they where activated. The PC ran a Z-Wave API, which we added a listener to, so that sensor and switch event was logged to a SQL database.

| sensor_events        ||
|-----------|-----------|
| id        | Integer   |
| timestamp | Timestamp |
[Database table for sensor events][sensor table]

| switch_events        ||
|-----------|-----------|
| id        | Integer   |
| timestamp | Timestamp |
| status    | Boolean   |
[Database table for switch events][sensor table]

### Simulator / AI interface

We have a smart house simulator available, which will be extended with an AI module, implementing the features discussed in this report. The simulator is implemented in scala, so an obvious choise would be to implement the AI in scala aswell. However work with the simulator in the initial stages of the project, showed that our programming speed in scala was too slow to get any meaningful amount of work done. The scala language is build upon Java, and both languages compiles to bytecode in _.class_ files. A result of that is that Scala and Java interface very easily, and Scala code can invoke Java methods and vice versa. We chose to implement the AI in Java, working in a language we're well-versed in, to increase our productivity and quality of the code.

### Decision table / matrix / markov / thingie...

Antallet af gange den klasse har skiftet navn... I've lost count... 
<TODO Andy, you deal with it>

### Event patterns

To make lookups based on the lastest event pattern, each new sensor event needs to be a matched to see if it's part of a pattern.
As each sensor and switch event is received by the system, a list of the most recent event pattern is maintained in an EventList. 

EventList determines if the lastest event is part of the pattern, and determines if a zone event has occured (if set to use zone events). 

The invariant of the EventList, is that after an event is added, the event list contains the current event patttern. This pattern can then be used to determine if any switches should be turned on or off.

### Correlation table

#### Correlation statistical generation

Correlation calculates the probability that a sensor is correlated a switch. It scans the database, and looks at the interval just after a switch is triggered. The sensors that triggered in the interval, are counted for that interval, in a way thay they're only counted once per switch event. If a multiple switch event are triggered in the same interval, the sensor events in the overlapping intervals should be counted for each of those switches. Having the number of times each switch is triggered, and each sensors triggeres with the given time interval, it's then calculated the probability that \\( sensor_i \\) is triggered, given that a \\( switch_j \\) was turned on atmost \\( \Delta t \\) ago. This gives the statistical correlation probability table. 

To this the correlation confirmations in the database, is then added. Each row in the database table contains the accululated correlation correction for that switch / sensor pair. The correlation correction is simply added to the correlation based on the statistical data.

The resulting correlation table is allowed to have probabilities above 100%, which is inteded as destribed in <TODO add reference>

#### Correlation correction

When a switch is turned on, a timer is started for that switch. If a correlated sensor is triggered, it timer is extended. The duration is determined by the correlation between the sensor and the switch, higher correlation gives longer timeouts. If the switch is turned off, the timer is stopped. If the timer runsout a timeoutevent is triggered, and the light is turned off, and a new timer is started, to verify that no manual overrides occur. If the a manual override occurs (e.g. the user turns the switch on again, while the timer is running), the system is "punished". The system increases the timeout time, by increasing the correlation between the switch and the first sensor triggered after the switch was turned off. If no manual override occurs, the system was correct in turning off the light, and lowers the timeout time, by reducing the correlation between switch and the last seen sensor before the switch was turned off.

These correlation corrections are stored in a separate table in the database. The correlation use for the timeout is based on both the statistical correlation, and these correlation corrections. The correlation corrections increase or reduce the correlation by 10 percent points. The system allows correlations higher than 100%, this gives the intended behavoir that a switch may have a longer timeout than what is default.

### Timers and timeout

Timers are implemented in the Timer and Sleeper class. Sleeper is a fairly simple class, it sleeps starts a new thread, sleeps for a given time, then fires a timeout event to a given timeout listener. Timer simply holds a map, where each switch can set a timeout. Timer creates a sleeper object, and puts in the map. The sleepers can then easily be monitored and interrupted if needed. 

To received the timeout events the SmartHouse class implements TimeoutListener. 

