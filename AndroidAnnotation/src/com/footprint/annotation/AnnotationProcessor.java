package com.footprint.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

public class AnnotationProcessor {
	private static final String ROOT_VIEW = "rootView";
	
	/**
	 * 为Activity以及其子类注解
	 * */
	public static void processActivity(Activity activity){
		Field[] fields = activity.getClass().getDeclaredFields();
		View rootView = null;
		
		for(Field field : fields){
			if(field.getName().equals(ROOT_VIEW)){
				Annotation[] annos = field.getAnnotations();
				
				if(annos.length < 0)
					throw new IllegalArgumentException("AndroidAnnotation: no annotation for field \""+ ROOT_VIEW + "\"");
				
				if(annos[0] instanceof ViewAnno) {
					ViewAnno viewAnno = (ViewAnno) annos[0];
					// Use field name if name not specified
					int id = viewAnno.id();
					
					if(id < 0)
						throw new IllegalArgumentException("AndroidAnnotation: view id < 0");
					
					rootView = activity.getLayoutInflater().inflate(id, null);
					try {
						field.setAccessible(true);
						field.set(activity, rootView);//设置属性
						activity.setContentView(rootView);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
		}
		
		for(Field field : fields){
			if(field.getName().equals(ROOT_VIEW)){
				continue;
			}
			
			Annotation[] annos = field.getAnnotations();
			
			if(annos.length < 0)
				continue;
			
			if(annos[0] instanceof ViewAnno) {
				ViewAnno viewAnno = (ViewAnno) annos[0];
				// Use field name if name not specified
				int id = viewAnno.id();
				if(id < 0)
					throw new IllegalArgumentException("AndroidAnnotation: view id < 0");
				
				View view = null;
				if(rootView == null)
					view = activity.findViewById(id);
				else
					view = rootView.findViewById(id);
				
				try {
					field.setAccessible(true);
					field.set(activity, view);//设置属性
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void processObject(Object obj, View view){
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field field : fields){			
			Annotation[] annos = field.getAnnotations();
			
			if(annos.length < 0)
				continue;
			
			if(annos[0] instanceof ViewAnno) {
				ViewAnno viewAnno = (ViewAnno) annos[0];
				// Use field name if name not specified
				int id = viewAnno.id();
				if(id < 0)
					throw new IllegalArgumentException("AndroidAnnotation: view id < 0");
				
				View subView = view.findViewById(id);
				
				try {
					field.setAccessible(true);
					field.set(obj, subView);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
