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
  const notice = $("<div class='notice'>");
  const noticeTitle = $(`<h6 class="notice-title">${notification.title}</h6>`);
  const noticeContent = $(`<p class="notice-content">${notification.content}</p>`);
  notice.append(noticeTitle).append(noticeContent);
  if ($(".notice")[3]) {
    $(".notice")[3].remove();
  }
  $("#notices").prepend(notice);
}
function showToast(notification) {
  const toastPositioner = $(".toast-positioner");
  const toast = $(`<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="5000">`);
  const toastHeader = $(`<div class="toast-header">`);
  const toastBody = $(`<div class="toast-body">`);
  const toastTitle = $(`<strong class="mr-auto">${notification.title}</strong>`);
  const toastContent = $(`<p class="toast-content">${notification.content}</p>`);
  const closeButton = $(`<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button>`);
  toastHeader.append(toastTitle).append(closeButton);
  toastBody.append(toastContent);
  toast.append(toastHeader).append(toastBody);
  toastPositioner.append(toast);
  $(".toast:last-child").toast('show');
}
