package com.example.tiantianbao;

/*
 * z这是一个城市的信息类   包括城市的详细信息类和获得这个类的一些信息
 */
public class WeatherBean {
	private String update_time; // 更新时间
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
