
## Analysis

QUOTES ON DATA:

_If you torture data long enough, it will confess!_ -- Ronald Coase 
	
_Data is not information. Information is not knowledge. Knowledge is not understanding. Understanding is not wisdom._ -- unknown source
	
	

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

### Smart House Survey

_"If I have seen further it is by standing on the shoulders of giants"_ -- Isaac Newton

The beginning of a good project, starts with a good survey of what already exists on in the field. What smart house solutions already exists, and what are their capabilities? What are the industry standards, if any? This section won't be a exhaustive survey of all smart house solutions, but provide a representative selection of smart house solutions.

First we're going to establish some classifications of smart houses, to better compare the different systems. All systems can contain switches, sensors and remote controls, the difference is the functionally they provide, and how they operate.
We distinguish between three types of systems:

**Controllable houses**
These are the simplest of the smart house solutions. Input devices like switches, remotes and sensors, can be setup to control output devices like appliance and dimmer switches, HVAC (Heating, Ventilation and Air Conditioning), etc. These solution may also include macros, e.g. where a single button may turn off all the lights in the home. 

**Programmable houses**
These solutions incorporate some degree of logical operations. They may be able to have scheduled task, e.g. turning down the thermostats when the users are at work. The behavior in these systems have to be programmed by the manufacturer or the users, and if the user's needs change may have to be reprogrammed.

**Intelligent houses**
In these solutions some form of artificial intelligence or AI is able to control the home. In computer science the term AI is used very loosely. I our case we will define an intelligent house, as a system that is capable of machine learning. That means that the system is capable of evolving behavioral patterns based on empirical data[^wikipedia-machine-learning].


