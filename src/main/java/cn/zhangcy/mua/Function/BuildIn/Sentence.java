package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MValue;

import java.util.ArrayList;

public class Sentence extends BuildInFunction {

    private static Class[] argTypes = {MList.class, MList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        MList value = new MList(new ArrayList<MValue>());
        value.getValue().addAll(((MList)args[0]).getValue());
        value.getValue().addAll(((MList)args[1]).getValue());
        return value;
    }
}

