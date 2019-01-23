
//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}, index: 0},
    template: [
        '<li>',
        '<a  v-if="item.parientId == -1" :href="item.url">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '</a>',

        '<a  v-if="item.parientId == 27" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}<i class="fa fa-angle-right pull-right"></i></span>',
        '</a>',

        '<ul v-if="item.parientId == 27" class="treeview-menu">',
        '<menu-item :item="item" :index="index" v-for="(item, index) in item.childList"></menu-item>',
        '</ul>',
        '<a v-if="item.parientId == 2" :href="\'#\'+item.url">' +
        '<i v-if="item.icon != null" :class="item.icon"></i>' +
        '<i v-else ></i> {{item.name}}' +
        '</a>',

        '</li>'
    ].join('')
});

//iframe自适应
/*$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();*/

//注册菜单组件
Vue.component('menuItem', menuItem);

const vm = new Vue({
    el: '#main',
    data: {
        username: localStorage.getItem('username'),

        user: {},
        menuList: {},
        main: "main.html",
        navTitle: "首页",
        navTag: '我的系统'
    },
    methods: {
        logout: function () {
            localStorage.removeItem("username");
            location.href = 'logout'
        },

        getMenuList: function () {
            $.getJSON("menu/nav", function (r) {
                vm.menuList = r.menuList;
                window.permissions = r.permissions;
            });
        },
        updatePassword: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['5.5rem', '2.7rem'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (r) {
                            if (r.code == 0) {
                                layer.close(index);
                                layer.alert('修改成功', function () {
                                    location.reload();
                                });
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            });
        }
    },
    created: function () {
        this.getMenuList();
    },
    updated: function () {
        //路由
        let router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});


function routerList(router, menuList) {
    for (let key in menuList) {
        let menu = menuList[key];
        if (menu.isChild === 1) {
            routerList(router, menu.childList);
        } else if (menu.isChild === 0) {
            router.add('#' + menu.url, function () {
                let url = window.location.hash;
                //替换iframe的url
                vm.main = url.replace('#', '');

                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $(".sidebar-menu li").removeClass("active");
                $("a[href='" + url + "']").parents("li").addClass("active");

                vm.navTitle = $("a[href='" + url + "']").text();
                vm.navTag= $('.sidebar-menu>li.active>a>span').text();
            });
        }
    }
}


/**
 * 全屏切换
 * @param obj
 */
function fullScreen(obj) {
    let $this = $(obj);
    let element;
    if ($this.text() === '全屏') {
        $this.text("退出全屏");
        element = document.documentElement;
        if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen();
        } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();
        }
    } else {
        elem = document;
        $this.text("全屏");
        if (elem.webkitCancelFullScreen) {
            elem.webkitCancelFullScreen();
        } else if (elem.mozCancelFullScreen) {
            elem.mozCancelFullScreen();
        } else if (elem.cancelFullScreen) {
            elem.cancelFullScreen();
        } else if (elem.exitFullscreen) {
            elem.exitFullscreen();
        }
    }
}