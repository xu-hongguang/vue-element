//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

//请求前缀
var baseURL = "/dxhy-gylpt/";
//发票匹配误差值
var cover = 20.00;

//登录token
var token = localStorage.getItem("token");
if (token == 'null') {
    parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        "token": token
    },
    // xhrFields: {
    //     withCredentials: true
    // },
    complete: function (xhr) {
        //token过期，则跳转到登录页面
        if (xhr.responseJSON.code == 401) {
            alert("登录超时，请重新登录", function () {
                parent.location.href = baseURL + 'login.html';
            });
        }
    }
});


//权限判断
function hasPermission(permission) {
    if (true) {
        return true;
    } else {
        return false;
    }
}

//重写alert
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg,{icon: 0,btn: ['确定', '取消']},
        function () {//确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        },function(){});
};

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}

$(function () {
    parent.window.scrollTo(0, 0);
    /*var scrollT= 0;
     $("body",parent.document).find("#myiframe").css('top',scrollT+'px');
     $(document).on("mousewheel DOMMouseScroll",function(e){ //模拟滚动条
     var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) ||  // chrome & ie
     (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1));              // firefox
     var hscroll=$("#myiframe").scrollTop();
     scrollT = scrollT + (delta*10);
     if(scrollT>0){
     scrollT=0
     }
     $("body",parent.document).find("#myiframe").css('top',scrollT+'px');
     });*/
    $(".detailFormList").parent('form').css('height', '3.0rem');
    $(".detailFormList").parent('form').css('overflow-y', 'scroll');
    /*导航的显示*/
    var r = window.location.href;
    $("body", parent.document).find('.sidebar-menu>li:first-child>a').css('background-color', 'transparent');
    if (r.match('main.html')) {
        $("body", parent.document).find('section.content-header').css('display', 'block');
    } else if (r.match('main_vendor.html')) {
        $("body", parent.document).find('section.content-header').css('display', 'block');
    } else if (r.match('index.html')) {
        $("body", parent.document).find('section.content-header').css('display', 'block');
    } else {
        $("body", parent.document).find('section.content-header').css('display', 'block');
    }
    /*获取分页值*/
    var hh = $(document).height();
    $("body", parent.document).find("#myiframe2").css('height', '6.22rem');
    $('input').change(function () {
    });
    // $("button").click(function () {
    //     var sixgod = window.setInterval(function(){
    //         console.log("--------------表格宽度自动修正开始--------------");
    //         superChange();
    //     },1000);
    //     window.setTimeout(function(){
    //         clearInterval(sixgod);
    //     },4000);
    // });
    // $("a").click(function () {
    //     var sixgod = window.setInterval(function(){
    //         console.log("--------------表格宽度自动修正开始--------------");
    //         superChange();
    //     },1000);
    //     window.setTimeout(function(){
    //         clearInterval(sixgod);
    //     },4000);
    // });


    var x = $(window).height() - 15;
    console.log("主要div高度" + x);
    $("#myiframe2").css("height", x);
    $("body", parent.document).find("#myiframe2").css('height', x);
    $(".sidebar-menu").css("height", x - 50);

    $(".sidebar-menu").css("overflow-x", "hidden");
    $(".sidebar-menu").css("overflow-y", "auto");


    /*自适应样式*/
    var wh = $(window).width();
    var whchange = function (width) {
    };
    whchange(wh);
    $(window).resize(function () {
        var wh = $(window).width();
        whchange(wh)
    });
    /*print mode*/
    /*var printAreaCount = 0;
     $.fn.printArea = function () {
     var ele = $(this);
     var idPrefix = "printArea_";
     removePrintArea(idPrefix + printAreaCount);
     printAreaCount++;
     var iframeId = idPrefix + printAreaCount;
     var iframeStyle = 'position:absolute;width:0.0rem;height:0.0rem;left:-5.0rem;top:-5.0rem;';
     iframe = document.createElement('IFRAME');
     $(iframe).attr({
     style: iframeStyle,
     id: iframeId
     });
     document.body.appendChild(iframe);
     var doc = iframe.contentWindow.document;
     $(document).find("link").filter(function () {
     return $(this).attr("rel").toLowerCase() == "stylesheet";
     }).each(
     function () {
     doc.write('<link type="text/css" rel="stylesheet" href="'
     + $(this).attr("href") + '" >');
     });
     doc.write('<div class="' + $(ele).attr("class") + '">' + $(ele).html()
     + '</div>');
     doc.close();
     var frameWindow = iframe.contentWindow;
     frameWindow.close();
     frameWindow.focus();
     frameWindow.print();
     };
     var removePrintArea = function (id) {
     $("iframe#" + id).remove();
     };*/
});