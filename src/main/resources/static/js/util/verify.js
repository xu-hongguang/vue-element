//产生随机的颜色值
function getColor() {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}

//将验证码保存到后台session
function saveVerify(verify) {

    // location.href = 'saveVerify';

    $.ajax({
        type: 'post',
        url: 'saveVerify',
        data: {
            verify: verify,
        },
        dataType: 'text',
        success: function (data) {
            if (data === 'suc') {
                console.log("验证码保存成功！")
            }
        }
    })
}

var canvas, context;
var str = ''; //获取验证码值
/*window.onload = function () {
    //先画验证码，在保存
    draw();
    saveVerify(str);
    //点击更新
    document.getElementById("canvas").onclick = function () {
        context.clearRect(0, 0, 120, 40);//清掉canvas
        draw();
        saveVerify(str)
    };
    // document.getElementById("text").innerText = str;
};*/

/**
 * 初始化
 */
function init() {
    //先画验证码，在保存
    draw();
    saveVerify(str);
}

/**
 * 点击更新并保存
 */
function updateVerify() {
    //点击更新
    // document.getElementById("canvas").onclick = function () {
        context.clearRect(0, 0, 120, 32);//清掉canvas
        draw();
        saveVerify(str)
    // };
}

//画出验证码
function draw() {
    canvas = document.getElementById("canvas"); //演员
    context = canvas.getContext("2d"); //演员表演的舞台
    context.strokeRect(0, 0, 120, 32);
    var sCode = "A,a,B,b,C,E,e,F,f,G,g,H,h,J,j,K,L,M,m,N,n,O,P,Q,q,R,r,S,T,t,U,u,V,W,X,Y,y,Z,0,1,2,3,4,5,6,7,8,9";
    var aCode = sCode.split(",");
    str = '';
    for (var i = 0; i < 4; i++) {
        var x = 20 + 20 * i; //让四个文字的x轴间距20
        var y = 20 + Math.random() * 2;//让四个文字的y轴在20~30之间随机取值
        var index = Math.floor(Math.random() * aCode.length); //产生随机的索引值
        var s = aCode[index];
        str += s;//获取验证码值
        context.font = "bold 25px 微软雅黑";
        context.fillStyle = getColor();
        var deg = 60 * Math.random() * Math.PI / 180;
        context.translate(x, y); //让canvas移动
        context.rotate(deg);//让canvas旋转
        context.fillText(s, 0, 0); //在旋转后的canvas的左上角写内容
        context.rotate(-deg);//归为
        context.translate(-x, -y);//归为
    }
    //干扰线
    for (var i = 0; i < 8; i++) {
        context.beginPath();//声明开始一个路径
        context.moveTo(Math.random() * 120, Math.random() * 32);//起点
        context.lineTo(Math.random() * 120, Math.random() * 32);//终点
        context.strokeStyle = getColor();//随机颜色
        context.stroke();//执行绘制
    }
    //干扰点
    for (var i = 0; i < 50; i++) {
        context.beginPath();
        var x = Math.random() * 120;
        var y = Math.random() * 32;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.strokeStyle = getColor();
        context.stroke();
    }
}

/*
$(function () {
    $(".login").click(function () {
        $.ajax({
            type: 'post',
            url: 'login/verify',
            data: {
                input: $("#text").val()
            },
            dataType: 'json',
            success: function (data) {
                if (data.isSuc === "suc") {
                    alert("登录成功！")
                } else {
                    alert("登录失败！")
                }
            }
        })
    })
});*/
