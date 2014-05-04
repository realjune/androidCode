package com.apk.infos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;



public class IndexShowUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new IndexShowUI();
	}
	/** ѡ���ļ��а�ť */
	JButton JBtn_Select;
	/** ��ȡ��ť */
	JButton JBtn_Get;
	/** ��ʾ�ļ���·�� */
	JTextField JText_Path;
	/** �ļ�ѡ���� */
	JFileChooser jFileChooser;
	JScrollPane scrollPane;
	JTable table;
	public static String strResult;

	public IndexShowUI() {
		super("��ȡAPK��Ϣ");
//		setBounds(500, 400, 0, 0);
		setSize(new Dimension(500, 400));
		
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		
		setResizable(false);
		JBtn_Select = new JButton("� ��");
		JBtn_Get = new JButton("�� ȡ");
		JPanel panel = new JPanel();
		JText_Path = new JTextField(25);
		JText_Path.setText(System.getProperty("user.home") + "\\����\\");
		panel.add(new JLabel("Ŀ���ļ���"));
		panel.add(JText_Path);
		panel.add(JBtn_Select);
		panel.add(JBtn_Get);
		jFileChooser = new JFileChooser();
		JPanel panelResult = new JPanel();
		panelResult.setOpaque(true);
		table=new JTable(new TableResult(""));
		table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 12));
        table.setRowHeight(28);
        scrollPane = new JScrollPane(table);
		panelResult.add(scrollPane);
		Container container = getContentPane();
		container.add(panel, BorderLayout.NORTH);
		container.add(panelResult, BorderLayout.CENTER);
		JBtn_Select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jFileChooser
						.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// �趨ֻ��ѡ���ļ���
				int state = jFileChooser.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
				if (state == 1) {
					return;// �����򷵻�
				} else {
					File f = jFileChooser.getSelectedFile();// fΪѡ�񵽵�Ŀ¼
					JText_Path.setText(f.getAbsolutePath()+"\\");
				}
			}
		});
		JBtn_Get.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String strPath = JText_Path.getText();
				if (strPath.equals("")) {
					// ��ʾ·��Ϊ��
					JOptionPane.showMessageDialog(IndexShowUI.this, "·��Ϊ�գ���ѡ��");
				} else {
					// ��֤·���Ƿ����
					File file = new File(strPath);
					if (!file.exists()) {
						JOptionPane.showMessageDialog(IndexShowUI.this,
								"·����������ѡ��");
					} else {
						// ִ��
						if (!strPath.endsWith("\\")) {
							strPath=strPath+"\\";
							JText_Path.setText(strPath);
						}
						table.setModel(new TableResult(strPath));
						JOptionPane.showMessageDialog(IndexShowUI.this, strResult);
					}
				}
			}
		});
//		setSize(500, 200);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
