$(document).ready(function(){
	//点击上传/添加/修改密码
	$('.pub_load').click(function(){
		$('#public').show();
		$('#bg').css('display','block');
		$('#bg').css('height',document.body.clientHeight+'px');
	});
	$('.pri_load').click(function(){
		$('#private').show();
		$('#bg').css('display','block');
		$('#bg').css('height',document.body.clientHeight+'px');
	});
	$('.add_user').click(function(){
		$('#user').show();
		$('#bg').css('display','block');
		$('#bg').css('height',document.body.clientHeight+'px');
	});
	$('input[name="revise"]').click(function(){
		$('#re_pwd').show();
		$('#bg').css('display','block');
		$('#bg').css('height',document.body.clientHeight+'px');
	});
	//关闭文件上传div并清空其内容
	$('.upload_head>img').click(function(){
		$('.show_upload').hide();
		$('#bg').css('display','none');
		$(".right_text").html('');
		$(".show_upload .file_style input").val("");
	});
	//显示表格省略内容
	$('td').on('mouseenter',function(){
		if (this.offsetWidth < this.scrollWidth) {
			var text = $(this).text();
			$(this).attr('title',text);
		}
	});
	$('td').on('mouseleave',function(){
		layer.close(tips);
	});
	//删除资料
	$('.delete').click(function(){
		if (confirm('确认删除该资料？')) {
			return true;
		}
		else {
			return false;
		}
	});
	//显示选择的文件名
     $("input[type=file]").on("change", function(){
         var index = $(this).val().lastIndexOf("\\");
         var sFileName = $(this).val().substr((index+1));
         $(".right_text").html(sFileName);
     });

    //表单验证--添加用户
	$("#user input[type=submit]").click(function(){
		//内容不能为空
		var content = [];
		$("#user .file_style input").each(function(){ 
			content.push($(this).val());
		});
		for (var i = 0; i < content.length; i++) {
			if (content[i].length == 0) {
				switch (i) {
					case 0: //添加-用户名
						$("#user input[name=userName]+span").show();
						break;
					case 1: //添加-密码
						$("#user .pwd_1+span").show();
						break;
					case 2: //添加-再次输入密码
						$("#user .pwd_2+span").show();
						break;
					case 3: //添加-角色
						$("input[name=role]+span").show();
						break;
					default: //添加-描述
						break;
				}
				$("#user .tips_1").show();
			}
		}
		if ($("#user .tips_1").is(":visible")) {
			return false;
		}
		else {
			//判断两次密码是否一致
			var pwd = [];
			$("#user input[type=password]").each(function(){
				pwd.push($(this).val());
			});
			if (pwd[1]==pwd[2]) {
				return true;
			}
			else {
				$("#user .tips_2").show();
				$("#user .pwd_1+span").show();
				$("#user .pwd_2+span").show();
				return false;
			}
		}
	});
    //表单验证--修改密码
	$("#re_pwd input[type=submit]").click(function(){
		//内容不能为空
		var content = [];
		$("#re_pwd .file_style input").each(function(){
			content.push($(this).val());
		});
		for (var i = 0; i < content.length; i++) {
			if (content[i].length == 0) {
				switch (i) {
					case 0: //修改-旧密码
						$("#re_pwd input[name=old_pwd]+span").show();
						break;
					case 1: //修改-新密码
						$("#re_pwd .pwd_1+span").show();
						break;
					default: //修改-再次输入密码
						$("#re_pwd .pwd_2+span").show();
						break;
				}
				$("#re_pwd .tips_1").show();
			}
		}
		if ($("#re_pwd .tips_1").is(":visible")) {
			return false;
		}
		else {
			//判断两次密码是否一致
			var pwd = [];
			$("#re_pwd input[type=password]").each(function(){ 
				pwd.push($(this).val());
			});
			if (pwd[1]==pwd[2]) {
				return true;
			}
			else {
				$("#re_pwd .tips_2").show();
				$("#re_pwd .pwd_1+span").show();
				$("#re_pwd .pwd_2+span").show();
				return false;
			}
		}
	});
	
	//输入框获得焦点，隐藏错误提示
	$(".show_upload form .file_style input").focus(function(){
		$(".tips_1").hide();
		$(".tips_2").hide();
		$(".show_upload form input+span").hide();
	});
});
