package com.example.tiantianbao;

/*
 * z����һ�����е���Ϣ��   �������е���ϸ��Ϣ��ͻ��������һЩ��Ϣ
 */
public class WeatherBean {
	private String update_time; // ����ʱ��
	private WeatherInfo weatherinfo;
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public WeatherInfo getWeatherinfo() {
		return weatherinfo;
	}
	public void setWeatherinfo(WeatherInfo weatherinfo) {
		this.weatherinfo = weatherinfo;
	}
	
}
