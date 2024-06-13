package com.dev.shop.member.service;


import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.dto.ReservationCriteriaDto;
import com.dev.shop.member.dto.RoomAndImageDto;
import com.dev.shop.member.dto.getReserveInfoDto;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.utils.PagingResponse;

import java.util.List;

public interface MemberService {
    /**
     * 새로 등록된 스팟(6개) 조회
     *
     * @return 조회된 스팟 6개
     */


    /**
     * 멤버 회원가입
     * @param memberDto - 회원가입 할 정보
     */
    void memberRegister(MemberDto memberDto);

    /**
     *
     * @param authId - 로그인 한 멤버의 아이디
     * @return 로그인 한 멤버의 정보
     */
    MemberDto memberInfoByAuthId(String authId);

    /**
     * 회원 정보 수정
     * @param memberDto - 멤버 정보 dto
     * @param memberNewPw - 설정하려는 새로운 비밀번호
     * @param memberNewPwChk - 설정하려는 새로운 비밀번호 확인
     */
    void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk);

    Long getMemberNoByAuthId(String authId);

    PagingResponse<getReserveInfoDto> getReservationInfoByMemberNo(Long memberNo, ReservationCriteriaDto params);

    void cancelReservation(Long reservationNo);


    List<RoomAndImageDto> getMainInfoNewSpot();
}
