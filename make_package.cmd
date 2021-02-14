SET JAVA_HOME="D:\java\jdk-11"
rem "C:\Program Files (x86)\Java\apache-maven-3.6.3\bin\mvn.cmd" clean package

copy "D:\WORK\Programming\capital\target\capital-0.0.1-SNAPSHOT.jar" "D:\WORK\Programming\capital\target\capital.jar"

pscp.exe -P 22 -i "C:\Users\dubin\.ssh\ssh\privat-saved.ppk" "D:\WORK\Programming\capital\target\capital.jar" dav@78.40.219.225:/home/dav/WORK/capital
