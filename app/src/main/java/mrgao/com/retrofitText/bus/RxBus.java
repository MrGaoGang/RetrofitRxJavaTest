package mrgao.com.retrofitText.bus;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by mr.gao on 2018/1/7.
 * Package:    mrgao.com.androidtest.bus
 * Create Date:2018/1/7
 * Project Name:AndroidTest
 * Description:
 */

public class RxBus {


    private static volatile RxBus mBus;
    private FlowableProcessor<Object> mProcessor;//有背压的事件总线

    private RxBus() {
        mProcessor = PublishProcessor.create().toSerialized();
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:39
     * MethodName:
     * Des：单例设计模式
     * Params：
     * Return:
     **/
    public static RxBus getInstance() {
        if (mBus == null) {
            synchronized (RxBus.class) {
                if (mBus == null) {
                    mBus = new RxBus();
                }
            }
        }
        return mBus;
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:42
     * MethodName:
     * Des：发送数据
     * Params：
     * Return:
     **/
    public void post(Object object) {
        if (mProcessor.hasSubscribers()) {
            mProcessor.onNext(object);
        }
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:43
     * MethodName:
     * Des：注册观察者
     * Params：
     * Return:
     **/
    public <T> Flowable<T> observe(Class<T> tClass) {
        return mProcessor.ofType(tClass);
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:46
     * MethodName:
     * Des：得到一个Observe对象
     * Params：
     * Return:
     **/
    public Flowable<Object> observe() {
        return mProcessor;
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:46
     * MethodName:
     * Des：取消所有的观察者,将所有的设置为监听结束，那么久无法得到监听了
     * Params：
     * Return:
     **/
    public void unObserveAll() {
        mProcessor.onComplete();
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:48
     * MethodName:
     * Des：是否有观察者
     * Params：
     * Return:
     **/
    public boolean hasSubstribers() {
        return mProcessor.hasSubscribers();
    }

}
