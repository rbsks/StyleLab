package com.stylelab.user.infrastructure;

import com.stylelab.user.constant.UserRole;
import com.stylelab.user.domain.User;
import com.stylelab.user.domain.UserDeliveryAddress;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toUserEntity(User user) {

        return UserEntity.builder()
                .userId(user.userId())
                .email(user.email())
                .password(user.password())
                .name(user.name())
                .nickname(user.nickname())
                .phoneNumber(user.phoneNumber())
                .role(user.role())
                .withdrawal(user.withdrawal())
                .withdrawalAt(user.withdrawalAt())
                .build();
    }

    public User toUser(UserEntity userEntity) {

        return new User(
                userEntity.getUserId(), userEntity.getEmail(), userEntity.getPassword(),
                userEntity.getName(), userEntity.getNickname(), userEntity.getPhoneNumber(), userEntity.getRole(),
                userEntity.isWithdrawal(), userEntity.getWithdrawalAt()
        );
    }

    public User saveUser(String email, String encodePassword, String name, String nickname, String phoneNumber) {

        return new User(
                null, email, encodePassword, name, nickname, phoneNumber, UserRole.ROLE_USER, false, null
        );
    }

    public UserDeliveryAddressEntity toUserDeliveryAddressEntity(UserDeliveryAddress userDeliveryAddress) {

        return UserDeliveryAddressEntity.builder()
                .userAddressId(userDeliveryAddress.userAddressId())
                .userEntity(this.toUserEntity(userDeliveryAddress.user()))
                .address(userDeliveryAddress.address())
                .addressDetail(userDeliveryAddress.addressDetail())
                .postalCode(userDeliveryAddress.postalCode())
                .addressAliases(userDeliveryAddress.addressAliases())
                .defaultDeliveryAddress(userDeliveryAddress.defaultDeliveryAddress())
                .build();
    }

    public UserDeliveryAddress toUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity) {

        return new UserDeliveryAddress(
                userDeliveryAddressEntity.getUserAddressId(), this.toUser(userDeliveryAddressEntity.getUserEntity()),
                userDeliveryAddressEntity.getAddress(), userDeliveryAddressEntity.getAddressDetail(), userDeliveryAddressEntity.getPostalCode(),
                userDeliveryAddressEntity.getAddressAliases(), userDeliveryAddressEntity.getDefaultDeliveryAddress()
        );
    }

    public UserDeliveryAddress saveUserDeliveryAddress(
            Long userId, String address, String addressDetail, String postalCode, String addressAliases, Boolean defaultDeliveryAddress) {

        return new UserDeliveryAddress(
                null, this.createUser(userId), address, addressDetail, postalCode, addressAliases, defaultDeliveryAddress
        );
    }

    private User createUser(Long userId) {

        return new User(userId, null, null, null, null, null, null, false, null);
    }
}
