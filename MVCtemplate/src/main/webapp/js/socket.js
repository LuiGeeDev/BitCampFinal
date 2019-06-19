var user = document.getElementById("userid");
var sock = new SockJS("/sample");
var client = Stomp.over(sock);

if (user) {
  client.connect({}, function() {
    client.subscribe("/topic/noti/" + user.innerText, function(data) {
      var message = JSON.parse(data.body);
      var noti = document.getElementById("noti");
      var newNotice = document.createElement("div");
      var h5 = document.createElement("h5");
      h5.innerText = "댓글이 달렸습니다."
      var p = document.createElement("p");
      p.innerText = message.writer + "님 " + message.content;
      newNotice.appendChild(h5);
      newNotice.appendChild(p);
      noti.appendChild(newNotice);

      setTimeout(function() {
        noti.removeChild(newNotice);
      }, 5000);
    });
  });
}