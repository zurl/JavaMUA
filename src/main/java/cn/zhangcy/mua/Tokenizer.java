package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.SyntaxError;
import cn.zhangcy.mua.Value.*;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by zcy on 27/09/2017.
 */
public class Tokenizer {

    private static Pattern commentRegex =
            Pattern.compile("//.*$");


    private static Pattern splitRegex =
            Pattern.compile("\\s+");

    static boolean isInteger(String word){
        try{
            Integer v = Integer.valueOf(word);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    static boolean isReal(String word){
        try{
            Double v = Double.valueOf(word);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    ArrayList<MValue> tokens = new ArrayList<MValue>();
    Stack<ArrayList<MValue>> listStack = new Stack<ArrayList<MValue>>();

    public void cleanUp(){
        tokens = new ArrayList<MValue>();
        listStack = new Stack<ArrayList<MValue>>();
    }

    public String cleanString(String str){
        StringBuilder sb = new StringBuilder();
        boolean onLiteral = false;
        for(int i = 0; i < str.length(); i++){
            char x = str.charAt(i);
            if( onLiteral ){
                if( x == ' ' || x == '\t' || x == '\n' || x == '\r' ){
                    onLiteral = false;
                }
                sb.append(x);
            }
            else{
                if( x == '+' || x == '-' || x == '*' || x == '/' || x == '%'
                   || x == '(' || x == ')' || x == '[' || x == ']'|| x == '='
                        ){
                    sb.append(' ');
                    sb.append(x);
                    sb.append(' ');
                }
                else if( x == '>' || x == '<' ){
                    sb.append(' ');
                    if( i + 1 < str.length() && str.charAt(i + 1) == '='){
                        sb.append(' ');
                        sb.append('=');
                        i++;
                    }
                    else{
                        sb.append(x);
                    }
                    sb.append(' ');
                }
                else{
                    sb.append(x);
                    if( x == '"') onLiteral = true;
                }
            }
        }
        return sb.toString();
    }

    public ArrayList<MValue> tokenize(String source, boolean isMultiline)
            throws Error{
        String programBody = commentRegex.matcher(source).replaceAll("\n");
        String wellBody = cleanString(programBody);
        String[] words = splitRegex.split(wellBody);
        for(String word : words){
            if( word.equals("") ) continue;
            if( word.charAt(0) == '+' || word.charAt(0) == '-' || word.charAt(0) == '*' || word.charAt(0) == '/'
                    || word.charAt(0) == '=' || word.charAt(0) == '%' || word.charAt(0) == '(' || word.charAt(0) == ')' ){
                tokens.add(new MOpe(word.charAt(0)));
            }
            else if( word.charAt(0) == '<' || word.charAt(0) == '>' ){
                if( word.length() >= 2 && word.charAt(1) == '='){
                    if(word.charAt(0) == '<') tokens.add(new MOpe(MOpe.LTE));
                    else if(word.charAt(0) == '>') tokens.add(new MOpe(MOpe.GTE));
                }
                else{
                    tokens.add(new MOpe(word.charAt(0)));
                }
            }
            else if( word.charAt(0) == '\'' || word.charAt(0) == '\"'){
                tokens.add(new MLiteral(word.substring(1)));
            }
            else if(word.charAt(0) == '['){
                listStack.push(tokens);
                tokens = new ArrayList<MValue>();
            }
            else if(word.charAt(0) == ']'){
                if( listStack.isEmpty() ) throw new SyntaxError(" Unmatched Brackets.");
                ArrayList<MValue> lastList = listStack.pop();
                lastList.add(new MList(tokens));
                tokens = lastList;
            }
            else if(word.charAt(0) == ':'){
                tokens.add(new MWord("thing"));
                tokens.add(new MLiteral(word.substring(1)));
            }
            else if(isInteger(word)){
                tokens.add(new MInteger(Integer.parseInt(word)));
            }
            else if(isReal(word)){
                tokens.add(new MReal(Double.parseDouble(word)));
            }
            else {
                tokens.add(new MWord(word));
            }
        }
        if(!listStack.isEmpty() ){
            if( isMultiline ) return null;
            else throw new SyntaxError("Unmatched Brackets");
        }
        return tokens;
    }



}
