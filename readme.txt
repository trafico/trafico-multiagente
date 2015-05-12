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

======== How to run application ========
* Generate a starter to this project following last instruction of section
  "Instalation (for eclipse)" of this file.
* Adds "src" directory to the path if it is not already added.
* Go to environment package and select "Ambientito.application.xml"
* Click Start.
