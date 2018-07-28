$(document).ready(function() {
	if ($.cookie("rmbUser") == true) {
		$("#ck_rmbUser").attr("checked",true);
		$("#username").val($.cookie("username"));
		$("#password").val($.cookie("password"));
	}
	$("input[type=submit").click(save());
});
function save(){
	if ($("ck_rmbUser").attr("checked")) {
		var str_username = $("#username").val();
		var str_password = $("#password").val();
		//存储一个带7天期限的cookie
		$.cookie("rmbUser", "true", {expires: 7});
		$.cookie("username", str_username, {expires: 7});
		$.cookie("password", str_password, {expires: 7});
	}
	else {
		$.cookie("rmbUser", "false", {expires: -1});
		$.cookie("username", "", {expires: -1});
		$.cookie("password", "", {expires: -1});
	}
}