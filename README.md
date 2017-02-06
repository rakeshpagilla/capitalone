#  Steps for Compiling and Running the code:
1) You need to have Git, maven and java to run this application on your VM or mac. Maven is used for dependency management. 

2) git clone https://github.com/rakeshpagilla/capitalone.git

3) cd capitalone

4) Run mvn clean install.

5) The jar file is created in target directory with the name CodingExercise.jar. All the necessary dependent jars are in the lib directory. You need to have the lib directory and the jar file in same directory to run it. 

5) Execute the below command for running the main method in jar file .

For just printing the monthly expenses and income . java -jar target/CodingExercise.jar

For Printing the monthly expenses and income excluding donuts related transactions.   java -jar target/CodingExercise.jar -donuts

For Printing the monthly expenses and income excluding credit card payments.   java -jar target/CodingExercise.jar -ignorecc

Please let me know if you have any issues running this code. you can reach me at rakeshpagilla@gmail.com