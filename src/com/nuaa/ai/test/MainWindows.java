package com.nuaa.ai.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MainWindows {
	private static JFrame jf = new JFrame();
	private static MainWindows mw;

	public static JPanel GImage = null;

	private static int WindowsHeight = 0;
	private static int WindowsWidth = 0;

	public static final Color MY_COLOR = new Color(0xffffff);

	private static int moduleHeight = 0;
	private static int moduleWidth = 0;
	private static int moduleY1 = 0;
	private static int moduleY2 = 0;
	private static int moduleXGape = 0;

	private JLabel[] module = new JLabel[8];

	public static void main(String[] args) throws IOException {
		mw = new MainWindows();

		mw.MaxFrame(jf);

		// 设置可见性;
		jf.setVisible(true);

		jf.setTitle("   卫星健康监控系统");

		Dimension d = jf.getSize();
		WindowsWidth = (int) d.getWidth();
		WindowsHeight = (int) d.getHeight();
		System.out.println("Windows Width:" + WindowsWidth);
		System.out.println("Windows Height:" + WindowsHeight);

		// 利用JPanel添加背景图片
		GImage = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6668680529227025511L;

			protected void paintComponent(Graphics g) {
				ImageIcon icon = new ImageIcon("F:/Java/TestWindows/image/background.jpg");
				Image img = icon.getImage();
				g.drawImage(img, 0, 0, WindowsWidth, WindowsHeight, null);
			}
		};
		jf.add(GImage);

		mw.iniModuleParameter();
		mw.iniModuleView();

		// 设置窗口关闭事件;
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				// 加入动作
				System.exit(0);
			}
		});
	}

	//最大化窗口;
	public void MaxFrame(JFrame jf) {
		// 将一个Frame最大化
		if (jf.isUndecorated()) {// 无边框窗口
			jf.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		} else {
			jf.setExtendedState(jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
	}

	//画一个标签;
	public JLabel showInformation(int x, int y) {
		JLabel label = new JLabel();
		label.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		label.setBounds(x, y, moduleWidth, moduleHeight);

		label.setOpaque(true);// 设置组件JLabel不透明，只有设置为不透明，设置背景色才有效
		label.setBackground(Color.WHITE);

		label.setText("Information Test");

		// 设置边框;
		label.setBorder(new LineBorder(Color.GREEN, 2, true));

		// jf.add(label);
		jf.getLayeredPane().add(label, new Integer(50));

		return label;

	}

	// 画一个正方形;(repaint() 之后会消失 考虑写在paint()函数里面或者在repaint() 之后再画一次);
	public void drawMyRect(int x, int y, int length, int width) {
		Graphics g = jf.getGraphics();
		Graphics2D g2d = (Graphics2D) g; // 首先把g转换成g2d
		g2d.setColor(MY_COLOR);
		g2d.setStroke(new BasicStroke(10.0f));// 关键,设置画笔的宽度. 越大,边框越粗
		g2d.drawRect(x, y, length, width);
	}

	//初始化标签所使用的参数;
	public void iniModuleParameter() {
		moduleHeight = WindowsHeight / 20 * 3;
		moduleWidth = WindowsWidth / 20 * 3;
		moduleY1 = WindowsHeight / 10 * 3;
		moduleY2 = moduleY1 + WindowsHeight / 10 + moduleHeight;
		moduleXGape = WindowsWidth / 25 * 2;

		System.out.println(moduleHeight + "      " + moduleWidth);
	}

	//初始化标签视图;
	public void iniModuleView() {
		//设置8个标签的位置;
		module[0] = mw.showInformation(moduleXGape, moduleY1);
		module[1] = mw.showInformation(moduleXGape * 2 + moduleWidth, moduleY1);
		module[2] = mw.showInformation(moduleXGape * 3 + moduleWidth * 2, moduleY1);
		module[3] = mw.showInformation(moduleXGape * 4 + moduleWidth * 3, moduleY1);

		module[4] = mw.showInformation(moduleXGape, moduleY2);
		module[5] = mw.showInformation(moduleXGape * 2 + moduleWidth, moduleY2);
		module[6] = mw.showInformation(moduleXGape * 3 + moduleWidth * 2, moduleY2);
		module[7] = mw.showInformation(moduleXGape * 4 + moduleWidth * 3, moduleY2);

		//重绘;
		jf.repaint();

		//添加点击事件;
		for (int i = 0; i < 8; i++) {
			module[i].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					System.out.println("鼠标进入");
					((JLabel) e.getSource()).setBackground(Color.BLACK);
				}

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						System.out.println("鼠标被双击了");
					} else {
						System.out.println("鼠标被点击");
					}

				}

				public void mouseExited(MouseEvent e) {
					System.out.println("鼠标移出");
					((JLabel) e.getSource()).setBackground(Color.WHITE);
				}
			});
		}

		//鼠标放上去变成小手;
		for (int i = 0; i < 8; i++) {
			module[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
}
