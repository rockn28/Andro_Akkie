package com.akkie.testproj.tab_setting;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.akkie.testproj.R;
import com.akkie.testproj.common.CommonUtils;
import com.akkie.testproj.common.DebugHelper;
import com.akkie.testproj.common.DebugUtils2;

/**
 * <端末情報 タブ>
 */
public class ActivityEx_TabSetting extends Activity implements View.OnClickListener {
    
    private enum idNo {
    	setting,
    	in_data_view,  //内部画像イメージをcsv吐き出し
    	ex_data_view,  //SD画像イメージをcsv吐き出し
    	ex_data_video, //SD動画イメージをcsv吐き出し
    	buildCheck,    //BUILD情報表示
    	dispCheck,     //ディスプレイ情報表示
    	memInfo,
    };
    
    //以下デバッグ確認
//    @Override
//    protected void onResume() {
//    	super.onResume();
//    	DebugUtils2.d("", "onResume");
//    }
//    @Override
//    protected void onRestart () {
//    	super.onRestart();
//    	DebugUtils2.d("", "onRestart");
//    }
//    @Override
//    protected void onStart (){
//    	super.onStart();
//    	DebugUtils2.d("", "onStart");
//    }
//    @Override
//    protected void onStop (){
//    	super.onStop();
//    	DebugUtils2.d("", "onStop");
//    }
//    @Override
//    protected void onPause () {
//    	super.onPause();
//    	DebugUtils2.d("", "onPause");
//    }
    @Override  
    public void onDestroy() {  
      super.onDestroy();  
      DebugUtils2.d("","onDestroy()");  
      DebugUtils2.d("","isFinishing : " + isFinishing());  
      int change = getChangingConfigurations();  
      DebugUtils2.d("","getChangingConfigurations() :" + change + " (" + String.format("0x%08x", change) + ")");  
    }  
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	DebugUtils2.d("", "onSaveInstanceState");
    }
    
    //アプリの初期化
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	DebugUtils2.d("", "onCreate() Start");
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);

    	//レイアウトの生成
    	LinearLayout layout = new LinearLayout(this);
    	layout.setBackgroundColor(Color.rgb(255,255,255));
    	layout.setOrientation(LinearLayout.VERTICAL);

    	//ボタンの生成
    	layout.addView(makeButton(idNo.setting.ordinal(),"設定画面の表示"));
    	layout.addView(makeButton(idNo.in_data_view.ordinal(),"内部　画像情報更新 csv作成"));
    	layout.addView(makeButton(idNo.ex_data_view.ordinal(),"SDCard 画像情報更新 csv作成"));
    	layout.addView(makeButton(idNo.ex_data_video.ordinal(),"SDCard Video情報更新 csv作成"));
    	layout.addView(makeButton(idNo.buildCheck.ordinal(),"BUILD情報"));
    	layout.addView(makeButton(idNo.dispCheck.ordinal(),"ディスプレイ情報"));
    	layout.addView(makeButton(idNo.memInfo.ordinal(),"メモリ情報"));
    	
    	//スクロールビューの生成
    	ScrollView scrollView=new ScrollView(this);
    	scrollView.addView(layout);
    	setContentView(scrollView);

    	DebugUtils2.d("", "onCreate() End");
    }
    
    //ボタンの生成
    private Button makeButton(int id,String text) {
        Button button=new Button(this);
        button.setId(id);
        button.setText(text);
        button.setOnClickListener(this); 
        setLLParams(button);
        return button;        
    }
    //ライナーレイアウトのパラメータ指定
    private static void setLLParams(View view) {
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }
    
    //アクティビティ呼び出し結果の取得
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
    	DebugUtils2.d("", "onActivityResult("+requestCode+", "+ resultCode +", "+ data +") Start");
        DebugUtils2.d("", "onActivityResult() End");
    }    
    //ボタンクリックイベントの処理
    public void onClick(View v) {
        String info;
        int id = v.getId();
        DebugUtils2.i("", "onClick() Start id = "+ id);
        //設定画面の表示
        if (id == idNo.setting.ordinal()) {
            Intent intent = new Intent("android.settings.SETTINGS");
            startActivity(intent);
        }
        //in コンテントリゾルバー csv 作成
        else if (id == idNo.in_data_view.ordinal()) {
        	SDCardview sdview = new SDCardview(this);
        	try {
				sdview.loadFromSDCard(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image_in.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        //out コンテントリゾルバー 画像 csv 作成
        else if (id == idNo.ex_data_view.ordinal()) {
        	SDCardview sdview = new SDCardview(this);
        	try {
				sdview.loadFromSDCard(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image_out.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        //out コンテントリゾルバー 動画 csv 作成
        else if (id == idNo.ex_data_video.ordinal()) {
        	SDCardview sdview = new SDCardview(this);
        	try {
				sdview.loadFromSDCard(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video_out.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        //ビルド情報
        else if (id == idNo.buildCheck.ordinal()) {
        	info = new DebugHelper().getBuildInfo();
        	CommonUtils.showAlertDialog("Build情報", info, this);
        }
        //ディスプレイ情報
        else if (id == idNo.dispCheck.ordinal()) {
            info = DebugHelper.displayCheck(this);
            CommonUtils.showAlertDialog("ディスプレイ情報", info, this);
        }
        // メモリ情報を取得
        else if(id == idNo.memInfo.ordinal()) {
            ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            
            String retInfo = "";
            // システムの利用可能な空きメモリ
            retInfo = retInfo.concat("memoryInfo.availMem[MB] = " + (int)(memoryInfo.availMem/1024/1024) + CommonUtils.BR);
            // 低メモリ(LowMemory)状態の閾値
            retInfo = retInfo.concat("memoryInfo.threshold[MB] = " + (int)(memoryInfo.threshold/1024/1024) + CommonUtils.BR);
            // 低メモリ状態を示すフラグ(trueでメモリ不足状態)
            retInfo = retInfo.concat("memoryInfo.lowMemory = " + memoryInfo.lowMemory);
            CommonUtils.showAlertDialog("メモリ情報", retInfo, this);
        }
        DebugUtils2.i("", "onClick() End id = "+ id);
    }
    
    /* メニュー内容を生成 */  
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        MenuInflater inflater = getMenuInflater();  
        inflater.inflate(R.menu.menu, menu);  
        return true;  
    }  
    /* ボタンを押した時の動作 */  
    @Override  
    public boolean onMenuItemSelected(int featureId, MenuItem item) {  
        int id = item.getItemId();  
        // XML中のメニューボタンにアクセスするにはR.id以下を利用する  
        if (id == R.id.setting) {    
        }  
        return true;  
    }
    
    @SuppressWarnings("unused")
    private void showToast(String mes) {
    	Toast myToast = Toast.makeText(this, mes, Toast.LENGTH_SHORT);
    	myToast.show();
    }
}
