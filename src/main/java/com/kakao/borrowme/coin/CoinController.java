package com.kakao.borrowme.coin;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class CoinController {

    private final CoinService coinService;

    // 1. 충전 금액 조회하기
    @GetMapping("")
    public ResponseEntity<?> getUserCoin(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CoinResponse.CoinInfoDTO responseDTO = coinService.getUserCoin(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // 2. 충전하기
    @PostMapping("/charge")
    public ResponseEntity<?> chargeCoin(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CoinRequest.ChargeCoinDTO chargeCoinDTO) {
        CoinResponse.CoinInfoDTO responseDTO = coinService.chargeCoin(userDetails.getUser(), chargeCoinDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // 3. 결제하기
    @PostMapping("/{productId}/create") // endpoint 수정
    public ResponseEntity<?> useCoin(@PathVariable String productId, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CoinRequest.UseCoinDTO useCoinDTO) {
        LocalDateTime startAt = LocalDateTime.parse(useCoinDTO.getStartAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endAt = LocalDateTime.parse(useCoinDTO.getEndAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        coinService.useCoin(userDetails.getUser(), productId, startAt, endAt);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success((Object)null);
        return ResponseEntity.ok(apiResult);
    }
}