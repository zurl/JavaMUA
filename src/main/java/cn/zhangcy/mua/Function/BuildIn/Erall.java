package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class Erall extends BuildInFunction {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        ctx.getSymbolTable().cleanAll();
        return new MNull();
    }
}
