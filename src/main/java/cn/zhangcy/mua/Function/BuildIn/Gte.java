package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Tools.Calculator;
import cn.zhangcy.mua.Value.MValue;

public class Gte extends BuildInFunction {

    private static Class[] argTypes = {MValue.class, MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        return Calculator.solve(args[0], args[1], new Calculator.Comparer() {
            public boolean run(int a, int b) {
                return a >= b;
            }

            public boolean run(double a, double b) {
                return a >= b;
            }

            public boolean run(String a, String b) {
                return a.compareTo(b) >= 0;
            }
        });
    }
}
