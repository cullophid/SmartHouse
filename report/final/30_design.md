<add explanation of config>
## Design

_The best computer is a quiet, invisible servant._ -Mark Weiser

In this chapter we will describe the design process, and discuss the major decisions we have made in regard to the system design. Since the system is research minded, and since the purpose of the project is to analyze the possibilities of developing an intelligent home control system using machine learning technology, we had to make some adjustments to the development process. The traditional waterfall model[^waterfallmodel] for software development dictates that after finishing the project analysis, we would start designing how the system should handle the problems found in the analysis, along with the system architecture. Finally we would then implement the designed solution. With this project we were however faced with an additional challenge. When using machine learning you generally end up with a system that does not have an intuitive execution flow. This means that it can be almost impossible to predict the execution outcome because of the vast amounts of data that form basis for the systems decision making. This means that we have no way of verifying the validity of our proposed solution before implementing the system, or at least parts of it. Therefore we decided to approach the project by using incremental development instead[^incremental-development]. <perhaps describe incremental development>

In order to successfully apply this development model we must first divide the project into smaller parts, that can be implemented with each cycle. This design approach also inspired our final system design. Just like the development had several phases, where each phase had to be concluded in order to activate the next, the system will have similarly <huh?> have different stages of operations. These stages are determined by the amount of data the system have collected on the user.

The system will have two different stages of operation. 

* In **The passive learning stage**, the system is running, but it has not yet collected enough data to make intelligent decisions. This stage is called the passive learning stage because the system is training it self by  

* The system enters **the active learning stage** when there's enough data to attempt to manipulate the switches in the house. We call this the active learning stage, because the system now actively attempts to interact with the house's switches . If the system makes a mistake and the user corrects it, e.g., the system turns off the lights and the user turns it back on, we can use that interaction to train our system further. In this case we can see it as the user punishing the system for making a mistake. The system will then adjust its decision scheme.  This way the system will actively initiate a learning sequence. The system will remain in this stage indefinitely, and will continue to train it self using both passive and active learning. 

By using incremental development we are able to design and implement the system one stage at a time, and evaluate the passive part of the system before designing the active part.

In this chapter we will discuss the different stages of the system, the problems that are present in each stage, and the solutions designed to solve these problems. 

In the section "Theory" we will present the mathematical and statistical theory, that forms the basis for our machine learning algorithms.
 
This data collection in the system is very simple, and will not be discussed in this chapter. In the chapter "Implementation" this process will be described in detail.<rephrase>

The section "The passive learning stage" consists of three subsections. In the sections "Event pattern" and "Decision table" we will discuss how the system analyses the passively collected data. As discussed in the chapter "Analysis" using motion sensors can reduce the precision, and reliability of the collected data.In the subsection "Zones" we will discuss our approach to solve these problems. We will also provide a brief evaluation of the system in this stage, which will form the basis for the design of the active learning stage.

In the section "The active learning stage" we will discuss the additional processes that are present in this stage. These processes are made in response to the  problems we have identified in the evaluation of the passive learning stage.

### Theory

_"Stand back! I'm going to try science!"_ -Randal Munroe

In the core of our system lies a series of machine learning algorithms. In this section we will explain some of the basic concepts of machine learning, along with the statistical theory that it is based on.

