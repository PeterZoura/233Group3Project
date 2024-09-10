# 233Group3Project
CPSC 233 2019 Winter Group 3 L01

instll java jdk 21. on Ubuntu you can use `sudo apt-get install openjdk-21-jdk`\
add the path where it was installed to the environment variables list
the path should look like this: `JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64`

install the javafx 11 SDK. on Ubuntu you can download the SDK file from the url, unzip it, then look in the folder 
for the /lib directory and add it's path to the environment variables list as described below
https://gluonhq.com/products/javafx/
add this: `PATH_TO_FX=path/to/javafx-sdk-22.0.1/lib`
to the environment variables list


This is our third demo for our rendition of Slay the Spire. To compile, ensure that all packages are located in a single folder, navigate
to that folder, then type 
`javac --module-path $PATH_TO_FX --add-modules javafx.controls buttonHandlers/*.java game/*.java logic/*.java`
After compiling, you may run the code with: 
`java game.Game` for no-graphics mode or `java --module-path $PATH_TO_FX --add-modules javafx.controls game.GameGUI`.

To run the tests, again ensure that all packages are located in a single folder, navigate to that folder, then type `"javac -cp .;junit-
4.12.jar;hamcrest-core-1.3.jar;logic;files tests/*.java"`. To run a test after compiling, type `"java -cp .;junit-4.12.jar;hamcrest-core-
1.3.jar org.junit.runner.JUnitCore tests.<TestName>"`.
UPDATE ON THE TESTS: When I updated this project to try to get the game to run I didn't bother to get the tests to run, so this info about the tests is wrong.
