package com.infinitycodehubltd.librarymanagement.user;

import com.infinitycodehubltd.librarymanagement.apiresponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired                                              //dependency injection
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping
    public List<Member> getMember(){
        return memberService.getMember();
    }


    @PostMapping("/addNewMember")
    public ResponseEntity<ApiResponse> addNewMember(@RequestBody Member member) {
        boolean created = memberService.addNewMember(member);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("New member created", 201));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("This email is already taken", 409));
        }
    }
}
