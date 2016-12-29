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
		
        // 利用JPanel添加背景图片  
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
        
		// 显示图片;
		ImageIcon imagetoshow = new ImageIcon("F:/Java/TestWindows/image/image1.jpg");
		JLabel showimagelabel = new JLabel(imagetoshow);
		jf.getLayeredPane().add(showimagelabel, new Integer(100));
		showimagelabel.setBounds(WindowsWidth / 15, WindowsHeight / 15, WindowsWidth / 20, WindowsWidth / 20);
		
		
		// 显示卫星;
		ImageIcon satelliteimage = new ImageIcon("F:/Java/TestWindows/image/satellite.jpg");
		JLabel satellitelabel = new JLabel(satelliteimage);
		jf.getLayeredPane().add(satellitelabel, new Integer(100));
		satellitelabel.setBounds((WindowsWidth-satelliteimage.getIconWidth())/2, (WindowsHeight-satelliteimage.getIconHeight())/2, satelliteimage.getIconWidth(), satelliteimage.getIconHeight());

		
		//设置边框;
		showimagelabel.setBorder(new LineBorder(Color.GREEN,3,false));
		
		
		// 设置鼠标时间;
		showimagelabel.addMouseListener(new MouseAdapter()// 鼠标监听
		{
			private int count = 1;
			private int mouseCount = 1;
			private JLabel label;

			public void mouseEntered(MouseEvent e) {
				System.out.println("鼠标进入" + count);
				// 显示信息;
				//int showX = showimagelabel.getX();
				//int showY = showimagelabel.getY();
				//label = mw.showInformation(showX, showY);
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("鼠标被双击了");
				} else {
					System.out.println("鼠标被点击" + mouseCount++);
					//DataWindow.showMyData();
					//DynamicData.showDynamicData();
					
					//测试轨道;
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
				System.out.println("鼠标移出" + count++);
				//隐藏信息;
				//jf.remove(label);
				//jf.getLayeredPane().remove(label);
				//jf.repaint();
			}
		});
		
		
		//设置窗口关闭事件;
		mw.addWindowListener(new WindowAdapter() {  
			public void windowClosing(WindowEvent e) {  
				super.windowClosing(e);  
				//加入动作  
				System.exit(0);
			 }		  
			});

		
		
		
		
		
		
		
	}

	public JFrame launchFrame() {
		//this.setSize(300, 300);// 设置宽度和高度
		//this.setLocation(300, 266);// 设置初始位置
		this.setVisible(true);// 设置可见
		return this;
	}

	public void MaxFrame(JFrame jf) {
		// 将一个Frame最大化
		if (jf.isUndecorated()) {// 无边框窗口
			jf.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		} else {
			jf.setExtendedState(jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
	}

	public JLabel showInformation(int x, int y) {
		JLabel label = new JLabel();
		label.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		label.setBounds(x + WindowsWidth / 20 + 20, y + 10, WindowsWidth / 15, WindowsHeight / 15);

		label.setOpaque(true);// 设置组件JLabel不透明，只有设置为不透明，设置背景色才有效
		label.setBackground(Color.WHITE);

		label.setText("Information Test");

		//设置边框;
		label.setBorder(new LineBorder(Color.GREEN,1,true));
		
		//jf.add(label);
		jf.getLayeredPane().add(label, new Integer(50));
		
		
		jf.repaint();

		return label;
		
		
	}
	
	//画一个正方形;(repaint() 之后会消失  考虑写在paint()函数里面或者在repaint() 之后再画一次);
	public void drawMyRect(int x,int y,int length,int width){
		Graphics g=jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g;  //首先把g转换成g2d
		g2d.setColor(MY_COLOR);
		g2d.setStroke(new BasicStroke(10.0f));//关键,设置画笔的宽度.  越大,边框越粗
		g2d.drawRect(x,y,length,width);
	}
	
	
	
	
	//目前依旧存在问题;也是主要是在repaint() 时会突然消失一下;后面需要想办法解决;
	//画一个圆,模拟卫星轨道;
	public void drawMyOval(int x,int y,int length,int width){
		Graphics g=jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g;  //首先把g转换成g2d
		g2d.setColor(MY_COLOR);
		g2d.setStroke(new BasicStroke(5.0f));//关键,设置画笔的宽度.  越大,边框越粗
		g2d.drawOval(x-length/2,y-length/2,length,width);
	}
	
	//画一个点,模拟卫星;
	public void drawMysatellite(int x,int y,int radii){
		int length=8;
		Graphics g=jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g;  //首先把g转换成g2d
		g2d.setColor(Color.GREEN);
		g2d.setStroke(new BasicStroke(5.0f));//关键,设置画笔的宽度.  越大,边框越粗
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
				
				
				
				//除了这种实现方式之外，可考虑更换卫星的显示的颜色;
				//建立一个color[5],当radio%360==0 时,颜色 i+1;画图时取颜色为color[i];也可以实现,而且资源消耗更少;
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
