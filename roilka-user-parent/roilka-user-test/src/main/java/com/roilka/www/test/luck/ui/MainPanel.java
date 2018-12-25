package com.roilka.www.test.luck.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.roilka.www.test.luck.interfaces.MainInterface;


public class MainPanel extends JPanel implements ActionListener {
	// 版本序列号
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private BufferedImage buff;
	// 定时器
	private Timer timer;
	// 存放背景图片
	private BufferedImage image;
	// 存放背景图片2
    private BufferedImage image2;
    // 存放背景图片3
    private BufferedImage image3;
   // 背景图列表
 	private List<BufferedImage> imageBackList = new ArrayList<BufferedImage>();
	// 候选者图片列表
	private List<BufferedImage> imageList = new ArrayList<BufferedImage>();
	// 候选者名字列表
	private List<String> imageNameList = new ArrayList<String>();
	// 使用单例模式
	private static MainPanel mainPanel = new MainPanel();
	// 生成随机数
	private Random random = new Random();

	private static int index1 = 0;
	private static int index2 = 1;
	private static int index3 = 2;
	private static int cnum = 0;
	// 开始标志
	public static boolean start = false;
	// 奖项是否还有剩余
	public static boolean remain = true;
	// 一、二、三等奖数量
	public static int firstPrize = 3;
	public static int secondPrize = 12;
	public static int thirdPrize = 20;
	// 默认为单人模式
	public static int drawMode = 1;
	// 画板
	public static Graphics2D g2d;
	
	private MainPanel() {
		// 完成panel的初始化(添加计时器)
		init();
		// 添加组件,如pen(Graphics),双缓冲BufferedImage
		initComs();
	}

	public static MainPanel getInstance() {
		return mainPanel;
	}

	private void init() {
		String name = null;
		try {
			image = ImageIO.read(new File("classpath:image/backf002.jpg"));// 读取关于游戏背景图片
			image3 = ImageIO.read(new File("classpath:image/backf001.jpg"));// 读取关于游戏背景图片
			image2 = ImageIO.read(new File("classpath:image/back003.jpg"));// 读取关于游戏背景图片
			File file = new File("candidate");
			String[] namel = temp.split("。"); 
			if (file.isDirectory()) {
				String[] fileList = file.list();
				for (int i = 0; i < fileList.length; i++) {
					if (i%3 == 0) {
						imageBackList.add(ImageIO.read(new File("classpath:image/backf001.jpg")));// 读取关于游戏背景图片
					}else if (i%3 == 1) {
						imageBackList.add(ImageIO.read(new File("classpath:image/backf001.jpg")));// 读取关于游戏背景图片
					}else if (i%3 == 2) {
						imageBackList.add(ImageIO.read(new File("classpath:image/backf001.jpg")));// 读取关于游戏背景图片
					}
					imageList.add(ImageIO.read(new File("classpath:candidate/"
							+ fileList[i])));
					// 分离出候选者姓名
					int indexOf = fileList[i].toString().indexOf('.');
					name = namel[i%10];
					imageNameList.add(name);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// panel中添加计时器,并启动计时器进行触发控制
		timer = new Timer(MainInterface.TIME, this);
		timer.setActionCommand("begin");
		timer.start();
	}

	private void initComs() {
		// 使用双缓冲技术
		buff = new BufferedImage(MainInterface.WIDTH, MainInterface.HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * 此方法会自动被调用进行绘制
	 */
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D) g;
		g2d.drawImage(image2,0,0, null);
		
		g2d.setColor(Color.WHITE); // 颜色带透明
		// 绘制圆角矩形
		g2d.fillRoundRect(320, 580, 500, 50, 30, 40);
		
		
		if (remain) { // 奖项有剩余
			if (start) { // 开始状态
				switch (MainPanel.drawMode) {
				case 1: // 单人模式
					index1 = random.nextInt(imageList.size());
					break;
					// 保证index3、index2、index1均不相等
				default:
					break;
				}
				// 在指定位置绘制中奖者图片和姓名
				g2d.drawImage(imageBackList.get(index1%3),300,10, null);
				g2d.setColor(Color.RED);
				g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 30));
				g2d.drawString(imageNameList.get(index1), 325, 620);
				printString(imageNameList.get(index1), 20*(cnum ++));
				long s =System.currentTimeMillis()/(1000*6);
				if (s/10 == 1) {
					try {
						System.out.println("come in ");
						printString(imageNameList.get(index1), 20*(cnum ++));
						Thread.sleep(3000l);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} else { // 单击鼠标左键,停止
				long s =System.currentTimeMillis()/(1000*6);
				
				g2d.drawImage(imageBackList.get(index1%3),300,10, null);
				g2d.setColor(Color.RED);
				g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 30));
				g2d.drawString(imageNameList.get(index1), 325, 620);

				if (s/10 == 1) {
					try {
						printString(imageNameList.get(index1), 20*(cnum ++));
						Thread.sleep(3000l);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else { // 奖项没有剩余
			g2d.drawImage(imageBackList.get(index1%3),300,10, null);
			g2d.setColor(Color.RED);
			g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 30));
			g2d.drawString(imageNameList.get(index1), 325, 620);

		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);		
		String hint = "";
		if (drawMode == 1) {
			hint = "单人模式";
		} else if (drawMode == 2) {
			hint = "双人模式";
		} else {
			hint = "三人模式";
		}
		g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 20));
		g2d.setColor(Color.RED);
		g2d.drawString("当前奖项剩余数为: ", MainInterface.PRIZE_X + 290, 
				MainInterface.PRIZE_Y - 270);
//		g2d.drawString(hint, MainInterface.PRIZE_X + 415,
//				MainInterface.PRIZE_Y - 70);	
		
		

		g2d.setColor(MainInterface.BORDER_COLOR_HYALINE); // 颜色带透明
		// 绘制圆角矩形
		g2d.fillRoundRect(900, 150, 250, 200, 30, 40);

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 20));

