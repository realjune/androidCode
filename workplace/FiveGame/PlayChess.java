import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import sun.java2d.pipe.DrawImage;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.*;

public class PlayChess extends JFrame{//�̳�JFrame֮��playChess����һ������
	
	private Image imageBG;//����ͼƬ����
	private Image imageBlack;//��ɫ����ͼƬ����
	private Image imageWhite;//��ɫ����ͼƬ����
	private Image imageMenu;//�˵�ͼƬ����
	private Image imageW;//û��Ц���̶���ͼƬ����
	private Image imageX;//��Ц���̶���ͼƬ����
	private Image imageRim;//��ɫ��ͼƬ����
	private Color[][] allChesses=new Color[14][14];//���е����
	private MyCanvas canvas;//�Զ����MyCanvas�ಢ�̳���JPanel
	private boolean isGameBegin=false;//��Ϸ�Ƿ�ʼ
	private boolean isGameOver=true;//��Ϸ�Ƿ����
	private Stack stack;//��ջ����
	private Robot robot;//�����˶���
	private Thread PlayerThread;//���ʱ������Ӧ���߳�
	private Thread robotThread;//������ʱ������Ӧ���߳�
	private Thread unDoThread;//����ʱ������Ӧ���߳�
	private int unDoTime=5;//�����ʱ����
	private int playerTime=1800;//��ҵ���ʱ��
	private int robotTime=1800;//�����˵���ʱ��
	private Graphics2D graphics2d;//��ͼ����
	private Color whoSmile=null;//˭Ӯ��˭��ʾ��Ц���̶���ͼƬ����
	private boolean isUndo=false;
	public PlayChess(){//���캯��
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PlayChess.class.getResource("pointer.png")),new Point(0,0), ""));
		Applet.newAudioClip(PlayChess.class.getResource("bg.mid")).loop();
		
		stack=new Stack();
		robot=new Robot();
		
		URL imgUrl1 = PlayChess.class.getResource("bg_game.JPG");// ��ȡͼƬ��Դ��·��
		imageBG = Toolkit.getDefaultToolkit().getImage(imgUrl1);// ��ȡͼƬ��Դ
		
		URL imgUrl2 = PlayChess.class.getResource("black.png");// ��ȡͼƬ��Դ��·��
		imageBlack= Toolkit.getDefaultToolkit().getImage(imgUrl2);// ��ȡͼƬ��Դ
		
		URL imgUrl3 = PlayChess.class.getResource("white.png");// ��ȡͼƬ��Դ��·��
		imageWhite = Toolkit.getDefaultToolkit().getImage(imgUrl3);// ��ȡͼƬ��Դ
		
		URL imgUrl4 = PlayChess.class.getResource("menu.png");// ��ȡͼƬ��Դ��·��
		imageMenu = Toolkit.getDefaultToolkit().getImage(imgUrl4);// ��ȡͼƬ��Դ
		
		URL imgUrl5 = PlayChess.class.getResource("W.png");// ��ȡͼƬ��Դ��·��
		imageW = Toolkit.getDefaultToolkit().getImage(imgUrl5);// ��ȡͼƬ��Դ
		
		URL imgUrl6 = PlayChess.class.getResource("X.gif");// ��ȡͼƬ��Դ��·��
		imageX = Toolkit.getDefaultToolkit().getImage(imgUrl6);// ��ȡͼƬ��Դ
		
		URL imgUrl8 = PlayChess.class.getResource("rim.png");// ��ȡͼƬ��Դ��·��
		imageRim = Toolkit.getDefaultToolkit().getImage(imgUrl8);// ��ȡͼƬ��Դ
		this.setSize(700,550);//���ô����С
		setWindowCenter();//���������
		canvas=new MyCanvas();//����һ��MyCanvas����
		this.getContentPane().add(canvas);//��canvas������ӵ������
		
		robotThread=new Thread(new Runnable(){
			public void run(){
				while(true){
					if(robot.getMyIsPlayingChess()==true&&isGameBegin==true&&isUndo==false){
						try{
							robotThread.sleep(1000);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						robotTime--;
						canvas.repaint();
						if(robotTime==0){
							isGameBegin=false;
							isGameOver=true;
							whoSmile=Color.white;
							canvas.repaint();
							JOptionPane.showMessageDialog(null,"��Ӯ�ˣ�");
						}
					}
				}
			}
		});
		
		PlayerThread=new Thread(new Runnable(){
			public void run(){//��ִ��start����ʱ���߳�����
				while(true){//��ѭ������Ҫ����ʱ���߻�ֹͣ��������������ʱ����߷���Ͳ���
					if(robot.getMyIsPlayingChess()==false&&isGameBegin==true&&isUndo==false){
						try{//���������û�����������Ϸ�Ѿ���ʼ��ô�ͱ�ʾ�������
							PlayerThread.sleep(1000);//�߳�˯һ����
						}
						catch(Exception e){
							e.printStackTrace();
						}
						playerTime--;//���ʱ���һ����
						canvas.repaint();//���ƴ���
						if(playerTime==0){//�����ҵ�ʱ�������ˣ���ʾ�����
							isGameBegin=false;//��Ϸû�п�ʼ
							isGameOver=true;//��Ϸ����
							whoSmile=Color.black;//��ɫЦ
							canvas.repaint();//���ƴ���
							JOptionPane.showMessageDialog(null,"������Ӯ�ˣ�");
						}
					}
				}
			}
		});
		unDoThread=new Thread(new Runnable(){
			public void run(){//��ִ��start����ʱ�߳�����
				while(true){
					if(unDoTime==0){//����ʱ�䵽,��ʼ����
						unDoTime--;//���û���ʱ��Ϊ-1
						if(stack.getStackTop()!=null){//���ջ����Ϊ�ձ�ʾ�м�¼
							if(stack.getStackTop().getChessColor()==Color.white){{//���Ϊ��ɫ��ʾ�û���������
								robot.cerebra(allChesses, canvas, Color.black,stack);//����������
								if(checkRowIsFive(robot.getRow(), robot.getCol())||checkColIsFive(robot.getRow(), robot.getCol())||checkRightBias(robot.getRow(), robot.getCol())||checkLeftBias(robot.getRow(), robot.getCol())){//�������Ƿ�Ӯ 
									isGameBegin=false;
									isGameOver=true;
									whoSmile=Color.black;
									canvas.repaint();
									JOptionPane.showMessageDialog(null,"������Ӯ�ˣ�");
									return;
								}
								if(isDogfall()==true){//�Ƿ�ƽ��
									isGameBegin=false;
									isGameOver=true;
									canvas.repaint();
									JOptionPane.showMessageDialog(null,"ƽ���ˣ�");
									return;
								}
							}
						}
					}
					else{//��ʾ������û�������ˣ�Ҳ�û���������
						robot.cerebra(allChesses, canvas, Color.black,stack);
						if(checkRowIsFive(robot.getRow(), robot.getCol())||checkColIsFive(robot.getRow(), robot.getCol())||checkRightBias(robot.getRow(), robot.getCol())||checkLeftBias(robot.getRow(), robot.getCol())){
							isGameBegin=false;
							isGameOver=true;
							whoSmile=Color.black;
							canvas.repaint();
							whoSmile=Color.black;
							canvas.repaint();
							JOptionPane.showMessageDialog(null,"������Ӯ�ˣ�");
							return;
						}
					}
					isUndo=false;//��������
					}
					else{
						try{
							PlayerThread.sleep(1000);//�߳�˯һ����
							unDoTime--;//����ʱ���һ
							if(unDoTime==-10){
								unDoTime=-1;
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		canvas.addMouseListener(new MouseInputAdapter(){
			
			public void mousePressed(MouseEvent e){
				if((e.getX()>=179&&e.getX()<=652)&&(e.getY()>=30&&e.getY()<=497)&&isGameBegin==true&&robot.getMyIsPlayingChess()==false&&isUndo==false){  //���������������򵥻�������Ϸ��ʼ���һ�����û��������ô��ִ��if����� 
						int row=(int)((e.getY()-30)/33.4);//������ת�����±���
						int col=(int)((e.getX()-179)/33.8);//������ת�����±���
						if(allChesses[row][col]==null){//����ڸ�����λ�õ��ڿվͱ�ʾ��λ��û�������������
							allChesses[row][col]=Color.white;//����ɫ��ֵ����λ�õĶ�ά����
							stack.Push(row, col, Color.white);//������Ϣѹ��ջ
							canvas.repaint();//���»���,����������
							if(checkRowIsFive(row, col)||checkColIsFive(row, col)||checkRightBias(row, col)||checkLeftBias(row, col)){    //�����Զ��庯���ж��ĸ������Ƿ����������飬�Ƿ���
								isGameBegin=false;//��Ϸû�п�ʼ
								isGameOver=true;//��Ϸ����
								whoSmile=Color.white;//����������Ц
								canvas.repaint();//���»���
								JOptionPane.showMessageDialog(null,"��Ӯ�ˣ�");
								return;
							}
							if(isDogfall()==true){//������û�����ж��Ƿ���ƽ��
								isGameBegin=false;//��Ϸû�п�ʼ
								isGameOver=true;//��Ϸ����
								canvas.repaint();//���»���
								JOptionPane.showMessageDialog(null,"ƽ���ˣ�");
								return;
							}
							robot.cerebra(allChesses, canvas, Color.black,stack);//���û����������������
							if(checkRowIsFive(robot.getRow(), robot.getCol())||checkColIsFive(robot.getRow(), robot.getCol())||checkRightBias(robot.getRow(), robot.getCol())||checkLeftBias(robot.getRow(), robot.getCol())){  ////�����Զ��庯���ж��ĸ������Ƿ����������飬�Ƿ���
								isGameBegin=false;//��Ϸû�п�ʼ
								isGameOver=true;//��Ϸ����
								whoSmile=Color.black;//����������������Ц
								canvas.repaint();//���»���
								JOptionPane.showMessageDialog(null,"������Ӯ�ˣ�");
								return;
							}
							if(isDogfall()==true){//���������û��Ӯ���ж��Ƿ���ƽ��
								isGameBegin=false;//��Ϸû�п�ʼ
								isGameOver=true;//��Ϸ����
								canvas.repaint();//���»���
								JOptionPane.showMessageDialog(null,"ƽ���ˣ�");
								return;
							}
						}
						else {
							JOptionPane.showMessageDialog(null,"��λ���������ˣ�");
						}
				}
				else{
					if((e.getX()>=17&&e.getX()<=39)&&(e.getY()>=249&&e.getY()<=289)){  //��������ڸ������ʾ�����˿�ʼ
						for(int i=0;i<allChesses.length;i++){//�������
							for(int j=0;j<allChesses.length;j++){
								allChesses[i][j]=null;
							}
						}
						
						canvas.repaint();//���»���
						isGameBegin=true;//��Ϸ��ʼ
						isGameOver=false;//��Ϸ����
						playerTime=1800;//���³�ʼ���������ʱ��
						robotTime=1800;//���³�ʼ���������ʱ��
						whoSmile=null;//���³�ʼ��˭Ц
						stack.clearStack();//���ջ
						if(PlayerThread.getState()==Thread.State.NEW){//�������̻߳�û�������������߳�
							PlayerThread.start();
						}
						if(robotThread.getState()==Thread.State.NEW){//����������̻߳�û�������������߳�
							robotThread.start();
						}
						JOptionPane.showMessageDialog(null,"���Կ�ʼ�ˣ�");
						robot.cerebra(allChesses, canvas, Color.black,stack);//����������
						if(checkRowIsFive(robot.getRow(), robot.getCol())||checkColIsFive(robot.getRow(), robot.getCol())||checkRightBias(robot.getRow(), robot.getCol())||checkLeftBias(robot.getRow(), robot.getCol())){  //�жϻ������Ƿ���
							isGameBegin=false;
							isGameOver=true;
							whoSmile=Color.black;
							canvas.repaint();
							JOptionPane.showMessageDialog(null,"������Ӯ�ˣ�");
							return;
						}
						if(isDogfall()==true){//�Ƿ���ƽ��
							isGameBegin=false;
							isGameOver=true;
							canvas.repaint();
							JOptionPane.showMessageDialog(null,"ƽ���ˣ�");
							return;
						}
					}
					if((e.getX()>=48&&e.getX()<=72)&&(e.getY()>=249&&e.getY()<=289)){  //������ͣ����
						if(isGameOver==true){//�����Ϸ�����Ͳ�ִ����ͣ
							return;
						}
						isGameBegin=false;//������Ϸû�п�ʼ�����ﵽ��ͣЧ��
						JOptionPane.showMessageDialog(null,"��ͣ��");
					}
					if((e.getX()>=80&&e.getX()<=104)&&(e.getY()>=249&&e.getY()<=289)){ //������������
						if(isGameOver==true){//�����Ϸ�����Ͳ�ִ����ͣ
							return;
						}
						isGameBegin=true;//������Ϸ��ʼ�����ﵽ����Ч��
						JOptionPane.showMessageDialog(null,"������");
					}
					if((e.getX()>=113&&e.getX()<=136)&&(e.getY()>=249&&e.getY()<=289)){  //������������
						if(isGameOver==true || isGameBegin==false){ //�����Ϸû�п�ʼ������Ϸ�Ѿ������Ͳ��ܻ���
							return;
						}
						isUndo=true;
						for(int i=0;i<allChesses.length;i++){//�������
							for(int j=0;j<allChesses.length;j++){
								allChesses[i][j]=null;
							}
						}
						stack.Pop();//���ߵ���һ�����ջ
						LNode p=stack.getHeadNode().getNext();//��õ�һ�����
						while(p!=null){//�����һ�����Ϊ�գ���ʾ����Ϊ�գ�����Ϊ�վ�������������ֱ��ջΪ�ս���
							allChesses[p.getRow()][p.getCol()]=p.getChessColor();
							p=p.getNext();
						}
						canvas.repaint();
						unDoTime=5;//���5���Ӳ����������ʾ�������
						if(unDoThread.getState()==Thread.State.NEW){//������߳�û������������
							unDoThread.start();
						}
				}
			}
		}
			});
		this.setTitle("��������Ϸ");//���ñ���
		this.setResizable(false);//���ò��ܸı����С
		this.setVisible(true);//������ʾ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرմ����Ĳ���,�رմ�����������
	}
	
	public boolean isDogfall(){//�Ƿ���ƽ��
		if(stack.StackLength()==14*14){//ջ�м�¼�����̵���Ϣ��������һ����û�о���ʤ��,ջ�е�Ԫ�ظ����������̵��������ͱ�ʾƽ��
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean checkLeftBias(int row,int col){//�����б
		int leftBiasUp=checkLeftBiasUp(row, col);//�õ���б�ϵĸ���
		int leftBiasDown=checkLeftBiasDown(row, col);//�õ���б�µĸ���
		int sum=leftBiasUp+leftBiasDown-1;//�ڼ�������ж�����һ�����ӣ�����Ҫ��һ������
		if(sum>=5){//�Ѿ���������
			return true;
		}
		else{
			return false;
		}
	}
	
	public int checkLeftBiasDown(int row,int col){//�����б��
		int i=row;//���и�ֵ��i
		int j=col;//���и�ֵ��j
		int count=1;//��1��ֵ��count��count��ʾ���ӵĸ���
		while(i<allChesses.length-1&&j>0){//�ս�����
			if(allChesses[i][j]==allChesses[i+1][j-1]){//�����ͬ�ͽ���ͳ��
				count++;//��������һ
				i=i+1;//��i��j��ֵΪ�ϴαȽϵ�λ��
				j=j-1;
			}
			else {//�����һ�β���ͬ���˳�ѭ�����ȽϽ���
				break;
			}
		}
		return count;//����ͳ�Ƶ�������
	}
	
	public int checkLeftBiasUp(int row,int col){//ͳ����б�ϵ�������
		int i=row;
		int j=col;
		int count=1;
		while(i>0&&j<allChesses.length-1){
			if(allChesses[i-1][j+1]==allChesses[i][j]){
				count++;
				i=i-1;
				j=j+1;
			}
			else {
				break;
			}
		}
		return count;
	}

	public boolean checkRightBias(int row,int col){//ͳ����б��������
		int rightBiasUp=checkRightBiasUp(row, col);
		int rightBiasDown=checkRightBiasDown(row, col);
		int sum=rightBiasUp+rightBiasDown-1;
		if(sum>=5){
			return true;
		}
		else{
			return false;
		}
	}
	public int checkRightBiasUp(int row,int col){//ͳ����б�ϵ�������
		int i=row;
		int j=col;
		int count=1;
		while(i>0&&j>0){
			if(allChesses[i-1][j-1]==allChesses[i][j]){
				count++;
				i=i-1;
				j=j-1;
			}
			else {
				break;
			}
		}
		return count;
	}
	
	public int checkRightBiasDown(int row,int col){//ͳ����б�µ�������
		int i=row;
		int j=col;
		int count=1;
		while(i<allChesses.length-1&&j<allChesses.length-1){
			if(allChesses[i][j]==allChesses[i+1][j+1]){
				count++;
				i=i+1;
				j=j+1;
			}
			else {
				break;
			}
		}
		return count;
	}
	
	public boolean checkColIsFive(int row,int col){//ͳ���е�������
		int upCount=checkColUpIsFive(row, col);
		int downCount=checkColDownIsFive(row, col);
		int sum=upCount+downCount-1;
		if(sum>=5){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkRowIsFive(int row,int col){//ͳ���е�������
		int rightCount=checkRowRightIsFive(row, col);
		int leftCount=checkRowLeftIsFive(row,col);
		int sum=rightCount+leftCount-1;
		if(sum>=5){
			return true;
		}
		else{
			return false;
		}
	}
	public int checkColDownIsFive(int row,int col){//ͳ�����µ�������
		int count=1;
		for(int i=row;i<allChesses.length-1;i++){
			if(allChesses[i][col]==allChesses[i+1][col]){
				count++;
			}
			else{
				break;
			}
		}
		return count;
	}
	
	public int checkColUpIsFive(int row,int col){//ͳ�����ϵ�������
		int count=1;
		for(int i=row;i>0;i--){
			if(allChesses[i-1][col]==allChesses[i][col]){
				count++;
			}
			else{
				break;
			}
		}
		return count;
	}
	
	public int checkRowLeftIsFive(int row,int col){//ͳ�������������
		int count=1;
		for(int i=col;i>0;i--){
			if(allChesses[row][i-1]==allChesses[row][i]){
				count++;
			}
			else{
				break;
			}
		}
		return count;
	}
	public int checkRowRightIsFive(int row,int col){//ͳ�����ҵ�������
		int count=1;
		for(int i=col;i<allChesses.length-1;i++){
			if(allChesses[row][i]==allChesses[row][i+1]){
				count++;
			}
			else{
				break;
			}
		}
		return count;
	}
	public void setWindowPercent(int percent){//���ô�������Ļ�İٷֱ�
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(percent/100.0*screenSize.width),(int)(percent/100.0*screenSize.height));
	}
	
	public void setWindowCenter(){//���ô������
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		if(this.getWidth()>screenSize.width || this.getHeight()>screenSize.height){//�������Ŀ�Ⱥ͸߶ȴ�����Ļ�Ŀ�Ⱥ͸߶ȣ���ô�ͽ�λ������Ϊ0,0
			this.setLocation(0,0);
		}
		else{
			this.setLocation((screenSize.width-this.getWidth())/2,(screenSize.height-this.getHeight())/2);
		}
	}
	
	class MyCanvas extends JPanel{
		public void paint(Graphics g) {
			BufferedImage bImage=new BufferedImage(700,550,BufferedImage.TYPE_INT_RGB);//����BufferedImage����
			Graphics2D g2=bImage.createGraphics();//����һ��Graphcs2D���󣬲�������Ƶ�BufferedImage��
			graphics2d=g2;//��g2����ֵ��graphics2d��������
			g2.drawImage(imageBG, 0, 0,this.getWidth(),this.getHeight(), this);// ���Ʊ���ͼƬ
			g2.drawImage(imageMenu,6 , 242, 140, 52, this);//���Ʋ˵�ͼƬ
			g2.drawImage(imageW,26 , 42, imageW.getWidth(this),imageW.getHeight(this), this);//�����̶��ܲ�Цʱ��ͼƬ
			g2.drawImage(imageW,26 , 307, imageW.getWidth(this),imageW.getHeight(this), this);
			if(whoSmile==Color.black){//���whoSmile��ֵ��black�ͱ�ʾ�ڷ�Ӯ���ںڷ�λ�û���Ц��
				g2.drawImage(imageX,26 , 307, imageX.getWidth(this),imageX.getHeight(this), this);
			}
			else if(whoSmile==Color.white){//�����white��ʾ�׷�Ӯ���ڰ׷�λ�û���Ц��
				g2.drawImage(imageX,26 , 42, imageX.getWidth(this),imageX.getHeight(this), this);
			}
			g2.setColor(Color.white);//����ͼ�������óɰ�ɫ
			int minute=playerTime/60;//������ҵ�ʱ��õ�����
			int second=playerTime%60;//������ҵ�ʱ��õ�����
			String minuteStr=minute<10?"0"+minute:minute+"";//�����������һλ��һ��0�����λ�����򲻼�0������Ҳ��������
			String secondStr=second<10?"0"+second:second+"";
			g2.drawString(minuteStr+":"+secondStr,77,209);//��BufferedImage�л���ʱ���ַ���
			minute=robotTime/60;//���ݻ����˵�ʱ��õ�����
			second=robotTime%60;//���ݻ����˵�ʱ��õ�����
			minuteStr=minute<10?"0"+minute:minute+"";//�����������һλ��һ��0�����λ�����򲻼�0������Ҳ��������
			secondStr=second<10?"0"+second:second+"";
			g2.drawString(minuteStr+":"+secondStr,77,477);//��BufferedImage�л���ʱ���ַ���
			for(int i=0;i<allChesses.length;i++){//allChess��ά�����¼������������Ϣ,������ǽ���ά�����е���Ϣ��ӳ����
				for(int j=0;j<allChesses.length;j++){
					if(allChesses[i][j]!=null){//���ĳһ��Ԫ�ز�Ϊ�վͱ�ʾ��λ��������
						if(allChesses[i][j]==Color.black){//���Ԫ��ֵ��black������Ӧ��λ�û��ƺ�ɫ������
							g2.drawImage(imageBlack, (int)((179+j*34.8)+2), (int)((30+i*33.4)+2),31,31, this);
						}                   //������֮��ľ�����34.8,j*34.8�ͱ�ʾ�ж��ٸ�34.8�õ����ȣ��ڼ���179�õ������ӵ�x���� 
						else{//�����ǰ�ɫ����Ӧλ�û�������
							g2.drawImage(imageWhite, (int)((179+j*34.8)+2), (int)((30+i*33.4)+2),31,31, this);
						}
					}
				}
			}
			if(stack.getStackTop()!=null){//���ջ��Ԫ��,ջ��Ԫ�ؼ�¼�����һ�����λ�ú���ɫ��Ϣ��Ϊ�������֪�����һ�����λ�ã����Ծ������һ���廭һ�����Ρ�
				g2.drawImage(imageRim, (int)((179+stack.getStackTop().getCol()*34.8)+2), (int)((30+stack.getStackTop().getRow()*33.4)+2),31,31, this);
			}
			g.drawImage(bImage, 0, 0, this);//��BufferedImage����JPanel��
		}
	}
	
	public static void main(String[] args){
		new PlayChess();//����һ��PlayChess������Ϸ��ʼ
	}
}

