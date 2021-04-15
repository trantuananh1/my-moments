package com.hunganh.mymoments.base;

import java.util.Map;

public interface ObjectBase {
    public void addObject(Object object);
    public void addAssoc(Object o1, Object o2);
    public void addExtendData();
    public Map<String, Object> getInsertData(String data, long userId);
}
