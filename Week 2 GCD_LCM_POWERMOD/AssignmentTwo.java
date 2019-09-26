/*
 * @Description:
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun/CPE-593
 * @Date: 2019-09-12 14:42:07
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-09-12 17:04:30
 */
public class AssignmentTwo {

    // use euclids algo
    public static int GCD(int a, int b) {
        return b == 0 ? a : GCD(b, a % b);
    }

    public static int powerMod(int a, int b, int c) {
        int product = 1;
        while (b > 0){
            if (b % 2 != 0) {
                product *= a % c;
            }
            a = a * a % c;
            b = b / 2;
        }
        return product;
    }
    public static void main(String[] args) {
        
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int r = Integer.parseInt(args[2]);
        int c = GCD(a, b);
        System.out.println("The GCD of " + a + " and " + b + " is " + c);
        System.out.println("The LCM of " + a + " and " + b + " is " + Math.abs(a * b) / c);
        System.out.println("The POWERMOD of " + a + "^" + b +  "%" + r + " = " + powerMod(a, b, r));
    }
}