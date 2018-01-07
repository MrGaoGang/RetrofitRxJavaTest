package mrgao.com.retrofitText;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mrgao.com.retrofitText.bus.RxBus;
import mrgao.com.retrofitText.retrofit.RetrofitUtils;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.show)
    TextView show;
    @BindView(R.id.getBtn)
    Button getBtn;
    @BindView(R.id.backBtn)
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

    }

    /**
    *Author: MrGao
    *CreateTime: 2018/1/7 13:54
    *MethodName:
    *Des：获取到数据
    *Params：
    *Return:
    **/
    private void initData() {

        RetrofitUtils.getInstance()
                .create(AndroidAPI.class)
                .getAndroidAPI(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //数据的过滤
                .map(new Function<AndroidBeans, String>() {

                    @Override
                    public String apply(AndroidBeans beans) throws Exception {
                        if (beans.getResults() != null && beans.getResults().size() != 0) {
                            //获取到数据后，通知有数据的变化
                            RxBus.getInstance().post(beans);
                            return beans.getResults().get(0).getDesc();
                        } else {
                            return "No data";
                        }
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        show.setText("获取到的数据是:" + value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        show.setText("获取数据失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.getBtn, R.id.backBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getBtn:
                initData();
                break;
            case R.id.backBtn:
                finish();
                break;
        }
    }
}
