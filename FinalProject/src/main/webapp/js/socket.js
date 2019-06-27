const sock = new SockJS("/socket");
const client = Stomp.over(sock);

function connectStomp(subscribe) {
  client.connect({}, function() {
    client.subscribe("/user/queue/notice", function(data) {
      console.log(data);
    });
    if (subscribe) {
      subscribe();  
    }
  });
}

connectStomp();