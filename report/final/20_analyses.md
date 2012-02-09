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

### observations and analysis of behavioral patterns
what assumptions can we make about the users, related to their behavioral patterns


The main element that separates KIIIB from other similar systems is that the system is autonomously attempting to mimic the users behavior.  In order to accomplish this the system must do three things.

*  The system must gather data on the users, and their behavior in the house
* The system must analyze the data in order to build an activation scheme on which it will base its actions
* The system must actively monitor the users, and act according to the activation scheme.

### Gathering data on the users
We have chosen to create some restrictions when it comes to gathering data on the users:

1. We do not want a system that requires cameras to be put up in private homes
	Visual analysis could provide more detailed information about the users, and their behavior, but we find this to be an unnecessary invasion of privacy. Even if the video data is not stored in the system, the presence of cameras will give many people the feeling of being watched in their own homes.
2. We want to use as little hardware as possible.
	There is two reasons for this:
	1.  We want to keep the system as simple as possible from the consumers perspective. That means a system with as few components as possible.
	2. Creating a system that analyses, and mimics user behavior will no matter how it is implemented have a lot of unknown variables, that are hard to predict. It will therefore be preferable to start out with a system that is stripped down to the bare necessaries, and then add components as the need for them arises.
3. The system should not require any direct interaction from the users.
	There has been a lot of change in software development the last few years, and on of the major focus areas has been on user interface design. The general theme is ease of use. You could say that the role of software is moving more towards being a servant, that a tool for the private user[^need-ref]. **elaborate on development in user interfaces**
[^need-ref]: Reference needed!

	

	