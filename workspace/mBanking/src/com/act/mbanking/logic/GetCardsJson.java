
package com.act.mbanking.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.act.mbanking.ServiceType;
import com.act.mbanking.bean.GetCardsResponseModel;
import com.act.mbanking.bean.InfoCardsModel;
import com.act.mbanking.bean.RequestPublicModel;
import com.act.mbanking.utils.LogManager;

public class GetCardsJson {
    /**
     * 
     * @param publicModel
     * @param cardHolder
     * @param last4Digits
     * @return
     */
    public static String GetCardsReportProtocal(RequestPublicModel publicModel, String cardHolder,
            String last4Digits,String accountCode) {
        String result = null;
        try {
            JSONObject jsonObj = new JSONObject();
            JSONObject getCardsObj = new JSONObject();
            getCardsObj.put("accountCode", accountCode);
            getCardsObj.put("bankName", publicModel.getBankName());
            getCardsObj.put("cardHolder", cardHolder);
            getCardsObj.put("channel", publicModel.getChannel());
            getCardsObj.put("customerCode", publicModel.getCustomerCode());
            getCardsObj.put("enterpriseId", publicModel.getEnterpriseId());
            getCardsObj.put("last4Digits", last4Digits);
            getCardsObj.put("serviceType", ServiceType.getCards);
            getCardsObj.put("userAgent", publicModel.getUserAgent());
            getCardsObj.put("sessionId",publicModel.getSessionId());
            getCardsObj.put("token",publicModel.getToken());

            jsonObj.put("GetCardsRequest", getCardsObj);
            result = jsonObj.toString();
        } catch (Exception e) {
            LogManager.e("GetCardsReportProtocal is error" + e.getLocalizedMessage());
        }
        return result;
    }
    
    public static GetCardsResponseModel parseGetCardResponse(String json){
        GetCardsResponseModel getCardsResponseMode = new GetCardsResponseModel();
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONObject getCardsObj = jsonObj.getJSONObject("GetCardsResponse");

            getCardsResponseMode.responsePublicModel.setResultCode(getCardsObj.getInt("resultCode"));
            getCardsResponseMode.responsePublicModel.setResultDescription(getCardsObj.optString("resultDescription"));
            if (getCardsResponseMode.responsePublicModel.getResultCode() != 0) {
                JSONObject eventManagementObj = getCardsObj.getJSONObject("eventManagement");
                getCardsResponseMode.responsePublicModel.eventManagement.setErrorCode(eventManagementObj.optString("errorCode"));
                getCardsResponseMode.responsePublicModel.eventManagement.setErrorDescription(eventManagementObj.optString("errorDescription"));
                return getCardsResponseMode;
            }
            getCardsResponseMode.responsePublicModel.setTransactionId(getCardsObj.optString("transactionId"));
           
            JSONArray infoCards = getCardsObj.getJSONArray("infoCards");
            List<InfoCardsModel> infoCardsList = new ArrayList<InfoCardsModel>();
            for(int i=0;i<infoCards.length();i++){
                InfoCardsModel infoCarsModel = new InfoCardsModel();
                infoCarsModel.setExpirationDate(infoCards.getJSONObject(i).optString("expirationDate"));
                infoCarsModel.setCardHash(infoCards.getJSONObject(i).optString("cardHash"));
                infoCarsModel.setCardNumberMask(infoCards.getJSONObject(i).optString("cardNumberMask"));
                infoCarsModel.setType(infoCards.getJSONObject(i).optString("type"));
                infoCarsModel.setTitle(infoCards.getJSONObject(i).optString("title"));
                infoCarsModel.setName(infoCards.getJSONObject(i).optString("name"));
                infoCarsModel.setHolderBirthDate(infoCards.getJSONObject(i).optString("holderBirthDate"));
                infoCardsList.add(infoCarsModel);
            }
           
            getCardsResponseMode.setInfoCardListModel(infoCardsList);
            LogManager.d("aaaaaaaaaaaaaaaaaaaaaaaa");
        }catch(Exception e){
            LogManager.d("parseGetCardResponse is error" + e.getLocalizedMessage());
        }
        return getCardsResponseMode;
    }

}
