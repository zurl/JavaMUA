package org.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Parser;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.SymbolTable;
import cn.zhangcy.mua.Tokenizer;
import cn.zhangcy.mua.Value.MReal;
import cn.zhangcy.mua.Value.MValue;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zcy on 27/09/2017.
 */
public class TestMain {

    private SymbolTable symbolTable = new SymbolTable();
    private Tokenizer tokenizer = new Tokenizer();
    private Runtime runtime = new Runtime(tokenizer, symbolTable);

    private String test(String source) throws Error{
        tokenizer.cleanUp();
        return runtime.run(tokenizer.tokenize(source, false), 1).toString();
    }
    private MValue testRaw(String source) throws Error{
        tokenizer.cleanUp();
        return runtime.run(tokenizer.tokenize(source, false), 1);
    }

    @Test
    public void testValue() throws Error{
        Assert.assertEquals(test("1"), "1");
        Assert.assertEquals(test("\"hello"), "hello");
        Assert.assertEquals(test("true"), "true");
        Assert.assertEquals(test("[ 1 2 3 ]"), "[ 1 2 3 ]");
        Assert.assertEquals(test("[ [ 1 2 ] [ 3 4 ] ]"), "[ [ 1 2 ] [ 3 4 ] ]");
    }

    @Test
    public void testCalculation() throws Error{
        Assert.assertEquals(test("add 1 2"), "3");
        Assert.assertEquals(test("sub 1 2"), "-1");
        Assert.assertEquals(test("mul 1 2"), "2");
        Assert.assertEquals(test("div 4 2"), "2");
        Assert.assertEquals(test("mod 4 2"), "0");
        Assert.assertEquals(((MReal)testRaw("sqrt 4")).getValue(), 2, 1e-6);
        Assert.assertNotEquals(test("random 1024"), "1025");
    }

    @Test
    public void testCompare() throws Error{
        Assert.assertEquals(test("gt 1 2"), "false");
        Assert.assertEquals(test("eq \"abc \"abc"), "true");
        Assert.assertEquals(test("eq 1.1 1.1"), "true");
        Assert.assertEquals(test("lt \"a \"b"), "true");
    }

    @Test
    public void testLogic() throws Error{
        Assert.assertEquals(test("and true false"), "false");
        Assert.assertEquals(test("or true true"), "true");
        Assert.assertEquals(test("not true"), "false");
    }

    @Test
    public void testIs() throws Error{
        Assert.assertEquals(test("isempty [ ]"), "true");
        Assert.assertEquals(test("isbool 1"), "false");
        Assert.assertEquals(test("isnumber 1.1"), "true");
        Assert.assertEquals(test("islist [ [ ] ]"), "true");
        Assert.assertEquals(test("isword \"a"), "true");
        Assert.assertEquals(test("isempty [ 1 ]"), "false");
        Assert.assertEquals(test("isbool true"), "true");
        Assert.assertEquals(test("isnumber \"aaa"), "false");
        Assert.assertEquals(test("islist \"a[a"), "false");
        Assert.assertEquals(test("isword 1"), "false");
    }

    @Test
    public void testList() throws Error{
        Assert.assertEquals(test("first [ 1 2 3 ]"), "1");
        Assert.assertEquals(test("last [ 1 2 3 ]"), "3");
        Assert.assertEquals(test("butfirst [ 1 2 3 ]"), "[ 2 3 ]");
        Assert.assertEquals(test("butlast [ 1 2 3 ]"), "[ 1 2 ]");
        Assert.assertEquals(test("list [ 1 ] [ 2 ]"), "[ 1 2 ]");
        Assert.assertEquals(test("join [ 1 ] [ 2 ]"), "[ 1 [ 2 ] ]");
        Assert.assertEquals(test("item 2 [ 2 4 6 8 ]"), "6");
    }

    @Test
    public void testWord() throws Error{
        Assert.assertEquals(test("first \"abc"), "a");
        Assert.assertEquals(test("last \"abc"), "c");
        Assert.assertEquals(test("butfirst \"abc"), "bc");
        Assert.assertEquals(test("butlast \"abc"), "ab");
        Assert.assertEquals(test("word \"abc \"abc"), "abcabc");
        Assert.assertEquals(test("item 2 \"abc"), "c");
    }

    @Test
    public void testVar() throws Error{
        Assert.assertEquals(test("make \"a 1 thing \"a"), "1");
        Assert.assertEquals(test("make \"a [ 1 ] thing \"a"), "[ 1 ]");
        Assert.assertEquals(test("make \"a 1 erase \"a isname \"a"), "false");
    }

    @Test
    public void testControl() throws Error{
        runtime.loadPresetCode();
        test("test true iftrue [ make \"b 1 ]");
        Assert.assertEquals(test("thing \"b"), "1");
        test("test false iftrue [ make \"b 2 ]");
        Assert.assertEquals(test("thing \"b"), "1");
        test("test true iffalse [ make \"b 3 ]");
        Assert.assertEquals(test("thing \"b"), "1");
        test("test false iffalse [ make \"b 4 ]");
        Assert.assertEquals(test("thing \"b"), "4");
        test("make \"a 1 run [ make \"a add thing \"a 1 ]");
        Assert.assertEquals(test("thing \"a"), "2");
        test("make \"a 1 repeat 4 [ make \"a add thing \"a 1 ]");
        Assert.assertEquals(test("thing \"a"), "5");
        test("make \"run [ [ \"a ] [ thing \"a ] ]");
        Assert.assertEquals(test("run [ make \"a add thing \"a 1 ]"), "[ make a add thing a 1 ]");
    }

    @Test
    public void testExpr() throws Error{
        Assert.assertEquals(test("1 + 1"), "2");
        Assert.assertEquals(test("2 * (2+2*3)"), "16");
        Assert.assertEquals(test("1+6/3%2+1"), "2");
        test("make \"a 4 ");
        test("make \"b 5 ");
        Assert.assertEquals(test(":a+:b"), "9");
        test("make \"double [ [ \"x ] [ output 2 * :x ] ]");
        Assert.assertEquals(test("double double 2 * double double 2"), "64");
        Assert.assertEquals(test("double double 2 + double double 2"), "40");
        Assert.assertEquals(test(":a+:a+:a+:a"), "16");
    }
}
