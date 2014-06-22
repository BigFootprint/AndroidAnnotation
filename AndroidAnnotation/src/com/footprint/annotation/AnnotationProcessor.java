package com.footprint.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.footprint.annointerface.GetView;

public class AnnotationProcessor {
	private static final String ROOT_VIEW = "rootView";
	
	/**
	 * ΪActivity�Լ�������ע��
	 * */
	public static void processActivity(Activity activity){
		checkRootViewForActivity(activity);
		processAnnotation(activity, ((GetView)activity).getContentView(), activity);
	}
	
	/**
	 * ���Activity��rootView���Բ�����
	 * */
	private static void checkRootViewForActivity(Activity activity){
		Field[] fields = activity.getClass().getDeclaredFields();
		View rootView = null;
		
		for(Field field : fields){
			if(field.getName().equals(ROOT_VIEW)){
				Annotation[] annos = field.getAnnotations();
				
				if(annos.length <= 0)
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
						field.set(activity, rootView);//��������
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
	}
	
	/**
	 * Ϊĳ��������Դ������е�Annotation
	 * @param annoObj ����ע�����Ե�Obj
	 * @param viewObj ���Ի�ȡView��Obj
	 * */
	private static void processAnnotation(Object annoObj, View viewObj, Context context){
		Field[] fields = annoObj.getClass().getDeclaredFields();
		
		for(Field field : fields){	
			Annotation[] annos = field.getAnnotations();
			
			if(annos.length <= 0)
				continue;
			
			if(!field.isAccessible())
				field.setAccessible(true);
			
			if(annos[0] instanceof ViewAnno) {
				ViewAnno viewAnno = (ViewAnno) annos[0];

				int id = viewAnno.id();
				if(id == viewObj.getId())//��ʾ�����������
					continue;
				
				checkoutId(id);
				
				View subView = viewObj.findViewById(id);
				
				try {
					field.set(annoObj, subView);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				checkListener(annoObj, subView, viewAnno.click());
			}else if(annos[0] instanceof BeanAnno){
				Class cl = field.getType();
				Log.d("LQM", cl.getName());
				try {
					field.set(annoObj, cl.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(annos[0] instanceof DimenAnno){
				DimenAnno anno = (DimenAnno)annos[0];
				int dimenId = anno.dimen();
				checkoutId(dimenId);
				try {
					field.set(annoObj, context.getResources().getDimensionPixelSize(dimenId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(annos[0] instanceof ColorAnno){
				ColorAnno anno = (ColorAnno)annos[0];
				int colorId = anno.color();
				checkoutId(colorId);
				try {
					field.set(annoObj, context.getResources().getColor(colorId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(annos[0] instanceof DrawableAnno){
				DrawableAnno anno = (DrawableAnno)annos[0];
				int drawableId = anno.drawable();
				checkoutId(drawableId);
				try {
					field.set(annoObj, context.getResources().getDrawable(drawableId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(annos[0] instanceof StringAnno){
				StringAnno anno = (StringAnno)annos[0];
				int stringId = anno.string();
				checkoutId(stringId);
				try {
					field.set(annoObj, context.getResources().getString(stringId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ���ע������û���¼�������У������
	 * */
	private static void checkListener(Object obj, View view, String click){
		if(!TextUtils.isEmpty(click)){
			try {
				Method method = obj.getClass().getDeclaredMethod(click, View.class);
				if(!method.isAccessible())
					method.setAccessible(true);
				addClickListener(obj, view, method);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void addClickListener(final Object obj, final View view, final Method m){
        view.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					m.invoke(obj, view);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        });
    }
	
	private static void checkoutId(int id){
		if(id < 0)
			throw new IllegalArgumentException("AndroidAnnotation: view id < 0");
	}
	
	/**
	 * ��ĳ��Object������������ĳ��View����View��ʱ�򣬸÷����ṩע�����
	 * */
	public static void processObject(Object obj, View view, Context context){
		processAnnotation(obj, view, context);
	}
}
