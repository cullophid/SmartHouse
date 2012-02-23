
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

### Smart House Survey

	"If I have seen further it is by standing on the shoulders of giants" -- Isaac Newton

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

Clipsal is targeted at large scale home controlled, install in such prominent buildings as the Sydney Opera house, Wembly Stadium and many more. Nodes communicate over it's own separate wired connection, using the C-bus protocol. Each node has it's own microprocessor, which allows for distributed intelligence. Each node can also be individually programmed, and communicate over the shared bus. This allows unconventional devices like motors for stadium roofs and many other devices to be part of the network. Clipsal's C-bus is a programmable solution, which exels at being a scalable and flexible system. It can also be installed in a private home, but requiring it's own seperate wiring to be installed through out a home, can be a disadvantage compared to other systems. 

[#CBus]: wipedia article on the Clipsal C-Bus protocol http://en.wikipedia.org/wiki/C-Bus_(protocol)

#### LK IHC

LK IHC is a programmable solution, targeted at private homes. It can be installed with a wired network, or wireless communication. This solusion tends to be build around simple switches, but with programmable scenarios, e.g. having a turn off all light button near the front door and the master bedroom. It is a modular system, where modules like wireless communication or alarms, can be added to the base installation. The plain vanilla implementation is a controllable system, the modules can provide programmable functionality to the system. LK IHC was per 2008 installed in nearly 30% of newly constructed building in denmark [#MSsurvey].

[#LK IHC]: Lauritz Knudsens http://www.lk.dk
 
[#MSsurvey]: Mads Ingwar and Soeren Kristian Jensen. IMM Smart House Project: a state of the art survey. [Cited April 2006]. 

[#Gruber]: John Gruber.  Daring Fireball: Markdown. [Cited January 2006]. 
  Available from <http://daringfireball.net/projects/markdown/>.

#### MIT House_n

The MIT House_n represent one of many smart environment, build by universities around the world. The smart environments are homes for one or more inhabitants, and are part of a living laboratory. The living lab part of House_n is called PlaceLab, and is a one-bedroom condominium, inhabited by volunteers for varying lengths of time. These homes are designed for multi-disciplinary studies, of people and their pattens and interactions with new technology and smart home environments. Being university run smart homes, the work comming out of these facilities tends to be proof of concepts. 

[#MIT House_n]: MIT House_n http://architecture.mit.edu/house_n/placelab.html

The projects highlighted in this survey represent the solutions currently available or in development. There are many different controllable and programmable solution commercially available, with INSTEON, Clipsal C-bus and LK IHC being some of the more widespread representative solutions. INSTEON being a simple controllable solution, Clipsal C-bus and LK IHK are both programmable smart house solutions, but where LK IHC is designed for private homes, the Clipsal C-bus system is better suited for larger buildings. 

MIT's House_n in this survey represent that truly intelligent smart houses only exists in demonstration environments and as proofs of concept, and are not yet widely available on the commercial market.

### System limitations, assumptions and project scope

Before we start to analyze what makes a good home control system, we start by outlining which  limitations we will impose on the system. Some of these limitations are born out of personal interest, and some are there to limit the scope of the project. In order to limit the scope of the project, we have chosen only to focus on controlling the lighting, and are disregarding any appliances, or house features, such as heating, ventilation, or hot water. These are all areas where the system will be equally beneficial as with lighting, but modifying the system to incorporate these elements, would not require any major changes to the design of the core system, but would require a lot of rather trivial, though time consuming implementation work. 
We assume that people who have home control systems don't have windows. At the very least we will work under the assumption that the lighting conditions outside a room does not affect the conditions inside. A room with the lights turned on is light, and a room with the lights turned off is dark. This is simply to eliminate outside factors such as varying lighting conditions. Normally if there is enough natural lighting in a room, the user will not want the artificial light turned on. 
The people in the house is trapped in "Groundhog Day"[#ramis:1993][], or maybe more precisely Groundhog hour. We will assume that the users behavior , and requirements, does not change depending on which day it is, nor the time of day.
Finally we will make the assumption that the user only needs light in rooms where he is present. In reality there might be situations where the user would want the light to stay on when he leaves the room, e.g. if he has a birdcage or a aquarium in the room.


### BIIIB 
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
An other approach would be to use a beacon worn by the user that sends out a digital signal. The system could then use multilateration to pinpoint the exact location of the user. The bacon could be attached to the users keychain, incorporated into his cellphone, or our personal science fiction favorite injected under his skin. Like the camera approach this solution also has very high  precision in tracking the user through the house. However there is one issue that poses a big problem. Besides the point that the user might not always carry his keys or cellphone around, the main issue with this solution is scalability. By scalability we mean more specifically scalability of users. Even though we limited the system to one user for now, we want a system that can be scaled up to accommodate multiple users acting autonomously at the same time. Having to attach a beacon to every visitor coming into the house is gonna be an annoyance, and without it the house would not react to the visitor at all. The solution we chose is to use motion sensor. 

We want to use as few hardware components as possible. There is two reasons for this:

* We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
* Creating a system that analyses, and mimics user behavior will no matter how it is implemented have a lot of unknown variables, that are hard to predict. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries, and then add components as the need for them arises.


### user interaction
The system should not require any direct interaction from the users. There has been a lot of change in software development the last few years, and on of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user[^need-ref]. **elaborate on development in user interfaces**
3. The system should not require any direct interaction from the users.
	There has been a lot of change in software development the last few years, and one of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user [^need-ref]. **elaborate on development in user interfaces**
[^need-ref]: Reference needed!

### Analyzing the collected data
Now that we have a lot of data on our users interactions with the house, we need to analyze the data in order for our systems AI to act on the collected data, or to be more specific: We need to create an action scheme, that the AI can use as a base for its decision making.

### Controlling the house
The final task is to have the 

### Testing data

In order to test and evaluate the implementation, we need some data to train the system. Ideally the implementation would complete early enough to not only collect data from the different stages of the system: untrained, learning and eventually stable stage. The untrained stage where the system is running, but it haven't yet collected enough data to make intelligent decisions. The learning stage where there's enough data to attempt to manipulate the switches of the home, which may or may not be correct, and the system will attempt to learn from it's successes or failures. And after the system has been in the learning stage, it should stabilize (assuming the users doesn't change their routines). Since the system should take over the control of the switches, the lack of new user input could potentially lead to unlearning correct behavior, returning to the learning stage.

If the system isn't ready in time for data to be collected for the learning or stable stage, learning data for the untrained stage is the most crucial. Data from the untrained stage allows us to evaluate how much the system can learn, without the user interaction with the system. With the data from the untrained stage alone, the system would still be able to determine the movement patterns of the users, and have an idea of when to turn the switches on and off. It would also have a good idea of the which sensors and switches are in the are near each other or in the same room.


