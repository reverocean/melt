package com.melt.config;

import com.melt.config.autowired.AutoWiredBy;
import com.melt.config.constructor.Constructor;
import com.melt.config.constructor.ConstructorParameter;
import com.melt.config.property.BeanProperty;
import com.melt.core.InjectionContext;
import com.melt.exceptions.InitBeanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class BeanInfo {
    private String name;
    private Class clazz;
    private AutoWiredBy autoWiredBy;
    private Constructor constructor = new Constructor(this);
    private List<BeanProperty> properties = newArrayList();
    private String factoryMethod;
    private Logger logger = LoggerFactory.getLogger(BeanInfo.class);

    public BeanInfo(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
        this.autoWiredBy = AutoWiredBy.NULL;
    }

    public void injectProperties(InjectionContext injectionContext) {
        autoWiredProperties(injectionContext);
        for (BeanProperty beanProperty : getProperties()) {
            beanProperty.injectPropertyValue(injectionContext);
        }
    }

    public void autoWiredProperties(InjectionContext injectionContext) {
        autoWiredBy.autoWired().autoWired(injectionContext, this);
    }

    public void addProperty(BeanProperty beanProperty) {
        this.properties.add(beanProperty);
    }

    public void addConstructorParameter(ConstructorParameter constructorParameter) {
        this.constructor.addParameter(constructorParameter);
    }

    public List<BeanProperty> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public void setAutoWiredBy(AutoWiredBy autoWiredBy) {
        this.autoWiredBy = autoWiredBy;
    }

    public Object initialize() {
        if (constructor.isDefaultConstructor()) {
            Class clazz = getClazz();
            if (factoryMethod == null) {
                return createBean(clazz);
            }
            return createBeanByFactory(clazz);
        }
        return null;
    }

    private Object createBeanByFactory(Class clazz) {
        try {
            Method factory = clazz.getDeclaredMethod(factoryMethod);
            Object bean = createBean(clazz);
            return factory.invoke(bean);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object createBean(Class clazz) {
        String message = String.format("Can't initialize bean: %s", clazz.getName());
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(message);
            throw new InitBeanException(message, e);
        } catch (IllegalAccessException e) {
            logger.error(message);
            throw new InitBeanException(message, e);
        }
    }

    public void setFactoryMethod(String factoryMethod) {
        this.factoryMethod = factoryMethod;
    }
}
