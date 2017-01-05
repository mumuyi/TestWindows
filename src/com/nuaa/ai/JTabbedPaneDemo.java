package com.nuaa.ai;

import java.awt.*; 
import javax.swing.*;

import org.jfree.chart.ChartPanel;
 
/**
 * <p>Title: ѡ���ʾ</p>
 * <p>Description: ������һ��ѡ���ʾ�������ͬ�Ŀ�Ƭ����ʾ�����ݲ�ͬ</p>
 */ 
 
public class JTabbedPaneDemo extends JPanel { 
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 8858707599953364911L;

	public JTabbedPaneDemo() { 
        super(new GridLayout(1, 1)); 
 
        //ImageIcon icon = createImageIcon("images/MyIcon.gif"); 
        JTabbedPane tabbedPane = new JTabbedPane(); 
 
        Component panel1 = makeTextPanel(); 
        tabbedPane.addTab("One", null, panel1, "��һ����Ƭ��ʾ��Ϣ��"); 
        tabbedPane.setSelectedIndex(0); 
 
        Component panel2 = makeTextPanel(); 
        tabbedPane.addTab("Two", null, panel2, "�ڶ�����Ƭ��ʾ��Ϣ��"); 
 
        Component panel3 = makeTextPanel(); 
        tabbedPane.addTab("Three", null, panel3, "��������Ƭ��ʾ��Ϣ��"); 
 
        Component panel4 = makeTextPanel(); 
        tabbedPane.addTab("Four", null, panel4, "���ĸ���Ƭ��ʾ��Ϣ��"); 
 
        // ��ѡ���ӵ�panl�� 
        add(tabbedPane); 
    } 
 
    /**
     * <br>
     * ����˵���������Ϣ��ѡ��� <br>
     * ���������String text ��ʾ����Ϣ���� <br>
     * �������ͣ�Component ��Ա����
     */ 
    protected Component makeTextPanel() { 
        JPanel panel = new JPanel(false);  

        DataWindow dw=new DataWindow();
        panel.add(new ChartPanel(dw.getPireChar()));
        
        
        //DynamicData dyd=new DynamicData();
        //panel.add(new ChartPanel(dyd.getJFreeChart()));
        //dyd.beginToshow();
        
         
        return panel; 
    } 
 
    /**
     * <br>
     * ����˵�������ͼƬ <br>
     * ���������String path ͼƬ��·�� <br>
     * �������ͣ�ImageIcon ͼƬ����
     */ 
    protected static ImageIcon createImageIcon(String path) { 
        // java.net.URL imgURL = TabbedPaneDemo.class.getResource(path); 
        if (path != null) { 
            return new ImageIcon(path); 
        } else { 
            System.out.println("Couldn't find file: " + path); 
            return null; 
        } 
    } 
 
    public static void main(String[] args) { 
        // ʹ��Swing�������� 
        // JFrame.setDefaultLookAndFeelDecorated(true); 
 
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) { 
             
        } 
        // �������� 
        JFrame frame = new JFrame("TabbedPaneDemo"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.getContentPane().add(new JTabbedPaneDemo(), BorderLayout.CENTER); 
 
        // ��ʾ���� 
        frame.setSize(400, 200); 
        frame.setVisible(true); 
    } 
}  