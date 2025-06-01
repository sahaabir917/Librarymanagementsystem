package com.infinitycodehubltd.librarymanagement.jwt;



import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new User(
                member.getEmail(),
                member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()))
        );
    }
}
