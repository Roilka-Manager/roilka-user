package com.roilka.www.test.luck.ui;

import javax.swing.JFrame;

import com.roilka.www.test.luck.handler.MouseHandler;
import com.roilka.www.test.luck.interfaces.MainInterface;


public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	// 得到唯一的MainPanel实例
	private MainPanel panel = MainPanel.getInstance();
	private MouseHandler mouse = new MouseHandler();

	public MainFrame() {
		// 初始化开始界面的一些信息
		init();
		// 初始化Frame上的组件
		initComs();
		// 添加监听器
		initListener();
	}

	private void initComs() {
		// 添加panel
		add(panel);
	}

	private void initListener() {
		// 鼠标监听
		addMouseListener(mouse);
	}

	private void init() {
		setTitle("途虎养车-商业用户部门-简易抽奖系统");
		setResizable(false);
		setSize(MainInterface.WIDTH, MainInterface.HEIGHT);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
