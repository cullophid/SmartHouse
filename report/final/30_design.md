## Design

* Develop the overall software architecture
    * Identify major system components
    * Specify interactions between system components
    * Specify system components
        * interfaces
        * semantecs
* The Design section should allow a skilled programmer to implement the system



### Switch and sensor correlation

It is beneficial to get a sense of which sensors are near which switches. And we have a lot of statistical data too look at. When a user turns a which on, it most likely because there isn't light where the user intends to be in the immediate future. So it's possible to get an idea of which sensors are near a which, by looking at the interval shortly after a switch is turned on.

@maybe talk about that is is less likely that a user will turn on a switch on, and then not enter that room

When flicking a switch off, the user may be leaving the room, or just have entered the room to turn the switch off. Each of the two cases are just as likely as the other, but the sensor events in the interval leaving up to the off event is completely opposite. 

@you could possebly look at the interval after it's turned off, and say there are less likely to be in the room, and then try to reduce the correlation for those sensors (NYI)

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
[Correlation table]


| Switches             || Sensors                                                                                       ||||||||||
|  	 |                  | 20      | 21       | 22   | 23       | 24       | 25       | 26       | 27       | 28       | 29       |
|                      || Hallway           || Living room              ||| Kitchen            || Bedroom            || WC       |
|---:|:-----------------|:-------:|:--------:|:----:|:--------:|:--------:|:--------:|:--------:|:--------:|:--------:|:--------:|
| 4  |	Hallway         | **0.4** | **0.67** | 0    | 0.2      | 0.13     | 0.07     | 0        | 0        | 0.07     | 0        |
| 13 |	Living room     | *0.38*  | 0.19     | 0.12 | **0.47** | **0.42** | 0.04     | 0.04     | 0.08     | 0.08     | 0        |
| 17 |	Kitchen         | *0.22*  | *0.28*   | 0    | 0.03     | 0.17     | **0.42** | **0.61** | 0.14     | 0.03     | 0.03     |
| 18 |	Bedroom         | 0.1     | 0.13     | 0    | 0        | 0.03     | 0.03     | 0        | **0.57** | **0.63** | 0.03     |
| 19 |	WC              | *0.31*  | *0.28*   | 0.06	| 0.09     | 0.08     | 0.06     | 0.01     | 0.07     | 0.05     | **0.75** |
[Correlation table, based on statistical data. > 40% in bold, 40-20% in italic.][Correlation table] 

 As can be seen in [Correlation table][QQQ], most (but not all) the correlation probability between sensors and switches in the same room is above 40%. As to be expected in an appartment with rooms connected through a single hallway, the hallway sensors tend to some correlation to all switches (20-40% in this case), but the hallways sensors have lower correlation in the other rooms, than the sensors in those room.

