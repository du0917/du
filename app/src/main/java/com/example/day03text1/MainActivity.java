package com.example.day03text1;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 跳过
     */
    private Button mBtJump;
    private ImageView mImage;
    private TextView mTvJump;
    private boolean b=true;
    private int A=4;
    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (A>0&&b){
               mTvJump.setText(A+"");
                Message message = mHandler.obtainMessage(1);
                mHandler.sendMessageDelayed(message,1000);
                 A--;
            }else {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtJump = (Button) findViewById(R.id.bt_jump);
        mBtJump.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.image);
        mTvJump = (TextView) findViewById(R.id.tvJump);

        // 平移动画
        ObjectAnimator transX = ObjectAnimator.ofFloat(mImage, "translationX", 0f,
                300f);
        transX.setDuration(2000);
        transX.setRepeatCount(5);
        transX.setRepeatMode(ObjectAnimator.RESTART);
        transX.start();
        // 旋转动画
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mImage, "rotation", 0f,
                360f);
        rotation.setDuration(1000);
        rotation.setRepeatCount(7);
        rotation.setRepeatMode(ObjectAnimator.RESTART);
        rotation.start();
        // 缩放动画
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImage, "scaleY", 0f, 1.6f);
        scaleY.setDuration(1000);
        scaleY.setRepeatCount(5);
        scaleY.setRepeatMode(ObjectAnimator.RESTART);
        scaleY.start();
//创建渐变动画   透明度变化，表示从0.1f 到 1.0 的变化（其中0.0f表示完全透明，1.0表示完全不透明）
        Animation animation = new AlphaAnimation(0.1f,1.0f);
        animation.setDuration(3000);//动画的持续的时间
        animation.setRepeatCount(2);//动画的重复次数
        mImage.startAnimation(animation);//开始动画
        Message message = mHandler.obtainMessage(1);
        mHandler.sendMessageDelayed(message,1000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_jump:

                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                //移除所有的任务和消息
                mHandler.removeCallbacksAndMessages(null);
                finish();
                break;
        }
    }
}
