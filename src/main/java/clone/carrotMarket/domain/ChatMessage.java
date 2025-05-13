package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessage_id")
    private Long id;

    private String message;
    private LocalDateTime sendTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public ChatMessage(String message, Member sender, ChatRoom chatRoom) {
        this.message = message;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.sendTime = LocalDateTime.now();
    }
}
