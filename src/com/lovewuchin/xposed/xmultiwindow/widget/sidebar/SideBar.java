package com.lovewuchin.xposed.xmultiwindow.widget.sidebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import com.lovewuchin.xposed.xmultiwindow.Common;
import com.lovewuchin.xposed.xmultiwindow.R;
import com.lovewuchin.xposed.xmultiwindow.widget.sidebar.adapter.ApplicationAdapter;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.ui.Window;

public class SideBar extends StandOutWindow{
	PackageManager mPackageManager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mPackageManager = getPackageManager();
	}
	@Override
	public String getAppName() {
		// TODO Auto-generated method stub
		return "SideBar";
	}

	@Override
	public int getAppIcon() {
		// TODO Auto-generated method stub
		return R.drawable.ic_launcher;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=LayoutInflater.from(this);
		final View view=inflater.inflate(R.layout.sidebar_app_layout, frame,true);
		final ListView mAppList=(ListView)view.findViewById(R.id.app_list);
		final List<ActivityInfo> apps = new ArrayList<ActivityInfo>();
		mAppList.setClickable(true);
		final ApplicationAdapter adapter=new ApplicationAdapter(this, R.layout.app_row, apps);
		mAppList.setAdapter(adapter);
		mAppList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				final ActivityInfo app = (ActivityInfo) parent
						.getItemAtPosition(position);
				Context mContext = getApplicationContext();
				DisplayMetrics metrics=new DisplayMetrics();
				WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				display.getMetrics(metrics);
				if(metrics.heightPixels>metrics.widthPixels){
				ActionItem  upitem=new ActionItem(1,getString(Common.POP_ADD_UPVIEW));
				ActionItem  downiten=new ActionItem(2, getString(Common.POP_ADD_DOWNVIEW));
				final QuickAction quickAction = new QuickAction(mContext, QuickAction.VERTICAL);
				quickAction.addActionItem(upitem);
				quickAction.addActionItem(downiten);
                quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					
					@Override
					public void onItemClick(QuickAction source, int pos, int actionId) {
						// TODO Auto-generated method stub
						switch(pos){
						case 0:{Intent intent = mPackageManager
								.getLaunchIntentForPackage(app.packageName);
						        intent.addFlags(Common.FLAG_ACTIVITY_UPVIEW);
								startActivity(intent);break;}
						case 1:{Intent intent = mPackageManager
								.getLaunchIntentForPackage(app.packageName);
						        intent.addFlags(Common.FLAG_ACTIVITY_DOWNVIEW);
								startActivity(intent);break;}
						}
					}
				});
				quickAction.show(view);
				quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
				}
				else{
					ActionItem  upitem=new ActionItem(1,getString(R.string.pop_add_leftview));
					ActionItem  downiten=new ActionItem(2, getString(R.string.pop_add_rightview));
					final QuickAction quickAction = new QuickAction(mContext, QuickAction.VERTICAL);
					quickAction.addActionItem(upitem);
					quickAction.addActionItem(downiten);
					quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
						
						@Override
						public void onItemClick(QuickAction source, int pos, int actionId) {
							// TODO Auto-generated method stub
							switch(pos){
							case 0:{Intent intent = mPackageManager
									.getLaunchIntentForPackage(app.packageName);
							        intent.addFlags(Common.FLAG_ACTIVITY_UPVIEW);
									startActivity(intent);break;}
							case 1:{Intent intent = mPackageManager
									.getLaunchIntentForPackage(app.packageName);
							        intent.addFlags(Common.FLAG_ACTIVITY_DOWNVIEW);
									startActivity(intent);break;}
							}
						}
					});
					quickAction.show(view);
					quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
				}
			}
			
        });
		new Thread(new Runnable() {
			@Override
			public void run() {
				final Intent mainIntent = new Intent(Intent.ACTION_MAIN,
						null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				final List<ResolveInfo> resolveApps = getPackageManager()
						.queryIntentActivities(mainIntent, 0);
				for (ResolveInfo resolveApp : resolveApps) {
					apps.add(resolveApp.activityInfo);
				}
				view.post(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}

	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		// TODO Auto-generated method stub
		DisplayMetrics metrics=new DisplayMetrics();
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getMetrics(metrics);
		return new StandOutLayoutParams(id, metrics.widthPixels/4, metrics.heightPixels, 0, 0);
	}
	public String getPersistentNotificationMessage(int id) {
		return getString(Common.NOFIY_CLEAR);
	}
	public Intent getPersistentNotificationIntent(int id) {
		return StandOutWindow.getCloseAllIntent(this, SideBar.class);
	}

}