[^wikipedia-machine-learning]:(http://en.wikipedia.org/wiki/Machine_learning)

#### INSTEON

Is targeted as at private homes, and is a controllable home solution. Nodes in the network can communicate using either RF signals or home's existing electrical wiring. A standard array of devices are supported: 

* Dimmers & switches 
* HVAC
* sprinklers
* motion sensors
* assorted bridge devices

INSTEON does support external application to be run on PC connected through a bridge devices to the network, so it's technically possible to make the system programmable or even intelligent. But no commercial products providing these features currently exists.

[#INSTEON]: INSTEON homepage http://www.insteon.net

#### Clipsal C-Bus

Clipsal is targeted at large scale home controlled, install in such prominent buildings as the Sydney Opera house, Wembly Stadium and many more. Nodes communicate over it's own separate wired connection, using the C-bus protocol. Each node has it's own microprocessor, which allows for distributed intelligence. Each node can also be individually programmed, and communicate over the shared bus. This allows unconventional devices like motors for stadium roofs and many other devices to be part of the network. Clipsal's C-bus is a programmable solution, which exels at being a scalable and flexible system. It can also be installed in a private home, but requiring it's own seperate wiring to be installed through out a home, can be a disadvantage compared to other systems. [#CBus]

[#CBus]: Wipedia article on the Clipsal C-Bus protocol http://en.wikipedia.org/wiki/C-Bus_(protocol)

#### LK IHC

LK IHC is a programmable solution, targeted at private homes. It can be installed with a wired network, or wireless communication. This solusion tends to be build around simple switches, but with programmable scenarios, e.g. having a turn off all light button near the front door and the master bedroom. It is a modular system, where modules like wireless communication or alarms, can be added to the base installation. The plain vanilla implementation is a controllable system, the modules can provide programmable functionality to the system. LK IHC was per 2008 installed in nearly 30% of newly constructed building in denmark[#MSsurvey].[#LK IHC]

[#LK IHC]: Lauritz Knudsens http://www.lk.dk
 
[#MSsurvey]: Mads Ingwar and Soeren Kristian Jensen. IMM Smart House Project: a state of the art survey. 2008.

#### MIT House_n

The MIT House_n represent one of many smart environment, build by universities around the world. The smart environments are homes for one or more inhabitants, and are part of a living laboratory. The living lab part of House_n is called PlaceLab, and is a one-bedroom condominium, inhabited by volunteers for varying lengths of time. These homes are designed for multi-disciplinary studies, of people and their pattens and interactions with new technology and smart home environments. Being university run smart homes, the work comming out of these facilities tends to be proof of concepts. [#MIT House_n]

[#MIT House_n]: MIT House_n http://architecture.mit.edu/house_n/placelab.html

The projects highlighted in this survey represent the solutions currently available or in development. There are many different controllable and programmable solution commercially available, with INSTEON, Clipsal C-bus and LK IHC being some of the more widespread representative solutions. INSTEON being a simple controllable solution, Clipsal C-bus and LK IHK are both programmable smart house solutions, but where LK IHC is designed for private homes, the Clipsal C-bus system is better suited for larger buildings. 

MIT's House_n in this survey represent that truly intelligent smart houses only exists in demonstration environments and as proofs of concept, and are not yet widely available on the commercial market.

### System limitations, assumptions and project scope

Before we start to analyze what makes a good home control system, we start by outlining which  limitations we will impose on the system. Some of these limitations are born out of personal interest, and some are there to limit the scope of the project. In order to limit the scope of the project, we have chosen only to focus on controlling the lighting, and are disregarding any appliances, or house features, such as heating, ventilation, or hot water. These are all areas where the system will be equally beneficial as with lighting, but modifying the system to incorporate these elements, would not require any major changes to the design of the core system, but would require a lot of rather trivial, though time consuming implementation work. 
We assume that people who have home control systems don't have windows. At the very least we will work under the assumption that the lighting conditions outside a room does not affect the conditions inside. A room with the lights turned on is light, and a room with the lights turned off is dark. This is simply to eliminate outside factors such as varying lighting conditions. Normally if there is enough natural lighting in a room, the user will not want the artificial light turned on. 
The people in the house is trapped in "Groundhog Day"[#ramis:1993][], or maybe more precisely Groundhog hour. We will assume that the users behavior , and requirements, does not change depending on which day it is, nor the time of day.
Finally we will make the assumption that the user only needs light in rooms where he is present. In reality there might be situations where the user would want the light to stay on when he leaves the room, e.g. if he has a birdcage or a aquarium in the room.


### BIIIB 

VISION:

### user interaction
The system should not require any direct interaction from the users. There has been a lot of change in software development the last few years, and on of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user[^need-ref]. **elaborate on development in user interfaces**
3. The system should not require any direct interaction from the users.
	There has been a lot of change in software development the last few years, and one of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user [^need-ref]. **elaborate on development in user interfaces**
[^need-ref]: Reference needed!


The vision for our project is to create an intelligent home control system, that requires minimum configuration, and installation, while providing optimal power saving, and comfort. 
One of the main problems with current home control solutions is that installing such a system is rather costly, and requires installation and configuration, which is rarely trivial. Some of the more advanced systems on the market, such as e.g. the LK IHC incorporates motion sensors, and timers in order to automatically turn on and off lights or various appliances. These systems will help the consumer save money over time, but they require  extensive configuration, or programming in order to function properly.
Of all the before mentioned qualities mentioned in our vision for the system,  power saving is the most important. **insert data on power consumption of average household** This is an area where most modern home control systems falls short. Most systems are capable of providing only a modest reduction in power consumption, and some even increase the net consumption, by adding the cost of running the control system. We want our system to differ from others on this specific aspect. In our system, reducing power consumption is the number one priority.
We will accomplish this by creating a system, that focuses on turning off all lights and appliances where they are not needed. There are several advantages to this approach, compared to attempting to reduce the power consumption of active appliances. The main advantage is that it provides the largest reduction in power consumption. Most people remember to turn off the light in the bathroom, when they leave it, but this is far less common for the kitchen, or dining room, and only the most environmentally conscious people would ever turn off the light in the living room when they got to the bath room. This means that there is a lot of wasted energy in the normal household, and thats where we want to focus our attention. An other advantage of this is that it incorporates perfectly with all other power reducing technologies. Buying appliances that use less energy will still give you the same percentage of power reduction as in a normal house. This system will also eliminate the common problem of standby mode, on many appliances such as tv's or stereos, by having the appliance only  in standby mode, when the user is likely to turn it on. The rest of the time, the appliance is simply turned off.

Our approach to creating a house that is capable of predicting what the users want it to do, is to learn from what the user does, and then mimic these actions, at the right times. in order to accomplice that the system must do three things:

*  The system must gather data on the users, and their behavior in the house
* The system must analyze the data in order to build an action scheme on which it will base its actions
* The system must be able control the house in real time, based on the action scheme.

### Gathering data on the users
In order for the system to learn how to mimic the users actions, it must first gather information on how the user interacts with the house. The first question we must answer is: What data should we collect on our users?  In order for the system to effectively take over the users direct interactions with the house, we need to know two things. 

* What action needs to be done?
* When shall the action be done?

The first question can be answered by monitoring the users direct interactions with the house. Since we have limited our system to handle lighting, this means monitoring the users interactions with the light switches. 
The second question is a lot more complex. We need to collect data that can help us determine if the conditions are right for performing a specific action. We could of cause quite literally look at the time the action is performed, and then use that as a trigger, but that requires that the users follow a very specific schedule.
In order to get a more detailed picture of when an action is done, we must analyze it relative to what the user is doing at the time.  Since were focussing on lighting this can be done simply by tracking the users movements. Thereby we will determine when an action shall be done based on where the user is, and where he is heading.
Perhaps the most obvious way of accomplishing this is by using cctv cameras. Using visual analysis is the most effective way of monitoring the user, as it will provide us with vast amounts of data on what the user is doing. By for example installing a fisheye camera in every room, and using motion tracking on the video data stream, we can determine exactly  where the user is, and what he is doing. While this is probably the solution that provides us with the most precise and detailed data, it does have one problem. Installing cameras in every room of the users house, is, in out opinion, an unnecessary  invasion of the users privacy. Even if the video data is not stored in the system, the presence of cameras will give many people the feeling of being watched in their own homes. 
An other approach would be to use a beacon worn by the user that sends out a digital signal. The system could then use multilateration to pinpoint the exact location of the user. The bacon could be attached to the users keychain, incorporated into his cellphone, or our personal science fiction favorite injected under his skin. Like the camera approach this solution also has very high  precision in tracking the user through the house. However there is one issue that poses a big problem. Besides the point that the user might not always carry his keys or cellphone around, the main issue with this solution is scalability. By scalability we mean more specifically scalability of users. Even though we limited the system to one user for now, we want a system that can be scaled up to accommodate multiple users acting autonomously at the same time. Having to attach a beacon to every visitor coming into the house is gonna be an annoyance, and without it the house would not react to the visitor at all. 
The solution we chose is to use motion sensors. While this solution does not provide nearly the same precision in determining the users location as using fish eye cameras or multilateration, motion sensors does come with a range of other advantages. Motion sensor is a very cheap solution, compared to installing cctv cameras, and will be far less invasive on the users privacy. The motion sensor solution will also work for any user in the house, and does not require the user to carry any beacon device like in the multilateration system.  The system could easily be expanded by several other types of sensors as well. An example of this could be to have pressure sensors in the furniture, so the system can determine if there is someone present, even if the motion sensors do not register them. There is several other examples of sensor technologies that could be incorporated in the system, and some of these will be discussed in the section Future work. For the moment we want to use as few hardware components as possible. There is two reasons for this:

* We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
* Creating a system that analyses, and mimics user behavior will no matter how it is implemented have a lot of unknown variables, that are hard to predict. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries, and then add components as the need for them arises. 

We have chosen not to inquire any information on the position of the motion sensors in the house. This means that the system does not know where each sensor is positioned, nor which other sensors are in the same room as it. This does make analyzing the data a lot more complicated, but we wanted to stick with the idea or minimizing the installation and configuration. This way the installation process can be boiled down to putting up the sensors, plugging in the system, and pressing "Start". 

#### Storing the data
Choosing to only monitor the light switches, and use motion sensors to track the user greatly simplifies the data collection.  Both the motion sensors and the switches generate events when they are triggered, and the system should simply store these events in a database. 
An alternative to this is to have the system analyze the data live, which would eliminate the the need to store the event data. With this approach we do not have to store the events in the system, which over time could amount to a considerable amount of data. The problem is that if we should chose to modify the algorithms that analyze the data, we would effectively loose everything the system has learned so far. By storing the raw event data we can always recalculate a new action scheme based on the collected data. This solution leaves us with a lot more options later on. The collection of data must happen in real time. Since it is very important that the event are recorded exactly when they happen, the system must not stall in this process.


### Analyzing the collected data

_"If you torture data long enough, it will tell you what you want!"_ -- Ronald Coase 
	
Now that we have a lot of data on our users interactions with the house, we need to analyze the data in order for our systems AI to act on the collected data, or to be more specific: We need to create an action scheme, that the AI can use as a base for its decision making. The purpose of analyzing the data is to find which specific situations that requires the system to perform an action. Since the system does not know which sensors are located near which switches the system will have to learn these relations  based on the data collected. The simplest solution would simply be to have the system learn which switches and which sensors are located in the same room, and then create a "link" between these so that the motion sensors controls the light. This would result in what we have named the silvan[^silvan] system. The silvan system is simply having a motion sensor turn on the light when triggered, and then have a timer turn off the light if the sensor is not triggered for a set amount of time. The main problem with this kind of system is that if the user does not trigger a motion sensor regularly, the light will turn off when the user is still in the room. This is commonly a problem in a room like the living room where the user will likely spend an extended amount of time sitting still. This problem can be addressed by extending the lights timeout time, which brings us to the second problem. If the user is merely passing by a sensor, the light will still be turned on for its full duration. This greatly reduces the effectiveness of the system from a power saving point of view. A better solution is to attempt to identify the users behavior leading up to a switch event. Since we only use motions sensors to track the users movements, these sensor events will form the basis for our data analysis. We could simply look at what sensor was triggered right before a switch was activated, but this would result in a system much like the silvan system described above. If we instead look at a series of sensor events leading up to a switch event, we will get a much more complex picture of what the user is doing. Since the switches in the house is located in fixed positions around the house, these movement patterns should repeat them selves relatively often. The movement patterns that lead up to a switch being turned off, will most likely also differ from a pattern leading up to a switch being turned on, since the user will be either entering or exiting a room. Once we have analyzed the data and identified the movement patterns related to a switch event, we need to create an action scheme that the system can base its decision making on. That means we have to organize the analyzed data in a way so we can easily look up a specific pattern, an see whether it should trigger a switch action. Unlike the data collection analyzing the data does not have the same strict time constraints. Since the action scheme will be based on data collected over an extended period, the action scheme does not need to be updated too frequently. We have chosen to update the action scheme on a daily basis. This is a somewhat arbitrarily chosen time interval. We want the decisions of the system  to be backed by enough data, so that an hourly update will not be noticeable. At the same time we still want the system to be able to adjust it self to changes in the users behavior, which is problematic if the update only occurs once every month.

[^silvan]: Danish building material retail-chain. 

### Controlling the house
After we have collected the data and analyzed it the final task is to have the system control the house in real time using the action schemes created based on the analyzed data. The system must constantly monitor the user, and attempt to recognize  any of the patterns in the activation scheme.

### Testing data
**this must either be rewriteen to ba a conclusion on the analysis, or moved to an other section**
In order to test and evaluate the implementation, we need some data to train the system. Ideally the implementation would complete early enough to not only collect data from the different stages of the system: untrained, learning and eventually stable stage. The untrained stage where the system is running, but it haven't yet collected enough data to make intelligent decisions. The learning stage where there's enough data to attempt to manipulate the switches of the home, which may or may not be correct, and the system will attempt to learn from it's successes or failures. And after the system has been in the learning stage, it should stabilize (assuming the users doesn't change their routines). Since the system should take over the control of the switches, the lack of new user input could potentially lead to unlearning correct behavior, returning to the learning stage.

If the system isn't ready in time for data to be collected for the learning or stable stage, learning data for the untrained stage is the most crucial. Data from the untrained stage allows us to evaluate how much the system can learn, without the user interaction with the system. With the data from the untrained stage alone, the system would still be able to determine the movement patterns of the users, and have an idea of when to turn the switches on and off. It would also have a good idea of the which sensors and switches are in the are near each other or in the same room.


