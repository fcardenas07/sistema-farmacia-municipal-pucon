package cl.ufro.dci.pds.infraestructura;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Lo que el servidor puede ENVIAR (topics)
        config.enableSimpleBroker("/topic");
        // Prefix que usarán tus @MessageMapping si los ocupas
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")        // endpoint WebSocket
                .setAllowedOriginPatterns("*")
                .withSockJS();             // si quieres fallback SockJS
    }
}