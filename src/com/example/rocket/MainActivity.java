package com.example.rocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button startRocket = (Button) findViewById(R.id.startRocket);
		Button endRocket = (Button) findViewById(R.id.endRocket);
		startRocket.setOnClickListener(this);
		endRocket.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startRocket:
			//开启小火箭服务
			startService(new Intent(this,RocketService.class));
			finish();
			break;
		case R.id.endRocket:
			//关闭小火箭服务
			stopService(new Intent(this,RocketService.class));
			finish();
			break;

		default:
			break;
		}
	}
}
