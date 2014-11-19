android\packages\apps\Settings\src\com\android\settings\vpn\VpnSettings.java 
VPN�Ķ���д����ͨ��Setting����дProfile�ļ�ʵ�ֵġ�
����Ȩ��ֻ����Settings���������APP������Settings��������Ӧ��Profile�ļ���
	static void saveProfileToStorage(VpnProfile p) throws IOException {
        File f = new File(getProfileDir(p));
        if (!f.exists()) f.mkdirs();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                new File(f, PROFILE_OBJ_FILE)));
        oos.writeObject(p);
        oos.close();
    } 
	// ����Ĵ���˵����VPNSettingsֻ�ǽ�һ��Profileֱ��д�����ļ�����ӵ�ж�Profile�Ĳ�����Ȩ�ޡ�
	
�ڵ�335�У�������´���

			String profileName = p.getName() ; 
			// �����AEMM��������Ϣ�������ò˵��ûҡ�
			if( !profileName.contains("aemm")) // by wh 
			{
				menu.add(0, CONTEXT_MENU_EDIT_ID, 0, R.string.vpn_menu_edit)
						.setEnabled(isNotConnect);
				menu.add(0, CONTEXT_MENU_DELETE_ID, 0, R.string.vpn_menu_delete)
						.setEnabled(isNotConnect);
			}
			else
			{
				menu.add(0, CONTEXT_MENU_EDIT_ID, 0, R.string.vpn_menu_edit)
						.setEnabled(false);
				menu.add(0, CONTEXT_MENU_DELETE_ID, 0, R.string.vpn_menu_delete)
						.setEnabled(false);
			}
			
//startVpnEditor VPN�ı༭��������Setting�������ģ�
VpnSettings.java -> startVpnEditor 
// ���ĵ����������£�
startVpnEditor->profileChanged->writeToParcel->writeString(String val ) ;  // native ����
����ĵ��ù�������Ӧ���ļ��ֱ�Ϊ��
VpnSettings.java��VpnEditor.java��VpnProfile.java��Parcel.java
�Ƽ���Settings����á�

deleteProfile 
		\packages\apps\Settings\src\com\android\settings\vpn\Util.java
		Util.deleteFile(String fName) ; 
	// VPN������Ϣ��ɾ����ֱ�Ӷ��ļ����еĲ����������˳�����£�
	removeProfileFromStorage(VPN Settings)->Util.DeleteFile(String fName) 
PS:
    �����޸ĺ�ɾ���˵� �� 

���˼·�� 
VPN������û��ר�ŵķ��������й���
����������Ϣ�Ǵ������ļ��ж����ģ�����Զ�ȡ�������ƣ�VPN�����ӽ�����Ӱ�졣
frameworks/base/vpn/java/android/net/vpn/VpnManager.java // ��VPN���в���
VpnManager.java�����Ƕ�VPN�������õģ�����˵��
������ֹͣVPN�������VPN���á����ܴﵽ����Ҫ��Ŀ�ꡣ
���ԣ�ֻ�����ϲ�˵��н��д��������AEMM������򽫱༭��ɾ��ѡ����á�
onCreate�������£�
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.vpn_settings);

        // restore VpnProfile list and construct VpnPreference map
		// ����VPN�����б��������������
        mVpnListContainer = (PreferenceCategory) findPreference(PREF_VPN_LIST);

        // ���VPNѡ��(add vpn)
        mAddVpn = (PreferenceScreen) findPreference(PREF_ADD_VPN);
		// ���ã�addvpn�����¼�������
        mAddVpn.setOnPreferenceClickListener(
                new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        startVpnTypeSelection();
                        return true;
                    }
                });

        // for long-press gesture on a profile preference
		// ΪVPNѡ����ӳ����¼�������
		// �ڳ����¼����������Ӧ�������AEMM���������ñ༭��ɾ��ѡ�
        registerForContextMenu(getListView());

        // listen to vpn connectivity event
		// ����VPN�����¼���
        mVpnManager.registerConnectivityReceiver(mConnectivityReceiver);

        retrieveVpnListFromStorage();
        checkVpnConnectionStatusInBackground();
    }