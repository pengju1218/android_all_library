package com.mikeliu.sharemydemo.lib;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import java.util.logging.LogRecord;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareDialog {
    public PlatformActionListener paListener = null;
    private AlertDialog dialog;
    private Handler handler;
    private GridView gridView;
    private Button cancelButton;
    private SimpleAdapter saImageItems;
    private int[] image = {R.drawable.wechat, R.drawable.circleoffriends, R.drawable.qq, R.drawable.qqzone, R.drawable.sina, R.drawable.information_sharing};
    private String[] name = {"微信好友", "微信朋友圈", "QQ", "QQ空间", "新浪微博", "信息"};

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public ShareDialog(final Context context) {
        ShareSDK.initSDK(context);
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
　　　　window.setBackgroundDrawableResource(android.R.color.transparent);

        window.setAttributes(p);

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

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(context, "微博分享成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(context, "微信分享成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(context, "朋友圈分享成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, "QQ分享成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(context, "QQ空间分享成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(context, "分享失败" + msg.obj, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        };
        paListener = new PlatformActionListener() {
            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                arg2.printStackTrace();
                Message msg = new Message();
                msg.what = 6;
                msg.obj = arg2.getMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                if (arg0.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪微博
                    handler.sendEmptyMessage(1);
                } else if (arg0.getName().equals(Wechat.NAME)) {
                    handler.sendEmptyMessage(2);
                } else if (arg0.getName().equals(WechatMoments.NAME)) {
                    handler.sendEmptyMessage(3);
                } else if (arg0.getName().equals(QQ.NAME)) {
                    handler.sendEmptyMessage(4);
                } else if (arg0.getName().equals(QZone.NAME)) {
                    handler.sendEmptyMessage(7);
                } else if (arg0.getName().equals(ShortMessage.NAME)) {
                    handler.sendEmptyMessage(8);
                }
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                handler.sendEmptyMessage(5);
            }
        };
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShareParams sp = new ShareParams();


                //qq
                HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);

                //Toast.makeText(context,item.get("ItemText")+"",Toast.LENGTH_LONG).show();
                //qq
                if (item.get("ItemText").equals("QQ")) {
                    sp.setImageUrl("http://a2.qpic.cn/psb?/V11fV9K61VChwJ/ADsrX.dohjHznYcCSCHgS0UwdUAz6Djjy8WyHk4Z6w4!/b/dAMBAAAAAAAA&bo=MgAyAAAAAAADByI!&rf=viewer_4");
                    sp.setTitle("汇新云分享");
                    sp.setText("汇新云分享");
                    //sp.setImageUrl("http://a2.qpic.cn/psb?/V11fV9K61VChwJ/ADsrX.dohjHznYcCSCHgS0UwdUAz6Djjy8WyHk4Z6w4!/b/dAMBAAAAAAAA&bo=MgAyAAAAAAADByI!&rf=viewer_4");
                    sp.setTitleUrl(url); // 标题的超链接
                    //sp.setImageUrl("http://img1.imgtn.bdimg.com/it/u=4283678493,757360244&fm=21&gp=0.jpg");
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(paListener); // 设置分享事件回调
                    // 执行图文分享
                    qq.share(sp);
                }
                //qq空間
                else if (item.get("ItemText").equals("QQ空间")) {
                    sp.setImageUrl("http://a2.qpic.cn/psb?/V11fV9K61VChwJ/ADsrX.dohjHznYcCSCHgS0UwdUAz6Djjy8WyHk4Z6w4!/b/dAMBAAAAAAAA&bo=MgAyAAAAAAADByI!&rf=viewer_4");
                    sp.setTitle("汇新云分享");
                    sp.setTitleUrl(url);
                   // sp.setSiteUrl(url);
                    sp.setText(url);
                   // sp.setTitleUrl(url); // 标题的超链接
                  //  sp.setText("测试qq空间分享的文本");
                  //  sp.setImageUrl("http://img1.imgtn.bdimg.com/it/u=4283678493,757360244&fm=21&gp=0.jpg");
                   // sp.setSite("发布分享的网站名称");
                   // sp.setSiteUrl("http://www.baidu.com");
                    Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                    qzone.setPlatformActionListener(paListener); // 设置分享事件回调
                    // 执行图文分享
                    qzone.share(sp);
                }
                //微信
                else if (item.get("ItemText").equals("微信好友")) {
                   // sp.setImageUrl("http://a2.qpic.cn/psb?/V11fV9K61VChwJ/ADsrX.dohjHznYcCSCHgS0UwdUAz6Djjy8WyHk4Z6w4!/b/dAMBAAAAAAAA&bo=MgAyAAAAAAADByI!&rf=viewer_4");
                   // sp.setShareType(Platform.SHARE_TEXT);
                    //sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setTitle("汇新云分享");
                    sp.setText(url);
                   // sp.setTitleUrl(url); // 标题的超链接
                   // sp.setSiteUrl(url);
                  //  sp.setTitleUrl("http://192.168.0.51:8080/app.html");
                   // sp.setText("汇新云分享");"
                    //sp.setText("http://192.168.0.51:8080/app.html");
                  //  sp.setText("xttblog://taoge/open");
                    //sp.setTitleUrl("https://www.baidu.com");
                   // sp.setUrl("https://www.baidu.com");
                   // sp.setText(url);
                   // sp.setSiteUrl("https://www.baidu.com");
                    //sp.setUrl("http://app.qq.com/#id=detail&appid=1105229671");
                    //sp.setImageUrl("http://img1.imgtn.bdimg.com/it/u=4283678493,757360244&fm=21&gp=0.jpg");
                    Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                    weChat.setPlatformActionListener(paListener); // 设置分享事件回调
                    // 执行图文分享
                    weChat.share(sp);

                }
                //朋友圈
                else if (item.get("ItemText").equals("微信朋友圈")) {

                    sp.setShareType(Platform.SHARE_TEXT);
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setImageUrl("http://a2.qpic.cn/psb?/V11fV9K61VChwJ/ADsrX.dohjHznYcCSCHgS0UwdUAz6Djjy8WyHk4Z6w4!/b/dAMBAAAAAAAA&bo=MgAyAAAAAAADByI!&rf=viewer_4");
                  //  sp.setShareType(Platform.SHARE_APPS);
                    sp.setTitle("汇新云分享");
                    sp.setText(url);
                   // sp.setSiteUrl(url);
                   // sp.setText("https://www.baidu.com");
                   // sp.setSite("app下载");
                    //sp.setSiteUrl("https;//www.baidu.com");
                 //   sp.setText("测试分享的-内容");
                    //sp.setUrl("http://app.qq.com/#id=detail&appid=1105229671");
                   // sp.setImageUrl("https://www.baidu.com");

                  //  sp.setTitleUrl(url); // 标题的超链接
                   //sp.setImageUrl("http://img1.imgtn.bdimg.com/it/u=4283678493,757360244&fm=21&gp=0.jpg");
                    Platform weChatF = ShareSDK.getPlatform(WechatMoments.NAME);
                    weChatF.setPlatformActionListener(paListener); // 设置分享事件回调
                    // 执行图文分享
                    weChatF.share(sp);
                }
                //新浪微博
                else if (item.get("ItemText").equals("新浪微博")) {
                    sp.setShareType(Platform.SHARE_TEXT);
                    sp.setImageUrl("http://a2.qpic.cn/psb?/V11fV9K61VChwJ/ADsrX.dohjHznYcCSCHgS0UwdUAz6Djjy8WyHk4Z6w4!/b/dAMBAAAAAAAA&bo=MgAyAAAAAAADByI!&rf=viewer_4");
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setText("汇新云分享");
                    sp.setTitleUrl(url); // 标题的超链接
                  //  sp.setImagePath("/storage/emulated/0/Mob/com.mikeliu.sharemydemo/cache/images/鹿晗.jpg");
                   // sp.setUrl("http://app.qq.com/#id=detail&appid=1105229671");
                   // sp.setImageUrl("http://img1.imgtn.bdimg.com/it/u=4283678493,757360244&fm=21&gp=0.jpg");
                    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                    weibo.setPlatformActionListener(paListener); // 设置分享事件回调
                    // 执行图文分享
                    weibo.share(sp);
                }
                //短信
                else if (item.get("ItemText").equals("信息")) {
                    sp.setText(url);
                    Platform circle = ShareSDK.getPlatform(ShortMessage.NAME);
                    circle.setPlatformActionListener(paListener); // 设置分享事件回调
                    // 执行图文分享
                    circle.share(sp);

                } else {
                    dialog.dismiss();
                }
            }
        });

    }


    public void setCancelButtonOnClickListener(OnClickListener Listener) {
        cancelButton.setOnClickListener(Listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        gridView.setOnItemClickListener(listener);

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
