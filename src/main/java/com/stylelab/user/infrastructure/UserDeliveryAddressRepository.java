package com.stylelab.user.infrastructure;

import com.stylelab.user.domain.UserDeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDeliveryAddressRepository {

    private final UserDeliveryAddressJpaRepository userDeliveryAddressJpaRepository;
    private final UserMapper userMapper;

    public UserDeliveryAddress save(UserDeliveryAddress userDeliveryAddress) {

        return userMapper.mapUserDeliveryAddressEntityToDomain(
                userDeliveryAddressJpaRepository.save(
                        userMapper.mapUserDeliveryAddressToEntity(userDeliveryAddress)
                )
        );
    }
}
