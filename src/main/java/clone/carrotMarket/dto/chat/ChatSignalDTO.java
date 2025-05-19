package clone.carrotMarket.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSignalDTO {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }
}
