package com.nuaa.ai;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.nuaa.ai.DynamicData.DemoPanel.MyThread;

public class MainWindows extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JFrame jf;
	private static MainWindows mw;

	public static JPanel GImage = null; 
	
	private static int WindowsHeight = 0;
	private static int WindowsWidth = 0;

	public static final Color MY_COLOR = new Color(0xffffff);
	
	public static void main(String[] args) throws IOException {
		mw = new MainWindows();
		jf = mw.launchFrame();
		mw.MaxFrame(jf);
		
		Dimension d = jf.getSize();
		WindowsWidth = (int) d.getWidth();
		WindowsHeight = (int) d.getHeight();
		System.out.println("Windows Width:" + WindowsWidth);
		System.out.println("Windows Height:" + WindowsHeight);
		
        // ����JPanel��ӱ���ͼƬ  
        GImage = new JPanel() {  
            protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("F:/Java/TestWindows/image/background.jpg");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, WindowsWidth,  
                		WindowsHeight, null);  
            }  
        }; 
        jf.add(GImage);
        //jf.getLayeredPane().add(GImage, new Integer(100));
        
		// ��ʾͼƬ;
		ImageIcon imagetoshow = new ImageIcon("F:/Java/TestWindows/image/image1.jpg");
		JLabel showimagelabel = new JLabel(imagetoshow);
		jf.getLayeredPane().add(showimagelabel, new Integer(100));
		showimagelabel.setBounds(WindowsWidth / 15, WindowsHeight / 15, WindowsWidth / 20, WindowsWidth / 20);
		
		
		// ��ʾ����;
		ImageIcon satelliteimage = new ImageIcon("F:/Java/TestWindows/image/satellite.jpg");
		JLabel satellitelabel = new JLabel(satelliteimage);
		jf.getLayeredPane().add(satellitelabel, new Integer(100));
		satellitelabel.setBounds((WindowsWidth-satelliteimage.getIconWidth())/2, (WindowsHeight-satelliteimage.getIconHeight())/2, satelliteimage.getIconWidth(), satelliteimage.getIconHeight());

		
		//���ñ߿�;
		showimagelabel.setBorder(new LineBorder(Color.GREEN,3,false));
		
		
		// �������ʱ��;
		showimagelabel.addMouseListener(new MouseAdapter()// ������
		{
			private int count = 1;
			private int mouseCount = 1;
			private JLabel label;

			public void mouseEntered(MouseEvent e) {
				System.out.println("������" + count);
				// ��ʾ��Ϣ;
				//int showX = showimagelabel.getX();
				//int showY = showimagelabel.getY();
				//label = mw.showInformation(showX, showY);
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("��걻˫����");
				} else {
					System.out.println("��걻���" + mouseCount++);
					//DataWindow.showMyData();
					//DynamicData.showDynamicData();
					
					//���Թ��;
					//int radii=300;
					//int Ovalx=WindowsWidth/2;
					//int Ovaly=WindowsHeight/2;
					//mw.Simulationsatellite(Ovalx,Ovaly,radii,0);

					
					MyThread2 myThread1;
					myThread1=new MyThread2();
					myThread1.start();
				}

			}

			public void mouseExited(MouseEvent e) {
				System.out.println("����Ƴ�" + count++);
				//������Ϣ;
				//jf.remove(label);
				//jf.getLayeredPane().remove(label);
				//jf.repaint();
			}
		});
		
		
		//���ô��ڹر��¼�;
		mw.addWindowListener(new WindowAdapter() {  
			public void windowClosing(WindowEvent e) {  
				super.windowClosing(e);  
				//���붯��  
				System.exit(0);
			 }		  
			});

		
		
		
		
		
		
		
	}

	public JFrame launchFrame() {
		//this.setSize(300, 300);// ���ÿ�Ⱥ͸߶�
		//this.setLocation(300, 266);// ���ó�ʼλ��
		this.setVisible(true);// ���ÿɼ�
		return this;
	}

	public void MaxFrame(JFrame jf) {
		// ��һ��Frame���
		if (jf.isUndecorated()) {// �ޱ߿򴰿�
			jf.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		} else {
			jf.setExtendedState(jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
	}

	public JLabel showInformation(int x, int y) {
		JLabel label = new JLabel();
		label.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		label.setBounds(x + WindowsWidth / 20 + 20, y + 10, WindowsWidth / 15, WindowsHeight / 15);

		label.setOpaque(true);// �������JLabel��͸����ֻ������Ϊ��͸�������ñ���ɫ����Ч
		label.setBackground(Color.WHITE);

		label.setText("Information Test");

		//���ñ߿�;
		label.setBorder(new LineBorder(Color.GREEN,1,true));
		
		//jf.add(label);
		jf.getLayeredPane().add(label, new Integer(50));
		
		
		jf.repaint();

		return label;
		
		
	}
	
	//��һ��������;(repaint() ֮�����ʧ  ����д��paint()�������������repaint() ֮���ٻ�һ��);
	public void drawMyRect(int x,int y,int length,int width){
		Graphics g=jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g;  //���Ȱ�gת����g2d
		g2d.setColor(MY_COLOR);
		g2d.setStroke(new BasicStroke(10.0f));//�ؼ�,���û��ʵĿ��.  Խ��,�߿�Խ��
		g2d.drawRect(x,y,length,width);
	}
	
	
	
	
	//Ŀǰ���ɴ�������;Ҳ����Ҫ����repaint() ʱ��ͻȻ��ʧһ��;������Ҫ��취���;
	//��һ��Բ,ģ�����ǹ��;
	public void drawMyOval(int x,int y,int length,int width){
		Graphics g=jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g;  //���Ȱ�gת����g2d
		g2d.setColor(MY_COLOR);
		g2d.setStroke(new BasicStroke(5.0f));//�ؼ�,���û��ʵĿ��.  Խ��,�߿�Խ��
		g2d.drawOval(x-length/2,y-length/2,length,width);
	}
	
	//��һ����,ģ������;
	public void drawMysatellite(int x,int y,int radii){
		int length=8;
		Graphics g=jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g;  //���Ȱ�gת����g2d
		g2d.setColor(Color.GREEN);
		g2d.setStroke(new BasicStroke(5.0f));//�ؼ�,���û��ʵĿ��.  Խ��,�߿�Խ��
		g2d.drawOval(x-length/2,y-length/2,length,length);
	}
	
	public void Simulationsatellite(int Ovalx,int Ovaly,int radii,int radio){
		mw.drawMyOval(Ovalx,Ovaly,radii,radii);
		getsatelliteLocation(Ovalx,Ovaly,radii,radio);
	}
	
	public void getsatelliteLocation(int Ovalx,int Ovaly,int radii,int radio){
		int y=(int) (Math.sin(Math.PI*radio/180)*radii/2);
		int x=(int) (Math.cos(Math.PI*radio/180)*radii/2);
		
		x+=Ovalx;
		y+=Ovaly;
		
		mw.drawMysatellite(x,y,radii);
	}
	
	
	static class MyThread2 extends Thread {
		int Ovalx=WindowsWidth/2;
		int Ovaly=WindowsHeight/2;
		int radii=300;
		int radio=0;
		public void run() {
			while (true) {
				
				mw.Simulationsatellite(Ovalx,Ovaly,radii,radio);				
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				radio+=30;
				
				
				
				//��������ʵ�ַ�ʽ֮�⣬�ɿ��Ǹ������ǵ���ʾ����ɫ;
				//����һ��color[5],��radio%360==0 ʱ,��ɫ i+1;��ͼʱȡ��ɫΪcolor[i];Ҳ����ʵ��,������Դ���ĸ���;
				jf.repaint();
				try {
					this.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
}
