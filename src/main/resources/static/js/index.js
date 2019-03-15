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


var navMenu = Vue.extend({
    name: 'nav-menu',
    props: ['navMenus'],
    template: `
        <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
                                     background-color="#20222A" text-color="#fff" active-text-color="#ffd04b"
                                     :unique-opened="true" :collapse="isCollapse">
            <template v-for="(item, index) in treeTree.children">
                <el-menu-item :index="treeTree.children[0].url + index + ''" v-if="treeTree.children[0].id === 19"
                              @click="changeMain(treeTree.children[0].url,'我的系统','首页')">
                    <i :class="treeTree.children[0].icon"></i>
                    <span slot="title">{{treeTree.children[0].text}}</span>
                </el-menu-item>
                <template v-if="item.children.length > 0" v-for="(child,index0) in item.children">
                    <!--v-if="item.children.length > 0" 没有子菜单则不显示-->
                    <el-submenu :index="child.url + index0 + ''"
                                class="myClass">
                        <template slot="title">
                            <i :class="child.icon"></i>
                            <span>{{child.text}}</span>
                        </template>

                        <!--<nav-menu :navMenus="child.children"></nav-menu>-->

                        <el-menu-item-group v-for="(child1, index1) in child.children"
                                            v-if="item.children">
                            <template slot="title">
                                <el-menu-item :index="child1.url + index1 + ''"
                                              @click="changeMain(child1.url, child.text, child1.text)">
                                    {{child1.text}}
                                </el-menu-item>
                            </template>
                        </el-menu-item-group>
                    </el-submenu>
                </template>
            </template>
        </el-menu>
    `
});
Vue.component("navMenu", navMenu);

const routes = [
    /*{
        path: '/h5',
        name: 'h5',
        component: html5,
        children: [
            {path:'basic', component: basic},
            {path:'big', component: big},
            // 配置根路由
            {path:'/', redirect: 'basic'}
        ]
    },*/
    // {path: '/table', component: java},
    // {path: '/python', component: python, name: 'python'},
    // 配置默认显示页
    {path: '/', redirect: 'main.html'}
];

const router = new VueRouter({
    routes
});

const vm = new Vue({
    el: '#main',
    // router: router,
    data: {
        username: localStorage.getItem('username'),

        user: {},
        menuList: {},
        treeTree: {},
        main: "main.html",
        password: '',
        newPassword: '',
        navTag: '我的系统',
        navTitle: "首页",

        isCollapse: false,
        isShrink: true,
    },
    methods: {
        //修改主页面显示
        changeMain: function (main, navTag, navTitle) {
            vm.navTag = navTag;
            vm.navTitle = navTitle;
            if (main === "main.html") {
                location.href = "index.html"
            } else {
                vm.main = main;
            }
        },

        logout: function () {
            localStorage.removeItem("username");
            location.href = 'logout'
        },

        getMenuList: function () {
            /*axios.get("menu/nav").then(function (response) {
                let r = response.data;
                vm.menuList = r.menuList;
                window.permissions = r.permissions;
            });*/

            let res = this;
            $.getJSON("menu/nav", function (r) {
                res.menuList = r.menuList;

                res.treeTree = r.treeTree;
                window.permissions = r.permissions;

                // console.log(res.treeTree.children[0].text);
                // for (let i = 0; i < res.treeTree.children.length; i++) {
                //     console.log(res.treeTree.children[i].id + "=" + res.treeTree.children[i].url + "="
                //         + res.treeTree.children[i].icon + "=" + res.treeTree.children[i].text)
                // }
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
                            if (r.code === 0) {
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


        handleShrink: function () {
            vm.isShrink = vm.isShrink !== true;
            console.log("isShrink = " + vm.isShrink);
        },
        /**
         * 导航菜单
         *
         * @param key
         * @param keyPath
         */
        handleOpen: function (key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose: function (key, keyPath) {
            console.log(key, keyPath);
        }
    },
    created: function () {
        this.getMenuList();
    },
    /*updated: function () {
        //路由
        let router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }*/
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
                vm.navTag = $('.sidebar-menu>li.active>a>span').text();
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