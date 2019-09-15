package com.spring.factory;

import com.spring.vo.BeanDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public class ClassPathXmlApplicationContext {
    /**
     * 此map存储Bean实例
     */
    private Map<String,Object> instanceMap = new HashMap<>();
    /**
     * 此map存储配置文件的定义Bean的对象信息
     */
    private Map<String, BeanDefinition> beanMap=new HashMap<>();
    public ClassPathXmlApplicationContext(String xml) throws Exception {
        //1读取配置文件
        InputStream in = getClass().getClassLoader().getResourceAsStream(xml);
        //2解析文件封装数据
        parse(in);
    }

    /**
     *本次xml的解析基于dom实现
     */
    private void parse(InputStream in) throws Exception {
        //1创建解析器对象
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //2解析流对象
        Document doc = builder.parse(in);
        //3.处理Document
        processDocument(doc);
    }

    private void processDocument(Document doc) throws Exception {
        //获取所以bean元素
        NodeList list = doc.getElementsByTagName("bean");
        //迭代bean元素,对其配置信息进行封装
        for (int i=0; i<list.getLength();i++){
            Node node = list.item(i);
            //获取node对象对应的NamedNodeMap
            NamedNodeMap Map = node.getAttributes();

            //存储配置信息
            addBeanDefinition(Map);
            //基于配置信息中属性的值,判定是非延迟加载和作用域
           /* if (!bd.isLazy()){
                Object obj = newBeanInstance(bd.getPkgClass());
                instanceMap.put(bd.getId(),obj);
            }*/
        }

    }

    private void addBeanDefinition(NamedNodeMap map) {
        BeanDefinition bd = new BeanDefinition();
        String id = map.getNamedItem("id").getNodeValue();
        if (id!=null)
        bd.setId(id);
        String aClass = map.getNamedItem("class").getNodeValue();
        if (aClass!=null)
        bd.setPkgClass(aClass);
        Boolean aBoolean = Boolean.valueOf(map.getNamedItem("lazy-init").getNodeValue());
        if (aBoolean!=null)
        bd.setLazy(aBoolean);
        String scope = map.getNamedItem("scope").getNodeValue();
        if (scope!=null)
        bd.setScope(scope);
        //存储配置信息
        beanMap.put(bd.getId(),bd);
    }

    /**
     *基于反射构建类的实例对象
     */
    private Object newBeanInstance(String pkgClass) throws Exception {
        Class<?> cls = Class.forName(pkgClass);
        Constructor<?> declaredConstructor = cls.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }

    public <T>T getBean(String BeaNname) throws Exception {
        //1.判定当前instanceMap中是否有bean的实例
        Object obj = instanceMap.get(BeaNname);
        if (obj!=null)
            return (T) obj;
        if (beanMap.get(BeaNname)==null){
            return null;
        }
        obj = newBeanInstance(beanMap.get(BeaNname).getPkgClass());
        instanceMap.put(BeaNname,obj);
        return (T) obj;
    }

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println((String) classPathXmlApplicationContext.getBean("object"));
    }
}
