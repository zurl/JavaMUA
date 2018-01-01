package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class IsEmpty implements BuildInFunction {

    private static Class[] argTypes = {MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        if( args[0] instanceof MLiteral ) return new MBoolean(((MLiteral)args[0]).getValue().equals(""));
        else if( args[0] instanceof MList )return new MBoolean(((MList)args[0]).getValue().size() == 0);
        else throw new RuntimeError("Illegal Type");
    }
}

