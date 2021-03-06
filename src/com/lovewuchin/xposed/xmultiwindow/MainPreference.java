package com.lovewuchin.xposed.xmultiwindow;

import com.lovewuchin.xposed.xmultiwindow.widget.preference.SeekBarPreference;
import com.lovewuchin.xposed.xmultiwindow.widget.sidebar.SideBarApp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuItem;

public class MainPreference extends PreferenceActivity implements OnPreferenceClickListener, OnPreferenceChangeListener{
	SeekBarPreference mSideWidth;
	SharedPreferences mPrefs;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	addPreferencesFromResource(R.xml.pref_main);
    	mPrefs=getSharedPreferences(Common.PREFERENCE_MAIN, MODE_WORLD_READABLE);
    	findPreference(Common.KEY_LUNCH_FLOAT).setOnPreferenceClickListener(this);
    	findPreference(Common.KEY_SIDEBAR_APP).setOnPreferenceClickListener(this);
    	mSideWidth=(SeekBarPreference)findPreference("sidebar_width");
    	mSideWidth.setValue(mPrefs.getInt(Common.PREFERENCE_WIDTH, 150));
    	mSideWidth.setOnPreferenceChangeListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	getMenuInflater().inflate(Common.MENU, menu);
		return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch(item.getItemId()){
    	case R.id.action_instruction:{
    		new AlertDialog.Builder(this).setTitle(Common.ACTION_INSTRUC).setMessage(Common.ACTION_MESSAGE)
    		    .setPositiveButton(Common.OK,null)
    		    .setNegativeButton(Common.CANCLE, null)
    		    .show();
    		    
    	}
    	}
    	return false;
    }
      public boolean onPreferenceClick(Preference preference) {
    	// TODO Auto-generated method stub
    	  String key=preference.getKey();
           if(key.equals(Common.KEY_LUNCH_FLOAT)){
    		  startActivity(new Intent(this,SideBarControlPanel.class));
    		  finish();
    		  return true;
    	  }else if(key.equals(Common.KEY_SIDEBAR_APP)){
    		  startActivity(new Intent(this,SideBarApp.class));
    		  return true;
    	  }
    	return false;
    }
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		String key=preference.getKey();
		if(key.equals(Common.KEY_SIDEBAR_WIDTH)){
			mPrefs.edit().putInt(Common.PREFERENCE_WIDTH, (Integer) newValue).commit();
			return true;
		}
		return false;
	}
}
