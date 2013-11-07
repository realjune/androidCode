package com.accenture.mbank.view.payment;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.accenture.mbank.NewPayee;
import com.accenture.mbank.R;
import com.accenture.mbank.NewPayee.BillHolder;
import com.accenture.mbank.capture.CaptureActivity;
import com.accenture.mbank.logic.GetPaymentTemplatesJson;
import com.accenture.mbank.model.GetPaymentTempLatesResponseModel;
import com.accenture.mbank.util.Contants;
import com.accenture.mbank.util.ViewUtil;

public class PaymentsManagerBlankBill implements OnClickListener {
	public static final int CaptureRequestCode=1001;
	
	private ViewGroup layout;
	private EditText bill_holder_et;
	private ImageButton bill_holder_ibtn;
	private EditText account_number_et;
	private EditText amount_doller_et;
	private EditText payable_to_et;
	private EditText description_et;
	private RadioGroup type_rg;
	private Context context;
	
	public static final String _123="123";
	public static final String _451="451";
	
	public PaymentsManagerBlankBill(Context context){
		this.context=context;
		LayoutInflater layoutInflater=LayoutInflater.from(context);
		layout=(ViewGroup) layoutInflater.inflate(R.layout.new_payment_blank_bill, null);
		initView();
	}
	
	public PaymentsManagerBlankBill(Context context,ViewGroup layout){
		this.context=context;
		this.layout=layout;
		initView();
	}
	
	private void initView(){
		bill_holder_et=(EditText) layout.findViewById(R.id.bill_holder_et);
		bill_holder_ibtn=(ImageButton) layout.findViewById(R.id.bill_holder_ibtn);
		bill_holder_ibtn.setOnClickListener(this);
		account_number_et=(EditText) layout.findViewById(R.id.account_number_et);
		amount_doller_et=(EditText) layout.findViewById(R.id.amount_doller_et);
		payable_to_et=(EditText) layout.findViewById(R.id.payable_to_et);
		description_et=(EditText) layout.findViewById(R.id._description_et);
		type_rg=(RadioGroup) layout.findViewById(R.id.type_rg);
	}
	
	public void setVisibility(int visibility){
		layout.setVisibility(visibility);
	}
	
	private void loadData(){
		String dataStr=GetPaymentTemplatesJson.GetPaymentTemplatesReportProtocal(Contants.publicModel);
		GetPaymentTempLatesResponseModel mGetPaymentTempLatesResponseModel =GetPaymentTemplatesJson.ParseGetPaymentTempLatesResponse(dataStr);
	}
	
	public ViewGroup getLayout(){
		return layout;
	}
	
	public void setBillHolderName(String string){
		bill_holder_et.setText(string);
	}
	
	public String getBillHolderName(){
		return bill_holder_et.getText().toString().trim();
	}
	
	public void setDecreption(String string){
		description_et.setText(string);
	}
	
	public String getDescription(){
		return description_et.getText().toString().trim();
	}
	
	public void setAmount(double amount){
		try {
            NumberFormat format =NumberFormat.getInstance(Locale.ITALY);
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);
            bill_holder_et.setText(format.format(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void setAmount(String str){
        try {
        	str = str.replace(".", "");
        	str = str.replace(',', '.');
            double amount = Double.parseDouble(str);
            amount += 0.001;
//            NumberFormat format = NumberFormat.getInstance();
//            format.setCurrency(Currency.getInstance("EUR"));
            NumberFormat format =NumberFormat.getInstance(Locale.ITALY);
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);
            str = format.format(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
		amount_doller_et.setText(str);
	}
	
	public double getAmount(){
		String str=amount_doller_et.getText().toString().trim();
		str = str.replace(".", "");
        str = str.replace(',', '.');
        double amount=0;
        try {
            amount = Double.parseDouble(str);
//            amount += 0.001;
//            NumberFormat format = NumberFormat.getInstance();
//            format.setCurrency(Currency.getInstance("EUR"));
//            NumberFormat format =NumberFormat.getInstance(Locale.ITALY);
//            format.setMinimumFractionDigits(2);
//            format.setMaximumFractionDigits(2);
//            str = format.format(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return amount;
	}
	
	public String getAccountNumber(){
		return account_number_et.getText().toString().trim();
	}
	
	public void setAccountNumber(String number){
		account_number_et.setText(number);
	}
	
	public String getPayableTo(){
		return payable_to_et.getText().toString().trim();
	}
	
	public void setType(String type){
		if(_123.equals(type)){
			type_rg.check(R.id._123_rbtn);
		}else if(_451.equals(type)){
			type_rg.check(R.id._451_rbtn);
		}else{
			type_rg.check(-1);
		}
	}

	
	public String getType(){
		String result=null;
		switch(type_rg.getCheckedRadioButtonId()){
			case R.id._123_rbtn:
				result=_123;
				break;
			case R.id._451_rbtn:
				result=_451;
				break;
		}
		return result;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bill_holder_ibtn:
			NewPayee.start((Activity) context, NewPayee.PRECOMPILED_BILL_HOLDER, NewPayee.PRECOMPILED_BILL_HOLDER,null);
			break;
		}
		
	}
	
	 public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		 if(requestCode==NewPayee.BLANK_BILL_HOLDER){
			 mBillHolder=(BillHolder) data.getSerializableExtra(NewPayee.OBJ_DATA);
			 setBillHolderName(mBillHolder.holderName);
			 return true;
		 }
		 
		 return false;
	 }
	 
	 public BillHolder getHolder(){
		 return mBillHolder;
	 }
	 NewPayee.BillHolder mBillHolder;

}
