
## Introduction

this is the the intro baby

### Scope

__Define Context__

* Present the domain of the project
  * From he general to the specific
    * Distributed systems -> wireless -> ubiquitous computing
    * Include references to demonstrate you know the domain
* Identify important properties of the project domain
  * What makes this project domain special?
    * Why do we need to undertake this project
* Define the scope of the project
  * what sub-problems will be considered
  * what sub-problems will _not_ be considered

### Goals

__Identify Goals__

* Project goals
  * What are the main problems that the project should solve?
* Non-functional requirements
  * Usability, performance, flexibility, extensibility, simplicity, security...
* Non-project goals
  * experiments with (specific) new technology
  * personal development

__short answers__

* Project goals
    * Create a home control system that relies on machine learning instead of programmability.
* Non-functional requirements
  * Usability, performance, flexibility, extensibility, simplicity, security...
* Non-project goals
    * examine the possibilities of a system with minimal knowledge about its operating environment.
    * examine the possibilities of a system with minimal setup, and programming required




### writing a good introduction

* what is the problem
    - explain the context of the project and identify goals
* what is the proposed solution?
    - Analyse problems and present proposed solution
* How do we evaluate the proposed solution?
    - Present evaluation criteria for proposed solution
    - define the method and scope of the project
* What did we learn?
    - Present a summary of the overall conclusions
    - lure the reader into reading the rest of the report

We want to create an intelligent smarthouse system, with a minimal setup requirement, to control the lighting. A system which after switches and sensors are physically installed, doesn't require further setup or technical expertise from the user. The system shouldn't require any direct user interaction.

The system should be able to gradutally take over turning on and off the light. It should learn solely based on the user's normal behavoid, which patterns leads up to turning the light on or off, for how long should the light stay on. 

Ideally this should lead to increased comfort for the users, by not having to manually control the light. Possebly also reduced energy consumption, by only having light on when the users need it. Also by being more active about turning light off where it isn't needed, where most users wouldn't bother to get up to turn it off.

### System limitations, assumptions and project scope

Before we start to analyze what makes a good home control system we will outline which  limitations, we will impose on the system. Some of these limitations are born out of personal interest , and some are there to limit the scope of the project. 

To  limit the scope of the project, we focus on controlling the lighting.This means that we will not go into appliances or house features such as heating, ventilation, or hot water. These are all areas where the system will be equally beneficial . However, modifying the system to incorporate these elements, would not require any major changes to the fundamental design of the core system. It would rather  require a lot of  time consuming implementation work. 

We assume that people who have home control systems don't have windows . At the very least we will work under the assumption that the lighting conditions outside a room does not affect the conditions inside. A room with the lights turned on is light, and a room with the lights turned off is dark. This is simply to eliminate outside factors such as varying lighting conditions.Normally if there is enough natural lighting in a room, the user will not want the artificial light turned on. 

The people in the house is trapped in "Groundhog Day"[#ramis:1993][], or more precisely, Groundhog hour. We will assume that the users behavior and requirements does not change depending on date nor  time of day. 
<TODO:Do we assume this.. or can we later explain that this assumption is unneccesarry!!???>

Finally we will make the assumption that a user only needs light in rooms where he or she is present. In reality situations may arise where the user would want the light to stay on when leaving at room, e.g., if he has a birdcage or an aquarium in the room.

<Only one user home!>




