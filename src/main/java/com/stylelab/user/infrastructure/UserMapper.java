package com.stylelab.user.infrastructure;

import com.stylelab.user.domain.User;
import com.stylelab.user.domain.UserDeliveryAddress;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity mapUserToEntity(User user) {

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

    public User mapUserEntityToDomain(UserEntity userEntity) {

        return User.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .nickname(userEntity.getNickname())
                .phoneNumber(userEntity.getPhoneNumber())
                .role(userEntity.getRole())
                .withdrawal(userEntity.isWithdrawal())
                .withdrawalAt(userEntity.getWithdrawalAt())
                .build();
    }

    public UserDeliveryAddressEntity mapUserDeliveryAddressToEntity(UserDeliveryAddress userDeliveryAddress) {

        return UserDeliveryAddressEntity.builder()
                .userAddressId(userDeliveryAddress.userAddressId())
                .userEntity(this.mapUserToEntity(userDeliveryAddress.user()))
                .address(userDeliveryAddress.address())
                .addressDetail(userDeliveryAddress.addressDetail())
                .postalCode(userDeliveryAddress.postalCode())
                .addressAliases(userDeliveryAddress.addressAliases())
                .defaultDeliveryAddress(userDeliveryAddress.defaultDeliveryAddress())
                .build();
    }

    public UserDeliveryAddress mapUserDeliveryAddressEntityToDomain(UserDeliveryAddressEntity userDeliveryAddressEntity) {

        return UserDeliveryAddress.builder()
                .userAddressId(userDeliveryAddressEntity.getUserAddressId())
                .user(this.mapUserEntityToDomain(userDeliveryAddressEntity.getUserEntity()))
                .address(userDeliveryAddressEntity.getAddress())
                .addressDetail(userDeliveryAddressEntity.getAddressDetail())
                .postalCode(userDeliveryAddressEntity.getPostalCode())
                .addressDetail(userDeliveryAddressEntity.getAddressDetail())
                .defaultDeliveryAddress(userDeliveryAddressEntity.getDefaultDeliveryAddress())
                .build();
    }
}
