
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

        '<a  v-if="item.parientId == 0" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}<i class="fa fa-angle-right pull-right"></i></span>',
        '</a>',

        '<ul v-if="item.parientId == 0" class="treeview-menu">',
        '<menu-item :item="item" :index="index" v-for="(item, index) in item.subList"></menu-item>',
        '</ul>',
        '<a v-if="item.menulevel == 2" :href="\'#\'+item.menuaction">' +
        '<i v-if="item.image != null" :class="item.image"></i>' +
        '<i v-else ></i> {{item.menulabel}}' +
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        user: {},
        menuList: {},
        main: "main.html",
        password: '',
        newPassword: '',
        navTitle: "首页",
        navTag: '沃尔玛结算平台'
    },
    methods: {
        getMenuList: function () {
            $.getJSON(baseURL + "sys/menu/nav", function (r) {
                vm.menuList = r.menuList;
                window.permissions = r.permissions;
            });
        },
        getUser: function () {
            $.getJSON(baseURL + "sys/user/info", function (r) {
                vm.user = r.user;
                if(r.orgtype=='8'){
                    vm.main = 'main_vendor.html';
                }else{
                    vm.main = 'main.html';
                }
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
        },
        logout: function () {
            //删除本地token
            localStorage.removeItem("token");
            localStorage.removeItem("announceShowTimes");
            //跳转到登录页面
            location.href = baseURL + 'login.html';
        },
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['8.06rem', '4.67rem'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.dxhy.io/donate.jpg', 'no']
            });
        },
        switchClothes: function () {
            $("#hf").slideToggle("slow");
        }
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    },
    updated: function () {
        //路由
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});


function routerList(router, menuList) {
    for (var key in menuList) {
        var menu = menuList[key];
        if (menu.isbottom === 1) {
            routerList(router, menu.subList);
        } else if (menu.isbottom === 0) {
            router.add('#' + menu.menuaction, function () {
                var url = window.location.hash;
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