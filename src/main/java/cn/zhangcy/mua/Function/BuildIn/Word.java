package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class Word implements BuildInFunction {

    private static Class[] argTypes = {MLiteral.class, MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        String word = ((MLiteral)args[0]).getValue();
        if( args[1] instanceof MInteger || args[1] instanceof MReal || args[1] instanceof MBoolean){
            return new MLiteral(word + args[1].toString());
        }
        else if(args[1] instanceof MLiteral ){
            return new MLiteral( word + ((MLiteral)args[1]).getValue());
        }
        else throw new RuntimeError("The type of arguments of function `word` is incorrect.");
    }
}
