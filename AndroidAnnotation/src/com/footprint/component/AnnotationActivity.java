package com.footprint.component;

import com.footprint.annotation.AnnotationProcessor;

import android.app.Activity;
import android.os.Bundle;

/**
 * �̳д����Activity������ʹ��ע��ʵ�������ԡ����Ǳ������������������֮һ
 * 1�������ԣ�rootView��
 * 2���ڵ���super.onCreate()����֮ǰ����setContentView
 * ͬʱ����������������ʱ����rootViewΪ׼
 * */
public class AnnotationActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AnnotationProcessor.processActivity(this);
	}
}
