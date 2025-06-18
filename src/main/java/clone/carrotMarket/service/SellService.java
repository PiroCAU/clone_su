package clone.carrotMarket.service;

import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.ProductImage;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.*;
import clone.carrotMarket.repository.MemberRepository;
import clone.carrotMarket.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellService {

    private final SellRepository sellRepository;
    private final SellLikeService sellLikeService;
    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final FileStorageService storageService;

    @Transactional
    public Sell save(Sell sell) {
        return sellRepository.save(sell);
    }

    public Sell findById(Long id) {
        return sellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

//    public List<SellListResponseDTO> getAllSellList(Member member) {
//        String place = member.getPlace();
//        List<Sell> sells = sellRepository.findAllByPlace(place);
//    }

    //sell 게시물의 상세 페이지
    //해당 게시물의 자세한 설명
    //같은 게시물의 작성자의 다른 글 간단하게 보여주기
    public SellDetailResponseDto findDetail(Sell sell) {

        List<Sell> top5ByMember = sellRepository.findTop5ByMember(sell.getMember());

        if (top5ByMember.contains(sell)) {
            top5ByMember.remove(sell);
        }

        SellDetailResponseDto sellDetailResponseDto = SellConverter.sellToSellDetailResponseDto(sell, top5ByMember, sellLikeService);
        return sellDetailResponseDto;
    }

    public List<MySellResponseDTO> findMySellList(Member member, String status) {
        SellStatus sellStatus;
        if (status.equals("판매중")) {
            sellStatus = SellStatus.SELLING;
        } else {
            sellStatus = SellStatus.FIN;
        }
        List<Sell> sells = sellRepository.findAllByMemberAndSellStatusOrderByCreatedAtDesc(member, sellStatus);
        List<MySellResponseDTO> dtos = SellConverter.sellToMySellResponseDTO(sells, member, chatService, sellLikeService);

        return dtos;
    }

    public List<SellDTO> findOtherUserSells(Long currendUserId, Member member, int page, int size) {
        log.info("findOtherUserSell: currentUserId: " , currendUserId);
        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Sell> content = sellRepository.findByMemberIdNotAndSellStatusNot(currendUserId, SellStatus.FIN, request).getContent();
        List<SellDTO> sellDTOS = SellConverter.sellToSellDTO(content, member, sellLikeService, chatService);
        return sellDTOS;
    }

    public List<SellDTO> findOtherSells(Long sellId, Long memberId, SellStatus sellStatus) {
        Sell sell = sellRepository.findById(sellId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Sell> sells = null;
        if (sellStatus == null || sellStatus != SellStatus.FIN) {
            sells = sellRepository.findTop5ByIdIsNotAndMemberAndSellStatusIsNot(sellId, member, SellStatus.FIN);
        } else if (sellStatus == SellStatus.SELLING || sellStatus == SellStatus.BOOKING) {
            sells = sellRepository.findTop5ByIdNotAndMemberAndSellStatus(sellId, member, sellStatus);
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

        //사용자가 새로운 이미지를 넣었는지 확인하고 ProductImg로 변환하여 입력
        if (dto.getImageFiles() != null) {
            List<ProductImage> productImages = storageService.getProductImgs(dto.getImageFiles());
            for (ProductImage productImage : productImages) {
                productImage.setProductImg(sell);
            }
        }

        sell.changeTitle(dto.getTitle());
        sell.changeContent(dto.getContent());
        sell.changePrice(dto.getPrice());
        sell.changeCategory(dto.getCategory());
        sell.changePlace(dto.getPlace());
        sell.changeUpdatedAt();
    }

    @Transactional
    public void updateSellStatus(Long sellId, Member member, SellStatus status) {
        Sell sell = findById(sellId);

        if (!isEditableMember(sell, member)) {
            throw new IllegalArgumentException("적합한 사용자가 아닙니다");
        }

        sell.changeSellStatus(status);
    }

    @Transactional
    public void deleteSell(Long sellId, Member member) {
        Sell sell = findById(sellId);
        if (!isEditableMember(sell, member)) {
            throw new IllegalArgumentException("적합한 사용자가 아닙니다");
        }

        sell.delete();
    }

    //게시글 수정 명령이 들어오면 수정한 사람과 작성자를 비교해서 수정 가능한 지 확인한다.
    public boolean isEditableMember(Sell sell, Member member) {
        if (sell.getMember() == member) {
            return true;
        } else {
            return false;
        }
    }
}
