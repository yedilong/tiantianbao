package com.example.tiantianbao;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		Intent();
		
	}
	
	/*
	 * ͨ���߳�ʵ����logoҳ��ͣ��2��
	 */
	public void Intent(){
		final Handler h = new Handler(){ // final �����Ըı�
			/*
			 * ���̲߳��ܶ�view���в�����Ҫ
			 */
			@Override
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				Intent in = new Intent(LogoActivity.this, MainActivity.class);
				startActivity(in);
				LogoActivity.this.finish();// �ݻٵ�ǰҳ��			
			}
		};
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg =new Message();
				h.handleMessage(msg);
			}
		});
		th.start();
	}

}
