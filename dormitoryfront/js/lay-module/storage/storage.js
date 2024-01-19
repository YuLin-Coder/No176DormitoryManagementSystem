/**
 * author: LindaSilk
 * date: 2021年3月10日, 周三
 * description: 对存取localStorage数据的简单封装
 */
layui.define([], function (exports) {
    exports("storage", storage = {
        setToken:function (token){
            localStorage.setItem("token", token);
        },
        getToken:function (){
            return localStorage.getItem("token");
        },
        clear:function () {
            return localStorage.removeItem("token");
        }
    });
});
