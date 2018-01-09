package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MNull;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Make extends BuildInFunction {

    private static Class[] argTypes = {MLiteral.class, MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        ctx.getSymbolTable().setSymbol(((MLiteral)args[0]).getValue(), args[1]);
        return new MNull();
    }


}
