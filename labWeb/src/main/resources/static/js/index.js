$(document).ready(function($) {
	var index_now = 0;
	var sum = $('.slideshow ul li').length;
	var imgplay;
	//切换图片
	function changeimg(){
		//$('.slideshow ul li:eq('+ index_now +')').addClass('active').siblings('li').removeClass('active');
		var li_now = $('.slideshow ul li:eq('+ index_now +')');
		li_now.siblings('li').hide();
		//li_now.siblings('li').fadeOut('slow');
		li_now.fadeIn('slow')
	}
	//自动播放动画
	function autoplay(){
		imgplay = setInterval(function(){
			index_now = (index_now + 1)%sum;
			changeimg();
		},4000);
	}
	autoplay();
	//清除动画
	function clearplay(){
		clearInterval(imgplay);
	}

	//点击左右切换
	$('.slideshow ul span.arrow_left').click(function(){
		clearplay();
		index_now = (index_now + sum -1)%sum;
		changeimg();
	});
	$('.slideshow ul span.arrow_right').click(function(){
		clearplay();
		index_now = (index_now + 1)%sum;
		changeimg();
	});

	//鼠标经过停止动画
	$('.slideshow ul').mouseenter(function(event) {
		clearplay();
	});
	$('.slideshow ul').mouseleave(function(event) {
		autoplay();
	});
});