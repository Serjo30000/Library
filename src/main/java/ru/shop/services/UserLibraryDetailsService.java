package ru.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shop.repositories.UserLibraryRepo;
import ru.shop.security.UserLibraryDetails;

@Service
@AllArgsConstructor
public class UserLibraryDetailsService implements UserDetailsService {
    private final UserLibraryRepo aRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var v = aRepo.findByLogin(s);

        if (v.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserLibraryDetails(v.get());
    }
}
