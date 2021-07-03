package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Resume resume = new Resume("777");
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        System.out.println(field.get(resume));
        field.set(resume, "new uuid");
        System.out.println(resume);
        Method[] methods = resume.getClass().getDeclaredMethods();
        for(int i = 0; i < methods.length; i++){
            if(methods[i].getName().equals("toString")){
                System.out.println(methods[i].invoke(resume));
            }
        }
    }
}
