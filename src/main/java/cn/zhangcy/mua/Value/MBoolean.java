package cn.zhangcy.mua.Value;

/**
 * Created by zcy on 27/09/2017.
 */
public class MBoolean implements MValue {

    private boolean value;

    public MBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    public String toRawString() {
        return toString();
    }
}
