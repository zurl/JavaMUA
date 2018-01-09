package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MValue;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class Repeat extends BuildInFunction {

    private static Class[] argTypes = {MInteger.class, MList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        return ctx.run(((MList)args[1]).getValue(), ((MInteger)args[0]).getValue());
    }
}
