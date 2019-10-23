import java.math.BigDecimal;

/*
 * @Description: 
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun
 * @Date: 2019-10-22 10:18:32
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-10-22 20:42:24
 */
public class Point {
    private BigDecimal x = null;
    private BigDecimal y = null;
    
    public Point(BigDecimal i, BigDecimal j) {
        this.x = i;
        this.y = j;
        // System.out.println("new point: (" +i + " , " +j + ")");
    }
    public String toString() {
        String str = "(" + x + " , " + y + ")";
        return str;
    }

    public BigDecimal getX() { return this.x; }
    public BigDecimal getY() { return this.y; }
}