package com.navigationdrawer;

import ldrawer.com.pragati.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneFragment extends Fragment{
	  private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_one, container, false);

          

        }
		return rootView;
		
		
		
	}

}
