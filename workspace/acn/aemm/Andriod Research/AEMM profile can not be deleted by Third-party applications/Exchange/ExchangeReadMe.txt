\Android\packages\apps\Email\src\com\android\email\activity\setup\AccountSettingsUtils.java
\Android\packages\apps\AccountsAndSyncSettings\src\com\android\settings\ManageAccountsSettings.java
\Android\packages\apps\Email\src\com\android\email\activity\setup\AccountSetupExchange.java
\Android\frameworks\base\core\java\android\accounts\AccountManagerService.java
\Android\packages\apps\AccountsAndSyncSettings\src\com\android\settings\AccountSyncSettings.java
\Android\packages\apps\Email\src\com\android\email\activity\AccountFolderList.java
AccountManagerService.java
	This should only be called by system code. One should only call this after the service �C ԭ�����е�ע��
	ֻ��ϵͳ�����ܹ�����AccountManagerService.java����Ľӿ�.
	����ʵ�ֵĹ��ܣ�
		���Account�Ƿ�Ϸ������⣬���ĳ���ʻ���ɾ�����ᷢ��AccountChanged�Ĺ㲥��Ϣ��
		���ṩ�����й���Account�Ĳ��������磺��ȡ����ӡ�ɾ���ʻ����޸��ʻ���ȷ�����޸��ʻ�����֤��Ϣ��
AccountSetupExchangeTests.java
	����Account�ʻ����á�
ManageAccountsSettings.java
	������ʻ����õ������档
AccountSettingsUtils.java
	������ʻ����Ա��޸ĵ��
AccountSyncSettings.java 
	����ʻ������ʻ����������Activity��
AccountFolderList.java
	Email������棬����ͨ�����Activity����Exchange��Email�ʻ���ѡ��ĳ���ʻ��Ժ󳤰������Զ��ʻ����У����¡��༭��ɾ���Ȳ�����
	�༭������Account�еı༭������һ��Activity��ɾ��ֻ��һ��ContextMenu���޷���Account�Ĵ��������Σ����ԣ���Ҫ������ļ������޸ģ���������ɾ�����롣

����˳��AccountSetupExchange.java -> AccountSyncSettings.java -> AccountSettings.java->AccountSettingsUtils.java->EmailContent.java(EmailContent.Account)(insert��update)
->ContentResolver(insert,update)

AccountSettings.java���棬���ݵ��ύ����ȡ������ͨ��Editor����ȡ�ģ�Editor��һ���ӿ��࣬���ľ���ʵ���ڣ�
Z:\Android\frameworks\base\core\java\android\app\ContextImpl.java�У�

�����޸ģ� 
AccountSyncSettings.java
line 161 
��Ӵ��룺
		if( !(mAccount.name.contains("accenture")) ) // by wh 
		{
			mRemoveAccountArea = (View) findViewById(R.id.remove_account_area);
			mRemoveAccountButton = (Button) findViewById(R.id.remove_account_button);
			mRemoveAccountButton.setOnClickListener(this);
		}
�����ǰ�ʻ���AEMM���趨���ʻ������ֹʹ��ɾ��ѡ�
AccountSettings.java
line 135 
��Ӵ��룺
		if( mAccount.getDisplayName().contains("aemm") ||  mAccount.getDisplayName().contains("accenture"))// by wh 
		{
			finish() ; 
			return ; 
		}
�����Ҫ��ʾ����ϸ��Ϣ��AEMM�ʻ�����Ϣ�����Զ���ת��ǰһ�����档
AccountFolderList.java
line 454
		if( mSelectedContextAccount.getDisplayName().contains("aemm.demo")
			|| mSelectedContextAccount.getDisplayName().contains("accenture")) // by wh 
		{
			return ; 
		}
����˵�ѡ�е�Ϊɾ������ң���Ҫɾ������ΪAEMM�ʻ����򲻽����κβ�����ֱ�ӷ��ء�

����ڵײ��޸ĵĻ�����Ҫ�޸�EmailProvider.java
Update���ң�����Delete�������ϲ��ʱ����������һ��TASK������ɾ��ĳ��ѡ�


\Android\packages\apps\Email\src\com\android\email\provider\EmailProvider.java
update������delete����
�н�������
line 1143
		if( values != null )
		{
			String strName = values.getAsString("name"); // by wh 
			if( strName != null )
				if( strName.length() != 0 && strName.contains("aemm") )
				{
					getContext().getContentResolver().notifyChange(uri, null);
					return 0 ; 
				}
		}

		�ʻ���ӵ�ʱ����FY���е��������Ҫ����������ݿ⣬���Ǹ��µ�ʱ����Ϊ�ߵĶ���һ�״��룬���ԣ�ֻ��Ҫ�޸Ĵ��ط����ɡ�
		
\Android\packages\apps\Email\src\com\android\exchange\provider\ExchangeProvider.java
��update������delete������Ϊ��