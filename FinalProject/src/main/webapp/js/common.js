const sock = new SockJS("/socket");
const client = Stomp.over(sock);
client.connect({}, function() {
  client.subscribe("/user/queue/notice", function(data) {
    const notification = JSON.parse(data.body);
    addNotice(notification);
    showToast(notification);
  });
});

function addNotice(notification) {
  const link = $(`<a class="notice-link" href="${notification.link}">`);
  const notice = $("<div class='notice'>");
  const noticeTitle = $(`<h6 class="notice-title">${notification.title}</h6>`);
  const noticeContent = $(`<p class="notice-content">${notification.content}</p>`);
  notice.append(noticeTitle).append(noticeContent);
  link.append(notice);
  
  if ($(".notice").length === 20) {
    $(".notice")[19].remove();
  }
  
  if ($(".no-notice")) {
    $(".no-notice").remove();
  }
  
  $("#notices").prepend(link);
  $(".noti-count").show();
  $(".noti-count").text(Number($(".noti-count").text()) + 1);
}

function showToast(notification) {
  const toastPositioner = $(".toast-positioner");
  const link = $(`<a href="${notification.link}">`);
  const toast = $(`<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="5000">`);
  const toastHeader = $(`<div class="toast-header">`);
  const toastBody = $(`<div class="toast-body">`);
  const toastTitle = $(`<strong class="mr-auto">${notification.title}</strong>`);
  const toastContent = $(`<p class="toast-content">${notification.content}</p>`);
  const closeButton = $(`<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button>`);
  toastHeader.append(toastTitle).append(closeButton);
  toastBody.append(toastContent);
  link.append(toastBody);
  toast.append(toastHeader).append(link);
  toastPositioner.append(toast);
  $(".toast-positioner .toast:last-child").toast('show');
}

// 추천 알림
function sendVoteNotice(senderName, receiverUsername, link) {
  const notification = {
    title: "추천",
    content: `${senderName}님에게 추천받았습니다.`,
    username: receiverUsername,
    link
  };

  client.send("/app/notice/vote", {}, JSON.stringify(notification));
}

// 댓글 알림
function sendCommentNotice(senderName, receiverUsername, link) {
  const notification = {
    title: "댓글",
    content: `${senderName}님이 댓글을 달았습니다.`,
    username: receiverUsername,
    link
  };

  client.send("/app/notice/comment", {}, JSON.stringify(notification));
}

// 
function sendReplyNotice(senderName, receiverUsername, link) {
  const notification = {
    title: "답글",
    content: `${senderName}님이 답글을 달았습니다.`,
    username: receiverUsername,
    link
  };

  client.send("/app/notice/reply", {}, JSON.stringify(notification));
}

function sendAssembleNotice(senderName, username, link) {
  const notification = {
    title: "모여라",
    content: `${senderName} 강사님이 여기로 부릅니다.`,
    username: username,
    link
  };

  client.send("/app/notice/assemble", {}, JSON.stringify(notification));
}

function sendMessageNotice(senderName, receiverUsername) {
  const notification = {
      title: "쪽지",
      username: receiverUsername,
      link: "/message"
  }

  client.send("/app/notice/message", {}, JSON.stringify(notification));
}
