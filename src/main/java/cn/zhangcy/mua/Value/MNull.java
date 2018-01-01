package cn.zhangcy.mua.Value;

/**
 * Created by zcy on 27/09/2017.
 */
public class MNull implements MValue {

    @Override
    public String toString() {
        return "null";
    }

    public String toRawString() {
        return toString();
    }
}
