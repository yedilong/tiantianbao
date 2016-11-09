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
	TextView wendu;// �����¶�
	TextView tianqi;//  ��������
	TextView news;//  �������Ϣ
	TextView time;//  ����ʱ��
	TextView ciryName;// ��������
	TextView week_1_time;// δ�������ʱ��
	TextView week_2_time;//
	TextView week_3_time;//
	TextView week_4_time;//
	TextView week_1_tinaqi;// δ�����������
	TextView week_2_tinaqi;//
	TextView week_3_tinaqi;//
	TextView week_4_tinaqi;//
	TextView week_1_wendu;//δ��������¶�
	TextView week_2_wendu;//
	TextView week_3_wendu;//
	TextView week_4_wendu;//
	String cityId;
	String[] weeks = new String[]{"����һ", "���ڶ�","������","������","������","������",
	"������"};
	ImageView refresh ;// ˢ��
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
	 *  view ��Ƭ  
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
	 * ��ȡ���
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
	 * ��ȡ����
	 */
	public void obtainData(){
		final Handler han = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				if(what == 1){   //��ȡ���ݳɹ�
					String date = msg.obj.toString();
					//��ȡ  ��ѯ����
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
					URL url = new URL(str_url);//��ʼ�����ӵ�ַ����
					HttpURLConnection con = (HttpURLConnection) url.openConnection();//����������
					con.setDoInput(true);//������������
					con.setRequestMethod("GET");//�������󷽷�Ϊget����
					con.setConnectTimeout(10000);//�������糬ʱʱ��

					//�����Ӧ�������200���������ӳɹ�
					if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
						InputStream is = con.getInputStream();
						br = new BufferedReader(new InputStreamReader(is));
						while((line = br.readLine()) != null){
							sb.append(line);
						}

						Message msg = Message.obtain();//����߳�ͨ�ŵ�Message����
						msg.what = 1;
						msg.obj = sb.toString();
						han.sendMessage(msg);

						//						tv.setText(sb.toString());

					}else{//����ʧ��Ҫ���еķ���

						Message msg = Message.obtain();//����߳�ͨ�ŵ�Message����
						msg.what = 0;
						han.sendMessage(msg);

						//						tv.setText("��������ʧ��");
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
	 * ��������
	 * 
	 */
	public void update(){
		wendu.setText(wi.getTemp()+"��C");
		tianqi.setText(wi.getWeather1());
		news.setText(wi.getTemp1()+" "+wi.getFl1()+" "+wi.getFx1()+" pm "+ wi.getPm());
		String t = wb.getUpdate_time();
		t = t.substring(t.indexOf(":")-2);
		time.setText("����ʱ��"+ t);
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
	 * ���ͼƬ�ĵ���¼�����
	 */
	public void onClick_add(View v){
		OnClick on = (OnClick)getActivity();
		on.onclick();
		
	}
	
	public interface OnClick{
		public void onclick();
	}
}
