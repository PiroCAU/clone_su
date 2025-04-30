package clone.carrotMarket.domain;

import clone.carrotMarket.repository.ChatRepository;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_id")
    private Sell sell;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "sender_id")
    private Member sender;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.REMOVE)
    private List<ChatMessage> chatMessage = new ArrayList<>();

    public ChatRoom(Sell sell, Member member);
}
