
package com.act.mbanking.manager.view.adapter;

import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Gallery.LayoutParams;

import com.act.mbanking.Contants;
import com.act.mbanking.R;
import com.act.mbanking.bean.AccountsModel;
import com.act.mbanking.bean.Balance;
import com.act.mbanking.utils.JXSuperscriptSpan;
import com.act.mbanking.utils.Utils;
import com.custom.view.DoubleShadowTextView;
import com.custom.view.Switch3DGallery;

/**
 * @author junxu.wang
 */
public class NPAccountsModelAdapter extends CoverFlowViewAdapter {

    int mGalleryItemBackground;

    private Context mContext;

    List<AccountsModel> datas;

    int viewId;

    LayoutInflater lInflater;

    Switch3DGallery.LayoutParams lp;

    private boolean addAble;

    private int clickDisableId = -1;

    private int size;

    public NPAccountsModelAdapter(Context c) {

        mContext = c;
        lInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        size = (int)c.getResources().getDimension(R.dimen.payment_account_card_size);
    }

    public void setClickDisableId(int id) {
        clickDisableId = id;
    }
    
    public int getClickDisableId(){
        return clickDisableId;
    }

    public void setViewId(int id) {
        this.viewId = id;
    }

    public int getCount() {
        if (datas == null) {
            return addAble ? 1 : 0;
        } else {
            return addAble ? datas.size() + 1 : datas.size();
        }
    }

    /**
     * @return datas
     */
    public List<AccountsModel> getDatas() {
        return datas;
    }

    /**
     * @param datas 要设置的 datas
     */
    public void setDatas(List<AccountsModel> datas) {
        this.datas = datas;
    }

    public void setAddable(boolean able) {
        addAble = able;
    }

    @Override
    public View getView(int position) {
        View convertView = null;
        if (datas == null || position >= datas.size()) {
            if (convertView == null) {
                View v = lInflater.inflate(R.layout.new_payee_item, null);
                convertView = v;
                lp = new Switch3DGallery.LayoutParams(size, LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(lp);
            }
        } else {
            if (convertView == null) {
                View v = lInflater.inflate(viewId, null);
                convertView = v;
                if (viewId == R.layout.account_data_closed_item) {
                    lp = new Switch3DGallery.LayoutParams(size, size);
                } else {
                    lp = new Switch3DGallery.LayoutParams(size, LayoutParams.WRAP_CONTENT);
                }
                convertView.setLayoutParams(lp);
            }
            if (viewId == R.layout.account_data_opened_content) {
                DoubleShadowTextView accountname_tv = (DoubleShadowTextView)convertView
                        .findViewById(R.id.accountname_tv);
                accountname_tv.setText(datas.get(position).getAccountAlias());
                Balance b=datas.get(position).getBalance();
                DoubleShadowTextView account_available_tv=(DoubleShadowTextView)convertView.findViewById(R.id.account_available_tv);
                String str=null;
                if(b!=null){
                    str=Utils.generateFormatMoney(Contants.COUNTRY,b.getAccountBalance());
                }else{
                    str=Utils.generateFormatMoney(Contants.COUNTRY,0);
                }
                SpannableString msp=new SpannableString(str);
                msp.setSpan(new JXSuperscriptSpan(), str.length()-3, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标
                account_available_tv.setText(msp);
            }else if(viewId==R.layout.account_data_closed_item){
                DoubleShadowTextView accountname_tv=(DoubleShadowTextView)convertView.findViewById(R.id.accountname_tv);
                accountname_tv.setText(datas.get(position).getAccountAlias());
                if (position == clickDisableId) {
                    accountname_tv.setClickable(false);
                    accountname_tv.setFocusable(false);
                    accountname_tv.setClickable(false);
                    accountname_tv.setSelected(false);
                }
            }
        }
        return convertView;

    }

}
