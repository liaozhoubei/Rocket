package com.example.rocket;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class RocketService extends Service {

	private int widthPixels;
	private int heightPixels;
	private WindowManager.LayoutParams params;
	private WindowManager windowManager;
	private View view;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		// 获取窗口服务
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		// 用于获取手机屏幕大小
		DisplayMetrics outMetrics= new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		// 获取手机屏幕宽度和高度
		widthPixels = outMetrics.widthPixels;
		heightPixels = outMetrics.heightPixels;
		// 获取小火箭的视图
		view = View.inflate(getApplicationContext(), R.layout.rocket, null);
		ImageView iv_rocket = (ImageView)view.findViewById(R.id.iv_rocket);
		// 设置小火箭在窗口中的参数
		params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		params.format = PixelFormat.TRANSLUCENT;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ;
		// 设置小火箭在窗口中的位置
		params.gravity = Gravity.LEFT | Gravity.TOP;
		// 开启小火箭的帧动画
		AnimationDrawable animationDrawable  = (AnimationDrawable) iv_rocket.getBackground();
		animationDrawable.start();
		// 把小火箭加载到窗口中去
		windowManager.addView(view, params );
		setTouch();
	}
	// 在Handler中进行火箭发射时的更新
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			params.y -= 10;
			windowManager.updateViewLayout(view, params);
		};
	};
	// 设置小火箭的滑动事件
	private void setTouch() {
		view.setOnTouchListener(new OnTouchListener() {
			
			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 获得手指按下时的坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
					
				case MotionEvent.ACTION_MOVE:
					// 获得手指移动后的坐标
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					// 计算手指移动的距离
					int dx = newX - startX;
					int dy = newY - startY;
					// 设置小火箭移动的距离
					params.x += dx;
					params.y += dy;
					// 设置小火箭的高度不能超出屏幕
					if (params.y < 0) {
						params.y = 0;
					}
					// 实时更新小火箭的距离
					windowManager.updateViewLayout(view, params);
					// 重新为手指开始时的位置赋值
					startX = newX;
					startY = newY;
					
					
					break;
					
				case MotionEvent.ACTION_UP:
					// 小火箭位于窗口下面和中间的时候能发射
					if (params.y > 290 && params.x > 100 && params.x < 200) {
						sendRocket();
						// 打开烟雾效果
						Intent intent = new Intent(RocketService.this, BackGroundActivity.class);
						// 设置BackGroundActivity的任务栈
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}
					
					break;

				default:
					break;
				}
				
				return true;
			}

			// 每个200毫秒发送一个空消息更新小火箭的位置
			private void sendRocket() {
				for(int i = 0; i < 45; i ++) {
					new Thread(){
						public void run() {
							SystemClock.sleep(200);
							handler.sendEmptyMessage(0);
						};
					}.start();;
					
				}				
			}
		});
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		// 服务停止时销毁小火箭
		if (view != null && windowManager != null){
			windowManager.removeView(view);
		}
	}
	

}
