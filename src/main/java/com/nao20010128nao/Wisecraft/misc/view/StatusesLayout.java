package com.nao20010128nao.Wisecraft.misc.view;
import android.content.*;
import android.graphics.*;
import android.support.v4.content.*;
import android.util.*;
import android.view.*;
import java.math.*;
import java.util.*;


public class StatusesLayout extends View
{
	int[] colors;
	List<Integer> statuses;
	Context ctx;
	Paint paint;
	float oneComp;
	
	public StatusesLayout(Context context) {
		super(context);
		setup(context);
	}

    public StatusesLayout(Context context, AttributeSet attrs) {
		super(context,attrs);
		setup(context,attrs);
	}

    public StatusesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		setup(context,attrs);
	}

	
	private void setup(Context ctx){
		this.ctx=ctx;
		paint=new Paint();
		paint.setStyle(Paint.Style.FILL);
	}
	
	private void setup(Context ctx,AttributeSet as){
		setup(ctx);
	}
	
	private void redraw(){
		if(getVisibility()!=VISIBLE)return;
		invalidate();
	}
	
	
	public void setColors(int... color){
		colors=Arrays.copyOf(color,color.length);
		redraw();
	}
	public void setColorRes(int... res){
		int[] c=new int[res.length];
		for(int i=0;i<c.length;i++){
			c[i]=ContextCompat.getColor(ctx,res[i]);
		}
		setColors(c);
	}
	public void initStatuses(int len,int def){
		int[] statuses=new int[len];
		Arrays.fill(statuses,def);
		setStatuses(statuses);
	}
	public void setStatuses(int... stat){
		statuses=wrap(stat);
		redraw();
	}
	public void setStatusAt(int ofs,int val){
		statuses.set(ofs,val);
		redraw();
	}
	public void addStatuses(int... values){
		statuses.addAll(wrap(values));
		redraw();
	}
	public void removeStatus(int ofs){
		try {
			ArrayList.class.getMethod("remove", int.class).invoke(statuses, ofs);
		} catch (Throwable e) {}
		redraw();
	}
	
	public int[] getStatuses(){
		return unwrap(statuses);
	}
	
	private boolean isInvalid(){
		return statuses==null|colors==null;
	}

	@Override
	public void setVisibility(int visibility) {
		// TODO: Implement this method
		boolean willRelayout=(visibility==VISIBLE&getVisibility()!=VISIBLE);
		super.setVisibility(visibility);
		if(willRelayout)redraw();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO: Implement this method
		if(isInvalid())return;
		if(statuses.size()==0){
			if(colors.length==0)return;//nothing to do
			paint.setColor(colors[0]);
			canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
			return;
		}
		float oneComp=BigDecimal.valueOf(getWidth()).divide(BigDecimal.valueOf(statuses.size()),10,RoundingMode.DOWN).floatValue();
		for(int i=0;i<statuses.size();i++){
			paint.setColor(colors[statuses.get(i)]);
			canvas.drawRect(oneComp*i,0,oneComp*(i+1),canvas.getHeight(),paint);
		}
	}
	
	
	private List<Integer> wrap(int[] array){
		ArrayList<Integer> result=new ArrayList<>(array.length);
		for(int i:array)result.add(i);
		return result;
	}
	private int[] unwrap(List<Integer> array){
		int[] data=new int[array.size()];
		for(int i=0;i<data.length;i++)data[i]=array.get(i);
		return data;
	}
}
