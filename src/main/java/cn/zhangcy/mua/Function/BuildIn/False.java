package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MBoolean;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class False extends BuildInFunction {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        return new MBoolean(false);
    }
}
