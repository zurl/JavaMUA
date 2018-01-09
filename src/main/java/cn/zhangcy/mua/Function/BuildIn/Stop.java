package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.StopInterrupt;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MNumber;
import cn.zhangcy.mua.Value.MReal;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Stop extends BuildInFunction {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        throw new StopInterrupt("");
    }
}

