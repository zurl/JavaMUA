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
            ButFirst.class,
            ButLast.class,
            Div.class,
            Eq.class,
            Erall.class,
            Erase.class,
            Export.class,
            False.class,
            First.class,
            Gt.class,
            Gte.class,
            If.class,
            IfFalse.class,
            IfTrue.class,
            Int.class,
            IsBool.class,
            IsEmpty.class,
            IsList.class,
            IsName.class,
            IsNumber.class,
            IsWord.class,
            Item.class,
            Join.class,
            Last.class,
            List.class,
            Load.class,
            Lt.class,
            Lte.class,
            Make.class,
            Mod.class,
            Mul.class,
            Not.class,
            Or.class,
            Output.class,
            Poall.class,
            Print.class,
            Random.class,
            Read.class,
            ReadList.class,
            Repeat.class,
            Run.class,
            Save.class,
            Sentence.class,
            Sqrt.class,
            Stop.class,
            Sub.class,
            Test.class,
            Thing.class,
            True.class,
            Wait.class,
            Word.class,
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
