/**
 * BestistPop v1.0.2
 * http://www.bestist.net
 *
 * Written by: bestist@naver.com, 2014
 */


// new window Open
function windowOpen(url, name, width, height, scrollYN, ResizeYN) {
    var top = screen.height / 2 - height / 2 - 50;
    var left = screen.width / 2 - width / 2 ;
    if (scrollYN == null) scrollYN = 'no';
    if (ResizeYN == null) ResizeYN = 'no';
    var win;
    win = open(url, name, 'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left + ',resizable=' + ResizeYN + ',status=no,toolbar=no,menubar=no,scrollbars=' + scrollYN);
    win.focus();
    return win;
}


var isMaskShow = false;
var isPopupOpen = false;
var pop = {

	openWindow:function(url, name, width, height, scrollYN, ResizeYN) {
		var top = screen.height / 2 - height / 2 - 50;
		var left = screen.width / 2 - width / 2 ;
		if (scrollYN == null) scrollYN = 'no';
		if (ResizeYN == null) ResizeYN = 'no';
		var win;
		win = open(url, name, 'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left + ',resizable=' + ResizeYN + ',status=no,toolbar=no,menubar=no,scrollbars=' + scrollYN);
		win.focus();
		return win;
	},
	
    open:function() {
        // Add the mask to body
        isPopupOpen = true;
        pop.showMask();
        $('body').append('<div id="pop-box"></div>');
    },
    openFull:function() {
        // Add the mask to body
        isPopupOpen = true;
        pop.showMask();
        $('body').append('<div id="pop-box-full"></div>');
    },
    popBoxCenter:function() {
        var popupleftmargin = ($('#pop-box').width() + 10) / 2;
        var popuptopmargin = ($('#pop-box').height() + 10 ) / 2  ;
        if (popuptopmargin > 300) {
        	popuptopmargin = 300;
        }
        $('#pop-box').css({
            'margin-top' : -popuptopmargin,
            'margin-left' : -popupleftmargin
        });
    },
    close:function() {
        isPopupOpen = false;
        $('#mask , #pop-box').fadeOut(100 , function() {
            pop.hideMask();
            $('#pop-box-full').remove();
            $('#pop-box').remove();
        }); 
    },
    
    hasMask:function() {
    	//console.log('#mask : ',isMaskShow);
    	return isMaskShow;
    },
    showMask:function() {
        $('body').append('<div id="mask" onclick="pop.close();"></div>');
        $('#mask').fadeIn(100);
    	//$('#mask').css("display","block");
        isMaskShow = true;
    },
    hideMask:function() {
        $('#mask').remove();
    	//$('#mask').css("display","none");
        isMaskShow = false;
    },
 
    // Alert을 팝업으로 띄운다.
    openAlert:function(title, txt) {
        var str = '';
        str += '<div class="pop-head">';
        str += '    <div class="pop-title">'+title+'</div>';
        str += '    <div class="pop-close"><img src="/lib/bestist/btn_close_sm.png" onclick="pop.close();"></div>';
        str += '</div>';
        str += '<div class="alert">'+txt+'</div>';
        pop.open();
        $("#pop-box").css("display","block");
        $("#pop-box").html(str);
        pop.popBoxCenter();
    },
    
    // div 의 내용을 팝업으로 띄운다.
    openDiv:function(id,fnc,param) {
        pop.open();
        $("#pop-box").css("display","block");
        $("#pop-box").html($('#'+id).html());
        if (fnc != null) {
            if (!(param == null || param == "")) {
                fnc(param);
            } else {
                fnc();
            }
        }
        pop.popBoxCenter();
    },
    openFullDiv:function(id,fnc,param) {
        pop.openFull();
        $("#pop-box-full").css("display","block");
        $("#pop-box-full").html($('#'+id).html());
        if (fnc != null) {
            if (!(param == null || param == "")) {
                fnc(param);
            } else {
                fnc();
            }
        }
        pop.popBoxCenter();
    },
 
    // url 페이지의 내용을 팝업으로 띄운다.
    openPage:function(url,fnc,param) {
        pop.open();
        $.ajax({
            type:"POST",
            url:url,
            dataType:"html",
            success:function(msg){
                //alert(msg);
                //$('body').append(msg);
                $("#pop-box").css("display","block");
                $("#pop-box").html(msg);
                if (fnc != null) {
                    if (!(param == null || param == "")) {
                        fnc(param);
                    } else {
                        fnc();
                    }
                }
                pop.popBoxCenter();
            }
        });
    },
    openFullPage:function(url,fnc,param) {
        pop.openFull();
        $.ajax({
            type:"POST",
            url:url,
            dataType:"html",
            success:function(msg){
                //alert(msg);
                //$('body').append(msg);
                $("#pop-box-full").css("display","block");
                $("#pop-box-full").html(msg);
                if (fnc != null) {
                    if (!(param == null || param == "")) {
                    	//console.log("fnc(param)");
                        fnc(param);
                    } else {
                    	//console.log("fnc():",fnc);
                        fnc();
                    }
                }
                pop.popBoxCenter();
            }
        });
    },
    zoom : function(img) {
    	var str = '';
		str += '	<div class="zoom-pop-box" style="background-image:url(\''+img+'\');">';
		str += '		<div class="pop-close"><img src="/lib/bestist/btn_close_sm.png" onclick="pop.close();"></div>';
		str += '	</div>';

    	pop.openFull();
        $("#pop-box-full").css("display","block");
        $("#pop-box-full").html(str);
        
        if (zoomScroll == null) {
	        zoomScroll = new IScroll('#zoom-img', {
	    		zoom: true,
	    		scrollX: true,
	    		scrollY: true,
	    		mouseWheel: true,
	    		wheelAction: 'zoom'
	    	});
        } else {
        	zoomScroll.refresh();
        }
    }
};