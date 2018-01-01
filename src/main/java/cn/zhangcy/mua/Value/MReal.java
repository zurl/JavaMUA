package cn.zhangcy.mua.Value;

/**
 * Created by zcy on 27/09/2017.
 */
public class MReal implements MNumber {

    private double value;

    public MReal(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    public String toRawString() {
        return toString();
    }
}
