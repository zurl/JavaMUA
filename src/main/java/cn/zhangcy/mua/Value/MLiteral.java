package cn.zhangcy.mua.Value;

/**
 * Created by zcy on 27/09/2017.
 */
public class MLiteral implements MValue{

    private String value;

    public MLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String toRawString() {
        return "\"" + toString();
    }
}
