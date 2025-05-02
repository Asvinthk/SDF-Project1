package arbitraryarithmetic;

public class AInteger {
    private final String value; // string to store the integer

    public static AInteger parse(String s) {
        return new AInteger(s);
    }
    

    public AInteger() {
        this.value = "0";
    }

    public AInteger(String s) {
        if (!s.matches("-?\\d+")) throw new IllegalArgumentException("Invalid integer");
        this.value = removeLeadingZeros(s);
    }

    public AInteger(AInteger other) {
        this.value = other.value;
    }

    // Add two positive numbers as strings
    private static String addStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1, j = b.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int digitA = (i >= 0) ? a.charAt(i--) - '0' : 0;
            int digitB = (j >= 0) ? b.charAt(j--) - '0' : 0;

            int sum = digitA + digitB + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }

        return sb.reverse().toString();
    }

    // Compare absolute values of two strings
    private static int compareAbs(String a, String b) {
        a = removeLeadingZeros(a);
        b = removeLeadingZeros(b);

        if (a.length() != b.length()) return Integer.compare(a.length(), b.length());
        return a.compareTo(b);
    }

    // Subtract two positive numbers (a >= b)
    private static String subtractStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int borrow = 0, i = a.length() - 1, j = b.length() - 1;

        while (i >= 0) {
            int digitA = a.charAt(i--) - '0';
            int digitB = (j >= 0) ? b.charAt(j--) - '0' : 0;

            int diff = digitA - digitB - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else borrow = 0;

            sb.append(diff);
        }

        return removeLeadingZeros(sb.reverse().toString());
    }

    // Multiply two positive numbers as strings
    private static String multiplyStrings(String a, String b) {
        int[] result = new int[a.length() + b.length()];

        for (int i = a.length() - 1; i >= 0; i--) {
            int digitA = a.charAt(i) - '0';
            for (int j = b.length() - 1; j >= 0; j--) {
                int digitB = b.charAt(j) - '0';
                int mul = digitA * digitB;
                int sum = mul + result[i + j + 1];

                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int num : result)
            if (!(sb.length() == 0 && num == 0)) sb.append(num);

        return (sb.length() == 0) ? "0" : sb.toString();
    }

    // Basic long division logic (positive integers)
    private static String divideStrings(String a, String b) {
        if (b.equals("0")) throw new ArithmeticException("Divide by zero");

        StringBuilder result = new StringBuilder();
        String dividend = "";

        for (char ch : a.toCharArray()) {
            dividend += ch;
            dividend = removeLeadingZeros(dividend);
            int count = 0;
            while (compareAbs(dividend, b) >= 0) {
                dividend = subtractStrings(dividend, b);
                count++;
            }
            result.append(count);
        }

        return removeLeadingZeros(result.toString());
    }

    private static String removeLeadingZeros(String s) {
        boolean negative = s.startsWith("-");
        s = s.replaceFirst("^-?0+", "");
        return negative ? "-" + (s.isEmpty() ? "0" : s) : (s.isEmpty() ? "0" : s);
    }

    public AInteger add(AInteger other) {
        if (this.value.startsWith("-") && other.value.startsWith("-"))
            return new AInteger("-" + addStrings(this.value.substring(1), other.value.substring(1)));
        else if (this.value.startsWith("-"))
            return other.subtract(new AInteger(this.value.substring(1)));

        else if (other.value.startsWith("-"))
            return other.subtract(new AInteger(this.value.substring(1)));
        else
            return new AInteger(addStrings(this.value, other.value));
    }

    public AInteger subtract(AInteger other) {
        boolean negA = this.value.startsWith("-");
        boolean negB = other.value.startsWith("-");

        String a = negA ? this.value.substring(1) : this.value;
        String b = negB ? other.value.substring(1) : other.value;

        if (negA && !negB) // -a - b = -(a + b)
            return new AInteger("-" + addStrings(a, b));
        else if (!negA && negB) // a - (-b) = a + b
            return new AInteger(addStrings(a, b));
        else if (negA && negB) // -a - (-b) = b - a
            return new AInteger(new AInteger(b).subtract(new AInteger(a)).value);
        else {
            int cmp = compareAbs(a, b);
            if (cmp == 0) return new AInteger("0");
            else if (cmp > 0)
                return new AInteger(subtractStrings(a, b));
            else
                return new AInteger("-" + subtractStrings(b, a));
        }
    }

    public AInteger multiply(AInteger other) {
        boolean neg = (this.value.startsWith("-") ^ other.value.startsWith("-"));
        String a = this.value.replace("-", "");
        String b = other.value.replace("-", "");
        String result = multiplyStrings(a, b);
        return new AInteger(neg && !result.equals("0") ? "-" + result : result);
    }

    public AInteger divide(AInteger other) {
        if (other.value.equals("0")) throw new ArithmeticException("Division by zero");
        boolean neg = (this.value.startsWith("-") ^ other.value.startsWith("-"));
        String a = this.value.replace("-", "");
        String b = other.value.replace("-", "");
        String result = divideStrings(a, b);
        return new AInteger(neg && !result.equals("0") ? "-" + result : result);
    }

    @Override
    public String toString() {
        return value;
    }
}
