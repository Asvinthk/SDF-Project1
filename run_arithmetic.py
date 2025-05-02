#!/usr/bin/env python3
import sys
import subprocess

def main():
    if len(sys.argv) != 5:
        print("Usage: python run_arithmetic.py <int/float> <add/sub/mul/div> <operand1> <operand2>")
        sys.exit(1)

    mode, operation, op1, op2 = sys.argv[1:]

    # Build the project
    subprocess.check_call(["C:\\Users\\asvin\\Downloads\\apache-ant-1.10.15\\bin\\ant.bat", "build"])

    # Run the jar
    jar_name = "aarithmetic.jar"
    subprocess.check_call(["java", "-jar", jar_name, mode, operation, op1, op2])

if __name__ == '__main__':
    main()