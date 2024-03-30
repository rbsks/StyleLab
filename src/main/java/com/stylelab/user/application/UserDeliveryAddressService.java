package com.stylelab.user.application;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.exception.UserException;
import com.stylelab.user.infrastructure.UserDeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.stylelab.common.exception.ServiceError.BAD_REQUEST;
import static com.stylelab.user.exception.UserError.DELIVERY_ADDRESS_SAVE_FAIL;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeliveryAddressService {

    private final UserDeliveryAddressRepository userDeliveryAddressRepository;

    public void createUserDeliveryAddress(CreateUserDeliveryAddressCommand createUserDeliveryAddressCommand) {

        try {
            userDeliveryAddressRepository.save(
                    createUserDeliveryAddressCommand.saveUserDeliveryAddress()
            );
        } catch (DataAccessException exception) {
            throw new UserException(DELIVERY_ADDRESS_SAVE_FAIL, exception);
        } catch (RuntimeException exception) {
            throw new ServiceException(BAD_REQUEST, exception);
        }
    }
}
