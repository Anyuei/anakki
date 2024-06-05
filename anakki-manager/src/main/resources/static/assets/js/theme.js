"use strict";

$(window).on("load", function () {


}),
    function userDetail() {

    }
$(window).on('load resize', function () {

    // Background image holder - Static hero with fullscreen autosize
    if ($('.spotlight').length) {
        $('.spotlight').each(function () {

            var $this = $(this);
            var holderHeight;

            if ($this.data('spotlight') == 'fullscreen') {
                if ($this.data('spotlight-offset')) {
                    var offsetHeight = $('body').find($this.data('spotlight-offset')).height();
                    holderHeight = $(window).height() - offsetHeight;
                } else {
                    holderHeight = $(window).height();
                }


                if ($(window).width() > 991) {
                    $this.find('.spotlight-holder').css({
                        'height': holderHeight + 'px'
                    });
                } else {
                    $this.find('.spotlight-holder').css({
                        'height': 'auto'
                    });
                }
            }
        })
    }
}),

    $(document).ready(function () {

        // Plugins init
        $(".scrollbar-inner")[0] && $(".scrollbar-inner").scrollbar().scrollLock();
        $('[data-stick-in-parent="true"]')[0] && $('[data-stick-in-parent="true"]').stick_in_parent();
        $('.selectpicker')[0] && $('.selectpicker').selectpicker();
        $('.textarea-autosize')[0] && autosize($('.textarea-autosize'));
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').each(function () {
            var popoverClass = '';
            if ($(this).data('color')) {
                popoverClass = 'popover-' + $(this).data('color');
            }
            $(this).popover({
                trigger: 'focus',
                template: '<div class="popover ' + popoverClass + '" role="tooltip"><div class="arrow"></div><h3 class="popover-header"></h3><div class="popover-body"></div></div>'
            })
        });


        // Floating label
        $('.form-control').on('focus blur', function (e) {
            $(this).parents('.form-group').toggleClass('focused', (e.type === 'focus' || this.value.length > 0));
        }).trigger('blur');


        // Custom input file
        $('.custom-input-file').each(function () {
            var $input = $(this),
                $label = $input.next('label'),
                labelVal = $label.html();

            $input.on('change', function (e) {
                var fileName = '';

                if (this.files && this.files.length > 1)
                    fileName = (this.getAttribute('data-multiple-caption') || '').replace('{count}', this.files.length);
                else if (e.target.value)
                    fileName = e.target.value.split('\\').pop();

                if (fileName)
                    $label.find('span').html(fileName);
                else
                    $label.html(labelVal);
            });


            // Firefox bug fix
            $input.on('focus', function () {
                $input.addClass('has-focus');
            })
                .on('blur', function () {
                    $input.removeClass('has-focus');
                });
        });


        // NoUI Slider
        if ($(".input-slider-container")[0]) {
            $('.input-slider-container').each(function () {

                var slider = $(this).find('.input-slider');
                var sliderId = slider.attr('id');
                var minValue = slider.data('range-value-min');
                var maxValue = slider.data('range-value-max');

                var sliderValue = $(this).find('.range-slider-value');
                var sliderValueId = sliderValue.attr('id');
                var startValue = sliderValue.data('range-value-low');

                var c = document.getElementById(sliderId),
                    d = document.getElementById(sliderValueId);

                noUiSlider.create(c, {
                    start: [parseInt(startValue)],
                    connect: [true, false],
                    //step: 1000,
                    range: {
                        'min': [parseInt(minValue)],
                        'max': [parseInt(maxValue)]
                    }
                });

                c.noUiSlider.on('update', function (a, b) {
                    d.textContent = a[b];
                });
            })

        }

        if ($("#input-slider-range")[0]) {
            var c = document.getElementById("input-slider-range"),
                d = document.getElementById("input-slider-range-value-low"),
                e = document.getElementById("input-slider-range-value-high"),
                f = [d, e];

            noUiSlider.create(c, {
                start: [parseInt(d.getAttribute('data-range-value-low')), parseInt(e.getAttribute('data-range-value-high'))],
                connect: !0,
                range: {
                    min: parseInt(c.getAttribute('data-range-value-min')),
                    max: parseInt(c.getAttribute('data-range-value-max'))
                }
            }), c.noUiSlider.on("update", function (a, b) {
                f[b].textContent = a[b]
            })
        }

        // Scroll to anchor with animation
        $('.scroll-me, .toc-entry a').on('click', function (event) {
            var hash = $(this).attr('href');
            var offset = $(this).data('scroll-to-offset') ? $(this).data('scroll-to-offset') : 0;

            // Animate scroll to the selected section
            $('html, body').stop(true, true).animate({
                scrollTop: $(hash).offset().top - offset
            }, 600);

            event.preventDefault();
        });

    }),

    $(document).ready(function () {
        $("body").on("click", "[data-action]", function (e) {

            e.preventDefault();

            var $this = $(this);
            var action = $this.data('action');
            var target = '';

            switch (action) {
                case "offcanvas-open":
                    target = $this.data("target"), $(target).addClass("open"), $("body").append('<div class="body-backdrop" data-action="offcanvas-close" data-target=' + target + " />");
                    break;
                case "offcanvas-close":
                    target = $this.data("target"), $(target).removeClass("open"), $("body").find(".body-backdrop").remove();
                    break;

                case 'aside-open':
                    target = $this.data('target');
                    $this.data('action', 'aside-close');
                    $this.addClass('toggled');
                    $(target).addClass('toggled');
                    $('.content').append('<div class="body-backdrop" data-action="aside-close" data-target=' + target + ' />');
                    break;


                case 'aside-close':
                    target = $this.data('target');
                    $this.data('action', 'aside-open');
                    $('[data-action="aside-open"], ' + target).removeClass('toggled');
                    $('.content, .header').find('.body-backdrop').remove();
                    break;
            }
        })
    });


