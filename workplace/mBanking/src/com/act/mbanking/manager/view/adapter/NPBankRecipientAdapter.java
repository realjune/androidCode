
package com.act.mbanking.manager.view.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.act.mbanking.R;
import com.act.mbanking.bean.BankRecipient;
import com.custom.view.CoverFlow;
import com.custom.view.DoubleShadowTextView;
import com.custom.view.Switch3DGallery;

/**
 * @author junxu.wang
 */
public class NPBankRecipientAdapter extends CoverFlowViewAdapter {

    int mGalleryItemBackground;

    private Context mContext;

    List<BankRecipient> datas;

    LayoutInflater lInflater;

    Switch3DGallery.LayoutParams lp;

    int size;

    public NPBankRecipientAdapter(Context c) {

        mContext = c;
        lInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        size = (int)c.getResources().getDimension(R.dimen.payment_account_card_size);

    }

    public int getCount() {

        return datas != null ? datas.size() + 1 : 1;

    }

    /**
     * @return datas
     */
    public List<BankRecipient> getDatas() {
        return datas;
    }

    /**
     * @param datas 要设置的 datas
     */
    public void setDatas(List<BankRecipient> datas) {
        this.datas = datas;
    }

    @Override
    public View getView(int position) {
        View convertView = null;
        BankRecipient mBankRecipient;
        if (datas == null || position >= datas.size()) {
            if (convertView == null) {
                View v = lInflater.inflate(R.layout.new_payee_item, null);
                convertView = v;
                lp = new CoverFlow.LayoutParams(size, size);
                convertView.setLayoutParams(lp);
            }
            String name=null;
            if(tmp_payee!=null){
                mBankRecipient=(BankRecipient)tmp_payee;
                name=mBankRecipient.getName();
            }
            if(!TextUtils.isEmpty(name)){
                DoubleShadowTextView accountname_tv = (DoubleShadowTextView)convertView
                .findViewById(R.id.accountname_tv);
                accountname_tv.setText(name);
            }
        } else {
            mBankRecipient= datas.get(position);
            if (convertView == null) {
                View v = lInflater.inflate(R.layout.account_data_closed_item, null);
                convertView = v;
                lp = new CoverFlow.LayoutParams(size, size);
                convertView.setLayoutParams(lp);
            }
            DoubleShadowTextView accountname_tv = (DoubleShadowTextView)convertView
            .findViewById(R.id.accountname_tv);
            accountname_tv.setText(mBankRecipient.getName());
        }
        return convertView;

    }

}
