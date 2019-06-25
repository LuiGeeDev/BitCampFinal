function getMessage(messageId) {
  $.ajax({
    url: "message/call",
    dataType: "html",
    data: {
      id: messageId,
    },
    success: function(data) {
      $(".message-paper").html(data);
    },
    error: function(xhr) {
      alert("쪽지를 불러오지 못했습니다.");
    },
  }).done(function() {
    if ($("#reply")) {
      $("#reply").click(function() {
        $(".modal").css("display", "block");
        const receiver_id = $(this)
          .siblings(".hidden")
          .text();
        $(".message-form").append(
          "<input type='hidden' value='" +
            receiver_id +
            "' name='receiver' class='receiver'>"
        );
      });
    }

    $("#refresh").click(function() {
      getMessage(messageId);
    });

    $("#delete").click(function() {
      const answer = confirm("쪽지를 삭제하시겠습니까?");
      if (answer) {
        location.href = "message/delete?id=" + messageId;
      }
    });
  });
}

$(function() {
  if (fromMain) {
    getMessage(messageId);
  }
});

$(".message").click(function() {
  const messageId = $(this)
    .children(".hidden")
    .text();
  getMessage(messageId);
});

$(".close-btn").click(function() {
  $(".receiver").remove();
  $(".modal").css("display", "none");
});
