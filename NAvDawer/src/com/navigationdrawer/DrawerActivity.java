package com.navigationdrawer;

import ldrawer.com.pragati.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.ldrawer.library.ActionBarDrawerToggle;
import com.ldrawer.library.DrawerArrowDrawable;
import com.ldrawer.library.utils.Utils;

public class DrawerActivity extends FragmentActivity implements
		AdapterView.OnItemClickListener {

	private ArrayAdapter<String> navigationDrawerAdapter;
	private DrawerLayout drawer;
	private ListView leftDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private String[] leftSliderData = { "Home", "Android", "Tech Zone",
			"Sitemap", "About", "Contact Me" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);
		init();
		initDrawer();
	}

	private void init() {
		getActionBar().setIcon(new ColorDrawable(Color.TRANSPARENT));
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		leftDrawerList = (ListView) findViewById(R.id.left_drawer);
		navigationDrawerAdapter = new ArrayAdapter<String>(DrawerActivity.this,
				android.R.layout.simple_list_item_1, leftSliderData);
		leftDrawerList.setAdapter(navigationDrawerAdapter);
		leftDrawerList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long l) {
		drawer.closeDrawers();
		switch (position) {
		case 0:

			Utils.addFragment(DrawerActivity.this, new OneFragment(),
					Constants.FRAGMENT_TAG_ONE, R.id.fragment_container);
			break;
		case 1:

			Utils.addFragment(DrawerActivity.this, new TwoFragment(),
					Constants.FRAGMENT_TAG_TWO, R.id.fragment_container);

		default:
			break;
		}

		getActionBar().setTitle(
				Html.fromHtml("<font color=\"#ffffff\">"
						+ leftSliderData[position].toString() + "</font>"));
		Toast.makeText(DrawerActivity.this,
				leftSliderData[position].toString() + "", Toast.LENGTH_SHORT)
				.show();
	}

	private void initDrawer() {
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		mDrawerToggle = new ActionBarDrawerToggle(this, drawer, drawerArrow,
				R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		drawer.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mDrawerToggle.onOptionsItemSelected(item);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

}
