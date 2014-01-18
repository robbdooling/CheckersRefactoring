CheckersRefactoring
===================

http://www.se.rit.edu/~swen-262/casestudies/RefactoringCaseStudy.htm

As part of our Engineering of Software Subsystems class, we were tasked with improving an old and buggy Checkers GUI:

Refactored 2013 Design
===================
A. Overview

Team King’s Row is proud to present 2013 Checkers with a more concise, balanced, and maintainable design compared to its 2002 predecessor. The blob of Driver has been significantly reduced with more allocation of responsibilities in other modules. 2013 Checkers’ design is simplified into four subsystems: GUI, Driver, Player, and Board. 

This design held lower coupling by only including collaborators that were required, while introducing higher cohesion with the use of Strategy and Proxy, which narrowed down the system data flow. We achieved an especially better separation of concerns in the breaking down of the Driver blob. Particularly after the removal of Facade, classes become only concerned with one set of concerns or functionality rather than being part of tangled chains of information all passing through Facade to Driver.

B. Analysis

The GUI Subsystem operates much more independently of Driver and other classes than in 2002 checkers. Instead of variables being initialized in the first two GUI screens and then carrying over to the main GUI, the main GUI oversees more variable creation by itself. We see much higher cohesion in this area.

We included the use of the Proxy and Strategy Patterns. The Proxy Pattern allows network gameplay through our standard system by creating a proxy player with NetworkPlayer. The Strategy Pattern removes type-checking of pieces being made by the Rules for validation. 2013 Checkers removes a Poltergeist anti-pattern in the Notifier class. It also simplified and reduced redundant coding by removing Facade and reworking the CheckerGUI class.

Our design also reduces the amount of type-checking in Rules by delegating more move-dependent decisions to Board and Move. This separation of concerns alleviates the massive nesting in Rules. Finally, we also removed several anti-patterns and bad code smells, which are detailed in our implementation.

Running the program
===================
In this directory at the command line, type "javac PlayCheckers.java"

Then "java PlayCheckers"
