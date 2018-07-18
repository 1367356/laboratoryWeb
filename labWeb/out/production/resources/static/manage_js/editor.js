$(function() {

	/*-----图片编辑器部分-----*/
	$('.black-cloth').click(closeTailor);
	$('.close-tailoring').click(closeTailor);
	(window.onresize = function () {
	    var win_height = $(window).height();
	    var win_width = $(window).width();
	    if (win_width <= 768){
	        $(".tailoring-content").css({
	            "top": (win_height - $(".tailoring-content").outerHeight())/2,
	            "left": 0
	        });
	    }else{
	        $(".tailoring-content").css({
	            "top": (win_height - $(".tailoring-content").outerHeight())/2,
	            "left": (win_width - $(".tailoring-content").outerWidth())/2
	        });
	    }
	})();

	//弹出图片裁剪框
	$("#replaceImg").on("click",function () {
	    $(".tailoring-container").toggle();
	});
	//cropper图片裁剪
	$('#tailoringImg').cropper({
	    aspectRatio: 3/4,//默认比例
	    preview: '.previewImg',//预览视图
	    guides: false,  //裁剪框的虚线(九宫格)
	    autoCropArea: 0.5,  //0-1之间的数值，定义自动剪裁区域的大小，默认0.8
	    movable: false, //是否允许移动图片
	    dragCrop: true,  //是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
	    movable: true,  //是否允许移动剪裁框
	    resizable: true,  //是否允许改变裁剪框的大小
	    zoomable: false,  //是否允许缩放图片大小
	    mouseWheelZoom: false,  //是否允许通过鼠标滚轮来缩放图片
	    touchDragZoom: true,  //是否允许通过触摸移动来缩放图片
	    rotatable: true,  //是否允许旋转图片
	    crop: function(e) {
	        // 输出结果数据裁剪图像。
	    }
	});
	//旋转
	$(".cropper-rotate-btn").on("click",function () {
	    $('#tailoringImg').cropper("rotate", 45);
	});
	//复位
	$(".cropper-reset-btn").on("click",function () {
	    $('#tailoringImg').cropper("reset");
	});
	//换向
	var flagX = true;
	$(".cropper-scaleX-btn").on("click",function () {
	    if(flagX){
	        $('#tailoringImg').cropper("scaleX", -1);
	        flagX = false;
	    }else{
	        $('#tailoringImg').cropper("scaleX", 1);
	        flagX = true;
	    }
	    flagX != flagX;
	});

	//裁剪后的处理
	$("#sureCut").on("click",function () {
	    if ($("#tailoringImg").attr("src") == null ){
	        return false;
	    }else{
	        var cas = $('#tailoringImg').cropper('getCroppedCanvas');//获取被裁剪后的canvas
	        var base64url = cas.toDataURL('image/png'); //转换为base64地址形式
	        $("#titleImage").prop("src",base64url);//显示为图片的形式
	        $('#for_titleImg').val(base64url);

	        //关闭裁剪框
	        closeTailor();
	    }
	});
	//关闭裁剪框
	function closeTailor() {
	    $(".tailoring-container").toggle();
	}

	/*-----图片编辑器部分结束-----*/

//	/*-----提交图片-----*/
//	$('form.edit_form').submit(function(){
//        var imgTitle = $('input[name="titleImage"]').val();
//        if(!imgTitle){
//            alert('您还未选择图片！');
//            return false;
//        }
//	});
	/*-----提交图片结束-----*/
});