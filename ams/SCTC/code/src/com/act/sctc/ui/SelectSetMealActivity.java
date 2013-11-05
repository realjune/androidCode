package com.act.sctc.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.act.sctc.BaseActivity;
import com.act.sctc.R;
import com.act.sctc.been.SetMeal;

public class SelectSetMealActivity extends BaseActivity {
	private ListView ls;
	private List<SetMeal>SetMeals;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_meal_content);
		ls=(ListView) findViewById(R.id.listView1);
		SetMeals=new ArrayList<SetMeal>();
		for(int i=0;i<10;i++){
			SetMeal sm=new SetMeal();
			sm.setName("3G");
			sm.setMonthlyRent(90);
			sm.setFreeMMS(10);
			sm.setFreeSMS(10);
			sm.setFreeWifiHour(10);
			sm.setFreeInlandCallIntroduce("dddfdfdf");
			sm.setDomesticTraffic(80);
			SetMeals.add(sm);
		}
		ls.setAdapter(new SetMealAdapter());
	}
	
	class SetMealAdapter extends BaseAdapter{
		
		public SetMealAdapter(){
			
		}

		@Override
		public int getCount() {
			return SetMeals==null?0:SetMeals.size();
		}

		@Override
		public Object getItem(int position) {
			return SetMeals==null?null:SetMeals.get(position) ;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=LayoutInflater.from(SelectSetMealActivity.this).inflate(R.layout.table_row_model, null);
			}
			SetMeal sm=SetMeals.get(position);
			TextView tv1=(TextView) convertView.findViewById(R.id.tv1);
			tv1.setText(sm.getName());
			TextView tv2=(TextView) convertView.findViewById(R.id.tv2);
			tv2.setText(sm.getMonthlyRent()+"");
			TextView tv3=(TextView) convertView.findViewById(R.id.tv3);
			tv3.setText(sm.getDomesticTraffic()+"");
			TextView tv4=(TextView) convertView.findViewById(R.id.tv4);
			tv4.setText(sm.getFreeInlandCallIntroduce());
			TextView tv5=(TextView) convertView.findViewById(R.id.tv5);
			tv5.setText(sm.getFreeWifiHour()+"");
			TextView tv6=(TextView) convertView.findViewById(R.id.tv6);
			tv6.setText(sm.getFreeSMS()+"");
			TextView tv7=(TextView) convertView.findViewById(R.id.tv7);
			tv7.setText(sm.getFreeMMS()+"");
			return convertView;
		}
	}

}
