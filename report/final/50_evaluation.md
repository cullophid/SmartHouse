
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
	* output should be explained
	* provisional conclusions should be presented	

![XKCD Correlation][correlation]

[correlation]:figures/correlation.png "Correlation"


### Software testing

The implementation have been unit and integration tested. The simple modules Eve
<TODO bedre intro, evt. opdel som unit, integration og system testing>

#### Unit testing 

We have used JUnit tests to implementation of the relative simple classes Event, EventList, and KeyList. The testing files have been included in the appendix. 

#### Integration testing

The more complex classes DecisionMatrix and Correlation are tested using integration testing, since they are very tightly coupled to EventList and KeyList. The testing setup for the integration testing, are based a database of simulated data instead of the collected data, in order to verifiable outputs. The simulator setup consists of 6 sensors (1,2,3,7,8,9) and 3 switches (4,5,6). A simulated user takes various paths to generate a representative sample of event patterns.

| Test case | Description | Event sequence |
|:-----:|:------------|:--------------:|
| 1     | Path _A_, switch 4 on, sensor 9 | [1, 1&2, 2&3, 3, 4 on, 9] |
| 2     | Path _B_ then turns switch 5 off | [1&2, 5 off] |
| 3     | Path _B_ without using any switches | [1&2] |
| 4     | Path _C_, switch 6 on, sensor 7 | [2&3, 6 on, 7] |
| 5     | Path _C_ without using any switches | [2&3] |
| 6     | Path _C_, switch 6 on, sensor 8 | [2&3, 6 on, 8] | 
[Event patterns used for black box testing][bbox data]

Based on these simple event pattens, an expected output can be determined for both the DecisionMatrix and Correlation. The expected output for the DecisionMatrix is based on the number of times each event pattern has been seen, and the number of times they have led to a switch event. 

