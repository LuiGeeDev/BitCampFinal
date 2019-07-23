package kr.or.bit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 웹소켓 설정
 * 
 * endPoint: 클라이언트에서 웹소켓에 접속하는 주소 ('/socket')
 * withSockJS(): SockJS 사용으로 웹소켓 객체 미지원 브라우저에서도 사용 가능
 * enableSimpleBroker: 클라이언트가 subscribe하는 주소, 웹소켓 컨트롤러가 응답을 보내는 주소
 * setApplicationDestinationPrefixes: 클라이언트가 웹소켓을 이용하여 메시지를 보내는 주소의 접두어
 * setUserDestinationPrefix: 특정 사용자에게만 응답이 가는 주소의 접두어
 * 
 * @작성자: 윤종석
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic", "/queue");
    registry.setApplicationDestinationPrefixes("/app");
    registry.setUserDestinationPrefix("/user");
  }
}
