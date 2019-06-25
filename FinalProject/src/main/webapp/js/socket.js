var sock = new SockJS("/socket");
var client = Stomp.over(sock);

client.connect({}, function() {
  client.subscribe("/user/queue/notice", function(data) {
    console.log(data);
  })
});