		g2d.drawString("一等奖剩余: ", MainInterface.PRIZE_X + 300, MainInterface.PRIZE_Y - 200);
		g2d.drawString("二等奖剩余: ", MainInterface.PRIZE_X + 300,
				MainInterface.PRIZE_Y - 150);
		g2d.drawString("三等奖剩余: ", MainInterface.PRIZE_X + 300,
				MainInterface.PRIZE_Y - 100);
		
		g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 40));
		g2d.setColor(Color.RED);
		g2d.drawString(String.valueOf(firstPrize), MainInterface.PRIZE_X + 440,
				MainInterface.PRIZE_Y - 190);
		g2d.drawString(String.valueOf(secondPrize),
				MainInterface.PRIZE_X + 440, MainInterface.PRIZE_Y - 140);
		g2d.drawString(String.valueOf(thirdPrize), MainInterface.PRIZE_X + 440,
				MainInterface.PRIZE_Y - 90);
		
	}

	// 监听
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("begin".equals(cmd)) {
			// 重新绘制MainPanel
			repaint();
		}
	}
	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {
			long s =System.currentTimeMillis()/(1000);
			System.out.println("haha"+s);
				try {
					Thread.sleep(1000l);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(s);
			
		}
		
	}
	public void printString(String name,int num) {
		g2d.setFont(new Font(MainInterface.FONT, Font.BOLD, 14));
		g2d.setColor(Color.RED);
		g2d.drawString(name, MainInterface.PRIZE_X -590, 
				MainInterface.PRIZE_Y - 270 );
	}
	private String temp = "旧时王谢堂前燕，飞入寻常百姓家。两座楼台钟鼓响，一轮明月满乾坤。玲珑骰子安红豆，入骨相思知不知。无端坠入红尘梦，惹却三千烦恼丝。衣带渐宽终不悔，为伊消得人憔悴。桃李春风一杯酒，江湖夜雨十年灯。伤心桥下春波绿，曾是惊鸿照影来。此去泉台招旧部，旌旗十万斩阎罗。仰天大笑出门去，我辈岂是蓬蒿人。一生一世一双人，半醉半醒半浮生";
}