package clone.carrotMarket.restController;

import clone.carrotMarket.apiPayload.ApiResponse;
import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.dto.chat.ChatRoomDTO;
import clone.carrotMarket.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "ChatRoom", description = "채팅방 관련 API")
public class ChatRoomRestController {

    private final ChatService chatService;

    @GetMapping("/rooms")
    @Operation(summary = "나의 채팅방 목록 조회", description = "특정 회원이 참여한 채팅방 목록을 조회합니다.")
    public ApiResponse<List<ChatRoomDTO>> myRooms(
            @Parameter(hidden = true)
            @LoginMember Member member) {
        List<ChatRoomDTO> list = chatService.findByMember(member); // member 대신 memberId로 조회하는 메서드 필요
        return ApiResponse.success(list);
    }

    @GetMapping("/rooms/{sellId}")
    @Operation(summary = "상품 관련 채팅방 목록 조회", description = "특정 상품과 관련된 채팅방 목록을 조회합니다.")
    public ApiResponse<List<ChatRoomDTO>> cheatingRoomBySell(
            @Parameter(description = "상품 ID", example = "10") @PathVariable Long sellId) {
        List<ChatRoomDTO> list = chatService.findBySell(sellId);
        return ApiResponse.success(list);
    }

    @PostMapping("/rooms/{sellId}")
    @Operation(summary = "채팅방 생성", description = "상품 ID와 회원 ID를 기반으로 새로운 채팅방을 생성합니다.")
    public ApiResponse<Long> createChatRoomBySell(
            @Parameter(description = "상품 ID", example = "10") @PathVariable Long sellId,
            @Parameter(hidden = true)
            @LoginMember Member member) {
        ChatRoom result = chatService.createChatRoom(sellId, member); // member → memberId로 대체한 메서드 필요
        return ApiResponse.success(result.getId());
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "채팅방 입장", description = "채팅방 ID와 회원 ID로 채팅방 정보에 접근합니다.")
    public ApiResponse<ChatRoomDTO> interChatRoom(
            @Parameter(description = "채팅방 ID", example = "5") @PathVariable Long roomId,
            @Parameter(hidden = true)
            @LoginMember Member member) {
        ChatRoomDTO chatRoomDTO = chatService.accessById(roomId, member); // member → memberId 대체
        return ApiResponse.success(chatRoomDTO);
    }
}
