package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MBoolean;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MNull;
import cn.zhangcy.mua.Value.MValue;

public class Export extends BuildInFunction {

    private static Class[] argTypes = {MLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        String name = ((MLiteral) args[0]).getValue();
        MValue mValue = ctx.getSymbolTable().getSymbol(name);
        if( mValue == null ){
            throw new RuntimeError(((MLiteral) args[0]).getValue() + " is undefined." );
        }
        ctx.getSymbolTable().setGlobalSymbol(name, mValue);
        return new MNull();
    }
}
