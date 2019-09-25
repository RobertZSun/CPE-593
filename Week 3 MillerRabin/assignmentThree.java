
/*
 * @Description:
 * Use the Miller Rabin algorithm together with power mod
 * to determine weather a number, passed as a command line argument, is prime or composite.
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun/CPE-593
 * @Date: 2019-09-21 18:24:04
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-09-24 14:45:41
 */
import java.util.Random;

public class assignmentThree {

    public static long powerMod(int x, int n, int m) {
        long product = 1;
        while (n > 0) {
            if (n % 2 != 0) {
                product = product * x % m;
            }
            x = x * x % m;
            n /= 2;
        }
        return product;
    }

    public static boolean miller_rabin(int n) throws Exception {
        Random random = new Random();
        if (n <= 0) {
            throw new Exception("the number must be greater than ZERO!");
        }
        if(n==2){
            return true;
        }
        if(n%2==0||n==1){
            return false;
        }

        int frontNum = n - 1, exponentOfTwo = 0;while(frontNum%2==0)
        {
            frontNum /= 2;
            exponentOfTwo++;
        }
        int loopTime = 6;out:for(
        int i = 0;i<loopTime;i++)
        {
        int randomNum_a = random.nextInt(n - 2) + 2;
        long x0 = powerMod(randomNum_a, frontNum, n);
        if (x0 == 1 || x0 == n - 1) {
            continue;
        }
        for (int j = 0; j < exponentOfTwo - 1; j++) {
            x0 = (x0 * x0) % n;
            if (x0 == 1) {
                return false;
            }
            if (x0 == n - 1) {
                continue out;
            }
        }
        return false;
    }return true;
    }

    public static void main(String[] args) {
        boolean result = false;
        try {
            result = miller_rabin(Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.out.println("Got an exception: "+ e.getMessage());
            e.printStackTrace();;
        }

        if (result) {
            System.out.println("this is probably a prime number");
        } else {
            System.out.println("this is not a prime number");
        }
    }
}