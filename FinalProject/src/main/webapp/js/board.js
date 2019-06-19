var submit = document.getElementById("submit");

submit.addEventListener("click", function(event) {
  var writer = document.getElementById("writer").innerText;
  client.send("/topic/noti/" + writer, {}, JSON.stringify({
    writer : document.getElementById("comment-writer").value,
    content : document.getElementById("content").value,
    boardWriter : writer
  }));
});