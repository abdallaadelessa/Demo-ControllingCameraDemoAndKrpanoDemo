
package com.example.androidtestproject;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewActivity extends Activity implements SurfaceHolder.Callback
{

	WebView webview;
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);

		setContentView(R.layout.camera);

		webview = (WebView) findViewById(R.id.webView1);
		loadUrl();

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if (!previewing)
				{
					startCamera();
				}
				else
				{
					stopCamera();
				}

			}
		});

	}

	// ---------------------------------------------------

	public void loadUrl()
	{

		webview.setBackgroundColor(0x00000000);
		WebSettings webSettings = webview.getSettings();

		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAllowFileAccessFromFileURLs(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setAllowUniversalAccessFromFileURLs(true);

		String mapPath = "file:///android_asset/krpano-master/pano.htm";
		// tmp3/krpano.com -Gyro Plugin Example.htm";
		webview.loadUrl(mapPath);

		Button button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				webview.reload();
			}
		});

	}

	public void startCamera()
	{

		if (!previewing)
		{
			camera = Camera.open();
			if (camera != null)
			{
				try
				{
					camera.setPreviewDisplay(surfaceHolder);
					camera.setDisplayOrientation(90);
					camera.startPreview();
					previewing = true;
				}
				catch (IOException e)
				{

				}
			}

		}
	}

	public void stopCamera()
	{

		if (camera != null && previewing)
		{
			camera.stopPreview();
			camera.release();
			camera = null;
			previewing = false;
		}
	}

	// ----------------------------------------------------

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		Log.i("DEBUG", "surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		//startCamera();
		Log.i("DEBUG", "surfaceCreated");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.i("DEBUG", "surfaceDestroyed");

	}
}
