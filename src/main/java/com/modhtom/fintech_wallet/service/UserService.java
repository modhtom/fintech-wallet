package com.modhtom.fintech_wallet.service;

import com.modhtom.fintech_wallet.DTO.UserRequestDTO;
import com.modhtom.fintech_wallet.DTO.UserRespondDTO;
import com.modhtom.fintech_wallet.repo.UserRepository;
import com.modhtom.fintech_wallet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public UserRespondDTO updateUser(UUID userId, UserRequestDTO userRequestDTO) {
        User existingUser = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        boolean isUpdated = false;

        if (userRequestDTO.getMail() != null && !userRequestDTO.getMail().isEmpty()) {
            existingUser.setEmail(userRequestDTO.getMail());
            isUpdated = true;
        }

        if (userRequestDTO.getPassword_unHashed() != null && !userRequestDTO.getPassword_unHashed().isEmpty()) {
            existingUser.setPassword_hash(bCryptPasswordEncoder.encode(userRequestDTO.getPassword_unHashed()));
            isUpdated = true;
        }

        if (userRequestDTO.getUsername() != null && !userRequestDTO.getUsername().isEmpty()) {
            existingUser.setUsername(userRequestDTO.getUsername());
            isUpdated = true;
        }

        if (isUpdated) {
            existingUser.setUpdated_at(new Date());
            repo.save(existingUser);
        }

        return mapToUserRespondDTO(existingUser);
    }

    private UserRespondDTO mapToUserRespondDTO(User user) {
        UserRespondDTO dto = new UserRespondDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setMail(user.getEmail());
        dto.setEnabled(user.getEnabled());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        return dto;
    }
}
