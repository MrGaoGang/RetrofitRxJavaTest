package mrgao.com.retrofitText;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;
import mrgao.com.retrofitText.bus.RxBus;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.show)
    TextView show;
    @BindView(R.id.button)
    Button button;


    @BindString(R.string.app_name)
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        show.setText("没有数据");
        registerRxBus();
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:35
     * MethodName:
     * Des：点击进入第二个界面
     * Params：
     * Return:
     **/
    @OnClick(R.id.button)
    public void show() {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:36
     * MethodName:
     * Des：注册时间总线
     * Params：
     * Return:
     **/
    private void registerRxBus() {
        RxBus.getInstance().observe(AndroidBeans.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<AndroidBeans>() {
                    @Override
                    public void onNext(AndroidBeans beans) {
                        show.setText("获取到的数据是" + beans.getResults().get(0).getDesc());
                    }

                    @Override
                    public void onError(Throwable t) {
                        show.setText("获取数据失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
