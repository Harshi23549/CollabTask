@echo off
if not exist out mkdir out
"C:\Program Files\Microsoft\jdk-21.0.10.7-hotspot\bin\javac.exe" -cp "lib/*;." -d out *.java ui/*.java service/*.java dao/*.java model/*.java utils/*.java
echo Compilation complete.
