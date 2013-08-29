package com.liljeson.mattias.fries.utils;

import java.util.ArrayList;
import java.util.List;

public class DeluxeArray<T> {
	public List<T> arr = new ArrayList<>();
	
	public T top(){
		return arr.get(arr.size()-1);
	}
	
	public T pop(){
		T val = top();
		arr.remove(val);
		return val;
	}
	
	public void push(T p_val){
		arr.add(p_val);
	}
	
	public int size(){
		return arr.size();
	}
	
	public T get(int p_idx){
		return arr.get(p_idx);
	}
}
