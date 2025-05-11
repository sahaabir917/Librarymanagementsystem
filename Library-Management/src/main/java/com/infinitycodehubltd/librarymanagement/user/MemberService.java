package com.infinitycodehubltd.librarymanagement.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    //Dependency Injection

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //end of Dependency Injection

    public List<Member> getMember(){
//        return List.of(new Member(1L, "Ashim Kumar Saha","ashimkumar1402@gmail.com","+8801715403687","22/A Jhultuly,Faridpur","10-02-1998"));

        return memberRepository.findAll();

    }

//    public boolean addNewMember(Member member) {
//
////        Optional<Member> getStudentEmail = memberRepository.findMemberByEmail(member.getEmail());
////        if(getStudentEmail.isPresent()){
//////            throw new IllegalStateException("Email Already taken");
////            return false;
////        }
//        member.setId(0);
//
//        memberRepository.save(member);
//            System.out.println(member);
//            return true;
//
//
//
//
//    }


public boolean addNewMember(Member member) {
    if (member.getId() != 0 && !memberRepository.existsById(member.getId())) {
        return false; // Or throw custom exception
    }

    Optional<Member> existing = memberRepository.findMemberByEmail(member.getEmail());

    if (existing.isPresent()) {
        return false; // email already exists
    }

    member.setId(0); // Always save as a new entity
    memberRepository.save(member);
    return true;
}

}
