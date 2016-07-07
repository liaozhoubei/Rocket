package com.example.rocket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class BackGroundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_background);
		
		ImageView smoke_m = (ImageView) findViewById(R.id.smoke_m);
		ImageView smoke_t = (ImageView) findViewById(R.id.smoke_t);
		// 设置透明渐变动画
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(200);
		smoke_m.startAnimation(alphaAnimation);
		smoke_t.startAnimation(alphaAnimation);
		// 活动开启一秒后销毁活动
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
			}
		}, 1000);
	}
}
