package BronzeAndFaith.GUI;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;


public class ScreenManager {

	private GraphicsDevice device;
	
	public ScreenManager(){
		GraphicsEnvironment environment= GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
	}
	
	public void setFullScreen(DisplayMode displayMode, JFrame window){
		window.setUndecorated(true);
		window.setResizable(false);
		
		device.setFullScreenWindow(window);
		if(displayMode != null && device.isDisplayChangeSupported()){
			try{
				device.setDisplayMode(displayMode);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public Window getFullScreenWindow(){
		return device.getFullScreenWindow();
	}
	
	public void restoreScreen(){
		Window window = device.getFullScreenWindow();
		if(window != null){
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}
}
