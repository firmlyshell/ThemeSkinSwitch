package com.firmlyshell.android.theme.skinswitch.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.firmlyshell.android.theme.skin.entity.DynamicAttr;
import com.firmlyshell.android.theme.skin.listener.DynamicNewView;

import java.util.List;


public class BaseFragment extends Fragment implements DynamicNewView {
	
	private DynamicNewView mIDynamicNewView;
	private LayoutInflater mLayoutInflater;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try{
			mIDynamicNewView = (DynamicNewView)context;
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("DynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(view, pDAttrs);
		}
	}

	public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
		LayoutInflater result = getActivity().getLayoutInflater();
		return result;
	}
}
