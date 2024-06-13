package com.dev.shop.member.service.impl;

import com.dev.shop.member.dao.MemberDao;
import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.dto.ReservationCriteriaDto;
import com.dev.shop.member.dto.RoomAndImageDto;
import com.dev.shop.member.dto.getReserveInfoDto;
import com.dev.shop.member.service.MemberService;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.utils.Pagination;
import com.dev.shop.utils.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    /**
     * 최근 등록된 방 (6개)
     * @return 최근 등록된 방과 이미지 정보
     */
    @Override
    public List<RoomAndImageDto> getMainInfoNewSpot() {
        return memberDao.selectRoomAndImage();
    }


    /**
     * 회원가입
     * @param memberDto - 입력한 회원가입 정보
     */
    @Override
    public void memberRegister(MemberDto memberDto) {

        memberDto.setMemberPw(bCryptPasswordEncoder.encode(memberDto.getMemberPw()));
        memberDto.setMemberAuth("USER");
        memberDto.setAppendDate(localTime);
        memberDto.setUpdateDate(localTime);

        memberDao.insertMemberRegister(memberDto);
    }

    @Override
    public MemberDto memberInfoByAuthId(String authId) {
        return memberDao.selectMemberInfoById(authId);
    }

    @Override
    public void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk) {

        String oldPw = memberDao.selectMemberPw(memberDto.getMemberNo());

        memberDto.setMemberPhone(memberDto.getMemberPhone());
        memberDto.setUpdateDate(localTime);
        memberDto.setMemberEmail(memberDto.getMemberEmail());

        if (bCryptPasswordEncoder.matches(memberDto.getMemberPw(), oldPw) && memberNewPw.equals(memberNewPwChk) && memberDto.getMemberPw() != null) {

            memberDto.setMemberPw(bCryptPasswordEncoder.encode(memberNewPw));

            memberDao.updateMemberInformation(memberDto);

        } else {

            memberDao.updateMemberInformation(memberDto);
        }


    }

    /**
     * memberNo 조회
     * @param authId
     * @return memberNo
     */
    @Override
    public Long getMemberNoByAuthId(String authId) {
        return memberDao.selectMemberNo(authId);
    }

    /**
     * 게시글 리스트 조회
     * @param memberNo
     * @return
     */
    @Override
    public PagingResponse<getReserveInfoDto> getReservationInfoByMemberNo(Long memberNo, final ReservationCriteriaDto params) {
        int count = memberDao.countReservationInfo(memberNo, params);
        if(count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);

        params.setPagination(pagination);

        List<getReserveInfoDto> list = memberDao.selectReservationInfoByMemberNo(memberNo, params);

        return new PagingResponse<>(list, pagination);
    }

    /**
     * 예약 삭제
     * @param reservationNo - 삭제할 예약 번호
     */
    @Override
    public void cancelReservation(Long reservationNo) {
        memberDao.updateReservationByReservationNo(reservationNo);
    }




}
