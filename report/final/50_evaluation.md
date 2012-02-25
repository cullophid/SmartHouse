
## Evaluation

_"I have not failed. I've just found 10,000 ways that won't work."_ -- Thomas A. Edison

_"Laughing at our mistakes can lengthen our own life. Laughing at someone else's can shorten it."_ -- Cullen Hightower

_"To err is human--and to blame it on a computer is even more so."_ -- Robert Orben

* Evaluation should document that the goals have been achieved
	* Functional requirements (i.e., testing)
	* Non-functional requirements (e.g., performance)
* Definition of the evaluation strategy
	* Qualitative-/quantitative evaluation
	* Software testing
		* white-box/black-box
		* testing levels
			*unit testing, integrationg testing, system testing, acceptance testing
* summarised output from the evaluation
	*output should be explained
	* provisional conclusions should be presented
	

![XKCD Correlation][correlation]

[correlation]:img/correlation.png "Correlation"


### Correlation

Before evaluating the correlation probability table, some goals should be established.

* A sensor should have the highest correlation to the switch in the room it's in.
* Ideally some threshold exists, so that all correlations above the threshold are in the same room, and all other correlations are below the threshold.

| Switches             || Sensors                                                                                       ||||||||||
|  	 |                  | 20      | 21       | 22   | 23       | 24       | 25       | 26       | 27       | 28       | 29       |
|                      || Hallway           || Living room              ||| Kitchen            || Bedroom            || WC       |
|---:|:-----------------|:-------:|:--------:|:----:|:--------:|:--------:|:--------:|:--------:|:--------:|:--------:|:--------:|
| 4  |	Hallway         | **0.4** | **0.67** | 0    | 0.2      | 0.13     | 0.07     | 0        | 0        | 0.07     | 0        |
| 13 |	Living room     | *0.35*  | *0.23*   | 0.12 | *0.27*   | **0.42** | 0.04     | 0.04     | 0.08     | 0.08     | 0        |
| 17 |	Kitchen         | *0.22*  | *0.28*   | 0    | 0.03     | 0.17     | *0.39*   | **0.58** | 0.14     | 0.03     | 0.03     |
| 18 |	Bedroom         | 0.1     | 0.13     | 0    | 0        | 0.03     | 0.03     | 0        | **0.57** | **0.6**  | 0.03     |
| 19 |	WC              | *0.29*  | *0.29*   | 0.06	| 0.09     | 0.08     | 0.06     | 0        | 0.07     | 0.03     | **0.75** |
[Correlation table, based on statistical data. > 40% in bold, 40-20% in italic.][ctable data] 

The [correlation table][ctable data] is based on collected data from the testing environment. The first criteria holds, that all sensors have the highest correlation with the switch in the room they're in.
Most (but not all) the correlation probability between sensors and switches in the same room are above 40%. All correlations between for switches and sensors not in the same room are below 40%. Three sensors have correlations lower than 40% to the switch in the room they're in, and one of them as low as 12%. Two of the three sensors in the living room, not only have correlations below 40%, but correlations below those of sensors in the adjecent hallway. As can be seen in [the overview of the appartment](#Hellebaekgade), the sensors 22 and 25 are located in the far end of the rooms from the switch and doorway. Since the calculated correlation probabilities are based on the time interval right after the light is turned on, it makes sense that these sensor, relatively far away from the switches ends up with a lower correlation. 

[Hellebaekgade]: img/hellebaekgade3.png "Hellebaekgade image" width="60%"


Sensor 23 is a bit more interesting, since it located fairly close to the doorway. Having one of the authors of this thesis also being the guinea pig running around generating sensor data, gives a unique insight why some sensor patterns look the way they do. Sensor 24 is located by a desk, and 23 next to a sofa. So in this case different user activities triggeres different sensor, in this case sitting on the sofa and watch TV, or go the the desk and work. 

One thing to note is, these are the probabilities based solely on the statistical data, and that correlation corretions would be added onto this schema. So it doesn't perfectly reflect the room / switch + sensor correlations on it's own, though it gives a close approximation

