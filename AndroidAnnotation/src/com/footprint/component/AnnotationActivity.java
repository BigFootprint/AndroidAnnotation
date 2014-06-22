package com.footprint.component;

import com.footprint.annointerface.GetView;
import com.footprint.annotation.AnnotationProcessor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * �̳д����Activity������ʹ��ע��ʵ�������ԡ����Ǳ������������������֮һ
 * 1�������ԣ�rootView;
 * 2���ڵ���super.onCreate()����֮ǰ����setContentView;
 * ͬʱ����������������ʱ����rootViewΪ׼
 * */
public class AnnotationActivity extends Activity implements GetView{
	private View contentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AnnotationProcessor.processActivity(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(this.getLayoutInflater().inflate(layoutResID, null));
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		this.contentView = view;
		super.setContentView(view, params);
	}

	@Override
	public void setContentView(View view) {
		this.contentView = view;
		super.setContentView(view);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		return contentView;
	}
}
