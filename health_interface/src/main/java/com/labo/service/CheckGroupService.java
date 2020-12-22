package com.labo.service;

import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
    public PageResult findPage(QueryPageBean pageBean);
    public CheckGroup findById(Integer id);
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds);
    public List<CheckGroup> findAll();
}
