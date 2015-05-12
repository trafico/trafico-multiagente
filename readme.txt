Este es el proyecto de la clase Tópicos avanzados en inteligencia artificial distribuida y sistemas multiagente 2015
 
This project can be imported into IDEs like eclipse.


======== Contents ========

* The 'pom.xml' is used by Maven to e.g. find
  project dependencies like the Jadex libraries.
  It tells maven to use the release libraries from the maven central
  repository or the latest Jadex libraries from the snapshot repository
  instead of the (possibly newer). The desired Jadex version can be
  specified in the POM before importing, but can also be changed later
  causing a reload of the dependencies in the IDE.

* The 'src/main/java' folder contains a simple "Hello world!" agent.
  As a default, compiled classes are placed by Maven in the
  'target/classes' folder.

* The 'default.settings.xml' is a preconfigured platform
  setting, that already includes the 'target/classes' folder
  in the library path and the explorer view.


======== Installation (for eclipse) ========

* Unzip the example project to a place of your choice.

* When using eclipse you need the Maven plugin, which is available at:
  http://m2eclipse.sonatype.org/

* Choose "Import..." -> "Maven/Existing Maven Projects"
  and select the unzipped example directory as root directory.

* To start Jadex, right-click on the imported project and choose
  "Run As" -> "Java Application".
  Select the 'Starter' class from package 'jadex.base'.
  Click "Run" and the Jadex platform should start.
  
* In the JCC, the 'HelloAgent' should be already selected.
  Click "Start" to start the agent and check the console for the
  output "Hello world!".
  
* Eclipse remembers the launch configuration. Therefore in the future,
  you can simply select the "Starter" configuration from the run history
  to start the platform.
  
* In order to run another project you have to duplicate the previous 
  launch configuration from run configurations and change the project name 
  to the desired one.
  

======== Requirements ========
* Eclipse (tested with version: Luna Service Release 2 (4.4.2))
* Jadex 2.4
  
  
======== Description of branches ========
* Master: Just dummy classes.
* Test: Just dummy classes.
* Develop: Classes developed to Jadex 2.3, without integration of agents
  in the environment, just test agents with interction with others of 
  the same type but not with other types.
* Funccional: Classes developed to Jadex 2.4, integration of agents in 
  environment "Ambientito.application.xml", use of services between 
  agents of the same type and also with other types. This branch 
  contains functional code to run a simulation, each package will be
  explained.
  
  
======== Packages of Funccional branch ========
* Cars:
     - SimpleCarBDI.java: This java class defines two posibles car agents: the intelligent car using Dijkstra algorithm to
                          to compute its trajectory in the environment and the random car using a random strategy to reach 
                          its goal through the streets regardless of the speed of these. 
                          At this time, the selection of the types of agents is done by calling "calcularRuta()" method, 
                          which lets you select between random route or using Dijkstra algorithm to achieve the objective point.
                          At the beginning of its operation, a "EstadoAuto" object is created, containing information on the 
                          current status of the agent properties, this object is to be passed as a service to other agents such 
                          car and traffic lights. The "EstadoAuto" object is updated with each change of agent properties.
                          Once the initial position of agent in the environment is determined, a target point randomly defined by 
                          the static method "posDisponible.getPosicion ()" in the enviroment packet, then the route to follow by the car
                          is determined depending on the defined agent behavior. The car begins to make its way step by step, 
                          taking the status of agents around him, stopping when a car in front and equal to his direction 
                          and making stops when it arrives to an intersection with a traffic light and red light in the direction 
                          to which it is directed.
                          In the current version of this agent execution it comes at the end when successfully reaches the target 
                          point on the map.
     
     -IEstadoAutoService.java: This java interface defines the provided service by the Car agent to other agents in the enviroment.
                               The service provides a "EstadoAuto" object type containing information of current properties in the agent 
                               through an "IFuture" container in Jadex definition.
                               
     -Rutas.java: This java class contains static methods that allows to the agent calculate a route from a given initial point to destination 
                  point in the environment. 
                  * getRutaDijstra(): It is a static method that provides a char string corresponding to the route for the car agent.
                                      This path is calculated by Dijkstra algorithm implementation for the optimal path between two vertices 
                                      in a graph where each edge corresponds to the speed of the roads between the intersections. 
                                      This method aims to select the best option for the agent to reach its destination within the environment.
                  *getRutaRandom(): It is also a static method that returns a character string corresponding to a way forward for the car. 
                                    The selection of nodes on the map is done randomly from the starting point to the target regardless street speeds.
                  *devolverRuta(): It is a method that interprets a route generated as a char string understandable for car agent. For example, 
                                   the string "n n n s s s" indicates three movements of the car towards the north and three moves south in
                                   the environment.
                                   
     -Grafo.java: This java class contains auxiliary data structures that allow us to interpret the environment as a directed graph, taking the 
                  intersections of the environment as vertices of a graph and information from the streets as edges. These data structures are
                  used in "Rutas.java" java class to generate paths between points in the environment.
                  
     *Other files as "SimpleCarBDI.agent.xml" and "DestinoPlan.java" correspond to the implementation of the Car agent on BDI Jadex version V2.
      Jadex BDI V3 version is used, this allows to development agents using only Java code for better functioning.

* Environment:
     - GrafoCalles.java: Generates an array of streets with its velocity,
                         also an array of availability which change every
                         five "estaDisponible()" method calls to give or
                         take availability of random streets.
     - HelloAgent.java: Dummy class
     - main.java: Used to run "GrafoCalles.java" alone.
     - MoveTask.java: Defines the task of movement of an agent. (Not used)
     - posDisponible.java: Gives random available positions. Is used by 
                           car agents to set position to cars and objectives.
     - AmbienteTrafico.application.xml: Representation of a city with 9x9 
                                        blocks, integration with agents is 
                                        not complete, this environment does
                                        not have all the features to run.
     - Ambientito.application.xml: Representation of a city with 3x3 blocks,
                                   integration with agents is complete, 
                                   default configuration runs SimpleCarBDI.class
                                   and SemaforoSimpleBDI.class agents.
* Environment.imagenes: Contains all the images used by the environment.
* Lights:
     -SemaforoSimpleBDI.java : Implements simple car. It uses a simple counter to 
                               change the line of one direction: North, South,East and West. 
                               Every 10 seconds the light changes its current line.
                               
    -SemaforoEgoistaBDI.java : This class has the same methods and services as SemaforoSimpleBDI.
                               It also has communication with his neighbors and it chooses the line
                               with the highest number of cars.
                               
    -SemaforoComunicacionBDI.java: This class has the same methods and serivces as SemaforoSimpleBDI.
                                   It communicates with his neighbors and obtains the number of cars 
                                   of their lines. If the number of cars of his neighbors its bigger 
                                   than its one line, it can change the current line to help its
                                   neighbors.
                                   
    -EstadoSemaforoService.java: The service of lights to cars, it returns the position and its direction
                                The position is a pair of integers and a string that represents a direction
                               
    -TraficoSemaforo.java: A container of traffic information.
                          
    -PaqueteTrafico.java: A container of every line and its current number of cars.
                         
    -TraficoEstadoSemaforo.java: Ther service of lights to lights. It returns an instance of PaqueteTrafico
                          
    -PosicionSemaforo.java: Container for the state of a light.

======== How to run application ========
* Generate a starter to this project following last instruction of section
  "Instalation (for eclipse)" of this file.
* Adds "src" directory to the path if it is not already added.
* Go to environment package and select "Ambientito.application.xml"
* Click Start.
