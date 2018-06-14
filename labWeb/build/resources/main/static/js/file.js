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
		if (confirm('确认删除？')) {
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
        
  		//限制上传文件大小
    	if ((($("input[type=file]")[0].files[0].size).toFixed(2))>(100*1024*1024)){
    		alert("文件太大，请上传小于100M的文件");
    		return false;
    	}else {
    		$(".right_text").html(sFileName);
    	}
    });
    //上传文件不能为空
    $("#private input[type=submit]").click(function(){
    	if ($(".right_text").text() == 0) {
    		alert("文件不能为空，请选择文件！");
    		return false;
    	}else {
    		return true;
    	}
    });
    $("#public input[type=submit]").click(function(){
    	if ($(".right_text").text() == 0) {
    		alert("文件不能为空，请选择文件！");
    		return false;
    	}else {
    		return true;
    	}
    });
    //表单验证--添加用户
	$("#user input[type=submit]").click(function(){
		//内容不能为空
		var content = [];
		$("#user .file_style input").each(function(){ 
			content.push($(this).val());
		});
		for (var i = 0; i < content.length-1; i++) {
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
					default: //添加-描述
						break;
				}
				$("#user .tips_1").show();
			}
		}
		//角色不能为空
		var roles=$('input:radio[name="role"]:checked').val();
		if (roles == null) {
			$("#user label+span").show();
			$("#user .tips_1").show();
		}
		if ($("#user .tips_1").is(":visible")) {
			return false;
		}else {
			//判断两次密码是否一致
			var pwd = [];
			$("#user input[type=password]").each(function(){
				pwd.push($(this).val());
			});
			if (pwd[0]==pwd[1]) {
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
	//输入框获得焦点，隐藏错误提示
	$(".show_upload form .file_style input").focus(function(){
		$(".tips_1").hide();
		$(".tips_2").hide();
		$(".show_upload form input+span").hide();
		$(".show_upload form label+span").hide();
	});
});
