package arbitraryarithmetic;

public class AFloat {
    private final String value;

    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    public AFloat() {
        this.value = "0.0";
    }

    public AFloat(String s) {
        if (!s.matches("-?\\d+(\\.\\d+)?")) throw new IllegalArgumentException("Invalid float");
        this.value = normalize(s);
    }

    public AFloat(AFloat other) {
        this.value = other.value;
    }

    // Remove unnecessary zeros and normalize the float string
    private static String normalize(String s) {
        boolean negative = s.startsWith("-");
        if (negative) s = s.substring(1);

        if (!s.contains(".")) s += ".0";
        String[] parts = s.split("\\.");
        String intPart = parts[0].replaceFirst("^0+(?!$)", "");
        String fracPart = parts[1].replaceFirst("0+$", "");

        if (fracPart.isEmpty()) fracPart = "0";
        return (negative ? "-" : "") + intPart + "." + fracPart;
    }

    // Helper: Split into integer and fractional parts
    private static String[] splitParts(String s) {
        if (!s.contains(".")) s += ".0";
        return s.split("\\.");
    }

    // Pad two fractions to the same length
    private static String[] alignFractionParts(String a, String b) {
        String[] p1 = splitParts(a);
        String[] p2 = splitParts(b);

        int fracLen = Math.max(p1[1].length(), p2[1].length());
        p1[1] = String.format("%-" + fracLen + "s", p1[1]).replace(' ', '0');
        p2[1] = String.format("%-" + fracLen + "s", p2[1]).replace(' ', '0');

        return new String[]{
            p1[0] + p1[1],
            p2[0] + p2[1],
            Integer.toString(fracLen)
        };
    }

    public AFloat add(AFloat other) {
        String[] aligned = alignFractionParts(this.value, other.value);
        AInteger result = new AInteger(aligned[0]).add(new AInteger(aligned[1]));
        return new AFloat(insertDecimal(result.toString(), Integer.parseInt(aligned[2])));
    }

    public AFloat subtract(AFloat other) {
        String[] aligned = alignFractionParts(this.value, other.value);
        AInteger result = new AInteger(aligned[0]).subtract(new AInteger(aligned[1]));
        return new AFloat(insertDecimal(result.toString(), Integer.parseInt(aligned[2])));
    }

    public AFloat multiply(AFloat other) {
        String[] p1 = splitParts(this.value);
        String[] p2 = splitParts(other.value);

        int totalFrac = p1[1].length() + p2[1].length();
        String num1 = p1[0] + p1[1];
        String num2 = p2[0] + p2[1];

        AInteger result = new AInteger(num1).multiply(new AInteger(num2));
        return new AFloat(insertDecimal(result.toString(), totalFrac));
    }

    public AFloat divide(AFloat other) {
        final int precision = 30;
        if (other.value.equals("0.0")) throw new ArithmeticException("Divide by zero");

        String[] p1 = splitParts(this.value);
        String[] p2 = splitParts(other.value);

        String num1 = (p1[0] + p1[1]) + "0".repeat(precision);  // scale numerator
        String num2 = p2[0] + p2[1];

        AInteger result = new AInteger(num1).divide(new AInteger(num2));
        return new AFloat(insertDecimal(result.toString(), precision));
    }

    private static String insertDecimal(String s, int positionFromRight) {
        boolean negative = s.startsWith("-");
        if (negative) s = s.substring(1);

        while (s.length() <= positionFromRight) s = "0" + s;
        int point = s.length() - positionFromRight;
        String res = s.substring(0, point) + "." + s.substring(point);
        return normalize((negative ? "-" : "") + res);
    }

    @Override
    public String toString() {
        return value;
    }
}
