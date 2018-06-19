$(document).ready(function(){
	
	//点击显示/隐藏团队人员
	$(".content_right .list_head").click(function(){
		$(this).next().toggle();
		$(this).children("img").toggle();
	});
	//移动图片上显示遮罩层
	$(".container img").mouseenter(function(){
		$(this).prev().show();
		$(this).prev().mouseout(function(){
			$(this).hide();
		});
	});
});