//设置cookie
function setCookie(name, value, day) {
    const date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires=' + date;
};

//获取cookie
function getCookie(name) {
    const reg = RegExp(name + '=([^;]+)');
    const arr = document.cookie.match(reg);
    if (arr) {
        return arr[1];
    } else {
        return '';
    }
};

//删除cookie
function delCookie(name) {
    setCookie(name, null, -1);
};

function logout() {
    localStorage.removeItem('jwtManageToken'); // Remove the JWT token from local storage
}

function userLogout() {
    localStorage.removeItem('user-token'); // Remove the JWT token from local storage
}

function listMenu() {
    const get = new XMLHttpRequest();
    get.open("GET", "/manage/menu/list", true);
    get.onreadystatechange = function () {
        if (get.readyState === 4 && get.status === 200) {
            let html = "";
            const response = JSON.parse(get.responseText);

            const itemList = response.data;

            for (let i = 0; i < itemList.length; i++) {
                const item = itemList[i];
                //获取路径后缀
                const path = item.path;
                const pathArray = path.split("/");
                const lastPath = pathArray[pathArray.length - 1];

                //获取当前路径后缀
                const localPath = window.location.pathname
                const localPathArray = localPath.split("/");
                const localPathLastPath = localPathArray[localPathArray.length - 1];


                const menuName = item.menuName;
                const isActive = localPathLastPath === lastPath;
                // Add active class if the paths match
                const activeClass = isActive ? "active" : "";
                html = html +
                    "<a href=\"" + path + "\" class=\"list-group-item list-group-item-action " + activeClass + "\" aria-current=\"false\" id=\"" + menuName + "\">" + menuName + "</a>\n"
            }
            document.getElementById("menu-list").innerHTML = html;
        }
    };
    const token = localStorage.getItem('jwtManageToken'); // Get the JWT token from localStorage
    get.setRequestHeader('authorization', token); // Include the token in the Authorization header
    get.send();
}


// Get the current page URL


function loadPersonDetail() {
    if (null != localStorage.getItem('user-token')) {
        const userDetail = document.getElementById("user-detail");
        const userAvatar = document.getElementById("user-avatar");
        $.ajax({
            url: "/api/anakki/user/detail",
            type: "GET",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('authorization', localStorage.getItem('user-token')); // Include the token in the Authorization header
            },
            success: function (response) {
                if (response.success) {
                    const user = response.data;
                    const avatar = user.avatar;

                    const nickname = user.nickname;
                    const exp = user.exp;
                    const loginDays = user.loginDays;
                    const state = user.state;
                    userDetail.innerHTML = `
                    <a class="nav-link dropdown-toggle" href="#" id="navbar_main_dropdown_1" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${nickname}</a>
                    <div class="dropdown-menu" aria-labelledby="navbar_1_dropdown_1">
                        <a class="dropdown-item" >昵称:${nickname}</a>
                        <a class="dropdown-item">经验值:${exp}</a>
                        <a class="dropdown-item">累计登录${loginDays}天</a>
                        <a class="dropdown-item">${state}</a>
                        <a class="dropdown-item" href="/pages/telephone-verify.html">设置手机号</a>
                        <a class="dropdown-item" href="/pages/sign-in.html" onclick="userLogout()">登出</a>
                    </div>`;
                    userAvatar.innerHTML = `<img src="${avatar}" alt="" style="width: 3rem; border-radius: 50%;" onclick="showAvatarModal()">`;
                }
            },
            error: function (xhr, status, error) {
                console.error(error);
            }
        });
    }
    changeLoginPage;
}

