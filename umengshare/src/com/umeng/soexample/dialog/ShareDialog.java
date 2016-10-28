package com.umeng.soexample.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.umeng.soexample.R;
import com.umeng.soexample.model.Defaultcontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShareDialog {

    private AlertDialog dialog;
    private Handler handler;
    private GridView gridView;
    private Button cancelButton;
    private SimpleAdapter saImageItems;
    private int[] image = {R.drawable.wechat, R.drawable.circleoffriends, R.drawable.qq, R.drawable.qqzone, R.drawable.sina, R.drawable.information_sharing};
    private String[] name = {"微信好友", "微信朋友圈", "QQ", "QQ空间", "新浪微博", "信息"};
    private SHARE_MEDIA[] memew = {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.SMS,};
    private String url;


    private Activity activity;

    public void setUrl(String url) {
        this.url = url;
    }

    public ShareDialog(final Activity context) {

        this.activity = context;
        dialog = new AlertDialog.Builder(context).create();
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        window.setGravity(Gravity.BOTTOM); // 非常重要：设置对话框弹出的位置
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度


        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        window.setAttributes(lp);

        window.setBackgroundDrawableResource(android.R.color.transparent);

        window.setContentView(R.layout.share_dialog);
        gridView = (GridView) window.findViewById(R.id.share_gridView);
        cancelButton = (Button) window.findViewById(R.id.share_cancel);
        List<HashMap<String, Object>> shareList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < image.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", image[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            shareList.add(map);
        }
        cancelButton.setVisibility(View.GONE);
        saImageItems = new SimpleAdapter(context, shareList, R.layout.share_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.share_img, R.id.share_name});
        gridView.setAdapter(saImageItems);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShareAction(context).withText(Defaultcontent.text + "来自汇新云")
                        .setPlatform(memew[position])
                        .withTitle(Defaultcontent.title)
                        .withMedia(new UMImage(context, Defaultcontent.imageurl))
                        .withTargetUrl(Defaultcontent.url)
                        .setCallback(umShareListener)
                        .share();
            }
        });


    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(activity, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }

    };

    public GridView getGridView() {
        return gridView;
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        dialog.dismiss();
    }


    public void show() {
        dialog.show();
    }


}