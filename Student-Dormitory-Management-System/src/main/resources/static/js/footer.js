var footerHTML = "<span style=\"margin-right: 36px\">©2023 软件过程与管理实训</span>" +
    "<span style=\"color:rgb(144, 145, 146)\">技术支持：https://www.layui.com/</span>";
var footerDOM = document.getElementById("sdms-footer");
if (typeof footerDOM !== "undefined" && footerDOM != null) {
    footerDOM.innerHTML = footerHTML;
}