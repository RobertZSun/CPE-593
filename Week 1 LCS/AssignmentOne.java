import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.util.Arrays;
/*
 * @Description:
 *  Write code to read in two large files and perform LCS, printing out the number of bytes the two files have in common and printing all the letters in common (the longest common sub-sequence).
 *  You will need to use memoization.
 *  All homework is to be handed in as a single .java or .cpp /.cc file.
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun/CPE-593
 * @Date: 2019-09-05 18:24:04
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-09-12 14:45:41
 */
public class AssignmentOne {

    //function to read file and extract all the characters and concatenate to a string without spaces
    public static String readFile(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
            int len = 0;
            String str = "";
            while ((len = fr.read()) != -1) {
                //remove all spaces between character and spaces at the beginning and the end
                if ((char)len == ' ') {
                    continue;
                }else{
                    // System.out.println((char)len);
                    str = str + (char)len;
                }
            }
            fr.close();
            //remove all line break at the end of each line
            str = str.replace("\r\n","");
            // System.out.println("str = " + str);
        return str;
    }

    /*calculate the number of LCS, using memorization by using array to store the previous result
    * to speed up the process
    * the formular used is:
    *            { 0                           if i = 0 or j = 0
    *   c[i,j] = { c[i-1, j-1] + 1             if i,j >0, xi = yj
    *            { max{c[i, j-1], c[i-1, j]}   if i,j >0, xi != yj
    */
    public static int LCS(char[] arg1, char[] arg2, int[][] memo) {
        if (memo[arg1.length][arg2.length] > 0) {
            // System.out.println("this time used memo[" + arg1.length + "][" + arg2.length + "] = " + memo[arg1.length][arg2.length]);
            return memo[arg1.length][arg2.length];
        }
        if (arg1.length == 0 || arg2.length == 0) {
            return 0;
        } else if (arg1[arg1.length-1] == arg2[arg2.length-1]){
            return memo[arg1.length][arg2.length] = (1 + LCS(Arrays.copyOfRange(arg1, 0, arg1.length -1), Arrays.copyOfRange(arg2, 0, arg2.length -1), memo));
        } else {
            int numleft = LCS(Arrays.copyOfRange(arg1, 0, arg1.length - 1), Arrays.copyOfRange(arg2, 0, arg2.length), memo);
            int numright = LCS(Arrays.copyOfRange(arg1, 0, arg1.length), Arrays.copyOfRange(arg2, 0, arg2.length - 1), memo);
            return  memo[arg1.length][arg2.length] = Math.max(numleft, numright);
        }
    }
    public static void main(String[] args) {
        //initialize Scanner to get user's file directory input
        Scanner sc = new Scanner(System.in);
        String strOne = "";
        String strTwo = "";
        String filePath1 = "";
        String filePath2 = "";

        System.out.println("Please enter the path of the first file(include filename Extension): [press Enter to finish]");
        System.out.println("Example: G:\\file1.txt. Enter here: ");
        if (sc.hasNextLine()) {
            filePath1 = sc.nextLine();
            System.out.println("The first filePath you entered is: " + filePath1);
        }
        System.out.println("Please enter the path of the second file(include filename Extension): [press Enter to finish]");
        if (sc.hasNextLine()) {
            filePath2 = sc.nextLine();
            System.out.println("The second filepath you entered is: " + filePath2);
        }
        sc.close();

        // read in two large files to perform LCS
        try {
            strOne = readFile(filePath1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            strTwo = readFile(filePath2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The data read from first file is: \n" + strOne + "\n");
        System.out.println("The data read from second file is: \n" + strTwo + "\n");
        char[] ca = strOne.toCharArray();
        char[] cb = strTwo.toCharArray();
        int[][] memo = new int[ca.length+1][cb.length+1];

        System.out.println("the number of LCS is: " + LCS(ca, cb, memo));
    }
}