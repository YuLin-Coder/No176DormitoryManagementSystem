package com.silk.utils;

/**
 * @author LindaSilk
 * @date 2021年3月08日, 周一
 * @description 该部分用来做分页
 */
public class Entity {

    private Integer page;           // 页码数
    private Integer limit=10;       // 页面大小（默认值为10）

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
