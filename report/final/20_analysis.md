
## Analysis

The vision for our project is to create an intelligent home control system that requires minimum configuration and installation while providing optimal power saving, and comfort. 

<fortsæt fra vision, og introduktion>

### Smart House Survey

_"If I have seen further it is by standing on the shoulders of giants."_ -- Isaac Newton

The beginning of any good project starts with a survey of what already exists in the field. Which smart house solutions already exist, and what are their capabilities? What are the industry standards, if any? This section will provide a representative selection of smart house solutions. However, it will not be an exhaustive survey of all smart house solutions.

First we will establish some basic classifications of smart houses. This allows us to compare different systems. All systems contain switches, sensors and remote controls. The difference is in the functionally they provide, and how they operate.

We distinguish between three types of systems: 
<Hvor kommer denne opdelign fra? Er det jeres idé? Så skal der vel nogle eksempler bag? >

**Controllable houses**./:
These are the simplest of the smart house solutions. Input devices like switches, remotes and sensors, can be set up to control output devices like appliance and dimmer switches, HVAC (Heating, Ventilation and Air Conditioning), etc. These solution may also include macros, e.g., where a single button may turn off all the lights in the home. 

**Programmable houses**./:
These solutions incorporate some degree of logical operations. They may be able to have scheduled tasks, e.g., turning down the thermostats during standard work-hours. The behavior of these systems have to be programmed by the manufacturer or the users. Consequently, changes in user needs will require the system to be reprogrammed. 

**Intelligent houses**./:
In these solutions some form of artificial intelligence or AI is able to control the home. In computer science the term AI is used very loosely. I our case we will define an intelligent house, as a system that is capable of machine learning. That means that the system is capable of evolving behavioral patterns based on empirical data[^wikipedia-machine-learning]. 


