package main.java.bankbook.member.service;

import main.java.bankbook.member.model.Member;
import main.java.bankbook.member.repository.MemberRepository;

public class MemberService {
    MemberRepository memberRepository;

    public MemberService() {
        memberRepository = new MemberRepository();
    }

    public Member getMember(String id, String password) {
        return memberRepository.selectMember(id, password);
    }

    public boolean existId(String id) {
        return memberRepository.existId(id);
    }

    /**
     * 회원가입
     *
     * @return 회원가입 후 바로 가입정보 리턴
     */
    public Member doRegister(String id, String password, String name) {

        Member member = new Member(id, password, name);

        return memberRepository.insertMember(member);
    }

}
