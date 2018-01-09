package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class Erase extends BuildInFunction {

    private static Class[] argTypes = {MLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        ctx.getSymbolTable().removeSymbol(((MLiteral)args[0]).getValue());

        return new MNull();
    }
}

