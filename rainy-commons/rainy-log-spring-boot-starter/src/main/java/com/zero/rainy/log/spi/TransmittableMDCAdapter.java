package com.zero.rainy.log.spi;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.helpers.ThreadLocalMapOfStacks;
import org.slf4j.spi.MDCAdapter;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


/**
 * Mapped Diagnostic Context, MDC是为了每个线程维护独立的上下文信息机制。
 * 自定义 MDC 适配器，替换Logback默认的 LogbackMDCAdapter, 将默认的 ThreadLocal 更改为 {@link TransmittableThreadLocal}
 * 实现 traceId 在父子线程之间的传递.
 *
 * @author Zero.
 * <p> Created on 2024/8/31 21:32 </p>
 */
public class TransmittableMDCAdapter implements MDCAdapter {
    /**
     * 线程上下文数据容器
     */
    private final ThreadLocal<Map<String, String>> copyOnInheritThreadLocal = new TransmittableThreadLocal<>();
    /**
     * 双端队列，支持在日志上下文中以堆栈的形式存储多个值
     */
    private final ThreadLocalMapOfStacks threadLocalMapOfStacks = new ThreadLocalMapOfStacks();

    /**
     * 记录上一次操作是读取(复制)还是写入
     */
    private final ThreadLocal<Integer> lastOperation = new ThreadLocal<>();
    private static final int WRITE_OPERATION = 1;
    private static final int READ_OPERATION = 2;

    /**
     * 单例模式
     */
    private TransmittableMDCAdapter(){}
    private static final TransmittableMDCAdapter INSTANCE;
    static {
        INSTANCE = new TransmittableMDCAdapter();
    }
    public static MDCAdapter getInstance() {
        return INSTANCE;
    }


    /**
     * 将参数放入当前线程上下文
     */
    @Override
    public void put(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        // 获取旧的上下文数据
        Map<String, String> oldContext = copyOnInheritThreadLocal.get();
        // 记录本次操作，并获取上次操作
        Integer lastOp = getAndSetLastOperation(WRITE_OPERATION);
        if (wasLastOpReadOrNull(lastOp) || oldContext == null){
            Map<String, String> context = buildContextByOldContext(oldContext);
            context.put(key, value);
        }else {
            oldContext.put(key, value);
        }
    }

    /**
     * 从当前线程上下文中获取指定的数据
     * @param key   key
     */
    @Override
    public String get(String key) {
        Map<String, String> context = getContext();
        if ((context != null) && (key != null)){
            return context.get(key);
        }
        return null;
    }

    /**
     * 从上下文中删除指定的映射
     * @param key   要删除的key
     */
    @Override
    public void remove(String key) {
        if (key == null) {
            return;
        };
        Map<String, String> oldCtx = copyOnInheritThreadLocal.get();
        if (oldCtx == null) {
            return;
        };
        Integer lastOp = getAndSetLastOperation(WRITE_OPERATION);
        if (wasLastOpReadOrNull(lastOp)){
            Map<String, String> context = buildContextByOldContext(oldCtx);
            context.remove(key);
        }else {
            oldCtx.remove(key);
        }
    }

    /**
     * 清除当前线程上下文数据
     */
    @Override
    public void clear() {
        lastOperation.set(WRITE_OPERATION);
        copyOnInheritThreadLocal.remove();
    }

    /**
     * 获取当前线程上下文数据的副本，可能为空。
     */
    @Override
    public Map<String, String> getCopyOfContextMap() {
        lastOperation.set(READ_OPERATION);
        Map<String, String> ctx = copyOnInheritThreadLocal.get();
//        Map<String, String> ctx = getContext();
        if (ctx == null) {
            return null;
        }
        return new HashMap<>(ctx);
    }

    @Override
    public void setContextMap(Map<String, String> ctx) {
        lastOperation.set(WRITE_OPERATION);
        Map<String, String> newCtx = Collections.synchronizedMap(new HashMap<>(ctx));
        newCtx.putAll(ctx);
        copyOnInheritThreadLocal.set(newCtx);
    }

    @Override
    public void pushByKey(String key, String value) {
        this.threadLocalMapOfStacks.pushByKey(key, value);
    }

    @Override
    public String popByKey(String key) {
        return this.threadLocalMapOfStacks.popByKey(key);
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String key) {
        return this.threadLocalMapOfStacks.getCopyOfDequeByKey(key);
    }

    @Override
    public void clearDequeByKey(String key) {
        this.threadLocalMapOfStacks.clearDequeByKey(key);
    }

    /**
     * 记录本次操作类型，并且获取上一次操作类型
     * @param operation 本次操作类型
     * @return          上次操作类型
     */
    private Integer getAndSetLastOperation(Integer operation){
        Integer lastOp = lastOperation.get();
        lastOperation.set(operation);
        return lastOp;
    }

    /**
     * 判断是否为读取操作类型，或者为Null
     */
    private static boolean wasLastOpReadOrNull(Integer op){
        return op == null || op == READ_OPERATION;
    }

    /**
     * 创建一个新的上下文容器，并将旧的上下文数据拷贝到新的容器中
     * @param oldContext    旧的上下文容器
     * @return              新的上下文容器
     */
    private Map<String, String> buildContextByOldContext(Map<String, String> oldContext){
        Map<String, String> newContext = Collections.synchronizedMap(new HashMap<>());
        if (oldContext != null){
            synchronized (oldContext){
                newContext.putAll(oldContext);
            }
        }
        copyOnInheritThreadLocal.set(newContext);
        return newContext;
    }

    /**
     * 获取当前线程上下文
     */
    private Map<String, String> getContext(){
        lastOperation.set(READ_OPERATION);
        return copyOnInheritThreadLocal.get();
    }
}