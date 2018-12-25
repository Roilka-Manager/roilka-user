package com.roilka.www.test.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class LocalParams {

	LocalParams(){
		
	}
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		int pin = 12345;

		System.out.println("WELCOME TO THE BANK OF MITCHELL.");
		System.out.print("ENTER YOUR PIN: ");
		int entry = keyboard.nextInt();

		while ( entry != pin )
		{
			System.out.println("\nINCORRECT PIN. TRY AGAIN.");
			System.out.print("ENTER YOUR PIN: ");
			entry = keyboard.nextInt();
		}

		System.out.println("\nPIN ACCEPTED. YOU NOW HAVE ACCESS TO YOUR ACCOUNT.");
		
//		getLocalNameAndIP();  
//		getScreen();
	    }
	//获取本机名称和IP
	public static void getLocalNameAndIP() {
		 InetAddress ia=null;
         try {
            ia = ia.getLocalHost();
             String localname=ia.getHostName();
             String localip=ia.getHostAddress();
                System.out.println("本机名称是："+ localname);
                System.out.println("本机的ip是 ："+localip);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
	}
	//获取电脑屏幕信息
	public static void getScreen()
	    {
	         Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
	         int width = (int)screensize.getWidth();  
	         int height = (int)screensize.getHeight();
	         System.out.println("宽的像素："+width+"高的像素："+height);
	         //获取屏幕的dpi
	         int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
	         System.out.println(dpi);
	         //根据dpi和像素，可以计算物理尺寸
	         System.out.println("宽："+width/dpi+"高："+height/dpi);
	    }
}
