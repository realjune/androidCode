package com.apk.infos;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class TableResult2 extends DefaultTableModel{

	private ArrayList<String> listTitle=new ArrayList<String>();
	private static ArrayList<ArrayList<String>> listDate=null;
	public TableResult2(String strpath){
		listTitle.add("�ļ�����");
		listTitle.add("����");
		listTitle.add("�汾��");
		listTitle.add("�汾��");
		listDate=new ArrayList<ArrayList<String>>();
		if (strpath != null && !"".equals(strpath)) {
			try {
				listDate = GetInfo.GetApkInfoAll(strpath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getColumnCount() {
		return listTitle.size();
	}

	@Override
	public int getRowCount() {
		if(listDate == null){
			return 0;
		}
		return listDate.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return listDate.get(arg0).get(arg1);
	}

	@Override
	public String getColumnName(int arg0) {
		return listTitle.get(arg0);
	}

}
