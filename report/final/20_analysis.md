
## Analysis
<Mark weiser>
<smart environments>

### Smart House Survey

_"If I have seen further it is by standing on the shoulders of giants"_ -- Isaac Newton

The beginning of any good project starts with a survey of what already exists. 

In the following section we present a short survey of what already exists in the field of smart environments. We evaluate the existing home control solutions and their capabilities and review the industry standards. This section is intended as a representative selection of smart environments and thus will not contain an exhaustive survey of all existing solutions on the market.

First we will establish some basic classifications of smart houses, to better compare the different systems. All systems can contain switches, sensors and remote controls, the difference is the functionally they provide, and how they operate.

We classify the smart environments into three categories, Controllable, Programmable and Intelligent. These categories are based on the taxonomy presented in Boguslaw Pilich's Master Thesis and we refer interested readers to [#Boguslaw]

All three categories of smart environments can contain switches, sensors and remote controls, thus the differences between the systems is what functionality they provide and how they operate.

#### Controllable houses

These are the simplest of the smart house solutions. Input devices like switches, remotes and sensors, can be setup to control output devices like appliance and dimmer switches, HVAC (Heating, Ventilation and Air Conditioning), etc. These solution may also include macros, e.g. where a single button may turn off all the lights in the home. 

#### Programmable houses
These solutions incorporate some degree of logical operations, like having motion sensors only turn on the lights, if lux[^luxsensor] sensors are below above a certain threshold. They may be able to have scheduled, tasks e.g adjusting the thermostats during standard work-hours. The behavior of these systems have to be programmed by the manufacturer or the users. Consequently, changes in user needs require the system to be reprogrammed.

