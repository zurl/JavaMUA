package cn.zhangcy.mua.Value;

import cn.zhangcy.mua.Function.Function;

/**
 * Created by zcy on 27/09/2017.
 */
public class MFunction implements MValue{

    private Function function;

    public MFunction(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "[Function]";
    }

    public String toRawString() {
        return toString();
    }
}
