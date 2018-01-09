package cn.zhangcy.mua.Value;

import cn.zhangcy.mua.Function.CustomFunction;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class MList implements MValue {

    private ArrayList<MValue> value;

    private CustomFunction customFunction = null;

    public MList(ArrayList<MValue> value) {
        this.value = value;
        if(value.size() >= 2){
            MValue mValue1 = value.get(0);
            MValue mValue2 = value.get(1);
            if( mValue1 instanceof MList && mValue2 instanceof MList){
                boolean isFunc = true;
                for(MValue mValue : ((MList) mValue1).getValue()){
                    if(!(mValue instanceof MLiteral || mValue instanceof MWord)) isFunc = false;
                }
                if(isFunc){
                    customFunction = new CustomFunction(((MList) mValue1).getValue(), ((MList) mValue2).getValue(), this);
                }
            }
        }

    }

    public CustomFunction getCustomFunction() {
        return customFunction;
    }

    public ArrayList<MValue> getValue() {
        return value;
    }

    public void setValue(ArrayList<MValue> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(MValue mValue : value){
            stringBuilder.append(" ");
            stringBuilder.append(mValue.toString());
        }
        if(value.size() != 0)stringBuilder.append(" ");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String toRawString() {
        return toString();
    }
}
