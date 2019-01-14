package com.miamor.Obj;

import java.util.Stack;

/**
 * Created by marco on 13/11/15.
 */
public class MyStack {

    private Stack<Object> value;
    private static MyStack myStack;

    public MyStack(){
        value=new Stack<Object>();
    }

    public static MyStack getInstance(){
        if(myStack ==null){
            myStack =new MyStack();
        }
        return myStack;
    }

    public Object getLastFragment(){
        if(value.size()>0){
            return value.peek();
        }
        return null;
    }

    public void addFragment(Object f){
        if(!value.contains(f)){
            value.push(f);
        }else{
            this.deleteFragment();
            value.push(f);
        }
    }
    public void deleteFragment(){
        value.pop();
    }

}
