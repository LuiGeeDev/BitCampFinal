$(function() {
  $('[data-toggle="popover"]').popover({
    title: "알림",
    content: function() {
      return $("#notices").html();
    },
    html: true
  });
});
