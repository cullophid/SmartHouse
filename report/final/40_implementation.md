
## Implementation

* Description of the system components
* The implementation section should allow a skilled programmer to maintain the software

* __Remember, the most important documentation of the implementation is comments in the code!__

### The physical setup

Since we needed real life data to train the system, the first task of the project was to create a physical system to start collecting data. We installed wireless switches and PIR sensors[^pir] in [David's appartment](#Hellebaekgade). The placebo switches were placed next to the normal switches controlling the light for each room, in all cases being the switch closest to the entrance. We installed a total of 10 motion sensors and 5 switches throughout the apartment, that collected data non stop for a period of two weeks.

The sensor setup consisted of three sensors in the living room, two sensors in the hallway, kitchen and bedroom, and one in the bathroom. When placing the sensors, we tried to provide as close to full coverage as possible, with special emphasis on making sure all the doorways were covered. 

[^pir]: Passive infrared sensors.
![Map of the testing environment with sensor and switch locations][Hellebaekgade]

The wireless nodes we have available communicate using the Zensys Z-Wave protocol[^z-wave]. This protocol was chosen because we already prior to this project had designed and implemented a z-wave API[^api] in java. This greatly reduced the time and effort needed to setup and implement the data collection system.

![Sensor installed in the bedroom][bedroom]
![Sensor installed in the livingroom ][livingroom]
![Sensor installed int the bath room][bathroom]
![Switch installed int the living room][switch]


<insert images>
We setup a mini PC with a Z-Wave serial device, and configured all PIR sensor and switches to send notifications to the PC, when they where activated. The PC ran a Z-Wave API, which we added a listener to, so that sensor and switch event was logged to a SQL database.

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
<TODO credit the guys who wrote the simulator>


### Configuration
The config class in created as a simple static class that uses a file reader to load a config file stored on the hard drive. The config class initially holds the default values for the system, which are overwritten with the values from the config file.
If no config file is present on the system, the config class generates a file based on the default values. After loading the config file, the other classes in the system, can then access the static fields of the class. These values are never altered after initially loading the config file.

A typical config file could look like this:

	#automatically generated preferences file
	#delete to return to default settings
	pattern_interval 3000
	pattern_length 2
	probability_threshold 0.01
	use_zones true
	zone_interval 500
	correlation_interval 7000
	

### Event patterns

To make lookups based on the lastest event pattern, each new sensor event needs to be a matched to see if it's part of a pattern.
As each sensor and switch event is received by the system, a list of the most recent event pattern is maintained in an EventList. 

EventList determines if the lastest event is part of the pattern, and determines if a zone event has occured (if set to use zone events). 

The invariant of the EventList, is that after an event is added, the event list contains the current event patttern. This pattern can then be used to determine if any switches should be turned on or off.


### Decision Matrix and KeyList
The Decision Matrix is the class that holds the decision table. The class consists of the two matrices "on" and "off"  which are the two decision tables.Instead of implementing the matrices as multidimensional arrays we have chosen to use hash-maps were the key is an array of length n. There are two main advantages to using hash maps instead of multidimensional arrays. The first advantage of this is that the lookup time is much faster in a hashmap, than an n-dimensional array. This is especially true when the amount of data in the system increases, and when increasing the number of dimension, i.e. increasing the pattern length. Secondly the multidimensional array would be much larger since it would have to allocate space for every possible pattern instead of just the ones extrapolated from the collected data.

Using an array as a key for the hash map does however come with a few problems. The main issue is that the hash function for arrays is inherited from the object class. This means that two arrays containing the same elements will produce different hash codes.  In order for our map to function properly, identical arrays must produce identical hash codes. The same problem occurs when comparing arrays using the equals() method. 

 We addressed this problem by implementing a KeyList class with a custom designed hashCode() and equals() method. The equals method was done by individually comparing each element in the list, and returning true, if the pairwise comparisons all succeeded. The hasCode() method is based on the hashCode method used in the String object in java. The method iterated through each element in the list, and for each value the sum of the previous values are multiplied by 31, and the current value is added. This ensures a very low collision rate with the amount of sensor and switches that are likely to be used in a private home.
 
 Besides the increased speed when performing lookup operations, the main advantage of using Hash maps is that it greatly simplifies extracting the keylist from a specific value. This is necessary when we divide the values in the decision maps "on" and"off" with the values in the denominator map. This is done by iterating through the decision maps, and for each value we extract the key, remove the last element, the switch event, and converts the resulting EventList into a KeyList to be used in the denominator map. When using Hash Maps this process is simply done using the keySet() method. but if we used multidimensional arrays instead, we would have to iterate through all possible key combinations in an array of unknown dimensions.
 
The Hash maps are generated in the method generateBasicMatrices(). This function first sends a query to the database returning all existing events. As the system scales, this will be have to be changed since collecting all the data using a single query could be a problem especially on a system with limited virtual memory. During the course of the project the size of the database never exceeded 1.3 MB, so it will require a substantial amount of data to cause problems for an average laptop.

Once the data is returned from the database the system iterates through the resultset, and inserts the data into the hashmaps as described in the design chapter. Finally the values in the maps "on" and "off" is divided by the corresponding values in the denominator map.

 If the use_zones option is enabled in the config file, the Decision matrix will repeat the process above using an EventList with zones enabled. This is done in the method generateZoneMatrices(). This time how ever a pattern not containing a zone event will not be added to the decision maps. This method uses temporary decision maps called "zoneOn" and "zoneOff". After the probability values in these maps have been properly calculated, they are appended to the original decision maps "on" and "off". 


### Correlation table
<intro to the correlation table>
#### Correlation statistical generation

Correlation calculates the probability that a sensor is correlated a switch. It scans the database, and looks at the interval just after a switch is triggered. The sensors that triggered in the interval, are counted for that interval, in a way thay they're only counted once per switch event. If a multiple switch event are triggered in the same interval, the sensor events in the overlapping intervals should be counted for each of those switches. Having the number of times each switch is triggered, and each sensors triggeres with the given time interval, it's then calculated the probability that \\( sensor_i \\) is triggered, given that a \\( switch_j \\) was turned on atmost \\( \Delta t \\) ago. This gives the statistical correlation probability table. 

To this the correlation confirmations in the database, is then added. Each row in the database table contains the accululated correlation correction for that switch / sensor pair. The correlation correction is simply added to the correlation based on the statistical data.

The resulting correlation table is allowed to have probabilities above 100%, which is inteded as destribed in <TODO add reference>

#### Correlation correction

When a switch is turned on, a timer is started for that switch. If a correlated sensor is triggered, it timer is extended. The duration is determined by the correlation between the sensor and the switch, higher correlation gives longer timeouts. If the switch is turned off, the timer is stopped. If the timer runsout a timeoutevent is triggered, and the light is turned off, and a new timer is started, to verify that no manual overrides occur. If the a manual override occurs (e.g. the user turns the switch on again, while the timer is running), the system is "punished". The system increases the timeout time, by increasing the correlation between the switch and the first sensor triggered after the switch was turned off. If no manual override occurs, the system was correct in turning off the light, and lowers the timeout time, by reducing the correlation between switch and the last seen sensor before the switch was turned off.

These correlation corrections are stored in a separate table in the database. The correlation use for the timeout is based on both the statistical correlation, and these correlation corrections. The correlation corrections increase or reduce the correlation by 10 percent points. The system allows correlations higher than 100%, this gives the intended behavior that a switch may have a longer timeout than what is default.

| correlation_confirmation ||
|-------------|-------------|
| switch      | Integer     |
| sensor      | Integer     |
| correlation | Float       |
[Database table for correlation corrections][corr corr table]


### Timers and timeout

Timers are implemented in the Timer and Sleeper class. Sleeper is a fairly simple class, it sleeps starts a new thread, sleeps for a given time, then fires a timeout event to a given timeout listener. Timer simply holds a map, where each switch can set a timeout. Timer creates a sleeper object, and puts in the map. The sleepers can then easily be monitored and interrupted if needed. 

To received the timeout events the SmartHouse class implements TimeoutListener. 

[bedroom]: figures/bedroom.jpg "A PIR motion sensor installed on location in hellebækgade" width="300px"
[livingroom]: figures/livingroom.jpg "A "placebo" switch installed on location in hellebækgade" width="300px"
[bathroom]: figures/bathroom.jpg "A PIR motion sensor installed on location in hellebækgade" width="300px"
[switch]: figures/switch.jpg "A "placebo" switch installed on location in hellebækgade" width="300px"
