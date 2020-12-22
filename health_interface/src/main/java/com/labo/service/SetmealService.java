package com.labo.service;

import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    public void add(Setmeal setmeal,Integer[] checkgroupIds);
    public PageResult findPage(QueryPageBean queryPageBean);
    public List<Setmeal> findAll();
    public Setmeal findById(Integer id);
    public List<Map> getSetmealReport();
}
