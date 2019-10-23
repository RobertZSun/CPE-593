import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * @Description: 
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun
 * @Date: 2019-10-22 00:20:07
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-10-22 21:53:09
 */
public class Assignment5GrowArray {

    /**
     * This function read the file path under the root of this project
     * return a BigDecimal[] contains the mins and max numbers
     * BigDecimal[0] = xMin;  BigDecimal[1] = xMax;  BigDecimal[2] = yMin; BigDecimal[3] = yMax;
     * @param filePath； given file path to parse
     * @param ga: a grow array which stores all the Points(class)
     * @param result pass in a BigDecimal[] to extract xMin, xMax, yMin, yMax
     * @throws IOException
     * @throws Exception
     */
    public static void readFile(String filePath, GrowArray<Point> ga, BigDecimal[] result) throws IOException, Exception {

        // make sure the file name is convexhullpoints.dat
        if (filePath.indexOf("convexhullpoints.dat") == -1) {
            throw new Exception("find an error in file's name: it should be <convexhullpoints.dat>");
        }

        // BufferedReader
        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        boolean firstRound = true;

        // initialize the xMin, xMax, yMin and yMax
        BigDecimal xMin = new BigDecimal(0);
        BigDecimal xMax = new BigDecimal(0);
        BigDecimal yMin = new BigDecimal(0);
        BigDecimal yMax = new BigDecimal(0);
        
        while ((str = bufferedRead.readLine()) != null) {
            // get rid of spaces at the start and the end
            str = str.trim();
            // get rid of the empty line
            if (str.equals(""))
                continue;
            String[] strArray = str.split(" ");
            // to store the first number(x)
            BigDecimal x = new BigDecimal(strArray[0]);
            // to store the second number(y)
            BigDecimal y = new BigDecimal(strArray[1]);

            // initialize those four extremums at the first timeList
            // after the first time we will not enter this anymore
            if (firstRound) {
                xMin = x;
                xMax = x;
                yMin = y;
                yMax = y;
                firstRound = false;
            }
            if (x.compareTo(xMin)== -1) {
                xMin = x;
            }
            if (x.compareTo(xMax)== 1) {
                xMax = x;
            }
            if (y.compareTo(yMin)== -1) {
                yMin = y;
            }
            if (y.compareTo(yMax)== 1) {
                yMax = y;
            }
            Point tempPoint = new Point(x, y);
            // add a Point class
            ga.insertAtEnd(Point.class, tempPoint);
        }
        inputStream.close();
        bufferedRead.close();
        // close\\

        // assign the values to the array passed in
        result[0] = xMin;
        result[1] = xMax;
        result[2] = yMin;
        result[3] = yMax;

    }

    /**
     *  This function create all boundary values for the Coordinate System
     * so that later points could be compared to assign to each box by using
     *  these boundary values of x and y
     * @param xb a BigDecimal[] to store all the boundary values of x
     * @param yb a BigDecimal[] to store all the boundary values of y
     * @param Interval, if it is a n*n grid, then Interval here is n
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     */
    public static void createBox(BigDecimal[] xb, BigDecimal[] yb, int Interval, BigDecimal xMin, BigDecimal xMax, BigDecimal yMin, BigDecimal yMax) {
        
        BigDecimal xInterval = xMax.subtract(xMin).divide(new BigDecimal(Interval));
        BigDecimal yInterval = yMax.subtract(yMin).divide(new BigDecimal(Interval));

        for (int i = 0; i < xb.length; i++) {
            xb[i] = xMin.add(xInterval.multiply(new BigDecimal(i)));
        }
        for (int j = 0; j < yb.length; j++) {
            yb[j] = yMin.add(yInterval.multiply(new BigDecimal(j)));
        }
    }

    /**
     * given a point whichs X-axis is x, Y-axis is y, then do the comparison
     * to see it falls to which grid, then add one to that grid's number
     * @param countPoints: the Two-dimensional array passed in to calculate the number occurs in each grid
     * @param xb a BigDecimal[] passed in for x value comparison
     * @param yb a BigDecimal[] passed in for y value comparison
     * @param x X-axis value of a specific point
     * @param y Y-axis value of a specific point
     */
    public static void countNumOfPoints(int[][] countPoints, BigDecimal[] xb, BigDecimal[] yb, BigDecimal x, BigDecimal y) {
        int xIndex = 0, yIndex = 0;
        // System.out.println("Point: (" + x + " , " + y + ")");

        // to see it lies in which x range
        for (int i = xb.length - 1; i >= 0; i--) {
            if (x.compareTo(xb[i]) > -1 ) {
                xIndex = i;
                break;
            }
        }

        // to see it lies in which y range
        for (int j = yb.length - 1; j >= 0; j--) {
            if (y.compareTo(yb[j]) > -1) {
                yIndex = j;
                break;
            }
        }

        // that grid's number added one
        countPoints[xIndex][yIndex]++;
    }

    // print the final result table
    public static void showNumOfPoints(int[][] target, int colum) {
        ConsoleTable t = new ConsoleTable(colum, true);
        for (int i = 0; i < target.length; i++) {
            t.appendRow();
            for (int j = 0; j < target[i].length; j++) {
                t.appendColum(target[i][j]);
            }
        }
        System.out.println(t.toString());
    }

    public static void main(String[] args) {
        // default file path is specified here
        String filePath = ".\\convexhullpoints.dat";

        // if this is n*n grid, then here is n, default is 8
        int interval = 8;
        int[][] countPoints = new int[interval][interval];
        BigDecimal[] xb = new BigDecimal[interval];
        BigDecimal[] yb = new BigDecimal[interval];

        // initialize a Point growArray
        GrowArray<Point> pointArray = new GrowArray<Point>(Point.class, 20);
        Point[] pa = pointArray.rep();
        // finish the initialization

        BigDecimal[] minsAndMaxs = new BigDecimal[4];

        try {
            readFile(filePath, pointArray, minsAndMaxs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Got an exception: " + e.getMessage());
            e.printStackTrace();
        }

        // create x and y range boundaries
        createBox(xb, yb, interval, minsAndMaxs[0], minsAndMaxs[1], minsAndMaxs[2], minsAndMaxs[3]);


        // traverse all the points what we added to the GrowArray
        for (int i = 0; i < pointArray.size(); i++) {
            BigDecimal tempX = pointArray.get(i).getX();
            BigDecimal tempY = pointArray.get(i).getY();
            countNumOfPoints(countPoints, xb, yb, tempX, tempY);
        }

        // print out the table which includes the number of point in each box
        showNumOfPoints(countPoints,interval);
    }
}