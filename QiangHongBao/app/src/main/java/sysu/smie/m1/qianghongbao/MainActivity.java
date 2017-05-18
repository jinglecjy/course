package sysu.smie.m1.qianghongbao;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_service = (Button) findViewById(R.id.bt_Service);
        Button bt_background = (Button) findViewById(R.id.bt_runBackground);
        Button bt_help = (Button) findViewById(R.id.bt_help);
        bt_service.setOnClickListener(this);
        bt_background.setOnClickListener(this);
        bt_help.setOnClickListener(this);
    }

    //点击事件处理监听器
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,HongBaoService.class);
        switch(v.getId()){
            case R.id.bt_Service:
                startAccessibilityService();
                break;
            case R.id.bt_runBackground:
                runBackground();
                break;
            case R.id.bt_help:
                help();
                break;
            default:
                break;
        }
    }

    /**
     * 判断自己的应用的AccessibilityService是否在运行
     *
     * @return
     */
    private boolean serviceIsRunning() {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Short.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            if (info.service.getClassName().equals(getPackageName() + ".HongBaoService")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 前往设置界面开启服务
     */
    private void startAccessibilityService() {
        new AlertDialog.Builder(this)
                .setTitle("抢红包服务 开启/关闭")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("使用此项功能需要您在辅助功能处设置")
                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 隐式调用系统设置界面
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                }).create().show();
    }

    /**
     * 前往设置界面开启锁屏时运行
     */
    private void runBackground() {
        new AlertDialog.Builder(this)
                .setTitle("锁屏时运行 开启/关闭")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("使用此项功能需要您允许该应用忽略电池优化/将应用设置为受保护应用")
                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 隐式调用系统设置界面
                        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                        startActivity(intent);
                    }
                }).create().show();
    }

    /**
     * 帮助界面
     */
    private void help() {
        new AlertDialog.Builder(this)
                .setTitle("帮助")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(this.getString(R.string.help))
                .setPositiveButton("好的", null)
                .create().show();
    }
}
