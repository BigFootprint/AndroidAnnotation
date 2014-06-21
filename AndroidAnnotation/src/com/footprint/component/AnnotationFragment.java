package com.footprint.component;

import com.footprint.annotation.AnnotationProcessor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * �̳д����Fragment������ʹ��ע��ʵ�������ԡ����Ǳ���ʵ���������󷽷�
 * ͬʱ����������������ʱ����rootViewΪ׼
 * */
public abstract class AnnotationFragment extends Fragment{
	private int contentViewId = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentViewId = getContentView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(contentViewId, null);
		AnnotationProcessor.processObject(this, rootView);
		onViewReady();
		return rootView;
	}
	
	/**
	 * ��������rootView����ʵ��ע���ʱ�򣬿��Է�������ֵ
	 * */
	abstract protected int getContentView();
	
	/**
	 * Viewȫ��׼���õ�ʱ�򣬻ص�
	 * */
	abstract protected void onViewReady();
}
