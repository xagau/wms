killall -9 java
rm -rf target
mvn -Dtomee-embedded-plugin.http=8082 package war:war org.apache.tomee.maven:tomee-embedded-maven-plugin:7.0.5:run
#



