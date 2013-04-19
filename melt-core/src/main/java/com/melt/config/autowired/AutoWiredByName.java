package com.melt.config.autowired;

import com.melt.config.BeanInfo;
import com.melt.core.BeansContainer;
import com.melt.exceptions.AutoWiredException;

import java.lang.reflect.Field;

public class AutoWiredByName extends AbstractAutoWiredBy {

    @Override
    protected Object getValue(BeansContainer beansContainer, Field field) {
        return beansContainer.resolve(field.getName());
    }
}
