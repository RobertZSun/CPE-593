import java.lang.reflect.Array;

/*
 * @Description: 
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun
 * @Date: 2019-10-22 00:22:14
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-10-22 21:15:13
 */
/*
 * @Description: 
 * @Author: Zhe Sun
 * @Github: https://github.com/RobertZSun
 * @Date: 2019-10-22 00:22:14
 * @LastEditors: Zhe Sun
 * @LastEditTime: 2019-10-22 10:11:14
 */
public class GrowArray<T> {
    private T[] data;
    private int used;

    public GrowArray(Class<T> type, int size) {
        this.data = (T[]) Array.newInstance(type, size);
        this.used = 0;
    }

    public T[] rep() {
        return data;
    }

    public int size() {
        return used;
    }

    public void extendArray(Class<T> type) {
        // T[] newArray = new T[2 * data.length];

        T[] newArray = (T[]) Array.newInstance(type, 2 * data.length);
        for (int i = 0; i < data.length; i++) {
            newArray[i] = data[i];
        }
        data = newArray;
    }

    public void shiftArray(int index) {
        for (int i = used - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
    }

    public void insertAtEnd(Class<T> type, T num) {
        if (used == data.length) {
            extendArray(type);
        }
        data[used++] = num;
    }

    public void insert(Class<T> type, int index, T num) {
        if (index == used) {
            insertAtEnd(type, num);
        }
        if (used == data.length) {
            extendArray(type);
        }
        shiftArray(index);
        data[index] = num;
        used++;
    }

    public T get(int index) {
        if (index > used) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    public void deleteAtEnd(int index) {
        used--;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < used; i++) {
            result += data[i].toString() + "\n";
        }
        return result;
    }
}