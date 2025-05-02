import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java -jar aarithmetics.jar <int/float> <add/sub/mul/div> <operand1> <operand2>");
            System.exit(1);
        }
        String mode = args[0].toLowerCase();
        String op   = args[1].toLowerCase();
        String s1   = args[2];
        String s2   = args[3];

        switch (mode) {
            case "int": {
                AInteger a = AInteger.parse(s1);
                AInteger b = AInteger.parse(s2);
                AInteger res;
                switch (op) {
                    case "add": res = a.add(b); break;
                    case "sub": res = a.subtract(b); break;
                    case "mul": res = a.multiply(b); break;
                    case "div": res = a.divide(b); break;
                    default:
                        System.err.println("Unknown operation: " + op);
                        return;
                }
                System.out.println(res);
                break;
            }
            case "float": {
                AFloat a = AFloat.parse(s1);
                AFloat b = AFloat.parse(s2);
                AFloat res;
                switch (op) {
                    case "add": res = a.add(b); break;
                    case "sub": res = a.subtract(b); break;
                    case "mul": res = a.multiply(b); break;
                    case "div": res = a.divide(b); break;
                    default:
                        System.err.println("Unknown operation: " + op);
                        return;
                }
                System.out.println(res);
                break;
            }
            default:
                System.err.println("Unknown mode: " + mode);
        }
    }
}