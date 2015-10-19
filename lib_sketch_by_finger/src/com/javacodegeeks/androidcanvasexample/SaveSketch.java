package com.javacodegeeks.androidcanvasexample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class SaveSketch {
	
	MediaScannerConnection msConn;

	public Bitmap saveSketch(Context context, CanvasView view, String fileName) {

//        String fileName = "sample.png";
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/"+ fileName);
	
	    FileOutputStream ostream = null;
	    Bitmap save = null;
        try
	       {
		       ostream = new FileOutputStream(file);
		
		       System.out.println(ostream);
		
		       Bitmap well = view.getBitmap();
		       save = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
		       Paint paint = new Paint();
		       paint.setColor(Color.WHITE);
		       Canvas now = new Canvas(save);
		       now.drawRect(new Rect(0,0,320,480), paint);
		       now.drawBitmap(well, new Rect(0,0,well.getWidth(),well.getHeight()), new Rect(0,0,320,480), null);
		
		       if(save == null) {
		           System.out.println("NULL bitmap save\n");
		       }
		       save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
		       
		       scanPhoto(fileName, context);
		       
	       }catch (NullPointerException e) 
	       {
	           e.printStackTrace();
	           Toast.makeText(context, "Null error", Toast.LENGTH_SHORT).show();
	       }
	       catch (FileNotFoundException e) 
	       {
	           e.printStackTrace();
	           Toast.makeText(context, "File error", Toast.LENGTH_SHORT).show();
	       }
        
        return save;

	}//saveSketch
	
	public void scanPhoto(final String imageFileName, Context context) {
	    msConn = new MediaScannerConnection(context, new MediaScannerConnectionClient() {
	                public void onMediaScannerConnected() {
	                    msConn.scanFile(imageFileName, null);
	                }

					@Override
					public void onScanCompleted(String path, Uri uri) {
						// TODO Auto-generated method stub
						msConn.disconnect();
					}
	            });
	    msConn.connect();
	}
}
