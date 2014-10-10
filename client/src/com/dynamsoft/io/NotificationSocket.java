package com.dynamsoft.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.dynamsoft.twain.NotificationService;

public class NotificationSocket extends Thread {
	private NotificationService mService;
	private Socket mSocket;
	private BufferedOutputStream mOutputStream;
	private BufferedInputStream mInputStream;
	
	public NotificationSocket(NotificationService service) {
		mService = service;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			mSocket = new Socket("192.168.8.84", 2015);
			mOutputStream = new BufferedOutputStream(mSocket.getOutputStream());
			mInputStream = new BufferedInputStream(mSocket.getInputStream());
            
			byte[] buff = new byte[256];
			int len = 0;
            String msg = "I'm client. Please send me any notification";
            mOutputStream.write(msg.getBytes());
            mOutputStream.flush();
            
            while ((len = mInputStream.read(buff)) != -1) {
            	msg = new String(buff, 0, len);
            	mService.showAppNotification("server", msg);
            }
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			onClose();
		}
	}
	
	public void onClose() {
		try {
			if (mSocket != null) {
				mSocket.close();
				mSocket = null;
			}
			
			if (mOutputStream != null) {
				mOutputStream.close();
				mOutputStream = null;
			}
			
			if (mInputStream != null) {
				mInputStream.close();
				mInputStream = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}