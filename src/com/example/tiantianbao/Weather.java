package com.example.tiantianbao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format.Field;

import com.google.gson.Gson;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;
public class Weather extends Fragment {
	TextView wendu;// 当天温度
	TextView tianqi;//  当天天气
	TextView news;//  单天的信息
	TextView time;//  更新时间
	TextView ciryName;// 城市名字
	TextView week_1_time;// 未来四天的时间
	TextView week_2_time;//
	TextView week_3_time;//
	TextView week_4_time;//
	TextView week_1_tinaqi;// 未来四天的天气
	TextView week_2_tinaqi;//
	TextView week_3_tinaqi;//
	TextView week_4_tinaqi;//
	TextView week_1_wendu;//未来四天的温度
	TextView week_2_wendu;//
	TextView week_3_wendu;//
	TextView week_4_wendu;//
	String cityId;
	String[] weeks = new String[]{"星期一", "星期二","星期三","星期四","星期五","星期六",
	"星期天"};
	ImageView refresh ;// 刷新
	ImageView add ;
	WeatherBean wb ;
	WeatherInfo wi;
	public Weather(String crityId){
		super();
		this.cityId = crityId;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.weather, null);

		return v;
	}

	/*
	 *  view 碎片  
	 * @see android.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		setView(view);
		obtainData();
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClick_add(v);
			}
		});
	}
	/*
	 * 获取组件
	 */
	private void  setView(View v){
		wendu = (TextView) v.findViewById(R.id.wendu);
		tianqi = (TextView) v.findViewById(R.id.tianqi);
		news = (TextView) v.findViewById(R.id.news);
		time = (TextView) v.findViewById(R.id.time);
		ciryName = (TextView) v.findViewById(R.id.ciryName );
		week_1_time = (TextView) v.findViewById(R.id.week_1_time);
		week_2_time = (TextView) v.findViewById(R.id.week_2_time);
		week_3_time = (TextView) v.findViewById(R.id.week_3_time);
		week_4_time = (TextView) v.findViewById(R.id.week_4_time);			
		week_1_tinaqi = (TextView) v.findViewById(R.id.week_1_tinaqi);
		week_2_tinaqi = (TextView) v.findViewById(R.id.week_2_tinaqi);
		week_3_tinaqi = (TextView) v.findViewById(R.id.week_3_tinaqi);
		week_4_tinaqi = (TextView) v.findViewById(R.id.week_4_tinaqi);
		week_1_wendu = (TextView) v.findViewById(R.id.week_1_wendu);
		week_2_wendu = (TextView) v.findViewById(R.id.week_2_wendu);
		week_3_wendu = (TextView) v.findViewById(R.id.week_3_wendu);
		week_4_wendu = (TextView) v.findViewById(R.id.week_4_wendu);
		refresh = (ImageView) v.findViewById(R.id.refresh);
		add = (ImageView) v.findViewById(R.id.add);

	}

	/*
	 * 获取数据
	 */
	public void obtainData(){
		final Handler han = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				if(what == 1){   //获取数据成功
					String date = msg.obj.toString();
					//截取  查询坐标
					date = date.substring(date.indexOf("(") + 1, date.indexOf(")"));
					Gson gson = new Gson();
					wb = gson.fromJson(date, WeatherBean.class);
					wi = wb.getWeatherinfo();
					update();
				}else{
					System.out.print("asd");
				}
			}
		};
		

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				if(cityId != null && cityId.length() != 0){
				String str_url = "http://weather.123.duba.net/static/weather_info/"+cityId+".html";
				BufferedReader br = null;
				StringBuffer sb = new StringBuffer();
				String line = null;

				try {
					URL url = new URL(str_url);//初始化连接地址对象
					HttpURLConnection con = (HttpURLConnection) url.openConnection();//打开网络连接
					con.setDoInput(true);//设置允许输入
					con.setRequestMethod("GET");//设置请求方法为get请求
					con.setConnectTimeout(10000);//设置网络超时时间

					//如果相应代码等于200，代表连接成功
					if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
						InputStream is = con.getInputStream();
						br = new BufferedReader(new InputStreamReader(is));
						while((line = br.readLine()) != null){
							sb.append(line);
						}

						Message msg = Message.obtain();//获得线程通信的Message对象
						msg.what = 1;
						msg.obj = sb.toString();
						han.sendMessage(msg);

						//						tv.setText(sb.toString());

					}else{//连接失败要进行的方法

						Message msg = Message.obtain();//获得线程通信的Message对象
						msg.what = 0;
						han.sendMessage(msg);

						//						tv.setText("网络连接失败");
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
			}
		});
		th.start();
	}
	/*
	 * 更新数据
	 * 
	 */
	public void update(){
		wendu.setText(wi.getTemp()+"°C");
		tianqi.setText(wi.getWeather1());
		news.setText(wi.getTemp1()+" "+wi.getFl1()+" "+wi.getFx1()+" pm "+ wi.getPm());
		String t = wb.getUpdate_time();
		t = t.substring(t.indexOf(":")-2);
		time.setText("更新时间"+ t);
		ciryName.setText(wi.getCity());

		String keekss = wi.getWeek();
		int i = 0 ;
		for(i = 0; i < weeks.length; i++ ){
			if(weeks[i].equals(keekss)){
				break;
			}
		}
		week_1_time.setText(weeks[(i+1)%7]);
		week_2_time.setText(weeks[(i+2)%7]);
		week_3_time.setText(weeks[(i+3)%7]);
		week_4_time.setText(weeks[(i+4)%7]);

		week_1_tinaqi.setText(wi.getWeather1());
		week_2_tinaqi.setText(wi.getWeather2());
		week_3_tinaqi.setText(wi.getWeather3());
		week_4_tinaqi.setText(wi.getWeather4());
		week_1_wendu.setText(wi.getTemp1());
		week_2_wendu.setText(wi.getTemp2());
		week_3_wendu.setText(wi.getTemp3());
		week_4_wendu.setText(wi.getTemp4());

	}
	public String week(int i , int j){
		int m = 0;
		return weeks[m];
	}
	public void refresh(){
		obtainData();
	}
	/*
	 * 添加图片的点击事件处理
	 */
	public void onClick_add(View v){
		OnClick on = (OnClick)getActivity();
		on.onclick();
		
	}
	
	public interface OnClick{
		public void onclick();
	}
}
