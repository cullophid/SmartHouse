
## Design

* Develop the overall software architecture
    * Identify major system components
    * Specify interactions between system components
    * Specify system components
        * interfaces
        * semantecs
* The Design section should allow a skilled programmer to implement the system

NOTES:
the system has two steps.
* first it analyzes collected user data. This is one way communication. The user acts and the system listens. this part is done, and have been tested. Training period. 
*Now the system evolves by adjusting to the user. The System still collects data, as in step one, but itnow interacts with the house, and the user reacts to the system. The system evolves based on the reactions of the user. The system never stops evolving. 



### Sensor data

In order to train the system, some sensor data is needed, but how to obtain it? A couple of options are available:

1.  Physical data, setting up wireless motion sensors and switches in a home, and collect the sensor data in a database. 
    1a      A full installation, where the wireless switches are able to turn the light on and off, and by extension also the system.
    1b      A placebo installation, where the wireless switches doesn't control the light, and merely collect training data.
2.  Simulated data, sing a simulator to generate data.
3.  Constructed data, manually or algorithmicly generated data.

Looking only at the quality of the data, the best would be to setup an entire house with switches controlling the light, and motion sensors in every room. However installing the system into an existing home would be difficult. The motion sensors and wall socket switches would be fairly easy, but most homes have ceiling with wires in the walls. This means a complete installation of wireless switches would be difficult and costly. 
A placebo solusion to the complete installation would be to have wireless wall socket switches next to all the actual switches. By have the users press the placebo switches and the actual switches when they use the light, it's possible to get the training data without having to permanently install the system into the home. It does come with the drawback that it will only be able to generate data for the untrained stage, since the system would not be able to manipulate the actual switches.

People are not robots, and while we are creatures of habit, our movement patterns do not run like clockworks. No matter how well we would generate training data using simulators, algorithms or any other artificial method, there would always be a doubt on how close to actual human behavior it actually is.

We chose to install a placebo system of wireless switches and sensors, to collect training data. This gives us the best quality training data, for the untrained stage of the system, without the expenses of installing operational wireless switches. 

With the training data, we can then use a simulator to evaluate the training stage. In the training stage, there it doesn't take that much data, to evaluate that the system is learning properly. The data from the simulator is good enough to simulate simple movement patterns, to see which lights go on or off, as a simulated user moves from room to room. 


### Event patterns

One thing is knowing where the user is, another where the user is headed. By also looking at the preceding interval leading up to an event, it's possible to match that against previously observed patterns, to estimate where the user might be headed.

To determine these pattern we try to make some rules about what to look for. If too long time passes between event, the event are probably not part of the same movement pattern. But what is too long time? TODO

### Zones

In many cases to cover an entire room with sensors, the sensors end up overlapping in some areas. This overlapping can be used to increase the precision of the sensors. If two sensors triggers shortly after each other, then the user is in the zone where the two sensors overlap. In cases where multiple sensors triggers at the same time, it can be seen as one zone event.

[Take](#zoneimg) as an example, of three sensors which overlap a bit, and three paths past the sensors a, b and c. The paths b and c should only be observed as zone events by the system. While path a should look something like 1, zone 1 & 2, 2, zone 2 & 3, 3. depending on the cooldown of the sensors each event may be multiple times in the pattern.

![Sensors with overlapping zones](img/zone.png)
### Switch and sensor correlation

It is beneficial to get a sense of which sensors are near which switches. And we have a lot of statistical data too look at. When a user turns a which on, it most likely because there isn't light where the user intends to be in the immediate future. So it's possible to get an idea of which sensors are near a which, by looking at the interval shortly after a switch is turned on.

TODO maybe talk about that is is less likely that a user will turn on a switch on, and then not enter that room

When flicking a switch off, the user may be leaving the room, or just have entered the room to turn the switch off. Each of the two cases are just as likely as the other, but the sensor events in the interval leaving up to the off event is completely opposite. 

TODO you could possebly look at the interval after it's turned off, and say there are less likely to be in the room, and then try to reduce the correlation for those sensors (NYI)

Based on the statistical data it is possible to generate a table of probability that a sensor is triggered shortly after a switch is turned on, and by extension of that give a idea of wich sensors are in the same room as a switch

\\[ P(sensor_i | switch_j , \Delta t) = \frac{\sum 1_{sensor_i} (switch_i, \Delta t) }{\sum switch_j \ events } \\]

The indentity function \\( 1_{sensor_i} (switch_i, \Delta t) \\) is 1 if the sensor is triggered within \\(\Delta t\\) after \\(switch_j\\) is triggered, and is not therefor not counted twice, in the sensor triggeres multiple times after the same switch event.

So to reiterate \\( P(sensor_i | switch_j , \Delta t) \\) is the probability that \\(sensor_i)\\) fires within \\(\Delta t\\) after \\(switch_j\\) fires.

|                       | sensor 1 \\((se_1)\\)          | sensor 2 \\((se_1)\\)          | ... | sensor n \\((se_n)\\)          |
|:---------------------:|:------------------------------:|:------------------------------:|:---:|:------------------------------:|
| switch 1 (\\(sw_1)\\) | \\(P(se_1 | sw_1, \Delta t)\\) | \\(P(se_2 | sw_1, \Delta t)\\) | ... | \\(P(se_n | sw_1, \Delta t)\\) |
| switch 2 (\\(sw_2)\\) | \\(P(se_1 | sw_2, \Delta t)\\) | \\(P(se_2 | sw_2, \Delta t)\\) | ... | \\(P(se_n | sw_2, \Delta t)\\) |
| \\(\vdots\\)          | \\(\vdots\\)                   | \\(\vdots\\)          | \\(\ddots\\) | \\(\vdots\\)                   |
| switch m (\\(sw_m)\\) | \\(P(se_1 | sw_m, \Delta t)\\) | \\(P(se_2 | sw_m, \Delta t)\\) | ... | \\(P(se_n | sw_m, \Delta t)\\) |
[Correlation table][ctable]

