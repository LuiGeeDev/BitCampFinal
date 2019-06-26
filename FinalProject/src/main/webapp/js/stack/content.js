var currentPosition = parseInt($("#sidebox").css("top"));
$(window).scroll(function() { 
  var position = $(window).scrollTop();
  $('#sidebox').animate({top:$(window).scrollTop()+"px" },{queue: false, duration: 500});
}); 
