package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by zcy on 27/09/2017.
 */
public class ReadList implements BuildInFunction {

    private static Class[] argTypes = {};

    private static Pattern splitRegex =
            Pattern.compile("\\s+");

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        String[] words = splitRegex.split(value);
        MList mList = new MList(new ArrayList<MValue>());
        for(String word : words){
            try{
                mList.getValue().add(new MInteger(Integer.parseInt(word)));
            }
            catch (Exception e){
                try {
                    mList.getValue().add(new MReal(Double.parseDouble(word)));
                }catch (Exception ex){
                    mList.getValue().add(new MLiteral(word));
                }
            }
        }
        return mList;
    }
}

