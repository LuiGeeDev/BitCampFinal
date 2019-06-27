const sock = new SockJS("/socket");
const client = Stomp.over(sock);

client.connect({}, function() {
  client.subscribe("/user/queue/notice", function(data) {
    console.log(data);
  });
});