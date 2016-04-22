$(document).ready(function() {
	$(".menu").hover(function() {
		if ($(this).hasClass("active") == false) {
			$(this).animate({"background-position-x":0},200);
		}
	}, function() {
		if ($(this).hasClass("active") == false) {
			$(this).animate({"background-position-x":-40},200);
		}
	});
});

var aside = {
	togleSub : function(main) {
		var h = 43;
		if ($("#u"+main).css("display") == "none") {
			$("#menu"+main+" .arrow img").attr("src","/images/arrow_top.png");
			var subCount = $("#u"+main).children().length;
			$("#u"+main).css("display","block");
			$("#u"+main).animate({height:h*subCount}, 200);
		} else {
			$("#menu"+main+" .arrow img").attr("src","/images/arrow_bottom.png");
			$("#u"+main).animate({height:0}, 100, function() {$("#u"+main).css("display","none");});
		}
	},
	setActive : function(main, sub) {
		var h = 43;
		var subCount = $("#u"+main).children().length;
		$("#menu"+main).addClass("active");
		$("#menu"+main+" .arrow img").attr("src","/images/arrow_top.png");
		$("#menu-sub"+main+""+sub).addClass("active");
		$("#u"+main).css("display","block");
		$("#u"+main).css("height",h*subCount);
	}
//	bulletShow : function(obj) {
//		$(obj).animate({"background-position-x":0},200);
//	},
//	bulletHide : function(obj) {
//		$(obj).animate({"background-position-x":-40},200);
//	},
};