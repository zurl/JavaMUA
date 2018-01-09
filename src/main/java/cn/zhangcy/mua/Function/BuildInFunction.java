package cn.zhangcy.mua.Function;

/**
 * Created by zcy on 27/09/2017.
 */
public abstract class BuildInFunction implements Function {

    public String getName() {
        return "BuildIn: " + this.getClass().getSimpleName();
    }

}
