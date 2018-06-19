$(document).ready(function() {
	//填充内容区域的导航栏
	var nav_pid = parseInt($('#nav_pid').text());
	var nav_id = parseInt($('#nav_id').text());
	function fill_content_nav(){
		var nav_div = '';
		switch(nav_pid){
			case 1:
				nav_div = '<div class="nav_head">实验室简介</div><ul class="nav_ul">';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=1&pid=1&page=1">实验室简介</a></li>';
				nav_div += '</ul>';
				break;
			case 2:
				nav_div = '<div class="nav_head">新闻公告</div><ul class="nav_ul">';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=1&pid=2&page=1">通知公告</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=2&pid=2&page=1">教学简报</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=3&pid=2&page=1">科研快讯</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=4&pid=2&page=1">桃李春风</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=5&pid=2&page=1">学子风采</a></li>';
				nav_div += '</ul>';
				break;
			case 3:
				nav_div = '<div class="nav_head">科学研究</div><ul class="nav_ul">';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=1&pid=3&page=1">研究领域</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=2&pid=3&page=1">科研项目</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=3&pid=3&page=1">学术论文</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=4&pid=3&page=1">获奖</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=5&pid=3&page=1">专利</a></li>';
				nav_div += '</ul>';
				break;
			case 4:
				nav_div = '<div class="nav_head">科研团队</div><ul class="nav_ul">';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=1&pid=4&page=1">团队教师</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=2&pid=4&page=1">博士后</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=3&pid=4&page=1">在读博士</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=4&pid=4&page=1">在读硕士</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=5&pid=4&page=1">光电菁英</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=6&pid=4&page=1">毕业学生</a></li>';
				nav_div += '</ul>';
				break;
			case 5:
				nav_div = '<div class="nav_head">人才培养</div><ul class="nav_ul">';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=1&pid=5&page=1">本科教学</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=2&pid=5&page=1">菁英计划</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=3&pid=5&page=1">研究生</a></li>';
				nav_div += '</ul>';
				break;
			case 6:
				nav_div = '<div class="nav_head">合作交流</div><ul class="nav_ul">';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=1&pid=6&page=1">国内</a></li>';
				nav_div += '<li class="nav_li"><a href="/background/querybyBackList?id=2&pid=6&page=1">国际</a></li>';
				nav_div += '</ul>';
				break;
			default:
				break;
		}
		$('.main_content .content_nav').append(nav_div);
		var active_li = $('.main_content li.nav_li').eq(nav_id-1);
		active_li.addClass('active');
		var nav_head = active_li.find('a').text();
		console.log(nav_head);
		$('.main_content .content_right .content_head span.head_title').text(nav_head);
	}
	fill_content_nav();

	//分页跳转
	$('.jump_page').click(function(event) {
		var page = $('input.page_num').val();
		if(page){
			$(location).attr('href', '/background/querybyBackList?id=' + nav_pid + '&pid=' + nav_id + '&page=' + page);
		}
	});

	//点击编辑
	$('#add').click(function(event) {
		
	});
	$('.content_list li span.edit').click(function(event) {
		
	});

	//删除
	$('.delete').click(function(){
		if (confirm('确认删除？')) {
			return true;
		}
		else {
			return false;
		}
	});
});