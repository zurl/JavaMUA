package cn.zhangcy.mua.Value;

/**
 * Created by zcy on 27/09/2017.
 */
public class MInteger implements MNumber {

    private int value;

    public MInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public String toRawString() {
        return toString();
    }
}