function showAvatarModal() {
    $('#avatarModal').modal('show'); // Show the avatar modal
}

function saveAvatar() {
    const avatarInput = document.getElementById("avatarInput");
    const file = avatarInput.files[0];
    const formData = new FormData();
    formData.append("file", file);
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/anakki/user/upload-avatar", true); // Replace with your backend API endpoint
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            const alertDiv = document.createElement("div");
            alertDiv.classList.add("alert", "alert-warning", "alert-dismissible", "fade", "show", "fixed-top");
            alertDiv.setAttribute("role", "alert");
            if (xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                if (response.success === false) {
                    const errorMessage = response.data;
                    const alertDiv = document.createElement("div");
                    alertDiv.classList.add("alert", "alert-warning", "alert-dismissible", "fade", "show", "fixed-top");
                    alertDiv.setAttribute("role", "alert");
                    alertDiv.innerHTML = `
                <span class="alert-inner--icon"><i class="fas fa-exclamation"></i></span>
                <span class="alert-inner--text"><strong>错误!</strong> ${errorMessage}</span>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            `;
                    document.body.appendChild(alertDiv);
                } else {
                    const alertDiv = document.createElement("div");
                    alertDiv.classList.add("alert", "alert-success", "alert-dismissible", "fade", "show", "fixed-top");
                    alertDiv.setAttribute("role", "alert");
                    alertDiv.innerHTML = `
                <span class="alert-inner--icon"><i class="fas fa-exclamation"></i></span>
                <span class="alert-inner--text"><strong>成功!</strong></span>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            `;
                    // Handle success response
                    console.log(response.data);
                    loadPersonDetail();
                }
            } else {
                // Handle error response
                const errorMessage = "Error: " + xhr.status + " " + xhr.statusText;
                // ...
            }
            // Close the modal
            $('#avatarModal').modal('hide');
        }
    };
    xhr.setRequestHeader('authorization', localStorage.getItem('user-token'));
    xhr.send(formData);
    // Close the modal
    $('#avatarModal').modal('hide');
}

function insertCreateTime() {
    const startDate = new Date("2023-11-01");
    const currentDate = new Date();
    const timeElapsed = currentDate - startDate;
    const daysElapsed = Math.floor(timeElapsed / (1000 * 60 * 60 * 24));
    document.getElementById("time-container").innerHTML = "距离今天已经有 " + daysElapsed + " 天。";
}

function changeLoginPage() {
    const currentPage = window.location.href;
    // Get the auth link element
    const authLink = document.getElementById("auth-link");
    // Check if the current page is login or register
    if (currentPage.includes("sign-in.html")) {
        // Set the auth link text to "注册"
        authLink.innerText = "注册";
        // Set the auth link href to the register page URL
        authLink.href = "../pages/sign-up.html";
    } else if (currentPage.includes("sign-up.html")) {
        // Set the auth link text to "登录"
        authLink.innerText = "登录";
        // Set the auth link href to the login page URL
        authLink.href = "../pages/sign-in.html";
    }
}

function refreshCaptcha() {
    const captchaImg = document.querySelector('.captcha-img');
    captchaImg.src = "/base/system/captcha?" + new Date().getTime();
}

/**
 * 原作者   简书 : GaoSQ
 * 链接     https://www.jianshu.com/p/84e995404b96
 */
