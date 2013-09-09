
title Code Coverage Analysis

call mvn -PCodeCoverage clean jacoco:prepare-agent test jacoco:report

rem mvn -PCodeCoverge clean test sonar:sonar

start target/site/jacoco/index.html

pause