package cn.zhangcy.mua.Value;

/**
 * Created by zcy on 22/11/2017.
 */
public class MOpe implements MValue {

    public static final char LTE = 'a';
    public static final char GTE = 'b';

    public char value;

    public MOpe(char value) {
        this.value = value;
    }

    public String toRawString() {
        return toString();
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}