var EventUtil = {
    addHandler: function (element, type, handler) {
        if (element.addEventListener)
            element.addEventListener(type, handler, false);
        else if (element.attachEvent)
            element.attachEvent("on" + type, handler);
        else
            element["on" + type] = handler;
    },
    removeHandler: function (element, type, handler) {
        if (element.removeEventListener)
            element.removeEventListener(type, handler, false);
        else if (element.detachEvent)
            element.detachEvent("on" + type, handler);
        else
            element["on" + type] = handler;
    },
    /**
     * 监听触摸的方向
     * @param target            要绑定监听的目标元素
     * @param isPreventDefault  是否屏蔽掉触摸滑动的默认行为（例如页面的上下滚动，缩放等）
     * @param upCallback        向上滑动的监听回调（若不关心，可以不传，或传false）
     * @param rightCallback     向右滑动的监听回调（若不关心，可以不传，或传false）
     * @param downCallback      向下滑动的监听回调（若不关心，可以不传，或传false）
     * @param leftCallback      向左滑动的监听回调（若不关心，可以不传，或传false）
     */
    listenTouchDirection: function (target, isPreventDefault, upCallback, rightCallback, downCallback, leftCallback) {
        this.addHandler(target, "touchstart", handleTouchEvent);
        this.addHandler(target, "touchend", handleTouchEvent);
        this.addHandler(target, "touchmove", handleTouchEvent);
        var startX;
        var startY;

        function handleTouchEvent(event) {
            switch (event.type) {
                case "touchstart":
                    startX = event.touches[0].pageX;
                    startY = event.touches[0].pageY;
                    break;
                case "touchend":
                    var spanX = event.changedTouches[0].pageX - startX;
                    var spanY = event.changedTouches[0].pageY - startY;
                    //test
                    // console.log("startX:", startX);//[old] X
                    // console.log("startY:", startY);//[old] Y
                    // console.log("event.touches[0].pageX:", event.changedTouches[0].pageX);//[now] X
                    // console.log("event.touches[0].pageY:", event.changedTouches[0].pageY);//[now] Y
                    // console.log("spanX:", spanX);//[span] X
                    // console.log("spanY:", spanY);//[span] X
                    if (Math.abs(spanX) > Math.abs(spanY)) {      //认定为水平方向滑动
                        if (spanX > 0) {         //向右
                            if (rightCallback)
                                rightCallback();
                        } else if (spanX < -0) { //向左
                            if (leftCallback)
                                leftCallback();
                        }
                    } else {                                    //认定为垂直方向滑动
                        if (spanY > 0) {         //向下
                            if (downCallback)
                                downCallback();
                        } else if (spanY < -0) {//向上
                            if (upCallback)
                                upCallback();
                        }
                    }
                break;
                case "touchmove":
                    //阻止默认行为
                    if(isPreventDefault)
                        event.preventDefault();
                    break;
            }
        }
    }
};
    window.alert = alert;
    function alert(data, callback) { //回调函数
    var alert_bg = document.createElement('div'),
    alert_box = document.createElement('div'),
    alert_text = document.createElement('div'),
    alert_btn = document.createElement('div'),
    textNode = document.createTextNode(data ? data : ''),
    btnText = document.createTextNode('确 定');

    // 控制样式
    css(alert_bg, {//背景颜色设置
    'position': 'fixed',
    'top': '0',
    'left': '0',
    'right': '0',
    'bottom': '0',
    'background-color': 'rgba(0, 0, 0, 0.1)',
    'z-index': '999999999'
});

    css(alert_box, {//控制盒子大小、背景颜色上下边距
    'width': '270px',
    'max-width': '90%',
    'font-size': '16px',
    'text-align': 'center',
    'background-color': '#fff',
    'border-radius': '15px',
    'position': 'absolute',
    'top': '50%',
    'left': '50%',
    'transform': 'translate(-50%, -50%)'
});

    css(alert_text, {
    'padding': '10px 15px',
    'border-bottom': '1px solid #ddd'
});

    css(alert_btn, {
    'padding': '10px 0',
    'color': '#007aff',
    'font-weight': '600',
    'cursor': 'pointer'
});

    // 内部结构套入
    alert_text.appendChild(textNode);
    alert_btn.appendChild(btnText);
    alert_box.appendChild(alert_text);
    alert_box.appendChild(alert_btn);
    alert_bg.appendChild(alert_box);

    // 总体显示到页面内
    document.getElementsByTagName('body')[0].appendChild(alert_bg);

    // 肯定绑定点击事件删除标签
    alert_btn.onclick = function() {
    alert_bg.parentNode.removeChild(alert_bg);
    if (typeof callback === 'function') {
    callback(); //回调
}
}
}

    function css(targetObj, cssObj) {
    var str = targetObj.getAttribute("style") ? targetObj.getAttribute('style') : '';
    for (var i in cssObj) {
    str += i + ':' + cssObj[i] + ';';
}
    targetObj.style.cssText = str;
}
    //点击确定跳转到指定地址
    alert('提示信息!', function() {window.location.href = 'http://cn.bing.com';});
