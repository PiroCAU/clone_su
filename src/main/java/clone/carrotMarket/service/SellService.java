package clone.carrotMarket.service;

import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.*;
import clone.carrotMarket.repository.MemberRepository;
import clone.carrotMarket.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellService {

    private final SellRepository sellRepository;
    private final SellLikeService sellLikeService;
    private final ChatService chatService;
    private final MemberRepository memberRepository;

    public Sell save(Sell sell) {
        return sellRepository.save(sell);
    }

    public Sell findById(Long id) {
        return sellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

    public SellDetailResponseDto findDetail(Sell sell) {

        List<Sell> top5ByMember = sellRepository.findTop5ByMember(sell.getMember());

        if (top5ByMember.contains(sell)) {
            top5ByMember.remove(sell);
        }

        SellDetailResponseDto sellDetailResponseDto = SellConverter.sellToSellDetailResponseDto(sell, top5ByMember, sellLikeService);
        return sellDetailResponseDto;
    }

    public List<MySellResponseDTO> findMySell(Member member, SellStatus status) {
        List<Sell> sells = sellRepository.findAllByMemberAndSellStatusOrderByCreated_atDesc(member, status);
        List<MySellResponseDTO> dtos = SellConverter.sellToMySellResponseDTO(sells, member, chatService, sellLikeService);

        return dtos;
    }

    public List<SellDTO> findOtherUserSells(Long currendUserId, Member member, int page, int size) {
        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "created_at"));

        List<Sell> content = sellRepository.findByMemberIdNotAndSellStatusNot(currendUserId, SellStatus.FIN, request).getContent();
        List<SellDTO> sellDTOS = SellConverter.sellToSellDTO(content, member, sellLikeService, chatService);
        return sellDTOS;
    }

    public List<SellDTO> findOtherSells(Long sellId, Long memberId, SellStatus sellStatus) {
        Sell sell = sellRepository.findById(sellId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Sell> sells = null;
        if (sellStatus == null || sellStatus != SellStatus.FIN) {
            sells = sellRepository.findTop5BySellNotAndMemberAndSellStatusNot(sell, member, SellStatus.FIN);
        } else if (sellStatus == SellStatus.SELLING || sellStatus == SellStatus.BOOKING) {
            sells = sellRepository.findTop5BySellNotAndMemberAndSellStatus(sell, member, sellStatus);
        }

        List<SellDTO> sellDTOS = SellConverter.sellToSellDTO(sells, member, sellLikeService, chatService);
        return sellDTOS;
    }

    public SellDetailDTO findByIdDTO(Long sellId) {
        Sell sell = sellRepository.findById(sellId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        SellDetailDTO sellDetailDTO = new SellDetailDTO(sell);
        return sellDetailDTO;
    }

    public UpdateSellDto editSellRequest(Long sellId, Member member) {
        Sell sell = sellRepository.findById(sellId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!isEditableMember(sell, member)) {
            throw new IllegalArgumentException("적합한 사용자가 아닙니다");
        }
        UpdateSellDto dto = SellConverter.sellToUpdateSellDTO(sell);
        return dto;
    }

    @Transactional
    public void editSell(UpdateSellDto dto, Member member) {
        Sell sell = sellRepository.findById(dto.getSellId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!isEditableMember(sell, member)) {
            throw new IllegalArgumentException("적합한 사용자가 아닙니다");
        }


    }

    public boolean isEditableMember(Sell sell, Member member) {
        if (sell.getMember() == member) {
            return true;
        } else {
            return false;
        }
    }
}
