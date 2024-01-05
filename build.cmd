@echo off

if "%1%" == "-nt" (
    call mvnw.cmd clean package -Pproduction -DskipTests
) else (
    call mvnw.cmd clean package -Pproduction
)

call docker build -t timetable:latest .