#### Intelligent houses
In these solutions some form of artificial intelligence is able to control the home. In computer science the term artificial intelligence is used very loosely. I our case we will define an intelligent house, as a system that is capable of machine learning. That means that the system is capable of evolving behavioral patterns based on empirical data[#wikipedia-machine-learning]. Consequently, the system will over time adapt itself to changes in user needs.

The solutions presented, are some of the most widespread smart house solutions, and represents the three different types of systems: Controllable, Programmable and Intelligent houses.

**INSTEON**

INSTEON is a controllable home control system, targeted at private homes. Nodes in the network can communicate using either RF signals or home's existing electrical wiring. A standard array of devices are supported: 

* Dimmers & switches 
* HVAC
* sprinklers
* motion sensors
* assorted bridge devices

INSTEON supports external application to be run on PC connected through a bridge devices to the network. By this logic it is technically possible to extend the system with a programmable or even intelligent component. However no commercial products providing these features currently exists. [#INSTEON]

INSTEON's solution is fairly widespread in the US. It represents what a commercial controllable smart house is capable of. It's functionaly very simplistic, but being able to communicate using the home electrical wiring, makes it a very non-intrusive system to install in an existing home. It enables the user more control of his home than just normal wall switches, being able to control his home with a remote and motion sensors. But in the end there is no intelligence in the INSTEON system, it can only do simple actions based on user inputs. 

**Clipsal (C-Bus)** 

Clipsal is targeted at large scale home control. The system is install in such prominent buildings as the Sydney Opera house, Wembly Stadium and many more. Nodes communicate over its own separate wired network, using the C-Bus protocol. Each node has its own microprocessor, allowing for distributed intelligence. This means each node can be individually programmed, allowing any device to be added to a Clipsal system. This allows unconventional devices like motors for stadium roofs and many other devices to be part of the network. Nodes can also be programmed to autonomously control the system, e.g. in a hotel a control unit in each appartment could monitor temperatur sensors, control ventilation and heating, while also logging power 

Clipsal represents the flexibility and scalability programmable solutions on the market are able to achieve. A very unique feature of Clipsal is the distrubuted intelligence<intelligence?>. Most programmable systems have central intelligence<again?>, where every other node in the system are slave nodes. With the microprocessors in each node, intelligence<Thats 3 strikes buddy!> can be distrubuted over a multitude of nodes, allowing nodes to be in charge of subsections of the system. The distributed intelligence<now youre just mocking me> can remove single point of failure, by removing the need for a central intelligence, make the system much fault tolerant.<du mener det rigtige, men du skal først forklare hvad single point of failure er>

But all of the features of the Clipsal system comes at a price. The system requires a wired communication network, and programming nodes to individual needs requires professional expertise. This is negligeble price to pay for a buisness, for the features it provides, but makes the system very expensive for a private user. [#CBus]

**LK IHC**

LK IHC is targeted at private homes. It can be installed with a wired network, or using wireless communication. This solution tends to be build around simple wall switches, but with programmable scenarios. An example of this could be having a switch near the front door and the master bedroom that turns off all lights. The IHC is a modular system, where modules like wireless communication or alarms, can be added to the base installation. <diskuter modular>

The IHC modules includes a programmable logic controller[^plc] which allows the system to be programmed. An example of this taken from their own presentation of the product is that motion sensors that normally are set to control the lights could, if the alarm is activated, be programmed to dial 911. 
LK IHC was per 2008 installed in nearly 30% of newly constructed building in denmark.[#MSsurvey]  [#LK IHC].

While the programmable logic controller provides an extended list of possibilities, programming the PLC requires a great deal of technical expertise. 

**MIT House_n**

House_n differs from the previous systems, as it is not a finished implementation, but a framework for a research projects. There are not any widespread commercially available intelligent smart house solution on the market, or at least that satisfies our classification of intelligent. 

House_n represent one of many smart environment, build by universities around the world. The smart environments are homes for one or more inhabitants, and are part of a living laboratory. The living lab part of House_n is called PlaceLab, and is a one-bedroom condominium, inhabited by volunteers for varying lengths of time. These homes are designed for multi-disciplinary studies, of people and their pattens and interactions with new technology and smart home environments. Being university run smart homes, the work coming out of these facilities tends to be proof of concepts. This means there are no complete product based on these projects. [#MIT House_n]

Like the Clipsal system, the nodes of House_n have distributed intelligence, and uses Hidden Markov Models to learn from user behavior. The system is also able to relay data gathered by the system, to PDA's or smartphones carried by the inhabitants of the house. 
The intelligence of House_n comes from the work of each team of master students working on the project. Each project explores different aspects of machine learning. The exact intellion gence implementation of House_n is dependant on the currently ongoing projects [#House_n ongoing]
[#House_n ongoing]: http://architecture.mit.edu/house_n/projects.html

The projects shown in this survey represent the solutions currently available or in development. There are many different controllable and programmable solution commercially available, with INSTEON, Clipsal C-bus and LK IHC being some of the more widespread representative solutions. INSTEON being a simple controllable solution, Clipsal C-bus and LK IHK are both programmable smart house solutions, but where LK IHC is designed for private homes, the Clipsal C-bus system is better suited for larger buildings. 

MIT's House_n in this survey represent that truly intelligent smart houses only exists in demonstration environments and as proofs of concept, and are not yet available on the commercial market.

<small conclusion on survey>
One of the main problems with current home control solutions is that installing such a system is rather costly and requires installation and configuration, which is rarely trivial. Some of the more advanced systems on the market, such as the LK IHC, incorporate motion sensors and timers that automatically turn on and off lights or various appliances. These systems will save money over time, but they require extensive configuration or programming in order to function properly.
<mere konklusion, tilfÃ¸j elementer til de enkelte huses konklusionser der kan refereres her>


### BIIIB 

Of all the  qualities mentioned in our vision for the system,  power saving is the most important. As seen in the survey above, this is an area where most modern home control systems falls short . Most systems are capable of providing only a modest reduction in power consumption, and some even increase the net consumption by adding the cost of running the control system. We want our system to differ from others on this specific aspect. In our system, reducing power consumption is the number one priority.

We want the users interactions with the system, to be as simple and familiar as possible. The user should only interact with the system through the wall mounted switches that are already present in all normal houses. 

We will accomplish this by creating a system that focuses on turning off all lights and appliances where they are not needed. There are several advantages to this approach, compared to attempting to reduce the power consumption of active appliances. The main advantage is that it provides the largest reduction in power consumption. Most people remember to turn off the light in the bathroom, when they leave it, but this is far less common for the kitchen, or dining room, and only the most environmentally conscious people would ever turn off the light in the living room when they got to the bathroom. This means that there is a lot of wasted energy in the normal household . 

Though we focus on controlling the lights in the house, the system must also be scalable so that in can incorporate other aspects, such as heating, ventilation, and electrical appliances. 

An other advantage  is that it incorporates perfectly with all other power reducing technologies. Buying appliances that use less energy will still give you the same percentage of power reduction as in a normal house. 

This system will also eliminate the common problem of standby mode on many appliances such as TVs or stereos by having the appliance only  in standby mode, when the user is likely to turn it on. The rest of the time the appliance is simply turned off.

Our approach to creating an intelligent house that is capable of predicting what the users want it to do, is to learn from what the user does and  mimic these actions at the right times. To accomplice this, the system must do three things:

*  The system must gather data on the users and their behavior in the house
* The system must analyze the data in order to build a decision scheme on which it will base its actions
* The system must be able control the house in real time, based on the decision scheme.

### Gathering data on the users
To mimic user actions, the system  must first gather information on how the user interacts with the house. Therefore the first question we must answer is: What data should we collect on our users?  In order for the system to effectively take over the users direct interactions with the house, we need to know two things. 

* What action needs to be done?
* When shall the action be done?

The first question can be answered by monitoring the users direct interactions with the house. Since we have limited our system to handle lighting, this means monitoring the users interactions with the light switches.
 
The second question is a lot more complex. We need to collect data that can help us determine if the conditions are right for performing a specific action. We could of cause quite literally look at the time the action is performed, and then use that as a trigger, but this requires that users follow a very specific schedule.

To get a more detailed picture of when an action is done, we must analyze it relative to what the user is doing at the time. Since we're focussing on lighting this can be done simply by tracking the users movements. Thereby we will determine when an action shall be done based on where the user is, and where he is heading.

Perhaps the most obvious way of accomplishing this is by using cctv cameras. Using visual analysis is the most effective way of monitoring the user, as it will provide us with vast amounts of data on what the user is doing. By for example installing a fisheye camera in every room and use motion tracking on the video data stream, we can determine exactly where the user is, and what he is doing. While this is probably the solution that provides us with the most precise and detailed data, it does have one problem. Installing cameras in every room of the users house is, in out opinion, an unnecessary  invasion of the users privacy. Even if the video data is not stored in the system, the presence of cameras will give many people the feeling of being watched in their own homes. 

An other approach would be to use a beacon worn by the user that sends out a digital signal. The system could then use multilateration[^multilateration] to pinpoint the exact location of the user. The beacon could be attached to the users keychain, incorporated into his cellphone, or, our personal science fiction favorite, injected under his skin. Like the camera approach this solution also has very high  precision in tracking the user through the house. However,besides the point that the user might not always carry his keys or cellphone around, the main issue with this solution is scalability of users. Even though we limit the system to one user for now , we want a system that can be scaled to accommodate multiple users acting both 	 and autonomously. <make sure to explain that the sensor house works with several users>Having to attach a beacon to every visitor coming into the house is gonna be an annoyance, and without it the house would not react to the visitor at all. 

The solution we chose is to use motion sensors. While this solution does not provide nearly the same precision in determining the users location as using fish eye cameras or multilateration, motion sensors does come with a range of other advantages. Motion sensor is a very cheap solution, compared to installing cctv cameras, and will be far less invasive on the user's privacy. The motion sensor solution will also work for any user in the house, and does not require the user to carry any beacon device like in the multilateration system.  <how do we handle unreliable motion sensors>

The system could easily be expanded by several other types of sensors as well. E.g. pressure sensors in the furniture, so the system can determine if there is someone present, even when  motion sensors do not register them. There are several other examples of sensor technologies that could be incorporated in the system. Some  will be discussed in the section 'Future work'. 

For the moment we want to use as few hardware components as possible. There are two reasons for this:

* We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
* Creating a system that analyses and mimics user behavior will have a lot of unknown variables that are hard to predict no matter how it is implemented. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries and then add components as the need for them arises. 

Because we want a system that is easy to install and configure, we have chosen not to inquire any information on the position of the motion sensors in the house.
This means that the system does not know where each sensor is located, nor which other sensors are in the same room as it. This does make analyzing the data a lot more complicated, but we want to stick with the idea of minimizing the installation and configuration. This way the installation process can be boiled down to putting up the sensors, plugging in the system, and pressing "Start". This also simplifies the maintenance of the system, when for example the user needs to replace a faulty sensor. This is again subscribes to the idea that the system should be smart, so the user does not have be.
 
Choosing to only monitor the light switches and using motion sensors to track the user greatly simplifies the data collection. Both the motion sensors and the switches generate events when they are triggered, and the system should simply store these events in a database. 

An alternative to this is to have the system analyze the data live, which would eliminate the need to store the event data. With this approach we do not have to store the events in the system, which over time could amount to a considerable amount of data. The problem is that if we should choose to modify the algorithms that analyze the data, we would effectively loose everything the system has learned so far. By storing the raw event data we can always recalculate a new decision scheme based on the collected data. This solution leaves us with a lot more options later on. The collection of data must still happen in real time. Since it is very important that the events are recorded exactly when they happen, the system must not stall in this process.

Since the project serves as a proof of concept for the idea of an intelligent house, we will need to collect real user data in order to properly evaluate our system. This is a necessary step in order to draw any meaningful conclusions on the system. There are two reasons for this:

* If we use generated data the house is not actually intelligent, it is merely acting on data created by the developers. The data we could supply the house would be based on how we think the user would behave. As developers it would be almost impossible not to be bias towards a behavioral pattern that is easy for the house to interpret, rather than how an actual user would interact with the house.
* The project had a very large unknown element when we started out. No system quite like it have ever been created before, and it is almost impossible to predict how the system will react to different inputs. Though we are creatures of habit, our movement patterns do not run like clockworks. No matter how well we would generate training data using simulators, algorithms or any other artificial method, there would always be a doubt on how close to actual human behavior it actually is.

We do however not wish to create a fully functional physical installation, since this would take away too much focus developing the actual software system.

We chose to install a "placebo" system[^placebosystem] of wireless switches and sensors, to collect training data. This gives us the best quality training data for the system, without the expenses of installing operational wireless switches. With this training data, we can then use a simulator to evaluate that the system is learning properly. The data from the simulator is good enough to simulate simple movement patterns, to see which lights go on or off, as a simulated user moves from room to room. 


### Analyzing the collected data

_"If you torture data long enough, it will tell you what you want!"_ -Ronald Coase 
	
Now that we have a lot of data on our users interactions with the house, we need to analyze the data in order for our systems AI to act on the collected data. To be more specific: We need to create a decision scheme that the AI can use as a base for its decision making. 

This is the critical part of the system. Collecting data, and acting based on an existing scheme are bot relatively simple tasks, however, designing the scheme to act, based on collected data, is far more complicated.

The purpose of analyzing the data is to find which specific situations that require the system to perform an action. Since the system does not know which sensors are located near which switches, the system will have to learn these relations  based on the data collected. The simplest solution would be to have the system learn which switches and which sensors are located in the same room, and then create a "link" between them so the motion sensors control the light. This would result in what we have named the silvan[^silvan] system. 

The silvan system is basically having a motion sensor turn on the light when triggered, and then have a timer turn off the light if the sensor is not triggered for a set amount of time. The main problem with this kind of system is that if the user does not trigger a motion sensor regularly, the light will turn off when the user is still in the room. This is commonly a problem in a room like the living room, where the user will likely spend an extended amount of time sitting still. This problem can be addressed by extending the light's timeout time. 

However, this brings us to the second problem. If the user is merely passing by a sensor, the light will still be turned on for its full duration. This greatly reduces the effectiveness of the system from a power saving point of view. 

A better solution is to attempt to identify the users behavior leading up to a switch event[^switchevent]. Since the system only use motions sensors to track the users movements, these sensor events will form the basis for the data analysis. The system could simply look at what sensor was triggered right before a switch was activated, and the create a link between that sensor and the switch. This, however, would result in a system much like the silvan system described above. 

If we instead look at a series of sensor events leading up to a switch event, we will get a much more complex picture of what the user is doing. Since the switches in the house are located in fixed positions around the house, these movement patterns should repeat themselves relatively often. The movement patterns that lead up to a switch being turned off, will most likely also differ from a pattern leading up to a switch being turned on, since the user will be either entering or exiting a room. Once we have analyzed the data and identified the movement patterns related to a switch event, we need to create a decision scheme that the system can base its decision making on. That means we have to organize the analyzed data in a way so we easily can look up a specific pattern, and see whether it should trigger a switch action.
 
Unlike data collection, analyzing the data does not have  strict time constraints. Since the decision scheme will be based on data collected over an extended period of time, the system will not benefit from having the decision scheme updated in real time.  As a result the time constraints on analyzing the data will be quite loose, and should not pose as a restriction on the system. 

<the house should react to the user, and the user to the house>

### Controlling the house
After we have collected and analyzed data the the final task is to have the system control the house in real time, using the decision scheme created from the analyzed data. The system must constantly monitor the user and attempt to match his movement pattern to those present in the decision scheme. As with data collection this has to happen in real time so the patterns are not corrupted.


### Requirement specification

Based of the analysis above we can now form a requirement specification for the project.  The system shall collect data using motion sensors and by monitoring switches. This data should be stored as it is collected and without being manipulated. 
<add more stuff>

[^switchevent]: An event generated in the system, by the user turning a switch on or off. 
[^silvan]: Danish building material retail-chain. 

[#MIT House_n]: MIT House_n. http://architecture.mit.edu/house_n/placelab.html

[#Boguslaw]: Boguslaw Pilich. Engineering Smart Houses, DTU IMM MSc Thesis Nr. 49/2004

[#wikipedia-machine-learning]: Wikipedia article on machine learning. http://en.wikipedia.org/wiki/Machine_learning

[#INSTEON]: INSTEON. http://www.insteon.net

[#CBus]: Wipedia article on the Clipsal C-Bus protocol. http://en.wikipedia.org/wiki/C-Bus_(protocol)

[#LK IHC]: Lauritz Knudsens. http://www.lk.dk
 
[#MSsurvey]: Mads Ingwar and Soeren Kristian Jensen. IMM Smart House Project: a state of the art survey. 2008.

[^placebosystem]: A system where the sensors and switches have no actual effect on the house, but are merely there to collect data.

[^luxsensor]: A device for measuring the amount of light in a room. 

[^multilateration]: Multilateration is a navigation technique based on the measurement of the difference in distance to two or more stations at known locations that broadcast signals at known times
