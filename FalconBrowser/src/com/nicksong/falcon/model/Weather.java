package com.nicksong.falcon.model;

import java.util.ArrayList;


public class Weather implements IBaseModel {
	public String error;
	public String status;
	public String date;
	public ArrayList<WeatherDetail> results;

	@Override
	public String toString() {
		return error + status + date + results.toString();
	}
	/*
	 * {"error":0,"status":"success","date":"2014-12-19",
	 * "results":[
	 * {
	 * "currentCity":"上海市",
	 * "pm25":"187",
	 * "index":[
	 * 
	 * {"title":"穿衣","zs":"较冷","tipt":"穿衣指数","des":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},
	 * {"title":"洗车","zs":"较适宜","tipt":"洗车指数","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},
	 * {"title":"旅游","zs":"适宜","tipt":"旅游指数","des":"天气较好，气温稍低，会感觉稍微有点凉，不过也是个好天气哦。适宜旅游，可不要错过机会呦！"},
	 * {"title":"感冒","zs":"较易发","tipt":"感冒指数","des":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"},
	 * {"title":"运动","zs":"较适宜","tipt":"运动指数","des":"天气较好，但考虑气温较低，推荐您进行室内运动，若户外适当增减衣物并注意防晒。"},
	 * {"title":"紫外线强度","zs":"中等","tipt":"紫外线强度指数","des":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}
	 * ],
	 * "weather_data":[{"date":"周五 12月19日 (实时：6℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"西北风微风","temperature":"4℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"微风","temperature":"9 ~ 2℃"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"多云转晴","wind":"西北风微风","temperature":"6 ~ 0℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"西北风微风","temperature":"7 ~ 2℃"}]}]}

	 */
}
