package main.java.bankbook.member.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.java.bankbook.common.util.ScannerService;
import main.java.bankbook.member.model.Member;

public class MemberRepository {

    private static final String MEMBER_FILE_PATH = "/Users/ashe/Desktop/dev/startingJava/src/main/resource/member.txt";
    private static final String SEPARATOR = " ";

    private Scanner scanner;

    private BufferedWriter bufferedWriter;

    public MemberRepository() {
    }

    public void openWriter() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(MEMBER_FILE_PATH), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원정보 조회
     */
    public Member selectMember(String id, String password) {

        List<Member> memberList = getAllMembersInFile();

        for (Member member : memberList) {
            if (member.getId().equals(id) && member.getPassword().equals(password)) {
                return member;
            }
        }

        return new Member();
    }

    /**
     * 아이디 존재 여부 확인(중복가입 방지)
     */
    public boolean existId(String id) {
        List<Member> memberList = getAllMembersInFile();

        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 신규회원가입
     */
    public Member insertMember(Member member) {

        try {
            openWriter();
            bufferedWriter.write(member.convert2TextData());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return member;
    }

    private List<Member> getAllMembersInFile() {

        List<Member> memberList = new ArrayList<>();

        scanner = ScannerService.getFileScanner(MEMBER_FILE_PATH);

        while (scanner.hasNext()) {
            String[] memberStringArray = scanner.nextLine().split(SEPARATOR);

            if (memberStringArray.length != 3) {
                // DB에 이상한 값이 있는거임
                continue;
            }

            Member member = new Member(memberStringArray[0], memberStringArray[1],
                memberStringArray[2]);

            memberList.add(member);
        }

        return memberList;
    }
}
