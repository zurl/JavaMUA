package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Tools.Calculator;
import cn.zhangcy.mua.Value.MNumber;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Mul extends BuildInFunction {

    private static Class[] argTypes = {MNumber.class, MNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        return Calculator.solve(args[0], args[1], new Calculator.Executor() {
            public int run(int a, int b) {
                return a * b;
            }

            public double run(double a, double b) {
                return a * b;
            }
        });
    }
}
