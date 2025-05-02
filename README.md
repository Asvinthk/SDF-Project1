# Arbitrary Precision Arithmetic Library

* Asvinth k  ES23BTECH11009

## Overview

This project implements an integer arbitrary precision arithmetic and floating point arithmetic in java. It supports basic arithmetic operations on large integers and floating-point numbers.

## Structure

* /src/arbitraryarithmetic/AInteger.java : Integer arithmetic using array
* /src/arbitraryarithmetic/AFloat.java   : Floating-point arithmetic using array
* /src/MyInfArith.java                   : Main class to parse inputs and invoke operations
* /run/arithmetic.py                     : Python script to run the program from CLI
* /build.xml                             : Ant build script for compiling and creating a JAR

## Build Instructions

1. Make sure you have Java, Python, and Ant installed.
2. Compile the project using:

   > ant build

## Run Instructions

Use the Python wrapper to run operations:

> python run_arithmetic.py <int/float> <operand1> <operand2>

Examples:

> python run\_arithmetic.py int add 123456789 987654321
> python run\_arithmetic.py float div 22.5 7.5

## Supported Operations

* add
* sub
* mul
* div

## Limitations

* No GUI or file-based input/output
* No input validation or graceful error messages
* Float division precision is set to 30 
* No automated unit testing

##