[^wikipedia-machine-learning]:(http://en.wikipedia.org/wiki/Machine_learning)


#### INSTEON

Insteon is a controllable home system targetedat private homes. Nodes in the network can communicate using either RF signals or the home's existing electrical wiring. A standard array of devices are supported: 

* Dimmers & switches 
* HVAC
* sprinklers
* motion sensors
* assorted bridge devices

INSTEON supports external application to be run on a PC, connected through a bridge devices to the network. By this logic it is  technically possible to make the system programmable or even intelligent. However, no commercial products providing these features currently exists. 

[#INSTEON]: INSTEON homepage http://www.insteon.net

#### Clipsal C-Bus

Clipsal is targeted at large scale home control??. The system is installed in prominent buildings as the Sydney Opera house, Wembly Stadium and many more . Nodes communicate over its own separate wired connection, using the C-bus protocol. Each node has its own microprocessor, which allows for distributed intelligence. Each node can also be individually programmed, and communicate over the shared bus. This allows unconventional devices like motors for stadium roofs and many other devices to be part of the network. 

Clipsal's C-bus is a programmable solution, which excels at being a scalable and flexible system. It can also be installed in a private home. However, compared to other systems the requirement of a separate wiring throughout a home can be a disadvantage. 

[#CBus]: wikipedia article on the Clipsal C-Bus protocol http://en.wikipedia.org/wiki/C-Bus_(protocol)

#### LK IHC

LK IHC is a programmable solution , targeted at private homes. It can be installed with a wired network, or using wireless communication. The system tends to be build around simple switches, but with programmable scenarios, e.g., having a button near the front door and in the master bedroom that turns off all lights. It is a modular system, where modules like wireless communication or alarms can be added to the base installation. The plain vanilla implementation is a controllable system, the modules can provide programmable functionality to the system. LK IHC was per 2008 installed in nearly 30% of newly constructed building in Denmark [#MSsurvey]. 

[#LK IHC]: Lauritz Knudsens http://www.lk.dk
 
[#MSsurvey]: Mads Ingwar and Soeren Kristian Jensen. IMM Smart House Project: a state of the art survey. [Cited April 2006]. 

[#Gruber]: John Gruber.  Daring Fireball: Markdown. [Cited January 2006]. 
  Available from <http://daringfireball.net/projects/markdown/>. 

#### MIT House_n

The MIT House_n represents one of many smart environment, build by universities around the world. The smart environments are homes for one or more inhabitants, and are part of a living laboratory. The living lab part of House_n is called PlaceLab and is a one-bedroom condominium, inhabited by volunteers for varying lengths of time. These homes are designed for multi-disciplinary studies of people and their pattens and interactions with new technology and smart home environments. Being university run smart homes, the work coming out of these facilities tends to be proof of concepts. 

[#MIT House_n]: MIT House_n http://architecture.mit.edu/house_n/placelab.html

The projects shown in this survey represent the solutions currently available or in development. There are many different controllable and programmable solution commercially available, with INSTEON, Clipsal C-bus and LK IHC being some of the more widespread representative solutions. INSTEON being a simple controllable solution, Clipsal C-bus and LK IHK are both programmable smart house solutions, but where LK IHC is designed for private homes, the Clipsal C-bus system is better suited for larger buildings. 

MIT's House_n in this survey represents that truly intelligent smart houses only exists in demonstration environments and as proofs of concept, and are not yet widely available on the commercial market.


One of the main problems with current home control solutions is that installing such a system is rather costly and requires installation and configuration, which is rarely trivial . Some of the more advanced systems on the market, such as the LK IHC, incorporate motion sensors and timers  that automatically turn on and off lights or various appliances. These systems will  save money over time, but they require  extensive configuration or programming in order to function properly.
<skal flyttes op som konklusion på survayet>


### BIIIB 
Of all the  qualities mentioned in our vision for the system,  power saving is the most important. **insert data on power consumption of average household** As seen in the survey above, this is an area where most modern home control systems falls short . Most systems are capable of providing only a modest reduction in power consumption, and some even increase the net consumption by adding the cost of running the control system. We want our system to differ from others on this specific aspect. In our system, reducing power consumption is the number one priority.

We will accomplish this by creating a system that focuses on turning off all lights and appliances where they are not needed. <what are the alternatives? why do we want to focus on this?>There are several advantages to this approach, compared to attempting to reduce the power consumption of active appliances. The main advantage is that it provides the largest reduction in power consumption. Most people remember to turn off the light in the bathroom, when they leave it, but this is far less common for the kitchen, or dining room, and only the most environmentally conscious people would ever turn off the light in the living room when they got to the bathroom. This means that there is a lot of wasted energy in the normal household . 

An other advantage  is that it incorporates perfectly with all other power reducing technologies. Bying appliances that use less energy will still give you the same percentage of power reduction as in a normal house. 

This system will also eliminate the common problem of standby mode on many appliances such as tvs or stereos by having the appliance only  in standby mode, when the user is likely to turn it on. The rest of the time the appliance is simply turned off.

Our approach to creating a house that is capable of predicting what the users want it to do, is to learn from what the user does and  mimic these actions at the right times. To accomplice this, the system must do three things:

*  The system must gather data on the users and their behavior in the house
* The system must analyze the data in order to build a decision scheme on which it will base its actions
* The system must be able control the house in real time, based on the decision scheme.

Since the system bases its decisions on data gathered on the user, the system is essentially trying to mimic useractions at the right times. The system will have three stages :
<must the stages be introduced now? can they wait till the design section>
* The untrained stage where the system is running, but it hasn't yet collected enough data to make intelligent decisions. 

* The learning stage where there's enough data to attempt to manipulate the switches of the home. We call this the learning stage, because it provides us with a unique opportunity for the system to learn from the user. If the system makes a mistake and the user corrects it, e.g., the system turns off the lights and the user turns it back on, we can use that interaction to train our system further. In this case we can see it as the user punishing the system for making a mistake. The system will then adjust its decision scheme.  

* After the system has been in the learning stage, it will enter its final stage, which we call the evolution stage. Here the system constantly updates its decision scheme with new data both from monitoring the user, and from being punished for its mistakes. In this stage there is a symbioses between the user and the system where the system reacts to the user and vice versa. <Som jeg forstår dette, så adskiller det sig ikke fra learning stage. Tekstmæssigt er forskellen, at her er det også brugeren, der ændrer opførsel. Men det har vel ikke noget med systemets udvikling at gøre. - men sandsynligt.>



### Gathering data on the users
To mimic user actions, the system  must first gather information on how the user interacts with the house. Therefore the first question we must answer is: What data should we collect on our users?  In order for the system to effectively take over the users direct interactions with the house, we need to know two things. 

* What action needs to be done?
* When shall the action be done?

The first question can be answered by monitoring the users direct interactions with the house. Since we have limited our system to handle lighting, this means monitoring the users interactions with the light switches.
 
The second question is a lot more complex. We need to collect data that can help us determine if the conditions are right for performing a specific action. We could of cause quite literally look at the time the action is performed, and then use that as a trigger, but this requires that users follow a very specific schedule.

To get a more detailed picture of when an action is done, we must analyze it relative to what the user is doing at the time. Since we're focussing on lighting this can be done simply by tracking the users movements. Thereby we will determine when an action shall be done based on where the user is, and where he is heading.

Perhaps the most obvious way of accomplishing this is by using cctv cameras. Using visual analysis is the most effective way of monitoring the user, as it will provide us with vast amounts of data on what the user is doing. By for example installing a fisheye camera in every room and use motion tracking on the video data stream, we can determine exactly where the user is, and what he is doing. While this is probably the solution that provides us with the most precise and detailed data, it does have one problem. Installing cameras in every room of the users house is, in out opinion, an unnecessary  invasion of the users privacy. Even if the video data is not stored in the system, the presence of cameras will give many people the feeling of being watched in their own homes. 

An other approach would be to use a beacon worn by the user that sends out a digital signal. The system could then use multilateration to pinpoint the exact location of the user. The beacon could be attached to the users keychain, incorporated into his cellphone, or, our personal science fiction favorite, injected under his skin. Like the camera approach this solution also has very high  precision in tracking the user through the house. However,besides the point that the user might not always carry his keys or cellphone around, the main issue with this solution is scalability of users. . Even though we limit the system to one user for now , we want a system that can be scaled to accommodate multiple users acting both 	 and autonomously. <make sure to explain that the sensor house works with several users>Having to attach a beacon to every visitor coming into the house is gonna be an annoyance, and without it the house would not react to the visitor at all. 

The solution we chose is to use motion sensors. While this solution does not provide nearly the same precision in determining the users location as using fish eye cameras or multilateration, motion sensors does come with a range of other advantages. Motion sensor is a very cheap solution, compared to installing cctv cameras, and will be far less invasive on the user's privacy. The motion sensor solution will also work for any user in the house, and does not require the user to carry any beacon device like in the multilateration system.  

The system could easily be expanded by several other types of sensors as well. E.g. pressure sensors in the furniture, so the system can determine if there is someone present, even when  motion sensors do not register them. There are several other examples of sensor technologies that could be incorporated in the system. Some  will be discussed in the section 'Future work'. 

For the moment we want to use as few hardware components as possible. There are two reasons for this:

* We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
* Creating a system that analyses and mimics user behavior will have a lot of unknown variables that are hard to predict no matter how it is implemented. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries and then add components as the need for them arises. 

We have chosen not to inquire??? any information on the position of the motion sensors in the house.<REWRITE, and explain> This means that the system does not know where each sensor is positioned, nor which other sensors are in the same room as it (the sensor og system?). This does make analyzing the data a lot more complicated, but we want to stick with the idea of minimizing the installation and configuration. This way the installation process can be boiled down to putting up the sensors, plugging in the system, and pressing "Start".
 
Choosing to only monitor the light switches and using motion sensors to track the user greatly simplifies the data collection. Both the motion sensors and the switches generate events when they are triggered, and the system should simply store these events in a database. 

An alternative to this is to have the system analyze the data live, which would eliminate the need to store the event data. With this approach we do not have to store the events in the system, which over time could amount to a considerable amount of data. The problem is that if we should choose to modify the algorithms that analyze the data, we would effectively loose everything the system has learned so far. By storing the raw event data we can always recalculate a new decision scheme based on the collected data. This solution leaves us with a lot more options later on. The collection of data must still happen in real time. Since it is very important that the events are recorded exactly when they happen, the system must not stall in this process.


### Analyzing the collected data

_“If you torture data long enough, it will tell you what you want!”_ -Ronald Coase Hvorfra
	
Now that we have a lot of data on our users interactions with the house, we need to analyze the data in order for our systems AI to act on the collected data. To be more specific: We need to create a decision scheme that the AI can use as a base for its decision making. 

This is the critical part of the system. Collecting data, and acting based on an existing scheme are bot relatively simple tasks, however, designing the scheme to act, based on collected data, is far more complicated.

The purpose of analyzing the data is to find which specific situations that require the system to perform an action. Since the system does not know which sensors are located near which switches, the system will have to learn these relations  based on the data collected. The simplest solution would be to have the system learn which switches and which sensors are located in the same room, and then create a "link" between them so the motion sensors control the light. This would result in what we have named the silvan[^silvan] system. 

The silvan system is basically having a motion sensor turn on the light when triggered, and then have a timer turn off the light if the sensor is not triggered for a set amount of time. The main problem with this kind of system is that if the user does not trigger a motion sensor regularly, the light will turn off when the user is still in the room. This is commonly a problem in a room like the living room, where the user will likely spend an extended amount of time sitting still. This problem can be addressed by extending the light's timeout time. 

However, this brings us to the second problem. If the user is merely passing by a sensor, the light will still be turned on for its full duration. This greatly reduces the effectiveness of the system from a power saving point of view. 

A better solution is to attempt to identify the users behavior leading up to a switch event <introduce the concept of sensor events and switch events before use>. Since the system only use motions sensors to track the users movements, these sensor events will form the basis for the data analysis. The system could simply look at what sensor was triggered right before a switch was activated, and the create a link between that sensor and the switch. This, however, would result in a system much like the silvan system described above. 

If we instead look at a series of sensor events leading up to a switch event, we will get a much more complex picture of what the user is doing. Since the switches in the house are located in fixed positions around the house, these movement patterns should repeat themselves relatively often. The movement patterns that lead up to a switch being turned off, will most likely also differ from a pattern leading up to a switch being turned on, since the user will be either entering or exiting a room. Once we have analyzed the data and identified the movement patterns related to a switch event, we need to create a decision scheme that the system can base its decision making on. That means we have to organize the analyzed data in a way so we easily can look up a specific pattern, and see whether it should trigger a switch action.
 
 
At first the system will need to gather enough data to make a solid analysis (the untrained stage). The length of this period will vary depending on how the data is analyzed. 
Unlike data collection, analyzing the data does not have  strict time constraints. Since the decision scheme will be based on data collected over an extended period of time, the system will not benefit from having the decision scheme updated in real time.  As a result the time constraints on analyzing the data will be quite loose, and should not pose as a restriction on the system. 
<reevalueate!>

<the house should react to the user, and the user to the house>

[^silvan]: Danish building material retail-chain. 

### Controlling the house
After we have collected and analyzed data the the final task is to have the system control the house in real time, using the decision scheme created from the analyzed data. The system must constantly monitor the user and attempt to match his movement pattern to those present in the decision scheme. As with data collection this has to happen in real time so the patterns are not corrupted.


### Requirement specification

Based of the analysis above we can now form a requirement specification for the project.  The system shall collect data using motion sensors and by monitoring switches. This data should be stored as it is collected and without being manipulated. 

The system should first be in an unlearned stage. In this stage the system should use the collected data to analyze the users movement patterns, leading up to a switch event, in order to create a decision scheme that can help the system mimic the actions of the user . The system should then enter a learning stage, where the system attempts to interact with the switches and learns from the users reactions. <shall we remove references to the stages? when does it change state?> Finally the system should enter, and stay in, an evolution stage, where the system constantly learns both from the actions and reactions of the user. 
<Svært at se, hvad forskellen mellem lærings og evolutionsfasen er. Ud over at systemet konsekvent bliver klogere og klogere? >

