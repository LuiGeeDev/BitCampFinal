var sock = new SockJS("/sample");
var client = Stomp.over(sock);

client.connect({}, function() {});