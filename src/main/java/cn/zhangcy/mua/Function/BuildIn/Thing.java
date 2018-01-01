package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Thing implements BuildInFunction {

    private static Class[] argTypes = {MLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        MValue value = ctx.getSymbolTable().getSymbol(((MLiteral) args[0]).getValue());
        if( value == null ) throw new RuntimeError("`" + ((MLiteral) args[0]).getValue() + "` is undefined .");
        return value;
    }

}

