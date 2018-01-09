package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/11/2017.
 */
public class Run extends BuildInFunction {

    private static Class[] argTypes = {MList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        return ctx.run(((MList)args[0]).getValue(), 1);
    }
}

