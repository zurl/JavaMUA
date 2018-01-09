package cn.zhangcy.mua.Tools;

import java.util.LinkedList;

public class LRUCache <T, R> {

    static class CacheBlock <T, R> {

        private T key;
        private R value;
        private Integer counter;


        public CacheBlock(T key, R value) {
            this.key = key;
            this.value = value;
            this.counter = 1;
        }

        public T getKey() {
            return key;
        }

        public R getValue() {
            return value;
        }

        public void hit(){
            counter ++;
        }

    }

    private static final int SIZE = 100;

    private LinkedList<CacheBlock<T, R>> cacheBlocks = new LinkedList<CacheBlock<T, R>>();

    public R find(T key){
        for(CacheBlock<T, R> cacheBlock : cacheBlocks){
            if(cacheBlock.getKey() == key){
                cacheBlock.hit();
                cacheBlocks.remove(cacheBlock);
                cacheBlocks.addFirst(cacheBlock);
                return cacheBlock.getValue();
            }
        }
        return null;
    }

    public void hit(T key, R value){
        if( cacheBlocks.size() > SIZE){
            int cnt = Integer.MAX_VALUE;
            CacheBlock<T, R> result = null;
            for(CacheBlock<T, R> cacheBlock : cacheBlocks){
                if(cacheBlock.counter < cnt){
                    cnt = cacheBlock.counter;
                    result = cacheBlock;
                }
            }
            cacheBlocks.remove(result);
        }

        cacheBlocks.addFirst(new CacheBlock<T, R>(key, value));
    }

}
