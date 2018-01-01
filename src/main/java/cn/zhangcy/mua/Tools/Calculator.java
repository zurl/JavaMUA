package cn.zhangcy.mua.Tools;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Value.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class Calculator {

    public interface Executor{
        int run(int a, int b);
        double run(double a, double b);
    }

    public interface Comparer{
        boolean run(int a, int b);
        boolean run(double a, double b);
        boolean run(String a, String b);
    }

    public static MNumber solve(MValue a, MValue b, Executor executor) throws Error {
        if( a instanceof MInteger && b instanceof MInteger)
            return new MInteger(executor.run(((MInteger)a).getValue(), ((MInteger)b).getValue()));
        else if( a instanceof MInteger && b instanceof MReal)
            return new MReal(executor.run(((MInteger)a).getValue(), ((MReal)b).getValue()));
        else if( a instanceof MReal && b instanceof MInteger)
            return new MReal(executor.run(((MReal)a).getValue(), ((MInteger)b).getValue()));
        else if( a instanceof MReal && b instanceof MReal)
            return new MReal(executor.run(((MReal)a).getValue(), ((MReal)b).getValue()));
        else
            throw new RuntimeError("Illegal Type to calculate");
    }

    public static MBoolean solve(MValue a, MValue b, Comparer comparer) throws Error {
        if( a instanceof MInteger && b instanceof MInteger)
            return new MBoolean(comparer.run(((MInteger)a).getValue(), ((MInteger)b).getValue()));
        else if( a instanceof MInteger && b instanceof MReal)
            return new MBoolean(comparer.run(((MInteger)a).getValue(), ((MReal)b).getValue()));
        else if( a instanceof MReal && b instanceof MInteger)
            return new MBoolean(comparer.run(((MReal)a).getValue(), ((MInteger)b).getValue()));
        else if( a instanceof MReal && b instanceof MReal)
            return new MBoolean(comparer.run(((MReal)a).getValue(), ((MReal)b).getValue()));
        else if( a instanceof MLiteral && b instanceof MInteger)
            return new MBoolean(comparer.run(((MLiteral)a).getValue(), Integer.toString(((MInteger)b).getValue())));
        else if( a instanceof MLiteral && b instanceof MReal)
            return new MBoolean(comparer.run(((MLiteral)a).getValue(), Double.toString(((MReal)b).getValue())));
        else if( a instanceof MLiteral && b instanceof MLiteral)
            return new MBoolean(comparer.run(((MLiteral)a).getValue(), ((MLiteral)b).getValue()));
        else
            throw new RuntimeError("Illegal Type to execute compare");
    }

}
