
## Analysis

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

### Smart House Survey

"If I have seen further it is by standing on the shoulders of giants" -- Isaac Newton

The beginning of a good project, starts with a good survey of what already exists on in the field. What smart house solusions already exists, and what are their capabilities? What are the industry standarts, if any? This section won't be a exhaustive survey of all smart house solutions, but provide a representative selection of smart house solusions.

First we're going to establish some classificasions of smart houses, to better compare the compare the different systems. All systems can contain switches, sensors and remote controlls, the difference is the functionally they provide, and how they operate.
We distinguish between three types of systems:

**Controllable houses**
These are the simplest of the smart house solusions. Input devices like switches, remotes and sensors, can be setup to control output devices like appliance and dimmer switches, HVAC (Heating, Ventilation and Air Conditioning), etc. These solusion may also include macroes, e.g. where a single button may turn off all the lights in the home. 

**Programmable houses**
These solusions incorporate some degree of logical operations. They may be able to have scheduled task, e.g. turning down the thermostats when the users are at work. The behavoir in these systems have to be programmed by the manufaturer or the users, and if the user's needs change may have to be reprogrammed.

**Intelligent houses**
In these solusions some form of AI is able to control the home. They are capable of autonomously controlling the home, and are able to learn from the user's behavoir. 

#### INSTEON

Is targeted as at private homes, and is a controllable home solusion. Nodes in the network can communicate using either RF signals or home's existing electrical wiring. A standard array of devices are supported: 

* Dimmers & switches 
* HVAC
* sprinklers
* motion sensors
* assorted bridge devices

INSTEON does support external application to be run on PC connected through a bridge devices to the network, so it's technicly possible to make the system programmable or even intelligent. But no commercial products providing these fetures currently exists.

#### Clipsal C-Bus

Clipsal is targeted at large scale home controlled, install in such prominent buildings as the Sydney Opera house, Wembly Stadium and many more. Nodes communicatate over it's own separate wired connection, using the C-bus protocol. Each node has it's own microprocessor, which allows for distributed intelligence. Each node can also be individually programmed, and communicate over the shared bus. This allows unconventional devices like motors for stadium roofs and many other devices to be part of the network. Clipsal's C-bus is a programmable solution, which exels at being a scalable and flexible system. It can also be installed in a private home, but requiring it's own seperate wiring to be installed through out a home, can be a disadvantage compared to other systems. 

#### LK IHC

LK IHC is a programmable solution, targeted at private homes. It can be installed with a wired network, or wireless communication. 

#### MIT House_n
Intelligent
#### A.H.R.I.
Intelligent
#### 


### observations and analysis of behavioral patterns
what assumptions can we make about the users, related to their behavioral patterns



The main element that separates KIIIB from other similar systems is that the system is autonomously attempting to mimic the users behavior.  In order to accomplish this the system must do three things.

*  The system must gather data on the users, and their behavior in the house
* The system must analyze the data in order to build an activation scheme on which it will base its actions
* The system must actively monitor the users, and act according to the activation scheme.

### Gathering data on the users
We have chosen to create some restrictions when it comes to interaction with the user.
1. We do not want a system that requires cameras to be put up in private homes
	Visual analysis could provide more detailed information about the users, and their behavior, but we find this to be an unnecessary invasion of privacy. Even if the video data is not stored in the system, the presence of cameras will give many people the feeling of being watched in their own homes.
2. We want to use as little hardware as possible.
	There is two reasons for this:
	1.  We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
	2. Creating a system that analyses, and mimics user behavior will no matter how it is implemented have a lot of unknown variables, that are hard to predict. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries, and then add components as the need for them arises.
3. The system should not require any direct interaction from the users.
	There has been a lot of change in software development the last few years, and one of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user [^need-ref]. **elaborate on development in user interfaces**
[^need-ref]: Reference needed!

### Testing data

In order to test and evaluate the implementation, we need some data to train the system. Ideally the implementation would complete early enough to not only collect data from the different stages of the system: untrained, learning and eventually stable stage. The untrained stage where the system is running, but it haven't yet collected enough data to make intelligent decisions. The learning stage where there's enough data to attempt to manipulate the switches of the home, which may or may not be correct, and the system will attempt to learn from it's successes or failures. And after the system has been in the learning stage, it should stabalize (assuming the users doesn't change their routines). Since the system should take over the control of the switches, the lack of new user input could potentially lead to unlearning correct behavior, returning to the learning stage.

If the system isn't ready in time for data to be collected for the learning or stable stage, learning data for the untrained stage is the most crucial. Data from the untrained stage allows us to evaluate how much the system can learn, without the user interaction with the system. With the data from the untrained stage alone, the system would still be able to determine the movement patterns of the users, and have an idea of when to turn the switches on and off. It would also have a good idea of the which sensors and switches are in the are near each other or in the same room.


