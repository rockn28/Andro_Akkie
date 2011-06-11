package com.akkie.testproj;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.akkie.testproj.common.DebugUtils2;
import com.akkie.testproj.tab_setting.ActivityEx_TabSetting;

/**
 * <起動画面 タブ管理>
 * @author Akkie 
 * @since
 * @version
 */
public class MainTabActivity extends TabActivity  implements TabHost.TabContentFactory, OnTabChangeListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //デバッグモード自動設定
        DebugUtils2.setDebuggable(this);
        DebugUtils2.d("", "onCreate() Start");
        super.onCreate(savedInstanceState);
        
        final TabHost tabhost = getTabHost();
        
        // アクティビティ(設定) をタブにホストする
        tabhost.addTab(tabhost.newTabSpec("tab1")
                .setIndicator("情報を得る", getResources().getDrawable(android.R.drawable.ic_menu_view))
                .setContent(new Intent(this, ActivityEx_TabSetting.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        // アクティビティ(テストアプリ集) をタブにホストする
//        tabhost.addTab(tabhost.newTabSpec("tab2")
//                .setIndicator("テスト中", getResources().getDrawable(android.R.drawable.ic_menu_delete))
//                .setContent(new Intent(this, ActivityEx_TabTest.class)
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        // アクティビティ(RegisterdItemPickupActivity)をタブにホストする
//        tabhost.addTab(tabhost.newTabSpec("tab2")
//                .setIndicator("items", getResources().getDrawable(android.R.drawable.ic_menu_delete))
//                .setContent(new Intent(this, RegisterdItemPickupActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

        // このアクティビティ自体をホストする(createTabContent が返す View が表示される)
        tabhost.addTab(tabhost.newTabSpec("tab3")
                .setIndicator("未実装", getResources().getDrawable(android.R.drawable.ic_menu_help))
                .setContent(this));
        
        tabhost.setOnTabChangedListener(this);
        
        DebugUtils2.d("", "onCreate() End");
    }
    
    @Override
    public View createTabContent(String tag) {
        DebugUtils2.d("", "createTabContent() called");
        final TextView tv = new TextView(this);
        tv.setText("Content for tab with tag :" + tag);
        return tv;
    }

    //以下デバッグ確認
    @Override
    protected void onResume() {
    	super.onResume();
    	DebugUtils2.d("", "onResume");
    }
    @Override
    protected void onRestart () {
    	super.onRestart();
    	DebugUtils2.d("", "onRestart");
    }
    @Override
    protected void onStart (){
    	super.onStart();
    	DebugUtils2.d("", "onStart");
    }
    @Override
    protected void onStop (){
    	super.onStop();
    	DebugUtils2.d("", "onStop");
    }
    @Override
    protected void onPause () {
    	super.onPause();
    	DebugUtils2.d("", "onPause");
    }
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
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	DebugUtils2.d("", "onRestoreInstanceState");
    }
    @Override
	public void onTabChanged(String tabId) {
    	DebugUtils2.d("", "onTabChanged("+ tabId +") called");
	}
    


	
}
