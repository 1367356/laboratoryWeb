$(document).ready(function(){
	//点击文件上传
	$('input[name=upload]').click(function(){
		$('#show_upload').show();
		$('#bg').css('display','block');
		$('#bg').css('height',document.body.clientHeight+'px');
	});
	//关闭文件上传页面
	$('.upload_head>img').click(function(){
		$('#show_upload').hide();
		$('#bg').css('display','none');
	});
	//显示表格省略内容
	$('td').on('mouseenter',function(){
		if (this.offsetWidth < this.scrollWidth) {
			var text = $(this).text();
			$(this).attr('title',text);
		}
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
});
