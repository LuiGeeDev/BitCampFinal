$(function() {
  if ($('.noti-count').text() === "0") {
    $('.noti-count').hide();
  }
  
  if ($('.message-count').text() === "0") {
    $('.message-count').hide();
  }
  
  $('[data-toggle="popover"]').popover({
    title: "새 알림",
    content: function() {
      return $("#notices").html();
    },
    html: true
  });
  
  $('[data-toggle="popover"]').on('shown.bs.popover', function() {
    $.ajax("/ajax/notification/checked");
    $(".noti-count").text("0");
    $(".noti-count").hide();
  });
});

