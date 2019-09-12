package org.spring.util;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BeanFactory {

    private Map map = new HashMap<String,Object>();
    public BeanFactory(String xml){
        parseXml(xml);
    }
    public void parseXml(String xml) {
        try {
            File file = new File(this.getClass().getResource("/").getPath()+"//"+xml);
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();
            //用迭代器遍历根标签(beans)
            for (Iterator<Element> itFirst = root.elementIterator(); itFirst.hasNext();) {
                Element elementFirstChill = itFirst.next();
                //获取bean标签的值
                String beanName = elementFirstChill.attribute("id").getValue();
                String clazzName = elementFirstChill.attribute("class").getValue();
                //加载这个类获取字节码对象
                Class<?> clazz = Class.forName(clazzName);
                Object object = null;
                for (Iterator<Element> itSecond = elementFirstChill.elementIterator(); itSecond.hasNext();) {
                    Element elementSecondChill = itSecond.next();
                    if (elementSecondChill.getName().equals("property")){
                        object = clazz.newInstance();
                        String ref = elementSecondChill.attribute("ref").getValue();
                        Object injectObject = map.get(ref);
                        String name = elementSecondChill.attribute("name").getValue();
                        Field field = clazz.getDeclaredField(name);
                        field.setAccessible(true);
                        field.set(object,injectObject);
                    }else{
                        String ref = elementSecondChill.attribute("ref").getValue();
                        Object injectObject = map.get(ref);
                        Class injectObjectClazz = injectObject.getClass();
                        Constructor constructor = clazz.getConstructor(injectObjectClazz);
                        object= constructor.newInstance(injectObject);
                    }
                }




                if (object==null)
                    object = clazz.newInstance();
                map.put(beanName,object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object getBean(String beanName){
        return map.get(beanName);
    }
}