| Description | Sensor pattern | Switch | State | Probability |
|:-----------:|:--------------:|:------:|:-----:|:-----------:|
| without zone events |||||
| Path _A_ | [1, 1, 2, 2, 3] | 4 | on | 1 |
| Path _B_ | [1, 2] | 5 | off | 0.5 |
| Path _C_ | [2, 3] | 6 | on | 0.67 |
| with zone events |||||
| Path _A_ | [1, 1&2, 2&3] | 4 | on | 1 |
| Path _B_ | [1, 2] | 5 | off | 0.5 |
| Path _C_ | [2, 3] | 6 | on | 0.67 |
[DecisionMatrix's expected output][matrix expected]

Testing of the DecisionMatrix releaved what at first looked like an error. The probability for Path _C_ without zones had a probability of 100%, but with zones had the expected probability of 67%. Investigation revealed the cause was test case 5, where sensor 2 and 3 was triggered in the opposite order as test case 4 and 6. So while this error at first glace looked like a bug, is actually a feature, and one of the very reasons zone events were implemented. All other probabilities in the DecisionMatrix was as expected.

For the correlation table, the output is determined only by test cases where switches are turned on (test case 1, 4 and 6). The expected output is:

| switches | sensors |||
|   |  7  |  8  | 9 |
|:-:|:---:|:---:|:-:|
| 4 |  0  |  0  | 1 |
| 6 | 0.5 | 0.5 | 0 |
[Correlation table's expected output][corr expected]

The correlation table produced the expected results.

![Overview of the simple setup used for black box testing the DecisionMatrix and Correlation][zonetest]

[zonetest]:figures/zone2.png "Simulated testing setup" 

### Evaluation based on passive learning data

This section is going to evaluate how much the system have been able to learn, based on the data collected from the passive learning stage. In total 45.628 sensor events and 346 switch events was recorded. This is a very high sensor event to switch event ration, slight above 130 sensor events per switch event.

Of the 346 switch events, 194 was On events and 152 was Off events. If all switch event in a continuous period was recorded, the discrepency between on and off events would be atmost the number of actual switches. This could be due to lost Z-Wave messages, users forgetting to pressing the placebo switches. The system isn't dependant on the correct ordering of switch events, i.e. that On events are eventually always followed by an Off event, and vice versa. 

The discrepency between On and Off events, are an indicator that data have been lost, the system should still be able to learn based on the user data. The system will obviously not be able to learn based on the lost switch events. Assuming only switch events are lost, the missing switch events will also impact the system by having an increased sensor to switch event ratio, lowering the probabilities in the decision matrix.

The Correlation table isn't based on the entire data set of sensor events, but merely the interval after each On event. Therefor the sensor to switch event ratio for the Correlation table, isn't necesarily affected by missing switch events. 

#### Decision matrix

In order to better evaluate the Decision Matrix, it have been run on the training several times, with different pattern lengths, with and without zone detection. The evaluation will look upon the advantages and disadvantages the different configurations, and evaluate what on how much the system is able to learn from the passive learning data.

| Settings                      || Unique observed patterns                     |||
| Pattern length | Zones enabled | Movement patterns | On patterns | Off patterns |
|:--------------:|:-------------:|:-----------------:|:-----------:|:------------:|
| 2              | No            | 111               | 90          | 78           |
| 2              | Yes           | 1.168             | 149         | 121          |
| 3              | No            | 910               | 142         | 116          |
| 3              | Yes           | 3.870             | 227         | 173          |
| 4              | No            | 3.614             | 169         | 121          |
| 7              | Yes           | 12.967            | 322         | 215          |
[Statistics about the Decision Matrix, using different configurations][dtable metadata]

With zones enabled, the system looks at the event patterns leading up to each switch event, with and without zone detection. Detecting up to two switch patterns for every switch event, in some configurations there are more total switch patterns detected than actual switch events. A complete dump of all patterns detected by the Decision Matrix for each configuration is included in the appendix.

With a 130 to 1 sensor to switch event ratio, the probabilities for each event pattern leading to a switch event is very low. This isn't necesarily a problem, it may just mean the probability threshold, for the system to be confident enough to manipulate switches, needs to be equally low. 

A lot of the On / Off patterns detected by the Decision Matrix have only been observed once. We're going to set the confindence threshold so that a pattern must have lead to an On or Off event atleast 5 times, and then analyse the correctness of the patterns observed 

With the expectancy that the probabilitites are going to be relatively low, for each switch pattern, the evaluation of Decision Matrix will look at plausability of the patterns detected, more than how high or low the probability should be. Does the detected patterns make sense from a user point of view. The expected result is to detect plausable user patterns, where users press switches as they're entering or leaving each room. The reverse of that expectency is the system shouldn't detect implausable patterns, where motion events lead to switch events in non-adjacent rooms.

| Pattern | Probability | Description |
|:-------:|:-----------:|:------------|
| 20 21 13 on | 0.57% | Moving in the hallway, and turning on the light in the Living room |
| 27 28 18 on | 0.75% | Moving in the bedroom, and turning on the light |
| 20 20 19 on | 2.38% | Moving in the hallway and turning on the light in the restroom |
| 20 21 19 on | 2.17% | |
| 21 20 19 on | 1.70% | |
| 21 25 17 on | 3.26% | Moving from the hallway into the kitchen and turning on the light |
| 20 25 17 on | 5.76% | |
| 20 20 19 off | 1.49% | Moving in the hallway turning off the light in the restroom |
| 21 20 19 off | 1.2% | |
| 20 21 19 off | 1.14% | |
[Decision matrix, patterns detected atleast 5 times, pattern length 2, without zone detection][decision2false]

With pattern length two, most of the patterns, above the confidence limit, only contain sensor event from a single room (from here on refered to as single room patterns). In some cases there are identical patterns for turning switch on and off; [20, 20],[20,21],[21,20] all both turn the switch to the rest room on and off. This is partially because the switch for the restroom is outside the restroom, so the light is turned on before the user opens the door and is detected by the motion sensor inside. With the probabilities being as low as they are, the system cannot meaningfully determine if the light should be turned on or off. If the system were to act based on these conflicting patterns for the, it would mostly likely turn the lights on and off constantly, without there being need for it. Since the conflicting pattern are for an adjacent room, where the door is likely closed, the user wouldn't necesarily be aware of it.

There are two pattern where sensor events are from different room (from here on refered to as multi room patterns): [20, 25 -> 17 on] and [21, 25 -> 17 on]. These two patterns occur when the user moves from the hallway and into the kitchen, and then turns on the light in the kitchen. These two multi room patterns, not only sound reasonable, but also have the highest probabilies of all the patterns above the confidence limit. 

With pattern length two, and zone detection enabled, no event patterns with zones (from here on referd to as zone patterns) are seen leading to switch events 5 times or more. So for pattern length two, adding zones detection doesn't give any patterns above the confidence limit, for our data set. While zone events can reduce the ambiguity and allow the system to learn faster, physical motion sensors tends to have a cooldown. Cooldown means it takes some time, after the sensor has detected motion, before it will detect motion again. A result of this is that zone events are less likely to be detected. Two sensors might overlap, but if time between the two sensors are triggered are longer than the zone detection interval. The cooldown then cause the two sensors to keep firing sensor events too far apart to be detected as zone events.  

| Pattern | Probability | Description |
|:-------:|:-----------:|:------------|
| 27 27 28 18 on | 1.86% | Moving in the bedroom, and turning on the light |
| 20 21 20 19 on | 2.35% | Moving in the hallway, and turning on the light in the restroom |
| 21 20 21 19 on | 2.03% | 
| 29 21 20 19 off | 10.2% | Moving from the restroom to the hallway, turning off the light in the restroom |
| 21 20 21 19 off | 2.36% | Moving in the hallway, turning off the light in the restroom |
[Decision matrix, patterns detected atleast 5 times, pattern length 3, without zone detection][decision3false]

When the pattern length is increased to three, fewer distinct switch patterns above the confidence limit are detected. Just as when the pattern length was two, the majority of the patterns are single room patterns. There is one multi room pattern: [29, 21, 10 -> 19 off] where the user leaves the restroom, enters the hallway and turns off the light to the restroom. Like the other two multi room patterns, this pattern sounds reasonable, and have a relatively high probility of just over 10%.

Again adding zone detection doesn't prododuce any zone patterns above the confidence threshold.

| Pattern | Probability | Description |
|:-------:|:-----------:|:------------|
| 28 27 21 20 19 on | 8.33% | Moving from the bedroom to the hallway, turning on the light in the restroom |
| 29 29 21 20 19 off | 11.11% | Moving from the restroom the the hallway, turning off the light in the restroom |
| -1 21 20 21 19 off | 9.38% | Moving in the hallway, turning off the light in the restroom |
[Decision matrix, patterns detected atleast 3 times, pattern length 4, without zone detection][decision4false]

Increasing the pattern length to 4, no patterns were above the confidence limit of 5, so these patterns have only been see 3 or more times. This matrix have an interesting multi room pattern [28, 27, 21, 20 -> 19 on], where the user moves from the bedroom to the hallway, and then turn on the light to the restroom. While a plausable pattern, it isn't a pattern that can be guaranteed to always happen. This is because the switch for the restroom is located outside the restroom, so users tend to activate the switch before being detected by the sensor on the other side of the door. 

The multi room patterns detected by the system, all seem like plausable behavoir, and these pattens have some of the highest probabilities, of the patterns seen atleast 5 times. Although three confirmed multi room patterns aren't a lot. With more learning data, more patterns would be above the confidence limit. The data suggests, that the plausable patterns that system should learn to act on stand out with high probabilities. So with more data, more plausable patterns should appear which stand out by having high probabilities. 

The Decision Matrix learned different patterns, when the pattern length was changed. With pattern length 2, the most distinct patterns above the confidence limit was detected. The number of confidently detected patterns decreased as the pattern length increased. This mean the system is able to learn faster with a lower pattern length. The patterns learned from pattern length 2 and 3 both had merrit, so while a lower pattern length cause the system to learn faster, longer patterns enables the system to better take into account where the user is comming from. For instance the pattern [29, 21, 20, 19 off] where the system turns off the light in the restroom, when the user leaves is too long to be detected with a pattern length of 2. 

#### Correlation

In this section we are going to evaluate how well correlation, based on the generated user data, matches to the actual setup. The system's ability to get accurate estimates of which sensors and switches are in the same room. We are also going to evaluate how well the correlation based timeout would work, with or without correlation corrections. Prior to looking at the actual data, we want to state some resoanable goals we want the system to achieve for the correlation probabilities:

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

The second criteria does not hold for all correlations. Most correlation probability for sensors and switches in the same room are above 40%. All correlations for switches and sensors not in the same room are below 40%. Although three sensors have correlations lower than 40% to the switch in the room they are in, and one of them as low as 12%. In the living room, two sensors not only have correlations below 40%, but correlations below those of sensors in the adjecent hallway. 

As can be seen in [the overview of the appartment](#Hellebaekgade), the sensors 22 and 25 are located in the far end of the rooms from the switch and doorway. The calculated correlations are based on the time interval after a switch is turned on, so it makes sense that sensors being relatively far away from the switches ends up with a lower correlation. 

[Hellebaekgade]: figures/hellebaekgade3.png "Hellebaekgade image"

Sensor 23 is positioned to monitor the sofa in front of the TV, and the data suggest that it only detect motion if the user goes to the sofa immediately after entering the room. So not all sensors neccesarily trigger in a room, depending on what the user decides to do in the room. 

So in this case, the correlation still gives an excelent estimate of which switches and sensors are in the same room, by looking switch each sensor has the highest correlation probability too. 

One thing to note is, these are the probabilities based solely on the statistical data, and that correlation corretions would be added onto this schema. So it is not a perfect reflect of which sensors are in the same room each switch, on it is own. But it does gives a good approximation.

#### Correlation based timeout

The implemented functionality of the correlation table, is to determine the timeout for each switch. How well is the correlation table able to keep the light on where it's needed. Different areas should have different timeouts, but most important is for the system to have long timeouts in areas where the user is likely to be still for extended periods of time, while still wanting the light to remain on. The most obvious area would be the sofa, where a user is likely to be for hours. Based on passive learning data, the system would have one of the lowest timeouts when the user is detected in the sofa, where it should be the highest. 

However with active learning the correlation correction comes into effect. Every time the system incorrectly turns off the light, and the user turns it on again, the system is punished and increases the correlation, and by extension the timeout. As a result of this, the system will gradually increase the timeout until it no longer turns off the light, while the user is watching TV.


