package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MReal;
import cn.zhangcy.mua.Value.MValue;

import java.util.Scanner;

/**
 * Created by zcy on 22/11/2017.
 */
public class MValueFunction extends BuildInFunction {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    MValue value;

    public MValueFunction(MValue value) {
        this.value = value;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        return value;
    }
    
}