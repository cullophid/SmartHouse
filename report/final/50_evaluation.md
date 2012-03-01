
## Evaluation

_"If it compiles, it is good; if it boots up, it is perfect."_ -- Linus Torvalds

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

[correlation]:figures/correlation.png "Correlation"


### Software testing

For the relative simple and predictable module EventList, we have done some white-box testing, using JUnit tests. Making sure it correctly detect zones,that pattern ordering is preserved, and old event are purged when new events are added according to the pattern length and interval parameters. The more complex modules DecisionMatrix and Correlation are tested and evaluated based on the gathered user data.

### Decision matrix

A bit of sensor meta data:

45.628 sensor events
346 switch events, 194 on and 152 off. The observant reader might wonder why there are a lot more off commands than on commands? The short answer is the user's may have forgotten to press the placebo switches from time to time.


<TODO 111 also being the theoretical maximum of unique patterns, for a home with 10 sensors
1  : no sensors, just switch
10 : single sensor events then switch event
100 (10*10) : two sensor events then switch event
I'm slightly impressed>

| Settings                      || Unique observed patterns                     |||
| Pattern length | Zones enabled | Movement patterns | On patterns | Off patterns |
|:--------------:|:-------------:|:-----------------:|:-----------:|:------------:|
| 2              | No            | 111               | 89          | 72           |
| 2              | Yes           | 928               | 147         | 120          |
| 3              | No            | 910               | 142         | 117          |
| 3              | Yes           | 4097              | 225         | 171          |
[Statistics about the Decision matrix][dtable metadata]

With zones enabled, the system looks at the event patterns both with and without zone. Which is why there are more observed on patterns, than actual on commands, when the pattern length is longer than three and zone detection is on. 

<TODO vi skal nok lige snakke lidt om hvad vi kan og vil konkludere baseret paa decision matrix>

### Correlation

In this section we are going to evaluate how well correlation, based on the generated user data, matches to the actual setup. Is the system able to get accurate estimates of which sensors and switches are in the same room. We are also going to evaluate how well the correlation based timeout would work, with or without correlation corrections. Prior to looking at the actual data, we want to state some resoanable goals we want the system to achieve for the correlation probabilities:

1. A sensor should have the highest correlation to the switch in the room it is in.
2. Some correlation threshold should exist, so that sensors and switches in the same room are above the threshold, and those not in the same room are below the threshold.

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

The [correlation table][ctable data] is based on collected data from the testing environment. The first criteria holds, that all sensors have the highest correlation with the switch in the room they are in. 
The send criteria does not hold for all correlations. Most correlation probability for sensors and switches in the same room are above 40%. All correlations for switches and sensors not in the same room are below 40%. But three sensors have correlations lower than 40% to the switch in the room they are in, and one of them as low as 12%. In the living room, two sensors not only have correlations below 40%, but correlations below those of sensors in the adjecent hallway. 

As can be seen in [the overview of the appartment](#Hellebaekgade), the sensors 22 and 25 are located in the far end of the rooms from the switch and doorway. Since the calculated correlation probabilities are based on the time interval just after the light is turned on, it makes sense that these sensor, being relatively far away from the switches ends up with a lower correlation. 

[Hellebaekgade]: figures/hellebaekgade3.png "Hellebaekgade image"

Sensor 23 is positioned to monitor the sofa in front of the TV, and the data suggest that it only detect the user if he go to the sofa immediately after he enters the room. So not all sensors neccesarily trigger in a room, depending on what the user decides to do in the room. 

So in this case, the correlation still gives an excelent estimate of which switches and sensors are in the same room, by looking switch each sensor has the highest correlation probability too. 

One thing to note is, these are the probabilities based solely on the statistical data, and that correlation corretions would be added onto this schema. So it is not a perfect reflect of which sensors are in the same room each switch, on it is own. But it does gives a good approximation.

#### Correlation based timeout

The implemented functionality of the correlation table, is to determine the timeout for each switch. How well is the correlation table able to keep the light on where it's needed. Different areas should have different timeouts, but most important is for the system to have long timeouts in areas where the user is likely to be still for extended periods of time, while still wanting the light to remain on. The most obvious area would be the sofa, where a user is likely to be for hours. Based on the statistical correlation alone, the system would have one of the lowest timeouts when the user is detected in the sofa, where it should be the highest. However in the evolution stage the correlation correction comes into effect. Every time the system incorrectly turns off the light, and the user turns it on again, the system is punished and increases the correlation, and by extension the timeout. As a result of this, the system will gradually increase the timeout until it no longer turns off the light, while the user is watching TV.


