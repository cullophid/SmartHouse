
## Analysis

* present the word smarthouse
* insert minisurvay.. (very superficial)

* Identify the major problems that must be addressed by the project
	* Analyze project goals in the given context
	* Decompose the main problems into sub-problems
		* Propose possible solutions to sub-problems
		* compare and contrast proposed solutions
		* select "best" solutions to identified sub-problems
			* "Best" could be "fastest", "cheapest", "simplest", ...
* requirements specification is one result of this analysis
* analysis should be general
	* Should apply regardless of whether implementation is in hardware/software and regardless of programming language

### observations and analysis of behavioral patterns
what assumptions can we make about the users, related to their behavioral patterns

With KIIIB we want to create an intelligent home control system, that requires minimum configuration, and installation, while providing optimal power saving, and comfort. 
Intelligent home control systems, have been around for several years **insert data**, but have not yet made the breakthrough on the market that they were anticipated to. One of the main reason for this is that installing such a system is rather costly, and requires installation and configuration, which is rarely trivial.
Some of the more advanced systems on the market, incorporates motion sensors, and timers in order to automatically turn on and off lights or various appliances **insert reference**.These systems will help the consumer save money over time, but they require  extensive configuration, or programming in order to function properly.
Of all the before mentioned qualities we want for our home control system, power saving is the most important. **insert data on power consumption of average household** This is an area where most modern home control systems falls short. Most systems are capable of providing only a modest reduction in power consumption, and some even increase the net consumption, by adding the cost of running the control system. We want our system to differ from others on this specific aspect. In our system, reducing power consumption is the number one priority.
We will accomplish this by creating a system, that focuses on turning off all lights and appliances where they are not needed. There are several advantages to this approach, compared to attempting to reduce the power consumption of active appliances. The main advantage is that it provides the largest reduction in power consumption. Most people remember to turn off the light in the bathroom, when they leave it, but this is far less common for the kitchen, or dining room, and only the most environmentally conscious people would ever turn off the light in the living room when they got to the bath room. This means that there is a lot of wasted energy in the normal household, and thats where we want to focus our attention. An other advantage of this is that it incorporates perfectly with all other power reducing technologies. Buying appliances that use less energy will still give you the same percentage of power reduction as in a normal house. This system will also eliminate the common problem of standby mode, on many appliances such as tv's or stereos, by having the appliance only  in standby mode, when the user is likely to turn it on. The rest of the time, the appliance is simply turned off.

Our approach to creating a house that is capable of predicting what the users want it to do, is to learn from what the user does, and then mimic these actions, at the right times. in order to accomplice that the system must do three things:

*  The system must gather data on the users, and their behavior in the house
* The system must analyze the data in order to build an action scheme on which it will base its actions
* The system must actively monitor the users, and act according to the action scheme.

As we discuss each of these three tasks we will also highlight some of the constraints we have put on the system. Some of these limitations are born out of personal interest, and some are there to limit the scope of the project. One example of this is that we have chosen only to focus on controlling the lighting, and are disregarding any appliances, or house features, such as heating, ventilation, and hot water. These are all areas where the system will be equally beneficial as with lighting, but modifying the system to incorporate these elements, would not require any major changes to the design of the core system, but would require a lot of rather trivial, though time consuming implementation work.

### Gathering data on the users
In order for the system to learn how to mimic the users actions, it must first gather information on how the user interacts with the house.

We have chosen to create some restrictions when it comes to interaction with the user. From the start we decided that we do not want a system that requires cameras to be put up in private homes. Visual analysis could provide more detailed information about the users, and their behavior, but we find this to be an unnecessary invasion of privacy. Even if the video data is not stored in the system, the presence of cameras will give many people the feeling of being watched in their own homes. The second restriction that we want to impose is to use as little hardware as possible. There is two reasons for this:

	1.  We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
	2. Creating a system that analyses, and mimics user behavior will no matter how it is implemented have a lot of unknown variables, that are hard to predict. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries, and then add components as the need for them arises.

The system should not require any direct interaction from the users. There has been a lot of change in software development the last few years, and on of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user[^need-ref]. **elaborate on development in user interfaces**
3. The system should not require any direct interaction from the users.
	There has been a lot of change in software development the last few years, and one of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user [^need-ref]. **elaborate on development in user interfaces**
[^need-ref]: Reference needed!

### Testing data

In order to test and evaluate the implementation, we need some data to train the system. Ideally the implementation would complete early enough to not only collect data from the different stages of the system: untrained, learning and eventually stable stage. The untrained stage where the system is running, but it haven't yet collected enough data to make intelligent decisions. The learning stage where there's enough data to attempt to manipulate the switches of the home, which may or may not be correct, and the system will attempt to learn from it's successes or failures. And after the system has been in the learning stage, it should stabalize (assuming the users doesn't change their routines). Since the system should take over the control of the switches, the lack of new user input could potentially lead to unlearning correct behavior, returning to the learning stage.

If the system isn't ready in time for data to be collected for the learning or stable stage, learning data for the untrained stage is the most crucial. Data from the untrained stage allows us to evaluate how much the system can learn, without the user interaction with the system. With the data from the untrained stage alone, the system would still be able to determine the movement patterns of the users, and have an idea of when to turn the switches on and off. It would also have a good idea of the which sensors and switches are in the are near each other or in the same room.



### Analyzing the collected data
Now that we have a lot of data on our users interactions with the house, we need to analyze the data in order for our systems AI to act on the collected data, or to be more specific: We need to create an action scheme, that the AI can use as a base for its decision making.


