$(document).ready(function() {
	(function() {
		var pid = parseInt($('#nav_pid').text());
		var id = parseInt($('#nav_id').text());
		if (pid == 4) {
			$('.for_type').empty();
			var type_li = '';
			switch(id){
				case 1:
					type_li = '<label>职称:</label><select name="type"><option selected value="教授">教授</option><option value="副教授">副教授</option><option value="讲师">讲师</option><option value="团队负责人">团队负责人</option></select>';
				    break;
				case 2:
					type_li = '<label>职称:</label><select name="type"><option selected value="教授">教授</option><option value="副教授">副教授</option><option value="讲师">讲师</option><option value="博士后">博士后</option></select>';
					break;
				case 6:
					type_li = '<label>毕业年份:</label>' + createLi('届',10);
					break;
				default:
					type_li = '<label>入学年份:</label>' + createLi('级',10);
					break;
			}
			$('.for_type').append(type_li);
		}

		function createLi(text,num){
			var now = new Date();
			var year = now.getFullYear()+1;
			var create_li = '<select name="type">';
			for(let i=1;i<=num;i++){
				if(i==1){
					create_li += '<option selected value="'+ (year-num+i) + text +'">'+ (year-num+i) + text +'</option>';
				}else{
					create_li += '<option value="'+ (year-num+i) + text +'">'+ (year-num+i) + text +'</option>';
				}
			}
			create_li+='</select>';
			return create_li;
		}
	})();
});