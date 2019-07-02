
  var currentPosition = parseInt($("#floatMenu").css("top"));
  $(window).scroll(function() {
    var position = $(window).scrollTop();
    $("#floatMenu").stop().animate({
      "top" : position + currentPosition + "px"
    }, 2000);
  });

  $('#top1,#top2,#top3,#bottom1,#c_w1,#category1,#g_w1').click(function() {
    var id = $(this).attr("href");
    var offset = 60;
    var target = $(id).offset().top - offset;
    $('html,body').animate({
      scrollTop : target
    }, 1000);
    event.preventDefault();
  });
