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
 * Created by zcy on 27/09/2017.
 */
public class Read implements BuildInFunction {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.next();
        try{
            return new MInteger(Integer.parseInt(value));
        }
        catch (Exception e){
            try {
                return new MReal(Double.parseDouble(value));
            }catch (Exception ex){
                return new MLiteral(value);
            }
        }
    }
}
