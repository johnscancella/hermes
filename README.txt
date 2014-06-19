to update eclipse run
===================================
gradle cleanEclipse eclipse

to build and run application run
===================================
gradle clean bootRepackage && java -jar build/libs/Hermes.jar

debug
===================================
java -Xdebug -Xrunjdwp:transport=dt_socket,address=1000,server=y,suspend=n -jar build/libs/Hermes.jar