import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Assignment4Quicksort {

    public static void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void printArray(int[] arr) {
        for (int var : arr) {
            System.out.print(var + " ");
        }
        System.out.println("");
    }

    public static void quick_sort(int[] arr, int left, int right) {
        if (left>=right) {
            return;
        } else {
            int middle = middleIndex(arr, left, right);
            quick_sort(arr, left, middle-1);
            quick_sort(arr, middle+1, right);
        }
    }

    private static int middleIndex(int[] arr, int left, int right){

        int pivot =arr[right];
        int newLeft = left;
        int newRight = right - 1;
        boolean loopOrNot = true;
        while (loopOrNot) {
            while (newLeft < right && arr[newLeft] <= pivot) {
                newLeft++;
                if (newLeft == right) {
                    break;
                }
            }
            while (newRight >= left && arr[newRight] >= pivot) {
                newRight--;
            }
            if (newLeft < newRight) {
                swap(arr, newLeft, newRight);
            } else {
                swap(arr, newLeft, right);
                loopOrNot = false;
            }
        }
        return newLeft;
    }


    public static void readFile(String filePath) throws IOException,Exception {

        if (filePath.indexOf("hw2a.dat")==-1) {
            throw new Exception("find an error in file's name: it should be <hw2a.dat>");
        }

        // BufferedReader
        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(inputStream));
        int total = 0, numOfDigits = 0, line = 0, maxNum = 0;
        String str = null;
        while ((str = bufferedRead.readLine()) != null) {
            ++line;
            String[] strArray =str.split(" ");
            int length = strArray.length;
            int[] temp = new int[length];
            if (line == 1){
                total = Integer.parseInt(str);
                maxNum = total;
                continue;
            }
            if (line%2==0 && length==1){
                numOfDigits = Integer.parseInt(strArray[0]);
                continue;
            } else if (length == numOfDigits){
                for (int i = 0; i < length; i++) {
                    temp[i] = Integer.parseInt(strArray[i]);
                }
                quick_sort(temp, 0, temp.length-1);
                printArray(temp);
                --total;
            } else {
                throw new Exception(line + " line has" + length + "numbers, but doesn't match expected " + numOfDigits);
            }
        }
        inputStream.close();
        bufferedRead.close();
        if ((total<0) || (total>0)) {
            throw new Exception("test case written in the file was: " + maxNum + " but " + total +" less");
        }
        // close
    }




    public static void main(String[] args) {

        //initialize Scanner to get user's file directory input
        Scanner sc = new Scanner(System.in);
        String filePath = "";
        System.out.println("Please enter the path of the file(include filename Extension): [press Enter to finish]");
        System.out.println("Example: G:\\hw2a.dat. Enter here: ");
        if (sc.hasNextLine()) {
            filePath = sc.nextLine();
            System.out.println("The first filePath you entered is: " + filePath);
        }
        sc.close();
        try {
            readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Got an exception: "+ e.getMessage());
            e.printStackTrace();;
        }
    }
}