#### Machine learning
The purpose of machine learning is to have the system evolve behaviors based on empirical data, rather than programming a specific behavioral pattern.  By using the supplied data as examples of relationships between data events, the system can recognize complex patterns, and make intelligent decisions based on the data analyzed[# wiki-machinelearning]. 

With **supervised learning**[^supervised-learning] the system is give labeled data consisting of examples of correct behavior. Because of both the human factor, and the imperfection of the motion sensors, the system will generate a certain amount of invalid data called noise. The algorithm will have to distinguish between what is proper training examples and what is noise.
  
**Active learning** is a form of supervised learning where the learner (the computer) prompts the user for information. In this form of learning the system initiates the interaction with the user, and trains it self based on the users response. This is especially useful if the system is generally well trained, but lacks training in specific areas. 
  
#### Markov chains

A Markov chain is a mathematical system that under goes transitions from one stage to an other [#markov chains]. In a Markov system each step taken in a Markov chain is represented by a certain probability, based on the current state that the system is in. Formally:

\\[P(X_{n+1} | X_n) \\]

Here \\(X_{n+1}\\) represents the next state, and \\(X_n\\) represents the current state. And the entire notion is defined as the probability of the event \\(X_{n+1}\\) given that event \\(X_n\\) has just occurred. 

By arranging these values in a matrix you can create a lookup table for future reference.

|                | \\(X_1\\) | \\(X_2\\) | \\(X_3\\) | \\(X_4\\) | \\(X_5\\) |
| :---------- | :--------------------: | :--------------------: | :--------------------: | :--------------------: | ---------------------: |
| \\(X_1\\) | P(\\(X_1| X_1)\\) | P(\\(X_1| X_2)\\) | P(\\(X_1| X_3)\\) | P(\\(X_1| X_4)\\) | P(\\(X_1| X_5)\\) |		 
| \\(X_2\\) | P(\\(X_2| X_1)\\) | P(\\(X_2| X_2)\\) | P(\\(X_2| X_3)\\) | P(\\(X_2| X_4)\\) | P(\\(X_2| X_5)\\) |
| \\(X_3\\) |  P(\\(X_3| X_1)\\) | P(\\(X_3| X_2)\\) | P(\\(X_3| X_3)\\) | P(\\(X_3| X_4)\\) | P(\\(X_3| X_5)\\) |
| \\(X_4\\) | P(\\(X_4| X_1)\\) | P(\\(X_4| X_2)\\) | P(\\(X_4| X_3)\\) | P(\\(X_4| X_4)\\) | P(\\(X_4| X_5)\\) |
| \\(X_5\\) | P(\\(X_5| X_1)\\) | P(\\(X_5| X_2)\\) | P(\\(X_5| X_3)\\) | P(\\(X_5| X_4)\\) | P(\\(X_5| X_5)\\) |

Each cell in the table represents the probability of entering the state represented by the cells row, assuming the system is currently in the state represented by the cells column.

#### Markov chains with memory 

One of the most iconic features of Markov chains is the fact that they are memoryless. The probability of entering a new state is only based on the current state of the system. The states prior to the current have no effect on this probability. With "Markov chains of order m"  the system has memory of the last m steps in the chain, and these affect the probability of entering future states. 
This probability can be written as: 
\\[P(X_{n+1}|X_{n},X_{n-1}, ..., X_{n-m} )\\] 
Now the probabilities are calculated based on the pattern of steps made through the system rather than just the current state.

Since our probabilities are calculated based on collected data, we will not have to perform any complex statistical calculations.

### The passive learning stage

In the passive learning stage the system monitors the user and trains it self based on his actions. In this stage the system does not interact actively with the house

#### Event patterns
<add reference to markov chains!!!!> <ULTRA IMPORTANT ANDREAS!>
<use the phrase sensor pattern>
<pattern interval>
We want to be able to trigger the switches, based on more than just where the user is right now. We want to be able to look at where the user is coming from, and try to predict where the light needs to be turned on or off. So the light is already on when the user enters a room, and is turned off where it's not needed. 

We want to determine the series of sensor events, or pattern, that leads up to a user turning the lights on or off, e.g. which sensors are triggered when a user goes from the couch to the restroom. If a series of sensor events, are less than some time interval apart, we consider them to be part of an event pattern. The time interval needs to be long enough, that a user moving around normally is seen as a continuous event pattern, and not broken into fragments. The time interval also needs to be short enough, that different user action, is seen as separate event patterns. For instance, a user going the kitchen to get a snack, and then returns to the living room, should ideally be seen as two separate event patterns.

With the idea of an event pattern, we can look at what patterns lead up to a switch event. And by extension of that analysis, when we observe an event pattern, we can determine the probability that it would lead to a switch event. 


#### Decision Table
In the core of the intelligent system lies the decision table. This is the product of the machine learning algorithm. The decision table is designed to be an efficient lookup table that the system can use as a decision scheme for its artificial intelligence. 

The algorithm for training the system in this stage is based on the concepts of passive supervised learning, since the user generates concrete examples for the system to follow. The data are labeled by type of event (sensor, switch), and the switch events are further divided into "on" and "off" events. These labels help the system determine how to analyze each pattern of events.

The decision table is designed as a Markov matrix, but we need the system to be able to handle Markov chains with memory, since we are tracking patterns, instead of single events. This effects the design of the Markov matrix.

Lets start by looking at the simple system with a pattern of length 1. Here we can simply use the Markov matrix described in the theory section. 

| switches \ sensors | sensor 1 | sensor 2 | sensor 3 | 
| :-----: | :-----: | :-----: | :-----: | :-----: | 
| switch 1| \\(P(switch 1 | sensor 1)\\) |\\(P(switch 1 | sensor 2)\\) |\\(P(switch 1 | sensor 3)\\) | 
| switch 2| \\(P(switch 2 | sensor 1)\\) |\\(P(switch 2 | sensor 2)\\) |\\(P(switch 2 | sensor 3)\\) |
| switch 3|  \\(P(switch 3 | sensor 1)\\) |\\(P(switch 3 | sensor 2)\\) |\\(P(switch 3 | sensor 3)\\) |

For each set of sensor and switch events, the table above holds the probability of the switch event occurring, given that the sensor event has just occurred. This table acts as a relation table between the sensors and switches, in a system based on traditional Markov chains. In our system this means the pattern length is 1.

When we expand the Markov matrix to handle chains with memory, the matrix becomes more complicated.  In the table above the number of cells is given by the number of sensors in the system multiplied by the number of switches in the system:
\\[\#switches \cdot \#sensors\\]
When we add a sensor event to the eventlist the number of cells in the matrix is multiplied by the number of switches again. This results in the general formula:
<alternativ formulering: When we increase the pattern length of the eventlist, the number of cells in the matrix multiplied by the number of switches.>

\\[ \#switches  \cdot \#sensors^{pattern length} \\]

As a result of this we see that for each event we add to the eventlist the matrix must be expanded by a new dimension. Thus a pattern length of n results in an n-dimensional matrix. 

As mentioned above we cannot at this moment determine what is the optimal pattern length, and therefore we must develop a system design that is flexible enough so that we can change the pattern length. This means that the decision table must be of n dimensions.
<insert figure>

One advantage is that, since we are only interested in the users behavior related to his interaction with the wall switches, we only need to handle the patterns where the last event is a switch event. We must now go though our database, and for each switch event we must extract an eventlist consisting of that event and the n-1 sensor events preceding it. The decision matrix will consist of the number of times a pattern has occurred in the collected data. This value is then divided by the number of occurrences of the eventlist without the switch events. Thereby the value of each cell in the matrix will be classified as the number of times a pattern has been observed divide by the number of times the pattern excluding the switch event has been observed.
<insert figure>

This value can also be interpreted as the probability that a specific switch event will occur after observing the pattern of sensor events. 



The system must also be able to handle patterns that are shorter than the maximum length, in case the pattern leading up to a switch event is smaller than the maximum pattern length. This could for example occur if the interval between two events have been too long. 

The algorithm that handles the table generation looks as follows:

	GenerateDecisionTable(events[]);
	lastevent = 0
	map decision_table
	map denominator
	queue eventlist
	
	for event in events
	do
	    if event is sensorevent
	    do
	        if event.time <= lastevent + pattern_interval
	        do 
	            push event to eventlist
	            if eventlist.length > pattern_length
	            	remove tail from eventlist
	        else
	            clear eventlist
	            push event to eventlist
	        done
	        insert event into denominator
	        lastevent = event.time
	    else if event is switchevent
	    do
	        if event.time <= lastevent + pattern_interval
	        do
	            insert event into decision_table
	        else
	            clear eventlist
	            add event to eventlist
	        done
	    done
	done
	
	for entry in decision_table
	do
	    extract eventlist
	    divide by matching denominator
	done
	
First the algorithm creates two maps: decision_table and denominator. The decison_table will, as the name suggests, hold the decision table. The denominator maps is used to keep track of the number of times each pattern of sensor events occur. This is used as the denominator when finding the probability in the decision table.  The eventlist always contains the last n events in the system, unless the time between events exceeds the value stored in pattern_interval. The algorithm now runs through the collected data in chronological order. 

If the current event is a sensor event, this is added to the eventlist, assuming that the time since the last event has occurred has not exceeded the pattern interval.	The eventlist is now used to navigate through the n dimensional matrix denominator, and increase the occurrence of the pattern by 1.

If the current event is a switch event, this is added to decision table	in the same fashion as with the denominator matrix. Since we are not interested in patterns that contains more than one switch even, the eventlist is now emptied.

Finally each value in the decision table is divided by the corresponding value in the denominator tables. This is done by extracting the eventlist from the decision table and using it to navigate the denominator matrix. 

The entire algorithm is run both for "on" and "off" switch event. This results in two separate tables, one for turning the lights on, and on for turning them off.


#### Zones
<add the other benefit>
In order for motion sensors to cover an entire room, the sensors tends to end up overlapping. These overlaps can be used to increase the precision of the sensors. If two sensors triggers shortly after each other, the user must be in the overlapping area between the two sensors. In cases where multiple sensors triggers at the same time, it can instead be seen as a single zone event.

[Take](#zoneimg) as an example, three sensors (1, 2 and 3) with overlap, and three paths the user could take (_A_, _B_ and _C_). The paths _B_ and _C_ should only be observed as zone events by the system. Path _A_ should be detected as the event pattern [1, zone 1 & 2, 2, zone 2 & 3, 3].

![Sensors with overlapping zones][zoneimg]

[zoneimg]: figures/zone.png "Sensor zones"

Zones can also augment the system by removing ambiguity when a user enters an area where sensors over lap. For path c without zone events, it's uncertain if sensor 2 or 3 would detect the user first, and these would be considered distinct event patterns by the system. With zone detection, the pattern will look the same to the system no matter which sensor fired first, and as a result the system would be able to learn the intended behavior for path c faster. 

Zones allow the system to determine the user's position more precisely, and to learn faster by removing ambiguity in some cases.

### The active learning stage

A key element of the system, is the transition from the passive learning stage to the active learning stage. 

There are two main metrics we believe should determine when the system is confident enough:
The system should start attempting to control the home, once it is confident enough, to act upon the decision schemes it has learned. But the system needs to have some quantifiable metric to determine its confidence, before it start to take over control of the home:

1.  The probability in the decision scheme must be above a certain threshold. \\(P(switch_i | pattern_j) > \varphi \\)
2.  The specific \\(pattern_j\\) must have occurred at least a certain number of times.

Exactly what the threshold should be, is up to speculation and could be determined through experimentation, once the system in ready to enter the learning stage. The second rule is to make sure, the system doesn't start acting based on patterns only observed once.
<we have to do better than this!>

In this section will to discuss the learning that will take place in the evolution stage. 

#### Switch and sensor correlation

It is beneficial to get a sense of which sensors are near which switches. And we have a lot of statistical data too look at. When a user turns a which on, it's most likely because there isn't light where the user intends to be in the immediate future. So it is possible to get an idea of which sensors are near a which, by looking at the interval shortly after a switch is turned on.

<TODO maybe talk about that is is less likely that a user will turn on a switch on, and then not enter that room>

When flicking a switch off, the user may be leaving the room, or just have entered the room to turn the switch off. Each of the two cases are just as likely as the other, but the sensor events in the interval leaving up to the off event is completely opposite. 

<TODO you could possebly look at the interval after it's turned off, and say there are less likely to be in the room, and then try to reduce the correlation for those sensors (NYI)>

Based on the statistical data it is possible to generate a table of probability that a sensor is triggered shortly after a switch is turned on, and by extension of that give a idea of which sensors are in the same room as a switch

\\[ P(sensor_i | switch_j , \Delta t) = \frac{\sum 1_{sensor_i} (switch_i, \Delta t) }{\sum switch_j \ events } \\]

The identity function \\( 1_{sensor_i} (switch_i, \Delta t) \\) is 1 if the sensor is triggered within \\(\Delta t\\) after \\(switch_j\\) is triggered, and is not therefor not counted twice, in the sensor triggeres multiple times after the same switch event.

So to reiterate \\( P(sensor_i | switch_j , \Delta t) \\) is the probability that \\(sensor_i)\\) fires within \\(\Delta t\\) after \\(switch_j\\) fires.

|                       | sensor 1 \\((se_1)\\)          | sensor 2 \\((se_1)\\)          | ... | sensor n \\((se_n)\\)          |
|:---------------------:|:------------------------------:|:------------------------------:|:---:|:------------------------------:|
| switch 1 (\\(sw_1)\\) | \\(P(se_1 | sw_1, \Delta t)\\) | \\(P(se_2 | sw_1, \Delta t)\\) | ... | \\(P(se_n | sw_1, \Delta t)\\) |
| switch 2 (\\(sw_2)\\) | \\(P(se_1 | sw_2, \Delta t)\\) | \\(P(se_2 | sw_2, \Delta t)\\) | ... | \\(P(se_n | sw_2, \Delta t)\\) |
| \\(\vdots\\)          | \\(\vdots\\)                   | \\(\vdots\\)          | \\(\ddots\\) | \\(\vdots\\)                   |
| switch m (\\(sw_m)\\) | \\(P(se_1 | sw_m, \Delta t)\\) | \\(P(se_2 | sw_m, \Delta t)\\) | ... | \\(P(se_n | sw_m, \Delta t)\\) |
[Correlation table][ctable]

#### Correlation based timeout

Ideally the system will turn off the light by detecting off patterns, but in the learning stage or if the user changes behavior, this isn't reliable. We want to avoid is the light being on longer than it needs to, even if the system doesn't detect the off pattern. The user leaves a room and doesn't realize the light is still on, or expect the system to turn off the light on it's own, causing a necessary waste of energy.

We wanted to make a situation where no matter what happens the light is eventually turned off. The system has a timer for each switch, and as the user is detected by the sensors, the timer is extended based on the correlation to the switch. In a real scenario it's very like for any sensor to have at least some correlation to any switch, however low it might be. So the system has to avoid having all sensors extending the timeout ever so slightly, essentially keeping the light on for as long as sensors events keep firing somewhere. Therefor the correlation has to be above some threshold in order to extend the timeout. Ideally only sensors in the same room as the switch are extending the timeout.

#### Timeout adjustment

The problem with a timeout based solution, is people sitting still. Most people have experience controllable or programmable smarthouse solutions, where motion sensors keep the light on for some amount of time. And it tend to work great in spaces where people are passing through, hallways, carports, et cetera. But in places where people some times sit still, be it working or relaxing, motion sensors won't be triggered, and user end up having to get up or wave their arms to keep the lights on. So we allow the system to keep the light on for longer duration in some areas, based on which sensors are triggered, and also a way for the system to learn where these areas are. As already stated the base timeout is based on the correlation, which means sensors close to the switch will keep the light on longer. A common scenario in a home would be a user laying on a couch watching TV. So we want the system to be able to keep the lights on longer, if it detects that the user is on the couch. If a timer runs out, the system turns the switch off, and if the user immediately turns it back on again, the system takes that as a punishment for it's behavior. The system reacts by increasing the timeout those sensors have to the switch. Adversely if a timer runs out, and the user doesn't take any action, it assumes it's behavior was correct, and decreases the timeout.



### Running the system



