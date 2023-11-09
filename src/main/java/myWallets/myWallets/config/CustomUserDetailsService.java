package myWallets.myWallets.config;

import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepo customerRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepo.findByUsernameOrEmail(username);
        if (customer.isEmpty()){
            throw new UsernameNotFoundException("Customer not found for the username " + username);
        }
        Customer customer1 = customer.get();

        Set<GrantedAuthority> authorities = customer1.getRolesSet()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getRoles())).collect(Collectors.toSet());

        return new User(
                customer1.getEmail() ,
                customer1.getPassword() ,
                authorities
                );

    }
}
