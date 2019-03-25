# 233Group3Project
CPSC 233 2019 Winter Group 3 L01

This is our third demo for our rendition of Slay the Spire. To compile, ensure that all packages are located in a single folder, navigate
to that folder, then type "javac buttonHandlers/*.java game/*.java logic/*.java -cp buttonHandlers;logic;game;files".
After compiling, you may run the code with: "java game.Game" or "java game.GameGUI".

To run the tests, again ensure that all packages are located in a single folder, navigate to that folder, then type "javac -cp .;junit-
4.12.jar;hamcrest-core-1.3.jar;logic;files tests/*.java". To run a test after compiling, type "java -cp .;junit-4.12.jar;hamcrest-core-
1.3.jar org.junit.runner.JUnitCore tests.<TestName>".
