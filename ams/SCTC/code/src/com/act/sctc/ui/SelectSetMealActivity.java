package com.act.sctc.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.act.sctc.BaseActivity;
import com.act.sctc.R;
import com.act.sctc.been.SearchRange;
import com.act.sctc.been.SetMeal;

public class SelectSetMealActivity extends BaseActivity{
	private ListView ls;
	private ListView ls2;
	private RadioGroup radioGroup1,radioGroup2,radioGroup3;
	
	private List<SetMeal>SetMeals;
	private List<SearchRange>searchRange1,searchRange2,searchRange3;

	LayoutInflater mLayoutInflater;//=LayoutInflater.from(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_meal_content);
		mLayoutInflater=LayoutInflater.from(this);
		iniController();
		iniListener();
        iniVariable();
		
	}
	
	private void iniVariable(){
		SetMeals=new ArrayList<SetMeal>();
		for(int i=0;i<10;i++){
			SetMeal sm=new SetMeal();
			sm.setName("乐享3G上网版");
			sm.setMonthlyRent(90);
			sm.setDomesticTraffic(80);
			sm.setFreeInlandCallIntroduce("市话、国内长话（含IP)、国内漫游共240分钟。");
			sm.setFreeWifiHour(30);
			sm.setFreeSMS(30);
			sm.setFreeMMS(6);
			sm.setExceedCall("市话、国内长话（含IP)、国内漫游主叫0.15元/分钟，其他按标准资费计收");
			sm.setExceedTraffic("国内上网0.30元/MB，20G自动断网。");
			sm.setFreeAnswerRange("全国");
			sm.setFreeServices("来显、彩铃月功能和免费189邮箱。");
			SetMeals.add(sm);
		}
		searchRange1=new ArrayList();
			SearchRange sr=new SearchRange();
			sr.s=Integer.MIN_VALUE;
			sr.e=Integer.MAX_VALUE;
			searchRange1.add(sr);
			sr=new SearchRange();
			sr.s=Integer.MIN_VALUE;
			sr.e=100;
			searchRange1.add(sr);	
			sr=new SearchRange();
			sr.s=101;
			sr.e=200;
			searchRange1.add(sr);
			sr=new SearchRange();
			sr.s=200;
			sr.e=Integer.MAX_VALUE;
			searchRange1.add(sr);

			searchRange2=new ArrayList();
			sr=new SearchRange();
			sr.s=Integer.MIN_VALUE;
			sr.e=Integer.MAX_VALUE;
			searchRange2.add(sr);
			sr=new SearchRange();
			sr.s=200;
			sr.e=600;
			searchRange2.add(sr);	
			sr=new SearchRange();
			sr.s=601;
			sr.e=2048;
			searchRange2.add(sr);
			sr=new SearchRange();
			sr.s=2048;
			sr.e=Integer.MAX_VALUE;
			searchRange2.add(sr);

			searchRange3=new ArrayList();
			sr=new SearchRange();
			sr.s=Integer.MIN_VALUE;
			sr.e=Integer.MAX_VALUE;
			searchRange3.add(sr);
			sr=new SearchRange();
			sr.e=300;
			sr.s=100;
			searchRange3.add(sr);	
			sr=new SearchRange();
			sr.s=301;
			sr.e=500;
			searchRange3.add(sr);
			sr=new SearchRange();
			sr.s=500;
			sr.e=1000;
			searchRange3.add(sr);
			sr=new SearchRange();
			sr.s=1000;
			sr.e=Integer.MAX_VALUE;;
			searchRange3.add(sr);
			
			iniSelectedRadioGroup(radioGroup1,searchRange1);
			iniSelectedRadioGroup(radioGroup2,searchRange2);
			iniSelectedRadioGroup(radioGroup3,searchRange3);
			
			ls.setAdapter(new SetMealAdapter());
			ls.setTextFilterEnabled(true);
			ls.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		ls2.setAdapter(new SetMealAdapter());
		
	}
	
	private List getData(int monthRent,int domesticTraffic,int freeInlandCall){
		List<SetMeal> results=new ArrayList<SetMeal>();
		int size=SetMeals.size();
		for(int i=0;i<size;i++){
			SetMeal sm=SetMeals.get(i);
//			if()
		}
		
		return results;
	}
	
	private void iniSelectedRadioGroup(RadioGroup rg,List<SearchRange> ls){
		SearchRange sr;
		int size = ls.size();
		for (int i = 0; i < size; i++) {
			sr=ls.get(i);
			RadioButton rbtn = (RadioButton) mLayoutInflater.inflate(
					R.layout.search_radiobutton_model, null);
			rbtn.setId(i);
			if(sr.s==Integer.MIN_VALUE&&sr.e==Integer.MAX_VALUE){
				rbtn.setText(R.string.all);
			}else if(sr.s==Integer.MIN_VALUE&&sr.e!=Integer.MAX_VALUE){
				rbtn.setText(getString(R.string.less_than, sr.e));
			}else if(sr.s!=Integer.MIN_VALUE&&sr.e!=Integer.MAX_VALUE){
				rbtn.setText(sr.s+"-"+sr.e);
			}else{
				rbtn.setText(getString(R.string.more_than, sr.s));
			}
			rg.addView(rbtn);
		}
	}
	
	private void iniListener(){
	}
	
	private void iniController(){
		ls=(ListView) findViewById(R.id.listView1);
		ls2=(ListView) findViewById(R.id.listView2);
		radioGroup1=(RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup2=(RadioGroup) findViewById(R.id.radioGroup2);
		radioGroup3=(RadioGroup) findViewById(R.id.radioGroup3);
	}
	
	class SetMealAdapter extends BaseAdapter implements OnClickListener{
		private int titleColor, selectedColor,normolColor;
		private View seleceView;
		private int selectedId=-1;
		public SetMealAdapter(){
			titleColor=getResources().getColor(R.color.set_meal_title);
			selectedColor=getResources().getColor(R.color.setmeal_seleted_color);
			normolColor=getResources().getColor(R.color.transparency_color);
		}

		@Override
		public int getCount() {
			return SetMeals==null?1:SetMeals.size()+1;
		}

		@Override
		public Object getItem(int position) {
			if(position==0){
				return null;
			}else{
				return SetMeals==null?null:SetMeals.get(position-1) ;
			}
		}

		@Override
		public long getItemId(int position) {
			return position-1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=LayoutInflater.from(SelectSetMealActivity.this).inflate(R.layout.table_row_model, null);
			}
			if(position==0){
				((TextView) convertView.findViewById(R.id.tv1)).setText(R.string.setmeal_category);
				((TextView) convertView.findViewById(R.id.tv2)).setText(R.string.month_rent);
				((TextView) convertView.findViewById(R.id.tv3)).setText(R.string.domestic_traffic);
				((TextView) convertView.findViewById(R.id.tv4)).setText(R.string.freeinlandcall);
				((TextView) convertView.findViewById(R.id.tv5)).setText(R.string.wifi_time);
				((TextView) convertView.findViewById(R.id.tv6)).setText(R.string.sms_count);
				((TextView) convertView.findViewById(R.id.tv7)).setText(R.string.mms_count);
				((TextView) convertView.findViewById(R.id.tv8)).setText(R.string.exceed_call);
				((TextView) convertView.findViewById(R.id.tv9)).setText(R.string.exceed_traffic);
				((TextView) convertView.findViewById(R.id.tv10)).setText(R.string.free_answer_area);
				((TextView) convertView.findViewById(R.id.tv11)).setText(R.string.free_service);
				convertView.setBackgroundColor(titleColor);
				convertView.setOnClickListener(null);
			}else{
				SetMeal sm=SetMeals.get(position-1);
				TextView tv1=(TextView) convertView.findViewById(R.id.tv1);
				tv1.setText(sm.getName());
				TextView tv2=(TextView) convertView.findViewById(R.id.tv2);
				tv2.setText(sm.getMonthlyRent()+"元");
				TextView tv3=(TextView) convertView.findViewById(R.id.tv3);
				tv3.setText(sm.getDomesticTraffic()+"M");
				TextView tv4=(TextView) convertView.findViewById(R.id.tv4);
				tv4.setText(sm.getFreeInlandCallIntroduce());
				TextView tv5=(TextView) convertView.findViewById(R.id.tv5);
				tv5.setText(sm.getFreeWifiHour()+"小时");
				TextView tv6=(TextView) convertView.findViewById(R.id.tv6);
				tv6.setText(sm.getFreeSMS()+"条");
				TextView tv7=(TextView) convertView.findViewById(R.id.tv7);
				tv7.setText(sm.getFreeMMS()+"条");
				((TextView) convertView.findViewById(R.id.tv8)).setText(sm.getExceedCall());
				((TextView) convertView.findViewById(R.id.tv9)).setText(sm.getExceedTraffic());
				((TextView) convertView.findViewById(R.id.tv10)).setText(sm.getFreeAnswerRange());
				((TextView) convertView.findViewById(R.id.tv11)).setText(sm.getFreeServices());
				convertView.setTag((Integer)position);
				if(selectedId==position){
					convertView.setBackgroundColor(selectedColor);
					seleceView=convertView;
				}else{
					convertView.setBackgroundColor(normolColor);
				}
				convertView.setOnClickListener(this);
			}
			return convertView;
		}

		@Override
		public void onClick(View v) {
			if(selectedId<0){
				v.setBackgroundColor(selectedColor);
				seleceView=v;
				selectedId=(Integer) v.getTag();
			}else if(v.getTag().equals(selectedId)){
				seleceView.setBackgroundColor(normolColor);
				seleceView=null;
				selectedId=-1;
			}else{
				seleceView.setBackgroundColor(normolColor);
				v.setBackgroundColor(selectedColor);
				seleceView=v;
				selectedId=(Integer) v.getTag();
			}
		}
	}

}
