package cn.zhangcy.mua.Function;

import java.util.HashMap;

import cn.zhangcy.mua.Value.MFunction;
import cn.zhangcy.mua.Value.MValue;
import cn.zhangcy.mua.Function.BuildIn.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class BuildInFunctionLoader {

    private static Class[] functions = {
            Add.class,
            And.class,
            Div.class,
            Eq.class,
            Erase.class,
            Gt.class,
            IsName.class,
            Read.class,
            ReadList.class,
            Print.class,
            Mod.class,
            Mul.class,
            Not.class,
            Or.class,
            Lt.class,
            True.class,
            False.class,
            Sub.class,
            Make.class,
            Thing.class,
            ButFirst.class,
            ButLast.class,
            First.class,
            IfFalse.class,
            IfTrue.class,
            IsBool.class,
            IsEmpty.class,
            IsList.class,
            IsNumber.class,
            IsWord.class,
            Item.class,
            Join.class,
            Last.class,
            List.class,
            Random.class,
            Test.class,
            Word.class,
            Sqrt.class,
            Repeat.class,
            Stop.class,
            Output.class,
            If.class,
            Run.class
    };

    public static void load(HashMap<String, MValue> symbolTable){
        try {
            for (Class x : functions) {
                symbolTable.put(x.getSimpleName().toLowerCase(), new MFunction((Function)x.newInstance()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
