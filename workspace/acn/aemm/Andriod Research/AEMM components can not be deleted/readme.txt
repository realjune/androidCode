���ļ� PackageManagerService.java �У��� 6066 �е���6078�м��������δ��롣
PackageManagerService.java ��AndroidԴ�����е�·���ǣ�
Android\frameworks\base\services\java\com\android\server
�����õ�ɾ��������deletePackage���÷�������PackageManagerService�ж���ġ�
adb shell �е�Install��Uninstall������õ���:
Z:\Android\frameworks\base\cmds\pm\src\com\android\commands\pm\Pm.java
�е� runInstall��runUninstall ����
��runUninstall Ϊ����
�����������£�
Pm.java(runUninstall)->IPackageManager.java(deletePackage)->PackageManagerService.java(deletePackage)->deletePackageX->deletePackageLI
��������������û�н��������滹�н�һ�����жϣ��ж��Ƿ�System��App���Ƿ�ֻ��ɾ�����ݵȡ�
�õ�����ҪϵͳȨ�ޣ�
 dpm.packageHasActiveAdmins(packageName)
runInstall ������ runUninstall���̵��ô�����ͬ��
����Install��packageManagerService.java���õ�������Ϣ�Ļ��ơ�
ֻ��Ҫ�ڽ���PackageManagerService.java��installPackage�������Σ����߽⿪���ξͿ����ˡ�
��������Ҫ���ļ���
Android\frameworks\base\services\java\com\android\server\ PackageManagerService.java
\Android\out\target\common\obj\JAVA_LIBRARIES\android_stubs_current_intermediates\src\android\content\pm\PackageManager.java 

