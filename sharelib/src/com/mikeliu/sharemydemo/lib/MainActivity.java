package com.mikeliu.sharemydemo.lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;

/**
 * 分享demo
 *
 * @author mikeliu
 * @date 2016年6月2日下午4:08:55
 */
public class MainActivity extends Activity implements OnClickListener{
	private Button btnShare;
	ShareDialog shareDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnShare = (Button) findViewById(R.id.btn_share);
		btnShare.setOnClickListener(this);


	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_share) {
			shareDialog=new ShareDialog(this);
			//Toast.makeText(MainActivity.this,"111111111111111",Toast.LENGTH_SHORT).show();

		/*	DialogUtils dialogUtils = new DialogUtils();
			dialogUtils.dialogShow(this);*/
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}


}
